import { Component, CUSTOM_ELEMENTS_SCHEMA, ViewChild } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

import { ChoiceComponent } from './choice.component';


@Component({selector: 'stub', template: `<rd-choice
    #choice
    [title]="title"
    [icon]="icon"
    [content]="content"
></rd-choice>`})
class Stub {
  @ViewChild('choice') choice: ChoiceComponent;
  title = '';
  icon = '';
  content = '';
}

describe('ChoiceComponent', () => {
  let component: ChoiceComponent;
  let fixture: ComponentFixture<ChoiceComponent>;
  let stub: Stub;
  let stubFixture: ComponentFixture<Stub>;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChoiceComponent, Stub],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(() => {

    stubFixture = TestBed.createComponent(Stub);
    stub = stubFixture.componentInstance;
    stubFixture.detectChanges();

    fixture = TestBed.createComponent(ChoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(component.icon).toEqual('pi-user')
  });


  describe('SVG icon', () => {
    it('Deve retornar um svg sanitizado', () => {
      component.svg = '<svg></svg>';
      let SafeHtmlImpl = component['ds'].bypassSecurityTrustHtml(component.svg);
      expect(component.svgIcon).toEqual(SafeHtmlImpl)
    })
    it('Deve retornar um string vazio caso não exista um svg', () => {
      component.svg = undefined;
      expect(component.svgIcon).toEqual('')
    })
  })

  describe('Inputs', () => {
    it('Deve atualizar o conteudo do componente de acordo aos inputs do parent', () => {
      const mock = {
        icon: 'pi-teste',
        title: 'title teste',
        content: 'content test'
      }


      const baseIcon = `pi pi-fw ${mock.icon}`;
      expect(stubFixture.debugElement.nativeElement.querySelector('#choice-title').textContent).toEqual('');
      expect(stubFixture.debugElement.nativeElement.querySelector('#choice-content').textContent).toEqual('');
      stub.icon = mock.icon;
      stub.title = mock.title;
      stub.content = mock.content
      stubFixture.detectChanges();
      fixture.detectChanges();
      expect(stubFixture.debugElement.nativeElement.querySelector('#choice-title').textContent).toEqual(mock.title);
      expect(stubFixture.debugElement.nativeElement.querySelector('#choice-content').textContent).toEqual(mock.content);
    })
  })


});
