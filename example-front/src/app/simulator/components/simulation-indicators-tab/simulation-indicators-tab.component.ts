import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApprovalFlowService } from 'src/app/shared/services/approval-flow/approval-flow.service';


@Component({
  selector: 'simulation-indicators-tab',
  templateUrl: './simulation-indicators-tab.component.html',
  styleUrls: ['./simulation-indicators-tab.component.scss']
})
export class SimulationIndicatorsTabComponent implements OnInit {

  resultSummary: any = {};

  @Input('indicators') indicators: any = {};

  constructor(
    private _approvalFlowService: ApprovalFlowService,
    private _ar: ActivatedRoute
    ) { }

  ngOnInit(): void {
    this._ar.paramMap.subscribe((data) => {
      this._approvalFlowService
          .getcashCicle(data.get('simulationId'))
          .subscribe((response) => {
            this.resultSummary = response;
        });
      });
  }

}
