import { DialogService } from 'primeng/dynamicdialog';
import { RouterTestingModule } from '@angular/router/testing';
import { CalendarModule } from 'primeng/calendar';
import { SimulateFollowUpService } from './../../../shared/services/simulate-follow-up/simulate-follow-up.service';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ApprovalToSendComponent } from './approval-to-send.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';


describe('ApprovalToSendComponent', () => {
  let component: ApprovalToSendComponent;
  let fixture: ComponentFixture<ApprovalToSendComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        RouterTestingModule,
        HttpClientModule,
        CalendarModule
      ],
      declarations: [ApprovalToSendComponent],
      providers: [
        SimulateFollowUpService,
        DialogService
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApprovalToSendComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
})