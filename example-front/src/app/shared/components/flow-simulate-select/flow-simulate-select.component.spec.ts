import { FlowSimulateComponent } from './flow-simulate-select.component';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

describe('FlowSimulateComponent', () => {
  let component: FlowSimulateComponent;
  let fixture: ComponentFixture<FlowSimulateComponent>;


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [],
      declarations: [FlowSimulateComponent],
      providers: [],
      schemas: [
				CUSTOM_ELEMENTS_SCHEMA
			]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FlowSimulateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should contain value into the idFlowSimulate', () => {
		const option = {
			label: '1 dia', id: '1'
		};
		expect(component.idFlowSimulate).toContainEqual(option);
	});
});