import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MessageService } from 'primeng/api';
import { of, throwError } from 'rxjs';
import { MOCK_CUSTOM_PAGED } from '../../utils/mock';

import { UsersService } from './users.service';

describe('UsersService', () => {
  let service: UsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        {provide: MessageService, useValue: {add: (...args: any[]) => {}}}
      ]
    });
    service = TestBed.inject(UsersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  describe('Listar usuarios', () => {
    it('Deve trazer uma lista de usuarios', () => {
      const mockList = {content: [{name: 'MOCK', code: 1}]}
      spyOn(service['api'], 'request').and.returnValue(of(mockList));
      service.findUsers().subscribe(data => {
        expect(data).toEqual(mockList)
      })
    })

    it('Deve retornar vazio e dar erro ao carregar uma lista de usuarios', () => {
      const error = {status: 401, error: {message: 'unauthorized'}};
      spyOn(service['api'], 'request').and.returnValue(throwError(error));
      service.findUsers().subscribe(data => {
        expect(data).toEqual([])
      })
    })
  })

  describe('Pegar cargos do usuario', () => {
    it('Deve trazer uma lista de cargos usuarios', () => {
      const mockList = {content: [{id: 0, name: 'Analista'}]}
      spyOn(service['api'], 'request').and.returnValue(of(mockList));
      service.getPositionUser('teste').subscribe(data => {
        expect(data).toEqual(mockList)
      })
    })

    it('Deve retornar vazio e dar erro ao carregar uma lista de cargos', () => {
      const error = {status: 401, error: {message: 'unauthorized'}};
      spyOn(service['api'], 'request').and.returnValue(throwError(error));
      service.getPositionUser('teste').subscribe(data => {
        expect(data).toEqual([])
      })
    })
  })

  describe('Criar Usuário', () => {
    it('Deve informar quando o usuário e cadastrada com sucesso', () => {
      let mock = { email: 'teste@exemplo.com' };
      let mockList = MOCK_CUSTOM_PAGED(
        { email: 'teste@exemplo.com' },
        10
      );
      spyOn(service['api'], 'request').and.returnValue(of(mock));
      spyOn(service, 'getAll').and.returnValue(of(mockList));
      service
        .createUser({ email: 'exemplo@teste.com' })
        .subscribe();
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service.getAll).toHaveBeenCalledTimes(1);
      expect(service.getAll).toHaveBeenCalledWith("",0, 10);
    });

    it('Deve informar quando a classificação e cadastrada com erro', () => {
      let mock = { status: 500, error: { message: 'Erro Teste' } };
      spyOn(service['api'], 'request').and.returnValue(throwError(mock));
      spyOn(service, 'getAll').and.returnValue(of({}));
      service
        .createUser({ email: 'exemplo@teste.com' })
        .subscribe();
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service.getAll).toHaveBeenCalledTimes(0);
    });
  });

  describe('Editar usuário', () => {
    it('Deve informar quando o usuário e alterada com sucesso', () => {
      let mock = { email: 'teste@exemplo.com', keycloakUserId: '12345' };
      let mockList = MOCK_CUSTOM_PAGED(
        { email: 'teste@exemplo.com', keycloakUserId: '12345' },
        10
      );
      spyOn(service['api'], 'request').and.returnValue(of(mock));
      spyOn(service, 'getAll').and.returnValue(of(mockList));
      service
        .updateUser({  email: 'teste@exemplo.com', keycloakUserId: '12345' })
        .subscribe();
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service.getAll).toHaveBeenCalledTimes(1);
      expect(service.getAll).toHaveBeenCalledWith("",0, 10);
    });

    it('Deve informar quando o usuário é alterada com erro', () => {
      let mock = { status: 500, error: { message: 'Erro Teste' } };
      spyOn(service['api'], 'request').and.returnValue(throwError(mock));
      spyOn(service, 'getAll').and.returnValue(of({}));
      service
        .updateUser({  email: 'teste@exemplo.com', keycloakUserId: '12345' })
        .subscribe();
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service.getAll).toHaveBeenCalledTimes(0);
    });
  });
});
