import { MessageService } from 'primeng/api';
import { IFlowExpiration } from './../../../shared/models/flow-expiration.model';
import { IExpirationParameters } from './../../../shared/models/expiration-parameters.model';
import { ExpirationService } from './../../../shared/services/expiration/expiration.service';
import { FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from "@angular/core";

@Component({
  selector: 'app-expiration',
  templateUrl: './expiration.component.html',
  styleUrls: ['./expiration.component.scss'],
})
export class ExpirationComponent implements OnInit {
  form: FormGroup;
  expirationFlowList: Array<IExpirationParameters> = [];

  constructor(
    private _fb: FormBuilder,
    private _service: ExpirationService,
    private _messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.getExpirationParameters();
  }

  getControl(lb: string): FormControl {
    return this.form.get(lb) as FormControl;
  }

  submit() {
    const flowApproval = this.form.get("flowApproval")?.value;
    const flowSimulate = this.form.get("flowSimulate")?.value;
    const description = this.form.get("description")?.value;

    if (!flowApproval && flowSimulate) this.setFlowSimulate(flowSimulate, description);

    if (flowApproval && !flowSimulate) this.setFlowApproval(flowApproval, description);

    if (flowApproval && flowSimulate) {
      this.setFlowApproval(flowApproval, description);
      this.setFlowSimulate(flowSimulate, description);
    }
  }

  setFlowSimulate(flowSimulate: IFlowExpiration, description: string) {
    const data = {
      flowId: "1",
      qtyExpirationDay: flowSimulate?.id,
      description,
    };

    this._service.setFlowParameters(data).subscribe(() => {
      this._messageService.add({
        severity: 'success',
        summary: 'Operação realizada com sucesso',
        detail: 'Fluxo de simulação criado com sucesso',
        key: 'main',
      });
      this.getExpirationParameters();
    }, () => {
      this._messageService.add({
        severity: 'error',
        summary: 'Erro ao cadastrar fluxo de simulação',
        detail: 'Fluxo de simulação não foi criado',
        key: 'main',
      });
    });
  }

  setFlowApproval(flowApproval: IFlowExpiration, description: string) {
    const data = {
      flowId: "2",
      qtyExpirationDay: flowApproval?.id,
      description,
    };

    this._service.setFlowParameters(data).subscribe(() => {
      this._messageService.add({
        severity: 'success',
        summary: 'Operação realizada com sucesso',
        detail: 'Fluxo de aprovação criado com sucesso',
        key: 'main',
      });
      this.getExpirationParameters();
    }, () => {
      this._messageService.add({
        severity: 'error',
        summary: 'Erro ao cadastrar fluxo de aprovação',
        detail: 'Fluxo de aprovação não foi criado',
        key: 'main',
      });
    });
  }

  initForm() {
    this.form = this._fb.group({
      flowSimulate: [null],
      flowApproval: [null],
      description: [null, Validators.required]
    });
  }

  getExpirationParameters() {
    this._service.getExpirationParameters().subscribe(res => {
      this.expirationFlowList = [];
      this._getExpirationParametersList(res);
    });
  }

  getFlowType(value: any) {
    if (value === "Fluxo de simulacao") return "Simulação";
    else return "Aprovação";
  }

  validDateFormat(value: number) {
    if (value === 1) return `${value} dia`;
    else return `${value} dias`;
  }

  getStatus(value?: boolean) {
    if (this.expirationFlowList.length !== 0) {
      this.expirationFlowList[0].status = true;
      this.expirationFlowList[1].status = true;
    }

    if (value) return 'ATIVO';
    else return 'DESATIVADO';
  }

  private _getExpirationParametersList(value: any) {
    if (value?.length !== 0) {
      let dataList = value;
      dataList.map((item: any) => {
        item.status = false
        this._service.getUsers([item.userId]).subscribe(res => {
          this.expirationFlowList.push({
            id: item.id,
            expirationFlow: item.expirationFlow.description,
            qtyExpirationDay: item.qtyExpirationDay,
            status: item.status,
            creationDateTime: item.creationDateTime,
            description: item.description,
            userId: res[0]?.firstName
          })
        });
      });
    }
  }
}
