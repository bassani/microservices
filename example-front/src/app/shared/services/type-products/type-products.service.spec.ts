import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { Path } from '../../consts/path';
import { TypeProductsService } from './type-products.service';

describe('TypeProductsService', () => {
  let service: TypeProductsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule]
    });
    service = TestBed.inject(TypeProductsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
    expect(service.endpoints.search).toEqual('/typeProducts/search')
  });

  describe('Search Type Products', () => {
    it('Should do a search request and handle success cases', () => {
      const response = [{ id: 1, name: 'Somente inativos' }, { id: 2, name: 'Desconsiderar Promopacks' },
      { id: 3, name: 'Somente Promopacks' }, { id: 4, name: 'Desconsiderar Cotados' },
      { id: 5, name: 'Somente Cotados' }];
      spyOn(service['_api'], 'request').and.returnValue(of(response));
      service.getTypeProducts().subscribe(data => {
        expect(data).toEqual(response)
      });
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    })

    it('Should do a search request and handle error cases', () => {
      const response = { status: 500, error: { message: 'Not Found' } };
      spyOn(service['_api'], 'request').and.returnValue(throwError(response));
      service.getTypeProducts().subscribe(data => { }, error => {
        expect(error.status).toEqual(response.status);
      });
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    })
  })
});
