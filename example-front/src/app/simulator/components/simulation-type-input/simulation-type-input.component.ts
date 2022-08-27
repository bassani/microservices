import { Component, Input, OnInit, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ISimulationType } from 'src/app/shared/models/search-simulador';


@Component({
  selector: 'div[with-simulation-dynamic-field]',
  templateUrl: './simulation-type-input.component.html',
  styleUrls: ['./simulation-type-input.component.scss'],
})
export class SimulationTypeInputComponent implements OnInit {
  minDateValue: Date = new Date();

  @Input('form') form: FormGroup;
  @Input('simulationType') simulationType: ISimulationType;
  constructor() { }

  ngOnInit(): void {
  }

  getControl(lb: string): FormControl {
    return this.form.get(lb) as FormControl;
  }
}
