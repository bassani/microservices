import { APP_BASE_HREF } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { NotAllowedComponent } from './not-allowed.component';

describe('NotAllowedComponent', () => {
  let component: NotAllowedComponent;
  let fixture: ComponentFixture<NotAllowedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NotAllowedComponent],
      imports: [RouterTestingModule],
      providers: [{ provide: APP_BASE_HREF, useValue: '/' }],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NotAllowedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Back', () => {
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
