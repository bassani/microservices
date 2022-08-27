import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../../shared/shared.module';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { RippleModule } from 'primeng/ripple';

import { EditUserComponent } from './edit-user.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { DropdownModule } from 'primeng/dropdown';
import { KeycloakService } from 'keycloak-angular';
import { MessageService } from 'primeng/api';
import { DynamicDialogRef, DynamicDialogConfig } from 'primeng/dynamicdialog';
import { of, throwError } from 'rxjs';
import { ErrorHandler } from 'src/app/shared/utils/error-handler.service';

describe('EditUserComponent', () => {
  let component: EditUserComponent;
  let fixture: ComponentFixture<EditUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditUserComponent],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        SharedModule,
        HttpClientModule,
        DropdownModule,
        RouterTestingModule,
      ],
      providers: [
        MessageService,
        { provide: DynamicDialogRef, useValue: { close: () => {} } },
        { provide: KeycloakService, useValue: { getUserRoles: (...args: any[]) => {return []} }},
        {
          provide: DynamicDialogConfig,
          useValue: {
            data: {user: {email: 'teste@exemplo.com'}, cdPerfil: undefined },
          },
        },
      ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditUserComponent);
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

  describe('Criar usuário', () => {
    it('Deve chamar o serviço e caso a resposta seja succes deve fechar o modal', () => {
      spyOn(component, 'addUser').and.callThrough();
      spyOn(component['_auth'], 'canExecuteAction').and.returnValue(of(['ROLE_USERS_REGISTRATION_SAVE']));

      spyOn(component['usersService'], 'createUser').and.returnValue(
        of({ status: 200 })
      );
      component.form.patchValue({
        email: 'teste@email.com'
      });
      spyOn(component, 'closeModal').and.returnValue({});
      fixture.debugElement.nativeElement
        .querySelector('#confirm-button-save')
        .click();
      fixture.detectChanges();

      expect(component.addUser).toHaveBeenCalledTimes(1);
      expect(component['usersService'].createUser).toHaveBeenCalledTimes(
        1
      );
      expect(component.closeModal).toHaveBeenCalledTimes(1);
    });

    it('Deve chamar manter o dialogo aberto e mostrar o erro', () => {
      const error = {status: 401, error: {message: 'unauthorized'}};
      spyOn(component, 'addUser').and.callThrough();
      spyOn(component['_auth'], 'canExecuteAction').and.returnValue(of(['ROLE_USERS_REGISTRATION_SAVE']));

      spyOn(component['usersService'], 'createUser').and.returnValue(
        throwError(error)
      );
      component.form.patchValue({
        email: 'teste@email.com'
      });
      spyOn(component, 'closeModal').and.returnValue({});
      fixture.debugElement.nativeElement
        .querySelector('#confirm-button-save')
        .click();
      fixture.detectChanges();

      expect(component.addUser).toHaveBeenCalledTimes(1);
      expect(component['usersService'].createUser).toHaveBeenCalledTimes(
        1
      );
      expect(component.closeModal).toHaveBeenCalledTimes(0);
    });
  });

  describe('Editar usuário', () => {
    it('Deve chamar o serviço e caso a resposta seja succes deve fechar o modal', () => {
      const error = {status: 401, error: {message: 'unauthorized'}};
      
      spyOn(component, 'editUser').and.callThrough();
      component.keyCloakUserId = "12345";
      spyOn(component['_auth'], 'canExecuteAction').and.returnValue(of(['ROLE_USERS_REGISTRATION_SAVE']));
      component.form.patchValue({
        email: 'teste@email.com.br'
      });
      
      spyOn(component['usersService'], 'updateUser').and.returnValue(
        throwError(error)
      );
      
      component.confirmButton();

      spyOn(component, 'closeModal').and.returnValue({});
      expect(component.form.valid).toEqual(true);
      expect(component.editUser).toHaveBeenCalledTimes(1);
      expect(component['usersService'].updateUser).toHaveBeenCalledTimes(
        1
      );
    });

    it('Deve chamar apresentar erros ao editar usuario', () => {
      spyOn(component, 'editUser').and.callThrough();
      component.keyCloakUserId = "12345";
      spyOn(component['_auth'], 'canExecuteAction').and.returnValue(of(['ROLE_USERS_REGISTRATION_SAVE']));
      component.form.patchValue({
        email: 'teste@email.com.br'
      });
      
      spyOn(component['usersService'], 'updateUser').and.returnValue(
        of({ status: 200 })
      );
      
      component.confirmButton();

      spyOn(component, 'closeModal').and.returnValue({});
      expect(component.form.valid).toEqual(true);
      expect(component.editUser).toHaveBeenCalledTimes(1);
      expect(component['usersService'].updateUser).toHaveBeenCalledTimes(
        1
      );
    });
  });

  describe('Positions', () => {
    it('Deve buscar uma lista de cargos', () => {
      let mock = {name: 'teste', id: '1'};
      spyOn(component['usersService'], 'getAllPositions').and.returnValue(of(mock));
      component.getPositions();
      expect(component['usersService'].getAllPositions).toHaveBeenCalledTimes(1);
      expect(component.positions$.value).toEqual(mock)
    })

    it('Deve dar erro ao buscar uma lista de cargos', () => {
      const error = {status: 401, error: {message: 'unauthorized'}};
      spyOn(component['usersService'], 'getAllPositions').and.returnValue(throwError(error));
      component.getPositions();
      expect(component['usersService'].getAllPositions).toHaveBeenCalledTimes(1);
      expect(component.positions$.value).toEqual([])
    })

    it('Deve buscar o cargo do usuário', () => {
      let mock = {name: 'teste', id: '1'};
      spyOn(component['usersService'], 'getPositionUser').and.returnValue(of(mock));
      component.getUserPosition();
      expect(component['usersService'].getPositionUser).toHaveBeenCalledTimes(1);
      expect(component.form.get('groupId')?.value).toEqual(mock.id)
    })
  })

  describe('Conversão data', () => {
    it('Deve converter a data', () => {
      const data = '2022-03-03';
      const dataReversa = '03/03/2022'
      component.config.data.user.vacationReturnDate = data;
      const cast = component.getDate();
      expect(cast).toEqual(dataReversa);
    })
  })
  
});
