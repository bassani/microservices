import { APP_BASE_HREF } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { NotFoundComponent } from './not-found.component';

describe('NotFoundComponent', () => {
  let component: NotFoundComponent;
  let fixture: ComponentFixture<NotFoundComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NotFoundComponent],
      imports: [RouterTestingModule],
      providers: [{ provide: APP_BASE_HREF, useValue: '/' }],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NotFoundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Back', () => {
    it('Deve retornar a rota anterior do localstorage', () => {
      //Setar rota no localstorage
      const prev = '/teste-rota';
      localStorage.setItem('prev', prev);
      //Spy router
      spyOn(component['_router'], 'navigateByUrl').and.returnValue({});

      //Execute and check
      component.back();
      expect(component['_router'].navigateByUrl).toHaveBeenCalledTimes(1);
      expect(component['_router'].navigateByUrl).toHaveBeenCalledWith(prev);
    });

    it('Deve retornar a rota inicial caso não tenha rota no localstorage', () => {
      //Setar rota no localstorage
      const mainRoute = '/';
      localStorage.removeItem('prev');
      //Spy router
      spyOn(component['_router'], 'navigateByUrl').and.returnValue({});

      //Execute and check
      component.back();
      expect(component['_router'].navigateByUrl).toHaveBeenCalledTimes(1);
      expect(component['_router'].navigateByUrl).toHaveBeenCalledWith(
        mainRoute
      );
    });
  });
});
