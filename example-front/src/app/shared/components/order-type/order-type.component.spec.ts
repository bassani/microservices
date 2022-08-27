import { HttpClientModule } from '@angular/common/http';
import { Component, CUSTOM_ELEMENTS_SCHEMA, ViewChild } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormControl, FormGroup } from '@angular/forms';
import { of } from 'rxjs';
import { OrderTypeService } from '../../services';
import { FieldUtilsService } from '../../utils/field-utils.service';
import { MOCK_SEARCH_PAGED, SEARCH_ITEM_LIST } from '../../utils/mock';

import { OrderTypeComponent } from './order-type.component';

@Component({
  selector: 'stub',
  template: `<app-order-type
    [control]="formGroup.get('testedValue')"
    #orderType
  ></app-order-type>`,
})
class Parent {
  @ViewChild('orderType') orderTypeComponent: OrderTypeComponent;
  formGroup: FormGroup = new FormGroup({
    testedValue: new FormControl([]),
    untestedValue: new FormControl(''),
  });
}

describe('OrderTypeComponent', () => {
  let component: OrderTypeComponent;
  let fixture: ComponentFixture<OrderTypeComponent>;
  let parentFixture: ComponentFixture<Parent>;
  let parent: Parent;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [OrderTypeComponent, Parent],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
      providers: [
        FieldUtilsService,
        {

          provide: OrderTypeService,
          useValue: { getAllOrderTypes: () => of(MOCK_SEARCH_PAGED) },
        },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    parentFixture = TestBed.createComponent(Parent);
    parent = parentFixture.componentInstance;
    parentFixture.detectChanges();

    fixture = TestBed.createComponent(OrderTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('Deve criar o componente', () => {
    expect(component).toBeTruthy();
  });

  describe('Input de tipo de pedido', () => {
    it('Deve atualizar as sugestões de acordo com o retorno do servidor', () => {
      component.orderTypes$.next({
        content: [],
        numberOfElements: 0,
        size: 10,
        page: 0,
      });
      spyOn(component['ordertypeService'], 'getAllOrderTypes').and.returnValue(
        of(MOCK_SEARCH_PAGED)
      );
      expect(component.orderTypes$.value.content).toEqual([]);
      component.getOrderTypes();
      expect(
        component['ordertypeService'].getAllOrderTypes
      ).toHaveBeenCalledTimes(1);
      expect(component.orderTypes$.value.content).toEqual(
        MOCK_SEARCH_PAGED.content
      );
    });

    it('Deve ter um control associado', () => {
      const val = [{ id: 1, name: 'teste' }];
      expect(parent.formGroup.get('testedValue')?.value).not.toEqual(val);
      expect(parent.formGroup.get('untestedValue')?.value).toEqual('');
      parent.orderTypeComponent.control.setValue(val);
      expect(parent.formGroup.get('testedValue')?.value).toEqual(val);
      expect(parent.formGroup.get('untestedValue')?.value).toEqual('');
    });
  });
});
