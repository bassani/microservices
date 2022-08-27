import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ButtonModule } from 'primeng/button';
import { SimulateService } from '../../services/simulate/simulate.service';

import { SimulateExportComponent } from './simulate-export.component';

describe('SimulateExportComponent', () => {
  let component: SimulateExportComponent;
  let fixture: ComponentFixture<SimulateExportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SimulateExportComponent ],
      imports: [HttpClientModule, ButtonModule],
      providers: [{provide: SimulateService, useValue: {downloadResume: () => true}}],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SimulateExportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Export result', () => {
    it('Deve chamar o serviço de download', () => {
      spyOn(component['simulationService'], 'downloadResume').and.returnValue({});
      spyOn(component,'exportResult').and.callThrough();
      fixture.detectChanges();
      fixture.nativeElement.querySelector('#export-result-button').click();
      expect(component.exportResult).toHaveBeenCalledTimes(1)
      expect(component['simulationService'].downloadResume).toHaveBeenCalledTimes(1)
    })
  })
});
