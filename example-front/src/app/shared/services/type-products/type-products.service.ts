import { ApiService } from 'src/app/shared/services';
import {
  ISearchResponse,
  ITypeProductsItem,
} from './../../models/search-simulador';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BASIC } from '../../utils/mock';
import { of, Observable } from 'rxjs';
import { Path } from '../../consts/path';

@Injectable({
  providedIn: 'root',
})
export class TypeProductsService {
  endpoints = {
    search: '/typeProducts/search',
  };

  constructor(private _http: HttpClient, private _api: ApiService) {}

  getTypeProducts(): Observable<ISearchResponse<ITypeProductsItem>> {
    return this._api.request<ISearchResponse<ITypeProductsItem>>({
      mock: false,
      mocker: of(BASIC.paymentTerm),
      http: this._http.get<ISearchResponse<ITypeProductsItem>>(
        Path.HTTP_API_BASE + this.endpoints.search,
        { params: { page: '0', size: '999' } }
      ),
    });
  }
}
