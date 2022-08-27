import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';

import { IdSimulationComponent } from './id-simulation.component';

describe('IdSimulationComponent', () => {
  let component: IdSimulationComponent;
  let fixture: ComponentFixture<IdSimulationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IdSimulationComponent ],
      imports: [HttpClientModule],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IdSimulationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Deve retornar os IDs das simulações pendentes', () => {
    it('Deve listar os IDs das simulacoes', () => {
      const mock = [
        {
          id: "3915",
          name: "BLACK FRIDAY"
      },
      {
          id: "4105",
          name: "COLABORATIVA"
      }
      ];
      spyOn(component['idSimulationService'], 'searchIDs').and.returnValue(of(mock));
      expect(component['idSimulationService'].searchIDs).toHaveBeenCalledTimes(0);
    });
  });
});
