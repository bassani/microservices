import { Component, ElementRef, forwardRef, Input, OnInit, Renderer2, ViewChild } from '@angular/core';
import { AbstractControl, ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR, ValidatorFn } from '@angular/forms';
import { PrimeNGConfig } from 'primeng/api';
import { map } from 'rxjs/operators';
import { IPaymentTerm } from '../../models/search-simulador';
import { PaymentTermService } from '../../services/payment-term/payment-term.service';

type TermOption = {
  newPaymentTermCode: number,
  newPaymentTermDescription: string,
  daysQuantityPayment: number
}

const RequiredFieldError = {paymentTerm: 'Campo requerido'};
const RequiredDCsFieldError = {paymentTerm: 'Preencha os prazos de todos os CDs'}

type PaymentTermByDistributionCenter = {
  type: PaymentTermTypes.dc;
  distributionCenters: {id: number, name: string, term: TermOption}[];
  generalTerm?: never;
}

type PaymentTermByOneValue = {
  type: PaymentTermTypes.general;
  generalTerm: TermOption | null;
  distributionCenters?: never;
}


/**
 * Validador utilizado nos reactive forms
 */
export const PaymentTermValidators = {
  requiredWhenNotNull: (dcs: any[]): ValidatorFn => {
    return (control: AbstractControl) => {
      let value = control.value;
      if(!value) return null;
      if(value.newPaymentTermType === PaymentTermTypes.general) return value.newPaymentTermGeneral ? null : RequiredFieldError
      if(value.newPaymentTermType === PaymentTermTypes.dc) return dcs.every(dc => (value.newPaymentTermDCs || []).find((dcSelected: any) => {
        return dcSelected.distributionCenter === dc.id
      })) ? null : RequiredDCsFieldError;
      return null
    }

  },
}

export enum PaymentTermTypes {
  general = 'TIPO_NOVO_PRAZO_GERAL',
  dc = 'TIPO_NOVO_PRAZO_POR_CD'
}

/**
 * Prazo de pagamento geral ou por cd
 */
export type  PaymentTermLike = PaymentTermByOneValue | PaymentTermByDistributionCenter;

/**
 * Componente de seleção de prazo geral ou por cd
 * O componente trabalha com normalização e desnormalização dos dados, para
 * poder representar corretamente a estrutura de tela proposta
 * Recomendação de melhoria:
 * Convertir em um modal como o campo de verbas ou desconto, para melhorar
 * a experiencia de usuario e evitar complicações
 */
@Component({
  selector: 'app-payment-term-cd',
  templateUrl: './payment-term-cd.component.html',
  styleUrls: ['./payment-term-cd.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => PaymentTermCdComponent),
      multi: true
    },
  ]
})
export class PaymentTermCdComponent implements OnInit, ControlValueAccessor {

  public onChangeFn = (_: any) => {};
  public paymentTypes = PaymentTermTypes;
  public onTouchedFn = () => {};
  type: PaymentTermLike = {type: PaymentTermTypes.general, generalTerm: null};
  @Input('dcs') dcs: any[] = [ 
    {
      id: 900,
      term: null,
    },
    {
      id: 1300,
      term: null,
    },
    {
      id: 1451,
      term: null,
    },
    {
      id: 908,
      term: null,
    },
  ];
  
  show: boolean = false;
  value: any = null;
  @ViewChild('contents') contents: ElementRef;
  @ViewChild('paymentInput') paymentInput: ElementRef;
  terms: any[] = [];

  constructor(
    private primengConfig: PrimeNGConfig,
    private renderer: Renderer2,
    private paymentTermService: PaymentTermService
  ) {
    
  }

  get termsOptions() {
    return this.paymentTermService.getPaymentTerm().pipe(map((result) => {
      return result.content.map(term => ({newPaymentTermCode: term.id, newPaymentTermDescription: term.conditionPaymentDescription, daysQuantityPayment: term.daysQuantityPayment}))
    }))
  }

  writeValue(obj: any | null): any {
    if(!obj || !obj.newPaymentTermType) return null;
    this.value = obj.newPaymentTermType === PaymentTermTypes.general ? this._readGeneral(obj) : this._readDC(obj);
  }

  registerOnChange(fn: any): void {
    this.onChangeFn = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedFn = fn;
  }

  get isInvalid() {
    let value = this.value;
    if(!value || !value.newPaymentTermType) return null;
    if(value.newPaymentTermType === PaymentTermTypes.general) return !!value.newPaymentTermGeneral ? null : RequiredFieldError;
    let hasAllItems = this.dcs.every(dc => {
      let resp = (value.newPaymentTermDCs||[]).filter((dcSelected: any) => {
        return dcSelected.distributionCenter === dc.id && !!dcSelected.newPaymentTermCode
      }).length > 0
      return resp
    });
    if(value.newPaymentTermType === PaymentTermTypes.dc) return hasAllItems ? null : RequiredDCsFieldError;

    return RequiredFieldError;
  }

  clear() {
    this.type = {generalTerm: null, type: PaymentTermTypes.general};
    this.changed()
  }

  public onTouched() {
    this.onTouchedFn();
  }

  public onChange() {
    this.onChangeFn(this.value);
  }

  ngOnInit() {
    this.primengConfig.ripple = true;
    this.renderer.listen('window', 'click', (e: any) => {
      let isOneOfChilds = e.path.includes(this.contents.nativeElement)
      if (!!this.contents && (!isOneOfChilds && this.contents.nativeElement != e.target && this.paymentInput.nativeElement != e.target)) {
        this.hideBox();
      }
    });

    this.paymentTermService.getPaymentTerm().pipe(map((result) => {
      return result.content.map(term => ({newPaymentTermCode: term.id, newPaymentTermDescription: term.conditionPaymentDescription, daysQuantityPayment: term.daysQuantityPayment}))
    })).subscribe(data => {
      this.terms = data;
    })
  } 

  get termInputValue() {
    if(!this.type.generalTerm && !this.type.distributionCenters) return null;
    if(this.type.type === PaymentTermTypes.general) return `Geral ${this.type.generalTerm?.newPaymentTermDescription}`;
    let completedDs = this.type.distributionCenters?.filter(ds => !!ds.term);
    if(this.type.type === PaymentTermTypes.dc) return `${completedDs?.length}/${this.type.distributionCenters?.length} Prazos selecionados`;
    return '';
  }

  showBox() {
    this.show = true;
  }
  hideBox() {
    this.show = false;
  }

  changeType(type: PaymentTermTypes){
    let base: any = {};
    base[type === PaymentTermTypes.general ? 'generalTerm' : 'distributionCenters'] = type === PaymentTermTypes.general ? null : this.dcs;
    this.type = {type: type, ...base};
    this.changed();
  }

  private _normalizeDC() {
    return {
      newPaymentTermType: this.type.type,
      newPaymentTermGeneral: null,
      newPaymentTermDCs: this.type.distributionCenters?.map(e => ({...e.term, distributionCenter: e.id, name: e.name}))
    }
  }
  
  private _normalizeGeneral() {
    return {
      newPaymentTermType: this.type.type,
      newPaymentTermGeneral: this.type.generalTerm,
      newPaymentTermDCs: null
    }
  }

  private _readDC(valueToParse: any) {
    return {
      newPaymentTermType: valueToParse.newPaymentTermType,
      distributionCenters: valueToParse.newPaymentTermDCs.map((e: any) => {
        let {distributionCenter, name, ...term} = e;
        return {id: distributionCenter, name: name, term: term}
      })
    }
  }

  private _readGeneral(valueToParse: any) {
    return {
      newPaymentTermType: valueToParse.newPaymentTermType,
      generalTerm: valueToParse.newPaymentTermGeneral
    }
  }

  changed() {
    let normalized = this.type.type === PaymentTermTypes.general ? this._normalizeGeneral() : this._normalizeDC();
    this.value = !normalized.newPaymentTermDCs && !normalized.newPaymentTermGeneral ? null : normalized;
    this.onChange();
  }
}
