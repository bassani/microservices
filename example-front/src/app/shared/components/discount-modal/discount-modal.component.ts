import { Component, Input, OnInit } from '@angular/core';
import { MessageService, PrimeNGConfig } from 'primeng/api';

import { FormControl, FormGroup, Validators } from '@angular/forms';
import { IBaseSimulation } from '../../models/simulation.model';
import { InputNumber } from 'primeng/inputnumber';
export enum DISCOUNT_TYPE {
  PER_PRODUCT = 0,
  ADD = 1,
  REPLACE = 2,
}

@Component({
  selector: 'app-discount-modal',
  templateUrl: './discount-modal.component.html',
  styleUrls: ['./discount-modal.component.scss'],
})
export class DiscountModalComponent implements OnInit {
  displayModal: boolean = false;
  constructor(private primengConfig: PrimeNGConfig) {}

  @Input('control') control: FormGroup = new FormGroup({
    discount: new FormControl(null, Validators.compose([Validators.min(0.0001), Validators.max(100)])),
    productsCovered: new FormControl('all'),
    discountType: new FormControl(DISCOUNT_TYPE.ADD),
    skus: new FormControl([]),
  });
  @Input('simulation') simulation: IBaseSimulation = {
    simulationType: {id: 0, key: '', title: '', icon: '', description: ''},
    distributionCenters: [],
    userId: 0,
    registerDate: new Date(),
    suppliers: [],
    category: [],
    subcategory: []
  };

  allDiscounts = { product: 'Todos os produtos', discount: null };
  etherealProduct = { product: '', discount: '' };

  perProducts: any[] = [];
  discountForm = new FormGroup({
    productsCovered: new FormControl('all'),
    discountType: new FormControl(DISCOUNT_TYPE.ADD),
    skus: new FormControl([]),
    discount: new FormControl(null),
  });
  etherealForm = new FormGroup({
    product: new FormControl(null, Validators.required),
    discount: new FormControl(null, Validators.compose([Validators.required, Validators.min(0.001), Validators.max(100)])),
  });

  ngOnInit(): void {
  }

  get DISCOUNT() {
    return DISCOUNT_TYPE
  }

  addProductDisctount(): void {
    let etherealValue = this.etherealForm.getRawValue();
    if(this.etherealForm.invalid || typeof etherealValue.product == 'string') return;
    if (this.perProducts.find((val) => val.skuId == etherealValue.product.id))
      return this.etherealForm.reset();
    this.perProducts.push({
      skuId: etherealValue.product.id,
      name: etherealValue.product.name,
      discount: etherealValue.discount,
      discountType: this.discountForm.getRawValue().discountType,
    });
    this.etherealForm.reset();
  }

  removeProductDiscount(product: any): void {
    this.perProducts = this.perProducts.filter(
      (item) => item.skuId != product.skuId
    );
  }

  getProductControl(control: string): FormControl {
    return this.etherealForm.get(control) as FormControl;
  }

  save(): void {
    let result = this.discountForm.getRawValue();
    result.discountType =
      result.productsCovered != 'all' ? DISCOUNT_TYPE.PER_PRODUCT : result.discountType;
    result.skus = result.productsCovered != 'all' ? this.perProducts : [];
    this.control.setValue(result);
    this.perProducts = result.skus;
    this.closeModal();
  }

  changeTab($event: { index: number }): void {
    this.discountForm.patchValue({
      productsCovered: $event.index > 0 ? 'product' : 'all',
    });
  }

  /** Em antecipar simulação, ao clicar em descontos, o modal é acionado.*/
  showModal(): void {
    if (this.control.getRawValue().discount) {
      this.discountForm.patchValue({
        discount: this.control.getRawValue().discount,
        skus: this.control.getRawValue().skus,
        discountType: this.control.getRawValue().discountType,
        productsCovered: this.control.getRawValue().productsCovered || 'all',
        
      });
    }
    this.perProducts = this.control.getRawValue()?.skus || [];
    this.displayModal = true;
  }

  /* Fecha o modal de descontos, ao clicar no botão cancelar. */
  closeModal(): void {
    this.perProducts = [];
    this.displayModal = false;
  }
}
