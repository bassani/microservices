import { MOCK_BLOB_XLS } from './../../utils/mock';
import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { SalesService } from './sales.service';
import { MessageService } from 'primeng/api';

describe('SalesService', () => {
  let service: SalesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        {
          provide: MessageService, useValue: {
            add: () => {

            }
          }
        }
      ]
    });
    service = TestBed.inject(SalesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('Search Payment', () => {
    it('Should do a search request and handle success cases', () => {
      const response = [{ id: 1, name: 'Vendas para cálculo 1' }, { id: 2, name: 'Vendas para cálculo 2' }];
      spyOn(service['_api'], 'request').and.returnValue(of(response));
      service.getSalesCalculation().subscribe(data => {
        expect(data).toEqual(response)
      });
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    })

    it('Should do a search request and handle error cases', () => {
      const response = { status: 500, error: { message: 'Not Found' } };
      spyOn(service['_api'], 'request').and.returnValue(throwError(response));
      service.getSalesCalculation().subscribe(data => { }, error => {
        expect(error.status).toEqual(response.status);
      });
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    })
  })

  describe('Exportar relatório de vendas', () => {
    it('Deve realizar o download do arquivo', () => {
      const response = MOCK_BLOB_XLS;
      spyOn(service['_api'], 'request').and.returnValue(of(response));
      service.exportSalesReport().subscribe(data => {
        expect(data).toEqual(response)
      });
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    })
  })

  it('Deve informar os erros ao usuário', () => {
    spyOn(service['_messageService'], 'add').and.returnValue(true);
    const response = { status: 500, error: { message: 'Not Found' } };
    spyOn(service['_api'], 'request').and.returnValue(throwError(response));
    service.exportSalesReport().subscribe(data => { }, error => {
      expect(error.status).toEqual(response.status);
    });
    expect(service['_messageService'].add).toHaveBeenCalledTimes(1);
  })
});
