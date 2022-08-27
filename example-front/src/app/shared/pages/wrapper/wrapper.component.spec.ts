import { MOCK_NOTIFICATIONS } from './../../utils/mock';
import { APP_BASE_HREF } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { KeycloakService } from 'keycloak-angular';
import { of } from 'rxjs';
import { MOCK_CUSTOM_PAGED } from '../../utils/mock';

import { WrapperComponent } from './wrapper.component';
import { SharedModule } from 'primeng/api';
import { NgVarDirective } from 'src/app/shared/directives/ng-var.directive';

describe('WrapperComponent', () => {
  let component: WrapperComponent;
  let fixture: ComponentFixture<WrapperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule, SharedModule],
      declarations: [WrapperComponent, NgVarDirective],
      providers: [{
        provide: KeycloakService,
        useValue: {
          loadUserProfile: () => {return new Promise(resolve => {
            resolve({name: 'teste', email: 'teste@teste.com', id: 0, token: '123'})
          })},
          logout: () => {}
        }
      },
      { provide: APP_BASE_HREF, useValue: '/' }],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WrapperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    let result = new Promise(resolve => {
      resolve({name: 'teste', email: 'teste@teste.com', id: 0, token: '123'})
    })
    spyOn(component['kc'], 'loadUserProfile').and.returnValue(result)
    expect(component).toBeTruthy();
    component.ngOnInit();
    expect(component['kc'].loadUserProfile).toHaveBeenCalledTimes(1);
  });


  describe('Nav To', () => {
    it('Deve navegar ate a rota indicada', () => {
      const _route = '/teste';
      spyOn(component['_router'], 'navigateByUrl').and.returnValue({});
      component.navTo(_route);
      expect(component['_router'].navigateByUrl).toHaveBeenCalledTimes(1);
      expect(component['_router'].navigateByUrl).toHaveBeenCalledWith(_route);
    });
  });

  describe('Menu Items', () => {
    it('Deve deslogar o usuario quando clickar em logout', () => {
      spyOn(component['kc'], 'logout').and.returnValue({});
      //@ts-ignore
      component.userMenu[0].command();
      expect(component['kc'].logout).toHaveBeenCalledTimes(1);
    });
  });

  describe('Deve validar o modal das notificações', () => {
    it('deve validar se o modal está fechado', () => {
      const mock = MOCK_NOTIFICATIONS;
      component.ngOnInit();
      expect(component.modalNotification).toEqual(false);
      spyOn(component['notificationService'], 'getMessages').and.returnValue(of(mock));
      component.loadNotification(0, 10, 1);
      expect(component['notificationService'].getMessages).toHaveBeenCalledTimes(1);
    });
  });

  describe('Deve listar as notificações', () => {
    it('Deve enviar os dados para o serviço', () => {
      const mock = MOCK_NOTIFICATIONS;
      spyOn(component['notificationService'], 'getMessages').and.returnValue(of(mock));
    });
  });

  describe('Pagination', () => {
    it('Deve listar as notificações de acordo com a paginação informada', () => {
      const mock = MOCK_CUSTOM_PAGED(
        { name: 'Teste', description: 'Teste', active: true },
        10
      );
      component.notificationsPagination$.next({ ...mock, content: [] });
      spyOn(component['notificationService'], 'getPagination').and.returnValue(of(mock));
      expect(component.notificationsPagination$.value.content).toHaveLength(0);
      component.changePage(0, 10, 1);
      expect(component['notificationService'].getPagination).toHaveBeenCalledTimes(1);
      expect(component['notificationService'].getPagination).toHaveBeenCalledWith(0, 10, 1);
      component.changePage(4, 20, 1);
      expect(component['notificationService'].getPagination).toHaveBeenCalledTimes(2);
      expect(component['notificationService'].getPagination).toHaveBeenCalledWith(4, 20, 1);
    });

    it('Deve listar as notificações ordenadas', () => {
      expect(component.notificationsPagination$.value.content).toStrictEqual(component.notificationsPagination$.value.content.sort(component.sortNotification))
    });
  });
});
