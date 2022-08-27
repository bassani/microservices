import { IAntecipationSimulation, ISkus } from '../../simulator/services/simulate/simulate.service';
import { Discount } from './discount.model';
import { ProductDetailing } from './product-detailing.model';

export class AntecipationModel {

  distributionCenters: [];
  manufacturers: [];
  category: number;
  subcategory: number;
  simulationType: number;
  userId: number;
  orderType: number;
  classification: number;
  specification: IAntecipationSimulation;
  anticipationDate: Date;
  paymentTerm: number;
  note: string;
  saleType: number;
  sale: number;
  discount: Discount;
  productDetailing: ProductDetailing;
  
  constructor() { }
}
