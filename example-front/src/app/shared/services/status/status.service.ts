import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { Path } from '../../consts/path';
import { BASIC } from '../../utils/mock';
import { ApiService } from '../api/api.service';

@Injectable({
  providedIn: 'root'
})
export class StatusService {
  endpoints = {
    search: '/status',
  };

  constructor(
    private _http: HttpClient,
    private _api: ApiService
  ) { }

  search() {
    return this._api.request<{ id: number, name: string}[]>({
      mock: false,
      mocker: of(BASIC.status.content),
      http: this._http.get(
        Path.HTTP_API_BASE + this.endpoints.search,
        { params: { page: '0', size: '999' } }
      ),
    });
  }

}
