import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ISearchResponse } from '../models/search-simulador';

/**
 * Serviço de abstração de requests
 * @deprecated devida a ser uma abstração inecesaria, adicionar complexidade no momento dos testes
 * assim que tiver a oportunidade pesquisar usos e trocar pelo api service diretamente
 */
@Injectable({
  providedIn: 'root',
})
export class ConfigService<T> {
  protected readonly apiUrl = `${this.baseUrl}/${this.endpoint}`;

  constructor(
    protected readonly http: HttpClient,
    @Inject('endpoint') readonly endpoint: string,
    @Inject('baseUrl') readonly baseUrl: string = environment.BASE_URL
  ) {}

  httpOptions = {
    Headers: new HttpHeaders({
      'Content-Type': 'applications/json',
    }),
  };

  fetch(query?: {
    [param: string]: string | string[];
  }): Observable<ISearchResponse<T>> {
    return this.http.get<ISearchResponse<T>>(this.apiUrl, { params: query });
  }

  get(id: number): Observable<T> {
    const url = this.entityUrl(id);
    return this.http.get<T>(url);
  }

  create(body: T): Observable<T> {
    return this.http.post<T>(this.apiUrl, body);
  }

  update(id: number, body: T): Observable<T> {
    const url = this.entityUrl(id);
    return this.http.put<T>(url, body);
  }

  delete(id: number): Observable<T> {
    const url = this.entityUrl(id);
    return this.http.delete<T>(url);
  }

  protected entityUrl(id: number): string {
    return [this.apiUrl, id].join('/');
  }
}
