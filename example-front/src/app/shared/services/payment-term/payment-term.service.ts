import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { of, Observable } from 'rxjs';
import { Path } from '../../consts/path';
import {
  IPaymentTermItem,
  ISearchResponse,
} from '../../models/search-simulador';
import { BASIC } from '../../utils/mock';
import { ApiService } from '../api/api.service';

/**
 * Service responsible of handling payment term and payment conditions
 */
@Injectable({
  providedIn: 'root',
})

export class PaymentTermService {
  /** Dict of endpoints that the service uses to comunicate with the backend */
  endpoints = {
    search: '/paymentTerms',
  };

  /**
   * Uses the http client and api service to abstract and create http requests
   * @param _http http client injected by the module or resolved at construction
   * @param _api api abstraction to more easily mock request on demand, injected by the module
   */
  constructor(
    private _http: HttpClient, 
    private _api: ApiService) {}

  /**
   * List the payment terms available
   * ```typescript
   * //...some component
   * constructor(private paymentService: PaymentTermService) {
   *  paymentService.getPaymentTerm()
   * .subscribe(data => {
   *      //handle the data result in case of success
   * }, 
   *  err => {
   *      //handle http errors
   * })
   * }
   * ```
   * @returns Http response / Http error
   */
  getPaymentTerm(): Observable<ISearchResponse<IPaymentTermItem>> {
    return this._api.request<ISearchResponse<IPaymentTermItem>>({
      mock: false,
      mocker: of(BASIC.paymentTerm),
      http: this._http.get<ISearchResponse<IPaymentTermItem>>(
        Path.HTTP_API_BASE + this.endpoints.search,
        { params: { page: '0', size: '999' } }
      ),
    });
  }
}
