import { Classification } from './classification.model';


describe('ClassificationModel', () => {
  it('should create an instance', () => {
    expect(new Classification({
      name: 'Gabriel',
      description: 'Descrição',
      id: 212,
      active: true
    })).toBeTruthy();
  });
});
