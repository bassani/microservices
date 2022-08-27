import { IFlowExpiration } from './../../models/flow-expiration.model';
import { FormControl } from '@angular/forms';
import { Component, Input } from "@angular/core";


@Component({
  selector: 'app-flow-simulate-select',
  templateUrl: './flow-simulate-select.component.html',
  styleUrls: ['./flow-simulate-select.component.scss']
})
export class FlowSimulateComponent {
  idFlowSimulate: Array<IFlowExpiration> = [
    { label: '1 dia', id: '1' },
    { label: '2 dias', id: '2' },
    { label: '3 dias', id: '3' },
    { label: '4 dias', id: '4' },
    { label: '5 dias', id: '5' }
  ];

  @Input('control') control: FormControl = new FormControl();
  constructor() { }

}