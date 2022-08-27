import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CheckboxModule } from 'primeng/checkbox';
import { RouterModule, Routes } from '@angular/router';
import { SalesReportComponent } from './pages/sales-report/sales-report.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { SharedModule } from '../shared/shared.module';
import { AuthGuardService } from '../shared/utils/auth-guard.service';


const routes: Routes = [
  {canActivate: [AuthGuardService], data: { roles: ['download_sales_report'] }, path: 'vendas', component: SalesReportComponent },
];

@NgModule({
  declarations: [SalesReportComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    CheckboxModule,
    SharedModule,
    CardModule
  ]
})
export class ReportsModule { }
