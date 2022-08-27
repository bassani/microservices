import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { KeycloakService } from 'keycloak-angular';
import { MessageService } from 'primeng/api';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { of } from 'rxjs';
import { SharedModule } from '../../../shared/shared.module';
import { EditUserComponent } from '../../dialogs/edit-user/edit-user.component';

import { UserRegisterComponent } from './user-register.component';

describe('UserRegisterComponent', () => {
  let component: UserRegisterComponent;
  let fixture: ComponentFixture<UserRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserRegisterComponent],
      imports: [SharedModule, ReactiveFormsModule],
      providers: [
        MessageService,
        {
          provide: KeycloakService, useValue: {isLoggedIn: () => true, login: () => {}, loadUserProfile: () => {return {}}}
        },
        { provide: DynamicDialogRef, useValue: { close: () => {} } }
      ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Editar usuário', () => {
    it('Deve abrir o modal de editar usuário', () => {
      const mock = {
        email: 'teste@teste.com',
        fullName: '',
        registrationNumber: 5,
        areaCode: 1,
        activationStatus: false,
        phoneNumber: '',
        accessProfile: 5,
        vacationReturnDate: '',
        keycloakUserId: '',
        firstName: '',
        lastName: ''
      };
      spyOn(component['_dialog'], 'open').and.returnValue(of(mock));
      component.editOrAddUser(mock);
      expect(component['_dialog'].open).toHaveBeenCalledTimes(1);
      expect(component['_dialog'].open).toHaveBeenCalledWith(
        EditUserComponent,
        {
          data: {user: mock, cdPerfil: undefined},
          header: 'Editar Usuário',
        }
      );
    });
  });

  describe('Cadastrar usuário', () => {
    it('Deve abrir o modal de cadastrar usuário', () => {
      spyOn(component['_dialog'], 'open').and.returnValue(of(null));
      component.editOrAddUser(null);
      expect(component['_dialog'].open).toHaveBeenCalledTimes(1);
      expect(component['_dialog'].open).toHaveBeenCalledWith(
        EditUserComponent,
        {
          data: {user: null, cdPerfil: undefined},
          header: 'Cadastrar Usuário',
        }
      );
    });
  });

  describe('Limpar busca', () => {
    it('Deve limpar o filtro e realizar nova busca', () =>{
      component.filter = jest.fn();
      component.searchControl.setValue('Teste');
      component.clearSearch();
      expect(component.filter).toHaveBeenCalledTimes(1);
    });
  });


  describe('Filtrar lista', () => {
    it('Deve ser capaz de filtrar lista', () =>{
      component.changePage = jest.fn();
      component.page = 0;
      component.filter();
      expect(component.changePage).toHaveBeenCalledTimes(1);
    });
  });
});
