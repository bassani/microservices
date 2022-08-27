import { HttpClientModule } from '@angular/common/http';
import { fakeAsync, TestBed, tick } from '@angular/core/testing';
import { ApiService } from 'src/app/shared/services';

import { StockService } from './stock.service';

describe('StockService', () => {
  let service: StockService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [ApiService]
    });
    service = TestBed.inject(StockService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  it('Deve realizar o download dos arquivos', () => {
    spyOn(service, 'save').and.returnValue(null);
    spyOn(service['api'], 'request').and.callThrough();
    let mockFilters = {dtInicialPedido: new Date(), dtFinalPedido: new Date(), cdProdutos: [], cdFiliais: [], canal: []};
    service.downloadConference(mockFilters).subscribe();
    expect(service['api'].request).toHaveBeenCalledTimes(1);
    expect(service.save).toHaveBeenCalledTimes(0);
  })
});
