import { SimulationClassification } from './simulation-classification.model';


describe('SimulationClassificationModel', () => {
  it('should create an instance', () => {
    expect(new SimulationClassification({
      name: 'Daniel',
      id: 789432,
    })).toBeTruthy();
  });
});
