import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MessageService } from 'primeng/api';
import { of, throwError } from 'rxjs';
import { ProductService } from '../../services/product/product.service';
import { BASIC } from '../../utils/mock';
import { ProductComponent } from './product.component';

describe('ProductComponent', () => {
  let component: ProductComponent;
  let fixture: ComponentFixture<ProductComponent>;
  let productService: ProductService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProductComponent],
      imports: [HttpClientModule],
      providers: [ProductService, {
        provide: MessageService, useValue: {add: () => {}}
      }],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
    productService = TestBed.inject(ProductService);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Procura por Produtos', () => {
    it('Deve fazer a busca por produtos com os filtros de supplier, category e subcategory', () => {
      const response = BASIC.product;
      component.products$.next([]);
      component.suppliers = [{id: 1, name: 'supplier 1'}];
      component.categories = [{id: 3, name: 'category 3'}];
      component.subcategories = [{id: 8, name: 'subcategory 8'}];
      expect(component.products$.value).not.toEqual(response.content);
      spyOn(component['productService'], 'getProducts').and.returnValue(
        of(response)
      );
      component.getProducts({query: 'q'});
      expect(component.products$.value).toEqual(response.content);
    });

    it('Deve mostar uma mensagem para erros de servidor sem detalhes', () => {
      const response = {
        status: 500,
        error: { message: 'Internal Unit Error' },
      };
      component.products$.next([{id: 0, name: 'teste'}])
      expect(component.products$.value).not.toEqual(response);
      spyOn(productService, 'getProducts').and.returnValue(
        throwError(response)
      );
      component.getProducts({query: 'q'});
      expect(component.products$.value).toEqual([]);

    });
  });
});
