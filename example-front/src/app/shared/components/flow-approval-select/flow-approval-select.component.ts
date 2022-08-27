import { IFlowExpiration } from './../../models/flow-expiration.model';
import { FormControl } from '@angular/forms';
import { AfterViewInit, Component, Input } from "@angular/core";

@Component({
  selector: 'app-flow-approval-select',
  templateUrl: './flow-approval-select.component.html',
  styleUrls: ['./flow-approval-select.component.scss']
})
export class FlowApprovalComponent {
  idFlowApproval: Array<IFlowExpiration> = [
    { label: '1 dia', id: '1' },
    { label: '2 dias', id: '2' },
    { label: '3 dias', id: '3' },
    { label: '4 dias', id: '4' },
    { label: '5 dias', id: '5' }
  ];

  @Input('control') control: FormControl = new FormControl();

  constructor() { }
}