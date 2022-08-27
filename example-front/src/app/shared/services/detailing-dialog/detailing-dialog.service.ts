import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Path } from '../../consts/path';
import {
  IDetailingDialog,
  ISearchResponse,
} from '../../models/search-simulador';
import { BASIC } from '../../utils/mock';
import { ApiService } from '../api/api.service';

@Injectable({
  providedIn: 'root',
})
export class DetailingDialogService {
  endpoints = {
    search: '/optionsDetailing/search',
  };

  constructor(private _http: HttpClient, private _api: ApiService) {}

  getDetailingOptions(): Observable<ISearchResponse<IDetailingDialog>> {
    return this._api.request<ISearchResponse<IDetailingDialog>>({
      mock: false,
      mocker: of(BASIC.optionsDetailing),
      http: this._http.get<ISearchResponse<IDetailingDialog>>(
        Path.HTTP_API_BASE + this.endpoints.search,
        { params: { page: '0', size: '999' } }
      ),
    });
  }
}
