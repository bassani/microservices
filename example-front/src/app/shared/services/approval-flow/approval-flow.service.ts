import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { of, Subject } from 'rxjs';
import { Path } from '../../consts/path';
import { ApprovalFlow } from '../../models/approval-flow';
import { ApiService } from '../api/api.service';

@Injectable({
  providedIn: 'root',
})
export class ApprovalFlowService {

  mocks = [
    {
      id: 1,
      gain: 755378.2156,
      amount: 755378.2152,
      cashflowImpact: -55733.69,
      totalBudgetValue: 20000,
      note: 'Antecipação com risco Supply',
      resume: {
        approvals: [{area: 'Comercial', approver: null}, {area: 'Supply', approver: 'Coordenação - Ana'}],
        order: {
          classification: 'Antecipação com risco Supply',
          amount: 800000,
          supplierName: 'ELI LILLY TERMOLABIL',
          parentSupplierName: 'ELI LILLY',
          calculationBasis: {
            type: 0,
            days: 60,
          },
        },
        negotiation: {
          regularTermDays: 30,
          negotiatedTermDays: 30,
          deltaTermDays: 30,
          regularPayment: new Date(),
          negotiatedPayment: new Date(),
          cashflowImpact: -55733.69,
        },
        stock: {
          buyDays: 57,
          buyDaysCD: 50,
          currentStockDays: 50,
          buyDaysGrid: 77,
        },
        verb: {
          type: null,
          percent: 0,
        },
        counterPart: null,
        gain: 755378.2156,
      },
      indicators: {},
    },
  ];

  resultChartMock = [
    {
      month: '2021-06-01',
      cmv: 984166.3719,
      stockValue: 1788016.9185,
      cycle: 0,
      stockDays: 0,
    },
    {
      month: '2021-07-01',
      cmv: 901433.457,
      stockValue: 1755982.5488,
      cycle: 0,
      stockDays: 0,
    },
    {
      month: '2021-08-01',
      cmv: 951413.7684,
      stockValue: 1761207.349,
      cycle: 0,
      stockDays: 0,
    },
    {
      month: '2021-09-01',
      cmv: 986293.8132,
      stockValue: 2540539.1205,
      cycle: 0,
      stockDays: 0,
    },
    {
      month: '2021-10-01',
      cmv: 1154047.2164,
      stockValue: 2796193.6802,
      cycle: 0,
      stockDays: 0,
    },
    {
      month: '2021-11-01',
      cmv: 1226670.4592,
      stockValue: 2392932.4595,
      cycle: 0,
      stockDays: 0,
    },
    {
      month: '2021-12-01',
      cmv: 1405758.8246,
      stockValue: 3514545.4727,
      cycle: 0,
      stockDays: 0,
    },
    {
      month: '2022-01-01',
      cmv: 1339444.7203,
      stockValue: 3797677.7931,
      cycle: 0,
      stockDays: 0,
    },
    {
      month: '2022-02-01',
      cmv: 1369580.4661,
      stockValue: 3757670.2309,
      cycle: 0,
      stockDays: 0,
    },
    {
      month: '2022-03-01',
      cmv: 1360512.771,
      stockValue: 3746925.1452,
      cycle: 0,
      stockDays: 0,
    },
    {
      month: '2022-04-01',
      cmv: 1300265.0232,
      stockValue: 3768645.5346,
      cycle: 0,
      stockDays: 0,
    },
    {
      month: '2022-05-01',
      cmv: 1711522.8779,
      stockValue: 5575436.4945,
      cycle: 0,
      stockDays: 0,
    },
    {
      month: '2022-06-01',
      cmv: 1351194.6726,
      stockValue: 4958245.5852,
      cycle: 0,
      stockDays: 0,
    },
  ];

  /**
   * Uses the http client and api service to abstract and create http requests
   * @param _http http client injected by the module or resolved at construction
   * @param _api api abstraction to more easily mock request on demand, injected by the module
   */

  constructor(
    private _api: ApiService,
    private _http: HttpClient
  ) { }

  getTemplate(simulationId: any) {
    return this._api.request<any>({
      mock: false,
      mocker: of({...this.mocks[0], id: simulationId}),
      http: this._http.get(`${Path.HTTP_API_BASE}/simulations/${simulationId}/template`, {})
    })
  }

  getTemplateStatus(simulationId: any) {
    return this._api.request<any>({
      mock: false,
      mocker: of({...this.mocks[0], id: simulationId}),
      http: this._http.get(`${Path.HTTP_API_BASE}/simulations/${simulationId}/status`, {})
    })
  }

  getTemplateSummary(simulationId: any){
    return this._api.request<any>({
      mock: false,
      mocker: of({...this.mocks[0], id: simulationId}),
      http: this._http.get(`${Path.HTTP_API_BASE}/simulations/${simulationId}/order-summary`, {})
    })
  }

  getcashCicle(simulationId: any){
    return this._api.request<any>({
      mock: false,
      mocker: of({...this.mocks[0], id: simulationId}),
      http: this._http.get(`${Path.HTTP_API_BASE}/simulations/${simulationId}/cash-cycle`, {})
    })
  }

  startApproval(simulationId: any) {
    return this._api.request<any>({
      mock: false,
      mocker: of({businessId: '1'}),
      http: this._http.post(`${Path.HTTP_API_BASE}/approval/${simulationId}/start`, {})
    })
  }

  approvalComplete(simulationId: any, reason: any) {
    let body =
    {
      approved: false,
      reason: reason.reasonRefuse
    }
    if(reason.checkedApprove){
      body ={
        approved: true,
        reason: null
      }
    }
    return this._api.request<any>({
      mock: false,
      mocker: of({businessId: '1'}),
      http: this._http.post(`${Path.HTTP_API_BASE}/approval/${simulationId}/complete`, body)
    })
  }

  getChart(simulationId: any) {
    return this._api.request<any>({
      mock: false,
      mocker: of(this.resultChartMock),
      http: this._http.get(
        `${Path.HTTP_API_BASE}/databi/${simulationId}/chart`,
        {}
      ),
    });
  }
}
