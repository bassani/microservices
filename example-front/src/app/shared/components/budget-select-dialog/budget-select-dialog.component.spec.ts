import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MessageService } from 'primeng/api';
import { DialogModule } from 'primeng/dialog';
import { InputNumberModule } from 'primeng/inputnumber';
import { RadioButtonModule } from 'primeng/radiobutton';
import { of } from 'rxjs';
import { ProductService } from '../../services';

import { BudgetSelectDialog, BudgetType } from './budget-select-dialog.component';

describe('ProductFilterComponent', () => {
  let component: BudgetSelectDialog;
  let fixture: ComponentFixture<BudgetSelectDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BudgetSelectDialog ],
      imports: [DialogModule, CommonModule, FormsModule, ReactiveFormsModule, HttpClientModule, InputNumberModule, NoopAnimationsModule, RadioButtonModule],
      providers: [
        {
          provide: MessageService,
          useValue: {
            add: (...args: any[]) => {}
          }
        }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BudgetSelectDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  
  describe('Close Dialog', () => {
    it('Deve salvar as alterações no grupo inputado caso a função de salvar seja chamada', () => {
      const toPatch = {
        type: BudgetType.currency,
        value: 1000,
        reason: 'teste'
      };
      expect(component.budgetForm.getRawValue()).not.toEqual(toPatch);
      fixture.nativeElement.querySelector('#budget-dialog-trigger').click();
      fixture.detectChanges();

      component.budgetLocalForm.patchValue(toPatch);
      fixture.detectChanges();

      fixture.nativeElement.querySelector('#budget-modal-save').click();
      fixture.detectChanges();

      expect(component.budgetForm.getRawValue()).toEqual(toPatch);
    });

    it('Não deve salvar as alterações caso a função de fechar seja chamada', () => {
      const toPatch = {
        type: BudgetType.currency,
        value: 1000,
        reason: ''
      };
      expect(component.budgetForm.getRawValue()).not.toEqual(toPatch);
      fixture.nativeElement.querySelector('#budget-dialog-trigger').click();
      fixture.detectChanges();

      component.budgetLocalForm.patchValue(toPatch);
      fixture.detectChanges();
      fixture.nativeElement.querySelector('#budget-modal-close').click();
      fixture.detectChanges();
      expect(component.budgetForm.getRawValue()).not.toEqual(toPatch);
    });
  });


});
