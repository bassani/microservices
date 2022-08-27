import { MOCK_NOTIFICATIONS } from 'src/app/shared/utils/mock';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MessageService, SharedModule } from 'primeng/api';
import { NotificationService } from 'src/app/shared/services/notification/notification.service';
import { of } from 'rxjs';

import { NotificationsComponent } from './notifications.component';
import { HttpClientModule } from '@angular/common/http';
import { KeycloakService } from 'keycloak-angular';

describe('NotificationsComponent', () => {
  let component: NotificationsComponent;
  let fixture: ComponentFixture<NotificationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NotificationsComponent],
      imports: [
        HttpClientModule,
        SharedModule,
      ],
      providers: [MessageService,
        {
          provide: NotificationService,
          useValue: {
            getMessages: (...args: any[]) => of(MOCK_NOTIFICATIONS),
            readNotifications: (...args: any[]) => of()
          }
        },
        { provide: KeycloakService, useValue: { getUserRoles: (...args: any[]) => {return []} }},
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Verifica a chamada do serviço de leitura de notificação', () => {
  it('Deve enviar os dados para o serviço e informar caso sucesso', () => {
    const mock = {
      ids: [
        121,
        6
    ],
    keycloakUserId: "123-4fea",
    read: 0
    };
    spyOn(component['message'], 'add').and.returnValue({});
    spyOn(component['notificationService'], 'readNotifications').and.returnValue(of(mock));
    });
  });
});
