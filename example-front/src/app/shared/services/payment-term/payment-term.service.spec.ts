import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { PaymentTermService } from './payment-term.service';

describe('PaymentTermService', () => {
  let service: PaymentTermService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    service = TestBed.inject(PaymentTermService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
    expect(service.endpoints.search).toEqual('/paymentTerms');
  });

  describe('Search Payment', () => {
    it('Should do a search request and handle success cases', () => {
      const response = [
        { id: 1, name: 'PAGAMENTO ANALGESICO' },
        { id: 2, name: 'PAGAMENTO PROTETOR SOLAR' },
      ];
      spyOn(service['_api'], 'request').and.returnValue(of(response));
      service.getPaymentTerm().subscribe((data) => {
        expect(data).toEqual(response);
      });
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    });

    it('Should do a search request and handle error cases', () => {
      const response = { status: 500, error: { message: 'Not Found' } };
      spyOn(service['_api'], 'request').and.returnValue(throwError(response));
      service.getPaymentTerm().subscribe(
        (data) => {},
        (error) => {
          expect(error.status).toEqual(response.status);
        }
      );
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    });
  });
});
