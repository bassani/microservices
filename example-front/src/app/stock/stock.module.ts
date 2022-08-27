import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from '../shared/shared.module';
import {ToolbarModule} from 'primeng/toolbar';
import {KeyFilterModule} from 'primeng/keyfilter';

/**
 * Modulo atualmente em desuso
 * Criado inicialmente para atender uma demanda emergencial que foi cancelada
 * @deprecated remover na proxima versão junto com os serviços e modelos
 */
@NgModule({
  declarations: [ ],
  imports: [
    CommonModule,
    SharedModule,
    ToolbarModule,
    KeyFilterModule
  ]
})
export class StockModule { }
