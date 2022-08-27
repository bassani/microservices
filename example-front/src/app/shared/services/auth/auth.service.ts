import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Path } from 'src/app/shared/consts/path';
import { BehaviorSubject, Observable, of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { User } from '../../models/user.model';
import { ApiService } from '../api/api.service';
import { MOCK_SIDEBAR } from '../../utils/mock';
import { KeycloakService } from 'keycloak-angular';
import { switchMap } from 'rxjs/operators';
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  $user: BehaviorSubject<User> = new BehaviorSubject(
    new User({
      email: '',
      name: '',
      loggedIn: false,
      token: localStorage.getItem('token'),
      id: 1,
    })
  );
  hydratable: any = {
    user: {
      reference: this.$user,
      defaults: new User({
        email: '',
        name: '',
        loggedIn: false,
        token: localStorage.getItem('token'),
        id: 1,
      }),
    },
  };
  navigateTo = '';
  force = false;
  constructor(
    private _http: HttpClient,
    private _router: Router,
    private _api: ApiService,
    private _kc: KeycloakService
  ) {}

  get roles() {
    return this._kc.getUserRoles()
  }

  menuItems(): Observable<any> {
    return this._api.request({
      mock: false,
      mocker: of([...MOCK_SIDEBAR]),
      http: this._http.get<any[]>(Path.HTTP_MENU_PATH),
    });
  }

  canExecuteAction(roles: string[]) {
    return of(this._kc.getUserRoles(true)).pipe(switchMap(val => {
      const allRoles = roles.every(role => val.includes(role));
      return allRoles ? of(val) : throwError({status: 401, error: {message: 'Você não tem permissão para realizar essa operação.'}})
    }))
  }

  setCredentials() {
    return this._api.request<any>({
      mock: false,
      mocker: of({}),
      http: this._http.get(Path.HTTP_API_BASE + '/first-access')
    })
  }
  
  
  //TODO separar routing em outra função
  login({ email, password }: { email: string; password: string }): void {
    this.$user.next(
      new User({
        email: email,
        name: 'Usuario Teste',
        loggedIn: true,
        token: '44d12',
        id: 1,
      })
    );
    localStorage.setItem('user', JSON.stringify(this.$user.value));
    const prev = localStorage.getItem('prev');
    const prevRoute = !prev || prev === '/' ? '/inicio' : prev;
    this.navigateTo = this.navigateTo || prevRoute;
    this._router.navigateByUrl(this.navigateTo);
    this.navigateTo = '';
    this.force = false;
  }

  forceLogin(url: string): void {
    this.force = true;
    this._router.navigateByUrl('/login');
    this.navigateTo = url === '/' ? '/inicio' : url;
  }

  logout(): void {
    this.$user.next(
      new User({ email: '', name: '', loggedIn: false, token: null, id: 1 })
    );
    localStorage.removeItem('user');
    localStorage.removeItem('roles');
    localStorage.removeItem('token');
    this._router.navigateByUrl('/login');
  }

  resetPassword(): void {
    throw Error('not implemented');
  }

  requestResetPassword(): void {
    throw Error('not implemented');
  }
}
