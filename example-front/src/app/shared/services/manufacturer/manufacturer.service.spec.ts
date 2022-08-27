import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MessageService } from 'primeng/api';
import { of, throwError } from 'rxjs';
import { Path } from '../../consts/path';

import { ManufacturerService } from './manufacturer.service';

describe('ManufacturerService', () => {
  let service: ManufacturerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [MessageService]
    });
    service = TestBed.inject(ManufacturerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  describe('Manufacturer search', () => {

    it('Should make a get request to search manufacturers', () => {
      const response = {
        content: [
          { id: 1853, name: 'JOHNSON - OTC - SP' },
          { id: 5828, name: 'SANTA CRUZ - RP' },
          { id: 101745, name: 'JOHNSON NEUTROGENA SUNFRESH' },
          { id: 101780, name: 'ABBOTT NUTRICIONAL' },
          { id: 103159, name: 'ONTEX' },
        ]
      };
      const q = 'Pan';
      spyOn(service['_api'], 'request').and.returnValue(of(response));
      service.search(q, 0, 10);
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    })

    it('Should handle server errors when searching', () => {
      const response = { status: 500, error: { message: 'Internal Server Error' } };
      const q = 'pan';
      spyOn(service['_api'], 'request').and.returnValue(throwError(response));
      spyOn(service, 'handleSearchError').and.returnValue({});
      service.search(q, 0, 10).subscribe(d => { });
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
      expect(service.handleSearchError).toHaveBeenCalledTimes(1);
    })
  })


  describe('Validar codigos por ponto e virgula', () => {
    it('Deve pesquisar somente codigos unicos', () => {
      const mock = {valid: [{id: 123, name: 'Teste 123'}, {id: 431, name: 'Teste 431'}], invalid: ['aa3', 'dd2']};
      const query = [123, 431, 'aa3', 'dd2', 431,]
      spyOn(service['_api'], 'request').and.returnValue(of(mock));
      service.validateCodes(query.join(';')).subscribe(data => {
        expect(data.valid).toEqual(mock.valid)
      });
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    })

    it('Deve fazer handling dos erros do servidor', () => {
      const response = {status: 500, error: {message: 'teste'}}
      const query = [123, 431, 'aa3', 'dd2', 431,]
      spyOn(service['_api'], 'request').and.returnValue(throwError(response));
      spyOn(service['_message'], 'add').and.returnValue({});
      service.validateCodes(query.join(';')).subscribe(data => {
        
      });
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
      expect(service['_message'].add).toHaveBeenCalledTimes(1);
    })
  })

  describe('Handle Search Error', () => {
    it('Deve realiza hanlding para erros 404', () => {
      const mockError = { status: 404, error: { message: 'NotFound' } } as HttpErrorResponse;
      spyOn(service['_message'], 'add').and.returnValue({});
      service.handleSearchError(mockError);
    })

    it('Deve mostar uma mensagem para erros de servidor com detalhes', () => {
      const mockError = { status: 500, error: { message: 'InternalError', title: 'Não foi possível obter fabricantes' } } as HttpErrorResponse;
      spyOn(service['_message'], 'add').and.returnValue({})
      service.handleSearchError(mockError);
      expect(service['_message'].add).toHaveBeenCalledTimes(1);
      expect(service['_message'].add).toHaveBeenLastCalledWith({
        severity: 'error',
        summary: 'Não foi possível obter fabricantes',
        detail: 'InternalError'
      });
    })
    it('Deve mostar uma mensagem para erros de servidor sem detalhes', () => {
      const mockError = { status: 500, error: { message: 'InternalError' } } as HttpErrorResponse;
      spyOn(service['_message'], 'add').and.returnValue({})
      service.handleSearchError(mockError);
      expect(service['_message'].add).toHaveBeenCalledTimes(1);
      expect(service['_message'].add).toHaveBeenLastCalledWith({
        severity: 'error',
        summary: 'Ops',
        detail: 'InternalError'
      });

    })
  })

});
