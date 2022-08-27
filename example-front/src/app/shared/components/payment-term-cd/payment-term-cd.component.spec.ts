import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { RadioButtonModule } from 'primeng/radiobutton';
import { of } from 'rxjs';
import { PaymentTermService } from '../../services/payment-term/payment-term.service';
import { PAYMENT_TERM_MOCK } from '../../utils/mock';

import { PaymentTermCdComponent, PaymentTermLike, PaymentTermTypes, PaymentTermValidators } from './payment-term-cd.component';


describe('Payment Validators', () => {
  it('Deve retornar null quando o control for nulo', () => {
    expect(PaymentTermValidators.requiredWhenNotNull([])(new FormControl(null))).toBeNull();
  })
  it('Deve validar as regras do valor geral quando o control tiver o type general', () => {
    let controlValueIncorrect = {newPaymentTermType: PaymentTermTypes.general, newPaymentTermGeneral : null}
    let controlValueCorrect = {newPaymentTermType: PaymentTermTypes.general, newPaymentTermGeneral : {id: 1}}
    // se o newPaymentTermGeneral for nulo ou invalido e o type for general, retornar erro de requerido
    expect(PaymentTermValidators.requiredWhenNotNull([])(new FormControl(controlValueIncorrect)))
    .toEqual({paymentTerm: 'Campo requerido'});

    // se o newPaymentTermGeneral não for nulo e o type for general retornar null (sem erros);
    expect(PaymentTermValidators.requiredWhenNotNull([])(new FormControl(controlValueCorrect)))
    .toBeNull();
  })

  it('Deve validar as regras do valor cd quando o control tiver o type dc', () => {
    let dcs = [{id: 1, name: 'Teste'}, {id: 2, name: 'MOCK'}]
    let controlValueIncorrectNoSelected = {newPaymentTermType: PaymentTermTypes.dc, newPaymentTermDCs : null}
    let controlValueIncorrectNotAllSelected = {newPaymentTermType: PaymentTermTypes.dc, newPaymentTermDCs : [{distributionCenter: 1, term: {id: 1}}]}
    let controlValueCorrect = {newPaymentTermType: PaymentTermTypes.dc, newPaymentTermDCs : [{distributionCenter: 1, term: {id: 1}}, {distributionCenter: 2, term: {id: 1}}]}

    // se o controlValueIncorrectNoSelected for nulo e o type for dc, retornar erro informando para preencher todos os cds
    expect(PaymentTermValidators.requiredWhenNotNull(dcs)(new FormControl(controlValueIncorrectNoSelected)))
    .toEqual({paymentTerm: 'Preencha os prazos de todos os CDs'});

    // se o controlValueIncorrectNoSelected não tiver todos os cds selecionados e o type for dc, 
    // retornar erro itens a ser selecionados
    expect(PaymentTermValidators.requiredWhenNotNull(dcs)(new FormControl(controlValueIncorrectNotAllSelected)))
    .toEqual({paymentTerm: 'Preencha os prazos de todos os CDs'});

    // se o newPaymentTermGeneral não for nulo e o type for general retornar null (sem erros);
    expect(PaymentTermValidators.requiredWhenNotNull(dcs)(new FormControl(controlValueCorrect)))
    .toBeNull();
  })

  it('Não deve validar caso nenhum dos tipos seja informado', () => {
    expect(PaymentTermValidators.requiredWhenNotNull([])(new FormControl({type: 'Other'}))).toBeNull();
  })
})

describe('PaymentTermCdComponent', () => {
  let component: PaymentTermCdComponent;
  let fixture: ComponentFixture<PaymentTermCdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaymentTermCdComponent ],
      imports: [DropdownModule, InputTextModule, FormsModule, ReactiveFormsModule, RadioButtonModule, ButtonModule ],
      providers: [{
        provide: PaymentTermService, useValue: {getPaymentTerm: () => of({content: PAYMENT_TERM_MOCK})}
      }]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentTermCdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(component.show).toBeFalsy();
    //mostrar box
    component.showBox();
    expect(component.show).toBeTruthy();
    // ocultar box
    component.hideBox();
    expect(component.show).toBeFalsy();

  });

  describe('Get term Options', () => {
    it('Deve retornar os payment term do serviço', () => {
      spyOn(component['paymentTermService'], 'getPaymentTerm').and.returnValue(of({content: [{
        id: 1, conditionPaymentDescription: 'Mock', daysQuantityPayment: 1
      }]}));
      let subs = component.termsOptions;
      subs.subscribe(data => {
        expect(component['paymentTermService'].getPaymentTerm).toHaveBeenCalledTimes(1);
      })
    })
  })


  describe('Proxy functions', () => {
    it('Deve chamar a função representada em cada proxy', () => {
      let myMockChange = jest.fn()
      let myMockTouch = jest.fn()
      component.onChange();
      component.onTouched();

      component.registerOnChange(myMockChange)
      component.registerOnTouched(myMockTouch)

      component.onChange();
      expect(myMockChange).toHaveBeenCalledTimes(1);

      component.onTouched();
      expect(myMockTouch).toHaveBeenCalledTimes(1);
    })
  }) 


  describe('Value writting and normalization', () => {
    it('Deve alterar o valor "value" somente quando for informado um tipo e valor', () => {
      component['_readGeneral'] = jest.fn();
      component['_readDC'] = jest.fn();
      expect(component.writeValue(null)).toBeNull();
      expect(component.writeValue({})).toBeNull();

      // Não deve ter sido chamado o read
      expect(component['_readDC']).toHaveBeenCalledTimes(0);
      expect(component['_readGeneral']).toHaveBeenCalledTimes(0);

      // se o type general for recebido deve ser chamado o read
      component.writeValue({newPaymentTermType : PaymentTermTypes.general});
      expect(component['_readGeneral']).toHaveBeenCalledTimes(1);

      // se o type dc for recebido deve ser chamado o read
      component.writeValue({newPaymentTermType : PaymentTermTypes.dc});
      expect(component['_readDC']).toHaveBeenCalledTimes(1);

    })

    it('Deve normalizar os dados de dc', () => {
      let toNormalize = {type: PaymentTermTypes.dc, distributionCenters: [{id: 1, name: 'teste', term: {newPaymentTermCode: 1, newPaymentTermDescription: '30'}}]} as PaymentTermLike;
      let normalized = {newPaymentTermType: PaymentTermTypes.dc,
        newPaymentTermGeneral: null,
        newPaymentTermDCs: [{distributionCenter: 1, name: 'teste', newPaymentTermCode: 1, newPaymentTermDescription: '30'}]
     };
     component.type = toNormalize;
     expect(component['_normalizeDC']()).toEqual(normalized);

     component.type.distributionCenters = undefined;
     component.type = toNormalize;
     expect(component['_normalizeDC']()).toEqual({...normalized, newPaymentTermDCs: undefined});

    })

    it('Deve normalizar os dados de tipo geral', () => {
      let toNormalize = {type: PaymentTermTypes.general, generalTerm: {newPaymentTermCode: 1, newPaymentTermDescription: '30'}} as PaymentTermLike;
      let normalized = {
        newPaymentTermType: PaymentTermTypes.general,
        newPaymentTermDCs: null,
        newPaymentTermGeneral: {
          newPaymentTermCode: 1,
          newPaymentTermDescription: '30'
      }
     };
     component.type = toNormalize;
     expect(component['_normalizeGeneral']()).toEqual(normalized);

    })

    it('Deve persistir as alterações de forma normalizada', () => {
      const mockNormalizedDC = {newPaymentTermType: PaymentTermTypes.dc, newPaymentTermDCs: []};
      const mockNormalizedGeneral = {newPaymentTermType: PaymentTermTypes.general, newPaymentTermGeneral: {id: 10}};
      component['_normalizeDC'] = jest.fn().mockReturnValue(mockNormalizedDC);
      component['_normalizeGeneral'] = jest.fn().mockReturnValue(mockNormalizedGeneral);
    
      // mudar para general, deve normalizar utilizando o geral
      component.type = {type: PaymentTermTypes.general, generalTerm: null};
      component.changed();
      expect(component['_normalizeGeneral']).toHaveBeenCalledTimes(1);
      expect(component.value).toEqual(mockNormalizedGeneral);

      // mudar para dc, deve normalizar utilizando o dc normalize
      component.type = {type: PaymentTermTypes.dc, distributionCenters: []};
      component.changed();
      expect(component['_normalizeDC']).toHaveBeenCalledTimes(1);
      expect(component.value).toEqual(mockNormalizedDC);

      // mudar para null o valor caso não for informado nenhum dado de geral ou dc
      component.type = {} as PaymentTermLike;
      // novo mock simulando dados vazios
      component['_normalizeDC'] = jest.fn().mockReturnValue({});
      component.changed();
      expect(component['_normalizeDC']).toHaveBeenCalledTimes(1);
      expect(component.value).toEqual(null);
    })


    it('Deve resetar o campo e propagar a alteração', () => {
      spyOn(component, 'changed');
      component.type = {type: PaymentTermTypes.dc, distributionCenters: []};
      component.clear();
      // ao limpar deve voltar ao state inicial
      expect(component.type).toEqual({type: PaymentTermTypes.general, generalTerm: null});
      expect(component.changed).toHaveBeenCalledTimes(1)
    })

    it('Deve ler dados e desnormalizalos', () => {
      expect(component['_readDC']({newPaymentTermType: PaymentTermTypes.dc,
      newPaymentTermDCs: [{distributionCenter: 1,name: 'MOCK', newPaymentTermCode: 1}]}))
      .toEqual({newPaymentTermType: PaymentTermTypes.dc, distributionCenters: [{id: 1, name: 'MOCK', term: {newPaymentTermCode: 1}}]})
    
      expect(component['_readGeneral']({newPaymentTermType: PaymentTermTypes.general,
        newPaymentTermGeneral: {}}))
        .toEqual({newPaymentTermType: PaymentTermTypes.general, generalTerm: {}})  
    
    })

    it('Deve mudar entre o tipo general e dc', () => {
      spyOn(component, 'changed');
      component.type = {type: PaymentTermTypes.general, generalTerm: null};
      component.changeType(PaymentTermTypes.dc);
      // deve mudar o tipo e a variavel descritiva do tipo para dc
      expect(component.type).toEqual({type: PaymentTermTypes.dc, distributionCenters: component.dcs});
      expect(component.changed).toHaveBeenCalledTimes(1)

      component.changeType(PaymentTermTypes.general);
      // deve mudar o tipo e a variavel descritiva do tipo para general
      expect(component.type).toEqual({type: PaymentTermTypes.general, generalTerm: null});
      expect(component.changed).toHaveBeenCalledTimes(2)      
    })
  })


  describe('Validation', () => {

    it('Deve retornar a quantidade de CDs selecionados', () => {
      // deve ser nulo quando o tipo e geral
      component.type = {type: PaymentTermTypes.general, generalTerm: null};
      expect(component.termInputValue).toBeNull();
      component.type = {type: PaymentTermTypes.general, generalTerm: {newPaymentTermDescription: '30', newPaymentTermCode: 1, daysQuantityPayment: 1}};
      expect(component.termInputValue).toEqual('Geral 30');

      // deve retornar 0/2
      component.dcs = [{id: 1, name: 'MOCK'}, {id: 2, name: 'MOCK'}]
      component.type = {type: PaymentTermTypes.dc, distributionCenters: [
        {id: 1, name: 'MOCK', term: null as any},
        {id: 2, name: 'MOCK', term: null as any}
      ]};
      expect(component.termInputValue).toEqual('0/2 Prazos selecionados');

      // selecionando 1 prazos
      (component.type.distributionCenters as unknown as any[])[0].term = {newPaymentTermCode: 1, newPaymentTermDescription: '30', daysQuantityPayment: 30};
      expect(component.termInputValue).toEqual('1/2 Prazos selecionados');

      // selecionando o segundo prazo
      (component.type.distributionCenters as unknown as any[])[1].term = {newPaymentTermCode: 1, newPaymentTermDescription: '30', daysQuantityPayment: 30};
      expect(component.termInputValue).toEqual('2/2 Prazos selecionados');

    })


    it('Deve retornar null se não a um valor selecionado', () => {
      component.value = null;
      expect(component.isInvalid).toEqual(null);
      component.value = {id: 1, newPaymentTermType: null};
      expect(component.isInvalid).toEqual(null);
    })

    it('Deve validar para os tipo DC e Geral', () => {
      // caso tiver type general e não tiver um valor de general
      component.value = {newPaymentTermType: PaymentTermTypes.general, newPaymentTermGeneral: null};
      expect(component.isInvalid).toEqual({paymentTerm: 'Campo requerido'});
      // caso tiver type general e ter um valor no general
      component.value = {newPaymentTermType: PaymentTermTypes.general, newPaymentTermGeneral: {id: 1}};
      expect(component.isInvalid).toBeNull()

      // caso tiver type dc e não tiver um valor de dc
      component.value = {newPaymentTermType: PaymentTermTypes.dc, newPaymentTermDCs: null};
      expect(component.isInvalid).toEqual({paymentTerm: 'Preencha os prazos de todos os CDs'});


      // caso tiver type dc e tiver todos os dc preenchidos
      component.dcs = [{id: 1, name: 'Teste'}, {id: 2, name: 'MOCK'}];
      let controlValueCorrect = {newPaymentTermType: PaymentTermTypes.dc, newPaymentTermDCs: [{distributionCenter: 1, newPaymentTermCode: 1}, {distributionCenter: 2, newPaymentTermCode: 1}]}
      component.value = controlValueCorrect;
      expect(component.isInvalid).toBeNull();

      // caso tiver type dc e tiver todos os dc preenchidos
      component.dcs = [{id: 1, name: 'Teste'}, {id: 2, name: 'MOCK'}];
      let controlValueIncorrectNotAllSelected = {newPaymentTermType: PaymentTermTypes.dc, newPaymentTermDCs : [{distributionCenter: 1, term: {id: 1, newPaymentTermCode: 1}}]}
      component.value = controlValueIncorrectNotAllSelected;
      expect(component.isInvalid).toEqual({paymentTerm: 'Preencha os prazos de todos os CDs'});

      //se for informado qualquer outro tipo não reconhecido retornar o erro
      component.value = {newPaymentTermType: 'OTHER'};
      expect(component.isInvalid).toEqual({paymentTerm: 'Campo requerido'});

    })
  })


});
