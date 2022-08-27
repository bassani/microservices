import { Component, Input, OnInit } from '@angular/core';
import { SimulateService } from '../../services/simulate/simulate.service';

/**
 * Componente encarregado de mostrar o botão de download e realizar o download do resumo da simulação
 */
@Component({
  selector: 'app-simulate-export',
  templateUrl: './simulate-export.component.html',
  styleUrls: ['./simulate-export.component.scss']
})
export class SimulateExportComponent implements OnInit {

  @Input('simulationId') simulationId: number;
  constructor(private simulationService: SimulateService) { }

  ngOnInit(): void {
  }

  exportResult() {
    this.simulationService.downloadResume(this.simulationId)
  }

}
