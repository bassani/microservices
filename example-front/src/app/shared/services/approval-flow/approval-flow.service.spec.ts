import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ApiService } from '../api/api.service';

import { ApprovalFlowService } from './approval-flow.service';

describe('ApprovalFlowService', () => {
  let service: ApprovalFlowService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
      providers: [
        ApiService
      ]
    });
    service = TestBed.inject(ApprovalFlowService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
