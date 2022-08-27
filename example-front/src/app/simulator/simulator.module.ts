import { DialogModule } from 'primeng/dialog';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SimulateComponent } from './pages/simulate/simulate.component';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { CascadeSelectModule } from 'primeng/cascadeselect';
import { MultiSelectModule } from 'primeng/multiselect';
import { ReactiveFormsModule } from '@angular/forms';
import { SimulateSpecifics } from './pages/simulate-specifics/simulate-specifics.component';
import { CardModule } from 'primeng/card';
import { SimulationTypeComponent } from './pages/simulation-type/simulation-type.component';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { SimulationDescriptionComponent } from './components/simulation-description/simulation-description.component';
import { SimulationResultComponent } from './pages/simulation-result/simulation-result.component';
import { AuthGuardService } from '../shared/utils/auth-guard.service';
import { SimulationResultCdComponent } from './components/simulation-result-cd/simulation-result-cd.component';
import { SimulateExportComponent } from './pages/simulate-export/simulate-export.component';
import { ButtonModule } from 'primeng/button';
import { TabViewModule } from 'primeng/tabview';
import { TableModule } from 'primeng/table';
import { ChartModule } from 'primeng/chart';
import {ChipsModule} from 'primeng/chips';
import { InputNumberModule } from 'primeng/inputnumber';
import { SimulationTypeInputComponent } from './components/simulation-type-input/simulation-type-input.component';
import { SimulationTypeLabelsComponent } from './components/simulation-type-labels/simulation-type-labels.component';
import { SimulationTrackComponent } from './pages/simulation-track/simulation-track.component';
import { SimulationApprovalFlowComponent } from './pages/simulation-approval-flow/simulation-approval-flow.component';
import { SimulationResumeTabComponent } from './components/simulation-resume-tab/simulation-resume-tab.component';
import { SimulationIndicatorsTabComponent } from './components/simulation-indicators-tab/simulation-indicators-tab.component';
import {TabMenuModule} from 'primeng/tabmenu';
import { LoadingSimulationComponent } from './components/simulation-loader/simulation-loader.component';
import { AutoCompleteModule } from 'primeng/autocomplete';
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {CheckboxModule} from 'primeng/checkbox';

const routes: Routes = [
  { canActivate: [AuthGuardService], path: 'fluxo/:simulationId', component: SimulationApprovalFlowComponent},
  { canActivate: [AuthGuardService], path: 'acompanhar', component: SimulationTrackComponent },
  { canActivate: [AuthGuardService], data: { roles: ['create_simulation'] }, path: 'simular', component: SimulationTypeComponent },
  { canActivate: [AuthGuardService], path: 'resultado/:id', component: SimulationResultComponent },
  { canActivate: [AuthGuardService], data: { roles: ['create_simulation'] }, path: 'simular/:simulationType/:id', component: SimulateComponent },
  {
    canActivate: [AuthGuardService], data: { roles: ['create_simulation'] },
    path: 'simular/:simulationType/:id/efetuar',
    component: SimulateSpecifics
  }
];

/**
 * Modulo principal e foco do sistema
 * encarregado das simulações e a listagem delas
 */
@NgModule({
  declarations: [
    SimulateComponent,
    SimulationTypeComponent,
    SimulateSpecifics,
    SimulationDescriptionComponent,
    SimulationResultComponent,
    SimulateExportComponent,
    SimulationResultCdComponent,
    SimulationTypeInputComponent,
    SimulationTypeLabelsComponent,
    SimulationTrackComponent,
    SimulationApprovalFlowComponent,
    SimulationResumeTabComponent,
    SimulationIndicatorsTabComponent,
    LoadingSimulationComponent,
  ],
  imports: [
    CheckboxModule,
    CommonModule,
    RouterModule.forChild(routes),
    SharedModule,
    CascadeSelectModule,
    MultiSelectModule,
    CardModule,
    ReactiveFormsModule,
    InputTextareaModule,
    InputNumberModule,
    TabViewModule,
    ButtonModule,
    TableModule,
    DialogModule,
    ChartModule,
    TabMenuModule,
    ChipsModule,
    AutoCompleteModule,
    MessageModule,
    MessagesModule
  ],
  providers: []
})
export class SimulatorModule { }
