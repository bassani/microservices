import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'rd-choice',
  templateUrl: './choice.component.html',
  styleUrls: ['./choice.component.scss']
})
export class ChoiceComponent implements OnInit {

  @Input() title: string;
  @Input() icon: string = 'pi-user'
  @Input() content?: string;
  @Input() svg?: string;
  constructor(private ds: DomSanitizer) {
   }

  ngOnInit(): void {
  }

  get svgIcon(): any {
    if(!this.svg) return '';
    return this.ds.bypassSecurityTrustHtml(this.svg)
  }

}
