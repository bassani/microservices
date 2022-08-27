import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { MessageService, SharedModule } from 'primeng/api';
import { CheckboxModule } from 'primeng/checkbox';
import { of } from 'rxjs';

import { SalesReportComponent } from './sales-report.component';
const mockClean = {
  distributionCenters: null,
  supplier:null,
  manufacturers: null,
  product: null,
  onlyInactives: null,
  category: null
}
const mock = {
  distributionCenters: [{id: 0, name: 'teste'}],
  supplier: {id: 0, name: 'teste'},
  manufacturers: [{id: 0, name: 'teste'}],
  product: [{id: 0, name: 'teste'}],
  onlyInactives: false,
  category: {id: 0, name: 'teste'}
};
describe('SimulateReportComponent', () => {
  let component: SalesReportComponent;
  let fixture: ComponentFixture<SalesReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SalesReportComponent],
      imports: [RouterTestingModule, CommonModule, ReactiveFormsModule, HttpClientModule, SharedModule, CheckboxModule],
      providers: [{provide: MessageService, useValue: {add: () => {}}}],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Criar formulario', () => {
    it('Deve criar o formulario', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});
      component.form = null as unknown as FormGroup;
      component.createForm();
      expect(component.form.valid).toEqual(false);
    });

    it('Deve limpar o formulario', () => {
      component.createForm();
     
      component.form.patchValue(mock);
      expect(component.form.getRawValue()).toEqual(mock);
      component.cleanButton();
      expect(component.form.getRawValue()).toEqual(mockClean)
    })
  });

  describe('Exportar relatorio', () => {
    it('Deve chamar o serviço caso o formulario seja valido', () => {
      spyOn(component['_salesService'], 'exportSalesReport').and.returnValue(of({}));
      spyOn(component['_message'], 'add').and.returnValue(of({}));
      component.form.patchValue(mock);
      component.exportData();
      expect(component['_salesService'].exportSalesReport).toHaveBeenCalledTimes(1);
      expect(component['_message'].add).toHaveBeenCalledTimes(0);

    })
    it('Deve não chamar o serviço caso o formulario seja invalido', () => {
        spyOn(component['_salesService'], 'exportSalesReport').and.returnValue(of({}));
        spyOn(component['_message'], 'add').and.returnValue(of({}));
        component.form.patchValue(mockClean);
        component.exportData();
        expect(component['_salesService'].exportSalesReport).toHaveBeenCalledTimes(0);
        expect(component['_message'].add).toHaveBeenCalledTimes(1);
    })
  })

});
