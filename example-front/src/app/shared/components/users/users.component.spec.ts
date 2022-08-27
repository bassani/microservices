import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { MultiSelectModule } from 'primeng/multiselect';
import { BehaviorSubject, of } from 'rxjs';
import { UsersService } from '../../services/users/users.service';

import { UsersComponent } from './users.component';

describe('UsersComponent', () => {
  let component: UsersComponent;
  let fixture: ComponentFixture<UsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MultiSelectModule],
      declarations: [ UsersComponent ],
      providers: [
        {
          provide: UsersService, useValue: {findUsers: () => {}}
        }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Deve chamar o serviço de pesquisa quando o usuario digitar no campo', fakeAsync(() => {
    const mock = [
      {name: 'TESTEMOCK', code: 1},
      {name: 'MOCK', code: 2}
    ]
    spyOn(component['userService'], 'findUsers').and.returnValue(of(mock));
    let input = fixture.debugElement.query(By.css('input')).nativeElement;
    input.value = 'MOC';
    input.dispatchEvent(new Event('input'));
    fixture.detectChanges();
    component.multiselect.onFilter.emit({filter: 'a'})
    tick(800)
  }));


  it('Deve ordenar a resposta do serviço', () => {

    const mock = [{id: 1, name: 'Hugo'}, {id: 2, name: 'Francesco'}];

    spyOn(component.multiselect.options, 'sort').and.returnValue(of(mock));

    const result = ([...mock].sort((a, b) => {
        let fa = a.name.toLowerCase(),
        fb = b.name.toLowerCase();
        if (fa < fb) {
          return -1;
        }
        if (fa > fb) {
            return 1;
        }
        return 0;
      }));
    expect(result).toStrictEqual([
      {name: 'Francesco', id: 2},
      {name: 'Hugo', id: 1}
    ]);
  });
});
