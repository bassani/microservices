import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import {
  IProductCategory,
  IProductSubcategory,
  ISearchResponse,
  ISupplier,
} from './../../models/search-simulador';
import { FormControl } from '@angular/forms';
import { BehaviorSubject, Subscription } from 'rxjs';
import { CategoryService } from '../../services';
import { PAGED_BASE } from '../../utils/mock';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss'],
})
export class CategoryComponent implements OnInit, OnDestroy {
  @Input() showSubcategory = true;
  category$ = new BehaviorSubject<ISearchResponse<IProductCategory>>(
    PAGED_BASE
  );
  subcategory$ = new BehaviorSubject<ISearchResponse<IProductSubcategory>>(
    PAGED_BASE
  );
  subscription: Subscription;
  @Input('category') categoryControl: FormControl = new FormControl([]);
  @Input('subcategory') subcategoryControl: FormControl = new FormControl([]);
  @Input('manufacturer') manufacturer: FormControl = new FormControl([]);
  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.getCategories();
    if (!this.subscription && this.manufacturer) {
      this.subscription = this.manufacturer.valueChanges
        .subscribe((data) => {
          if (data) this.getCategories();
        });
    }
  }

  getCategories() {
    this.categoryService
      .getAllCategories(this.manufacturer?.value)
      .subscribe((data) => {
        this.category$.next(data);
        this.categoryControl.setValue((this.categoryControl.value || []).filter((category: any) => {
          const foundCategory = this.category$.value.content.find((li: any) => li.id == category.id);
          this.subcategoryControl.setValue(this.subcategoryControl.value.filter((sub: any) => sub.categoryId != category.id))
          return !!foundCategory
        }))
      });
  }

  get manufacturersSelected() {
    return (this.manufacturer.value || [])
      .map((m: ISupplier) => m.id)
      .join(', ');
  }

  getSubcategories($e?: any) {
    this.categoryService
      .getAllSubcategories(this.categoryControl.value, this.manufacturer.value)
      .subscribe((data) => this.subcategory$.next({ ...this.subcategory$.value, content: [...data.content], numberOfElements: data.numberOfElements || 0 }));

    if (!this.categoryControl.value.find((e: any) => e?.id == $e?.value?.id)) {
      this.subcategoryControl.setValue((this.subcategoryControl.value || []).filter((el: any) => el.categoryId !== $e.itemValue.id))
      return this.subcategory$.next({ ...this.subcategory$.value, content: this.subcategory$.value.content.filter((el: any) => el.categoryId != $e.itemValue.id) });
    }
  }
  clearManufacturers() {
    this.manufacturer.setValue([]);
  }

  ngOnDestroy() {
    if (!this.subscription.closed) this.subscription.unsubscribe();
  }
}
