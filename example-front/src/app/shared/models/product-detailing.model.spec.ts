import { ProductDetailing } from './product-detailing.model';

describe('ProductDetailingModel', () => {
  it('should create an instance', () => {
    expect(new ProductDetailing({
      quoted: true,
      promopack: true,
      onlyInactive: true
    })).toBeTruthy();
  });
});
