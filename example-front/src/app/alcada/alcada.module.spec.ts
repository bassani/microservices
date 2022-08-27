import { TestBed } from '@angular/core/testing';
import { AlcadaModule } from './alcada.module';

describe('AlcadaModule', () => {
  let pipe: AlcadaModule;

  beforeEach(() => {
    TestBed.configureTestingModule({ providers: [AlcadaModule] });
    pipe = TestBed.inject(AlcadaModule);
  });

  it('can load instance', () => {
    expect(pipe).toBeTruthy();
  });
});
