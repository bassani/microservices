import { APP_BASE_HREF } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router, RouterModule } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { subscribeOn } from 'rxjs/operators';
import { AuthService } from '../../services';

import { SidebarComponent } from './sidebar.component';

describe('SidebarComponent', () => {
  let component: SidebarComponent;
  let fixture: ComponentFixture<SidebarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
      declarations: [SidebarComponent],
      providers: [
        {provide: AuthService, useValue: {
          menuItems: () => of([
           { 
              name: 'teste',
              icon: 'pi-test',
              url: '/teste',
              items: []
            }
          ])
        }},
        { provide: APP_BASE_HREF, useValue: '/' },
        { provide: Router, useValue: { url: '/teste/nested' } },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Load menu', () => {
    it('Deve Obter e formatar os menus do auth service', () => {
      spyOn(component, 'checkActiveState').and.returnValue(false);
      const mockMenuResponse = [...Array(5)].map((e, i) => {
        return {
          label: `Menu ${i + 1}`,
          icon: `${i + 1}`,
          items: [...Array(3)].map((ee, ii) => {
            return {
              label: `Menu ${ii + 1}`,
              icon: i % 2 ? `${ii + 1}` : null,
              url: `/${i}/${ii}`,
            };
          }),
        };
      });
      const mockMenuFinal = [...Array(5)].map((e, i) => {
        return {
          label: `Menu ${i + 1}`,
          icon: `${i + 1}`,
          expanded: false,
          items: [...Array(3)].map((ee, ii) => {
            return {
              label: `Menu ${i + 1}`,
              icon: i % 2 ? `${ii + 1}` : `${i + 1}`,
              routerLink: `${i}/${ii}`,
            };
          }),
        };
      });
      spyOn(component['_auth'], 'menuItems').and.returnValue(
        of(mockMenuResponse)
      );
      component.loadMenu();
      component.$menu.subscribe((data) => {
        expect(data).toEqual(mockMenuFinal);
      });
    });

    it('Deve funcionar com menus de 1 nivel', () => {
      spyOn(component, 'checkActiveState').and.returnValue(false);
      const mockMenuResponse = [...Array(5)].map((e, i) => {
        return {
          label: `Menu ${i + 1}`,
          icon: `${i + 1}`,
          items: null,
        };
      });
      const mockMenuFinal = [...Array(5)].map((e, i) => {
        return {
          label: `Menu ${i + 1}`,
          icon: `${i + 1}`,
          items: null,
          expanded: false,
        };
      });
      spyOn(component['_auth'], 'menuItems').and.returnValue(
        of(mockMenuResponse)
      );
      component.loadMenu();
      component.$menu.subscribe((data) => {
        expect(data).toEqual(mockMenuFinal);
      });
    });
  });

  describe('Check can activate', () => {
    it('Deve verificar se um menu tem subrotas com o link do router atual', () => {
      const mock = [
        { url: '/test/nested' },
        { url: '/teste' },
        { url: '/rota2' },
      ];
      expect(component.checkActiveState(mock)).toEqual(true);
    });

    it('Deve retornar false em caso de que as subrotas não estejam na url do router', () => {
      const mock = [
        { url: '/noRoute' },
        { url: '/should/false' },
        { url: '/rota2' },
      ];
      expect(component.checkActiveState(mock)).toEqual(false);
    });
  });
});
