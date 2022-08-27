import { Path } from './../../consts/path';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ApiService } from './../api/api.service';
import { Injectable } from "@angular/core";

@Injectable({
	providedIn: 'root',
})
export class SimulateFollowUpService {

	constructor(
		private _http: HttpClient, 
		private _api: ApiService,
	) {

	}

	getApprovalSimulate(body: any) {
		return this._api.request<any>({
			mock: false,
			mocker: of({}),
			http: this._http.post(Path.HTTP_API_BASE + '/simulations-followup?page=0&size=10', body)
		})
	}

}