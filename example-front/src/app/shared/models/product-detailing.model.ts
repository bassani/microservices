export class ProductDetailing {
    quoted: boolean;
    promopack: boolean;
    onlyInactive: boolean;

    constructor(data: ProductDetailing) {
        this.quoted = (data && data.quoted) || false;
        this.promopack = (data && data.promopack) || false;
        this.onlyInactive = (data && data.onlyInactive) || false;
      }
}

