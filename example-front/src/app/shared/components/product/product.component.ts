import { BehaviorSubject, of } from 'rxjs';
import { FormControl } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';
import { ISearchResponse, IProduct } from '../../models/search-simulador';
import { ProductService } from '../../services/product/product.service';
import { PAGED_BASE } from '../../utils/mock';
import { catchError, map } from 'rxjs/operators';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss'],
})
export class ProductComponent implements OnInit {
  products$ = new BehaviorSubject<IProduct[]>([]);
  /* products$ = new BehaviorSubject<ISearchResponse<IProduct>>({ content: [], page: 0, size: 999, numberOfElements: 0 }); */
  @Input('control') control = new FormControl();
  @Input('suppliers') suppliers: any[] = [];
  @Input('categories') categories: any[] = [];
  @Input('subcategories') subcategories: any[] = [];
  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.getProducts(null);
  }

  getProducts($e: any | null): void {
    this.productService
      .getProducts({
        query: $e?.query || '',
        supplier: (this.suppliers).map(el => el.id),
        category: (this.categories).map(el => el.id),
        subcategory: (this.subcategories).map(el => el.id)
      })
      .pipe(
        map((data) => data.content),
        catchError(err => of([]))
      )
      .subscribe(
        (data) => {
          this.products$.next(data);
        }
      );
  }

}
