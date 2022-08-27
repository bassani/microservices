import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { IOrderType, ISearchResponse } from '../../models/search-simulador';

import { OrderTypeService } from './order-type.service';
const ORDER_TYPE_MOCK: ISearchResponse<IOrderType> = {content: [...Array(10)].map((e,i) => ({id: i, name: i.toString()})), page: 0, size: 10, numberOfElements: 10}
describe('OrderTypeService', () => {
  let service: OrderTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule]
    });
    service = TestBed.inject(OrderTypeService);
  });

  it('Deve ser criado o serviço', () => {
    expect(service).toBeTruthy();
    expect(service.service).toBeDefined()
  });

  describe('Pesquisa de tipo de pedido', () => {
    it('Deve retornar a resposta do servidor', () => {
      spyOn(service.service, 'fetch').and.returnValue({})
    })

    it('Deve realizar handling dos erros de servidor', () => {

    })

  })
});
