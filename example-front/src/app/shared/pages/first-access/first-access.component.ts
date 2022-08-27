import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from '../../services';

type messageStatus = 'processing' | 'erroed' | 'success';

@Component({
  selector: 'app-first-access',
  templateUrl: './first-access.component.html',
  styleUrls: ['./first-access.component.scss']
})
export class FirstAccessComponent implements OnInit {
  messages = {
    processing: 'Cadastrando usuário e permissões, isso pode demorar alguns segundos',
    erroed: 'Não conseguimos adicionar as permissões para o seu usuario automaticamente, contate o suporte para adicionar as permissões manualmente.',
    success: 'Sucesso! você será redirecionado para a tela inicial.'
  }
  
  status = new BehaviorSubject<{status: messageStatus, token: string | null}>({status: 'processing', token: null})

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.setCredentials().subscribe(
      data => {
        this.status.next({status: 'success', token: data.token})
      },
      err => this.status.next({status: 'erroed', token: null})
    )
  }

}
