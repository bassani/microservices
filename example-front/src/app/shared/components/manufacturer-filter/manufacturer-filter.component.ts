import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ManufacturerService } from '../../services';
import { plural } from '../../utils/utils';

/**
 * Componente de filtro e validação de fabricantes em bulk separados por ; ou pesquisa normal
 * Atualmente sendo utilizado somente na tela de relatorio de vendas, porem essa tela se encontra
 * destativada devido a falta de endpoints e serviços necesarios.
 */
@Component({
  selector: 'app-manufacturer-filter',
  templateUrl: './manufacturer-filter.component.html',
  styleUrls: ['./manufacturer-filter.component.scss']
})
export class ManufacturerFilterComponent implements OnInit {
  @Input('control') control: FormControl = new FormControl([]);
  manufacturersControl: FormControl = new FormControl([]);
  filterControl: FormControl = new FormControl([]);
  manufacturers$ = new BehaviorSubject<any[]>([]);
  separatorErrorList$ = new BehaviorSubject<String[]>([]);
  displayModal: boolean = false;

  constructor(private _manufacturerService: ManufacturerService) { }

  ngOnInit(): void {
    this.manufacturersControl.setValue(this.manufacturersControl.value)
  }

  search(q: any) {
    this.separatorErrorList$.next([])
    console.log('including', q.query)
    if(q.query.includes(';')) return this.validate(q);
    this._manufacturerService
      .search(q.query, 0, 25)
      .pipe(map((data) => data.content))
      .subscribe(
        (data) => this.manufacturers$.next(data),
        (error) => this.manufacturers$.next([])
      );
  }

  selected() {
    let [current, newValue] = [this.manufacturersControl.value || [], this.filterControl.value];
    if(!current.find((el: {id: any, name: any}) => el.id == newValue.id)) this.manufacturersControl.setValue([...current, newValue]);
    this.filterControl.reset();
  }

  removeManufacturer({name, id}: {name: string, id: number}) {
    this.manufacturersControl.setValue(this.manufacturersControl.value.filter((val: any) => val.id != id))
  }

 
  
  get errors() {
    return this.separatorErrorList$.pipe(
      map(errors => {
        return `Fabricante${plural('s', errors)} não encontrado para o${plural('s', errors)} código${plural('s', errors)}: ${errors.join(', ')}`
      }) 
    )
  }

  validate(q: any) {
    this._manufacturerService
    .validateCodes(q.query)
    .subscribe(
      (data) => {
        const prev = this.manufacturersControl.value || [];
        this.manufacturersControl.setValue([
        ...prev, 
        ...data.valid.filter((supplier) => {
          return !prev.find((cSupplier: any) => cSupplier.id === supplier.id)
        })]);
        this.separatorErrorList$.next(data.invalid || []);
        this.manufacturers$.next([]);
        q.originalEvent.target.value = "";
      },
      (error) => this.manufacturers$.next([])
    );
  }

  closeModal() {
    this.displayModal = false;
  }
 showModal() {
  this.manufacturersControl.setValue(this.control.value)
    this.displayModal = true;
  }

  save() {
    this.control.setValue(this.manufacturersControl.value);
    this.displayModal = false;
  }

}
