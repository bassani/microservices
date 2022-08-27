import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { ApiService } from '../api/api.service';
import { ISearchResponse, IProduct } from '../../models/search-simulador';
import { BASIC } from '../../utils/mock';
import { Path } from '../../consts/path';
import { MessageService } from 'primeng/api';
import { map, tap } from 'rxjs/operators';

/**
 * Service in charge or handling product data and requests
 */
@Injectable({
  providedIn: 'root',
})
export class ProductService {
  /** Dictionary of endpoints used to comunicate with the server */
  endpoints = {
    search: '/supplier-products',
    validate: '/products/validate',
  };

  /**
   * makes use of http client and api service dependencies for http comunication and mocking, and message
   * service for erro handling
   * @param _http http client to create http request
   * @param _api abstraction of api comunication to facilitate the use of mocks
   * @param _message message service to show toast and alerts
   */
  constructor(private _http: HttpClient, private _api: ApiService, private _message: MessageService) { }

  /**
   * Handles the http error from the server and shows a message formated using primeng message service
   * @param {HTTPErrorResponse} err error response from the httpClient
   */
  handleSearchError(err: HttpErrorResponse) {
      this._message.add({
        severity: 'error',
        summary: err.error?.title || 'Ops',
        detail: err.error?.message || 'Não foi possível comunicar com o servidor. Tente novamente mais tarde',
      });
    
  }

  /**
   * List all products that match the provided filter and return in a paged estructure
   * @param q queryparams object with every filter
   * @returns http response
   */
  getProducts(q: {query: string, supplier: number[], category?: number[], subcategory?: number[]} ): Observable<ISearchResponse<IProduct>> {
    return this._api.request<ISearchResponse<IProduct>>({
      mock: false,
      mocker: of({
        ...BASIC.product,
        content: [
          ...BASIC.product.content.filter((e) =>
            e.name.toLowerCase().includes(q.query.toLowerCase())
          ),
          ...BASIC.product.content.filter((e) =>
            e.id.toString().toLowerCase().includes(q.query.toLowerCase())
          ),
        ],
      }),
      http: this._http.post<ISearchResponse<{productId: any, productDescription: any}>>(
        Path.HTTP_API_BASE + this.endpoints.search,
        {
          supplierIds: q.supplier || [],
          categoryIds: q.category || [],
          subCategoryIds: q.subcategory || []
        },
        { params: { page: '0', size: '30', query: q.query } }
      ).pipe(map(data => ({...data, content: data.content.map(v => ({id: v.productId, name: v.productDescription}))}))),
    })
    .pipe(
      tap(
        (data) => { },
        (err) =>
          this.handleSearchError({
            ...err,
            error: {
              ...err.error,
              title: 'Não foi possível pesquisar produtos',
            },
          })
      )
    );
  }


  
  /**
   * Search products by code
   * and returns an observable
   * @param codes list of products ids separated by ;
   * ```typescript
   *  validateCodes('123;321;555;333;2').subscribe(data => {
   *            // use data.valid and/or data.invalid to get the valid and invalid codes from the list informed
   * })
   * ```
   */
   validateCodes(codes: string) {
    let params = {productIds: [...new Set(codes.split(';').map(e => e.trim()).filter(val => !!val && val !== ''))]};
    return this._api.request<{valid: any[], invalid: any[]}>({
      mock: true,
      mocker: of({valid: [
        ...params.productIds.filter(val => !isNaN(Number(val))).map(id => ({id: Number(id), name: 'Teste ' + id}))
      ], invalid: [...params.productIds.filter(val => isNaN(Number(val)))]}),
      http: this._http.post(Path.HTTP_API_BASE + this.endpoints.validate, params)
    }).pipe(
      tap(
        (data) => { },
        (err) =>
          this.handleSearchError({
            ...err,
            error: {
              ...err.error,
              title: 'Não foi possível validar os produtos',
            },
          })
      )
    );
  }
}
