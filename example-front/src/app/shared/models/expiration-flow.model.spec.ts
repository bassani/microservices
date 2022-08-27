import { ExpirationFlow } from './expiration-flow.model';

describe('ExpirationFlowModel', () => {
	it('should create an instance', () => {
		expect(new ExpirationFlow({
			id: 12,
			description: 'description expirationn flow'
		})).toBeTruthy();
	});
});
