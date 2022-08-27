import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { switchMap } from 'rxjs/operators';
import { IBaseSimulation } from 'src/app/shared/models/simulation.model';
import { RDValidators } from 'src/app/shared/utils/validators';
import {
  SimulateService,
} from '../../services/simulate/simulate.service';

@Component({
  selector: 'app-simulate',
  templateUrl: './simulate.component.html',
  styleUrls: ['./simulate.component.scss'],
})
export class SimulateComponent implements OnInit {
  form: FormGroup;
  simulationType: any = '';
  simulation: IBaseSimulation;
  constructor(
    private _simulationService: SimulateService,
    private _fb: FormBuilder,
    private _message: MessageService,
    private _router: Router,
    private _ar: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.createForm();
    this._ar.paramMap
      .pipe(
        switchMap((value) => {
          this.simulationType = value.get('simulationType');
          return this._simulationService.getSimulation(Number(value.get('id')));
        })
      )
      .subscribe(
        (data) => {
          this.simulation = data;
          if (!this.simulation || this.simulation.status == 9) {
            this._router.navigate(['simulador', 'simular']);
          } else {
            this.simulation = data;
          }
        },
        (err) => this._router.navigate(['simulador', 'simular'])
      )
      .add(() => this.updateForm());
  }

  updateForm() {
    if (this.simulation) this.form.patchValue(this.simulation);
  }

  createForm() {
    this.form = this._fb.group(
      {
        distributionCenters: [null, [Validators.required]],
        classification: [null, [Validators.required]],
        suppliers: [[]],
        manufacturer: [[]],
        category: [[]],
        subcategory: [[]],
        orderType: [null, [Validators.required]],
      },
      {
        validator: (group: FormGroup) => {
          let result = {
            ...RDValidators.atLeastOneTruthy(
              group,
              ['suppliers', 'category'],
              'Selecione pelo menos um fabricante ou categoria'
            ),
          };
          return Object.keys(result).length > 0 ? result : null;
        },
      }
    );
  }

  getControl(lb: string): FormControl {
    return this.form.get(lb) as FormControl;
  }

  startSimulation() {
    this.form.markAllAsTouched();

    if (!this.form.valid) {
      this._message.add({
        severity: 'error',
        summary: 'Campos incompletos',
        detail:
          'Verifique que todos os campos requeridos estejam completos para continuar.',
        key: 'main',
      });
      return;
    } else {
      this._simulationService
        .patchBasics(this.form.getRawValue(), this.simulation.id)
        .subscribe(
          (data) => {
            this._router.navigate([
              'simulador',
              'simular',
              this.simulationType,
              data.id,
              'efetuar',
            ]);
          },
          (err) => {
            this._message.add({
              severity: 'error',
              summary: 'Não foi possível gerar simulação',
              detail: 'Verifique a sua conexão e tente novamente mais tarde.',
              key: 'main',
            });
          }
        );
    }
  }
}
