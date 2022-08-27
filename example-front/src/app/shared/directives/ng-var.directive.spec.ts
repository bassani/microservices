import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NgVarDirective } from './ng-var.directive';

@Component({
  template: `
    <div *ngVar="test.id as id">
      <span id="id">{{id}}</span>
      <span *ngVar="test.nested as nested" id="nestedWrapper">
        <span id="value1">{{nested.value}}</span>
        <span id="value2">{{nested.value2}}</span>
      </span>
    </div>
  `
})
class StubComponent {
  test = {id: 1, name: 'teste', nested: {value: 50, value2: 55}}
}

describe('NgVarDirective', () => {
  
  let component: StubComponent;
  let fixture: ComponentFixture<StubComponent>;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommonModule],
      declarations: [ StubComponent, NgVarDirective ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StubComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });



  it('should create an instance', () => {
    expect(component).toBeDefined()
  });
});
