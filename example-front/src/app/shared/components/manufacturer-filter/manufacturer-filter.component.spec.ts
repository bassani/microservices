import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DialogModule } from 'primeng/dialog';
import { of } from 'rxjs';
import { ManufacturerService } from '../../services';

import { ManufacturerFilterComponent } from './manufacturer-filter.component';

describe('ManufacturerFilterComponent', () => {
  let component: ManufacturerFilterComponent;
  let fixture: ComponentFixture<ManufacturerFilterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManufacturerFilterComponent ],
      imports: [DialogModule],
      providers: [
        {
          provide: ManufacturerService,
          useValue: {
            search: (...args: any[]) => {},
            validateCodes: (...args: any[]) => {},
          }
        }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManufacturerFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Select', () => {
    it('Deve adicionar a lista de produtos selecionados sem duplicados', () => {
      let mock = {id: 0, name: 'teste'}
      component.manufacturersControl.setValue([]);
      component.filterControl.setValue(mock)
      component.selected();
      expect(component.filterControl.value).toEqual(null);
      expect(component.manufacturersControl.value).toEqual([mock]);

      component.filterControl.setValue(mock)
      component.selected();
      expect(component.manufacturersControl.value).toEqual([mock]);

    })


  })

  describe('Get errors', () => {
    it('Deve retornar um string informando do erro atual do campo', () => {
      component.separatorErrorList$.next(['123', '321']);
      component.errors.subscribe(data => {
        expect(data).toEqual(`Fabricantes não encontrado para os códigos: ${component.separatorErrorList$.value.join(', ')}`)
      })
      component.separatorErrorList$.next(['123']);
      component.errors.subscribe(data => {
        expect(data).toEqual(`Fabricante não encontrado para o código: ${component.separatorErrorList$.value.join(', ')}`)
      })
    })
  })

  describe('Remove', () => {

    it('Deve remover os itens com o id informado', () => {
      let mock = [{id: 0, name: 'teste'}, {id: 1, name: 'teste'}, {id: 2, name: 'teste'}]
      component.manufacturersControl.setValue(mock);
      component.removeManufacturer(mock[1]);
      expect(component.manufacturersControl.value).toEqual([mock[0], mock[2]]);
      component.removeManufacturer(mock[1]);
      expect(component.manufacturersControl.value).toEqual([mock[0], mock[2]]);

    })
  })

  describe('Search', () => {
    it('Deve chamar o serviço e preencher a lista de opções', () => {
      let mockList = {content: [{id: 0, name: 'teste'}, {id: 1, name: 'teste'}]}
      spyOn(component['_manufacturerService'], 'search').and.returnValue(of(mockList));
      component.manufacturers$.next([]);
      component.search({query: 'tes'});
      expect(component['_manufacturerService'].search).toHaveBeenCalledTimes(1);
      expect(component['_manufacturerService'].search).toHaveBeenCalledWith('tes', 0, 25);
      expect(component.manufacturers$.value).toEqual(mockList.content)
    })

  })

  describe('Validate', () => {
    it('Deve validar uma lista de codigos de fabricante', () => {
      let codes = '123;321;a23;aa';
      let mock = {invalid: ['a23', 'aa'], valid: [{id: 123, name: 'teste'}, {id: 321, name: 'teste'}]}
      spyOn(component['_manufacturerService'], 'validateCodes').and.returnValue(of(mock));
      component.separatorErrorList$.next([]);
      component.search({query: codes});
      expect(component['_manufacturerService'].validateCodes).toHaveBeenCalledTimes(1);
      expect(component['_manufacturerService'].validateCodes).toHaveBeenCalledWith(codes);
      expect(component.manufacturersControl.value).toEqual(mock.valid)
      expect(component.separatorErrorList$.value).toEqual(mock.invalid)
    })
  })


  describe('Modal', () => {

    it('deve mostrar o modal e setar o valor do control', () => {
      component.displayModal = false;
      const controlValueMock = [{id: 0, name: 'teste'}, {id: 1, name: 'teste'}];
      expect(component.manufacturersControl.value).toEqual([]);
      component.control.setValue(controlValueMock)
      component.showModal();
      expect(component.displayModal).toEqual(true);
      expect(component.manufacturersControl.value).toEqual(controlValueMock);

    })

    it('deve ocultar Modal sem salvar ao cancelar', () => {
      component.displayModal = true;
      const controlValueMock = [{id: 0, name: 'teste'}, {id: 1, name: 'teste'}];
      expect(component.control.value).toEqual([]);
      component.manufacturersControl.setValue(controlValueMock)
      component.closeModal();
      expect(component.displayModal).toEqual(false);
      expect(component.control.value).toEqual([]);
    })

    it('Deve setar o valor do control quando salvar', () => {
      component.displayModal = true;
      const controlValueMock = [{id: 0, name: 'teste'}, {id: 1, name: 'teste'}];
      expect(component.control.value).toEqual([]);
      component.manufacturersControl.setValue(controlValueMock)
      component.save();
      expect(component.displayModal).toEqual(false);
      expect(component.control.value).toEqual(controlValueMock);
    })
  })


});
