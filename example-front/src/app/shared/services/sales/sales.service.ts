import { tap } from 'rxjs/operators';
import { MOCK_BLOB_XLS } from './../../utils/mock';
import { ApiService } from '../api/api.service';
import {
  ISalesCalculationItem,
  ISearchResponse,
} from '../../models/search-simulador';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { of, Observable } from 'rxjs';
import { Path } from '../../consts/path';
import { BASIC } from '../../utils/mock';
import { saveAs } from 'file-saver';
import { MessageService } from 'primeng/api';

@Injectable({
  providedIn: 'root',
})
export class SalesService {
  endpoints = {
    search: '/calculationBasis',
    report: '/sales/report'
  };

  constructor(
    private _http: HttpClient,
    private _api: ApiService,
    private _messageService: MessageService
  ) { }

  getSalesCalculation(): Observable<ISalesCalculationItem[]> {
    return this._api.request<ISalesCalculationItem[]>({
      mock: false,
      mocker: of(BASIC.salesCalculation.content),
      http: this._http.get<ISalesCalculationItem[]>(
        Path.HTTP_API_BASE + this.endpoints.search,
        { params: { page: '0', size: '999' } }
      ),
    });
  }

  exportSalesReport() {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'text/plain; charset=utf-8');
    return this._api.request<Blob>({
      mock: true,
      mocker: of(MOCK_BLOB_XLS),
      http: this._http.get<Blob>(
        Path.HTTP_API_BASE + this.endpoints.report,
        { headers: headers, responseType: 'text' as 'json' }
      )
    })
      .pipe(tap(response => {
        saveAs(response, `relatorio_de_vendas.xls`);
      }, error => {
        this._messageService.add({
          severity: 'error',
          summary: 'Erro ao fazer download',
          detail: 'O download não foi realizado',
          key: 'main',
        });
      }));
  }

}
