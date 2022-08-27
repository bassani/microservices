import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SharedModule } from 'primeng/api';
import { of, throwError } from 'rxjs';
import { AuthService } from '../../services';

import { FirstAccessComponent } from './first-access.component';

describe('FirstAccessComponent', () => {
  let component: FirstAccessComponent;
  let fixture: ComponentFixture<FirstAccessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SharedModule, HttpClientModule],
      declarations: [ FirstAccessComponent ],
      providers: [{
        provide: AuthService, 
        useValue: {setCredentials: () => of({})}
      }]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FirstAccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Deve realizar o hanlding de erros do serviço ao setar as credenciasi', () => {
    const ERROR = {status: 401, message: 'MOCK ERROR'}
    spyOn(component['authService'], 'setCredentials').and.returnValue(throwError(ERROR))
    component.ngOnInit()
    expect(component.status.value.status).toEqual('erroed')
  })

});
