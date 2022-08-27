import { Directive, ElementRef, Input, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import { AuthService } from '../services';

/**
 * Directive criado para desabilitar elementos basado nos roles informados e a presença deles no 
 * usuario do keycloak
 * @example <button withRoles roles=['CREATE_PRODUCT']>criar produto</button>
 * <button withRoles roles=['CREATE_PRODUCT', 'UPDATE_PRODUCT']>atualizar produto</button>
 */
@Directive({
  selector: '[withRoles]'
})
export class WithRolesDirective implements OnInit{
  @Input()
  roles: string[] = [];
  constructor(private el: ElementRef, private auth: AuthService) {
    this.updateView()
  }


  ngOnInit() {
    this.updateView()
  }

  updateView() {
    let showElement = this.roles.every(role => {
      return this.auth.roles.includes(role.toLowerCase())
    })
    if(!showElement){
      console.log(this.roles, this.auth.roles, 'rooole')
      this.el.nativeElement.disabled = true
      this.el.nativeElement.classList.add('disabled');

    }

    
  }
}
