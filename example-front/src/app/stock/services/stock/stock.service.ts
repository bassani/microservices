import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { ApiService } from 'src/app/shared/services';
import { ConferenceFilter, IConferenceResult } from '../../models/conference-filter';
import { saveAs } from 'file-saver';
import { delay, tap } from 'rxjs/operators';
import { Path } from 'src/app/shared/consts/path';


@Injectable({
  providedIn: 'root'
})
export class StockService {
  save = saveAs;
  private apiUrl = `${Path.HTTP_API_BASE}/black-friday`
  constructor(
    private api: ApiService,
    private http: HttpClient
  ) { 

  }


  downloadConference(filters: ConferenceFilter) {
    const mock = new Blob([''], { type: 'application/octet-stream;charset=UTF-8' });
    const headers = new HttpHeaders().set(
      'Content-Type',
      'application/octet-stream;charset=UTF-8'
    );
    return this.api.request<Blob>({
      mock: true,
      mocker: of(mock),
      http: this.http.post(
        this.apiUrl,
        filters,
        { headers: headers, responseType: 'blob' as 'json' }
      )
    }).pipe(
      delay(15000),
      tap(data => {
      this.save(data, `estoque_${filters.dtInicialPedido.toISOString().split('T')[0]}_ao_${filters.dtFinalPedido.toISOString().split('T')[0]}.xls`);
    }))
  }




}
