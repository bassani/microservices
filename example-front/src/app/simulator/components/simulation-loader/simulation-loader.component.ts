import { Component, Input, OnInit } from '@angular/core';
import {
  asyncScheduler,
  BehaviorSubject,
  interval,
  Observable,
  of,
  Subscription,
  throwError,
  timer
} from 'rxjs';
import { scheduleObservable } from 'rxjs/internal/scheduled/scheduleObservable';
import {
  catchError,
  concatMap,
  delay,
  map,
  mapTo,
  switchMap,
  take,
  tap,
  timeout
} from 'rxjs/operators';



@Component({
  selector: 'simulating',
  template: `
    <div class="p-d-flex full-width p-relative p-flex-wrap">
        <span role="heading" [class]="'header p-ml-2 p-col-12 ' + (message.period < 4999 && !showError ? 'loading' : '')">
            {{showError ? ERR_PROCESSING.message : message.message }} 
        </span>
        <span class="p-ml-2 p-col-12 subheader">
        {{ showError ? ERR_PROCESSING.details : message.details }} 
    </span>
        <img class="full-width p-mt-3 loader-img" [src]="showError ? ERR_PROCESSING.url : message.url"/>
    </div>
  `,
  styleUrls: ['./simulation-loader.component.scss']
})
export class LoadingSimulationComponent implements OnInit {
  messages = [
    {
      message: 'Iniciando simulação',
      details: 'Isso pode demorar alguns minutos',
      period: 8,
      url: '/assets/imgs/undraw/undraw_Adjustments.svg',
      idx: 0
    },
    {
      message: 'Compilando dados',
      details: 'Isso pode demorar alguns minutos',
      period: 8,
      url: '/assets/imgs/undraw/undraw_advanced_customization.svg',
      idx: 1
    },
    {
      message: 'Aplicando calculos',
      details: 'Isso pode demorar alguns minutos',
      period: 8,
      url: '/assets/imgs/undraw/undraw_Career_development.svg',
      idx: 2
    },
    {
      message: 'Salvando os resultados',
      details: 'Isso pode demorar alguns minutos, só mais um momento',
      period: 8,
      url: '/assets/imgs/undraw/undraw_Mind_map.svg',
      idx: 3
    },
    {
      message: 'Preparando a visualização',
      details: 'Realizando os últimos ajustes nos dados',
      period: 84,
      url: '/assets/imgs/undraw/undraw_publish_post.svg',
      idx: 4
    }
  ];
  message = this.messages[0];
  @Input('showError') showError = false;
  ERR_PROCESSING = {
    message: 'Erro ao processar',
    details: 'Verifique a sua conexão e tente novamente mais tarde.',
    period: 5000,
    url: '/assets/imgs/undraw/undraw_warning.svg',
    idx: 999999
  };
  private multiplierMS = 1000;
  private maxTimeoutMS = 300000;
  subs: Subscription;
  constructor() {}

  ngOnInit() {
    interval(this.multiplierMS)
      .pipe(
        concatMap(x => timer(this.multiplierMS * this.message.period).pipe(mapTo(x))),
        map(data => {
          return this.messages[data] || this.messages[this.messages.length-1];
        }),
        timeout(this.maxTimeoutMS),
        catchError(err => {
          return throwError(this.ERR_PROCESSING);
        })
      )
      .subscribe(data => {
            this.message = data;
      }, err => {
            this.message = err;
            this.showError = true;
      });
  }

  ngOnDestroy() {
      if(this.subs) {
          this.subs.unsubscribe()
      }
  }
}
