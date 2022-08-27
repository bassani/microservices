import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ClassificationService } from 'src/app/shared/services';
import { DialogService } from 'primeng/dynamicdialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IClassification } from 'src/app/shared/models/classification.model';
import { EditClassificationDialog } from '../../dialogs/edit-classification/edit-classification.dialog';
import { $ } from 'protractor';

@Component({
  selector: 'app-classification',
  templateUrl: './classification.component.html',
  styleUrls: ['./classification.component.scss'],
})
export class ClassificationComponent implements OnInit {
  form: FormGroup;
  constructor(
    private _service: ClassificationService,
    private _message: MessageService,
    private _dialog: DialogService,
    private _fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.form = this._fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
    });
    this.getAll();
  }

  paginate($event: {
    first: number;
    rows: number;
    page: number;
    pageCount: number;
  }) {
    const { page, rows } = $event;
    this.changePage(page, rows);
  }

  changePage(page = 0, size = 10): void {
    this._service.getAll(page, size);
  }

  getAll(): void {
    this.changePage();
  }

  get classifications$(): any {
    return this._service.classifications$;
  }

  downloadCurrentClassifications(): void {
    return this._service.downloadCurrentClassifications();
  }

  editOrAddClassification(classification: IClassification): void {
     this._dialog.open(EditClassificationDialog, {
      data: classification,
      width: '35%',
      header: classification.id
        ? 'Alterar Classificação'
        : 'Cadastrar Classificação',
    });

  }
}
