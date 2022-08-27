import { APP_BASE_HREF, CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import {
  ComponentFixture,
  fakeAsync,
  TestBed,
  tick,
} from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { MessageService } from 'primeng/api';
import { CalendarModule } from 'primeng/calendar';
import { of, Subscription } from 'rxjs';
import { ISimulationType } from 'src/app/shared/models/search-simulador';
import { SharedModule } from 'src/app/shared/shared.module';
import { SimulateService } from '../../services/simulate/simulate.service';

import { SimulationTypeComponent } from './simulation-type.component';

describe('SimulationTypeComponent', () => {
  let component: SimulationTypeComponent;
  let fixture: ComponentFixture<SimulationTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        SharedModule,
        FormsModule,
        ReactiveFormsModule,
        CommonModule,
        CalendarModule
      ],
      declarations: [SimulationTypeComponent],
      providers: [{ provide: APP_BASE_HREF, useValue: '/' }, MessageService, {
        provide: SimulateService, useValue: {
          startSimulation: (...args: any[]) => {throw new Error('This should be mocked');}
        }
      }],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SimulationTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    component.ngOnDestroy();
  });


  describe('Select simulation', () => {
    it('Deve navegar para a rota indicada baseado no tipo selecionado', () => {
      spyOn(component['_router'], 'navigate').and.returnValue({});
      spyOn(component['_simulationService'], 'startSimulation').and.returnValue(of({id: 0}));
      const mockSim: ISimulationType = {
        key: 'teste',
        id: 0,
        title: 'teste',
        description: 'teste',
        icon: 'teste',
      };
      component.selectType(mockSim);
      expect(component['_router'].navigate).toHaveBeenCalledTimes(1);
      component.ngOnDestroy();
    });
  });

  describe('Input de pesquisa', () => {
    it('Deve escutar as mudanças de valor no input e buscar apos um delay', fakeAsync(() => {
      spyOn(component, 'searchType').and.returnValue({});
      component.listenToSearch();
      expect(component.searchSub).toBeDefined();
      component.searchControl.setValue('a');
      tick(300);
      expect(component.searchType).toHaveBeenCalledTimes(1);
      component.searchControl.setValue('ab');
      tick(300);
      expect(component.searchType).toHaveBeenCalledTimes(2);
      component.ngOnDestroy();
    }));

    it('Não deve criar mais de uma subsc para o mesmo input', fakeAsync(() => {
      spyOn(component, 'searchType').and.returnValue({});
      component.listenToSearch();
      expect(component.searchSub).toBeDefined();
      component.listenToSearch();
      component.listenToSearch();
      component.listenToSearch();
      component.searchControl.setValue('a');
      tick(300);
      expect(component.searchType).toHaveBeenCalledTimes(1);
      component.ngOnDestroy();
    }));
  });

  describe('OnDestroy', () => {
    it('Deve destruir as suscriptions aberta ao chamar ngDestroy', () => {
      component.searchSub = new Subscription();
      expect(component.searchSub.closed).toEqual(false);
      component.ngOnDestroy();
      expect(component.searchSub.closed).toEqual(true);
    })
    

  })
});
