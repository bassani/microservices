import { FlowSimulateComponent } from './components/flow-simulate-select/flow-simulate-select.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { RippleModule } from 'primeng/ripple';
import { CheckboxModule } from 'primeng/checkbox';
import { SidebarModule } from 'primeng/sidebar';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { NotificationsComponent } from './pages/wrapper/notifications/notifications.component';
import { PanelMenuModule } from 'primeng/panelmenu';
import { MenuModule } from 'primeng/menu';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { HttpClientModule } from '@angular/common/http';
import { ChoiceComponent } from './components/choice/choice.component';
import { CdSelectComponent } from './components/cd-select/cd-select.component';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { ReactiveFormsModule } from '@angular/forms';
import { ManufacturerSelectComponent } from './components/manufacturer-select/manufacturer-select.component';
import { MultiSelectModule } from 'primeng/multiselect';
import { OrderTypeComponent } from './components/order-type/order-type.component';
import { ClassificationComponent } from './components/classification/classification.component';
import { CategoryComponent } from './components/category/category.component';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { SalesCalculationComponent } from './components/sales-calculation/sales-calculation.component';
import { TypeProductsComponent } from './components/type-products/type-products.component';
import { DividerModule } from 'primeng/divider';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { NgVarDirective } from './directives/ng-var.directive';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { CalendarModule } from 'primeng/calendar';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { DialogModule } from 'primeng/dialog';
import { TabViewModule } from 'primeng/tabview';
import { ProductComponent } from './components/product/product.component';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { DialogService } from 'primeng/dynamicdialog';
import { PaginatorModule } from 'primeng/paginator';
import { CascadeSelectModule } from 'primeng/cascadeselect';
import { DiscountModalComponent } from './components/discount-modal/discount-modal.component';
import { PaymentTermComponent } from './components/payment-term/payment-term.component';
import { DetailingDialogComponent } from './components/detailing-dialog/detailing-dialog.component';
import { SkeletonModule } from 'primeng/skeleton';
import { NotAllowedComponent } from './pages/not-allowed/not-allowed.component';
import { SupplierComponent } from './components/supplier/supplier.component';
import { ManufacturerFilterComponent } from './components/manufacturer-filter/manufacturer-filter.component';
import { ProductFilterComponent } from './components/product-filter/product-filter.component';
import { StatusComponent } from './components/status/status.component';
import { FirstAccessComponent } from './pages/first-access/first-access.component';
import { UsersComponent } from './components/users/users.component';
import { WithRolesDirective } from './directives/with-roles.directive';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { FieldUtilsService } from './utils/field-utils.service';
import { PaymentTermCdComponent } from './components/payment-term-cd/payment-term-cd.component';
import { BudgetSelectDialog } from './components/budget-select-dialog/budget-select-dialog.component';
import { IdSimulationComponent } from './components/id-simulation/id-simulation.component';
import { FlowApprovalComponent } from './components/flow-approval-select/flow-approval-select.component';
import { ConfirmModalComponent } from './components/confirm-modal/confirm-modal.component';

@NgModule({
  declarations: [
    ChoiceComponent,
    CdSelectComponent,
    ManufacturerSelectComponent,
    OrderTypeComponent,
    ClassificationComponent,
    CategoryComponent,
    SidebarComponent,
    NotificationsComponent,
    NotFoundComponent,
    NotAllowedComponent,
    NgVarDirective,
    WithRolesDirective,
    WelcomeComponent,
    PaymentTermComponent,
    SalesCalculationComponent,
    TypeProductsComponent,
    DiscountModalComponent,
    ProductComponent,
    DetailingDialogComponent,
    SupplierComponent,
    ManufacturerFilterComponent,
    ProductFilterComponent,
    StatusComponent,
    FirstAccessComponent,
    UsersComponent,
    PaymentTermCdComponent,
    BudgetSelectDialog,
    IdSimulationComponent,
    ConfirmModalComponent,
    FlowSimulateComponent,
    FlowApprovalComponent
  ],
  providers: [DialogService, FieldUtilsService],
  imports: [
    CommonModule,
    FormsModule,
    ButtonModule,
    InputTextModule,
    CardModule,
    RippleModule,
    CheckboxModule,
    SidebarModule,
    PanelMenuModule,
    MenuModule,
    AutoCompleteModule,
    ReactiveFormsModule,
    MultiSelectModule,
    HttpClientModule,
    MultiSelectModule,
    DropdownModule,
    DividerModule,
    ProgressSpinnerModule,
    CalendarModule,
    InputTextareaModule,
    DialogModule,
    TabViewModule,
    RadioButtonModule,
    InputNumberModule,
    InputTextareaModule,
    PaginatorModule,
    CascadeSelectModule,
    SkeletonModule
  ],
  exports: [
    CdSelectComponent,
    CascadeSelectModule,
    MultiSelectModule,
    CardModule,
    ReactiveFormsModule,
    InputTextareaModule,
    InputNumberModule,
    TabViewModule,
    ButtonModule,
    SkeletonModule,
    FormsModule,
    InputTextModule,
    RippleModule,
    CheckboxModule,
    SidebarModule,
    PanelMenuModule,
    SidebarComponent,
    NotificationsComponent,
    MenuModule,
    NotFoundComponent,
    NotAllowedComponent,
    HttpClientModule,
    ChoiceComponent,
    ReactiveFormsModule,
    MultiSelectModule,
    ManufacturerSelectComponent,
    OrderTypeComponent,
    ClassificationComponent,
    CategoryComponent,
    PaymentTermComponent,
    SalesCalculationComponent,
    TypeProductsComponent,
    DividerModule,
    ProgressSpinnerModule,
    NgVarDirective,
    WithRolesDirective,
    DiscountModalComponent,
    InputTextareaModule,
    PaginatorModule,
    CalendarModule,
    InputTextareaModule,
    DetailingDialogComponent,
    ProductComponent,
    SupplierComponent,
    IdSimulationComponent,
    ManufacturerFilterComponent,
    ProductFilterComponent,
    StatusComponent,
    UsersComponent,
    ConfirmDialogModule,
    PaymentTermCdComponent,
    BudgetSelectDialog, 
    FlowSimulateComponent,
    FlowApprovalComponent
  ],
})
export class SharedModule { }
