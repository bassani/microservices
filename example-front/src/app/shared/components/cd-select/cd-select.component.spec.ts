import { HttpClientModule } from '@angular/common/http';
import {
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { DropdownModule } from 'primeng/dropdown';
import { MultiSelectModule } from 'primeng/multiselect';
import { of, throwError } from 'rxjs';
import { CdService } from '../../services/cd/cd.service';
import { FieldUtilsService } from '../../utils/field-utils.service';
import { BASIC, MOCK_CUSTOM_PAGED } from '../../utils/mock';
import { CdSelectComponent } from './cd-select.component';

describe('CdSelectComponent', () => {
  let component: CdSelectComponent;
  let fixture: ComponentFixture<CdSelectComponent>;
  let cdService: CdService;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CdSelectComponent],
      imports: [
        HttpClientModule,
        DropdownModule,
        MultiSelectModule,
        ReactiveFormsModule,
        NoopAnimationsModule,
      ],
      providers: [CdService, FieldUtilsService],
    }).compileComponents();
    cdService = TestBed.inject(CdService);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CdSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Search', () => {
    it('Should select one by one', () => {
      spyOn(component['cdService'], 'search').and.returnValue(of(BASIC.cd));
      const dropdown = fixture.debugElement.nativeElement.querySelector(
        '#cd-multi-select-element > .p-multiselect.p-component'
      );
      component.ngOnInit();
      // check initial status
      expect(component.control.value).toBeFalsy();
      //open dropdown
      dropdown.click();
      fixture.detectChanges();
      //select dropdown items
      const dropItems = dropdown.querySelector('li');
      //click on first item only
      dropItems.click();
      fixture.detectChanges();
      expect(component.control.value[0]).toBeDefined();
    });

    it('Should call the service with the search term', () => {
      const response = MOCK_CUSTOM_PAGED({ id: 1, name: 'teste' }, 10);
      expect(component.suggestions$.value).not.toEqual(response.content);
      spyOn(cdService, 'search').and.returnValue(of(response));
      component.search();
      expect(cdService.search).toHaveBeenCalledTimes(1);
      expect(component.suggestions$.value).toEqual(response.content);
    });

    it('Should handle server errors', () => {
      const response = {
        status: 500,
        error: { message: 'Internal Unit Error' },
      };
      expect(component.suggestions$.value).not.toEqual(response);
      spyOn(cdService, 'search').and.returnValue(throwError(response));
      component.search();
      expect(cdService.search).toHaveBeenCalledTimes(1);
      expect(component.suggestions$.value).toEqual([]);
    });
  });
});
