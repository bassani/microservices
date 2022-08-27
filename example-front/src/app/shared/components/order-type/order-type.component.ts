import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { BehaviorSubject, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { SIMULATION_TYPES } from 'src/app/simulator/pages/simulation-type/simulation-type.component';
import { SimulateService } from 'src/app/simulator/services/simulate/simulate.service';
import { IOrderType, ISearchResponse } from '../../models/search-simulador';
import { OrderTypeService } from '../../services';
import { FieldUtilsService } from '../../utils/field-utils.service';
import { PAGED_BASE } from '../../utils/mock';

@Component({
  selector: 'app-order-type',
  templateUrl: './order-type.component.html',
  styleUrls: ['./order-type.component.scss'],
})
export class OrderTypeComponent implements OnInit {
  orderTypes$ = new BehaviorSubject<ISearchResponse<any>>(PAGED_BASE);
  @Input('control') control = new FormControl();
  @Input('simulations') simulations?: boolean = false;

  constructor(private ordertypeService: OrderTypeService, public fieldUtils: FieldUtilsService) {}

  ngOnInit(): void {
    this.getOrderTypes();
  }

  getOrderTypes() {
    (this.simulations ? 
      of(SIMULATION_TYPES.map((e) => ({...e, name: e.title}))).subscribe((data) => {this.orderTypes$.next({content: data, size: 10, page: 0, numberOfElements: 10})}, err => {}) 
      : this.ordertypeService.getAllOrderTypes().subscribe((data) => {this.orderTypes$.next(data)}, err => {}));
  }
}
