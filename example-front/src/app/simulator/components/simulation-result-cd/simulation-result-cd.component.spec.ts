import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimulationResultCdComponent } from './simulation-result-cd.component';

describe('SimulationResultCdComponent', () => {
  let component: SimulationResultCdComponent;
  let fixture: ComponentFixture<SimulationResultCdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SimulationResultCdComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SimulationResultCdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
