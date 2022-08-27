import { ApprovalToSendModalComponent } from './pages/approval-to-send/approval-to-send-modal/approval-to-send-modal.component';
import { ApprovalToSendComponent } from './pages/approval-to-send/approval-to-send.component';
import { DialogModule } from 'primeng/dialog';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { CascadeSelectModule } from 'primeng/cascadeselect';
import { MultiSelectModule } from 'primeng/multiselect';
import { ReactiveFormsModule } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { AuthGuardService } from '../shared/utils/auth-guard.service';
import { ButtonModule } from 'primeng/button';
import { TabViewModule } from 'primeng/tabview';
import { TableModule } from 'primeng/table';
import { ChartModule } from 'primeng/chart';
import { ChipsModule } from 'primeng/chips';
import { InputNumberModule } from 'primeng/inputnumber';
import { TabMenuModule } from 'primeng/tabmenu';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { PendingApprovalComponent } from './pages/pending-approval/pending-approval.component';

const routes: Routes = [
  { canActivate: [AuthGuardService], path: 'pendentes', component: PendingApprovalComponent },
  { canActivate: [AuthGuardService], path: 'aprrovadas', component: ApprovalToSendComponent },
];

/**
 * Modulo principal e foco do sistema
 * encarregado das simulações e a listagem delas
 */
@NgModule({
  declarations: [
    PendingApprovalComponent,
    ApprovalToSendComponent,
    ApprovalToSendModalComponent
  ],
  imports: [
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
export class AlcadaModule { }
