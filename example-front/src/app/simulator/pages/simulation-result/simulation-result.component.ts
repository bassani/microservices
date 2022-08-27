import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, ParamMap, Router } from '@angular/router';
import { forkJoin, Observable, of, Subscription } from 'rxjs';
import { filter, map, mergeMap, pairwise, switchMap, tap } from 'rxjs/operators';
import {
  SimulateService,
} from '../../services/simulate/simulate.service';
import { SimulationResult, IDiscount } from 'src/app/shared/models/simulation.model';
import { SIMULATION_TYPES } from '../simulation-type/simulation-type.component';
import { ConfirmationService, Message } from 'primeng/api';

type DOWNLOAD_RESUME = 'mensal' | 'semanal';
/**
 * Componente encarregado de mostrar os resultados gerais da simulação
 */
@Component({
  selector: 'app-simulation-result',
  templateUrl: './simulation-result.component.html',
  styleUrls: ['./simulation-result.component.scss'],
  providers: [ConfirmationService]

})
export class SimulationResultComponent implements OnInit, OnDestroy {
  /**
   * Resultado da simulação, obtido e preenchido com o serviço
   */
  simulation: SimulationResult | any;
  /** tab atual (resumo por CD, resumo por produto) */
  currentTab = 1;
  simulationByCd: any[] = [];
  summarySubscription: Subscription | undefined;
  /** Mensagem toast informando da simulação salva */
  msgs: Message[] = [{severity:'success', summary:'Salvo!', detail:'Simulação salva como rascunho'}];
  /** a resposta do servidor presenta algum erro */
  hasError = false;
  /** ultima url acessada, para poder dar utilizar o botão voltar */
  prevUrl = '';
  constructor(
    private _simulationService: SimulateService,
    private _ar: ActivatedRoute,
    private _router: Router,
    private _confirmationDialog: ConfirmationService
  ) { 
  }

  /**
   * Ao iniciar o componente pequisar os dados de resultado da simulaçao com o param value informado
   */
  ngOnInit(): void {

    this._ar.paramMap
      .subscribe(
        (data) => {
          this.idToSimulation(data)
        },
        (err) => {
          //this._router.navigate(['simulador', 'simular']
        }
      );
  }

  /** Realiza o download do resultado por produto  */
  getDownloadResume(): void {
    this._simulationService.downloadResume(this.simulation.simulationId);
  }

  /** Obtem os parametros e resultados da simulação e os combina em um so objeto
   * caso apresentar erro os parametros ou estiver no status processando e incrementado
   * um timer de retry e intentado novamente apos esse timer concluir
   */
  idToSimulation(params: ParamMap): void {
    const id = params.get('id');
    // se não existe um id não intentar pesquisar os dados
    if (id === undefined || id === null || id === '') {
      throw new Error('Id da simulação inválida');
    }
    // obter parametros da simulação
    this.summarySubscription = this._simulationService.findSimulationById(Number(id))
    .pipe(
      // switch map para o resultado da simulação
      switchMap(response => {
        this.simulation = {id: response.id}
        return this._simulationService.getResult(id).pipe(map(summary => ({result: response, parameters: summary})))
      })
    )
    .subscribe(data => {
      // combina os parametros e os resultados da simulação
      this.simulation = {...data.parameters,...data.result, simulationType: this.getSimulationType({id: data.result.simulationType})};
      // pesquisa os resultados por CD
      this._simulationService.getResultByCd(id).subscribe(data => {
        this.simulationByCd = data;
      })
    }, err => {
      this.simulation = err.id ? {id: err.id} : this.simulation;
      //caso tiver erro setar flag para informar para o usuario
      this.hasError = true;
    });
  }

  /** retorna a lista de cds em formato de string separado por virgulas */
  get cdList(): string | null {
    return (this.simulation?.distributionCenterId || []).map((v: any, idx: number) => `${v}`).join(', ');
  }
  /** retorna os forncedores separados por virgulas ou uma escrita generica de Todos */
  get manufacturers(): string | null {
    const result = this.simulation?.manufacturers
    ? this.simulation.manufacturers.map((v: any) => v.name).join(', ')
    : 'Todos';
    return result.length > 0 ? result : 'Todos';
  }
  /** Obtem os dados normalizados de desconto em formato de string */
  get discount(): Observable<string> {
    let discountItem: IDiscount = {
      discountType: this.simulation.productDiscounts.length > 0 && !this.simulation.generalDiscount ? 0 : this.simulation.generalDiscount?.type,
      skus: this.simulation.productDiscounts,
      discount: this.simulation.generalDiscount?.value
    }
    return of(discountItem || {}).pipe(
      switchMap(
        (discount: IDiscount | undefined): Observable<string> =>
          of(
            {
              1: `desconto adicional de ${discount?.discount}%`,
              2: `novo desconto de ${discount?.discount}%`,
              0: `desconto alterado de ${discount?.skus?.length || 0} produtos`,
              none: 'Parâmetro Regular',
            }[
            (discount?.discountType !== undefined && discount?.discount !== null) ? discount.discountType : 'none'
            ]
          )
      )
    );
  }

  /** Retorna a data da simulação com formato BR */
  getDate(simulation: any) {
    if(!simulation?.simulationDate) return '';
    let simDate = simulation?.simulationDate?.split(' ')[0];
    if(!simDate) return '';
    let simDateReversed = simDate.split('-').reverse().join('/');
    return simDateReversed
  }

  /** Retorna os dados de base de calculo normalizados para string*/
  getCalculationBasis(basis: {id: number, days?: number}): string {
    let bases: any = {1: `Venda dos Últ. ${basis.days} Dias`, 2: 'Forecast Semanal', 3: 'Forecast Mensal'};
    return bases[basis.id];
  }

  /** Retorna os dados de tipo de simulação  */
  getSimulationType(simulationType: {id: number}) {
    return SIMULATION_TYPES.find(sim => sim.id == simulationType.id)
  }

  /** Retorna os dados de fornecedores pai separados por virgula */
  getParentSuppliers(suppliers: {parentSupplierId: number, parentSupplierName?: string}[] = []) {
    return this.asList(suppliers.map(supplier => ({id: supplier.parentSupplierId, name: supplier.parentSupplierName})).filter(el => el.id !== null), 'o')
  }

  /** Retorna uma lista separada por virgulas ou a escrita de Todos */
  asList(list: any[], suffix = 'a'): string {
    return list && list.length > 0 ? list.map(el => el.name || el.id).join(', ') : `Tod${suffix}s`;
  }

  /** Retorna uma lista de itens separados por virgula ou a escrita 5+ suffix informado */
  asLimitedList(arr: any[] = [], suffix: string) {
    return arr.length > 5 ? `5+ ${suffix}` : this.asList(arr)
  }

  /** Navega ate a tela de template */
  finishSimulation(): void {
    this._router.navigate(['simulador','fluxo', this.simulation.id, ])
  }

  /** Voltar a tela anterior */
  back(): void {
    let route = localStorage.getItem('prev') === '/simulador/acompanhar' ? 'acompanhar' : 'simular';
    this._router.navigate(['simulador', route]);
  }

  /** Tentar novamente simular uma simulação com erro
   * TODO finalizar implementação no serviço
   */
  retry(): void {
      this._confirmationDialog.confirm({
        message: 'Essa ação ira criar uma nova simulação com os mesmos parâmetros',
        icon: 'info',
        acceptLabel: 'Simular novamente',
        rejectLabel: 'Cancelar',
        accept: () => {
          this._simulationService.retry(this.simulation.id)
        }
      })
  }

  /** destroy as subscrições pendentes */
  ngOnDestroy() {
    if(!!this.summarySubscription) {
      this.summarySubscription.unsubscribe()
      this.summarySubscription =undefined;
    }
  }

}
