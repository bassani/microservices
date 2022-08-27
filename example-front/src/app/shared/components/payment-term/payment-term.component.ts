import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { IPaymentTermItem, ISearchResponse } from '../../models/search-simulador';
import { PaymentTermService } from '../../services/payment-term/payment-term.service';
import { PAGED_BASE } from '../../utils/mock';

@Component({
  selector: 'app-payment-term',
  templateUrl: './payment-term.component.html',
  styleUrls: ['./payment-term.component.scss']
})
export class PaymentTermComponent implements OnInit {
  paymentTerm$ = new BehaviorSubject<ISearchResponse<IPaymentTermItem>>(PAGED_BASE);
  @Input('control') control = new FormControl();
  constructor(private paymentTermService: PaymentTermService) { }

  ngOnInit(): void {
    this.getPaymentTerm();
  }

  getPaymentTerm(): void {
    this.paymentTermService.getPaymentTerm().subscribe(data => {
      this.paymentTerm$.next(data)
    }, err => this.paymentTerm$.next(PAGED_BASE))
  }

}
