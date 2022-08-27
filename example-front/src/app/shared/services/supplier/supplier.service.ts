import { ApiService } from './../api/api.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IDistributionCenter, ISearchResponse, ISearchResultItem } from '../../models/search-simulador';
import { BASIC } from '../../utils/mock';
import { Path } from '../../consts/path';
import { of } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SupplierService {
  endpoints = {
    search: '/manufacturers/parents',
  };
  constructor(private _http: HttpClient, private _api: ApiService) { }

  search(q: string) {
    const params: any = { page: '0', size: '50' };
    params['query'] = q;
    return this._api.request<ISearchResponse<ISearchResultItem>>({
      mock: false,
      mocker: of({...BASIC.supplier, content: BASIC.supplier.content.filter(el => el.name.toLowerCase().includes(q.toLowerCase()))}),
      http: this._http.get<ISearchResultItem>(
        `${Path.HTTP_API_BASE}${this.endpoints.search}`,
        { params: params }
      ),
    }).pipe(debounceTime(300));
  }
}
