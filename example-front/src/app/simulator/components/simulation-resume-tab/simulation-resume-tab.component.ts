import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApprovalFlowService } from 'src/app/shared/services/approval-flow/approval-flow.service';


@Component({
  selector: 'simulation-resume-tab',
  templateUrl: './simulation-resume-tab.component.html',
  styleUrls: ['./simulation-resume-tab.component.scss']
})
export class SimulationResumeTabComponent implements OnInit {

  resultSummary: any = {};

  constructor(
    private _approvalFlowService: ApprovalFlowService,
    private _ar: ActivatedRoute
  ) { }

  ngOnInit(): void {

  this._ar.paramMap.subscribe((data) => {
    this._approvalFlowService
        .getTemplateSummary(data.get('simulationId'))
        .subscribe((response) => {
          this.resultSummary = response;
      });
    });

  }

}
