import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';
import { Notification } from '../../models/notification.model';
import { Path } from '../../consts/path';
import {  BehaviorSubject, of } from 'rxjs';
import { IPaged } from '../../models/common.model';
import {
  MOCK_NOTIFICATIONS
} from '../../utils/mock';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  notificationsPagination$ = new BehaviorSubject<IPaged<Notification>>({
    page: 0,
    size: 10,
    numberOfElements: 0,
    content: [],
    number: 0,
    totalElements: 0,
  });

  /**
   * Uses the http client and api service to abstract and create http requests
   * @param _http http client injected by the module or resolved at construction
   * @param _api api abstraction to more easily mock request on demand, injected by the module
   */

   constructor(
    private _api: ApiService,
    private _http: HttpClient
  ) { }

  getPagination(page: number, size: number, sort:number) {
    this.notificationsPagination$.next({
      ...this.notificationsPagination$.value
    });
    this._api
    .request<IPaged<Notification>>({
        mock: false,
        mocker: of(...MOCK_NOTIFICATIONS),
        http: this._http.get(`${Path.HTTP_API_BASE}/messages/`, {
          params: { page: page.toString(), size: size.toString(), orderByExpirationDate : sort.toString() }
        })
      }).subscribe(
        (data) => {
          this.notificationsPagination$.next({ ...data, page: data.page || data.number});
        },
        (err) => {
          this.notificationsPagination$.next({
            ...this.notificationsPagination$.value
          });
        }
      );
  }

  getMessages(page: number, size: number, sort: number) {
    return this._api
      .request<Notification>({
        mock: false,
        mocker: of(...MOCK_NOTIFICATIONS),
        http: this._http.get(`${Path.HTTP_API_BASE}/messages/`, {
          params: { page: page.toString(), size: size.toString(), orderByExpirationDate : sort.toString()}
        })
      })
  }

  readNotifications(notificationRead: boolean, notificationId: number, keyUser: string){
    const body =
    {
      ids: [notificationId || undefined],
      keycloakUserId: keyUser || undefined,
      read: true
    };

    if(notificationRead){
      body.read = false;
    }
    return this._api
      .request({
        mock: false,
        mocker: of({ sucess: true }),
        http: this._http.put(`${Path.HTTP_API_BASE}/recipient/message-status/`, body)
      })
  }
}
