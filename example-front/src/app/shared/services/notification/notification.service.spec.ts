import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MessageService } from 'primeng/api';

import { NotificationService } from './notification.service';

describe('NotificationService', () => {
  let service: NotificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        { provide: MessageService, useValue: { add: () => {} } },
      ],
    });
    service = TestBed.inject(NotificationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  describe('Retorno de notificação', () => {
    it('Deve retornar a resposta do servidor', () => {
      service.getMessages(0, 10, 1).subscribe((data) => {
        expect(data);
      });
    });
  });
});
