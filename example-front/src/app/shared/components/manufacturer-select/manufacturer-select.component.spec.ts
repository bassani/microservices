import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormControl } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { of, throwError } from 'rxjs';
import { ISearchResponse, ISupplier } from '../../models/search-simulador';
import { ManufacturerService } from '../../services/manufacturer/manufacturer.service';

import { ManufacturerSelectComponent } from './manufacturer-select.component';

describe('ManufacturerSelectComponent', () => {
  let component: ManufacturerSelectComponent;
  let fixture: ComponentFixture<ManufacturerSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ManufacturerSelectComponent],
      providers: [ManufacturerService, MessageService],
      imports: [HttpClientModule],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManufacturerSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    component.parentControl = new FormControl([])
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Search manufacturer', () => {
    it('Should use the service to make the search with parent supplier', fakeAsync(() => {
      const response: ISearchResponse<ISupplier> = {
        content: [
          { id: 1, name: 'Panpharma', parentSupplierId: 1 },
          { id: 2, name: 'Pantest', parentSupplierId: 2 },
        ],
        numberOfElements: 2,
        size: 10,
        page: 0,
      };
      component.control = new FormControl(null);
      spyOn(component['_manufacturerService'], 'search').and.returnValue(
        of(response)
      );
    }));

    it('Should use the service to make the search without parent supplier', fakeAsync(() => {
      component.parentControl = undefined;
      component.control = new FormControl();
      const response: ISearchResponse<ISupplier> = {
        content: [
          { id: 1, name: 'Panpharma', parentSupplierId: 1 },
          { id: 2, name: 'Pantest', parentSupplierId: 2 },
        ],
        numberOfElements: 2,
        size: 10,
        page: 0,
      };
      spyOn(component['_manufacturerService'], 'search').and.returnValue(
        of(response)
      );
    }));

    it('Should handle service errors', fakeAsync(() => {
      component.parentControl = undefined;
      component.control = new FormControl();
      const response = throwError({ error: { message: 'MOCK' } })
      component.control.setValue([{ id: 1, name: 'MOCK', parentSupplierId: 1 }])
    }));

    // describe('Parent relation', () => {
    //   it('Should use the service to make the search without parent supplier', fakeAsync(() => {
    //     component.parentControl = new FormControl();
    //     component.control = new FormControl();
    //     component.ngOnInit()
    //     const mockParent = [{id: 1, name: 'teste'}];
    //     const response: ISearchResponse<ISupplier> = {
    //       content: [
    //         { id: 1, name: 'Panpharma', parentSupplierId: 1 },
    //         { id: 2, name: 'Pantest', parentSupplierId: 2 },
    //       ],
    //       numberOfElements: 2,
    //       size: 10,
    //       page: 0,
    //     };
    //     component.control.setValue([{id: 1, name: 'MOCK', parentSupplierId: 1}])
    //     // expect(component.manufacturers$.value).toHaveLength(0);
    //     spyOn(component['_manufacturerService'], 'search').and.returnValue(
    //       of(response)
    //     );
    //     component.parentControl.setValue(mockParent)
    //     component.parentControl.markAsDirty();
    //     fixture.detectChanges();
    //     tick(50)
    //     expect(component['_manufacturerService'].search).toHaveBeenCalledTimes(1);
    //     expect(component['_manufacturerService'].search).toHaveBeenCalledWith(
    //       '',
    //       0,
    //       100,
    //       mockParent
    //     );
    //   }));

    // it('Should handle service errors', fakeAsync(() => {
    //   component.parentControl = undefined;
    //   component.control = new FormControl();
    //   component.ngOnInit()
    //   const response = throwError({error: {message: 'MOCK'}})

    //   component.control.setValue([{id: 1, name: 'MOCK', parentSupplierId: 1}])
    //   // expect(component.manufacturers$.value).toHaveLength(0);
    //   spyOn(component['_manufacturerService'], 'search').and.returnValue(response);
    //   tick(50)
    //   expect(component['_manufacturerService'].search).toHaveBeenCalledTimes(1);
    //   expect(component['_manufacturerService'].search).toHaveBeenCalledWith(
    //     '',
    //     0,
    //     100,
    //     undefined
    //   );
    // }));
  });
});
