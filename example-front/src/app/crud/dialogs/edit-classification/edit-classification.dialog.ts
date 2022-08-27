import { Component, OnInit, Input } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl,
} from '@angular/forms';
import { KeycloakService } from 'keycloak-angular';
import { MessageService } from 'primeng/api';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { BehaviorSubject } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import {
  IActive,
  ISearchResponse,
} from 'src/app/shared/models/search-simulador';
import { AuthService, ClassificationService } from 'src/app/shared/services';
import { ErrorHandler } from 'src/app/shared/utils/error-handler.service';

@Component({
  selector: 'app-edit-classification',
  templateUrl: './edit-classification.dialog.html',
  styleUrls: ['./edit-classification.dialog.scss'],
})
export class EditClassificationDialog implements OnInit {
  form: FormGroup;
  options = [
    { label: 'Habilitado', value: 'habilitado' },
    { label: 'Desabilitado', value: 'desabilitado' },
  ];
  constructor(
    private _service: ClassificationService,
    private _fb: FormBuilder,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    private _auth: AuthService,
  ) {}

  ngOnInit(): void {
    this.form = this._fb.group({
      name: [{ value: '', disabled: !!this.config.data.id }],
      description: ['', Validators.required],
      id: [],
      active: ['habilitado', Validators.required],
    });
    this.form.patchValue(this.config.data);
  }

  saveClassification() {
    this._auth.canExecuteAction(['update_classification'])
    .pipe(
      switchMap(roles => {
        return this._service.updateClassification(this.form.getRawValue())
      })
    ).subscribe({
      next: () => this.closeModal(),
      error: (err) => ErrorHandler.handleHttpError('Não foi possível salvar a classificação', err)
    });
  }

  addClassification() {
    this._auth.canExecuteAction(['create_classification'])
    .pipe(
      switchMap(roles => {
        return this._service.createClassification(this.form.value)
      })
    ).subscribe({
      next: () => this.closeModal(),
      error: (err) => ErrorHandler.handleHttpError('Não foi possível criar a classificação', err)
    });
   
  }

  closeModal() {
    this.ref.close();
  }
}
