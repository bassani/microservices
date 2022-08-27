import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { BehaviorSubject } from 'rxjs';
import { ACESS_PROFILE } from 'src/app/shared/consts/acess-profile';
import { AREA_CODE } from 'src/app/shared/consts/area-code';
import { AuthService } from 'src/app/shared/services';
import { UsersService } from 'src/app/shared/services/users/users.service';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit {
  form: FormGroup;
  keyCloakUserId: string;

  options = [
    { label: 'Abastecimento', value: AREA_CODE.ABASTECIMENTO },
    { label: 'Comercial', value: AREA_CODE.COMERCIAL }
  ];

  optionsAcessProfile = [
    { label: 'Gestor', value: ACESS_PROFILE.GESTOR },
    { label: 'Aprovador', value: ACESS_PROFILE.APROVADOR },
    { label: 'Administrador', value: ACESS_PROFILE.ADMINISTRADOR }
  ];

  optionsStatus = [
    { label: 'Ativo', value: true},
    { label: 'Inativo', value: false },
  ];

  positions$ = new BehaviorSubject<{id: number, name: string}[]>([]);
  constructor(
    private _fb: FormBuilder,
    public config: DynamicDialogConfig,
    private usersService: UsersService,
    private _message: MessageService,
    public ref: DynamicDialogRef,
    private _auth: AuthService
  ) { 
  }

  ngOnInit(): void {
    this.form = this._fb.group({
      fullName: [],
      registrationNumber: ['',Validators.compose([Validators.maxLength(20), Validators.pattern("^[0-9]*$")])],
      areaCode: [],
      groupId: [],
      groupName: [],
      activationStatus: [],
      email: ['', Validators.compose([Validators.required, Validators.email])],
      phoneNumber: ['', [Validators.maxLength(12), Validators.pattern("^[0-9]*$")]],
      accessProfile: [],
      vacationReturnDate: [],
      keycloakUserId: []
    });
  
    this.getPositions();
    this.keyCloakUserId = this.config.data?.user?.keyCloakUserId;
    if(this.keyCloakUserId) {
      this.getUserPosition();
      this.form.patchValue(this.config.data.user);
      this.form.patchValue({keycloakUserId: this.keyCloakUserId})
      if(this.config.data.user.vacationReturnDate) {
        const newDate = this.getDate();
        this.form.patchValue({vacationReturnDate: newDate})
      }
    }
  }

  getPositions() {
    this.usersService.getAllPositions()
    .subscribe(
      (data) => {
        this.positions$.next(data)
      },
      (err) => this.positions$.next([])
    )
  }

  getUserPosition(): void {
    this.usersService.getPositionUser(this.config.data?.user?.keyCloakUserId)
    .subscribe(
      (data) => {
        if(data) {
          this.form.patchValue({groupId: data.id})
        }
      }
    )
  }

  confirmButton(): void {
    this.form.markAllAsTouched();

    if (this.form.invalid){
      return this._message.add({
        severity: 'error',
        summary: 'Verifique os campos',
        detail: 'Formulário inválido',
        key: 'main'
      });
    }
    
    this.keyCloakUserId ? this.editUser() : this.addUser();
  }


  addUser() {
    this._auth.canExecuteAction(['ROLE_USERS_REGISTRATION_SAVE'])
    .pipe(
      switchMap(roles => {
        return this.usersService.createUser(this.form.value);;
      })
    ).subscribe({
      next: () => {
        this._message.add({
          key: 'main',
          severity: 'success',
          summary: 'Usuário cadastrado!',
          detail: 'Usuário cadastrado com sucesso!',
          life: 3000,
        });
        this.closeModal()
      },
      error: (err) => {
        this._message.add({
          key: 'main',
          severity: 'error',
          summary: 'Erro ao cadastrar usuário',
          detail: err.error.message,
          life: 3000,
        });
      }
    });
   
  }

  editUser() {
    this._auth.canExecuteAction(['ROLE_USERS_REGISTRATION_UPDATE'])
    .pipe(
      switchMap(roles => {
        return this.usersService.updateUser(this.form.getRawValue())
      })
    ).subscribe({
      next: () => {
        this._message.add({
          key: 'main',
          severity: 'success',
          summary: 'Usuário editado!',
          detail: 'Usuário editado com sucesso!',
          life: 3000,
        });
        this.closeModal()
      },
      error: (err) => {
        this._message.add({
          key: 'main',
          severity: 'error',
          summary: 'Erro ao editar usuário',
          detail: err.error.message,
          life: 3000,
        });
      }
    });
  }

  closeModal() {
    this.ref.close();
  }

  getDate() {
    let date = this.config.data?.user?.vacationReturnDate?.split(' ')[0];
    if(!date) return '';
    let dateReversed = date.split('-').reverse().join('/');
    return dateReversed
  }

  disableButton(): boolean {
    const admin = this.config.data.cdPerfil == ACESS_PROFILE.ADMINISTRADOR ? false : true;
    return admin;
  }
  
}
