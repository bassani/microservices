import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { IdSimulationService } from '../../services/id-simulation/id-simulation.service';

@Component({
  selector: 'app-id-simulation',
  templateUrl: './id-simulation.component.html',
  styleUrls: ['./id-simulation.component.scss']
})
export class IdSimulationComponent{
  selectedSimulations: any[];
  idSimulation: any[];
  @Input('control') control: FormControl = new FormControl();
  constructor(private idSimulationService: IdSimulationService) { }

  filterIDSimulation() {
    this.idSimulationService.searchIDs().subscribe(data => {
      this.idSimulation = data;
      this.idSimulation.forEach((element: any) => {
        element.id = element.id.toString();
      });
    });
  }

  ngOnInit() {
    this.filterIDSimulation();
  }
}
