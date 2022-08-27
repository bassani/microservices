import { IFlowExpiration } from './../../../shared/models/flow-expiration.model';
import { MessageService } from 'primeng/api';
import { of, throwError } from 'rxjs';
import { ExpirationService } from './../../../shared/services/expiration/expiration.service';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ExpirationComponent } from './expiration.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

const RESPONSE_MOCK = {
  content: [
    {
      id: -46,
      expirationFlow: {
        id: 1,
        description: "Fluxo de simulacao"
      },
      qtyExpirationDay: 2,
      creationDateTime: "2022-05-26 17:09",
      description: "teste 3",
      userId: "f3755ed0-fbcb-4606-8957-7fae8db6cef6"
    },
  ],
  page: 0,
  size: 6,
  totalElements: 25,
  first: true,
  numberOfElements: 6,
  last: false,
  hasContent: true,
  totalPages: 5,
  pageable: {
    sort: {
      unsorted: true,
      sorted: false,
      empty: true
    },
    pageSize: 6,
    pageNumber: 0,
    offset: 0,
    paged: true,
    unpaged: false
  },
  empty: false
}

describe('ExpirationComponent', () => {
  let component: ExpirationComponent;
  let fixture: ComponentFixture<ExpirationComponent>;
  let expirationService: ExpirationService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        ReactiveFormsModule
      ],
      declarations: [ExpirationComponent],
      providers: [{
        provide: ExpirationService,
        useValue: {
          getExpirationParameters: () => of([]),
          setFlowParameters: () => of([])
        }
      },
        MessageService
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();
    expirationService = TestBed.inject(ExpirationService);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExpirationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('OnInit', () => {

    it('Should create a form', () => {
      component.form.setValue({
        flowSimulate: "1",
        flowApproval: "2",
        description: "description"
      });
      expect(component.form.valid).toEqual(true);
    });    
  });

  describe('should return valid format', () => {
    it('Should status valid', () => {
      const mock = true;
      expect(component.getStatus(mock)).toBeTruthy();
    });

    it('Should return the type flow when pass the value', () => {
      const mock = "Fluxo de simulacao";
      expect(component.getFlowType(mock)).toBeDefined();
    });

    it('Should format day valid ', () => {
      const mock = 1;
      expect(component.validDateFormat(mock)).toBeDefined();
    });
  });

  describe('should call the api the expiration-parameters', () => {

    it('Should success after call the api', () => {
      const response = RESPONSE_MOCK;
      spyOn(expirationService, 'getExpirationParameters').and.returnValue(of(response));
      component.getExpirationParameters();
      expect(expirationService.getExpirationParameters).toHaveBeenCalledTimes(1);
    });

    it('Should handle server errors', () => {
      const response = {
        status: 500,
        error: { message: 'Internal Unit Error' },
      };
      spyOn(expirationService, 'getExpirationParameters').and.returnValue(throwError(response));
      component.getExpirationParameters();
      expect(expirationService.getExpirationParameters).toHaveBeenCalledTimes(1);
    });
  });
}); 
