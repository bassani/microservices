export interface IForecast {
  id: number;
  cd_region: number;
  cd_product: number;
  error: string;
  date: number;
}

export class Forecast implements IForecast {
  id: number;
  cd_region: number;
  cd_product: number;
  error: string;
  date: number;

  constructor(data: IForecast) {
    this.id = (data && data.id) || 0;
    this.cd_region = (data && data.cd_region) || 0;
    this.cd_product = (data && data.cd_product) || 0;
    this.error = (data && data.error) || '';
    this.date = (data && data.date) || 0;
  }
}

