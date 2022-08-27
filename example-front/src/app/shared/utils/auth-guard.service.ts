import { ConstantPool } from '@angular/compiler';
import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';
import { Observable, of } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';
import { AuthService } from '../services';

@Injectable({
  providedIn: 'root',
})
export class AuthGuardService extends KeycloakAuthGuard implements CanActivateChild, CanActivate {
  constructor(protected readonly router: Router,     protected readonly keycloak: KeycloakService    ) {
    super(router, keycloak);

  }

  public async canActivateChild(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ) {
    return this.isAccessAllowed(route, state);
  }


  public async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ) {
    if (!this.authenticated) {
      await this.keycloak.login({
        redirectUri: window.location.origin + state.url,
      });
      return false;
    }

    // Get the roles required from the route.
    const requiredRoles = route.data.roles;

    // Allow the user to to proceed if no additional roles are required to access the route.
    if (!(requiredRoles instanceof Array) || requiredRoles.length === 0) {
      return true;
    }
    let user = await this.keycloak.loadUserProfile()
    // Allow the user to proceed if all the required roles are present.
    let res = requiredRoles.every((role) => this.roles.includes(role));
    if(!res) this.router.navigateByUrl('/401')
    return requiredRoles.every((role) => this.roles.includes(role));
  }
}
