import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api';
import { BehaviorSubject, Observable, of, throwError } from 'rxjs';
import { catchError, delay, map, retryWhen, switchMap, tap } from 'rxjs/operators';
import { DISCOUNT_TYPE } from 'src/app/shared/components/discount-modal/discount-modal.component';
import { Path } from 'src/app/shared/consts/path';
import {
  ISimulationType,
} from 'src/app/shared/models/search-simulador';
import { BaseSimulation, IBaseSimulation, IDiscount, SimulationResult } from 'src/app/shared/models/simulation.model';
import { ApiService, AuthService } from 'src/app/shared/services';
import { genericRetryStrategy } from 'src/app/shared/utils/utils';
import { saveAs } from 'file-saver';
import { log } from 'console';
import { IClassification } from 'src/app/shared/models/classification.model';
import { IPaged } from 'src/app/shared/models/common.model';

/** Status da simulação
 * Processing, foi enviado para o back e o backend esta processando, utilizado
 * muito em logicas de pooling
 * Draft, ja foi processado pelo back e gerou um resultado, a simulação se torna um rascunho
 * Error, a simualção deu erro ao processar
 */
enum SimulationStatus {
  processing = 'PROCESSING',
  draft = 'DRAFT',
  error = 'ERROR'
}


/**
 * Serviço central e mais complexo do sistema, encarregado de gerenciar a comunicação e
 * as simulações
 */
@Injectable({
  providedIn: 'root',
})
export class SimulateService {
  /** behaviour subject para manter registro de todas as simulações in-memory */
  simulations$: BehaviorSubject<{
    [id: number]: BaseSimulation;
  }> = new BehaviorSubject<{ [id: number]: BaseSimulation }>({});
  /** instancia save as para salvar arquivos */
  save = saveAs;

  /** endpoints utilizados dentro do serviço */
  private endpoints = {
    downloadErrors: 'errors',
    reprocess: 'reprocess',
    downloadCurrent: 'actives',
    simulate: 'simulations'
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private api: ApiService,
    private _auth: AuthService
  ) { }

  /** realiza o download do resumo da simulação (resultado) */
  downloadResume(id: string | number): void {
    const mock = new Blob([''], { type: 'application/octet-stream;charset=UTF-8' });
    const headers = new HttpHeaders().set(
      'Content-Type',
      'application/octet-stream;charset=UTF-8'
    );
    this.api
      .request<string>({
        mock: false,
        mocker: of(mock), // mock em caso de execução mockada
        http: this.http.get(
          `${Path.HTTP_API_BASE}/simulations/${id}/download`,
          { headers: headers, responseType: 'blob' as 'json' }
        ),
      })
      .subscribe(
        (data) => {
          /** mostar mensagem de sucesso e e salvo o blob */
          this.messageService.add({
            severity: 'success',
            summary: 'Arquivo gerado com sucesso',
            detail: 'O download começará em breve.',
            key: 'main',
          });
          this.save(data, `resultado_simulação_${id}.xls`);

        },
        (err) => {
          /** mostrar mensagem de erro */
          this.messageService.add({
            severity: 'error',
            summary: 'Erro ao gerar arquivo',
            detail: 'Tente novamente mais tarde.',
            key: 'main',
          });
        }
      );
  }


  /**
   * Função principal que consome os dados de parametro de simulação
   * chama funções de formatação para enviar ao back um modelo normalizado
   * e enviar o comando de criar simulação ao bff
   * @param data dados da simulação
   * @param id id da simulação se existir
   */
  patchSpecifics(data: any, id: number): Observable<IBaseSimulation> {
    const res = this.simulations$.value;
    // TODO unpoop this
    /** Normaliza os dados de calculation baasis, devido a construção
     * do componente calculation basis e necesaria essa normalização
     */
    if(!!data.calculationBasis && data.calculationBasis.parent) {
      data.calculationBasis.id = data.calculationBasis.parent
    }
    /** Alterar o nome do atributo para onlyFilter, pois o backend recebe esse parametro
     * nesse formato.
     * TODO mudar o componente para utilizar o mesmo atributo do back, evitando esse mapeamento
     */
    data.productFilter.onlyActive = !data.productFilter.inactive;
    delete data.productFilter.inactive;

    /** verifica se o antecipation date para o tipo de simulação existe
     * caso existir e convertida a uma string o objeto de data
     */
    if(data.anticipationDate) {
      data.anticipationDate = typeof data.anticipationDate === 'string' ? data.antecipationDate : data.anticipationDate.toISOString().split('T')[0]
    }

    /** cria a base da simulação */
    if(!res[id]) res[id] = data;
    res[id] = { ...res[id], ...data };

    /**
     * Mapping dos parametros para um modelo normalizado
     * !Importante esse maper deve ser alterado caso o modelos de parametros mudo
     */
    let newRes = this.mapToRequest(res[id]);

    /** Request em si do envio para o BFF */
    return this.api.request<BaseSimulation>({
      mock: false,
      mocker: of(newRes),
      http: this.http.post(`${Path.HTTP_API_BASE}/${this.endpoints.simulate}`, newRes)
    }).pipe(
      tap((val) => {
        res[id] = val;
        this.simulations$.next(res);
      })
    );
  }

  /**
   * Atualiza os dados basicos de uma simulação in-memory
   * @param data parametros basicos da simulação
   * @param id id da simulação in-memory
   */
  patchBasics(data: any, id: number | undefined): Observable<BaseSimulation> {
    /** Se a simulação não existir na memoria então e informado do erro */
    if (id === undefined){
      return throwError({
        status: 404,
        error: { message: 'Simulação não encontrada' },
      });
    }
    const res = this.simulations$.value;
    /** criado objeto patcher */
    res[id] = { ...res[id], ...data };

    /** Criando respota puramente mockada
     * Pode ser incluido como melhoria no futuro salvar esses primeiros parametros basicos
     * no servidor em vez do in-memory
     * Não realiza nenhum request!
     */
    return this.api.request<any>({
      mock: true,
      mocker: of(res[id]),
      http: of(res[id])
    }).pipe(
      tap((val) => {
        const list = this.simulations$.value;
        list[id] = val;
        this.simulations$.next(list);
      })
    );
  }

  /**
   * Retorna uma simulação da lista de simulações in-memory
   * @param id id da simulação
   */
  getSimulation(id: number): Observable<BaseSimulation> {
    const sim = this.simulations$.value[id];
    return of(sim);
  }

  /**
   * Obtem parametros de uma simulação ja enviada para o back
   * Caso retorne erro e feito um retry
   * caso o status seja processando e feito um throw para realizar retry
   * @param id id da simulação
   */
  findSimulationById(id: number) {
    return this.api.request<any>({
      mock: false,
      mocker: of(this.simulations$.value[id]),
      http: this.http.get(`${Path.HTTP_API_BASE}/simulations/${id}`)
    })
    .pipe(
      switchMap(response => {
        let status: {[key: string]: number} = {[SimulationStatus.processing]: 425, [SimulationStatus.error]: 406};
        let errResponse = throwError({status: status[response.simulationStatus] || 500, id: response.id})
        return response.simulationStatus === SimulationStatus.draft ? of(response) :  errResponse;
      }),
      retryWhen(genericRetryStrategy({maxRetryAttempts: 30, scalingDuration: 10000, excludedStatusCodes: [500, 503, 406, 404, 400]}))
    )
  }

  /**
   * Retorna os parametros de uma simulação
   * sem nenhum tipo de logica de retry ou handling de status
   * @param id id da simulação
   */
  getParametersById(id: number) {
    return this.api.request<any>({
      mock: false,
      mocker: of(this.simulations$.value[id]),
      http: this.http.get(`${Path.HTTP_API_BASE}/simulations/${id}`)
    })
  }

  /**
   * Cria uma simulação in-memory a partir de um tipo de simulação informado
   * ponto de partida do fluxo de criar simulação
   */
  startSimulation(type: ISimulationType): Observable<BaseSimulation> {
    const simulation = new BaseSimulation({
      id: Object.keys(this.simulations$.value).length,
      userId: this._auth.$user.value.id,
      distributionCenters: [],
      suppliers: [],
      simulationType: type,
    });
    const tmp = this.simulations$.value;
    tmp[simulation.id || 0] = simulation;
    this.simulations$.next(tmp);
    return of(simulation);
  }


  /** Caso tenha budget retorar um objeto com a chave de budget
   * caso não tenha retornar um objeto vazio
   */
  getBudget({budget}: {budget?: any}) {
    return budget && !!budget.value ? {budget: budget} : {}
  }

  /**
   * Retorna os dados de desconto normalizado para o backend comprender
   * @param discountRaw dados de desconto não normalizado
   */
  getDiscount(discountRaw?: IDiscount) {
    if(!discountRaw || (discountRaw.skus == null && discountRaw.discount == null)) {
      return {}
    }
    return discountRaw.discountType == DISCOUNT_TYPE.PER_PRODUCT ?
    {
      "productDiscounts": discountRaw.skus.map(sku => ({
        id: sku.skuId, name: sku.name, type: sku.discountType || sku.type, value: sku.discount
      }))
    }
    :
    {
      "generalDiscount": {
        "type": discountRaw.discountType,
        "value": discountRaw.discount
      }
    }
  }


  /**
   * Retorna o body necessario e mapeado para o envio de uma simulação para
   * o backend
   * @param simulation simulação atual
   */
  mapToRequest(simulation: any): any {
    const result = simulation;
    /** handling de erros de parametros requeridos */
    if (result.orderType === undefined){
      throw Error('OrderType is not defined');
    }
    /** handling de erros de parametros requeridos */
    if (result.classification === undefined) {
      throw Error('Classification is not defined');
    }
    /** Respuesta mapeada a ser consumida por el backend */
    let res = {
      "distributionCenters": simulation.distributionCenters,
      "newPaymentTerm": simulation.newPaymentTerm,
      "suppliers": simulation.manufacturer,
      "categories": simulation.category,
      "subcategories": simulation.subcategory,
      "classification": simulation.classification,
      "orderType": simulation.orderType,
      "calculationBasis": simulation.calculationBasis,
      "productFilter": simulation.productFilter,
      "note": simulation.note,
      "simulationType": simulation.simulationType.id,
      "paymentTerm": simulation.paymentTerm,
      "operatorId": 1,
      "anticipationDate": simulation.anticipationDate,
      "stockCoverageDC": simulation.stockCoverageDC,
      "amount": simulation.amount
      }
      /** respuesta mapeada + budget normalizado + desconto normalizado */
    return {...res, ...this.getDiscount(result.discount), ...this.getBudget(simulation)};
  }

  /**
   * Retorna o resultado geral (resumo) da simulação com uma estrategia de retry
   * @param id id da simulação
   */
  getResult(id: number | string): Observable<SimulationResult> {
    const sim = this.simulations$.value[Number(id)];
    return this.api.request<SimulationResult[]>({
      mock: false,
      mocker: throwError({
        id,
        simulationType: sim?.simulationType,
        registerDate: new Date(),
        distributionCenters: sim?.distributionCenters,
        manufacturers: sim?.suppliers,
        category: sim?.category,
        subcategory: sim?.subcategory,
        orderType: sim?.orderType,
        classification: sim?.classification,
        anticipationDate: sim?.anticipationDate,
        stockCoverageDC: sim?.stockCoverageDC,
        amount: sim?.amount,
        note: sim?.note || 'Obs',
        paymentTerm: sim?.paymentTerm,
        saleType: sim?.saleType || { parent: 0, name: '90 Dias', id: 5 },
        sale: sim?.sale,
        discount: sim?.discount,
        productFilters: sim?.productFilter,
        newTerm: 45,
        TEadopted: 5,
        defaultTF: 5,
        regularDiscountAmount: 10000,
        negotiatedAmount: 9000,
        totalUnits: 200,
        currentStockCoverage: 30,
        finalStockCoverage: 30,
        gridStockCoverage: 30,
        gain: 1000
      }),
      http: this.http.get(`${Path.HTTP_API_BASE}/simulations/${id}/summary`)
    }).pipe(
      map(r => {
        if(!!r[0]) {
          return r[0]
        }else {
          throw new Error('Not finished')
        }
      }),
      retryWhen(genericRetryStrategy({maxRetryAttempts: 5, scalingDuration: 10000})),
      catchError(error => of(error))
    );
  }

  /**
   * Retorna os dados de resultado por CD de uma simulação com uma estrategia de retry
   * @param id id da simulação
   */
  getResultByCd(id: number | string): Observable<any[]> {
    return this.api.request<SimulationResult[]>({
      mock: false,
      mocker: throwError({status: 400}),
      http: this.http.get(`${Path.HTTP_API_BASE}/simulations/${id}/summary-dc`)
    }).pipe(
      retryWhen(genericRetryStrategy({maxRetryAttempts: 30, scalingDuration: 10000})),
      catchError(error => of(error))
    );
  }

  /**
   * Retorna uma lista de simulações, utilizada no acompanhamento da simulação
   * recebe uma lista de filtros e paginação
  */
   findSimulations(params: any, page: number, size: number) {

    const query: any = {
      page: page,
      size: size
    };

    let queryString = Object.keys(query).filter(key => query[key] != null).map((key) => `${key}=${query[key]}`).join('&');

    const body = {
      simulationTypeId: params.simulationType?.id || undefined,
      classificationId: params.classification?.id || undefined,
      statusId: params.status?.id || undefined,
      initialDate: params.registerDate[0].toISOString().split('T')[0],
      finalDate: (params.registerDate[1] || params.registerDate[0]).toISOString().split('T')[0],
      operatorName: !!params.user && params.user.length > 0 ? params.user.map((e: any) => e.id) : undefined,
      supplierIds: !!params.manufacturer && params.manufacturer.length > 0 ? params.manufacturer.map((e: any) => e.id) : undefined,
      parentSupplierId: !!params.supplier && params.supplier.length > 0 ? params.supplier.map((e: any) => e.id) : undefined,
    }

    return this.api.request({
      mock: false,
      mocker: of({
        page: page,
        size: size,
        numberOfElements: 100,
        number: page,
        totalElements: 100,
        content: [...Array(100)].map((el, idx) => {
        return {
          createdAt: new Date(),
          user: {name: 'Teste Mock'},
          simulationType: {title: 'Antecipação'},
          classification: {name: `MOCK ${idx}`},
          supplier: {parentSupplierName: 'Ache'},
          status: {id: 3, name: 'ERROR'},
          orderAmount: 50000,
          orderQuantity: 3000
        }
      }).slice(page*size, (page+1)*size)}),
      http: this.http.post(`${Path.HTTP_API_BASE}/simulations-followup?${queryString}`, body)
    });
  }
  /**
   * Realiza um retry de uma simulação com erro
   * !Importante cria uma nova simulação com novos parametros
   * @param simulationId id da simulação a ser re-feita
   */
  retry(simulationId: any) {
    this.getParametersById(simulationId).pipe(
      switchMap(failedSimulation => {
        return this.patchSpecifics({...failedSimulation, simulationType: {id: failedSimulation.simulationType}}, failedSimulation.id)
      })
    ).subscribe(data => {
    }, err => console.log('err data', err))
  }
}
