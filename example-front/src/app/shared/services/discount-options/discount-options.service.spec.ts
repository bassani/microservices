import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { DiscountOptionsService } from './discount-options.service';

describe('DiscountOptionsService', () => {
  let service: DiscountOptionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule]
    });
    service = TestBed.inject(DiscountOptionsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
    expect(service.endpoints.search).toEqual('/discountOptions/search')
  });

  describe('Search Discount Options', () => {
    it('Should do a search request and handle success cases', () => {
      const response = [{ id: 1, name: 'Todos os Produtos' }, { id: 2, name: 'Por Produto' }];
      spyOn(service['_api'], 'request').and.returnValue(of(response));
      service.getDiscountOptions().subscribe(data => {
        expect(data).toEqual(response)
      });
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    })

    it('Should do a search request and handle error cases', () => {
      const response = { status: 500, error: { message: 'Not Found' } };
      spyOn(service['_api'], 'request').and.returnValue(throwError(response));
      service.getDiscountOptions().subscribe(data => { }, error => {
        expect(error.status).toEqual(response.status);
      });
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    })
  })
});
