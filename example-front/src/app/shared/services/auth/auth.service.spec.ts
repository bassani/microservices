import { APP_BASE_HREF } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { KeycloakService } from 'keycloak-angular';
import { of } from 'rxjs';
import { Path } from '../../consts/path';
import { User } from '../../models/user.model';

import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      providers: [{ provide: APP_BASE_HREF, useValue: '/' }, AuthService, {
        provide: KeycloakService, useValue: {
          getUserRoles: (val: boolean) => ['basic', 'jdc']
        }
      }],
    });
    service = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('MenuItems', () => {
    it('Deve realizar um request para obter os menus', () => {
      spyOn(service['_http'], 'get').and.returnValue(of({}));
      service.menuItems().subscribe();
      expect(service['_http'].get).toHaveBeenCalledTimes(1);
      expect(service['_http'].get).toHaveBeenCalledWith(Path.HTTP_MENU_PATH);
    });
  });

  describe('Login', () => {
    it('Deve setar as credenciais de usuario e o token', () => {
      service.navigateTo = '/teste-navigate-origem';
      const mockCreds = { email: 'teste@teste.com', password: '1234567' };
      spyOn(service['_router'], 'navigateByUrl').and.returnValue({});
      service.login(mockCreds);
      expect(service.$user.value.email).toEqual(mockCreds.email);
      expect(service.$user.value.loggedIn).toEqual(true);
      expect(localStorage.getItem('token')).toEqual('44d12');
    });
    it('Deve navegar para a rota de origem se existir apos o login', () => {
      const mockNav = '/teste-navigate-origem';
      service.navigateTo = mockNav;
      const mockCreds = { email: 'teste@teste.com', password: '1234567' };
      spyOn(service['_router'], 'navigateByUrl').and.returnValue({});
      service.login(mockCreds);
      expect(service['_router'].navigateByUrl).toHaveBeenCalledWith(mockNav);
      expect(service['_router'].navigateByUrl).toHaveBeenCalledTimes(1);
    });

    it('Deve navegar para a rota de previa se a rota de origem não existir apos o login', () => {
      service.navigateTo = '';
      const mockNav = '/teste-prev';
      localStorage.setItem('prev', mockNav);
      const mockCreds = { email: 'teste@teste.com', password: '1234567' };
      spyOn(service['_router'], 'navigateByUrl').and.returnValue({});
      service.login(mockCreds);
      expect(service['_router'].navigateByUrl).toHaveBeenCalledWith(mockNav);
      expect(service['_router'].navigateByUrl).toHaveBeenCalledTimes(1);
    });
  });

  describe('Logout', () => {
    it('Deve limpar os dados do usuario e redirecionalo a tela de login', () => {
      const mock: User = new User({
        email: 'teste@teste.com',
        name: 'teste',
        loggedIn: true,
        token: '123',
        id: 1,
      });
      spyOn(service['_router'], 'navigateByUrl').and.returnValue({});
      service.$user.next(mock);
      expect(service.$user.value).toEqual(mock);
      service.logout();
      expect(service.$user.value.loggedIn).toEqual(false);
      expect(service.$user.value.token).toEqual(null);
    });
  });

  describe('Force login', () => {
    it('Deve forçar um login e deve salvar a url de origem', () => {
      const route = '/teste';
      spyOn(service['_router'], 'navigateByUrl').and.returnValue({});
      expect(service.navigateTo).not.toEqual(route);
      expect(service.force).toEqual(false);
      service.forceLogin(route);
      expect(service.force).toEqual(true);
      expect(service['_router'].navigateByUrl).toHaveBeenCalledWith('/login');
      expect(service['_router'].navigateByUrl).toHaveBeenCalledTimes(1);
      expect(service.navigateTo).toEqual(route);
    });
  });
});
