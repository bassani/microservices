import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { KeycloakService } from 'keycloak-angular';
import { MessageService, SharedModule } from 'primeng/api';
import { DropdownModule } from 'primeng/dropdown';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { of, throwError } from 'rxjs';
import { ErrorHandler } from 'src/app/shared/utils/error-handler.service';
import { EditClassificationDialog } from './edit-classification.dialog';

describe('EditClassificationDialog', () => {
  let component: EditClassificationDialog;
  let fixture: ComponentFixture<EditClassificationDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        FormsModule,
        ReactiveFormsModule,
        SharedModule,
        HttpClientModule,
        DropdownModule,
        RouterTestingModule,
        
      ],
      declarations: [EditClassificationDialog],
      providers: [
        MessageService,
        { provide: DynamicDialogRef, useValue: { close: () => {} } },
        { provide: KeycloakService, useValue: { getUserRoles: (...args: any[]) => {return []} }},
        {
          provide: DynamicDialogConfig,
          useValue: {
            data: { id: 1, name: 'teste', description: 'teste', active: true },
          },
        },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditClassificationDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Deve fechar o modal com a referencia', () => {
    spyOn(component['ref'], 'close').and.returnValue({});
    component.closeModal();
    expect(component['ref'].close).toHaveBeenCalledTimes(1);
  });

  describe('Save classification', () => {
    it('Deve chamar o serviço e caso a resposta seja succes deve fechar o modal', () => {
      spyOn(component, 'saveClassification').and.callThrough();
      spyOn(component['_auth'], 'canExecuteAction').and.returnValue(of(['update_classification']));

      component.form.patchValue({
        id: 10,
        name: 'TESTE',
        description: 'TESTE DESCRIPTION',
        active: true,
      });
      spyOn(component['_service'], 'updateClassification').and.returnValue(
        of({ status: 200 })
      );
      spyOn(component, 'closeModal').and.returnValue({});
      fixture.debugElement.nativeElement
        .querySelector('#edit-classification-dialog-save')
        .click();
      fixture.detectChanges();
      expect(component.saveClassification).toHaveBeenCalledTimes(1);
      expect(component['_service'].updateClassification).toHaveBeenCalledTimes(
        1
      );
      expect(component.closeModal).toHaveBeenCalledTimes(1);
    });

    it('Não deve chamar o serviço salvar caso o usuario não tenha as permissões de alterar', () => {
      const error = {status: 401, error: {message: 'unauthorized'}};
      spyOn(component['_auth'], 'canExecuteAction').and.returnValue(throwError(error));
      spyOn(component, 'saveClassification').and.callThrough();
      spyOn(ErrorHandler, 'handleHttpError').and.returnValue(true)
      component.form.patchValue({
        id: 10,
        name: 'TESTE',
        description: 'TESTE DESCRIPTION',
        active: true,
      });
      spyOn(component['_service'], 'updateClassification').and.returnValue(
        of({ status: 200 })
      );
      spyOn(component, 'closeModal').and.returnValue({});
      fixture.debugElement.nativeElement
        .querySelector('#edit-classification-dialog-save')
        .click();
      fixture.detectChanges();
      expect(component.saveClassification).toHaveBeenCalledTimes(1);
      expect(ErrorHandler.handleHttpError).toHaveBeenCalledTimes(1);
      expect(component['_service'].updateClassification).toHaveBeenCalledTimes(
        0
      );
      expect(component.closeModal).toHaveBeenCalledTimes(0);    
    })
  });

  describe('Create classification', () => {
    it('Deve chamar o serviço e caso a resposta seja succes deve fechar o modal', () => {
      spyOn(component, 'addClassification').and.callThrough();
      spyOn(component['_auth'], 'canExecuteAction').and.returnValue(of(['create_classification']));

      spyOn(component['_service'], 'createClassification').and.returnValue(
        of({ status: 200 })
      );
      component.form.patchValue({
        id: null,
        name: 'Teste',
        description: 'Teste',
        active: true,
      });
      spyOn(component, 'closeModal').and.returnValue({});
      fixture.debugElement.nativeElement
        .querySelector('#edit-classification-dialog-save')
        .click();
      fixture.detectChanges();

      expect(component.addClassification).toHaveBeenCalledTimes(1);
      expect(component['_service'].createClassification).toHaveBeenCalledTimes(
        1
      );
      expect(component.closeModal).toHaveBeenCalledTimes(1);
    });
    it('Não deve chamar o serviço criar caso o usuario não tenha as permissões de criar', () => {
      const error = {status: 401, error: {message: 'unauthorized'}};
      spyOn(component['_auth'], 'canExecuteAction').and.returnValue(throwError(error));
      spyOn(component, 'addClassification').and.callThrough();
      spyOn(ErrorHandler, 'handleHttpError').and.returnValue(true)
      component.form.patchValue({
        id: null,
        name: 'TESTE',
        description: 'TESTE DESCRIPTION',
        active: true,
      });
      spyOn(component['_service'], 'createClassification').and.returnValue(
        of({ status: 200 })
      );
      spyOn(component, 'closeModal').and.returnValue({});
      fixture.debugElement.nativeElement
        .querySelector('#edit-classification-dialog-save')
        .click();
      fixture.detectChanges();
      expect(component.addClassification).toHaveBeenCalledTimes(1);
      expect(ErrorHandler.handleHttpError).toHaveBeenCalledTimes(1);
      expect(component['_service'].createClassification).toHaveBeenCalledTimes(
        0
      );
      expect(component.closeModal).toHaveBeenCalledTimes(0);    
    })
  });
});
