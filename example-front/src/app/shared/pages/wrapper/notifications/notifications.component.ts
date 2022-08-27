import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { MenuItem, MessageService } from 'primeng/api';
import { NotificationService } from 'src/app/shared/services/notification/notification.service';
import { Notification } from './../../../models/notification.model';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss']
})
export class NotificationsComponent implements OnInit {

  @Input() notifications: Notification;
  @Input() footerNotification: boolean;

  notificationId: number;
  keyUser: string;
  allNotification = false;
  messageService: any;
  notificationRead: boolean;
  showNotificationRead: boolean = false;
  approver: boolean = true;


  @Output() reloadNotifications = new EventEmitter<number>();
  @Output() sortNotificationsType = new EventEmitter<number>();
  @Output() modalNotification = new EventEmitter<boolean>();

  sortOptions: MenuItem[] = [
    {
      label: 'Mais recentes',
      command: () =>
      {
        this.sortNotificationsType.emit(0);
      }
    },
    {
      label: 'Mais próximo da data de expiração',
      command: () =>
      {
        this.sortNotificationsType.emit(1);
      }
    }

  ];

  notificationOptions: MenuItem[] = [
    {
      label: "Marcar como lida / não lida",
      command: () =>
      {
        this.notificationService.readNotifications(this.notificationRead, this.notificationId, this.keyUser).subscribe(
          (_data: any) => {
            if(this.notificationRead){
              this.message.add({
                key: 'main',
                severity: 'success',
                summary: 'Notificação visualizada',
                detail: 'Essa notificação foi visualizada com sucesso.',
                life: 3000,
              });
            }else{
              this.message.add({
                key: 'main',
                severity: 'success',
                summary: 'Notificação marcada como não lida',
                detail: 'Essa notificação foi marcada como não lida com sucesso.',
                life: 3000,
              });
            }
            this.reloadNotifications.emit();
          },
          (err: any) => {
            this.message.add({
              key: 'main',
              severity: 'error',
              summary: 'Falha na visualização',
              detail:
                err.error.description ||
                'Ocorreu uma falha na visualização. Tente novamente mais tarde.',
              life: 3000,
            });
          }
        );
      }
    }

  ];

  constructor(
    private message: MessageService,
    private notificationService: NotificationService,
    protected readonly keycloak: KeycloakService,
    ) {}

  async ngOnInit(): Promise<void> {
    const user = await this.keycloak.getKeycloakInstance().loadUserInfo();
    type ObjectKey = keyof typeof user;
    const groups = 'groups' as ObjectKey;
    const userGroup: string = user[groups][0];
    const perfil = userGroup.replace("/", '');
    if(perfil === "Analista" || perfil === "Comprador"){
      this.approver = false;
    }
    if(this.footerNotification){
      this.allNotification = true;
    }
  }

  enviaModal(){
    this.modalNotification.emit(true);
  }

}
