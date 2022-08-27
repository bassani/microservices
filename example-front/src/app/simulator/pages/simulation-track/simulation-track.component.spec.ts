import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { MessageService } from 'primeng/api';
import { CalendarModule } from 'primeng/calendar';
import { of, throwError } from 'rxjs';
import { NgVarDirective } from 'src/app/shared/directives/ng-var.directive';
import { WithRolesDirective } from 'src/app/shared/directives/with-roles.directive';
import { AuthService } from 'src/app/shared/services';
import { MOCK_SEARCH_PAGED } from 'src/app/shared/utils/mock';
import { SimulateService } from '../../services/simulate/simulate.service';

import { SimulationTrackComponent } from './simulation-track.component';


const FORMS_STATES = {
  VALID: {registerDate: [new Date(), new Date()], classification: null, manufacturer: null, simulationType: null, status:null, supplier: null, user: null},
  INVALID: {registerDate: null}
}

describe('SimulationTrackComponent', () => {
  let component: SimulationTrackComponent;
  let fixture: ComponentFixture<SimulationTrackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule, CalendarModule, ReactiveFormsModule],
      providers: [{
        provide: SimulateService,
        useValue: {
          getResult: (...args: any[]) => of(MOCK_SEARCH_PAGED),
          findSimulations: () => of([])
        }
      },
      {
        provide: AuthService, useValue: {roles: ['create_classification', 'update_classification']}
      },
      MessageService],
      declarations: [SimulationTrackComponent, NgVarDirective, WithRolesDirective],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SimulationTrackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('OnInit', () => {
    it('Deve criar o form', () => {
      spyOn(component, 'createForm').and.callThrough();
      expect(component.simulation$).toBeDefined();
      component.ngOnInit();
      expect(component.form.valid).toEqual(false);
    });

  });


  describe('Search', () => {
    it('Should make sure that the form is valid', () => {
      component.ngOnInit();
      component.form.patchValue(FORMS_STATES.INVALID)
      expect(component.form.valid).toEqual(false);
      spyOn(component['_message'], 'add').and.returnValue({});
      expect(component.search(0, 10));
      expect(component['_message'].add).toHaveBeenCalledTimes(1);
    })


    it('Should paginate', () => {
      component.ngOnInit();
      component.form.patchValue(FORMS_STATES.VALID)
      spyOn(component['_simulationService'], 'findSimulations').and.returnValue(of({
        page: 0,
        size: 10,
        numberOfElements: 10,
        content: [],
        number: 0,
        totalElements: 500,
      }))
      expect(component.form.valid).toEqual(true);
      expect(component.changePage(0, 10));
      expect(component['_simulationService'].findSimulations).toHaveBeenCalledWith({
        ...FORMS_STATES.VALID,
      }, 0, 10)
    })

    it('Should handle server errors', () => {
      component.ngOnInit();
      component.form.patchValue(FORMS_STATES.VALID)
      component.simulation$.next({...component.simulation$.value, content: [{id: 1, name: 'teste'}]})
      spyOn(component['_simulationService'], 'findSimulations').and.returnValue(
        throwError({status: 500, errors: {message: 'teste'}})
      );
      expect(component.form.valid).toEqual(true);
      expect(component.changePage(1,10));
      expect(component.simulation$.value.content).toEqual([]);
    })
  })

  describe('As list function', () => {
    it('Should return a list of names or a string with telling the user when 3+ parent suppliers are selected', () => {
      expect(component.asList([{parentSupplierName: 'teste 1'}, {parentSupplierName: 'teste 2'}, {parentSupplierName: 'teste 3'}])).toEqual('teste 1, teste 2, teste 3');
      expect(component.asList([{parentSupplierName: 'teste 1'}, {parentSupplierName: 'teste 2'}, {parentSupplierName: 'teste 3'}, {parentSupplierName: 'teste 4'}])).toEqual('3+ fornecedores pai');
    })

    it('Should work with an empty array', () => {
      expect(component.asList()).toEqual('');
    })
  })

  describe('Paginate function', () => {
    it('Should call onPage with the arguments provided', () => {
      let mock = {page: 1, rows: 5};
      spyOn(component, 'changePage').and.returnValue({});
      component.paginate(mock);
      expect(component.changePage).toHaveBeenCalledTimes(1);
      expect(component.changePage).toHaveBeenLastCalledWith(mock.page, mock.rows);
    })
  })
});
