import { APP_BASE_HREF, CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { MessageService, SharedModule } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { of, throwError } from 'rxjs';
import { BaseSimulation } from 'src/app/shared/models/simulation.model';
import { SimulateService } from '../../services/simulate/simulate.service';

import { SimulateComponent } from './simulate.component';
const MOCK = new BaseSimulation({
  id: 0,
  userId: 0,
  simulationType: {
    key: 'teste',
    id: 0,
    title: 'teste',
    description: 'teste',
    icon: 'none',
  },
  orderType: { id: 0, name: 'test' },
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

const VALID_PATCH = {
  distributionCenters: [{ id: 1, name: 'teste' }],
  manufacturers: [],
  category: { id: 1, name: 'teste' },
  subcategory: null,
  orderType: { name: 'teste', id: 1 },
  classification: { name: 'teste', id: 1 },
};
describe('SimulateComponent', () => {
  let component: SimulateComponent;
  let fixture: ComponentFixture<SimulateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        SharedModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        RouterTestingModule,
        ButtonModule,
        CardModule,
      ],
      declarations: [SimulateComponent],
      providers: [
        {
          provide: SimulateService,
          useValue: {
            getSimulation: (...args: any[]) => {},
            patchBasics: (...args: any[]) => {}
          }
        },
        MessageService,
        { provide: APP_BASE_HREF, useValue: '/' },
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of({ get: (val: any) => '1' }),
          },
        },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SimulateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Init component', () => {
    it('Deve criar o form', () => {
      component.form = undefined as unknown as FormGroup;
      expect(component.form).not.toBeDefined();
      component.createForm();
      expect(component.form).toBeDefined();
    });

    it('Deve setar os valores do form se a simulação existir', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});
      spyOn(component, 'createForm').and.callThrough();
      spyOn(component, 'updateForm').and.callThrough();
      spyOn(component['_ar'], 'paramMap').and.returnValue(
        of({ get: (val: any) => val })
      );
      spyOn(component['_simulationService'], 'getSimulation').and.returnValue(
        of(MOCK)
      );
      component.ngOnInit();

      expect(component['_router'].navigate).toHaveBeenCalledTimes(0);
      expect(component.createForm).toHaveBeenCalledTimes(1);
      expect(component.createForm).toHaveBeenCalledTimes(1);
    });

    it('Deve redirecionar para o inicio caso a simulação não exista', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});

      spyOn(component['_simulationService'], 'getSimulation').and.returnValue(
        of(null)
      );
      component.ngOnInit();
      expect(component['_router'].navigate).toHaveBeenCalledTimes(1);
    });

    it('Deve redirecionar para o inicio caso a pesquisa da simualação retorne erro', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});
      spyOn(component['_simulationService'], 'getSimulation').and.returnValue(
        throwError({ status: 500, error: { message: 'Internal Server Error' } })
      );
      component.ngOnInit();
      expect(component['_router'].navigate).toHaveBeenCalledTimes(1);
    });

    it('Deve redirecionar para o inicio caso a simulação exista mais esteja concluida', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});

      spyOn(component['_simulationService'], 'getSimulation').and.returnValue(
        of({ ...MOCK, status: 9 })
      );
      component.ngOnInit();
      expect(component['_router'].navigate).toHaveBeenCalledTimes(1);
    });
  });

  describe('Validadores do formulario', () => {
    it('Deve ter como requerido o campo de CD', () => {
      component.createForm();
      expect(component.form.get('distributionCenters')?.valid).toEqual(false);
      component.form
        .get('distributionCenters')
        ?.setValue([{ name: 'teste', id: 1 }]);
      expect(component.form.get('distributionCenters')?.valid).toEqual(true);
    });

    it('Deve ter como requerido o tipo de pedido', () => {
      component.createForm();
      expect(component.form.get('orderType')?.valid).toEqual(false);
      component.form.get('orderType')?.setValue([{ name: 'teste', id: 1 }]);
      expect(component.form.get('orderType')?.valid).toEqual(true);
    });

    it('Deve ter como requerido o campo de classificação', () => {
      component.createForm();
      expect(component.form.get('classification')?.valid).toEqual(false);
      component.form
        .get('classification')
        ?.setValue([{ name: 'teste', id: 1 }]);
      expect(component.form.get('classification')?.valid).toEqual(true);
    });

    it('Deve ter como requerido ou o campo de fornecedor ou categoria', () => {
      component.createForm();
      component.form.patchValue(VALID_PATCH);
      component.form.get('suppliers')?.setValue(null);
      component.form.get('category')?.setValue(null);
      expect(component.form.valid).toEqual(false);
      component.form.get('suppliers')?.setValue([{ name: 'teste', id: 1 }]);
      expect(component.form.valid).toEqual(true);
      component.form.get('suppliers')?.setValue(null);
      expect(component.form.valid).toEqual(false);
      component.form.get('category')?.setValue({ name: 'teste', id: 1 });
      expect(component.form.valid).toEqual(true);
      component.form.get('suppliers')?.setValue([{ name: 'teste', id: 1 }]);
      expect(component.form.valid).toEqual(true);
    });
  });

  describe('Start simulation', () => {
    it('Deve informar o erro se os campos requeridos não estiverem completos', () => {
      component.createForm();
      spyOn(component['_message'], 'add').and.returnValue({});
      spyOn(component['_simulationService'], 'patchBasics').and.returnValue(
        of({})
      );
      expect(component.form.valid).toEqual(false);
      component.startSimulation();
      fixture.detectChanges();

      expect(component['_message'].add).toHaveBeenCalledTimes(1);
      expect(component['_message'].add).toHaveBeenCalledWith({
        severity: 'error',
        summary: 'Campos incompletos',
        detail:
          'Verifique que todos os campos requeridos estejam completos para continuar.',
        key: 'main',
      });
      expect(component['_simulationService'].patchBasics).toHaveBeenCalledTimes(
        0
      );
    });

    it('Deve chamar o serviço e redirecionar para o proximo step', () => {
      component.createForm();
      component.simulationType = '1';
      const mocker = { ...MOCK, id: 33 };
      component.simulation = mocker;
      spyOn(component['_simulationService'], 'patchBasics').and.returnValue(
        of(mocker)
      );
      spyOn(component['_router'], 'navigate').and.returnValue({});
      component.form.patchValue(VALID_PATCH);
      expect(component.form.valid).toEqual(true);
      component.startSimulation();
      fixture.detectChanges();

      expect(component['_simulationService'].patchBasics).toHaveBeenCalledTimes(
        1
      );
      expect(component['_router'].navigate).toHaveBeenCalledTimes(1);
      expect(component['_router'].navigate).toHaveBeenCalledWith([
        'simulador',
        'simular',
        '1',
        mocker.id,
        'efetuar',
      ]);
    });

    it('Deve chamar o serviço e realizar handling em caso de retorno de erro do server', () => {
      component.createForm();
      const mocker = { ...MOCK, id: 33 };
      component.simulation = mocker;
      spyOn(component['_simulationService'], 'patchBasics').and.returnValue(
        throwError({ status: 500, error: { message: 'Internal' } })
      );
      spyOn(component['_router'], 'navigate').and.returnValue({});
      spyOn(component['_message'], 'add').and.returnValue({});
      component.form.patchValue(VALID_PATCH);
      expect(component.form.valid).toEqual(true);
      component.startSimulation();
      fixture.detectChanges();
      expect(component['_simulationService'].patchBasics).toHaveBeenCalledTimes(
        1
      );
      expect(component['_message'].add).toHaveBeenCalledTimes(1);
      expect(component['_message'].add).toHaveBeenCalledWith({
        severity: 'error',
        summary: 'Não foi possível gerar simulação',
        detail: 'Verifique a sua conexão e tente novamente mais tarde.',
        key: 'main',
      });
      expect(component['_router'].navigate).toHaveBeenCalledTimes(0);
    });
  });
});
