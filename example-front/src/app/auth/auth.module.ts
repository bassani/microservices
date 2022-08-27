import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { SharedModule } from '../shared/shared.module';

import { PasswordModule } from 'primeng/password';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ReactiveFormsModule } from '@angular/forms';


const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'register/:token', component: RegisterComponent },
  { path: '', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },

]

@NgModule({
  declarations: [LoginComponent, RegisterComponent],
  providers: [],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SharedModule,
    PasswordModule,
    CardModule,
    InputTextModule,
    ButtonModule,
    ReactiveFormsModule
  ],
})
export class AuthModule { }
