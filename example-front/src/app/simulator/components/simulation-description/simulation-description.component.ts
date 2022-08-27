import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { map, take, takeLast } from 'rxjs/operators';
import { IDistributionCenter } from 'src/app/shared/models/search-simulador';
import { IBaseSimulation } from 'src/app/shared/models/simulation.model';
import { CdService } from 'src/app/shared/services';

@Component({
  selector: 'app-simulation-description',
  templateUrl: './simulation-description.component.html',
  styleUrls: ['./simulation-description.component.scss'],
})
export class SimulationDescriptionComponent implements OnInit {
  @Input('simulation') simulation: IBaseSimulation;
  @Input('subtitle') subtitle: string;
  cds: Observable<IDistributionCenter[]>;
  constructor(private _cdService: CdService) {}

  ngOnInit(): void {
    this.cds = this._cdService.search().pipe(
      map((data) => data.content),
      takeLast(1)
    );
  }

  get cdList() {
    return this.simulation.distributionCenters.map((e) => e.id).join(', ');
  }
  get manufacturerList() {
    return this.simulation.suppliers?.map((e) => e.name).join(', ');
  }

  plural(value: any[] = [], suffix: string = 's') {
    return !value || value.length <= 1 ? '' : suffix;
  }

  get category() {
    if (this.simulation.category && this.simulation.category.length > 0) {
      return this.simulation.category.map(el => el.name).join(', ');
    } else {
      return 'Todas';
    }
  }
  get subcategory() {
    if (this.simulation.subcategory && this.simulation.subcategory.length > 0) {
      return this.simulation.subcategory.map(el => el.name).join(', ');
    } else {
      return 'Todas';
    }
  }
}
