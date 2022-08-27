import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DialogModule } from 'primeng/dialog';
import { of } from 'rxjs';
import { ProductService } from '../../services';

import { ProductFilterComponent } from './product-filter.component';

describe('ProductFilterComponent', () => {
  let component: ProductFilterComponent;
  let fixture: ComponentFixture<ProductFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductFilterComponent ],
      imports: [DialogModule],
      providers: [
        {
          provide: ProductService,
          useValue: {
            getProducts: (...args: any[]) => {},
            validateCodes: (...args: any[]) => {},
          }
        }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Select', () => {
    it('Deve adicionar a lista de produtos selecionados sem duplicados', () => {
      const mock = {id: 0, name: 'teste'}
      component.productsControl.setValue([]);
      component.filterControl.setValue(mock)
      component.selected();
      expect(component.filterControl.value).toEqual(null);
      expect(component.productsControl.value).toEqual([mock]);

      component.filterControl.setValue(mock)
      component.selected();
      expect(component.productsControl.value).toEqual([mock]);

    })


  })

  describe('Remove', () => {

    it('Deve remover os itens com o id informado', () => {
      const mock = [{id: 0, name: 'teste'}, {id: 1, name: 'teste'}, {id: 2, name: 'teste'}]
      component.productsControl.setValue(mock);
      component.removeProduct(mock[1]);
      expect(component.productsControl.value).toEqual([mock[0], mock[2]]);
      component.removeProduct(mock[1]);
      expect(component.productsControl.value).toEqual([mock[0], mock[2]]);

    })
  })

  describe('Search', () => {
    it('Deve chamar o serviço e preencher a lista de opções', () => {
      const mockList = {content: [{id: 0, name: 'teste'}, {id: 1, name: 'teste'}]}
      spyOn(component['_productService'], 'getProducts').and.returnValue(of(mockList));
      component.products$.next([]);
      component.getProducts({query: 'tes'});
      expect(component['_productService'].getProducts).toHaveBeenCalledTimes(1);
      expect(component['_productService'].getProducts).toHaveBeenCalledWith('tes');
      expect(component.products$.value).toEqual(mockList.content)
    })

  })

  describe('Validate', () => {
    it('Deve validar uma lista de codigos de fabricante', () => {
      const codes = '123;321;a23;aa';
      const mock = {invalid: ['a23', 'aa'], valid: [{id: 123, name: 'teste'}, {id: 321, name: 'teste'}]}
      spyOn(component['_productService'], 'validateCodes').and.returnValue(of(mock));
      component.separatorErrorList$.next([]);
      component.getProducts({query: codes});
      expect(component['_productService'].validateCodes).toHaveBeenCalledTimes(1);
      expect(component['_productService'].validateCodes).toHaveBeenCalledWith(codes);
      expect(component.productsControl.value).toEqual(mock.valid)
      expect(component.separatorErrorList$.value).toEqual(mock.invalid)
    })
  })


  describe('Modal', () => {

    it('deve mostrar o modal e setar o valor do control', () => {
      component.displayModal = false;
      const controlValueMock = [{id: 0, name: 'teste'}, {id: 1, name: 'teste'}];
      expect(component.productsControl.value).toEqual([]);
      component.control.setValue(controlValueMock)
      component.showModal();
      expect(component.displayModal).toEqual(true);
      expect(component.productsControl.value).toEqual(controlValueMock);

    })

    it('deve ocultar Modal sem salvar ao cancelar', () => {
      component.displayModal = true;
      const controlValueMock = [{id: 0, name: 'teste'}, {id: 1, name: 'teste'}];
      expect(component.control.value).toEqual([]);
      component.productsControl.setValue(controlValueMock)
      component.closeModal();
      expect(component.displayModal).toEqual(false);
      expect(component.control.value).toEqual([]);
    })

    it('Deve setar o valor do control quando salvar', () => {
      component.displayModal = true;
      const controlValueMock = [{id: 0, name: 'teste'}, {id: 1, name: 'teste'}];
      expect(component.control.value).toEqual([]);
      component.productsControl.setValue(controlValueMock)
      component.save();
      expect(component.displayModal).toEqual(false);
      expect(component.control.value).toEqual(controlValueMock);
    })
  })


});
