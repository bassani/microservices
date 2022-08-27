import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { BaseSimulation } from 'src/app/shared/models/simulation.model';
import { MOCK_SEARCH_PAGED } from 'src/app/shared/utils/mock';

import { SimulationDescriptionComponent } from './simulation-description.component';
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
});
describe('SimulationDescriptionComponent', () => {
  let component: SimulationDescriptionComponent;
  let fixture: ComponentFixture<SimulationDescriptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [SimulationDescriptionComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SimulationDescriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Deve retornar o conteudo do cdService', () => {
    spyOn(component['_cdService'], 'search').and.returnValue(
      of(MOCK_SEARCH_PAGED)
    );
    component.cds.subscribe((data) => {
      expect(data).toEqual(MOCK_SEARCH_PAGED.content);
    });
  });

  describe('ManufacturerList', () => {
    it('Deve retornar uma lista de fornecedores como string separado por virgula', () => {
      component.simulation = MOCK;
      expect(component.manufacturerList).toEqual('first, seccond, third');
    });

    it('Deve não retornar caso não tenha nenhum fornecedor selecionado', () => {
      component.simulation = { ...MOCK, suppliers: undefined };
      expect(component.manufacturerList).toEqual(undefined);
    });
  });

  describe('cdList', () => {
    it('Deve retornar uma lista de id de cds como string separado por virgula', () => {
      component.simulation = MOCK;
      (component.simulation.distributionCenters = [
        { id: 1, name: 'first' },
        { id: 3, name: 'seccond' },
        { id: 5, name: 'third' },
      ]),
        expect(component.cdList).toEqual('1, 3, 5');
    });
  });

  describe('Plural', () => {
    it('Deve retornar o suffix caso a lista tenha mais de 1 item', () => {
      expect(component.plural([1, 2])).toEqual('s');
      expect(component.plural([1, 2, 3], 'es')).toEqual('es');
    });
    it('Deve returnar um string vazio caso a lista não tenha mais de 1 item', () => {
      expect(component.plural([1])).toEqual('');
      expect(component.plural([], 'es')).toEqual('');
      expect(component.plural(undefined, '')).toEqual('');
    });
  });

  describe('Categoria', () => {
    it('Deve retornar o nome da categoria selecionada', () => {
      const cat = { name: 'teste', id: 1 };
      component.simulation = { ...MOCK, category: [cat] };
      expect(component.category).toEqual(cat.name);
    });
    it('Deve retornar "Todas" caso a categoria não esteja selecionada', () => {
      component.simulation = { ...MOCK };
      expect(component.category).toEqual('Todas');
    });
  });

  describe('SubCategoria', () => {
    it('Deve retornar o nome da subcategoria selecionada', () => {
      const cat = { name: 'teste', id: 1 };
      const sub = { name: 'teste', id: 1, categoryId: 1 };
      component.simulation = { ...MOCK, category: [cat] };
      component.simulation = { ...MOCK, subcategory: [sub] };
      expect(component.subcategory).toEqual(sub.name);
    });
    it('Deve retornar "Todas" caso a subcategoria não esteja selecionada', () => {
      component.simulation = { ...MOCK };
      expect(component.subcategory).toEqual('Todas');
    });
  });
});
