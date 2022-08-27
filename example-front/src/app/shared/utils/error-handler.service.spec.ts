import { APP_BASE_HREF } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import {
  RouterModule,
} from '@angular/router';
import { AuthService } from '../services';
import { ErrorHandler } from './error-handler.service';


describe('ErrorHandler', () => {
  let service: ErrorHandler;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterModule.forRoot([]), HttpClientModule],
      providers: [AuthService, { provide: APP_BASE_HREF, useValue: '/' }, ErrorHandler],
    });
    service = TestBed.inject(ErrorHandler);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('handleHttpError', () => {
    it('Deve mostrar uma mensagem com os detalhes de um erro http', () => {
      let mockError = {error: {message: 'TESTE UNITARIO'}};
      spyOn(ErrorHandler._message, 'add')
      ErrorHandler.handleHttpError('TITLE',mockError);
      expect(ErrorHandler._message.add).toHaveBeenCalledTimes(1);
      expect(ErrorHandler._message.add).toHaveBeenCalledWith({summary: 'TITLE', detail: mockError.error.message, severity: 'error'});
    })

    it('Deve mostrar uma mensagem padrão caso o erro http não esteja sendo informado', () => {
      const mockError = {error: {message: undefined}};
      spyOn(ErrorHandler._message, 'add')
      ErrorHandler.handleHttpError('TITLE',mockError);
      expect(ErrorHandler._message.add).toHaveBeenCalledTimes(1);
      expect(ErrorHandler._message.add).toHaveBeenCalledWith({summary: 'TITLE', detail: 'Tente novamente mais tarde', severity: 'error'});
    })
  })

  describe('handleUnexpectedError', () => {
    it('Deve realizar o handling de erros de javascript', () => {
      const mockError = new Error('ERR');
      spyOn(ErrorHandler._message, 'add');
      const expectedTitle = 'Ops! algo não esta certo';
      ErrorHandler.handleUnexpectedError(mockError);
      expect(ErrorHandler._message.add).toHaveBeenCalledTimes(1);
      expect(ErrorHandler._message.add).toHaveBeenCalledWith({summary: expectedTitle, detail: 'Tente novamente mais tarde', severity: 'error'});
    })

    it('Deve realizar o handling de erros de customizados', () => {
      const mockError = {error: {message: 'TESTE UNITARIO'}};
      spyOn(ErrorHandler._message, 'add');
      const expectedTitle = 'Ops! algo não esta certo';
      ErrorHandler.handleUnexpectedError(mockError);
      expect(ErrorHandler._message.add).toHaveBeenCalledTimes(1);
      expect(ErrorHandler._message.add).toHaveBeenCalledWith({summary: expectedTitle, detail: 'TESTE UNITARIO', severity: 'error'});
    })
  })


});
