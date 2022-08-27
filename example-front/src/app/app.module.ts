import { BrowserModule } from '@angular/platform-browser';
import { APP_INITIALIZER, LOCALE_ID, NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthModule } from './auth/auth.module';
import { SharedModule } from './shared/shared.module';
import { WrapperComponent } from './shared/pages/wrapper/wrapper.component';
import { AuthGuardService } from './shared/utils/auth-guard.service';
import { HttpClientModule } from '@angular/common/http';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { AuthService } from './shared/services';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { environment } from 'src/environments/environment';
import { CommonModule, registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';

registerLocaleData(localePt, 'pt');

/**
 * Função encarregada de inicializar a instancia do keycloak com as credenciais do environment
 */
function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: environment.KC.URL,
        realm: environment.KC.REALM,
        clientId: environment.KC.CLIENT,
      },
      bearerExcludedUrls: ['/assets'],
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html',
      },
    });
}
@NgModule({
  declarations: [AppComponent, WrapperComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    SharedModule,
    AuthModule,
    HttpClientModule,
    ToastModule,
    KeycloakAngularModule,
    CommonModule
  ],
  providers: [AuthGuardService, AuthService, MessageService,
    {provide: LOCALE_ID, useValue: 'pt' },
    {
    provide: APP_INITIALIZER,
    useFactory: initializeKeycloak, // função declarada acima
    multi: true,
    deps: [KeycloakService],
  }],
  bootstrap: [AppComponent],
})
export class AppModule {}
