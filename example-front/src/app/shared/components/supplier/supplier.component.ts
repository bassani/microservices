import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MultiSelect } from 'primeng/multiselect';
import { BehaviorSubject, of } from 'rxjs';
import { catchError, map, skipWhile } from 'rxjs/operators';
import { IDistributionCenter } from '../../models/search-simulador';
import { ManufacturerService } from '../../services';
import { SupplierService } from '../../services/supplier/supplier.service';

@Component({
  selector: 'app-supplier',
  templateUrl: './supplier.component.html',
  styleUrls: ['./supplier.component.scss']
})
export class SupplierComponent {
  suppliers: any = {content: [], status: 'READY'};
  filter: string = '';
  @Input('control') control: FormControl = new FormControl();
  constructor(private supplierService: SupplierService) { }



  filterSuppliers($event: any, input: {activateFilter: Function}) {
    let value = this.control.value || [];
    this.filter = $event.filter;
    this.suppliers = {content: [], status: 'LOAD'}
    this.supplierService.search($event.filter).subscribe(data => {
      this.suppliers = {content: [...data.content, ...(value)], status: 'READY'};

    })
  }

}
