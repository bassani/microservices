import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { SimulateService } from '../../../simulator/services/simulate/simulate.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SalesService } from 'src/app/shared/services/sales/sales.service';
import { IBaseSimulation } from 'src/app/shared/models/simulation.model';

@Component({
  selector: 'app-sales-report',
  templateUrl: './sales-report.component.html',
  styleUrls: ['./sales-report.component.scss']
})
export class SalesReportComponent implements OnInit {
  form: FormGroup;
  simulation: IBaseSimulation;

  constructor(
    private _fb: FormBuilder,
    private _message: MessageService,
    private _salesService: SalesService,
    private _router: Router,
    private _ar: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    this.form = this._fb.group({
      distributionCenters: [null, Validators.required],
      category: [null],
      supplier: [],
      manufacturers: [],
      product: [[]],
      onlyInactives: [false]
    });
  }

  getControl(lb: string): FormControl {
    return this.form.get(lb) as FormControl;
  }

  cleanButton() {
    this.form.reset();
  }

  exportData() {
    this.form.markAllAsTouched();
    if (this.form.invalid)
      return this._message.add({
        severity: 'error',
        summary: 'Verifique os campos requeridos',
        detail: 'Formulário inválido',
      });
    this._salesService.exportSalesReport().subscribe();
  }

}
