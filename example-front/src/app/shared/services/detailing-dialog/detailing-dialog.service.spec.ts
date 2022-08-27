import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { ApiService } from '../api/api.service';

import { DetailingDialogService } from './detailing-dialog.service';

describe('DetailingDialogService', () => {
  let service: DetailingDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [ApiService],
    });
    service = TestBed.inject(DetailingDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
    expect(service.endpoints.search).toEqual('/optionsDetailing/search');
  });
  describe('Search Type Products', () => {
    it('Should do a search request and handle success cases', () => {
      const response = [
        {
          id: 1,
          items: [
            {
              id: 1,
              name: 'Somente itens inativos',
              inactive: false,
              details: 'inactive',
            },
          ],
        },
        {
          id: 2,
          items: [
            {
              id: 1,
              name: 'Desconsiderar promopacks',
              inactive: false,
              details: 'promoPacks',
            },
            {
              id: 2,
              name: 'Somente promopacks',
              inactive: false,
              details: 'promoPacks',
            },
          ],
        },
        {
          id: 3,
          items: [
            {
              id: 1,
              name: 'Desconsiderar cotados',
              inactive: true,
              details: 'quoted',
            },
            {
              id: 2,
              name: 'Somente cotados',
              inactive: true,
              details: 'quoted',
            },
          ],
        },
      ];
      spyOn(service['_api'], 'request').and.returnValue(of(response));
      service.getDetailingOptions().subscribe((data) => {
        expect(data).toEqual(response);
      });
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    });

    it('Should do a search request and handle error cases', () => {
      const response = { status: 500, error: { message: 'Not Found' } };
      spyOn(service['_api'], 'request').and.returnValue(throwError(response));
      service.getDetailingOptions().subscribe(
        (data) => {},
        (error) => {
          expect(error.status).toEqual(response.status);
        }
      );
      expect(service['_api'].request).toHaveBeenCalledTimes(1);
    });
  });
});
