import { APP_BASE_HREF } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { MessageService } from 'primeng/api';

import { ForecastComponent } from './forecast.component';

describe('ForecastComponent', () => {
  let component: ForecastComponent;
  let fixture: ComponentFixture<ForecastComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterModule.forRoot([]), HttpClientModule],
      declarations: [ ForecastComponent ],
      providers: [{provide: APP_BASE_HREF, useValue: '/'}, MessageService],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ForecastComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  describe('Reprocessar', () => {
    it('Deve chamar o reprocessamento do serviço', () => {
      spyOn(component['config'], 'reprocess').and.returnValue({});
      component.type = 'mensal';
      component.getReprocess();
      expect(component['config'].reprocess).toHaveBeenCalledTimes(1);
      expect(component['config'].reprocess).toHaveBeenCalledWith('mensal');
    })
  })

  
  describe('Download de erros', () => {
    it('Deve chamar o download erros do serviço', () => {
      spyOn(component['config'], 'downloadErrors').and.returnValue({});
      component.type = 'semanal';
      component.getDownloadErrors();
      expect(component['config'].downloadErrors).toHaveBeenCalledTimes(1);
      expect(component['config'].downloadErrors).toHaveBeenCalledWith('semanal');
    })
  })

  
  describe('Download vigente', () => {
    it('Deve chamar o download vigente do serviço', () => {
      spyOn(component['config'], 'downloadCurrent').and.returnValue({});
      component.type = 'mensal';
      component.getDownloadCurrent();
      expect(component['config'].downloadCurrent).toHaveBeenCalledTimes(1);
      expect(component['config'].downloadCurrent).toHaveBeenCalledWith('mensal');
    })
  })
});
