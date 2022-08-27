import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { RDValidators } from 'src/app/shared/utils/validators';
import { ActivatedRoute, Router } from '@angular/router';
import { delay, map, switchMap } from 'rxjs/operators';
import {
  SimulateService,
} from '../../services/simulate/simulate.service';
import { IBaseSimulation } from 'src/app/shared/models/simulation.model';
import { INATIVOS_TEMPORARIOS } from 'src/app/shared/consts/filters';
import { PaymentTermValidators } from 'src/app/shared/components/payment-term-cd/payment-term-cd.component';
import { CdService } from '../../../../app/shared/services';
import { BudgetType } from 'src/app/shared/components/budget-select-dialog/budget-select-dialog.component';

/**
 * Componente encarregado de aceitar os ultimos parametros especificos e iniciar a simulação
 */
@Component({
  selector: 'app-simulate-specifics',
  templateUrl: './simulate-specifics.component.html',
  styleUrls: ['./simulate-specifics.component.scss'],
})
export class SimulateSpecifics implements OnInit {
  /** Instancia de form dos parametros */
  form: FormGroup;
  /** Representação da simulação ate o ponto atual */
  simulation: IBaseSimulation;
  constructor(
    private _fb: FormBuilder,
    private _message: MessageService,
    private _simulationService: SimulateService,
    private _router: Router,
    private _ar: ActivatedRoute,
    private _dcService: CdService

  ) { }

  /** ao iniciar o componente obtem do serviço a simulação atual
   * estrutura pronta para poder continuar a simulação dos dados salvos no back
   * porem hj em dia todo o processo da simulação tem somente state local,
   * ou seja não e puxado nada do backend e si do serviço in-memory
   */
  ngOnInit(): void {
    this._ar.paramMap
      .pipe(
        switchMap((value) => {
          return this._simulationService.getSimulation(Number(value.get('id')));
        })
      )
      .subscribe(
        (data) => {
          this.simulation = data;
          if (!this.simulation || this.simulation.status == 9) {
            // se a simulação não existir voltar ao primeiro step da simulação
            this._router.navigate(['simulador', 'simular']);
          } else {
            // se a simulação existir cria o form
            this.createForm();
          }
        },
        (err) => {
          if (!this.simulation) this._router.navigate(['simulador', 'simular']);
        }
      );
  }

  /** Cria o form group para inserir os dados especificos da simulação */
  createForm(): void {
    let specifics: any = {
      /** utilizado na simulação de antecipação */
      antecipacao: {
        anticipationDate: [null, Validators.required]
      },
      /** utilizado na simulação por valor */
      valor: {
        amount: [null, Validators.compose([Validators.required, Validators.min(1)])]
      },
      /** utilizado na simulação por cobertura */
      cobertura: {
        stockCoverageDC: [null, Validators.compose([Validators.required, Validators.min(1), Validators.max(800)])]
      }
    }
    let key = this.simulation?.simulationType.key || 'cobertura';
    this.form = this._fb.group({
      ...specifics[key], /** chave de filtro especifico dinamica segundo o tipo da simulação */
      newPaymentTerm: [null, PaymentTermValidators.requiredWhenNotNull(this.simulation.distributionCenters)],
      calculationBasis: [null, Validators.required],
      budget: this._fb.group({
        type: [BudgetType.percentage],
        value: [null],
        reason: [null]
      }),
      productFilter: this._fb.group({
        promoPacks: [null],
        quoted: [null],
        inactive: [null],
        temporaryInactiveCode: [INATIVOS_TEMPORARIOS.NAO_SE_APLICA]
      }),
      note: [null, Validators.compose([Validators.required, Validators.maxLength(512), Validators.minLength(1)])],
      discount: this._fb.group({
        productsCovered: ['all'],
        discountType: [0],
        skus: [null],
        discount: [null],
      }),
    });
  }

  /**
   * Chama o serviço para enviar os dados ao back e iniciar a simulação
   * esses dados de form no serviço são ainda transformados para o modelo que o 
   * back precisar
   */
  simulate() {
    this.form.markAllAsTouched();
    if (this.form.invalid)
      return this._message.add({
        severity: 'error',
        summary: 'Verifique os campos requeridos',
        detail: 'Formulário inválido',
        key: 'main'
      });
    this._simulationService
      .patchSpecifics(this.form.getRawValue(), this.simulation.id || 0)
      .subscribe((data) => {
        this._router.navigate(['simulador', 'resultado', data.id]);
      }, err => {
        this._message.add({
          severity: 'error',
          summary: 'Não foi possível criar a simulação',
          detail: err.error?.message || 'Tente novamente mais tarde',
          key: 'main'
        })
      });
  }

  /** retorna um form control typado, utilizado em componentes customizados */
  getControl(lb: string): FormControl {
    return this.form.get(lb) as FormControl;
  }

  /** retorna um form group typado, utilizado em componentes customizados */
  getGroup(gp: string): FormGroup {
    return this.form.get(gp) as FormGroup;
  }

  /** Retorna uma lista de cds sem referencia */
  get DCs() {
    let dcs = JSON.parse(JSON.stringify(this.simulation.distributionCenters))
    return dcs;
  }
}
