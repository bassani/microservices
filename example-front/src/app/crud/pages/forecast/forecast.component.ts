import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PrimeNGConfig } from 'primeng/api';
import { ForecastService } from '../../services/forecast.service';

type TIPO_FORECAST = 'mensal' | 'semanal';

@Component({
  selector: 'app-forecast',
  templateUrl: './forecast.component.html',
  styleUrls: ['./forecast.component.scss']
})
export class ForecastComponent implements OnInit {

  @Input() title: string;
  type: TIPO_FORECAST;
  constructor(
    private route: ActivatedRoute,
    private config: ForecastService,
    private primengConfig: PrimeNGConfig
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => { this.title = params['type']; this.type = params['type']; });
  }

  getDownloadErrors() {
    this.config.downloadErrors(this.type);
  }

  getReprocess() {
    this.config.reprocess(this.type);
  }

  getDownloadCurrent() {
    this.config.downloadCurrent(this.type);
  }

}
