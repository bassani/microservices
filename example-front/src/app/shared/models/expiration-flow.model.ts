
export interface IExpirationFlow {
	id: number;
	description: string;
}

export class ExpirationFlow implements IExpirationFlow {
	id: number;
	description: string;

	constructor(data: IExpirationFlow) {
		this.id = (data && data.id) || 0;
		this.description = (data && data.description) || '';
	}
}

