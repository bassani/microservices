import { ISimulationClassification } from './../../models/simulation-classification.model';
import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api';
import { BehaviorSubject, of, throwError } from 'rxjs';
import { catchError, delay, map, tap } from 'rxjs/operators';
import { ApiService, SEARCH_MOCK } from '../api/api.service';
import { ConfigService } from '../../config/config.service';
import { IActive, ISearchResponse } from '../../models/search-simulador';
import {
  BASIC,
  MOCK_BLOB_CSV,
  MOCK_CUSTOM_PAGED,
  PAGED_BASE,
} from '../../utils/mock';
import { IPaged } from '../../models/common.model';
import { IClassification } from '../../models/classification.model';
import { Path } from '../../consts/path';
import { saveAs } from 'file-saver';

@Injectable({
  providedIn: 'root',
})
export class ClassificationService {
  save = saveAs;
  endpoint: string = 'classifications';
  endpointAdd: string = 'classification';
  service: ConfigService<ISimulationClassification>;
  classifications$ = new BehaviorSubject<IPaged<IClassification>>({
    page: 0,
    size: 10,
    numberOfElements: 0,
    content: [],
    number: 0,
    totalElements: 0,
  });
  constructor(
    private http: HttpClient,
    private api: ApiService,
    private message: MessageService
  ) {
    this.service = new ConfigService(this.http, this.endpoint);
  }

  getAllClassifications(query: string) {
    return this.api
      .request<ISearchResponse<ISimulationClassification>>({
        mock: false,
        mocker: of(BASIC.classification),
        http: this.http.get(`${Path.HTTP_API_BASE}/${this.endpoint}?active=habilitado&page=0&size=200`),
      })
      .pipe(
        catchError((err) =>
          err.status == 404 ? of(PAGED_BASE) : throwError(err)
        )
      );
  }

  createClassification(classification: any) {
    if(typeof classification.active != 'string') {
      classification.active = !!classification.active ? 'habilitado' : 'desabilitado';
    };
    classification.registrationOperator = 9998;
    delete classification.id;

    return this.api
      .request({
        mock: false,
        mocker: of({ sucess: true }),
        http: this.http.post(
          `${Path.HTTP_API_BASE}/${this.endpoint}`,
          classification
        ),
      })
      .pipe(
        tap(
          (data: any) => {
            this.message.add({
              severity: 'success',
              detail: 'Sucesso',
              summary: 'Classificação cadastrada com sucesso',
            });
            const { page, size } = this.classifications$.value;
            this.getAll(page, size);
          },
          (err) => {
            this.message.add({
              severity: 'error',
              detail: 'Não foi possível cadastrar classificação',
              summary: err.error.message,
            });
          }
        )
      );
  }

  updateClassification(classification: any) {
    if(typeof classification.active != 'string') {
      classification.active = classification.active ? 'habilitado' : 'desabilitado';
    }

    // TODO remove mock operator
    classification.registrationOperator = 9998;
    return this.api
      .request({
        mock: false,
        mocker: of({ sucess: true }),
        http: this.http.put(
          `${Path.HTTP_API_BASE}/${this.endpoint}/${classification.id}`,
          classification
        ),
      })
      .pipe(
        tap(
          (data: any) => {
            this.message.add({
              severity: 'success',
              detail: 'Sucesso',
              summary: 'Classificação alterada com sucesso',
            });
            const { page, size } = this.classifications$.value;
            this.getAll(page, size);
          },
          (err) => {
            this.message.add({
              severity: 'error',
              detail: err.error.message,
              summary: 'Não foi possível alterar a classificação',
            });
          }
        )
      );
  }

  getAll(page: number, size: number) {
    const mock = BASIC.classification;
    this.classifications$.next({
      ...this.classifications$.value,
      status: 'LOADING',
    });
    this.api
      .request<IPaged<IClassification>>({
        mock: false,
        mocker: of(mock),
        http: this.http.get(Path.HTTP_API_BASE + '/classifications', {
          params: { page: page.toString(), size: size.toString() },
        }),
      })
      .subscribe(
        (data) => {
          this.classifications$.next({ ...data, page: data.page || data.number, status: 'READY' });
        },
        (err) => {
          this.classifications$.next({
            ...this.classifications$.value,
            status: 'ERROR',
          });
        }
      );
  }

  downloadCurrentClassifications(): void {
    const headers = new HttpHeaders().set(
      'Content-Type',
      'text/plain; charset=utf-8'
    );
    this.api
      .request<string>({
        mock: false,
        mocker: of(MOCK_BLOB_CSV),
        http: this.http.get(
          `${Path.HTTP_API_BASE}/classifications/export`,
          { headers: headers, responseType: 'text' }
        ),
      })
      .subscribe(
        (data) => {
          this.save(new Blob([data]), `cadastro_vigente_classificação.csv`);
          this.message.add({
            severity: 'success',
            summary: 'Operação realizada com sucesso',
            detail: 'Download realizado com sucesso',
            key: 'main',
          });
        },
        (err) => {
          this.message.add({
            severity: 'error',
            summary: 'Erro ao fazer download',
            detail: 'O download não foi realizado',
            key: 'main',
          });
        }
      );
  }

  getAllOptionsActive() {
    return this.api
      .request<ISearchResponse<IActive>>({
        mock: false,
        mocker: of(BASIC.active),
        http: this.service.fetch(),
      })
      .pipe(
        catchError((err) =>
          err.status == 404 ? of(PAGED_BASE) : throwError(err)
        )
      );
  }
}
