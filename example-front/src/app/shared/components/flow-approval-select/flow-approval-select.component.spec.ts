import { FlowApprovalComponent } from './flow-approval-select.component';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

describe('FlowApprovalComponent', () => {
  let component: FlowApprovalComponent;
  let fixture: ComponentFixture<FlowApprovalComponent>;


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [],
      declarations: [FlowApprovalComponent],
      providers: [],
      schemas: [
				CUSTOM_ELEMENTS_SCHEMA
			]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FlowApprovalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

	it('should contain value into the idFlowApproval', () => {
		const option = {
			label: '1 dia', id: '1'
		};
		expect(component.idFlowApproval).toContainEqual(option);
	});
});