import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MessageService } from 'primeng/api';
import { of, throwError } from 'rxjs';
import { Path } from '../../consts/path';
import { handleErrorDetail } from '../../utils/utils';
import { ApiService } from '../api/api.service';
import { ProductService } from './product.service';

describe('ProductService', () => {
  let service: ProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        {provide: ApiService, useValue: {request: () => {}}},
        {provide: MessageService, useValue: {add: () => {}}}
      ]
    });
    service = TestBed.inject(ProductService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
    expect(service.endpoints.search).toEqual('/supplier-products');
  });

  describe('Handle errors', () => {
    it('Deve mostar a mensagem recebida com os dados recebidos', () => {
      const err = {status: 400, error: {message: 'UnitTest', title: 'TitleUnitTest'}} as HttpErrorResponse;
      spyOn(service['_message'], 'add').and.returnValue({});
      service.handleSearchError(err);
      expect(service['_message'].add).toHaveBeenCalledTimes(1);
      expect(service['_message'].add).toHaveBeenCalledWith({
        severity: 'error',
        summary: err.error.title,
        detail: err.error.message
      });
    })

    it('Deve mostar um erro generico caso não tenha mensagem', () => {
      let err = {status: 400, error: null} as HttpErrorResponse;
      spyOn(service['_message'], 'add').and.returnValue({});
      service.handleSearchError(err);
      expect(service['_message'].add).toHaveBeenCalledTimes(1);
      expect(service['_message'].add).toHaveBeenCalledWith({
        severity: 'error',
        summary: 'Ops',
        detail: 'Não foi possível comunicar com o servidor. Tente novamente mais tarde'
      });
    })
  })

  
  describe('Search Products', () => {
    it('Should do a search request and handle success cases', () => {
      const response = [
        { id: 1, name: 'Dorflex com 50 comprimidos' },
        { id: 2, name: 'Buscopan Composto com 20 Comprimidos' },
        { id: 3, name: 'Targifor C com 16 comprimidos' },
        { id: 4, name: 'AAS Infantil 100mg com 30 Comprimidos' },
        { id: 5, name: 'Acertil 10MG com 60 Comprimidos' },
        { id: 6, name: 'Drenison 0,125mg/G' },
      ];
      spyOn(service['_api'], 'request').and.returnValue(of(response));
      service.getProducts({query: '', supplier: []}).subscribe((data) => {
        expect(data).toEqual(response);
      });
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    });

    it('Should do a search request and handle error cases', () => {
      const response = { status: 500, error: { message: 'Not Found' } };
      spyOn(service['_api'], 'request').and.returnValue(throwError(response));
      spyOn(service, 'handleSearchError');
      service.getProducts({query: 'q', supplier: []}).subscribe(
        (data) => { },
        (error) => {
          expect(error.status).toEqual(response.status);
          expect(service.handleSearchError).toHaveBeenCalledTimes(1)
        }
      );
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    });
  });
});
