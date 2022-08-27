import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { AuthService } from 'src/app/shared/services';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  form: FormGroup;

  constructor(
    private _fb: FormBuilder,
    private _auth: AuthService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.form = this._fb.group({
      email: [
        null,
        Validators.compose([Validators.required, Validators.email]),
      ],
      password: [null, Validators.required],
    });
  }

  login() {
    if (this.form.invalid) {
      this.messageService.add({
        severity: 'error',
        summary: 'Credenciais inválidas',
        detail: 'Verifique as suas credenciais e tente novamente',
        key: 'main',
      });
      return;
    }
    this._auth.login(this.form.getRawValue());
    return;
  }
}
