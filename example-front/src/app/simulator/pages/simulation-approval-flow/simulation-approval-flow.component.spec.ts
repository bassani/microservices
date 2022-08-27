import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { KeycloakService } from 'keycloak-angular';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ChartModule } from 'primeng/chart';
import { TabViewModule } from 'primeng/tabview';
import { of, throwError } from 'rxjs';

import { SimulationApprovalFlowComponent } from './simulation-approval-flow.component';

describe('SimulationApprovalFlowComponent', () => {
  let component: SimulationApprovalFlowComponent;
  let fixture: ComponentFixture<SimulationApprovalFlowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ButtonModule, CardModule, TabViewModule, HttpClientModule, RouterTestingModule, ReactiveFormsModule, FormsModule],
      declarations: [ SimulationApprovalFlowComponent ],
      providers: [MessageService,
        {
          provide: KeycloakService, useValue: {isLoggedIn: () => true, login: () => {}, loadUserProfile: () => {return {}}}
        },
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({
              simulationID: 2,
            }),
          },
        }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SimulationApprovalFlowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Init', () => {

    it('Deve criar o form', () => {
      spyOn(component, 'createForm').and.callThrough();
      component.ngOnInit();
      expect(component.reasonForm.valid).toEqual(false);
      expect(component.approveForm.valid).toEqual(true);
    });

    it('Deve redirecionar para o home quando o id informado no param for invalido', () => {
      spyOn(component['router'], 'navigateByUrl').and.returnValue({});
      component['_ar'] = {paramMap: of({get: (...args: any[]) => null})} as unknown as ActivatedRoute;
      component.ngOnInit();
      fixture.detectChanges();
      expect(component['router'].navigateByUrl).toHaveBeenCalledTimes(0);
      // expect(component['router'].navigateByUrl).toHaveBeenCalledWith('/');
    })

    it('Deve setar o valor da simulação quando o id informado no param for valido', () => {
      let mockResponse = {id: 11, value: 'teste'};
      spyOn(component['router'], 'navigateByUrl').and.returnValue({});
      expect(component.result).not.toEqual(mockResponse);
      spyOn(component['_approvalFlowService'], 'getTemplate').and.returnValue(of(mockResponse));
      expect(component.resultChart).not.toEqual(mockResponse);
      spyOn(component['_approvalFlowService'], 'getChart').and.returnValue(of(mockResponse));
      component['_ar'] = {paramMap: of({get: (...args: any[]) => (1)})} as unknown as ActivatedRoute;
      component.ngOnInit();
      fixture.detectChanges();
      expect(component['router'].navigateByUrl).toHaveBeenCalledTimes(0);
      expect(component['_approvalFlowService'].getTemplate).toHaveBeenCalledTimes(0);
      // expect(component.result).toEqual(mockResponse);
      // expect(component.resultChart).toEqual(mockResponse);
    })

  })

  describe('Voltar ao fluxo', () => {
    it('Deve chamar ao router para voltar ao fluxo', () => {
      spyOn(component['router'], 'navigate').and.returnValue({});
      component.toResume();
      expect(component['router'].navigate).toHaveBeenCalledTimes(1);
    })
  })


  describe('Completar simulação', () => {
    it('Deve chamar ao serviço para aprovar a simulação',() => {
      spyOn(component['_approvalFlowService'], 'approvalComplete').and.returnValue(of({success: true}));
      component.simulationID = 1;
      component.completeStatus("aprovado");
      expect(component['_approvalFlowService'].approvalComplete).toHaveBeenCalledWith(1, {"checkedApprove": false})
    });

    it('Deve chamar ao serviço para reprovar a simulação',() => {
      spyOn(component['_approvalFlowService'], 'approvalComplete').and.returnValue(of({success: true}));
      component.simulationID = 1;
      component.reasonForm.patchValue({reasonRefuse: "Motivo"})
      component.completeStatus("reprovado");
      expect(component['_approvalFlowService'].approvalComplete).toHaveBeenCalledWith(1, {"reasonRefuse": "Motivo"})
    })
  });

  describe('Deve inicializar os modais', () => {
    it('Inicializa o modal de aprovação',() => {
      component.approve();
      expect(component.displayApprove).toEqual(true);
    });
    it('Inicializa o modal de reprovação',() => {
      component.refuse();
      expect(component.displayReason).toEqual(true);
    });
  });

  describe('Start da aprovação', () => {
    it('Deve chamar ao serviço de start da aprovação',() => {
      spyOn(component['_approvalFlowService'], 'startApproval').and.returnValue(of({success: true}));
      component.simulationID = 1;
      component.startApprovalProcess();
      expect(component['_approvalFlowService'].startApproval).toHaveBeenCalledWith(1)
    })

    it('Deve realizar o handling de erros', () => {
      let mockError = {error: {description: 'ERRORMOCK'}};
      spyOn(component['message'], 'add').and.returnValue({})

      spyOn(component['_approvalFlowService'], 'startApproval').and.returnValue(throwError(mockError));
      component.simulationID = 1;
      component.startApprovalProcess();
      expect(component['_approvalFlowService'].startApproval).toHaveBeenCalledWith(1);
      expect(component['message'].add).toHaveBeenCalledWith({key: 'main', severity: 'error', summary: 'Falha no envio', detail: mockError.error.description, life: 3000})
    })

    it('Deve realizar o handling de erros inesperados', () => {
      let mockError = {error: {description: undefined}};
      spyOn(component['message'], 'add').and.returnValue({})
      spyOn(component['_approvalFlowService'], 'startApproval').and.returnValue(throwError(mockError));
      component.simulationID = 1;
      component.startApprovalProcess();
      expect(component['_approvalFlowService'].startApproval).toHaveBeenCalledWith(1);
      expect(component['message'].add).toHaveBeenCalledWith({key: 'main', severity: 'error', summary: 'Falha no envio', detail: 'Ocorreu uma falha no envio para a aprovação. Tente novamente mais tarde.', life: 3000})
    })
  })
});
