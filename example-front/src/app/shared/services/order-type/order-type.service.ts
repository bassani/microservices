import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ConfigService } from '../../config/config.service';
import { IOrderType, ISearchResponse } from '../../models/search-simulador';
import { BASIC } from '../../utils/mock';
import { ApiService, PAGED_SEARCH_MOCK, SEARCH_MOCK } from '../api/api.service';

/**
 * Service in charge or handling order types
*/
@Injectable({
  providedIn: 'root',
})
export class OrderTypeService {
  /** endpoint base of the order types requests */
  endpoint: string = 'typesorder';
  /**
   * abstraction over http requests to allow for mocking
   * @deprecated in a next version this will be replaced by the api service abstraction
   */
  service: ConfigService<IOrderType>;


  /**
   * Makes use of the http client and api service abstractions to make and handle http requests
   * @param http http client for making http requests
   * @param _api abstraction over http comunications to allow for mocking
   */
  constructor(private http: HttpClient, private _api: ApiService) {
    this.service = new ConfigService(this.http, this.endpoint);
  }

  /**
   * List all of the order types available
   */
  getAllOrderTypes(): Observable<ISearchResponse<IOrderType>> {
    return this._api.request<ISearchResponse<IOrderType>>({
      mock: false,
      mocker: of(BASIC.orderType),
      http: this.service.fetch({page: "0", size: "100"}),
    });
  }
}
