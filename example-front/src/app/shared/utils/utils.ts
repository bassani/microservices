import { HttpErrorResponse } from '@angular/common/http';
import { Observable, of, throwError, timer } from 'rxjs';
import { finalize, mergeMap } from 'rxjs/operators';

export function handleErrorDetail(error: any) {
    return error.message || 'Tente novamente mais tarde.'   
}

export function searchCatchErrorHandler(err: HttpErrorResponse): Observable<any> {
    return err.status === 404 ? of({content: [], page: 0, size: 25, numberOfElements: 0}) : throwError(err)
}

/**
 * Returns the plural informed character if the seccond argument have a length greater than 1
 * use it when you need to pluralize words by adding suffix characters
 *```typescript 
 * let myArray: {id: number, name: string}[] = [];
 * let textToShow = `Todo${plural("s", myArray)}`
 * let fornecedorLabel = `Fornecedor${plural("es", myArray)}`
 * ```
 * @returns {string} informed character or empty string to concat
*/
export function plural(character: string, array: any[]): string {
    return `${array.length>1?character:''}`
  }

  export const genericRetryStrategy = ({
    maxRetryAttempts = 3,
    scalingDuration = 1000,
    excludedStatusCodes = []
  }: {
    maxRetryAttempts?: number,
    scalingDuration?: number,
    excludedStatusCodes?: number[]
  } = {}) => (attempts: Observable<any>) => {
    return attempts.pipe(
      mergeMap((error, i) => {
        const retryAttempt = i + 1;
        if (
          retryAttempt > maxRetryAttempts ||
          excludedStatusCodes.find(e => e === error.status)
        ) {
          return throwError(error);
        }
        console.log(
          `Attempt ${retryAttempt}: retrying in ${retryAttempt *
            scalingDuration + 30000}ms`
        );
        // retry after 1s, 2s, etc...
        return timer(30000 + retryAttempt * scalingDuration);
      }),
      finalize(() => console.log('Done!'))
    );
  };