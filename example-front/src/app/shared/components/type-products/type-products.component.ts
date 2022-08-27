import { PAGED_BASE } from './../../utils/mock';
import {
  ITypeProductsItem,
  ISearchResponse,
} from './../../models/search-simulador';
import { BehaviorSubject } from 'rxjs';
import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { TypeProductsService } from '../../services/type-products/type-products.service';
import { ValueConverter } from '@angular/compiler/src/render3/view/template';

@Component({
  selector: 'app-type-products',
  templateUrl: './type-products.component.html',
  styleUrls: ['./type-products.component.scss'],
})
export class TypeProductsComponent implements OnInit {
  typeProducts$ = new BehaviorSubject<ISearchResponse<ITypeProductsItem>>(
    PAGED_BASE
  );
  options = [
    { name: 'Somente Inativos', value: 1 },
    { name: 'Desconsiderar Promopacks', value: 2 },
    { name: 'Somente Promopacks', value: 3 },
    { name: 'Desconsiderar Cotados', value: 4 },
    { name: 'Somente Cotados', value: 5 },
  ];
  @Input('control') control = new FormControl();
  constructor(private typeProductService: TypeProductsService) {}

  ngOnInit(): void {}
}
