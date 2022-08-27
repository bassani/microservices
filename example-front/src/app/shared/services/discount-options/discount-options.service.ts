import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Path } from '../../consts/path';
import {
  IDiscountOptionsItem,
  ISearchResponse,
} from '../../models/search-simulador';
import { BASIC } from '../../utils/mock';
import { ApiService } from '../api/api.service';

@Injectable({
  providedIn: 'root',
})
export class DiscountOptionsService {
  endpoints = {
    search: '/discountOptions/search',
  };

  constructor(private _http: HttpClient, private _api: ApiService) {}

  getDiscountOptions(): Observable<ISearchResponse<IDiscountOptionsItem>> {
    return this._api.request<ISearchResponse<IDiscountOptionsItem>>({
      mock: false,
      mocker: of(BASIC.paymentTerm),
      http: this._http.get<ISearchResponse<IDiscountOptionsItem>>(
        Path.HTTP_API_BASE + this.endpoints.search,
        { params: { page: '0', size: '999' } }
      ),
    });
  }
}
