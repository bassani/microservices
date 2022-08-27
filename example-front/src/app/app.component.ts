import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, RoutesRecognized } from '@angular/router';
import { PrimeNGConfig } from 'primeng/api';
import { Subscription } from 'rxjs';
import { filter, pairwise } from 'rxjs/operators';
import { AuthService } from './shared/services';

/**
 * Ponto de entrada do sistema
 * Contem funções axuliares na operação como listeners para o router
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'example-front';
  /** subscription de eventos do router */
  routeSubscription: Subscription;
  constructor(
    private primengConfig: PrimeNGConfig,
    private _router: Router,
    private _auth: AuthService
  ) {
    this.listenRoutes();
  }

  /**
   * Ao inicializar o sistema
   * e setada a configuração de tradução para o calendario e modais
   */
  ngOnInit() {
    /** ativar efeitos ripple */
    this.primengConfig.ripple = true;

    /** traduções */
    this.primengConfig.setTranslation({
      //translations
      accept: 'Aceitar',
      reject: 'Cancelar',
      dayNamesMin: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
      monthNames: [
        'Janeiro',
        'Fevereiro',
        'Março',
        'Abril',
        'Maio',
        'Junho',
        'Julho',
        'Agosto',
        'Setembro',
        'Outubro',
        'Novembro',
        'Dezembro',
      ],
    });
  }

  /**
   * Função encarregada de escutar as mudanças nas rotas
   * para poder identificar a ultima rota acessada
   * e fazer handling das rotas 404 e 401
   * Utilizado em contextos onde e necesario saber a fonte anterior da pagina
   */
  listenRoutes(): void {
    localStorage.setItem('prev', '/');
    this.routeSubscription = this._router.events
      .pipe(
        filter((evt: any) => evt instanceof RoutesRecognized),
        pairwise()
      )
      .subscribe(
        (events: RoutesRecognized[]) => {
          let [prev] = events.map((r) => r.urlAfterRedirects);
          if(!(['401', '404'].includes(prev))) localStorage.setItem('prev', prev);
        },
        (err) => {}
      );
  }

  /** Ao destruir remove a subscripção */
  ngOnDestroy(): void {
    this.routeSubscription.unsubscribe();
  }
}
