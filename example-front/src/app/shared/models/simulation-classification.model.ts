
export interface ISimulationClassification {
  id: number;
  name: string;
}

export class SimulationClassification implements ISimulationClassification {
  name: string;
  id: number;

  constructor(data: ISimulationClassification) {
    this.name = (data && data.name) || '';
    this.id = (data && data.id) || 0;
  }
}
