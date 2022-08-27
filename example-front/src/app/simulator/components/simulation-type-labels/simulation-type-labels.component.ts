import { Component, Input, OnInit } from '@angular/core';
import { ISimulationType } from 'src/app/shared/models/search-simulador';
import { IBaseSimulation } from 'src/app/shared/models/simulation.model';

@Component({
  selector: 'div[with-simulation-type-results]',
  template: `
    <ng-container [ngSwitch]="simulation?.simulationType?.key">
      <ng-container *ngSwitchCase="'antecipacao'">
        <div class="p-col-12 resume-row">Data antecipada: <span class="resume-row-value">{{simulation?.anticipationDate | date: 'shortDate'}}</span></div>
      </ng-container>
      <ng-container *ngSwitchCase="'valor'">
        <div class="p-col-12 resume-row">Valor da compra (R$): <span class="resume-row-value">{{simulation?.amount | currency: 'BRL'}}</span></div>
      </ng-container>
      <ng-container *ngSwitchCase="'cobertura'">
        <div class="p-col-12 resume-row">Cobertura (Dias): <span class="resume-row-value">{{simulation?.stockCoverageDC}}</span></div>
      </ng-container>
      <div class="p-col-12 resume-row" *ngSwitchCaseDefault></div>
    </ng-container>
    <ng-content></ng-content>
  `,
})
export class SimulationTypeLabelsComponent implements OnInit {
  @Input('simulation') simulation: IBaseSimulation;

  constructor() { }

  ngOnInit(): void {
  }

}
