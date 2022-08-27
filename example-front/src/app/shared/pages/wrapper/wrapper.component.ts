import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';
import { MenuItem } from 'primeng/api';
import { from } from 'rxjs';
import { NotificationService } from '../../services/notification/notification.service';
import { Notification } from '../../models/notification.model';


@Component({
  selector: 'app-wrapper',
  templateUrl: './wrapper.component.html',
  styleUrls: ['./wrapper.component.scss'],
})
export class WrapperComponent implements OnInit {
  totalElementsPage:number;
  loading: boolean = false;
  footerNotification: boolean;
  modalNotification: boolean = false;
  sortType = 1;
  sort: string;
  pageNumber = 0;
  page: number;
  pageSize = 10;
  size: number;
  showRedDot = false;
  notifications: Notification;
  modalNotificationService: Notification;
  showBellContainer: boolean = false;
  userMenu: MenuItem[] = [
    { label: 'Sair', command: () => this.kc.logout() },
  ];
  user: any;
  timerSubscription: any;

  constructor(
    private _router: Router,
    protected readonly kc: KeycloakService,
    private notificationService: NotificationService
    ) {}

  ngOnInit(): void {
    from(this.kc.loadUserProfile()).subscribe(data => {
      this.user = data
      if(this.user){
        this.loadNotification(0, 10, this.sortType);
          setInterval(()=>{
            if(!this.modalNotification){
              this.loadNotification(0, 10, this.sortType);
            }
          }, 30000);
        }
    })
  }

  paginate($event: {
    first: number;
    rows: number;
    page: number;
    pageCount: number;
  }) {
    const { page, rows } = $event;
    this.changePage(page, rows, 1);
  }

  changePage(page = this.pageNumber, size = this.pageSize, sortType: number): void {
    this.notificationService.getPagination(page, size, sortType);
    this.notificationService.getMessages(page, size, sortType).subscribe((data: any) => {
      this.modalNotificationService = data;
      this.pageNumber = data.number;
      this.pageSize = data.size;
      this.sortNotification(this.modalNotificationService);
    })
  }

  get notificationsPagination$(): any {
    return this.notificationService.notificationsPagination$;
  }

  startModal(startModal: boolean){
    this.modalNotification = startModal;
    this.changePage(0, 10, 1);
  }

  sortNotificationType(sort: number){
    this.sortType = sort;
    if(this.modalNotification){
      this.changePage(this.pageNumber, this.pageSize, this.sortType);
    }else{
      this.loadNotification(this.pageNumber, this.pageSize, this.sortType);
    }
  }

  navTo(route: string): void {
    this._router.navigateByUrl(route);
  }

  loadNotification(number = 0, size = 10, sortType: number){
    this.showRedDot = false;
      this.notificationService
      .getMessages(number, size, sortType)
      .subscribe((data: any) => {
        this.notifications = data;
        this.modalNotificationService = data;
        this.sortNotification(this.notifications);
      })
  }

  sortNotification(type: Notification){
    type.content.forEach(notification => {
      if(notification.message.simulationStatus.startsWith('PENDENTE')){
        notification.message.simulationStatus = "Pendente Aprovação"
      }
      if(!notification.read){
        this.showRedDot = true;
      }
    });
    type.content.sort(function(a, b){
      if(a.read === b.read){
        return 0;
      }
      return a.read ? 1 : -1;
    });
  }
}
