import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-simulation-result-cd',
  templateUrl: './simulation-result-cd.component.html',
  styleUrls: ['./simulation-result-cd.component.scss']
})
export class SimulationResultCdComponent implements OnInit {
  @Input('simulationByCd') simulationByCd: any[] = [];
  constructor() { }

  ngOnInit(): void {
  }

}
