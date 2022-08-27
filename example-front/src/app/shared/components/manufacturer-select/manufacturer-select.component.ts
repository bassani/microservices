import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { ISearchResponse, ISupplier } from '../../models/search-simulador';
import {
  ManufacturerService,
  page_size,
} from '../../services/manufacturer/manufacturer.service';
import { PAGED_BASE } from '../../utils/mock';

/**
 * Componente de filtro de fabricante com ligação ao fabricante pai
 * No futuro sera solicitada a alteração para poder pesquisar em massa
 * Recomendação de implantação dessa feature:
 * Convençer a PO a utilizar o modelo do componente anterior, para isso tera que solicitar ao back
 * criar um endpoint de validação de codigos 
 * ou
 * Capturar o evento de onchange, verificar se o value atual tem o caracter ;
 * se tiver mandar para uma nova função no service ex. validateAndAdd
 * que vai realizar um trim da string, e depois split da string pelo caracter ;
 * e criar um request http para cada item dentro do array criado no split
 * e retornar um forkJoin, combineLast ou zip dos observables dependendo do tipo de retorno
 * No componente tratar os erros e sucessos
 * nos sucessos adicionar os itens no value do form
 * no erro adicionar ao string de erro para informar qual item tem erro
 */
@Component({
  selector: 'app-manufacturer-select',
  templateUrl: './manufacturer-select.component.html',
  styleUrls: ['./manufacturer-select.component.scss'],
})
export class ManufacturerSelectComponent implements OnInit, OnDestroy {
  manufacturersList: any = [];
  private paging: { page: number; size: page_size } = { page: 0, size: 100 };
  @Input('control') control: FormControl;
  @Input('parentControl') parentControl?: FormControl;
  constructor(private _manufacturerService: ManufacturerService) { }

  ngOnInit(): void {
    this.getManufacturer();
    this.parentControl?.valueChanges
      .subscribe((data) => {
        if (data.length !== 0) this.getManufacturer();
      });
  }

  private getManufacturer() {
    this._manufacturerService.search('', this.paging.page, this.paging.size, this.parentControl?.value)
      .subscribe((value: any) => {
        this.manufacturersList = value;
      });
  }

  get supplierSelected() {
    return (this.parentControl?.value || [])
      .map((m: ISupplier) => m.id)
      .join(', ');
  }

  clearManufacturers() {
    this.parentControl?.setValue([]);
    // this.control?.setValue([]);
    this.manufacturersList.content = [];
  }

  ngOnDestroy() {
  }
}
