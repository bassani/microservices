import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { saveAs } from 'file-saver';
import { delay } from 'rxjs/operators';
import { MessageService } from 'primeng/api';
import { ApiService } from 'src/app/shared/services/api/api.service';
import { Path } from 'src/app/shared/consts/path';

type TIPO_FORECAST = 'mensal' | 'semanal';

@Injectable({
  providedIn: 'root',
})
export class ForecastService {
  private endpoints = {
    downloadErrors: 'errors',
    reprocess: 'reprocess',
    downloadCurrent: 'actives',
  };

  save = saveAs;

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private api: ApiService
  ) {}

  downloadErrors(type: TIPO_FORECAST): void {
    var blob = new Blob([''], { type: 'text/csv;charset=utf-8' });
    this.api
      .request<Blob>({
        mock: false,
        mocker: of(blob),
        http: this.http.get(
          `${Path.HTTP_API_BASE}/forecast/${type}/${this.endpoints.downloadErrors}`
        ),
      })
      .subscribe(
        (data) => {
          this.save(data, `erros_${type}_forecast.csv`);
          this.messageService.add({
            severity: 'success',
            summary: 'Operação realizada com sucesso',
            detail: 'Download realizado com sucesso',
            key: 'main',
          });
        },
        (err) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Erro ao fazer download',
            detail: 'O download não foi realizado',
            key: 'main',
          });
        }
      );
  }

  reprocess(type: TIPO_FORECAST): void {
    let mock = { status: 1, msg: 'Reprocessamento inciado' };
    this.api
      .request({
        mock: false,
        mocker: of(mock),
        http: this.http.get(
          `${Path.HTTP_API_BASE}/forecast/${type}/${this.endpoints.reprocess}`
        ),
      })
      .subscribe(
        (data) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso ao reprocessar',
            detail: 'O reprocessamento foi realizado',
            key: 'main',
          });
        },
        (err) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Erro ao reprocessar',
            detail: 'O reprocessamento não foi realizado',
            key: 'main',
          });
        }
      );
  }

  downloadCurrent(type: TIPO_FORECAST): void {
    var blob = new Blob([''], { type: 'text/csv;charset=utf-8' });
    this.api
      .request<Blob>({
        mock: false,
        mocker: of(blob),
        http: this.http.get(
          `${Path.HTTP_API_BASE}/forecast/${type}/${this.endpoints.downloadCurrent}`
        ),
      })
      .subscribe(
        (data) => {
          this.save(data, `vigentes_${type}_forecast.csv`);
          this.messageService.add({
            severity: 'success',
            summary: 'Operação realizada com sucesso',
            detail: 'Download realizado com sucesso',
            key: 'main',
          });
        },
        (err) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Erro ao fazer download',
            detail: 'O download não foi realizado',
            key: 'main',
          });
        }
      );
  }
}
