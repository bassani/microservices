import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, CUSTOM_ELEMENTS_SCHEMA, ViewChild } from '@angular/core';
import {
  ComponentFixture,
  fakeAsync,
  TestBed,
  tick,
} from '@angular/core/testing';
import { FormControl, FormGroup } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { of } from 'rxjs';
import { CategoryService, PAGED_SEARCH_MOCK } from '../../services';
import { CATEGORY_MOCK, MOCK_SEARCH_PAGED, SEARCH_ITEM_LIST, SUBCATEGORY_MOCK } from '../../utils/mock';

import { CategoryComponent } from './category.component';

@Component({
  selector: 'stub',
  template: `<app-category
    [category]="formGroup.get('categoryControl')"
    [subcategory]="formGroup.get('subCategoryControl')"
    #category
  >
  </app-category>`,
})
class Stub {
  @ViewChild('category') categoryComponent: CategoryComponent;
  formGroup: FormGroup = new FormGroup({
    categoryControl: new FormControl([]),
    subCategoryControl: new FormControl([]),
    untestedValue: new FormControl(''),
  });
}
describe('CategoryComponent', () => {
  let component: CategoryComponent;
  let fixture: ComponentFixture<CategoryComponent>;

  let stub: Stub;
  let stubFixture: ComponentFixture<Stub>;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CategoryComponent, Stub],
      imports: [HttpClientModule],
      providers: [CategoryService, MessageService],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    stubFixture = TestBed.createComponent(Stub);
    stub = stubFixture.componentInstance;
    stubFixture.detectChanges();

    fixture = TestBed.createComponent(CategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('Deve criar o componente', () => {
    expect(component).toBeTruthy();
  });
  it('Deve ter um control associado', fakeAsync(() => {
    const val = [{ id: 1, name: 'teste' }];
    expect(stub.formGroup.get('testedValue')?.value).not.toEqual(val);
    expect(stub.formGroup.get('categoryControl')?.value).toEqual([]);
    expect(stub.formGroup.get('subCategoryControl')?.value).toEqual([]);
    spyOn(
      stub.categoryComponent['categoryService'],
      'getAllCategories'
    ).and.returnValue(of([]));
    spyOn(
      stub.categoryComponent['categoryService'],
      'getAllSubcategories'
    ).and.returnValue(of([]));
    component.ngOnInit();
    tick(10);
    // Testing category control
    stub.categoryComponent.categoryControl.setValue(val);
    tick(100);
    expect(stub.formGroup.get('categoryControl')?.value).toEqual(val);
    expect(stub.formGroup.get('untestedValue')?.value).toEqual('');

    // Testing subcaregory control
    stub.categoryComponent.subcategoryControl.setValue(val);
    tick(10);
    expect(stub.formGroup.get('subCategoryControl')?.value).toEqual(val);
    expect(stub.formGroup.get('untestedValue')?.value).toEqual('');
  }));
  describe('Input de categoria', () => {
    it('Deve obter sugestões baseado na resposta do servidor', () => {
      component.category$.next({
        content: [],
        numberOfElements: 0,
        size: 10,
        page: 0,
      });

      spyOn(component['categoryService'], 'getAllCategories').and.returnValue(
        of(MOCK_SEARCH_PAGED)
      );
      expect(component.category$.value.content).toEqual([]);
      component.getCategories();
    });
  });

  describe('Input de subcategoria', () => {
    it('Deve atualizar as recomendações baseado no valor da categoria', () => {
      const mock = [{ id: 1, name: 'teste' }];
      component.ngOnInit();
      spyOn(component, 'getSubcategories').and.callThrough();
      spyOn(
        component['categoryService'],
        'getAllSubcategories'
      ).and.returnValue(of({...MOCK_SEARCH_PAGED, content: SUBCATEGORY_MOCK.map((e => ({...e, categoryId: e.id})))}));
      component.categoryControl.setValue(mock);
      component.getSubcategories({itemValue: {...mock[0]}});
      expect(component.getSubcategories).toHaveBeenCalledTimes(1);
      expect(
        component['categoryService'].getAllSubcategories
      ).toHaveBeenCalledTimes(1);
      expect(
        component['categoryService'].getAllSubcategories
      ).toHaveBeenCalledWith(mock, []);
    });

    it('Deve remover as subcategoria que não pertencem as categorias selecionadas', () => {
      const mock = { id: 1, name: 'teste' };
      component.ngOnInit();
      spyOn(component, 'getSubcategories').and.callThrough();
      spyOn(
        component['categoryService'],
        'getAllSubcategories'
      ).and.returnValue(of({...MOCK_SEARCH_PAGED, content: SUBCATEGORY_MOCK.map((e => ({...e, categoryId: e.id})))}));
      component.categoryControl.setValue([{id: 1, name: 'teste'}]);
      component.subcategoryControl.setValue([{id: 1, name: 'teste', categoryId: 1}, {id: 2, name: 'teste2', categoryId: 2}]);
      component.subcategory$.next({...component.subcategory$.value, content: [{id: 1, name: 'teste', categoryId: 1}, {id: 2, name: 'teste2', categoryId: 2}]})
      component.getSubcategories({itemValue: {...mock, id: 2}});
      
      expect(component.subcategoryControl.value.find((el: any) => el.categoryId === 2)).toBeFalsy();
      expect(component.subcategory$.value.content.find((el: any) => el.categoryId === 2)).toBeFalsy();
    });

    it('Deve obter sugestões baseado na resposta do servidor', () => {
      component.categoryControl.setValue([CATEGORY_MOCK[1]])
      spyOn(
        component['categoryService'],
        'getAllSubcategories'
      ).and.returnValue(of({...MOCK_SEARCH_PAGED, content: SUBCATEGORY_MOCK}));
      expect(component.subcategory$.value.content).toEqual([]);
      component.getSubcategories({itemValue: CATEGORY_MOCK[1]});
      expect(
        component['categoryService'].getAllSubcategories
      ).toHaveBeenCalledTimes(1);
    });
  });

  describe('OnDestroy', () => {
    it('Deve destruir a subscripção quando o componente for destruido', () => {
      expect(component.subscription.closed.valueOf()).toBeFalsy();
      component.ngOnDestroy();
      expect(component.subscription.closed.valueOf()).toBeTruthy();
    });
    it('Não deve realizar nada ao destruir o componente caso não tenha subscription', () => {
      component.subscription.unsubscribe();
      expect(component.subscription.closed.valueOf()).toBeTruthy();
      component.ngOnDestroy();
      expect(component.subscription.closed.valueOf()).toBeTruthy();
    });
  });
});
