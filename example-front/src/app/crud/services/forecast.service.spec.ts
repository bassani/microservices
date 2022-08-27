import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MessageService } from 'primeng/api';
import { of, throwError } from 'rxjs';

import { ForecastService } from './forecast.service';

describe('ForecastService', () => {
  let service: ForecastService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [MessageService],
    });
    service = TestBed.inject(ForecastService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('Download erros', () => {
    it('Deve realizar o download do erros com o blob enviado pelo servidor', () => {
      var blob = new Blob(['Downlaod Errors - Mock'], {
        type: 'text/plain;charset=utf-8',
      });
      spyOn(service, 'save').and.returnValue(blob);
      spyOn(service['messageService'], 'add').and.returnValue({});
      spyOn(service['api'], 'request').and.returnValue(of(blob));
      service.downloadErrors('mensal');
      expect(service.save).toHaveBeenCalledTimes(1);
      expect(service['messageService'].add).toHaveBeenCalledTimes(1);
      expect(service.save).toHaveBeenCalledWith(
        blob,
        `erros_mensal_forecast.csv`
      );
      expect(service['messageService'].add).toHaveBeenCalledWith({
        severity: 'success',
        summary: 'Operação realizada com sucesso',
        detail: 'Download realizado com sucesso',
        key: 'main',
      });
    });

    it('Deve mostar um erro pro usuario e não realizar download caso o servidor retorne erro', () => {
      const mockError = { status: 500, error: { message: 'Internal Error' } };
      spyOn(service, 'save').and.returnValue({});
      spyOn(service['messageService'], 'add').and.returnValue('ok');
      spyOn(service['api'], 'request').and.returnValue(throwError(mockError));
      service.downloadErrors('semanal');
      expect(service['messageService'].add).toHaveBeenCalledTimes(1);
      expect(service.save).toHaveBeenCalledTimes(0);
      expect(service['messageService'].add).toHaveBeenCalledWith({
        severity: 'error',
        summary: 'Erro ao fazer download',
        detail: 'O download não foi realizado',
        key: 'main',
      });
    });
  });

  describe('Reprocessar', () => {
    it('Deve realizar o handling para o sucesso do reprocessamento mostrando uma mensagem pro usuario', () => {
      spyOn(service['messageService'], 'add').and.returnValue({});
      spyOn(service['api'], 'request').and.returnValue(of({ status: 1 }));
      service.reprocess('semanal');
      expect(service['messageService'].add).toHaveBeenCalledTimes(1);
      expect(service['messageService'].add).toHaveBeenCalledWith({
        severity: 'success',
        summary: 'Sucesso ao reprocessar',
        detail: 'O reprocessamento foi realizado',
        key: 'main',
      });
    });

    it('Deve realizar o handling para o erro do reprocessamento mostrando uma mensagem pro usuario', () => {
      const mockError = { status: 500, error: { message: 'Internal Error' } };
      spyOn(service['messageService'], 'add').and.returnValue({});
      spyOn(service['api'], 'request').and.returnValue(throwError(mockError));
      service.reprocess('mensal');
      expect(service['messageService'].add).toHaveBeenCalledTimes(1);
      expect(service['messageService'].add).toHaveBeenCalledWith({
        severity: 'error',
        summary: 'Erro ao reprocessar',
        detail: 'O reprocessamento não foi realizado',
        key: 'main',
      });
    });
  });

  describe('Download Vigentes', () => {
    it('Deve realizar o download do cadastro vigente com o blob enviado pelo servidor', () => {
      var blob = new Blob(['Downlaod Vigente - Mock'], {
        type: 'text/plain;charset=utf-8',
      });
      spyOn(service, 'save').and.returnValue(blob);
      spyOn(service['messageService'], 'add').and.returnValue({});
      spyOn(service['api'], 'request').and.returnValue(of(blob));
      service.downloadCurrent('semanal');
      expect(service.save).toHaveBeenCalledTimes(1);
      expect(service['messageService'].add).toHaveBeenCalledTimes(1);
      expect(service.save).toHaveBeenCalledWith(
        blob,
        `vigentes_semanal_forecast.csv`
      );
      expect(service['messageService'].add).toHaveBeenCalledWith({
        severity: 'success',
        summary: 'Operação realizada com sucesso',
        detail: 'Download realizado com sucesso',
        key: 'main',
      });
    });

    it('Deve mostar um erro pro usuario e não realizar download caso o servidor retorne erro', () => {
      const mockError = { status: 500, error: { message: 'Internal Error' } };
      spyOn(service, 'save').and.returnValue({});
      spyOn(service['messageService'], 'add').and.returnValue('ok');
      spyOn(service['api'], 'request').and.returnValue(throwError(mockError));
      service.downloadCurrent('mensal');
      expect(service['messageService'].add).toHaveBeenCalledTimes(1);
      expect(service.save).toHaveBeenCalledTimes(0);
      expect(service['messageService'].add).toHaveBeenCalledWith({
        severity: 'error',
        summary: 'Erro ao fazer download',
        detail: 'O download não foi realizado',
        key: 'main',
      });
    });
  });
});
