import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { Path } from '../../consts/path';
import {
  IDistributionCenter,
  ISearchResponse,
} from '../../models/search-simulador';
import { BASIC, PAGED_BASE } from '../../utils/mock';
import { ApiService, SEARCH_MOCK } from '../api/api.service';

@Injectable({
  providedIn: 'root',
})
export class CdService {
  endpoints = {
    search: '/distributionCenters',
  };
  constructor(private _http: HttpClient, private _api: ApiService) {}
  // of([{id: 900, name: 'EMBU'}, {id: 905, name: 'PARANA'}])

  search() {
    return this._api.request<ISearchResponse<IDistributionCenter>>({
      mock: false,
      mocker: of(BASIC.cd),
      http: this._http.get<IDistributionCenter>(
        Path.HTTP_API_BASE + this.endpoints.search,
        { params: { page: '0', size: '999' } }
      ),
    });
  }
}
