import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';

import { AlcadaService } from './alcada.service';
import { of } from 'rxjs';

describe('AlcadaService', () => {
  let service: AlcadaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    service = TestBed.inject(AlcadaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('Retorno simulações pendentes', () => {

    it('Deve retornar a resposta do servidor', () => {
      const mockBody =
        {
          registerDate: [new Date, null],
          idSimulation: null,
          user: null,
          supplier: null,
          manufacturer: null,
          category: null
        };
      spyOn(service['api'], 'request').and.returnValue(of(mockBody));
      service.findSimulationsPending(mockBody, 0, 10).subscribe(
        (data) => expect(data)
      );
      expect(service['api'].request).toHaveBeenCalledTimes(1);
      });
    });
  });
