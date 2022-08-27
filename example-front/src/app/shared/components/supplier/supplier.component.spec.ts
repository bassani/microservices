import { HttpClientModule } from '@angular/common/http';
import { Component, CUSTOM_ELEMENTS_SCHEMA, ElementRef } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormControl } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { MultiSelect, MultiSelectModule } from 'primeng/multiselect';
import { of } from 'rxjs';

import { SupplierComponent } from './supplier.component';

@Component({selector: 'mock-parent', template: '<app-supplier [control]="control"></app-supplier>'})
class MockParent{
  control: FormControl;
}

describe('SupplierComponent', () => {
  let component: MockParent;
  let fixture: ComponentFixture<MockParent>;
  let mockChildFixture: ComponentFixture<SupplierComponent>;
  let mockChild: SupplierComponent
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SupplierComponent, MockParent ],
      imports: [HttpClientModule, MultiSelectModule],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MockParent);
    mockChildFixture = TestBed.createComponent(SupplierComponent);
    component = fixture.componentInstance;
    mockChild = mockChildFixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  describe('Filter suppliers', () => {

    it('Should call the service when the user types', () => {
      spyOn(mockChild['supplierService'], 'search').and.returnValue(of([]))
      let input = fixture.debugElement.query(By.css('input')).nativeElement;
      input.value = 'MOCK';
      mockChild.filterSuppliers({filter: 'a'}, {activateFilter: () => {}})
    })

    it('Should add values to the current value', () => {
      component.control = new FormControl([]);
      let input = fixture.debugElement.query(By.css('input')).nativeElement;
      input.value = 'F';
    })

    
    it('Should add values to ab empty array if there is not previous value', () => {
      spyOn(mockChild['supplierService'], 'search').and.returnValue(of([]))
      mockChild.control = new FormControl(null);
      mockChild.filterSuppliers({}, {activateFilter: () => {}});

    })

  })
});
