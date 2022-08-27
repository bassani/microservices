import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Observable, of } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from '../../services';
import { IMenu } from '../../models/menu.model';
import pkg from '../../../../../package.json';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
  $menu: Observable<MenuItem[]>;
  version: '0.0.0';
  constructor(private _auth: AuthService, private _router: Router) {}

  ngOnInit(): void {
    this.loadMenu();
    this.version = (pkg as any).version;
  }

  loadMenu(): void {
    this.$menu = this._auth.menuItems().pipe(
      switchMap((value: IMenu[]): Observable<MenuItem[]> => {
        return of(
          value.map((menu: IMenu) => {
            return {
              label: menu.name,
              icon: `pi pi-fw ${menu.icon}`,
              items: menu.items?.map((subMenu) => ({
                label: subMenu.name,
                icon: `pi pi-fw ${subMenu.icon || menu.icon}`,
                routerLink: subMenu.url,
              })),
              expanded: this.checkActiveState(menu.items || []),
            };
          })
        );
      })
    );
  }

  checkActiveState(subMenus: any[]) {
    return (
      subMenus.filter((e) => this._router.url.startsWith(e.url)).length > 0
    );
  }
}
