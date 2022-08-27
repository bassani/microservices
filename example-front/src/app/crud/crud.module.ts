import { ExpirationComponent } from './pages/expiration/expiration.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ForecastComponent } from './pages/forecast/forecast.component';

import { CardModule } from 'primeng/card';
import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ReactiveFormsModule } from '@angular/forms';
import { ClassificationComponent } from './pages/classification/classification.component';
import { SharedModule } from '../shared/shared.module';
import { RippleModule } from 'primeng/ripple';
import { EditClassificationDialog } from './dialogs/edit-classification/edit-classification.dialog';
import { AuthGuardService } from '../shared/utils/auth-guard.service';
import { UserRegisterComponent } from './pages/user-register/user-register.component';
import { EditUserComponent } from './dialogs/edit-user/edit-user.component';

const routes: Routes = [
  {canActivate: [AuthGuardService], data: {roles: ['view_forecast']}, path: 'forecast/:type', component: ForecastComponent },
  {canActivate: [AuthGuardService], data: {roles: ['view_classification']}, path: 'classificacao', component: ClassificationComponent },
  {canActivate: [AuthGuardService], data: {roles: ['view_expiration']}, path: 'expiracao', component: ExpirationComponent },
  {canActivate: [AuthGuardService], path: 'cadastro/usuarios', component: UserRegisterComponent },
];

@NgModule({
  declarations: [
    ForecastComponent,
    ClassificationComponent,
    ExpirationComponent,
    EditClassificationDialog,
    UserRegisterComponent,
    EditUserComponent
  ],
  entryComponents: [EditClassificationDialog],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    PasswordModule,
    CardModule,
    InputTextModule,
    ButtonModule,
    ReactiveFormsModule,
    SharedModule,
    RippleModule
  ],
})
export class CrudModule {}
