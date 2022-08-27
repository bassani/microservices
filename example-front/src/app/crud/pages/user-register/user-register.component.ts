import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ClassificationService } from 'src/app/shared/services';
import { DialogService } from 'primeng/dynamicdialog';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { IClassification } from 'src/app/shared/models/classification.model';
import { EditClassificationDialog } from '../../dialogs/edit-classification/edit-classification.dialog';
import { ConfirmModalComponent } from 'src/app/shared/components/confirm-modal/confirm-modal.component';
import { EditUserComponent } from '../../dialogs/edit-user/edit-user.component';
import { UsersService } from 'src/app/shared/services/users/users.service';
import { AREA_CODE } from 'src/app/shared/consts/area-code';
import { ACESS_PROFILE } from 'src/app/shared/consts/acess-profile';
import { IUser } from 'src/app/shared/models/user.model';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.scss']
})
export class UserRegisterComponent implements OnInit {

  form: FormGroup;
  /** ReactiveFormControl do input de pesquisa */
  searchControl = new FormControl('');
  page = 0;
  cdPerfil: string;

  areaMap: Map<Number | null, string> = new Map<Number | null, string>([
    [ AREA_CODE.ABASTECIMENTO, "Abastecimento" ],
    [ AREA_CODE.COMERCIAL, "Comercial" ],
    [ null, "-" ],
  ]);

  acessProfileMap: Map<Number | null, string> = new Map<Number | null, string>([
    [ ACESS_PROFILE.ADMINISTRADOR, "Administrador" ],
    [ ACESS_PROFILE.APROVADOR, "Aprovador" ],
    [ ACESS_PROFILE.GESTOR, "Gestor" ],
    [ null, "-" ],
  ]);

  constructor(
    private _service: UsersService,
    private _message: MessageService,
    private _dialog: DialogService,
    private _fb: FormBuilder,
    protected readonly keycloak: KeycloakService

  ) {
    this.getAll();
  }

  async ngOnInit(): Promise<void> {
   await this.getPerfilCode();
  }

  paginate($event: any) {
    const { page, rows } = $event;
    this.page = page;
    this.changePage(page, rows);
  }

  changePage(page:number, size:number): void {
    this._service.getAll(this.searchControl.value, this.page, size);
  }

  getAll(): void {
    this.changePage(this.page, 10);
  }

  get users$(): any {
    return this._service.users$;
  }

  filter() {
    this.page = 0;
    this.changePage(this.page, 10);
  }

  clearSearch(){
    this.searchControl.setValue('');
    this.filter();
  }

  editOrAddUser(user: IUser | null): void {
    const message = user ? "Editar Usuário" : "Cadastrar Usuário"
    this._dialog.open(EditUserComponent, {
      data: {user: user, cdPerfil: this.cdPerfil},
      header: message
    });
  }

  async getPerfilCode() {
    const user = await this.keycloak.getKeycloakInstance().loadUserInfo();
    type ObjectKeyArea = keyof typeof user;
    const cd_perfil = 'CD_PERFIL' as ObjectKeyArea;
    this.cdPerfil = user[cd_perfil];
  }
}
