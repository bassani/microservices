import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { Path } from '../../consts/path';
import { CdService } from './cd.service';

describe('CdService', () => {
  let service: CdService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    service = TestBed.inject(CdService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
    expect(service.endpoints.search).toEqual('/distributionCenters');
  });

  describe('Search CDs', () => {
    it('Should do a search request and handle success cases', () => {
      const response = [
        { id: 900, name: 'EMBU' },
        { id: 905, name: 'PARANA' },
      ];
      spyOn(service['_http'], 'get').and.returnValue(of(response));
      service.search().subscribe((data) => {
        expect(data).toEqual(response);
      });
      expect(service['_http'].get).toHaveBeenCalledTimes(1);
      expect(service['_http'].get).toHaveBeenCalledWith(
        `${Path.HTTP_API_BASE}${service.endpoints.search}`,
        { params: { page: '0', size: '999' } }
      );
    });

    it('Should do a search request and handle error cases', () => {
      const response = { status: 500, error: { message: 'Not Found' } };
      spyOn(service['_http'], 'get').and.returnValue(throwError(response));
      service.search().subscribe(
        (data) => {},
        (error) => {
          expect(error.status).toEqual(response.status);
        }
      );
      expect(service['_http'].get).toHaveBeenCalledTimes(1);
      expect(service['_http'].get).toHaveBeenCalledWith(
        `${Path.HTTP_API_BASE}${service.endpoints.search}`,
        { params: { page: '0', size: '999' } }
      );
    });
  });
});
