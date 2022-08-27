import { fakeAsync, TestBed, tick } from '@angular/core/testing';
import { of } from 'rxjs';
import { delay } from 'rxjs/operators';

import { LoaderService } from './loader.service';
describe('LoaderService', () => {
  let service: LoaderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoaderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
    expect(service.loading).toBeDefined();
  });

  describe('Loading', () => {

    it('Should be able to start and stop a loader', () => {
      expect(service.loading).toEqual(false);
      service.start();
      expect(service.loading).toEqual(true);
      service.stop();
      expect(service.loading).toEqual(false);
    })

    it('Should not do anything is the load counter is already zero', () => {
      expect(service.loading).toEqual(false);
      expect(service['_loaders']).toEqual(0);
      service.stop();
      expect(service.loading).toEqual(false);
      expect(service['_loaders']).toEqual(0);

    })

    it('Should work for multiple loaders', fakeAsync(() => {
      expect(service.loading).toEqual(false);
      // Starts 3 loaders
      service.start();
      service.start();
      service.start();
      expect(service.loading).toEqual(true);

      const first = of(1).pipe(delay(25)).subscribe(data => service.stop());
      const seccond = of(2).pipe(delay(50)).subscribe(data => service.stop());
      const third = of(3).pipe(delay(100)).subscribe(data => service.stop());

      tick(25);
      expect(service.loading).toEqual(true)
      tick(50)
      expect(service.loading).toEqual(true)
      tick(50)
      expect(service.loading).toEqual(false)
    }))
  })
});
