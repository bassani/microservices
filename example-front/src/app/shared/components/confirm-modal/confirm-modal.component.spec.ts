import { ComponentFixture, TestBed } from '@angular/core/testing';
import { KeycloakService } from 'keycloak-angular';
import { MessageService, SharedModule } from 'primeng/api';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';

import { ConfirmModalComponent } from './confirm-modal.component';

describe('ConfirmModalComponent', () => {
  let component: ConfirmModalComponent;
  let fixture: ComponentFixture<ConfirmModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfirmModalComponent ],
      imports: [SharedModule],
      providers: [
        MessageService,
        { provide: DynamicDialogRef, useValue: { close: () => {} } },
        {
          provide: DynamicDialogConfig,
          useValue: {
            data: { message: 'Teste Mensasgem' },
          },
        },
      ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
