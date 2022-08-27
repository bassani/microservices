import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { environment } from 'src/environments/environment';

import { ApiService, PAGED_SEARCH_MOCK } from './api.service';

describe('ApiService', () => {
  let service: ApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('Create request', () => {
    it('Deve realizar uma requisição http', () => {
      environment.forceHttp = false;
      environment.forceMocks = false;
      service
        .request({
          mock: true,
          mocker: of(PAGED_SEARCH_MOCK(3)),
          http: of(PAGED_SEARCH_MOCK(10)),
        })
        .subscribe((data) => {
          expect(data).toEqual(PAGED_SEARCH_MOCK(10));
        });
    });
    it('Deve forçar a utilização do mock', () => {
      environment.forceHttp = false;
      environment.forceMocks = false;
      service
        .request({
          mock: false,
          mocker: of(1),
          http: of(2),
        })
        .subscribe((data) => {
          expect(data).toEqual(1);
        });
    });
  });

  describe('Environment', () => {
    it('Deve preceder o environment sobre o argumento recebido e deve forçar o mock', () => {
      environment.forceHttp = false;
      environment.forceMocks = true;
      service
        .request({
          mock: true,
          mocker: of(1),
          http: of(2),
        })
        .subscribe((data) => {
          expect(data).toEqual(1);
        });
    });

    it('Deve preceder o environment sobre o argumento recebido e deve forçar o http request', () => {
      environment.forceHttp = true;
      environment.forceMocks = false;
      service
        .request({
          mock: false,
          mocker: of(1),
          http: of(2),
        })
        .subscribe((data) => {
          expect(data).toEqual(2);
        });
    });

    it('Deve forçar http sem importar outras variaveis de ambiente ou argumentos', () => {
      environment.forceHttp = true;
      environment.forceMocks = true;
      service
        .request({
          mock: false,
          mocker: of(1),
          http: of(2),
        })
        .subscribe((data) => {
          expect(data).toEqual(2);
        });
    });
  });
});
