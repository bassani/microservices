import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api';
import { BehaviorSubject, of, throwError } from 'rxjs';
import { catchError, debounceTime, delay, map, tap } from 'rxjs/operators';
import { ApiService } from '..';
import { Path } from '../../consts/path';
import { IPaged } from '../../models/common.model';
import { IPosition } from '../../models/position.model';
import { IUser } from '../../models/user.model';
import { BASIC, PAGED_BASE } from '../../utils/mock';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  baseUrl = `${Path.HTTP_API_BASE}/users`;

  users$ = new BehaviorSubject<IPaged<IUser>>({
    page: 0,
    size: 10,
    numberOfElements: 0,
    content: [],
    number: 0,
    totalElements: 0,
  });
  constructor(
    private http: HttpClient,
    private api: ApiService,
    private _message: MessageService,
  ) { }


  findUsers(searchString: string = '') {

    let filterType = isNaN(Number(searchString)) ? 'name' : 'code';

    return this.api.request<IPaged<{code: number, name: string}>>({
      mock: false,
      mocker: of({content: [{name: 'MOCK', code: 1}]}),
      http: this.http.get(`${this.baseUrl}?size=50&${filterType}=${searchString}`).pipe(
      )
    })
    .pipe(
      map(data => data.content),
      catchError(err => {
        this._message.add({key: 'main', severity: 'error', detail: 'Não foi possível pesquisar', summary: err.error.message, life: 3000});
        return of([])
    }))
  }

  getAllPositions() {
    return this.api.request<{ id: number, name: string}[]>({
      mock: false,
      mocker: of(BASIC.positions.content),
      http: this.http.get(
        `${Path.HTTP_API_BASE}/users/positions`,
        { params: { page: '0', size: '999' } }
      ),
    });
  }

  getAll(q:string ,page: number, size: number) {
    this.users$.next({
      ...this.users$.value,
      status: 'LOADING',
    });
    const mock = BASIC.users;
    this.api
      .request<IPaged<IUser>>({
        mock: false,
        mocker: of(mock),
        http: this.http.get(`${Path.HTTP_API_BASE}/users`, {
          params: { search: q, page: page.toString(), size: size.toString() },
        }),
      })
      .subscribe(
        (data) => {
          this.users$.next({ ...data, page: data.page, status: 'READY' });
        },
        (err) => {
          this.users$.next({
            ...this.users$.value,
            status: 'ERROR',
          });
        }
      );
  }

  getPositionUser(keyCloakId: string) {
    return this.api.request<any>({
      mock: false,
      mocker: of({content: [{id: '1', name: 'MOCK'}]}),
      http: this.http.get(`${Path.HTTP_API_BASE}/users/${keyCloakId}/position`).pipe(
      )
    })
    .pipe(
      catchError(err => {
        this._message.add({key: 'main', severity: 'error', detail: 'Não foi possível achar usuário', summary: err.error.message, life: 3000});
        return of([])
    }))
  }

  createUser(user: any) {
    return this.api
      .request({
        mock: false,
        mocker: of({ sucess: true }),
        http: this.http.post(
          `${Path.HTTP_API_BASE}/users`,
          user
        ),
      })
      .pipe(
        tap(
          (data: any) => {
            const { page, size } = this.users$.value;
            this.getAll('',page, size);
          },
          (err) => {}
        )
      );
  }

  updateUser(user: any) {
    if(user.vacationReturnDate) {
      user.vacationReturnDate =  user.vacationReturnDate instanceof Date ? user.vacationReturnDate : this.convertData(user);
    }
  
    return this.api
      .request({
        mock: false,
        mocker: of({ sucess: true }),
        http: this.http.put(
          `${Path.HTTP_API_BASE}/users`,
          user
        ),
      })
      .pipe(
        tap(
          (data: any) => {
            const { page, size } = this.users$.value;
            this.getAll('', page, size);
          },
          (err) => {}
        )
      );
  }

  convertData(user: any): string {
    const data = user.vacationReturnDate?.split(' ')[0];
    const dataRequest = data.split('/').reverse().join('-')
    return dataRequest;
  }
}
