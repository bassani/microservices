import { HttpClientModule } from '@angular/common/http';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { APP_BASE_HREF, CommonModule } from '@angular/common';
import { MessageService, SharedModule } from 'primeng/api';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimulateSpecifics } from './simulate-specifics.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CalendarModule } from 'primeng/calendar';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import {  SimulateService } from '../../services/simulate/simulate.service';
import { SimulationTypeInputComponent } from '../../components/simulation-type-input/simulation-type-input.component';
import { BaseSimulation } from 'src/app/shared/models/simulation.model';
import { InputNumberModule } from 'primeng/inputnumber';
import { RadioButtonModule } from 'primeng/radiobutton';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
const MOCK = new BaseSimulation({
  id: 0,
  userId: 0,
  simulationType: {
    key: 'antecipacao',
    id: 1,
    title: 'teste',
    description: 'teste',
    icon: 'none',
  },
  orderType: { id: 0, name: 'test' },
  calculationBasis: {id: 1, name: 'teste', parent: 1},
  suppliers: [
    { id: 3, name: 'first', parentSupplierId: 1 },
    { id: 5, name: 'seccond', parentSupplierId: 1 },
    { id: 8, name: 'third', parentSupplierId: 1 },
  ],
  distributionCenters: [
    { id: 1, name: 'first' },
    { id: 3, name: 'seccond' },
    { id: 5, name: 'third' },
  ],
});

class StubAR {
  get paramMap(): any {
    return of({ get: (...val: any[]) => ({ id: 1 }) });
  }
}
describe('SimulateAntecipationComponent', () => {
  let component: SimulateSpecifics;
  let fixture: ComponentFixture<SimulateSpecifics>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        SharedModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        InputTextareaModule,
        RouterTestingModule,
        CalendarModule,
        InputNumberModule,
        RadioButtonModule,
        NoopAnimationsModule
      ],
      declarations: [SimulateSpecifics, SimulationTypeInputComponent],
      providers: [
        MessageService,
        { provide: APP_BASE_HREF, useValue: '/' },
        {
          provide: ActivatedRoute,
          useClass: StubAR,
        },
        {
          provide: SimulateService,
          useValue: {
            getSimulation: (...args: any[]) => {},
            patchSpecifics: (...args: any[]) => {},
          }
        }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SimulateSpecifics);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Init', () => {
    it('Deve redirecionar caso a simulação não exista', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});
      spyOn(component['_simulationService'], 'getSimulation').and.returnValue(
        of(null)
      );
      component.ngOnInit();
      expect(component['_router'].navigate).toHaveBeenCalledTimes(1);
    });
    it('Deve redirecionar caso a simulação esteja concluida', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});
      spyOn(component['_simulationService'], 'getSimulation').and.returnValue(
        of({ status: 9 })
      );
      component.ngOnInit();
      expect(component['_router'].navigate).toHaveBeenCalledTimes(1);
    });
    it('Deve setar a simulação com o valor recebido do serviço', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});
      spyOn(component['_simulationService'], 'getSimulation').and.returnValue(
        of(MOCK)
      );
      component.ngOnInit();
      expect(component['_router'].navigate).toHaveBeenCalledTimes(0);
      expect(component.simulation).toEqual(MOCK);
    });
    it('Deve redirecionar para a simulação se a requisição do serviço retornar erro', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});
      spyOn(component['_simulationService'], 'getSimulation').and.returnValue(
        throwError({ status: 500, error: { message: 'Teste erro' } })
      );
      component.ngOnInit();
      expect(component['_router'].navigate).toHaveBeenCalledTimes(1);
    });
    it('Deve ignorar o erro da requisição do serviço caso a simulação exista na instancia', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});
      component.simulation = MOCK;
      spyOn(component['_simulationService'], 'getSimulation').and.returnValue(
        throwError({ status: 500, error: { message: 'Teste erro' } })
      );
      component.ngOnInit();
      expect(component['_router'].navigate).toHaveBeenCalledTimes(0);
    });
  });

  describe('Criar formulario', () => {
    it('Deve criar o formulario', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});
      component.simulation = MOCK;
      component.form = null as unknown as FormGroup;
      component.createForm();
      expect(component.form.valid).toEqual(false);
    });

    it('Deve ter os validadores incorretos e informar a mensagem ao usuário', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});
      spyOn(component['_simulationService'], 'patchSpecifics').and.returnValue(
        of(MOCK)
      );
      spyOn(component['_simulationService'], 'getSimulation').and.returnValue(
        of({...MOCK, status: 1})
      );
      component.ngOnInit();
      spyOn(component['_message'], 'add').and.returnValue([]);
      component.simulation = MOCK;
      component.simulate();
      expect(component.form.valid).toEqual(false);
      expect(component['_message'].add).toHaveBeenCalledTimes(1);
    });
    it('Deve ter os validadores corretos e não informar a mensagem ao usuário', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});
      spyOn(component['_simulationService'], 'patchSpecifics').and.returnValue(
        of(MOCK)
      );
      component.ngOnInit();
      spyOn(component['_message'], 'add').and.returnValue([]);
      component.simulation = MOCK;
      component.createForm();

      component.form.patchValue({
        newPaymentTerm: null,
        anticipationDate: new Date(),
        calculationBasis: {id: 1, name: 'teste', parent: 1},
        note: 'teste observation',
      });
      expect(component.form.valid).toEqual(true);
      expect(component['_message'].add).toHaveBeenCalledTimes(0);
    });
  });

  describe('Simular', () => {
    it('Deve realizar patch do modelo de simulação e navegar para o resultado', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});
      spyOn(component['_simulationService'], 'patchSpecifics').and.returnValue(
        of(MOCK)
      );
      component.simulation = MOCK;
      component.createForm();
      component.form.patchValue({
        anticipationDate: new Date(),
        newPaymentTerm: null,
        salesCalculation: [{ id: 1, parent: 1, name: 'teste' }],
        typeProducts: [],
        calculationBasis: {id: 1, name: 'teste', parent: 1},
        note: 'teste',
      });

      component.simulate();
      expect(component['_router'].navigate).toHaveBeenCalledTimes(1);
      expect(component['_router'].navigate).toHaveBeenCalledWith([
        'simulador',
        'resultado',
        MOCK.id,
      ]);
    });
  });
});
