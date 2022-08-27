import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, Subscription } from 'rxjs';
import { debounceTime, map } from 'rxjs/operators';
import { ISimulationType } from 'src/app/shared/models/search-simulador';
import { IBaseSimulation } from 'src/app/shared/models/simulation.model';
import { CdService } from 'src/app/shared/services';
import { Icons } from 'src/app/shared/utils/icons';
import {
  SimulateService,
} from '../../services/simulate/simulate.service';
export const SIMULATION_TYPES = [
  {
    key: 'antecipacao',
    id: 1,
    title: 'Antecipação',
    description:
      'Criar nova simulação de pedido  antecipando a demanda de compra regular. Todos os parâmetros básicos estão considerados na simulação.',
    icon: 'rd-i-calendar',
    customField: 'antecipationDate',
    customLabel: 'Data da antecipação:',
    svg: Icons.agenda
  },
  {
    key: 'valor',
    id: 2,
    title: 'Valor da compra (R$)',
    description:
      'Criar nova simulação de pedido considerando o valor (R$) alvo informado como limitador para projetar a compra.',
    icon: 'pi-calendar',
    customField: 'amount',
    customLabel: 'Valor simulado:',
    svg: Icons.money
  },
  {
    key: 'cobertura',
    id: 4,
    title: 'Cobertura Estoque CD',
    description:
      'Criar nova simulação de pedido limitando o estoque máximo dos CDs em dias de estoque desconsiderando o lead time - TE.',
    svg: Icons.fine_tune,
    customField: 'stockCoverageDC',
    customLabel: 'Cobertura simulada:',
    icon: 'pi-calendar',
    },
];
/**
 * Componente tela de seleção do tipo da simulação
 * Utilizada como primeiro passo para criar uma simulação
 */
@Component({
  selector: 'app-simulation-type',
  templateUrl: './simulation-type.component.html',
  styleUrls: ['./simulation-type.component.scss'],
})
export class SimulationTypeComponent implements OnInit, OnDestroy {
  /** Lista de tipos de simulação, paginada */
  simulationTypes$ = new BehaviorSubject<{
    content: ISimulationType[];
    ready: boolean;
  }>({ content: [], ready: false });
  /** ReactiveFormControl do input de pesquisa */
  searchControl = new FormControl('');
  /** Subscrição as alterações de pesquisa */
  searchSub: Subscription;
  /** Simulação base inicial, criada apos clickar em um tipo de simulação */
  simulation: IBaseSimulation;
  /** Dados mocks do tipo de simulação */
  mockData = SIMULATION_TYPES;
  control = new FormControl({});
  constructor(
    private _router: Router,
    private _simulationService: SimulateService,
    private _ar: ActivatedRoute
  ) {}

  /**
   * No Init
   *  - preenchemos a lista de simulações com dados mockados
   *  - chamamos a função de escutar o input de pesquisa
   */
  ngOnInit(): void {
    this.simulationTypes$.next({ content: this.mockData, ready: true });
    this.listenToSearch();
  }

 

  /**
   * Função que cria o subscriber para o input de pesquisa caso este ja não teja um subscriber
   * Escuta a alterações com um debounce de x ms e apos esse debounce chama a função de pesquisar do serviço
   */
  listenToSearch(): void {
    if (!this.searchSub) {
      this.searchSub = this.searchControl.valueChanges
        .pipe(debounceTime(300))
        .subscribe((q) => {
          this.searchType(q.toLowerCase());
        });
    }
  }

  /**
   * Função de selecionar tipo, recebe um tipo de simulação e cria a base da simulação chamando ao serviço
   * apos a base ser criada com sucesso redireciona para o proximo step da simulação (dados basicos)
   * @param type type of the simulation
   */
  selectType(type: ISimulationType): void {
    this._simulationService.startSimulation(type).subscribe((simulation) => {
      this._router.navigate([
        'simulador',
        'simular',
        type.key.toLowerCase(),
        simulation.id,
      ]);
    });
  }

  /**
   * Chama a função de pesquisar tipos de simulação do servidor
   * ou no caso do mocker preenche com o mock filtrando por nome ou descrição do tipo
   * @param q querysting se pesquisa
   */
  searchType(q: string): void {
    this.simulationTypes$.next({ content: [], ready: false });
    this.simulationTypes$.next({
      content: this.mockData.filter(
        (el) =>
          el.title.toLowerCase().includes(q) ||
          el.description.toLowerCase().includes(q)
      ),
      ready: true,
    });
  }

  /**
   * Ao destruir o componente destruimos a subscription caso ela exista para evitar leaks
   */
  ngOnDestroy(): void {
    if (!this.searchSub.closed) {
      this.searchSub.unsubscribe()
    };
  }
}
