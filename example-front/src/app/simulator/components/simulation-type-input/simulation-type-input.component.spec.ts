import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormControl, FormGroup } from '@angular/forms';

import { SimulationTypeInputComponent } from './simulation-type-input.component';

describe('SimulationTypeInputComponent', () => {
  let component: SimulationTypeInputComponent;
  let fixture: ComponentFixture<SimulationTypeInputComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SimulationTypeInputComponent ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SimulationTypeInputComponent);
    component = fixture.componentInstance;
    component.simulationType = {key: 'antecipacao', title: 'Antecipação', id: 1, description: '', icon: ''};
    component.form = new FormGroup({
      stockCoverageDC: new FormControl(0),
      anticipationDate: new FormControl(new Date()),
      amount: new FormControl(0),
    })
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
