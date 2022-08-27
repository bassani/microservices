import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';

export enum BudgetType {
  percentage = 2,
  currency = 1
}

@Component({
  selector: 'app-budget-dialog',
  templateUrl: './budget-select-dialog.component.html',
  styleUrls: ['./budget-select-dialog.component.scss']
})
export class BudgetSelectDialog implements OnInit {
  displayModal: boolean = false;
  /** Form representado o valor do componente pai */
  @Input('control') budgetForm: FormGroup = new FormGroup({
    type: new FormControl(BudgetType.percentage),
    value: new FormControl('', Validators.required),
    reason: new FormControl('', Validators.compose([Validators.required, Validators.maxLength(50)])) 
  });
  /** Form representando valores transistivos locais */
  budgetLocalForm: FormGroup;
  /** Enum listando os tipos de verbas */
  budgetTypes = BudgetType;

  constructor(private fb: FormBuilder, private message: MessageService) { }

  /** Inicializa o form local e seta o valor recibido via prop */
  ngOnInit(): void {
    this.budgetLocalForm = this.fb.group({
      type: [BudgetType.percentage],
      value: [null, Validators.required],
      reason: [null, Validators.required]
    })
    this.budgetLocalForm.patchValue(this.budgetForm.getRawValue());
  }

  removeBudget() {
    this.budgetLocalForm.patchValue({
      type: BudgetType.percentage,
      value: null,
      reason: null
    })
    this.budgetLocalForm.reset();
    this.save();
  }

  /** Getter para indentificar no template
   * se o tipo de verba local e porcentagem
   */
  get isPercent() {
    return this.budgetLocalForm.get('type')?.value === BudgetType.percentage;
  }

  /** Getter para indentificar no template
   * se o tipo de verba selecionado e salvo e porcentagem
   */
  get isSavedPercent() {
    return this.budgetForm.get('type')?.value === BudgetType.percentage;
  }

  closeModal() {
    this.displayModal = false;
  }

  showModal() {
    this.budgetLocalForm.setValue(this.budgetForm.value)
    this.displayModal = true;
  }

  save() {
    let formValues = this.budgetLocalForm.getRawValue();
    if(formValues.value && !formValues.reason) {
      return this.message.add({
        severity: 'error',
        summary: 'Motivo obrigatorio',
        detail: 'Verifque se o campo de valor e motivos estão preenchidos corretamente.',
        life: 3000,
        key: 'main'

      })
    }
    this.budgetForm.setValue(this.budgetLocalForm.value);
    this.displayModal = false;
  }

}
