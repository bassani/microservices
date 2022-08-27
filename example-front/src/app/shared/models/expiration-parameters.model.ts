import { IExpirationFlow } from './expiration-flow.model';

export interface IExpirationParameters {
	id: number,
	expirationFlow: IExpirationFlow,
	qtyExpirationDay: number,
	creationDateTime: string,
	description: string,
	userId: string,
	status?: boolean 
}

export class ExpirationParameters implements IExpirationParameters {
	id: number;
	expirationFlow: IExpirationFlow;
	qtyExpirationDay: number;
	creationDateTime: string;
	description: string;
	userId: string;
	status?: boolean;

	constructor(data: IExpirationParameters) {
		this.id = (data && data.id) || 0;
		this.expirationFlow = (data && data.expirationFlow) || null;
		this.qtyExpirationDay = (data && data.qtyExpirationDay) || 0;
		this.creationDateTime = (data && data.creationDateTime) || '';
		this.description = (data && data.description) || '';
		this.userId = (data && data.userId) || '';
		this.status = (data && data.status) || false;
	}
}