import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { Path } from '../../consts/path';
import { SupplierService } from './supplier.service';

describe('SupplierService', () => {
  let service: SupplierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    service = TestBed.inject(SupplierService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
    expect(service.endpoints.search).toEqual('/manufacturers/parents');
  });

  describe('Search Suppliers', () => {
    it('Should do a search request and handle success cases', () => {
      const response = [
        { id: 1, name: 'FABRICANTE PAI 1' },
        { id: 2, name: 'FABRICANTE PAI 2' },
        { id: 3, name: 'FABRICANTE PAI 3' },
        { id: 4, name: 'FABRICANTE PAI 4' },
      ];
      spyOn(service['_http'], 'get').and.returnValue(of(response));
      service.search('FABRICANTE').subscribe((data) => {
        expect(data).toEqual(response);
      });
      expect(service['_http'].get).toHaveBeenCalledTimes(1);
      expect(service['_http'].get).toHaveBeenCalledWith(
        `${Path.HTTP_API_BASE}${service.endpoints.search}`,
        { params: { page: '0', size: '50', query: 'FABRICANTE' } }
      );
    });

    it('Should do a search request and handle error cases', () => {
      const response = { status: 500, error: { message: 'Not Found' } };
      spyOn(service['_http'], 'get').and.returnValue(throwError(response));
      service.search('').subscribe(
        (data) => { },
        (error) => {
          expect(error.status).toEqual(response.status);
        }
      );
      expect(service['_http'].get).toHaveBeenCalledTimes(1);
      expect(service['_http'].get).toHaveBeenCalledWith(
        `${Path.HTTP_API_BASE}${service.endpoints.search}`,
        { params: { page: '0', size: '50', query: '' } }
      );
    });
  });

});
