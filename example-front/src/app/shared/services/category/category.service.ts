import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap } from 'rxjs/operators';
import { of, throwError } from 'rxjs';
import { MessageService } from 'primeng/api';
import { ApiService, SEARCH_MOCK } from '../api/api.service';
import { ConfigService } from '../../config/config.service';
import {
  IProductCategory,
  IProductSubcategory,
  ISearchResponse,
} from '../../models/search-simulador';
import { searchCatchErrorHandler, handleErrorDetail } from '../../utils/utils';
import { BASIC } from '../../utils/mock';
import { Path } from '../../consts/path';

interface IMultiEndpoint {base: string, manufacturer: string}
@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  categoryEndpoints: IMultiEndpoint = {base: 'products/categories', manufacturer: 'supplier-products/categories'};
  endpointSubcategory: IMultiEndpoint = {base: 'products/categories/subcategories', manufacturer: 'supplier-products/subcategories'};
  serviceCategory: ConfigService<IProductCategory>;

  constructor(
    private http: HttpClient,
    private api: ApiService,
    private message: MessageService
  ) {
  }

  getAllCategories(manufacturers: any[] = []) {
    let manufacturersId = manufacturers.map(e => e.id).join(',')
    return this.api
      .request<ISearchResponse<IProductCategory>>({
        mock: false,
        mocker: of({...BASIC.category, content: BASIC.category.content.filter(e => manufacturers.length > 1 && e.id > 1 ? false : true)}),
        http: this.http.get(`${Path.HTTP_API_BASE}/` + (manufacturers && manufacturers.length > 0 ? this.categoryEndpoints.manufacturer : this.categoryEndpoints.base) + `?size=500&supplierIds=${manufacturersId}`),
      })
      .pipe(
        catchError((err) => searchCatchErrorHandler(err)),
        tap(
          (d) => {},
          (err) =>
            this.message.add({
              severity: 'error',
              summary: 'Não foi possivel pesquisar categorias',
              detail: handleErrorDetail(err.error),
            })
        )
      );
  }

  getAllSubcategories(categories: IProductCategory[] = [], manufacturers: {id: number}[] = []) {
    return this.api
      .request<ISearchResponse<IProductSubcategory>>({
        mock: false,
        mocker: of({
          ...BASIC.subcategory,
          content: BASIC.subcategory.content.filter((e) => {
            return categories.find(v => v.id === e.categoryId)
          }),
        }),
        http: this.http.get(
          `${Path.HTTP_API_BASE}/${manufacturers && manufacturers.length > 0 ? this.endpointSubcategory.manufacturer : this.endpointSubcategory.base}`
          + `?size=500&categoriesIds=${categories.map(e => e.id).join(',')}&supplierIds=${manufacturers.map(e => e.id).join(',')}`
        ),
      })
      .pipe(
        catchError((err) => searchCatchErrorHandler(err)),
        tap(
          (d) => {},
          (err) =>
            this.message.add({
              severity: 'error',
              summary: 'Não foi possivel pesquisar subcategorias',
              detail: handleErrorDetail(err.error),
            })
        )
      );
  }
}
