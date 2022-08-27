import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimulationTypeLabelsComponent } from './simulation-type-labels.component';

describe('SimulationTypeLabelsComponent', () => {
  let component: SimulationTypeLabelsComponent;
  let fixture: ComponentFixture<SimulationTypeLabelsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SimulationTypeLabelsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SimulationTypeLabelsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
