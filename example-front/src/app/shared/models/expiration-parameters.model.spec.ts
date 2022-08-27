import { ExpirationParameters } from './expiration-parameters.model';


describe('ExpirationParametersModel', () => {
	it('should create an instance', () => {
		expect(new ExpirationParameters({
			id: 21,
			expirationFlow: {
				id: 23,
				description: 'description expiration flow'
			},
			qtyExpirationDay: 3,
			creationDateTime: '22',
			description: 'another description',
			userId: 'id com uma string grande',
			status: true
		})).toBeTruthy();
	});
});
