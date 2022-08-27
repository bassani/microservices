import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { MessageService } from 'primeng/api';
import { of } from 'rxjs';

import { ConfigService } from './config.service';

interface SubInterface {
  id: number;
}
@Injectable({providedIn: 'root'})
class StubService {
  endpoint: string = "testeService";
  baseUrl: string = "http://localhost:3000";
  service: ConfigService<SubInterface>;

  constructor(private http: HttpClient) {
    this.service = new ConfigService(this.http, this.endpoint, this.baseUrl);
  }
}


describe('ServiceService', () => {
  let service: StubService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [MessageService]
    });
    service = TestBed.inject(StubService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('fetch', () => {
    it('Deve obter lista de dados via get', () => {
      const params = {name: '1'};
      spyOn(service['http'], 'get').and.returnValue(of({}));
      service.service.fetch(params).subscribe(data => {});
      expect(service['http'].get).toHaveBeenCalledTimes(1);
      expect(service['http'].get).toHaveBeenCalledWith(`${service.baseUrl}/${service.endpoint}`, {params: params})
    })
  })
  describe('get', () => {
    it('Deve obter dado por id via get', () => {
      const id = Math.floor(Math.random() * (100 - 1 + 1)) + 1;
      spyOn(service['http'], 'get').and.returnValue(of({}));
      service.service.get(id).subscribe(data => {});
      expect(service['http'].get).toHaveBeenCalledTimes(1);
      expect(service['http'].get).toHaveBeenCalledWith(`${service.baseUrl}/${service.endpoint}/${id}`)
    })
  })
  describe('create', () => {
    it('Deve realizar um post request enviando como body o argumento recebido', () => {
      const bod = {
        id: Math.floor(Math.random() * (100 - 1 + 1)) + 1,
        name: 'Teste'
      };
      spyOn(service['http'], 'post').and.returnValue(of({}));
      service.service.create(bod).subscribe(data => {});
      expect(service['http'].post).toHaveBeenCalledTimes(1);
      expect(service['http'].post).toHaveBeenCalledWith(`${service.baseUrl}/${service.endpoint}`, bod)
    })
  })
  describe('delete', () => {
    it('Deve realizar um delete request com o id informado', () => {
      const id = Math.floor(Math.random() * (100 - 1 + 1)) + 1;
      spyOn(service['http'], 'delete').and.returnValue(of({}));
      service.service.delete(id).subscribe(data => {});
      expect(service['http'].delete).toHaveBeenCalledTimes(1);
      expect(service['http'].delete).toHaveBeenCalledWith(`${service.baseUrl}/${service.endpoint}/${id}`)
      
    })
  })

  describe('Create Entity URL', () => {
    it('Deve criar uma url com um path param', () => {
      const id = Math.floor(Math.random() * (100 - 1 + 1)) + 1;
      const result = service.service['entityUrl'](id);
      expect(result).toEqual(`${service.baseUrl}/${service.endpoint}/${id}`)
    })
  })
});

