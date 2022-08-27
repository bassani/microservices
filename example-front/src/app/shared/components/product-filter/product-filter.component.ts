import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProductService } from '../../services';

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.scss']
})
export class ProductFilterComponent implements OnInit {
  displayModal: boolean = false;
  @Input('control') control: FormControl = new FormControl([]);
  productsControl: FormControl = new FormControl([]);
  filterControl: FormControl = new FormControl();
  products$ = new BehaviorSubject<any[]>([]);
  separatorErrorList$ = new BehaviorSubject<String[]>([]);

  constructor(private _productService: ProductService) { }

  ngOnInit(): void {
    this.productsControl.setValue(this.control.value)

  }

  
  getProducts($e: any | null): void {
    this.separatorErrorList$.next([])
    if(!!$e && $e.query.includes(';')) return this.validate($e);
    this._productService
      .getProducts($e ? $e.query : '')
      .pipe(map((data) => data.content || []))
      .subscribe(
        (data) => {
          this.products$.next(data);
        },
        (err) => this.products$.next([])
      );
  }

  
  plural(character: string, array: any[]): string {
    return `${array.length>1?character:''}`
  }
  
  get errors() {
    return this.separatorErrorList$.pipe(
      map(errors => {
        return `Produto${this.plural('s', errors)} não encontrado para o${this.plural('s', errors)} codigo${this.plural('s', errors)}: ${errors.join(', ')}`
      }) 
    )
  }

  validate(q: any) {
    this._productService
    .validateCodes(q.query)
    .subscribe(
      (data) => {
        let prev = this.productsControl.value || [];
        this.productsControl.setValue([...prev, ...data.valid.filter((product) => {
          return !prev.find((cProd: any) => cProd.id === product.id)
        })]);
        this.separatorErrorList$.next(data.invalid || []);
        this.products$.next([]);
        q.originalEvent.target.value = "";
      },
      (error) => this.products$.next([])
    );
  }

  
  selected() {
    let [current, newValue] = [this.productsControl.value || [], this.filterControl.value];
    if(!current.find((el: {id: any, name: any}) => el.id == newValue.id)) this.productsControl.setValue([...current, newValue]);
    this.filterControl.reset();
  }

  removeProduct({name, id}: {name: string, id: number}) {
    this.productsControl.setValue(this.productsControl.value.filter((val: any) => val.id != id))
  }

  closeModal() {
    this.displayModal = false;
  }

  showModal() {
    this.productsControl.setValue(this.control.value)
    this.displayModal = true;
  }

  save() {
    this.control.setValue(this.productsControl.value);
    this.displayModal = false;
  }

}
