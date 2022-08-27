import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, discardPeriodicTasks, fakeAsync, flush, flushMicrotasks, TestBed, tick } from '@angular/core/testing';
import { FormControl, FormGroup } from '@angular/forms';
import { LoadingSimulationComponent } from './simulation-loader.component';


describe('SimulationTypeInputComponent', () => {
  let component: LoadingSimulationComponent;
  let fixture: ComponentFixture<LoadingSimulationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoadingSimulationComponent ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingSimulationComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Steps', () => {
    it('Deve passar por todos os steps ate o timeout chegar', fakeAsync(() => {

        component['multiplierMS'] = 1;
        component['maxTimeoutMS'] = 100;
        component.ngOnDestroy();
        component.ngOnInit();
        expect(component.message).toEqual(component.messages[0]);
        tick(18 * component['multiplierMS']);

        expect(component.message).toEqual(component.messages[1]);
        tick(8 * component['multiplierMS']);

        expect(component.message).toEqual(component.messages[2]);
        tick(8 * component['multiplierMS']);

        expect(component.message).toEqual(component.messages[3]);
        tick(8 * component['multiplierMS']);

        expect(component.message).toEqual(component.messages[4]);
        flush();
        flushMicrotasks();
        discardPeriodicTasks()
        tick(Infinity);
        expect(component.message).toEqual(component.ERR_PROCESSING);
        component.ngOnDestroy();
    }))
  })
});
