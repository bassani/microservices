import { Component, OnInit } from '@angular/core';
import { ApprovalFlowService } from 'src/app/shared/services/approval-flow/approval-flow.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CurrencyPipe, DatePipe, DecimalPipe } from '@angular/common';
import { MessageService } from 'primeng/api';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';

import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl,
} from '@angular/forms';

@Component({
  selector: 'app-simulation-approval-flow',
  templateUrl: './simulation-approval-flow.component.html',
  styleUrls: ['./simulation-approval-flow.component.scss'],
  providers: [DatePipe, CurrencyPipe],
})
export class SimulationApprovalFlowComponent implements OnInit {
  loading: boolean = false;
  reasonForm: FormGroup;
  approveForm: FormGroup;
  basicData: any;
  basicOptions: any = {
    height: '230px',
    scales: {
      yAxes: [
        {
          id: 'R$',
          type: 'linear',
          position: 'left',
          ticks: {
            min: 0,
            callback: function (value: any, index: any, ticks: any) {
              let decimalPipe = new DecimalPipe('pt-BR');
              return decimalPipe.transform(value, '1.2-2');
            },
          },
        },
        {
          id: 'dias',
          type: 'linear',
          position: 'right',
          ticks: {
            min: 0,
          },
        },
      ],
    },
    tooltips: {
      callbacks: {
        label: function (tooltipItem: any, data: any) {
          let label = data.datasets[tooltipItem.datasetIndex].label;
          let value =
            data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
          let currencyPipe = new CurrencyPipe('pt-BR', 'BRL');
          return (
            `${label}: ` +
            (tooltipItem.datasetIndex < 2
              ? `${value}`
              : currencyPipe.transform(value))
          );
        },
      },
    },
    legend: {
      position: 'bottom',
    },
    ticks: {
      source: 'data',
    },
  };
  result: any = {};
  resultSummary: any = {};
  resultChart: any = {};
  resultStatus: any ={};
  resultStatusPendente: any;
  resultArea: any;
  simulationID: any;
  approver: boolean;
  displayReason: boolean = false;
  displayApprove: boolean = false;
  checkedApprove: boolean = false;
  completeSimulation: any;
  disableCompleteButtons: boolean = false;
  tipoArea: string;

  constructor(
    private _approvalFlowService: ApprovalFlowService,
    private _ar: ActivatedRoute,
    private router: Router,
    private datePipe: DatePipe,
    private currencyPipe: CurrencyPipe,
    private message: MessageService,
    protected readonly keycloak: KeycloakService,
    private _fb: FormBuilder,
    private _message: MessageService,
  ) {}

  /**
   * Ao inicializar o componente, pesquisa as informações de template no serviço
   * e preenche os mocks do grafico
   */
  async ngOnInit(): Promise<void> {
    this.createForm();
    const user = await this.keycloak.getKeycloakInstance().loadUserInfo();
    type ObjectKeyArea = keyof typeof user;
    const cd_area = 'CD_AREA' as ObjectKeyArea;
    const area = user[cd_area];
    if(area == 1){
      this.tipoArea = "Abastecimento";
    }else{
      this.tipoArea = "Comercial";
    }

    type ObjectKey = keyof typeof user;
    const groups = 'groups' as ObjectKey;
    const userGroup: string = user[groups][0];

    this._ar.paramMap.subscribe((data) => {
      this.simulationID = data.get('simulationId');
      if (!this.simulationID) {
        this.router.navigateByUrl('/');
        return;
      }
      this._approvalFlowService
        .getTemplate(this.simulationID)
        .subscribe((response) => {
          this.result = response;
        });
      this._approvalFlowService
        .getTemplateStatus(this.simulationID)
        .subscribe((status) => {
          this.resultStatus = status.name;
          this.resultStatusPendente = typeof this.resultStatus === 'string' ? this.resultStatus.startsWith('Pendente') : false;
          if(userGroup){
            const perfil = userGroup.replace("/", '');
            if(perfil === "Analista" || perfil === "Comprador"){
              this.approver = false;
            }else{
              if(!this.resultStatusPendente){
                this.approver = false;
              }else{
                if(perfil === "Coordenador"){
                  this.resultArea = this.resultStatus.split(' ').pop();
                  if(this.tipoArea == this.resultArea){
                    this.approver = true;
                  }else{
                    this.approver = false;
                  }
                }else{
                  this.approver = true;
                }
              }
            }
          }
        });
    });
  }

  /**
   * Cria o formulario com os filtros do modal
   */
   createForm() {
    this.reasonForm = this._fb.group(
      {
        reasonRefuse: [null, Validators.required]
      },
    );
    this.approveForm = this._fb.group(
      {
        checkedApprove: this.checkedApprove
      },
    );
  }

  toggleSelection(event: any) {
    this.checkedApprove = event.checked;
  }

  getControl(lb: string): FormControl {
    return this.approveForm.get(lb) as FormControl;
  }

  /**
   * Voltar para a tela de resumo geral (resultado) da simulação
   */
  toResume() {
    this.router.navigate(['simulador', 'resultado', this.simulationID]);
  }

  refuse() {
    this.displayReason = true;
  }

  completeStatus(status: string){

    this.loading = true;
    if(status === "reprovado"){
      this.completeSimulation = this.reasonForm.getRawValue();
    }else{
      this.completeSimulation = this.approveForm.getRawValue();
    }

    this._approvalFlowService
        .approvalComplete(this.simulationID, this.completeSimulation)
        .subscribe(
          (data: any) => {
            if(status === "aprovado"){
              this.message.add({
                key: 'main',
                severity: 'success',
                summary: 'Simulação aprovada com sucesso',
                detail: 'Essa simualação foi aprovada com sucesso.',
                life: 3000,
              });
              this.loading = false;
              this.displayApprove = false;
            }else{
              this.message.add({
                key: 'main',
                severity: 'success',
                summary: 'Simulação reprovada com sucesso',
                detail: 'Essa simualação foi reprovada com sucesso.',
                life: 3000,
              });
              this.loading = false;
              this.displayReason = false;
            }
          },
          (err: any) => {
            this.message.add({
              key: 'main',
              severity: 'error',
              summary: 'Falha no envio',
              detail:
                err.error.description ||
                'Ocorreu uma falha no envio do status da simulação. Tente novamente mais tarde.',
              life: 3000,
            });
          }
        );

       this.disableCompleteButtons = true;

  }

  /**
   * Inicia o processo de aprovação.
   * Chama o serviço de aprovação para que comunique com o BFF e este ultimo
   * acione o camunda para iniciar o fluxo de aprovação
   */
  startApprovalProcess() {
      this._approvalFlowService
        .startApproval(this.simulationID)
        .subscribe(
          (data: any) => {
            this.message.add({
              key: 'main',
              severity: 'success',
              summary: 'Enviado para aprovação',
              detail: 'Essa simualação foi enviada com sucesso para a aprovação.',
              life: 3000,
            });
          },
          (err: any) => {
            this.message.add({
              key: 'main',
              severity: 'error',
              summary: 'Falha no envio',
              detail:
                // err.error.description ||
                'Ocorreu uma falha no envio para a aprovação. Tente novamente mais tarde.',
              life: 3000,
            });
          }
        );
  }
  approve() {
    this.displayApprove = true;
  }
}
