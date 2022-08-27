import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { MessageService, SharedModule } from 'primeng/api';
import { Button, ButtonModule } from 'primeng/button';
import { DialogService, DynamicDialogModule } from 'primeng/dynamicdialog';
import { of, throwError } from 'rxjs';
import { NgVarDirective } from 'src/app/shared/directives/ng-var.directive';
import { WithRolesDirective } from 'src/app/shared/directives/with-roles.directive';
import { AuthService } from 'src/app/shared/services';
import { MOCK_CUSTOM_PAGED } from 'src/app/shared/utils/mock';
import { EditClassificationDialog } from '../../dialogs/edit-classification/edit-classification.dialog';

import { ClassificationComponent } from './classification.component';

describe('ClassificationComponent', () => {
  let component: ClassificationComponent;
  let fixture: ComponentFixture<ClassificationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ClassificationComponent, WithRolesDirective, NgVarDirective],
      imports: [
        ButtonModule,
        HttpClientModule,
        DynamicDialogModule,
        FormsModule,
        ReactiveFormsModule,
        SharedModule,
        RouterTestingModule

      ],
      providers: [MessageService, DialogService, {
        provide: AuthService, useValue: {roles: ['create_classification', 'update_classification']}
      }],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClassificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Listar classificações', () => {
    it('Deve listar as classificações utilizando o servico', () => {
      const mock = MOCK_CUSTOM_PAGED(
        { name: 'Teste', description: 'Teste', active: true },
        10
      );
      component.classifications$.next({
        ...component.classifications$.value,
        content: [],
      });
      spyOn(component['_service'], 'getAll').and.callFake((...args) => {
        component['_service'].classifications$.next(mock);
      });
      expect(component.classifications$.value.content).toHaveLength(0);
      component.getAll();
      expect(component['_service'].getAll).toHaveBeenCalledTimes(1);
      expect(component['_service'].getAll).toHaveBeenCalledWith(0, 10);
      expect(component.classifications$.value.content).toHaveLength(10);
    });

    it('Deve mostrar uma mensagem de retry caso o serviço retorne erro', () => {
      const mock = { status: 500, error: { message: 'Critial Mission Error' } };
      component.classifications$.next({
        ...component.classifications$.value,
        content: [],
      });
      spyOn(component['_service'], 'getAll').and.returnValue(throwError(mock));
      expect(component.classifications$.value.content).toHaveLength(0);
      component.getAll();
      expect(component['_service'].getAll).toHaveBeenCalledTimes(1);
      expect(component['_service'].getAll).toHaveBeenCalledWith(0, 10);
      expect(component.classifications$.value.content).toHaveLength(0);
    });
  });

  describe('Pagination', () => {
    it('Deve listar as classificações de acordo com a paginação informada', () => {
      const mock = MOCK_CUSTOM_PAGED(
        { name: 'Teste', description: 'Teste', active: true },
        10
      );
      component.classifications$.next({ ...mock, content: [] });
      spyOn(component['_service'], 'getAll').and.returnValue(of(mock));
      expect(component.classifications$.value.content).toHaveLength(0);
      component.changePage();
      expect(component['_service'].getAll).toHaveBeenCalledTimes(1);
      expect(component['_service'].getAll).toHaveBeenCalledWith(0, 10);
      component.changePage(4, 20);
      expect(component['_service'].getAll).toHaveBeenCalledTimes(2);
      expect(component['_service'].getAll).toHaveBeenCalledWith(4, 20);
    });
  });

  describe('Adicionar classificação', () => {
    it('Deve validar os campos para permitir a inserção', () => {
      component.ngOnInit();
      expect(component.form.valid).toBeFalsy();
      component.form.patchValue({ name: 'TESTE NAME' });
      expect(component.form.valid).toBeFalsy();
      component.form.setValue({
        name: 'TESTE NAME',
        description: 'TESTE DESCRIPTION',
      });
      expect(component.form.valid).toBeTruthy();
      component.form.setValue({ name: null, description: 'TESTE DESCRIPTION' });
      expect(component.form.valid).toBeFalsy();
    });

    it('Deve enviar os dados para o serviço e informar caso sucesso', () => {
      const mock = {
        id: 1,
        name: 'Teste 1',
        description: 'Teste 1 description',
        active: true,
      };
      spyOn(component['_message'], 'add').and.returnValue({});
      spyOn(component['_dialog'], 'open').and.returnValue(of(mock));
      component.form.setValue({
        name: mock.name,
        description: mock.description,
      });
      component.editOrAddClassification(component.form.getRawValue());
      expect(component['_dialog'].open).toHaveBeenCalledTimes(1);
      expect(component['_dialog'].open).toHaveBeenCalledWith(
        EditClassificationDialog,
        {
          width: '35%',
          header: 'Cadastrar Classificação',
          data: {
            name: mock.name,
            description: mock.description,
          },
        }
      );
    });
  });

  describe('Editar classificação', () => {
    it('Deve abrir o modal de editar classificação', () => {
      const mock = {
        id: 1,
        name: 'Teste Edit',
        description: 'Teste description',
        active: true,
      };
      spyOn(component['_dialog'], 'open').and.returnValue(of(mock));
      component.editOrAddClassification(mock);
      expect(component['_dialog'].open).toHaveBeenCalledTimes(1);
      expect(component['_dialog'].open).toHaveBeenCalledWith(
        EditClassificationDialog,
        {
          data: mock,
          width: '35%',
          header: 'Alterar Classificação',
        }
      );
    });
  });

  describe('Download vigencia atual', () => {
    it('Deve chamar a função de download de vigentes do serviço de classificação', () => {
      spyOn(
        component['_service'],
        'downloadCurrentClassifications'
      ).and.returnValue({});
      component.downloadCurrentClassifications();
      expect(
        component['_service'].downloadCurrentClassifications
      ).toHaveBeenCalledTimes(1);
    });
  });
});
