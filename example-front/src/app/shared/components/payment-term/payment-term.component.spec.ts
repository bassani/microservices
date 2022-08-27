import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { PaymentTermService } from '../../services/payment-term/payment-term.service';
import { PaymentTermComponent } from './payment-term.component';

describe('PaymentTermComponent', () => {
  let component: PaymentTermComponent;
  let fixture: ComponentFixture<PaymentTermComponent>;
  let paymentTermService: PaymentTermService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PaymentTermComponent],
      imports: [HttpClientModule],
      providers: [PaymentTermService],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();
    paymentTermService = TestBed.inject(PaymentTermService)
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentTermComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Procura por pagamentos', () => {
    it('Deve fazer a busca por pagamento', () => {
      const response = [{ id: 1, name: 'PAGAMENTO ANALGESICO' }, { id: 2, name: 'PAGAMENTO PROTETOR SOLAR' }];
      expect(component.paymentTerm$.value).not.toEqual(response)
      spyOn(paymentTermService, 'getPaymentTerm').and.returnValue(of(response));
      component.getPaymentTerm();
    })

    it('Deve mostar uma mensagem para erros de servidor sem detalhes', () => {
      const response = { status: 500, error: { message: 'Internal Unit Error' } };
      expect(component.paymentTerm$.value).not.toEqual(response)
      spyOn(paymentTermService, 'getPaymentTerm').and.returnValue(throwError(response));
    })
  })

});
