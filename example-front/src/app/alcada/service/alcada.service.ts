import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, of, throwError } from 'rxjs';
import { Path } from 'src/app/shared/consts/path';
import { BaseSimulation } from 'src/app/shared/models/simulation.model';
import { ApiService } from 'src/app/shared/services';



/**
 * Serviço central e mais complexo do sistema, encarregado de gerenciar a comunicação e
 * as simulações
 */
@Injectable({
  providedIn: 'root',
})
export class AlcadaService {
  /** behaviour subject para manter registro de todas as simulações in-memory */
  simulationsPending$: BehaviorSubject<{
    [id: number]: BaseSimulation;
  }> = new BehaviorSubject<{ [id: number]: BaseSimulation }>({});

  constructor(
    private http: HttpClient,
    private api: ApiService,
  ) { }

  /**
   * Retorna uma lista de simulações pendentes,
   * recebe uma lista de filtros e paginação
  */

  findSimulationsPending(params: any, page: number, size: number) {

    console.log(params);


    const query: any = {
      page: page,
      size: size
    };

    let queryString = Object.keys(query).filter(key => query[key] != null).map((key) => `${key}=${query[key]}`).join('&');

    const body = {
      simulationId: parseInt(params.idSimulation?.id) || undefined,
      initialDate: params.registerDate[0].toISOString().split('T')[0],
      finalDate: (params.registerDate[1] || params.registerDate[0]).toISOString().split('T')[0],
      operatorIds: !!params.user && params.user.length > 0 ? params.user.map((e: any) => e.code) : undefined,
      supplierIds: !!params.manufacturer && params.manufacturer.length > 0 ? params.manufacturer.map((e: any) => e.id) : undefined,
      parentSupplierIds: !!params.supplier && params.supplier.length > 0 ? params.supplier.map((e: any) => e.id) : undefined,
      categoryIds: !!params.category && params.category.length > 0 ? params.category.map((e: any) => e.id) : undefined,
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
          registerDate: params.registerDate[0].toISOString().split('T')[0],
          expirationDate: (params.registerDate[1] || params.registerDate[0]).toISOString().split('T')[0],
          simulationId: "10" + idx,
          operatorName: 'Teste Mock' + idx,
          simulationUserName: 'Teste Mock' + idx,
          classification: {name: `MOCK ${idx}`},
          suppliers: {parentSupplierName: 'Ache'},
          totalValue: 50000,
          status: {id: 7, name: 'Pendente de Aprovação'}
        }
      }).slice(page*size, (page+1)*size)}),
      http: this.http.post(`${Path.HTTP_API_BASE}/simulations-pending-approval?${queryString}`, body)
    })
  }
}

