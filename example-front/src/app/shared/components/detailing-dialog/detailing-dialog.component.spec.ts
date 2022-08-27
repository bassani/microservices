import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { SharedModule } from 'primeng/api';
import { CheckboxModule } from 'primeng/checkbox';
import { DropdownModule } from 'primeng/dropdown';
import { NgVarDirective } from '../../directives/ng-var.directive';
import { ApiService } from '../../services';

import { DetailingDialogComponent } from './detailing-dialog.component';

@Component({
  selector: 'Stub',
  template: `<app-detailing-dialog></app-detailing-dialog>`,
})
class StubComponent {
  control: FormControl = new FormControl({
    inactive: null,
    onlyPromopacks: null,
    onlyQuoted: null,
  });
}

describe('DetailingDialogComponent', () => {
  let parentComponent: StubComponent;
  let parentFixture: ComponentFixture<StubComponent>;

  let component: DetailingDialogComponent;
  let fixture: ComponentFixture<DetailingDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        FormsModule,
        CommonModule,
        HttpClientModule,
        SharedModule,
        CheckboxModule,
        DropdownModule
      ],
      declarations: [DetailingDialogComponent, NgVarDirective, StubComponent],
      providers: [ApiService],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    parentFixture = TestBed.createComponent(StubComponent);
    parentComponent = parentFixture.componentInstance;
    fixture = TestBed.createComponent(DetailingDialogComponent);
    component = fixture.componentInstance;

    parentFixture.detectChanges();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Close Dialog', () => {
    it('Deve salvar as alterações no grupo inputado caso a função de salvar seja chamada', () => {
      const toPatch = {
        inactive: true,
        promoPacks: false,
        quoted: true,
        temporaryInactiveCode: 0
      };
      expect(component.control.getRawValue()).not.toEqual(toPatch);
      fixture.nativeElement.querySelector('#details-dialog-trigger').click();
      fixture.detectChanges();

      component.cloneGroup.patchValue(toPatch);
      fixture.detectChanges();

      fixture.nativeElement.querySelector('#detailing-modal-save').click();
      fixture.detectChanges();

      expect(component.control.getRawValue()).toEqual(toPatch);
    });

    it('Não deve salvar as alterações caso a função de fechar seja chamada', () => {
      const toPatch = {
        inactive: true,
        promoPacks: false,
        quoted: true,
        temporaryInactiveCode: 1
      };
      expect(component.control.getRawValue()).not.toEqual(toPatch);
      fixture.nativeElement.querySelector('#details-dialog-trigger').click();
      fixture.detectChanges();

      component.cloneGroup.patchValue(toPatch);
      fixture.detectChanges();
      fixture.nativeElement.querySelector('#detailing-modal-close').click();
      fixture.detectChanges();
      expect(component.control.getRawValue()).not.toEqual(toPatch);
    });
  });


  describe('Check inactives', () => {
    it('Deve mudar o form quando mudar o inactive', () => {
      component.checkInactives(true);
      expect(component.cloneGroup.getRawValue().inactive).toEqual(true);
      component.checkInactives(true);
      expect(component.cloneGroup.getRawValue().inactive).toEqual(null);
    })
  })


});
