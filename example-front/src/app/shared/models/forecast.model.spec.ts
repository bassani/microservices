import { Forecast } from './forecast.model';

describe('ForecastModel', () => {
  it('should create an instance', () => {
    expect(new Forecast({
      id: 0,
      cd_region: 21,
      cd_product: 2,
      error: 'error',
      date: 2
    })).toBeTruthy();
  });
});
