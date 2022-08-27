import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NotFoundComponent } from './shared/pages/not-found/not-found.component';
import { WelcomeComponent } from './shared/pages/welcome/welcome.component';
import { WrapperComponent } from './shared/pages/wrapper/wrapper.component';
import { AuthGuardService } from './shared/utils/auth-guard.service';
import { NotAllowedComponent } from './shared/pages/not-allowed/not-allowed.component';

/**
 * Lista de rotas e modulos lazy loaded
 * utilizar o AuthGuardService e data: {roles: ['LISTA_DE_ROLES_NECESSARIOS']} para determinar se o usuario
 * precisa dos roles no keycloak para acessar a tela. Caso seja uma tela livre não colocar o data
 * ou informar o array de roles como [] ou não utilizar o AuthGuardService
 */

const routes: Routes = [
  {
    path: 'login',
    loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: '',
    component: WrapperComponent,
    canActivate: [AuthGuardService],
    data: { roles: [] },
    children: [
      {
        path: 'parametros',
        loadChildren: () =>
          import('./crud/crud.module').then((m) => m.CrudModule),
      },
      { path: 'inicio', component: WelcomeComponent },
      {
        path: 'parametros',
        loadChildren: () =>
          import('./crud/crud.module').then((m) => m.CrudModule),
      },
      {
        path: 'relatorio',
        loadChildren: () =>
          import('./reports/reports.module').then((m) => m.ReportsModule),
      },
      {
        path: 'simulador',
        loadChildren: () =>
          import('./simulator/simulator.module').then((m) => m.SimulatorModule),
      },
      {
        path: 'alcada',
        loadChildren: () =>
          import('./alcada/alcada.module').then((m) => m.AlcadaModule),
      },
    ],
  },
  { path: '404', component: NotFoundComponent },
  { path: '401', component: NotAllowedComponent },
  { path: '**', redirectTo: '404' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
