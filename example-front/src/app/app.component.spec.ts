import { APP_BASE_HREF } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router, RouterStateSnapshot, RoutesRecognized } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { PrimeNGConfig } from 'primeng/api';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { AppComponent } from './app.component';
import { AuthService } from './shared/services';

describe('AppComponent', () => {
  let app: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
      declarations: [AppComponent],
      providers: [
        {provide: AuthService, useValue: {
          hydrate: (...args: any[]) => {}
        }},
        { provide: APP_BASE_HREF, useValue: '/' },
        PrimeNGConfig,

      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });
  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    app = fixture.componentInstance;
    fixture.detectChanges();
  });
  it('should create the app', () => {
    app.ngOnInit();
    expect(app).toBeTruthy();
  });

  it(`should have as title 'example-front'`, () => {
    expect(app.title).toEqual('example-front');
  });

  describe('Listen to route', () => {
    it('Deve escutar as alterações de rotas e salvar a ultima rota visitada', () => {
      const firstEvent = new RoutesRecognized(
        0,
        '/teste',
        '/afterRedirectTest',
        {
          url: 'snapshot',
        } as RouterStateSnapshot
      );
      const afterEvent = new RoutesRecognized(
        0,
        '/teste',
        '/firstAfterRedirect',
        {
          url: 'snapshot',
        } as RouterStateSnapshot
      );
      expect(localStorage.getItem('prev')).not.toEqual('/secondAfterRedirect');
      const behaviour = new BehaviorSubject<unknown>(firstEvent);
      app['_router'] = { events: behaviour as Observable<unknown> } as Router;
      app.listenRoutes();
      behaviour.next(afterEvent);
      expect(localStorage.getItem('prev')).toEqual(
        firstEvent.urlAfterRedirects
      );
      app.ngOnDestroy();
    });
  });
});
