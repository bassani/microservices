import { ApiService } from './../api/api.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IDistributionCenter, ISearchResponse, ISearchResultItem } from '../../models/search-simulador';
import { BASIC } from '../../utils/mock';
import { Path } from '../../consts/path';
import { of } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

/**
 * Service in charge of handling Manufacturers related request and data
 */
@Injectable({
  providedIn: 'root',
})
export class IdSimulationService {

  constructor(private _http: HttpClient, private _api: ApiService) { }

  searchIDs() {
    const mockID =
    [
      { id: 1, name: 'SIMULACAO 1' },
      { id: 2, name: 'SIMULACAO 2' },
      { id: 3, name: 'SIMULACAO 3' },
      { id: 4, name: 'SIMULACAO 4' }
    ];
    return this._api.request<any>({
      mock: true,
      mocker: of(mockID),
      http: this._http.get<ISearchResultItem>(
        `${Path.HTTP_API_BASE}/simulations-pending-approval`
      ),
    }).pipe(debounceTime(300));
  }
}
