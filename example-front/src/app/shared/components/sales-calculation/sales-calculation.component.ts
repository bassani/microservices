import { Component, Input, OnInit } from '@angular/core';
import { AbstractControl, FormControl } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import {
  ISalesCalculationItem,
} from './../../models/search-simulador';
import { SalesService } from '../../services/sales/sales.service';
import { map } from 'rxjs/operators';
import { FieldUtilsService } from '../../utils/field-utils.service';

@Component({
  selector: 'app-sales-calculation',
  templateUrl: './sales-calculation.component.html',
  styleUrls: ['./sales-calculation.component.scss'],
})
export class SalesCalculationComponent implements OnInit {
  salesCalculation$ = new BehaviorSubject<
    ISalesCalculationItem[]
  >([]);
  @Input('control') control = new FormControl();
  constructor(public salesCalculationService: SalesService, public fieldUtils: FieldUtilsService) {}

  ngOnInit(): void {
    this.getSalesCalculation();
  }

  getSalesCalculation(): void {
    this.salesCalculationService.getSalesCalculation()
    .subscribe((data: ISalesCalculationItem[]) => {
      this.salesCalculation$.next(data)
    })
  }

  optionText(val: any) {
    return val
      ? `${this.salesCalculation$.value.find((p) => p.id == val.parent)?.name || ''} ${
          val.name
        }`.trim()
      : 'Selecione uma base';
  }
}
