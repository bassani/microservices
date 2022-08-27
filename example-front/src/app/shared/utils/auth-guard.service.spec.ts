import { APP_BASE_HREF } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import {
  ActivatedRouteSnapshot,
  RouterModule,
  RouterStateSnapshot,
  UrlSegment,
  ɵangular_packages_router_router_n,
} from '@angular/router';
import { KeycloakService } from 'keycloak-angular';
import { User } from '../models/user.model';
import { AuthService } from '../services';

import { AuthGuardService } from './auth-guard.service';

describe('AuthGuardService', () => {
  let service: AuthGuardService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterModule.forRoot([]), HttpClientModule],
      providers: [AuthService, { provide: APP_BASE_HREF, useValue: '/' }, {
        provide: KeycloakService, useValue: {isLoggedIn: () => true, login: () => {}, loadUserProfile: () => {return {}}}
      }],
    });
    service = TestBed.inject(AuthGuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('Is allowed access', () => {

    it('Deve redirecionar para o login caso esteja deslogado', async () => {
      service['authenticated'] = false;
      spyOn(service['keycloak'], 'login').and.returnValue({})
      const state = { url: '/teste' };
      const snapshot = new ActivatedRouteSnapshot();
      snapshot.data = { roles: [] };
      const r = await service.isAccessAllowed(
        snapshot,
        state as RouterStateSnapshot
      );
      expect(r).toEqual(false);
      expect(service['keycloak'].login).toHaveBeenCalledTimes(1);
      return true;
    })

    it('Deve retornar true se a rota não tiver roles requeridos', async () => {
      service['authenticated'] = true;
      const state = { url: '/teste' };
      const snapshot = new ActivatedRouteSnapshot();
      snapshot.data = { roles: [] };
      const r = await service.isAccessAllowed(
        snapshot,
        state as RouterStateSnapshot
      );
      expect(r).toEqual(true);
      return true;
    });

    it('Deve retornar true se o usuario tiver as permissões requeridas pela rota', async () => {
      service['authenticated'] = true;
      const snapshot = new ActivatedRouteSnapshot();
      snapshot.data = { roles: ['basic', 'stub'] };
      service['roles'] = ['basic', 'stub']
      const state = { url: '/teste' };
      const r = await service.isAccessAllowed(
        snapshot,
        state as RouterStateSnapshot
      );
      expect(r).toEqual(true);
      return true;
    });
    it('Não Deve retornar true se o usuario não tiver as permissões requeridas pela rota', async () => {
      service['authenticated'] = true;
      spyOn(service['router'], 'navigateByUrl').and.returnValue({});
      const snapshot = new ActivatedRouteSnapshot();
      service['roles'] = ['basic']
      snapshot.data = { roles: ['basic', 'stub'] };
      const state = { url: '/teste' };
      const r = await service.isAccessAllowed(
        snapshot,
        state as RouterStateSnapshot
      );
      expect(r).toEqual(false);
      expect(service['router'].navigateByUrl).toHaveBeenCalledTimes(1);
      expect(service['router'].navigateByUrl).toHaveBeenCalledWith('/401');
      return true;
    });
  });

  describe('Can active child', () => {
    it('Deve chamar a função de is allowed com os parametros recebidos e retornar false cuando resolver com false', async () => {
      let expectedResponse = false;
      spyOn(service, 'isAccessAllowed').and.returnValue(
        new Promise((resolve) => {
          resolve(expectedResponse);
        })
      );
      const snapshot = new ActivatedRouteSnapshot();
      snapshot.data = { roles: ['basic', 'stub'] };
      const state = { url: '/teste' };
      let response = await service.canActivateChild(
        snapshot,
        state as RouterStateSnapshot
      );
      expect(service.isAccessAllowed).toHaveBeenCalledTimes(1);
      expect(service.isAccessAllowed).toHaveBeenCalledWith(snapshot, state);
      expect(response).toEqual(false);
      return true;
    });

    it('Deve chamar a função de is allowed com os parametros recebidos e retornar true cuando resolver corretamente', async () => {
      let expectedResponse = true;
      spyOn(service, 'isAccessAllowed').and.returnValue(
        new Promise((resolve) => {
          resolve(expectedResponse);
        })
      );
      const snapshot = new ActivatedRouteSnapshot();
      snapshot.data = { roles: ['basic', 'stub'] };
      const state = { url: '/teste' };
      let response = await service.canActivateChild(
        snapshot,
        state as RouterStateSnapshot
      );
      expect(service.isAccessAllowed).toHaveBeenCalledTimes(1);
      expect(service.isAccessAllowed).toHaveBeenCalledWith(snapshot, state);
      expect(response).toEqual(true);
      return true;
    });
  });
});
