import { Path } from './../../consts/path';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ApiService } from './../api/api.service';
import { Injectable } from "@angular/core";


@Injectable({
	providedIn: 'root',
})
export class ExpirationService {

	constructor(
		private _http: HttpClient, 
		private _api: ApiService,
	) { }

	getExpirationParameters() {
		return this._api.request<any>({
			mock: false,
			mocker: of({}),
			http: this._http.get(Path.HTTP_API_BASE + '/expiration-parameters?latest=1')
		})
	}

	setFlowParameters(body: any) {
		return this._api.request<any>({
			mock: false,
			mocker: of({}),
			http: this._http.post(Path.HTTP_API_BASE + '/expiration-parameters', body)
		})
	}

	getUsers(body: Array<string>) {
		return this._api.request<any>({
			mock: false,
			mocker: of({}),
			http: this._http.post(Path.HTTP_API_BASE + '/users/ids', body)
		})
	}
}