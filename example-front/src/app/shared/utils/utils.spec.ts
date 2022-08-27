import { HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { handleErrorDetail, plural, searchCatchErrorHandler } from './utils';

describe('Utils functions', () => {
  describe('handleErrorDetail', () => {
    it('Deve retornar o detalhe do erro informado ou um string padrão caso não seja informado', () => {
      const mocks = {
        full: { message: 'Cusom teste message' },
        partial: { title: 'Some title, no message' },
      };
      const defaultsTo = 'Tente novamente mais tarde.';
      expect(handleErrorDetail(mocks.full)).toEqual(mocks.full.message);
      expect(handleErrorDetail(mocks.partial)).toEqual(defaultsTo);
    });
  });

  describe('Search catch error handler', () => {
    it('Deve retornar um observable vazio caso o erro seja 404', () => {
      const mock = {
        status: 404,
        error: { message: 'Not found' },
      } as HttpErrorResponse;
      searchCatchErrorHandler(mock).subscribe((data) => {
        expect(data).toEqual(mock);
      });
    });
    it('Deve propagar o erro caso o status não seja 404', () => {
      const mock = {
        status: 500,
        error: { message: 'Not found' },
      } as HttpErrorResponse;
      searchCatchErrorHandler(mock).subscribe(
        (data) => {
          expect(data).toEqual(null);
        },
        (err) => {
          expect(err).toEqual(mock);
        }
      );
    });

    describe('Plural', () => {
      it('Deve retonar o plural com os parametros informados caso o array tenha itens', () => {
        let arr = ["teste", "teste2"];
        expect(plural('s', arr)).toEqual('s')
        expect(plural('es', arr)).toEqual('es')
        expect(plural('es', [])).toEqual('')
        expect(plural('s', [])).toEqual('')
      })
    })
  });
});
