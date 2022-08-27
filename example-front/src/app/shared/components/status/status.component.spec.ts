import { ReactiveFormsModule } from '@angular/forms';
import { MultiSelectModule } from 'primeng/multiselect';
import { DropdownModule } from 'primeng/dropdown';
import { HttpClientModule } from '@angular/common/http';
import { StatusService } from './../../services/status/status.service';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatusComponent } from './status.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { BASIC, MOCK_CUSTOM_PAGED } from '../../utils/mock';
import { of, throwError } from 'rxjs';

describe('StatusComponent', () => {
  let component: StatusComponent;
  let fixture: ComponentFixture<StatusComponent>;
  let status: StatusService;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StatusComponent],
      imports: [
        HttpClientModule,
        DropdownModule,
        MultiSelectModule,
        ReactiveFormsModule,
        NoopAnimationsModule
      ],
      providers: [StatusService]
    }).compileComponents();
    status = TestBed.inject(StatusService)
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Search', () => {
    it('Should select one by one', () => {
      spyOn(component['status'], 'search').and.returnValue(of(BASIC.cd));
      expect(component.control.value).toBeDefined();
    });

    it('Should call the service with the search term', () => {
      const response = [{ id: 1, name: 'teste' }];
      expect(component.status$.value).not.toEqual(response);
      spyOn(status, 'search').and.returnValue(of(response));
      component.search();
      expect(status.search).toHaveBeenCalledTimes(1);
      expect(component.status$.value).toEqual(response);
    });

    it('Should handle server errors', () => {
      const response = {
        status: 500,
        error: { message: 'Internal Unit Error' },
      };
      expect(component.status$.value).not.toEqual(response);
      spyOn(status, 'search').and.returnValue(throwError(response));
      component.search();
      expect(status.search).toHaveBeenCalledTimes(1);
      expect(component.status$.value).toEqual([]);
    });
  });
});
