import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { element } from 'protractor';
import { BehaviorSubject } from 'rxjs';
import { INATIVOS_TEMPORARIOS } from '../../consts/filters';
import {
  IDetailingDialog,
  ISearchResponse,
} from '../../models/search-simulador';
import { DetailingDialogService } from '../../services/detailing-dialog/detailing-dialog.service';
import { PAGED_BASE } from '../../utils/mock';

@Component({
  selector: 'app-detailing-dialog',
  templateUrl: './detailing-dialog.component.html',
  styleUrls: ['./detailing-dialog.component.scss'],
})
export class DetailingDialogComponent implements OnInit {
  displayDialog: boolean = false;
  optionsDetailingDialilog$ = new BehaviorSubject<
    ISearchResponse<IDetailingDialog>
  >(PAGED_BASE);
  deactivateQuoted = true;
  temporaryInactivesOptions = [
    {value: INATIVOS_TEMPORARIOS.CONSIDERAR_INATIVOS_TEMPORARIOS, name: 'Considerar inativo temporário'},
    {value: INATIVOS_TEMPORARIOS.SOMENTE_INATIVOS_TEMPORARIOS, name: 'Somente inativo temporário'},
  ]
  promopacksOptions = [
    {value: true, name: 'Somente promopacks'},
    {value: false, name: 'Desconsiderar promopacks'},
  ]
  quotedOptions = [
    {value: true, name: 'Somente cotados'},
    {value: false, name: 'Desconsiderar cotados'},
  ]
  constructor(private _service: DetailingDialogService) {}

  @Input('control') control: FormGroup = new FormGroup({
    promoPacks: new FormControl(null),
    quoted: new FormControl(null),
    inactive: new FormControl(null),
    temporaryInactiveCode: new FormControl(INATIVOS_TEMPORARIOS.NAO_SE_APLICA),
  });

  cloneGroup: FormGroup = new FormGroup({
    promoPacks: new FormControl(null),
    quoted: new FormControl(null),
    inactive: new FormControl(null),
    temporaryInactiveCode: new FormControl(INATIVOS_TEMPORARIOS.NAO_SE_APLICA),
  });

  ngOnInit(): void {
    this.cloneGroup.setValue(this.control.getRawValue());
  }

  saveDetailing() {
    let clone = this.cloneGroup.getRawValue();
    this.control.setValue({...clone, temporaryInactiveCode: clone.temporaryInactiveCode || INATIVOS_TEMPORARIOS.NAO_SE_APLICA});
    this.closeDialog();
  }

  /** Em antecipar simulação, ao clicar em filtrar itens, o modal é acionado.*/
  showDialog(): void {
    this.cloneGroup.setValue(this.control.getRawValue());
    this.displayDialog = true;
  }

  /* Fecha o modal filtrar itensd, ao clicar no botão cancelar. */
  closeDialog(): void {
    this.displayDialog = false;
  }

  /** Cahama o checkValue do campo inactive para o campo somente inativos */
  checkInactives(val: boolean) {
    let current = this.cloneGroup.getRawValue().inactive;
    let patchValue: any = {};
    patchValue.inactive = val === current ? null : val;
    this.cloneGroup.patchValue(patchValue);
  }

  get selected() {
    let raw = this.control.getRawValue();
    let inactives = raw.inactive == true;
    let result = [
      !!inactives ? 'Somente Inativos' : null,
      this.promopacksOptions.find(item => item.value === raw.promoPacks)?.name || null,
      this.quotedOptions.find(item => item.value === raw.quoted)?.name || null,
      this.temporaryInactivesOptions.find(item => item.value === raw.temporaryInactive)?.name || null
    ];
    return result.filter((val) => val !== null);
  }


  getValue(field: 'promoPacks' | 'quoted' | 'inactive') {
    let val = this.cloneGroup.getRawValue()[field];
    return val !== null ? val.toString() : 'null';
  }

  clearTemporaryInactive() {
    this.cloneGroup.patchValue({temporaryInactiveCode: INATIVOS_TEMPORARIOS.NAO_SE_APLICA})
  }
}
