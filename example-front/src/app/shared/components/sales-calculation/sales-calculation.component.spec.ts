import { SalesService } from '../../services/sales/sales.service';
import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { SalesCalculationComponent } from './sales-calculation.component';
import { MessageService } from 'primeng/api';
import { BASIC } from '../../utils/mock';
import { FieldUtilsService } from '../../utils/field-utils.service';

describe('SalesCalculationComponent', () => {
  let component: SalesCalculationComponent;
  let fixture: ComponentFixture<SalesCalculationComponent>;
  let salesCalculationService: SalesService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SalesCalculationComponent],
      imports: [HttpClientModule],
      providers: [SalesService,
        FieldUtilsService, 
      {provide: MessageService, useValue: {add: () => {}}}],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
    salesCalculationService = TestBed.inject(SalesService);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesCalculationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Procura por Vendas para Cálculo', () => {
    it('Deve fazer a busca por pagamento', () => {
      const response = [{ id: 1, name: 'PAGAMENTO ANALGESICO' }];
      expect(component.salesCalculation$.value).not.toEqual(response);
      spyOn(salesCalculationService, 'getSalesCalculation').and.returnValue(
        of(response)
      );
      component.getSalesCalculation();
    });

    it('Deve mostar uma mensagem para erros de servidor sem detalhes', () => {
      const response = {
        status: 500,
        error: { message: 'Internal Unit Error' },
      };
      expect(component.salesCalculation$.value).not.toEqual(response);
      spyOn(salesCalculationService, 'getSalesCalculation').and.returnValue(
        throwError(response)
      );
    });
  });

  describe('Texto de selecionado', () => {
    it('Deve mostar "Selecione uma base" caso nenhum valor seja informado', () => {
      expect(component.optionText(undefined)).toEqual('Selecione uma base');
    });

    it('Deve retonar o valor concatenado do parent e do child', () => {
      component.salesCalculation$.next(BASIC.salesCalculation.content)
      const currentValue = component.salesCalculation$.value[0].options;
      expect(component.optionText(currentValue ? currentValue[0] : [])).toEqual(
        `${component.salesCalculation$.value[0].name} ${currentValue? currentValue[0].name : ''}`
      );
    });

    it('Deve retonar o valor somente do child caso não exista parent', () => {
      const mock = { name: 'Teste sem parent' };
      expect(component.optionText(mock)).toEqual(mock.name);
    });
  });
});
