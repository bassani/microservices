import { SimulateFollowUpService } from './simulate-follow-up.service';
import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

describe('SimulateFollowUpService', () => {
  let service: SimulateFollowUpService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule]
    });
    service = TestBed.inject(SimulateFollowUpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
