import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api';
import { of } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Path } from '../../consts/path';
import { ISearchResponse, ISupplier } from '../../models/search-simulador';
import { BASIC } from '../../utils/mock';
import { ApiService, SEARCH_MOCK } from '../api/api.service';

export type page_size = 5 | 10 | 25 | 50 | 100 | 999;

/**
 * Service in charge of handling Manufacturers related request and data
 */
@Injectable({
  providedIn: 'root',
})
export class ManufacturerService {
  /**
   * Dict of endpoints related to manufacturers
   */
  endpoints = {
    search: '/manufacturers',
    validate: '/manufacturers/validate',
  };

  /**
   * Uses a series os abstraction services to create and handle http requests
   * @param _http http client to make http requests
   * @param _message message service to show alerts and toasts
   * @param _api api service to wrap mockers and helper error handlers
   */
  constructor(
    private _http: HttpClient,
    private _message: MessageService,
    private _api: ApiService
  ) { }


  /**
   * Handle http errors
   * @param err http error object from the httpClient
   */
  handleSearchError(err: HttpErrorResponse) {
    if (err.status !== 404) {
      this._message.add({
        severity: 'error',
        summary: err.error.title || 'Ops',
        detail: err.error.message,
      });
    }
  }

  
  /**
   * Search manufacturers by code
   * and returns an observable
   * @param codes list of manufacturer ids separated by ;
   */
  validateCodes(codes: string) {
    let params = {manufacturerIds: [...new Set(codes.split(';').map(e => e.trim()).filter(val => !!val && val !== ''))]};
    return this._api.request<{valid: any[], invalid: any[]}>({
      mock: true,
      mocker: of({valid: [
        ...params.manufacturerIds.filter(val => !isNaN(Number(val))).map(id => ({id: Number(id), name: 'Teste fornecedor' + id}))
      ], invalid: [...params.manufacturerIds.filter(val => isNaN(Number(val)))]}),
      http: this._http.post(Path.HTTP_API_BASE + this.endpoints.validate, params)
    }).pipe(
      tap(
        (data) => { },
        (err) =>
          this.handleSearchError({
            ...err,
            error: {
              ...err.error,
              title: 'Não foi possível validar os fabricantes',
            },
          })
      )
    );
  }

  /**
   * Search manufacturers paginated, handles the server response
   * and returns an observable
   * @param q querystring to search for
   * @param page page number for the pagination, starts at zero
   * @param size page size, represents the maximun number of elements you want to be returned
   */
  search(q: string, page: number = 0, size: page_size = 100, parent?: any[]) {
    let params: any = {
      query: q,
      page: page.toString(),
      size: size.toString(),
    };
    if (q === "") delete params.query;
    if(parent) params['parentSupplierIds'] = parent?.map(parent => parent.id).join(',');
    return this._api
      .request<ISearchResponse<ISupplier>>({
        mock: false,
        mocker: of({
          ...BASIC.manufacturers,
          content: [
            ...BASIC.manufacturers.content.filter((e) =>
              e.name.toLowerCase().includes(q.toLowerCase())
            ),
            ...BASIC.manufacturers.content.filter((e) =>
              e.id.toString().toLowerCase().includes(q.toLowerCase())
            ),
          ],
        }),
        http: this._http.get(Path.HTTP_API_BASE + this.endpoints.search, {
          params,
        }),
      })
      .pipe(
        tap(
          (data) => { },
          (err) =>
            this.handleSearchError({
              ...err,
              error: {
                ...err.error,
                title: 'Não foi possível pesquisar fabricantes',
              },
            })
        )
      );
  }
}
