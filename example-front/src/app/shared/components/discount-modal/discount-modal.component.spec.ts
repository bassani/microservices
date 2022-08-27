import { HttpClientModule } from '@angular/common/http';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MessageService, SharedModule } from 'primeng/api';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiscountModalComponent, DISCOUNT_TYPE } from './discount-modal.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { NgVarDirective } from '../../directives/ng-var.directive';
const ALL_PRODUCTS_DISCOUNT = {
  discountType: DISCOUNT_TYPE.ADD,
  discount: 10,
  skus: [],
  productsCovered: 'all'
}
const PER_PRODUCT_DISCOUNT = {
  discountType: DISCOUNT_TYPE.PER_PRODUCT,
  discount: 10,
  skus: [],
  productsCovered: 'product'
}

const PER_PRODUCT_VALUES = [{skuId: 10, name: 'teste replace', discount: 10, discountType: DISCOUNT_TYPE.REPLACE},{skuId: 11, name: 'teste add', discount: 11, discountType: DISCOUNT_TYPE.ADD}];
describe('DiscountModalComponent', () => {
  let component: DiscountModalComponent;
  let fixture: ComponentFixture<DiscountModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        SharedModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        RadioButtonModule,
        InputNumberModule
      ],
      declarations: [DiscountModalComponent, NgVarDirective],
      providers: [MessageService],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DiscountModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Cadastro e envio de dados', () => {
    it('Cadastro e envio de dados', () => {
      const response = [
        { id: 1, name: 'Todos os Produtos' },
        { id: 2, name: 'Por Produto' },
      ];
      expect(component.etherealForm.value).not.toEqual(response);
      component.ngOnInit();
    });

    it('Deve mostar uma mensagem para erros de servidor sem detalhes', () => {
      const response = {
        status: 500,
        error: { message: 'Internal Unit Error' },
      };
      expect(component.discountForm.value).not.toEqual(response);
    });
  });


  describe('Tipos de desconto', () => {
    it('Deve trocar de abas e trocar de tipo de desconto', () => {
      expect(component.discountForm.getRawValue().productsCovered).toEqual('all');
      component.changeTab({index: 1});
      expect(component.discountForm.getRawValue().productsCovered).toEqual('product');
      component.changeTab({index: 0});
      expect(component.discountForm.getRawValue().productsCovered).toEqual('all');
    })
  })


  describe('Salvar desconto', () => {
    it('Deve salvar desconto para todos os produtos', () => {
      component.discountForm.setValue(ALL_PRODUCTS_DISCOUNT);
      spyOn(component, 'closeModal').and.returnValue({});
      component.save();
      expect(component.control.getRawValue()).toEqual(ALL_PRODUCTS_DISCOUNT)
      expect(component.closeModal).toHaveBeenCalledTimes(1);
   
    })
    it('Deve salvar desconto por produto', () => {
      component.discountForm.setValue(PER_PRODUCT_DISCOUNT)
      spyOn(component, 'closeModal').and.returnValue({});
      component.perProducts = PER_PRODUCT_VALUES;
      component.save();
      expect(component.control.getRawValue()).toEqual({...PER_PRODUCT_DISCOUNT, discountType: DISCOUNT_TYPE.PER_PRODUCT, skus: PER_PRODUCT_VALUES})
      expect(component.closeModal).toHaveBeenCalledTimes(1);
    })
  })

  describe('Fechar modal sem salvar', () => {
   it('Deve fechar o modal sem sobreescrever o valor do form', () => {
    component.discountForm.setValue(ALL_PRODUCTS_DISCOUNT);
    component.closeModal();
    expect(component.control.getRawValue()).not.toEqual(ALL_PRODUCTS_DISCOUNT)
   })
  })

  describe('Abrir modal', () => {
    it('Deve criar o form do state do modal', () => {
      const baseDiscountForm = {skus: [], discount: null, productsCovered: 'all', discountType: DISCOUNT_TYPE.ADD};
      expect(component.displayModal).toEqual(false);
      expect(component.discountForm.getRawValue()).toEqual(baseDiscountForm);
      component.control.setValue({...PER_PRODUCT_DISCOUNT, skus: PER_PRODUCT_VALUES, discount: 20})
      component.showModal();
      expect(component.discountForm.getRawValue()).toEqual({...PER_PRODUCT_DISCOUNT, skus: PER_PRODUCT_VALUES, discountType: DISCOUNT_TYPE.PER_PRODUCT,  discount: 20});
      expect(component.perProducts).toEqual(PER_PRODUCT_VALUES);
      expect(component.displayModal).toEqual(true)
    })

    it('Deve criar o form do state do modal vazio caso o control esteja vazio', () => {
      const baseDiscountForm = {skus: [], discount: null, productsCovered: 'all', discountType: DISCOUNT_TYPE.ADD};
      expect(component.displayModal).toEqual(false);
      expect(component.discountForm.getRawValue()).toEqual(baseDiscountForm);
      component.control.setValue({...PER_PRODUCT_DISCOUNT, skus: [], discount: null, productsCovered: null})
      component.showModal();
      expect(component.discountForm.getRawValue()).toEqual({...ALL_PRODUCTS_DISCOUNT, discountType: DISCOUNT_TYPE.ADD,  discount: null});
      expect(component.perProducts).toEqual([]);
      expect(component.displayModal).toEqual(true)
    })
  })


  describe('Product add and remove', () => {
    it('Deve adicionar um produto somente uma vez na lista de produtos ', () => {
      const product = {id: 1, name: 'teste prd'};
      const productForm = {product: product, discount: 10};
      const productResult = {skuId: 1, name: 'teste prd', discountType: DISCOUNT_TYPE.PER_PRODUCT, discount: 10};
      component.discountForm.patchValue({discountType: DISCOUNT_TYPE.PER_PRODUCT})
      component.etherealForm.setValue(productForm);
      expect(component.etherealForm.getRawValue()).toEqual(productForm)
      component.addProductDisctount();
      expect(component.perProducts).toHaveLength(1);
      expect(component.perProducts[0]).toEqual(productResult);
      expect(component.etherealForm.getRawValue()).not.toEqual(productResult)

      component.etherealForm.setValue(productForm);
      expect(component.etherealForm.getRawValue()).toEqual(productForm)
      component.addProductDisctount();
      expect(component.perProducts).toHaveLength(1);
      expect(component.perProducts[0]).toEqual(productResult);
      expect(component.etherealForm.getRawValue()).not.toEqual(productResult)

    })


    it('Deve remover um produto da lista de produtos', () => {
      const product = {skuId: 1, name: 'teste prd', discount: 10, discountType: DISCOUNT_TYPE.PER_PRODUCT};
      const productNonDelete = {skuId: 12, name: 'non delete prd', discount: 15, discountType: DISCOUNT_TYPE.ADD};
      component.perProducts = [product, productNonDelete];
      expect(component.perProducts).toHaveLength(2);
      expect(component.perProducts[0]).toEqual(product);
      fixture.detectChanges();
      fixture.debugElement.nativeElement.querySelector('#product-row-1>#action-cell>#remove-button').click();
      fixture.detectChanges();
      expect(component.perProducts).toHaveLength(1);
      expect(component.perProducts[0]).not.toEqual(product);
    })

    it('Deve tratar valores negativos ou nulos na adição dos produtos', () => {
      const product = {id: 1, name: 'teste prd'};
      const productForm = {product: product, discount: null};
      const productResult = {skuId: 1, name: 'teste prd', discountType: DISCOUNT_TYPE.PER_PRODUCT, discount: null};
      component.discountForm.patchValue({discountType: DISCOUNT_TYPE.PER_PRODUCT})
      component.etherealForm.setValue(productForm);
      expect(component.etherealForm.getRawValue()).toEqual(productForm)
      component.addProductDisctount();
      expect(component.perProducts).toHaveLength(0);

      component.etherealForm.setValue({...productForm, discount: 0});
      expect(component.etherealForm.getRawValue()).toEqual({...productForm, discount: 0})
      component.addProductDisctount();
      expect(component.perProducts).toHaveLength(0);
    })
  })

});
