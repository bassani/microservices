import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { delay } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import {
  ISearchResponse,
  ISearchResultItem,
} from '../../models/search-simulador';

export const SEARCH_MOCK = (...args: any[]) =>
  of([...Array(10)].map((e, i) => ({ id: i + 1, name: `${i + 1} test` }))).pipe(
    delay(300)
  );

export const PAGED_SEARCH_MOCK = (...args: any[]) => {
  const content = [...Array(10)].map((e, i) => ({
    id: i + 1,
    name: `${i + 1} test`,
  }));
  const resp: ISearchResponse<ISearchResultItem> = {
    content: content,
    page: 0,
    size: 25,
    numberOfElements: 10,
  };
  of(resp);
};

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  constructor() {}

  /**
   * Wrapper function to allow easy mocking into a http request
   * @param {{mock: boolean, mocker: any, http: Observable<any>}} config configuration for the request
   * @todo Implement the onBreak function to enable a smooth circuit break
   */
  request<T>({
    mock,
    mocker,
    http,
    onBreak,
  }: {
    mock: boolean;
    mocker: any;
    http: Observable<any>;
    onBreak?: Function;
  }): Observable<T> {
    mock = mock || false;
    if (environment.forceMocks == true) mock = true;
    if (environment.forceHttp == true) mock = false;
    return mock ? mocker : http;
  }
}
