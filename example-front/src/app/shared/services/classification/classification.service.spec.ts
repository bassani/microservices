import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MessageService } from 'primeng/api';
import { of, throwError } from 'rxjs';
import { MOCK_BLOB_CSV, MOCK_CUSTOM_PAGED } from '../../utils/mock';
import { ApiService } from '../api/api.service';

import { ClassificationService } from './classification.service';

describe('ClassificationService', () => {
  let service: ClassificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        { provide: MessageService, useValue: { add: () => {} } },
        { provide: ApiService, useValue: { request: (...args: any[]) => {} } },
      ],
    });
    service = TestBed.inject(ClassificationService);
  });

  it('Deve ser criado o serviço', () => {
    expect(service).toBeTruthy();
  });

  describe('Pesquisa de classificação', () => {
    it('Deve retornar a resposta do servidor', () => {
      const mock = [...Array(10)].map((e, i) => ({
        id: i + 1,
        name: `Test ${i + 1}`,
      }));
      spyOn(service['api'], 'request').and.returnValue(of(mock));
      service.getAllClassifications('test').subscribe((data) => {
        expect(data).toEqual(mock);
      });
      expect(service['api'].request).toHaveBeenCalledTimes(1);
    });

    it('Deve realizar handling dos erros 404', () => {
      const mock = throwError({ status: 404, error: { message: 'not found' } });
      spyOn(service['api'], 'request').and.returnValue(mock);
      spyOn(service['message'], 'add').and.returnValue(null);
      service.getAllClassifications('test').subscribe(
        (data) => {
          expect(data).toEqual([]);
        },
        (err) => {
          expect(err).toEqual(null);
          expect(service['message'].add).toHaveBeenCalledTimes(0);
        }
      );
      expect(service['api'].request).toHaveBeenCalledTimes(1);
    });

    it('Deve realizar handling dos erros de servidor', () => {
      const mock = throwError({
        status: 500,
        error: { message: 'Internal server error' },
      });

      spyOn(service['api'], 'request').and.returnValue(mock);
      spyOn(service['message'], 'add').and.returnValue(null);
      service.getAllClassifications('test').subscribe(
        (data) => {
          expect(data).toEqual(null);
        },
        (err) => {
          expect(err.status == 500);
          expect(service['message'].add).toHaveBeenCalledTimes(1);
        }
      );
      expect(service['api'].request).toHaveBeenCalledTimes(1);
    });
  });

  describe('Get All', () => {
    it('Deve receber uma paginação e listar as classificações', () => {
      let mock = MOCK_CUSTOM_PAGED(
        { name: 'teste', description: 'teste', active: true },
        10
      );
      spyOn(service['api'], 'request').and.returnValue(of(mock));
      expect(service.classifications$.value.content).toHaveLength(0);
      service.getAll(0, 10);
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service.classifications$.value.content).toHaveLength(10);
    });
    it('Deve receber uma paginação e listar as classificações', () => {
      let mock = MOCK_CUSTOM_PAGED(
        { name: 'teste', description: 'teste', active: true },
        10
      );
      service.classifications$.next(mock);
      spyOn(service['api'], 'request').and.returnValue(
        throwError({ status: 500, error: { message: 'Erro Teste' } })
      );
      expect(service.classifications$.value.content).toHaveLength(10);
      service.getAll(0, 10);
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service.classifications$.value.content).toHaveLength(10);
      expect(service.classifications$.value.status).toEqual('ERROR');
    });
  });

  describe('Create Classification', () => {
    it('Deve informar quando a classificação e cadastrada com sucesso', () => {
      let mock = { name: 'teste', description: 'teste', active: true, id: 1 };
      let mockList = MOCK_CUSTOM_PAGED(
        { name: 'teste', description: 'teste', active: true },
        10
      );
      spyOn(service['api'], 'request').and.returnValue(of(mock));
      spyOn(service, 'getAll').and.returnValue(of(mockList));
      spyOn(service['message'], 'add').and.returnValue(of({}));
      service
        .createClassification({ name: 'teste', description: 'teste', active: true })
        .subscribe();
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service['message'].add).toHaveBeenCalledWith({
        severity: 'success',
        summary: 'Classificação cadastrada com sucesso',
        detail: 'Sucesso',
      });
      expect(service.getAll).toHaveBeenCalledTimes(1);
      expect(service.getAll).toHaveBeenCalledWith(0, 10);
    });

    it('Deve verificar se o attributo active não e booleano', () => {
      let mock = { name: 'teste', description: 'teste', active: 'habilitado', id: 1 };
      let mockList = MOCK_CUSTOM_PAGED(
        { name: 'teste', description: 'teste', active: 'habilitado' },
        10
      );
      spyOn(service['api'], 'request').and.returnValue(of(mock));
      spyOn(service, 'getAll').and.returnValue(of(mockList));
      spyOn(service['message'], 'add').and.returnValue(of({}));
      service
        .createClassification({ name: 'teste', description: 'teste', active: 'habilitado' })
        .subscribe();
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service['message'].add).toHaveBeenCalledWith({
        severity: 'success',
        summary: 'Classificação cadastrada com sucesso',
        detail: 'Sucesso',
      });
      expect(service.getAll).toHaveBeenCalledTimes(1);
      expect(service.getAll).toHaveBeenCalledWith(0, 10);
    });

    it('Deve informar quando a classificação e cadastrada com erro', () => {
      let mock = { status: 500, error: { message: 'Erro Teste' } };
      spyOn(service['api'], 'request').and.returnValue(throwError(mock));
      spyOn(service, 'getAll').and.returnValue(of({}));
      spyOn(service['message'], 'add').and.returnValue(of({}));
      service
        .createClassification({ name: 'teste', description: 'teste' })
        .subscribe();
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service['message'].add).toHaveBeenCalledWith({
        severity: 'error',
        summary: mock.error.message,
        detail: 'Não foi possível cadastrar classificação',
      });
      expect(service.getAll).toHaveBeenCalledTimes(0);
    });
  });
  describe('Update Classification', () => {
    it('Deve informar quando a classificação e alterada com sucesso', () => {
      let mock = { name: 'teste', description: 'teste', active: true, id: 1 };
      let mockList = MOCK_CUSTOM_PAGED(
        { name: 'teste', description: 'teste', active: true },
        10
      );
      spyOn(service['api'], 'request').and.returnValue(of(mock));
      spyOn(service, 'getAll').and.returnValue(of(mockList));
      spyOn(service['message'], 'add').and.returnValue(of({}));
      service
        .updateClassification({ name: 'teste', description: 'teste', active: 'habilitado' })
        .subscribe();
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service['message'].add).toHaveBeenCalledWith({
        severity: 'success',
        summary: 'Classificação alterada com sucesso',
        detail: 'Sucesso',
      });
      expect(service.getAll).toHaveBeenCalledTimes(1);
      expect(service.getAll).toHaveBeenCalledWith(0, 10);
    });

    it('Deve aceitar valores booleanos', () => {
      let mock = { name: 'teste', description: 'teste', active: true, id: 1 };
      let mockList = MOCK_CUSTOM_PAGED(
        { name: 'teste', description: 'teste', active: true },
        10
      );
      spyOn(service['api'], 'request').and.returnValue(of(mock));
      spyOn(service, 'getAll').and.returnValue(of(mockList));
      spyOn(service['message'], 'add').and.returnValue(of({}));
      service
        .updateClassification({ name: 'teste', description: 'teste', active: true })
        .subscribe();
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service['message'].add).toHaveBeenCalledWith({
        severity: 'success',
        summary: 'Classificação alterada com sucesso',
        detail: 'Sucesso',
      });
      expect(service.getAll).toHaveBeenCalledTimes(1);
      expect(service.getAll).toHaveBeenCalledWith(0, 10);
    });
    it('Deve informar quando a classificação e alterada com erro', () => {
      let mock = { status: 500, error: { message: 'Erro Teste' } };
      spyOn(service['api'], 'request').and.returnValue(throwError(mock));
      spyOn(service, 'getAll').and.returnValue(of({}));
      spyOn(service['message'], 'add').and.returnValue(of({}));
      service
        .updateClassification({ name: 'teste', description: 'teste', active: false })
        .subscribe();
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service['message'].add).toHaveBeenCalledWith({
        severity: 'error',
        detail: mock.error.message,
        summary: 'Não foi possível alterar a classificação',
      });
      expect(service.getAll).toHaveBeenCalledTimes(0);
    });
  });

  describe('Download dos vigentes', () => {
    it('Deve realizar o download do cadastro vigente com sucesso', () => {
      spyOn(service, 'save').and.returnValue({});
      spyOn(service['message'], 'add').and.returnValue({});
      spyOn(service['api'], 'request').and.returnValue(of(MOCK_BLOB_CSV));
      service.downloadCurrentClassifications();
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service.save).toHaveBeenCalledTimes(1);
      expect(service.save).toHaveBeenCalledWith(
        MOCK_BLOB_CSV,
        `cadastro_vigente_classificação.csv`
      );
      expect(service['message'].add).toHaveBeenCalledTimes(1);
      expect(service['message'].add).toHaveBeenCalledWith({
        severity: 'success',
        summary: 'Operação realizada com sucesso',
        detail: 'Download realizado com sucesso',
        key: 'main',
      });
    });

    it('Deve realizar o handling dos erros', () => {
      const mockError = { status: 500, error: { message: 'Erro Teste' } };
      spyOn(service, 'save').and.returnValue({});
      spyOn(service['message'], 'add').and.returnValue({});
      spyOn(service['api'], 'request').and.returnValue(throwError(mockError));
      service.downloadCurrentClassifications();
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service.save).toHaveBeenCalledTimes(0);
      expect(service['message'].add).toHaveBeenCalledTimes(1);
      expect(service['message'].add).toHaveBeenCalledWith({
        severity: 'error',
        summary: 'Erro ao fazer download',
        detail: 'O download não foi realizado',
        key: 'main',
      });
    });
  });

  describe('Get all active options', () => {
    it('Deve pesquisar por todas as classificações ativas', () => {
      let mock = MOCK_CUSTOM_PAGED(
        { name: 'teste', description: 'teste', active: true },
        10
      );
      spyOn(service['api'], 'request').and.returnValue(of(mock));
      service.getAllOptionsActive().subscribe(data => {
        expect(service['api'].request).toHaveBeenCalledTimes(1);
        expect(data.content).toHaveLength(10);
      });
     
    });

    it('Deve retornar conteudo vazio caso o servidor responda com 404', () => {
      let mock = throwError({status: 404, error: {message: 'not foud'}});
      spyOn(service['api'], 'request').and.returnValue(mock);
      service.getAllOptionsActive().subscribe(data => {
        expect(service['api'].request).toHaveBeenCalledTimes(1);
        expect(data.content).toHaveLength(0);
      });
    });
    it('Deve retornar qualquer erro que não seja 404', () => {
      let mock = throwError({status: 500, error: {message: 'not foud'}});
      spyOn(service['api'], 'request').and.returnValue(mock);
      service.getAllOptionsActive().subscribe(data => {
        
      }, err => {expect(err).toBeDefined()});
    });
  })
});
