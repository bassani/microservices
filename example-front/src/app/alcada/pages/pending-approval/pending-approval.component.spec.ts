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
import { AlcadaService } from '../../service/alcada.service';

import { PendingApprovalComponent } from './pending-approval.component';


const FORMS_STATES = {
  VALID: {registerDate: [new Date(), new Date()], idSimulation: null,  user: null, supplier: null, manufacturer: null, category: null},
  INVALID: {registerDate: null}
}

describe('PendingApprovalComponent', () => {
  let component: PendingApprovalComponent;
  let fixture: ComponentFixture<PendingApprovalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule, CalendarModule, ReactiveFormsModule],
      providers: [{
        provide: AlcadaService,
        useValue: {
          getResult: (...args: any[]) => of(MOCK_SEARCH_PAGED),
          findSimulationsPending: () => of([])
        }
      },
      MessageService],
      declarations: [PendingApprovalComponent, NgVarDirective, WithRolesDirective],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PendingApprovalComponent);
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
    it('test case date range', () => {
      const form = FORMS_STATES.VALID;
      const getFinalDate = form.registerDate[1].getDate() + 7;
      fixture.detectChanges();
      expect(form.registerDate).not.toBeNull();
      expect(getFinalDate - form.registerDate[0].getDate() > 7)
    });
    it('test case date range failed', () => {
      const form = FORMS_STATES.INVALID;
      expect(form.registerDate).toBeNull();
      spyOn(component['_message'], 'add').and.returnValue(true);
    });

    it('Should make sure that the form is valid', () => {
      component.ngOnInit();
      component.form.patchValue(FORMS_STATES.INVALID)
      expect(component.form.valid).toEqual(false);
      spyOn(component['_message'], 'add').and.returnValue({});
      expect(component.search(0, 10));
      expect(component['_message'].add).toHaveBeenCalledTimes(1);
    });


    it('Should paginate', () => {
      component.ngOnInit();
      component.form.patchValue(FORMS_STATES.VALID)
      spyOn(component['_alcadaService'], 'findSimulationsPending').and.returnValue(of({
        page: 0,
        size: 10,
        numberOfElements: 0,
        content: [],
        number: 0,
        totalElements: 50,
      }))
      expect(component.form.valid).toEqual(true);
      expect(component.changePage(0, 10));
      expect(component['_alcadaService'].findSimulationsPending).toHaveBeenCalledWith({
        ...FORMS_STATES.VALID,
      }, 0, 10)
    });

    it('Should handle server errors', () => {
      component.ngOnInit();
      component.form.patchValue(FORMS_STATES.VALID)
      component.simulation$.next({...component.simulation$.value, content: [{id: 1, name: 'teste'}]})
      spyOn(component['_alcadaService'], 'findSimulationsPending').and.returnValue(
        throwError({status: 500, errors: {message: 'teste'}})
      );
      expect(component.form.valid).toEqual(true);
      expect(component.changePage(1,10));
      expect(component.simulation$.value.content).toEqual([]);
    });
  });
  describe('As list function', () => {
    const mockListSuplier = [
      {
        id: 105173,
        supplierId: null,
        supplierName: null,
        parentSupplierId: null,
        parentSupplierName: "teste 1"
      },
      {
        id: 105173,
        supplierId: null,
        supplierName: null,
        parentSupplierId: null,
        parentSupplierName: "teste 2"
      },
      {
        id: 105173,
        supplierId: null,
        supplierName: null,
        parentSupplierId: null,
        parentSupplierName: "teste 3"
      },
    ];
    const mockListSuplier2 = [
      {
        id: 105173,
        supplierId: null,
        supplierName: null,
        parentSupplierId: null,
        parentSupplierName: "teste 1"
      },
      {
        id: 105173,
        supplierId: null,
        supplierName: null,
        parentSupplierId: null,
        parentSupplierName: "teste 2"
      },
      {
        id: 105173,
        supplierId: null,
        supplierName: null,
        parentSupplierId: null,
        parentSupplierName: "teste 3"
      },
      {
        id: 105173,
        supplierId: null,
        supplierName: null,
        parentSupplierId: null,
        parentSupplierName: "teste 4"
      },
    ];
    const mockListSuplierEmpty = [{}];
    it('Should return a list of names or a string with telling the user when 3+ parent suppliers are selected', () => {
      expect(component.asList(mockListSuplier)).toEqual('teste 1, teste 2, teste 3');
      expect(component.asList(mockListSuplier2)).toEqual('3+ fornecedores pai');
    });

    it('Should work with an empty array', () => {
      expect(component.asList(mockListSuplierEmpty)).toEqual('');
    });
  });

  describe('Paginate function', () => {
    it('Should call onPage with the arguments provided', () => {
      let mock = {page: 1, rows: 5};
      spyOn(component, 'changePage').and.returnValue({});
      component.paginate(mock);
      expect(component.changePage).toHaveBeenCalledTimes(1);
      expect(component.changePage).toHaveBeenLastCalledWith(mock.page, mock.rows);
    });
  });

  describe('should return a new control of groups', () => {

    it('Should return a user control', () => {
      let mock = "user";
      spyOn(component, 'getControl').and.returnValue({});
      component.getControl(mock);
      expect(component.getControl).toBeDefined();
    });

    it('Should a valid form after used getControl', () => {
      component.ngOnInit();
      component.form.patchValue(FORMS_STATES.VALID);
      expect(component.form.valid).toEqual(true);
    })
  });
});
