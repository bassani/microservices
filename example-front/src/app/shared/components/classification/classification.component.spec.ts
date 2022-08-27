import { HttpClientModule } from '@angular/common/http';
import { Component, CUSTOM_ELEMENTS_SCHEMA, ViewChild } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormControl, FormGroup } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Dropdown, DropdownModule } from 'primeng/dropdown';
import { of } from 'rxjs';
import { ClassificationService } from '../../services';
import { FieldUtilsService } from '../../utils/field-utils.service';
import { MOCK_SEARCH_PAGED, SEARCH_ITEM_LIST } from '../../utils/mock';

import { ClassificationComponent } from './classification.component';

@Component({
  selector: 'stub',
  template: `<app-classification
    [control]="formGroup.get('testedValue')"
    #classi
  ></app-classification>`,
})
class Stub {
  @ViewChild('classi') classi: ClassificationComponent;
  formGroup: FormGroup = new FormGroup({
    testedValue: new FormControl([]),
    untestedValue: new FormControl(''),
  });
}

describe('ClassificationComponent', () => {
  let stub: Stub;
  let stubFixture: ComponentFixture<Stub>;
  let component: ClassificationComponent;
  let fixture: ComponentFixture<ClassificationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ClassificationComponent, Stub],
      imports: [DropdownModule, HttpClientModule],
      providers: [ClassificationService, MessageService, FieldUtilsService],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    stubFixture = TestBed.createComponent(Stub);
    stub = stubFixture.componentInstance;
    stubFixture.detectChanges();
    fixture = TestBed.createComponent(ClassificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('Deve criar o componente', () => {
    expect(component).toBeTruthy();
  });

  describe('Input de classificação', () => {
    it('Deve atualizar as sugestões de acordo com a pesquisa', () => {
      component.classifications$.next({
        content: [],
        numberOfElements: 0,
        size: 10,
        page: 0,
      });
      spyOn(
        component['classificationService'],
        'getAllClassifications'
      ).and.returnValue(of(MOCK_SEARCH_PAGED));
      expect(component.classifications$.value.content).toEqual([]);
      component.getClassifications({query: 'q'});
      expect(
        component['classificationService'].getAllClassifications
      ).toHaveBeenCalledTimes(1);
      expect(component.classifications$.value).toEqual(MOCK_SEARCH_PAGED);
    });

    it('Deve ter um control associado', () => {
      const val = [{ id: 1, name: 'teste' }];
      expect(stub.formGroup.get('testedValue')?.value).not.toEqual(val);
      expect(stub.formGroup.get('untestedValue')?.value).toEqual('');
      stub.classi.control.setValue(val);
      expect(stub.formGroup.get('testedValue')?.value).toEqual(val);
      expect(stub.formGroup.get('untestedValue')?.value).toEqual('');
    });
  });
});
