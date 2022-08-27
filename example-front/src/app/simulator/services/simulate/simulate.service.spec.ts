import { APP_BASE_HREF } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { MessageService } from 'primeng/api';
import { of, throwError } from 'rxjs';
import { DISCOUNT_TYPE } from 'src/app/shared/components/discount-modal/discount-modal.component';
import { BaseSimulation, IDiscount, SimulationResult } from 'src/app/shared/models/simulation.model';
import { AuthService } from 'src/app/shared/services';
import { CATEGORY_MOCK, CLASSIFICATION_MOCK, MOCK_BLOB_CSV, PAYMENT_TERM_MOCK, SUBCATEGORY_MOCK } from 'src/app/shared/utils/mock';

import {
  SimulateService,
} from './simulate.service';
const MOCK = new BaseSimulation({
  id: 0,
  userId: 0,
  simulationType: {
    key: 'teste',
    id: 0,
    title: 'teste',
    description: 'teste',
    icon: 'none',
  },
  orderType: { id: 0, name: 'test' },
  paymentTerm: PAYMENT_TERM_MOCK[0],
  category: [CATEGORY_MOCK[0]],
  subcategory: [SUBCATEGORY_MOCK[0]],
  productFilter: {inactive: false},
  note: 'Teste unitario',
  suppliers: [
    { id: 3, name: 'first', parentSupplierId: 1 },
    { id: 5, name: 'seccond', parentSupplierId: 1 },
    { id: 8, name: 'third', parentSupplierId: 1 },
  ],
  distributionCenters: [
    { id: 1, name: 'first' },
    { id: 3, name: 'seccond' },
    { id: 5, name: 'third' },
  ],
  classification: CLASSIFICATION_MOCK[0],
});
const MOCK_RESULT: SimulationResult = {
  ...MOCK,
  simulationType: 1,
  currentPaymentTerm: { name: 'teste', id: 2 },
};
describe('SimulateService', () => {
  let service: SimulateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterModule.forRoot([])],
      providers: [MessageService, { provide: APP_BASE_HREF, useValue: '/' }, {
        provide: AuthService, useValue: {
          $user: {value: {id: 0}}
        }
      }],
    });
    service = TestBed.inject(SimulateService);
  });

  it('DEve ser criado o serviço', () => {
    expect(service).toBeTruthy();
  });

  describe('Simular', () => {
    describe('Start Simulation', () => {
      it('Deve chamar o servidor para obter o id inicial da simulação', () => {
        const mockType = {
          id: 0,
          title: 'Teste title',
          description: 'Teste description',
          key: 'antecipacao',
          icon: '',
        };
        expect(Object.keys(service.simulations$.value)).toHaveLength(0);
        service.startSimulation(mockType).subscribe((data) => {
          expect(data.id).toEqual(1);
          expect((data.simulationType = mockType));
          expect(Object.keys(service.simulations$.value)).toHaveLength(1);
        });
      });
    });

    describe('PathBasics', () => {
      it('Deve atualizar os dados basicos da simulação', () => {
        const mocker = { ...MOCK, id: 0 };
        service.simulations$.next({ 0: mocker });
        expect(service.simulations$.value[0]).toEqual(MOCK);
        service
          .patchBasics({ manufacturers: [], distributionCenters: [] }, MOCK.id)
          .subscribe((data) => {
            expect(data).toEqual(MOCK);
          });
      });
      it('Deve retornar um erro caso não seja informado um id valido', () => {
        service.simulations$.next([MOCK]);
        service
          .patchBasics(
            { manufacturers: [], distributionCenters: [] },
            undefined
          )
          .subscribe({
            error: (err) => {
              expect(err).toEqual({
                status: 404,
                error: { message: 'Simulação não encontrada' },
              });
            },
          });
      });
    });

    describe('Map Request', () => {

      it('Deve mapear os descontos gerais', () => {
        const discount: IDiscount = {
          discountType: DISCOUNT_TYPE.ADD,
          discount: 10,
          skus: [{skuId: 1, discount: 10, type: 1}]
        };
        const expected = {
          "generalDiscount": {
            "type": discount.discountType,
            "value": discount.discount
          }
        }
        expect(service.getDiscount(discount)).toEqual(expected)
      })

      it('Deve mapear a verba', () => {
        const simulationWithBudget = {budget: {value: 10}, id: 10}
        const simulationWithoutBudget = {budget: {value: null}, id: 10}
        const simulationWithoutBudgetKey: any = {id: 10}
        expect(service.getBudget(simulationWithBudget)).toEqual({budget: simulationWithBudget.budget})
        expect(service.getBudget(simulationWithoutBudget)).toEqual({})
        expect(service.getBudget(simulationWithoutBudgetKey)).toEqual({})
      })

      it('Deve mapear os descontos por produtos', () => {
        const discount = {
          discountType: DISCOUNT_TYPE.PER_PRODUCT,
          discount: 10,
          skus: [{skuId: 1, name: 'Teste', discount: 10, type: 1}, {skuId: 2, name: 'Teste', discount: 10, type: 0}]
        };
        const expected = {
          "productDiscounts": [
            {id: discount.skus[0].skuId, name: 'Teste', type: discount.skus[0].type, value: discount.skus[0].discount},
            {id: discount.skus[1].skuId, name: 'Teste', type: discount.skus[1].type, value: discount.skus[1].discount}
          ]
        }
        expect(service.getDiscount(discount as unknown as IDiscount)).toEqual(expected)
      })
      it('Deve realizar o mapping para o modelo do contrato http', () => {
        spyOn(service, 'getDiscount').and.returnValue({});
        expect(service.mapToRequest(MOCK)).toBeDefined()
        expect(service.getDiscount).toHaveBeenCalledTimes(1)
      })
    })
    describe('Patch Specifics', () => {
      it('Deve atualizar os dados especificos da simulação', () => {
        const specif = {
          anticipationDate: new Date(),
          paymentTerm: { id: 0, name: 'teste' },
          discount: undefined,
          note: 'Teste none',
          productFilter: MOCK.productFilter,
          saleType: 2,
        };
        service.simulations$.next([MOCK]);
        let base = {
          anticipationDate: MOCK.anticipationDate,
          paymentTerm: MOCK.paymentTerm,
          productFilter: MOCK.productFilter,
          discount: MOCK.discount,
          note: MOCK.note,
          saleType: MOCK.saleType,
          classification: MOCK.classification,
        };
        expect(
          base
        ).not.toEqual(specif);
        service.patchSpecifics(specif, MOCK.id || 0).subscribe((data) => {
          base = {
            anticipationDate: MOCK.anticipationDate,
            paymentTerm: MOCK.paymentTerm,
            productFilter: data.productFilter,
            discount: MOCK.discount,
            note: MOCK.note,
            saleType: MOCK.saleType,
            classification: MOCK.classification,
            
          };
          expect(base).toEqual({...specif, productFilter: {onlyActive: MOCK.productFilter?.inactive}});
        });
      });

      it('Deve atualizar os dados especificos da simulação para retry', () => {
        const specif = {
          anticipationDate: '2022-01-01',
          paymentTerm: { id: 0, name: 'teste' },
          discount: undefined,
          note: 'Teste none',
          productFilter: MOCK.productFilter,
          saleType: 2,
          calculationBasis: {parent: 1}
        };
        service.simulations$.next([MOCK]);
        let base = {
          anticipationDate: MOCK.anticipationDate,
          paymentTerm: MOCK.paymentTerm,
          productFilter: MOCK.productFilter,
          discount: MOCK.discount,
          note: MOCK.note,
          saleType: MOCK.saleType,
          classification: MOCK.classification,
        };
        expect(
          base
        ).not.toEqual(specif);
        service.patchSpecifics(specif, MOCK.id || 0).subscribe((data) => {
          base = {
            anticipationDate: MOCK.anticipationDate,
            paymentTerm: MOCK.paymentTerm,
            productFilter: data.productFilter,
            discount: MOCK.discount,
            note: MOCK.note,
            saleType: MOCK.saleType,
            classification: MOCK.classification,
            
          };
          expect(base).toEqual({...specif, productFilter: {onlyActive: MOCK.productFilter?.inactive}});
        });
      });
    });

    describe('Processar simulação', () => {
      it('Deve realizar o request para gerar a simulação', () => {
        spyOn(service['api'], 'request').and.returnValue(of(MOCK_RESULT));
        service.simulations$.next({0: MOCK});
        service.getResult(0).subscribe((data) => {
          expect(data).toEqual(MOCK);
        });
        expect(service['api'].request).toHaveBeenCalledTimes(1);
      });

      it('Deve retornar a simulação in memory', () => {
        service.simulations$.next([MOCK]);
        service.getSimulation(MOCK.id || 0).subscribe(data => {
          expect(data).toEqual(MOCK)

        })
      })
    });

    describe('Reprocessar', () => {
      it('Deve realizar o handling para o sucesso do reprocessamento mostrando uma mensagem pro usuario', () => {
        spyOn(service['messageService'], 'add').and.returnValue({});
        spyOn(service['api'], 'request').and.returnValue(of({ status: 1 }));
        service.downloadResume('semanal');
        expect(service['messageService'].add).toHaveBeenCalledTimes(1);
        expect(service['messageService'].add).toHaveBeenCalledWith({
          severity: 'success',
          summary: 'Arquivo gerado com sucesso',
          detail: 'O download começará em breve.',
          key: 'main',
        });
      });

      it('Deve realizar o handling para o erro do reprocessamento mostrando uma mensagem pro usuario', () => {
        const mockError = { status: 500, error: { message: 'Internal Error' } };
        spyOn(service['messageService'], 'add').and.returnValue({});
        spyOn(service['api'], 'request').and.returnValue(throwError(mockError));
        service.downloadResume('mensal');
        expect(service['messageService'].add).toHaveBeenCalledTimes(1);
        expect(service['messageService'].add).toHaveBeenCalledWith({
          severity: 'error',
          summary: 'Erro ao gerar arquivo',
          detail: 'Tente novamente mais tarde.',
          key: 'main',
        });
      });

    });
  });
});
