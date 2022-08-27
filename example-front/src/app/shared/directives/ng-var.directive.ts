import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';

/**
 * Directive utilizado para criar um contexto local. Simplificando o acesso a objetos complexos sem
 * necesidade de criar getters
 * @example 
 * <div *ngVar="(service.subject | async) as sub">{{sub.name}}</div>
 * <div *ngVar="service.someObject.someArray as arr"><span *ngFor="item of arr">{{item.name}}</span></div>
 */
@Directive({
  selector: '[ngVar]'
})
export class NgVarDirective {
  @Input()
  set ngVar(context: any) {
    this.context.$implicit = this.context.ngVar = context;
    this.updateView()
  }
  context: any = {};
  constructor(private vcRef: ViewContainerRef, private templateRef: TemplateRef<any>) { }


  updateView() {
    this.vcRef.clear();
    this.vcRef.createEmbeddedView(this.templateRef, this.context)
  }
}
