import { IExpirationParameters } from './../../models/expiration-parameters.model';
import { ISearchResponse } from './../../models/search-simulador';
import { of } from 'rxjs';
import { ExpirationService } from './expiration.service';
import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { ApiService } from '../api/api.service';

const SIMULATE_MOCK = {
  flowId: "1",
  qtyExpirationDay: '5',
  description: "Fluxo de simulação",
};

const APPROVAL_MOCK = {
  flowId: "1",
  qtyExpirationDay: '5',
  description: "Fluxo de aprovação",
};


describe('ExpirationService', () => {
	let service: ExpirationService;

	beforeEach(() => {
		TestBed.configureTestingModule({
			imports: [
				HttpClientModule
			],
			providers: [
				ApiService,
			],
		});
		service = TestBed.inject(ExpirationService);
	});

	it('Should create a service', () => {
		expect(service).toBeTruthy();
	});

	describe('Should get the expiration parameters', () => {
		it('should receive success the server response', () => {
			const content = [...Array(10)].map<IExpirationParameters>((e, i) => ({
				id: i,
				expirationFlow: {
					id: i,
					description: 'description'
				},
				qtyExpirationDay: 12,
				creationDateTime: '12',
				description: 'another description',
				userId: `user id ${i}`,
				status: false
			}));
			const mock: ISearchResponse<IExpirationParameters> = {
				content: content,
				page: 0,
				size: 25,
				numberOfElements: content.length,
			};

			spyOn(service['_api'], 'request').and.returnValue(of(mock));

			service.getExpirationParameters().subscribe(
				(data) => expect(data).toEqual(mock),
        (err) => expect(err).not.toBeDefined()
			);

			expect(service['_api'].request).toHaveBeenCalledTimes(1)
		});
	});

	describe('Should post the expiration parameters', () => {

		it('Should create a new simulate expiration parameters', () => {
			let mock = SIMULATE_MOCK;
			spyOn(service['_api'], 'request').and.returnValue(of(mock));
			service.setFlowParameters(mock);
			expect(service['_api'].request).toHaveBeenCalledTimes(1);
		});

		it('Should create a new approval expiration parameters', () => {
			let mock = APPROVAL_MOCK;
			spyOn(service['_api'], 'request').and.returnValue(of(mock));
			service.setFlowParameters(mock);
			expect(service['_api'].request).toHaveBeenCalledTimes(1);
		});
	});
});
