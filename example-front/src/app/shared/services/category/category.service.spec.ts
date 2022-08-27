import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MessageService } from 'primeng/api';
import { of, throwError } from 'rxjs';
import {
  IProductCategory,
  ISearchResponse,
} from '../../models/search-simulador';
import { ApiService } from '../api/api.service';
import { SEARCH_ITEM_LIST } from '../../utils/mock';

import { CategoryService } from './category.service';

describe('CategoryService', () => {
  let service: CategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        ApiService,
        { provide: MessageService, useValue: { add: (...args: any[]) => {} } },
      ],
    });
    service = TestBed.inject(CategoryService);
  });

  it('Deve criar o serviço', () => {
    expect(service).toBeTruthy();
  });

  describe('Pesquisa de categoria', () => {
    it('Deve retornar a resposta do servidor', () => {
      const content = [...Array(10)].map<IProductCategory>((e, i) => ({
        id: i,
        name: `teste ${i}`,
      }));
      const mock: ISearchResponse<IProductCategory> = {
        content: content,
        page: 0,
        size: 25,
        numberOfElements: content.length,
      };
      spyOn(service['api'], 'request').and.returnValue(of(mock));
      service.getAllCategories().subscribe(
        (data) => expect(data).toEqual(mock),
        (err) => expect(err).not.toBeDefined()
      );
      expect(service['api'].request).toHaveBeenCalledTimes(1);
    });

    it('Deve realizar handling dos erros de servidor', () => {
      const mock = throwError({
        status: 500,
        error: { message: 'Internal server error' },
      });
      const error = {
        severity: 'error',
        summary: 'Não foi possivel pesquisar categorias',
        detail: 'Internal server error',
      };
      spyOn(service['api'], 'request').and.returnValue(mock);
      spyOn(service['message'], 'add').and.returnValue(true);
      service.getAllCategories().subscribe(
        (data) => expect(data).toEqual(null),
        (err) => expect(err.status).toEqual(500)
      );
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service['message'].add).toHaveBeenCalledTimes(1);
      expect(service['message'].add).toHaveBeenLastCalledWith(error);
    });
  });

  describe('Pesquisa de subcategoria', () => {
    it('Deve retornar a resposta do servidor', () => {
      const content = [...Array(10)].map<IProductCategory>((e, i) => ({
        id: i,
        name: `teste ${i}`,
      }));
      const mock: ISearchResponse<IProductCategory> = {
        content: content,
        page: 0,
        size: 25,
        numberOfElements: content.length,
      };
      spyOn(service['api'], 'request').and.returnValue(of(mock));
      service.getAllSubcategories(content).subscribe(
        (data) => expect(data).toEqual(mock),
        (err) => expect(err).not.toBeDefined()
      );
      expect(service['api'].request).toHaveBeenCalledTimes(1);
    });

    it('Deve realizar handling dos erros de servidor', () => {
      const error = {
        severity: 'error',
        summary: 'Não foi possivel pesquisar subcategorias',
        detail: 'Internal server error',
      };
      const mock = throwError({
        status: 500,
        error: { message: 'Internal server error' },
      });
      spyOn(service['api'], 'request').and.returnValue(mock);
      spyOn(service['message'], 'add').and.returnValue(true);

      service.getAllSubcategories([{ id: 0, name: 'teste' }]).subscribe(
        (data) => expect(data).toEqual(null),
        (err) => expect(err.status).toEqual(500)
      );
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      expect(service['message'].add).toHaveBeenCalledTimes(1);
      expect(service['message'].add).toHaveBeenLastCalledWith(error);
    });
  });
});
