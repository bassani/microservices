import { PaymentTermService } from './../../services/payment-term/payment-term.service';
import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { TypeProductsService } from '../../services/type-products/type-products.service';
import { TypeProductsComponent } from './type-products.component';

describe('TypeProductsComponent', () => {
  let component: TypeProductsComponent;
  let fixture: ComponentFixture<TypeProductsComponent>;
  let typeProductService: TypeProductsService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TypeProductsComponent],
      imports: [HttpClientModule],
      providers: [PaymentTermService],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();
    typeProductService = TestBed.inject(TypeProductsService);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TypeProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
