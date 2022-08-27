import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { IdSimulationService } from './id-simulation.service';

describe('IdSimulationService', () => {
  let service: IdSimulationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    service = TestBed.inject(IdSimulationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
