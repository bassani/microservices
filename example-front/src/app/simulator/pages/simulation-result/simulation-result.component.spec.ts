import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { MessageService } from 'primeng/api';
import { of } from 'rxjs';
import { SimulationResult } from 'src/app/shared/models/simulation.model';
import { LoadingSimulationComponent } from '../../components/simulation-loader/simulation-loader.component';
import { SimulationTypeLabelsComponent } from '../../components/simulation-type-labels/simulation-type-labels.component';
import {
  SimulateService,
} from '../../services/simulate/simulate.service';
import { SIMULATION_TYPES } from '../simulation-type/simulation-type.component';

import { SimulationResultComponent } from './simulation-result.component';
const baseSimulationResult: any = {
  distributionCenters: [],
  suppliers: [],
  category: undefined,
  subcategory: undefined,
  description: '',
  createdAt: new Date(),
  updatedAt: new Date(),
  productDiscounts: [],
  userId: 0,
  simulationType: 1,
  currentPaymentTerm: { id: 0, name: 'teste' },
  anticipationDate: new Date(),
  paymentTerm: { name: 'teste', id: 0 },
  saleType: 2,
  note: '',
};
describe('SimulationResultComponent', () => {
  let component: SimulationResultComponent;
  let fixture: ComponentFixture<SimulationResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
      providers: [
        {provide: ActivatedRoute, useValue: {
          paramMap: of({get: (...args: any[]) => 0})
        }},
        {
        provide: SimulateService,
        useValue: {
          downloadResume: (...args: any[]) => {},
          getResult: (...args: any[]) => {},
          findSimulationById: (...args: any[]) => {}
        }
      }, MessageService],
      declarations: [SimulationResultComponent, SimulationTypeLabelsComponent, LoadingSimulationComponent],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SimulationResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  afterEach(() => {
    component.ngOnDestroy()
  })

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('OnInit', () => {
    it('Deve setar a simulação com o valor recebido da pesquisa por id', () => {
      spyOn(component['_simulationService'], 'getResult').and.returnValue(
        of(baseSimulationResult)
      );
      spyOn(component['_simulationService'], 'findSimulationById').and.returnValue(
        of(baseSimulationResult)
      );
      expect(component.simulation).toBeUndefined();
      component.ngOnInit();
      expect(component.simulation).toEqual({...baseSimulationResult, simulationType: SIMULATION_TYPES.find(i => i.id === baseSimulationResult.simulationType)});
    });
  });

  describe('Id to Simulation', () => {
    it('Deve pesquisar no serviço se existe a simulação se for informado o paramMap de id', () => {
      spyOn(component['_simulationService'], 'getResult').and.returnValue(
        of({})
      );
      spyOn(component['_simulationService'], 'findSimulationById').and.returnValue(of({}))
      const params = {
        get: (label: string) => {
          return '10';
        },
        has: () => true,
        getAll: () => [],
        keys: [],
      };
      component.idToSimulation(params);
      expect(component['_simulationService'].getResult).toHaveBeenCalledTimes(
        1
      );
      expect(component['_simulationService'].getResult).toHaveBeenCalledWith(
        params.get('id')
      );
    });

    it('Deve retornar um erro quando não existir o paramMap de id', () => {
      spyOn(component['_simulationService'], 'getResult').and.returnValue(
        of({})
      );
      spyOn(component['_simulationService'], 'findSimulationById').and.returnValue(of({}))

      const params = {
        get: (label: string) => {
          return null;
        },
        has: () => true,
        getAll: () => [],
        keys: [],
      };

      expect(() => component.idToSimulation(params)).toThrowError(
        'Id da simulação inválida'
      );
      expect(component['_simulationService'].getResult).toHaveBeenCalledTimes(
        0
      );
    });
  });

  describe('Format cd list', () => {
    it('Deve retornar um string formatado de ids dos cds selecionados', () => {
      component.simulation = {
        ...baseSimulationResult,
        distributionCenterId: [1, 2],
        distributionCenterName: ['teste', 'teste1'],
      };
      expect(component.cdList).toEqual('1, 2');
      component.simulation.distributionCenterId = [];
      expect(component.cdList).toEqual('');
      
      component.simulation = null;
      expect(component.cdList).toEqual('');
      component.simulation = {
        ...baseSimulationResult,
        distributionCenterId: null,
        distributionCenterName: ['teste', 'teste1'],
      };
      expect(component.cdList).toEqual('');
    });
  });

  describe('Format manufacturers list', () => {
    it('Deve retornar um string formatado de nomes dos fabricantes selecionados ou "Todos" se nehum fabricante e selecionado', () => {
      expect(component.manufacturers).toEqual('Todos');
      component.simulation = {
        ...baseSimulationResult,
        manufacturers: [
          { id: 1, name: 'teste 1' },
          { id: 2, name: 'teste 2' },
        ],
      };
      expect(component.manufacturers).toEqual('teste 1, teste 2');
      component.simulation.manufacturers = undefined;
      expect(component.manufacturers).toEqual('Todos');
      component.simulation = {
        ...baseSimulationResult,
        manufacturers: [],
      };
      expect(component.manufacturers).toEqual('Todos');


    });
  });

  
  describe('Format category and subcategory', () => {
    it('Deve retornar um string com a escrita "+5 categorias/subcategorias" quando a simulação tenha mais de 5 categorias/subcategorias', () => {
      expect(component.manufacturers).toEqual('Todos');

      component.simulation = {
        ...baseSimulationResult,
        categories: [{id: 1, name: 'teste'},{id: 2, name: 'teste'},{id: 3, name: 'teste'},{id: 4, name: 'teste'},{id: 5, name: 'teste'}],
        subcategories: [{id: 1, name: 'teste'},{id: 2, name: 'teste'},{id: 3, name: 'teste'},{id: 4, name: 'teste'},{id: 5, name: 'teste'}]
      };
      expect(component.asLimitedList(component.simulation.categories, 'Categorias')).not.toEqual('+5 Categorias');
      expect(component.asLimitedList(component.simulation.subcategories, 'Subcategorias')).not.toEqual('+5 Subcategorias');
      component.simulation = {
        ...baseSimulationResult,
        categories: [{id: 1, name: 'teste'},{id: 2, name: 'teste'},{id: 3, name: 'teste'},{id: 4, name: 'teste'},{id: 5, name: 'teste'},{id: 6, name: 'teste'}],
        subcategories: [{id: 1, name: 'teste'},{id: 2, name: 'teste'},{id: 3, name: 'teste'},{id: 4, name: 'teste'},{id: 5, name: 'teste'},{id: 6, name: 'teste'},,{id: 7, name: 'teste'}]
      };
      expect(component.asLimitedList(component.simulation.categories, 'Categorias')).toEqual('5+ Categorias');
      expect(component.asLimitedList(component.simulation.subcategories, 'Subcategorias')).toEqual('5+ Subcategorias');

    });
  });
  describe('Discount', () => {
    it('Deve mappear corretamente o desconto adicional para todos os produtos', () => {
      component.simulation = { ...baseSimulationResult };
      component.simulation.discount = {
        discountType: 0,
        discount: 10,
        skus: [],
      };

      component.discount.subscribe((data) => {
        expect(data).toEqual(
          `desconto adicional de ${component.simulation.discount?.discount}`
        );
      });

      component.simulation.productDiscounts = [{id: 1, value: 10}];
      component.simulation.generalDiscount = null;
      component.discount.subscribe((data) => {
        expect(data).toEqual(
          `desconto alterado de ${1} produtos`
        );
      });

    });

    it('Deve mappear corretamente o desconto novo para todos os produtos', () => {
      component.simulation = { ...baseSimulationResult };
      component.simulation.discount = {
        discountType: 1,
        discount: 10,
        skus: [],
      };

      component.discount.subscribe((data) => {
        expect(data).toEqual(
          `novo desconto de ${component.simulation.discount?.discount}`
        );
      });
    });

    it('Deve mappear corretamente o desconto para varios produtos', () => {
      component.simulation = { ...baseSimulationResult };
      component.simulation.discount = {
        discountType: 2,
        discount: 10,
        skus: [
          { skuId: 0, discount: 10, type: 1 },
          { skuId: 1, discount: 5, type: 0 },
        ],
      };
      component.discount.subscribe((data) => {
        expect(data).toEqual(
          `desconto alterado de ${component.simulation.discount?.skus.length} produtos`
        );
      });
    });

    it('Deve mappear corretamente para nenhum desconto aplicado', () => {
      component.simulation = { ...baseSimulationResult };
      component.simulation.discount = undefined;
      component.discount.subscribe((data) => {
        expect(data).toEqual(`Nenhum desconto aplicado`);
      });
    });
  });

  describe('Reprocessar', () => {
    it('Deve chamar o serviço que faz o download do xlsx', () => {
      spyOn(component['_simulationService'], 'downloadResume').and.returnValue({});
      component.simulation = {simulationId: 1}
      component.getDownloadResume();
      expect(component['_simulationService'].downloadResume).toHaveBeenCalledTimes(1);
      expect(component['_simulationService'].downloadResume).toHaveBeenCalledWith(1);
    })
  })

  describe('CalcBase', () => {  
    it('Deve obter o calculo base da simulação em formato string', () => {
      expect(component.getCalculationBasis({id: 1, days: 999})).toEqual(`Venda dos Últ. 999 Dias`)
      expect(component.getCalculationBasis({id: 2})).toEqual(`Forecast Semanal`)
      expect(component.getCalculationBasis({id: 3, days: 999})).toEqual(`Forecast Mensal`)
    })
  });


  describe('Simulation type', () => {  
    it('Deve obter o tipo da simulação em formato string', () => {
      expect(component.getSimulationType({id: 1})?.title).toEqual(SIMULATION_TYPES[0].title);
      expect(component.getSimulationType({id: 999})).toEqual(undefined);
    })
  });

  describe('As list', () => {  
    it('Deve retornar uma lista em formato de string utilizando o prefixo indicado', () => {
      expect(component.asList([{name: 'Teste'}, {id: 1}, {name: 't1'}])).toEqual('Teste, 1, t1');
      expect(component.asList([], 'o')).toEqual('Todos');
      expect(component.asList([], 'a')).toEqual('Todas');
    })
  });
});
