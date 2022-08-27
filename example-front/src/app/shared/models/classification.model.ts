export interface IClassification {
  name: string;
  description: string;
  id: number;
  active: boolean;
}

export class Classification implements IClassification {
  name: string;
  description: string;
  id: number;
  active: boolean;

  constructor(data: IClassification) {
    this.name = (data && data.name) || '';
    this.description = (data && data.description) || '';
    this.id = (data && data.id) || 0;
    this.active = (data && data.active) || false;
  }
}

