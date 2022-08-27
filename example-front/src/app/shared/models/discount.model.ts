import { DISCOUNT_TYPE } from "../components/discount-modal/discount-modal.component";
import { ISkus } from "./simulation.model";

export class Discount {
    discountType: DISCOUNT_TYPE;
    skus: ISkus[] = [];
    discount: number;
}