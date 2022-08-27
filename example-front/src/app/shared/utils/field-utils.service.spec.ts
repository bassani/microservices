import { APP_BASE_HREF } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { FormControl, Validators } from '@angular/forms';
import {
  RouterModule,
} from '@angular/router';
import { AuthService } from '../services';
import { ErrorHandler } from './error-handler.service';
import { FieldUtilsService } from './field-utils.service';


describe('Field Utils', () => {
  let service: FieldUtilsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterModule.forRoot([]), HttpClientModule],
      providers: [AuthService, { provide: APP_BASE_HREF, useValue: '/' }, FieldUtilsService],
    });
    service = TestBed.inject(FieldUtilsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('is Required', () => {
    it('Deve retornar se o campo e requerido ou não', () => {
      let requiredControl = new FormControl('', [Validators.required]);
      let nonRequiredControl = new FormControl('');
      expect(service.isRequired(requiredControl)).toEqual(true);
      expect(service.isRequired(nonRequiredControl)).toEqual(false);
    })


    it('Deve realizar handling de exceptional cases', () => {
      expect(service.isRequired(null as unknown as FormControl)).toEqual(false);
      expect(service.isRequired({validator: null} as unknown as FormControl)).toEqual(false);
    })
  })


});
