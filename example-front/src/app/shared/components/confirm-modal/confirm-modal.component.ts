import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-confirm-modal',
  templateUrl: './confirm-modal.component.html',
  styleUrls: ['./confirm-modal.component.scss']
})
export class ConfirmModalComponent implements OnInit {
  
  message: string;
  constructor(
    public config: DynamicDialogConfig,
    public ref: DynamicDialogRef
  ) { }

  ngOnInit(): void {
    this.message = this.config.data;
  }

  closeModal() {
    this.ref.close();
  }

}
