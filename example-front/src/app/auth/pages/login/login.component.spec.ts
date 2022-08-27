import { APP_BASE_HREF, CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MessageService } from 'primeng/api';
import { PasswordModule } from 'primeng/password';
import { AuthService } from 'src/app/shared/services';
import { SharedModule } from 'src/app/shared/shared.module';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        FormsModule,
        RouterModule.forRoot([]),
        HttpClientModule,
        SharedModule,
        CommonModule,
        PasswordModule,
      ],
      declarations: [LoginComponent],
      providers: [
        { provide: APP_BASE_HREF, useValue: '/' },
        {provide: AuthService, useValue: {
          login: (...args: any[]) => {throw new Error('This should be mocked')}
        }},
        MessageService,
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Formulario de login', () => {
    it('Deve validar email e senha validos', () => {
      component.ngOnInit();
      expect(component.form.valid).toEqual(false);

      // Somente senha
      component.form.setValue({ password: '123456789', email: '' });
      expect(component.form.valid).toEqual(false);

      // Senha e emails validos
      component.form.setValue({
        password: '123456789',
        email: 'teste@teste.com',
      });
      expect(component.form.valid).toEqual(true);

      // Senha valida, email invalido
      component.form.setValue({ password: '123456789', email: 'teste@' });
      expect(component.form.valid).toEqual(false);

      // Senha invalida, email valido
      component.form.setValue({ password: '', email: 'teste@teste.com' });
      expect(component.form.valid).toEqual(false);
    });

    it('Não deve consultar o serviço de login se o form não for valido e deve informar o usuario do erro', () => {
      component.ngOnInit();
      component.form.setValue({ password: '', email: '' });
      spyOn(component['_auth'], 'login').and.returnValue({});
      spyOn(component['messageService'], 'add').and.returnValue({});
      expect(component.form.valid).toEqual(false);
      component.login();
      expect(component['_auth'].login).toHaveBeenCalledTimes(0);
      expect(component['messageService'].add).toHaveBeenCalledTimes(1);
    });

    it('Deve chamar o serviço de login com as credenciais caso o form for valido', () => {
      component.ngOnInit();
      const mockCreds = { password: '123456', email: 'teste@teste.com' };
      component.form.setValue(mockCreds);
      spyOn(component['_auth'], 'login').and.returnValue({});
      spyOn(component['messageService'], 'add').and.returnValue({});
      expect(component.form.valid).toEqual(true);
      component.login();
      expect(component['_auth'].login).toHaveBeenCalledTimes(1);
      expect(component['_auth'].login).toHaveBeenCalledWith(mockCreds);
      expect(component['messageService'].add).toHaveBeenCalledTimes(0);
    });
  });
});
