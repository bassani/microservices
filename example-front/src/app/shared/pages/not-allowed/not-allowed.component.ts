import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-not-allowed',
  templateUrl: './not-allowed.component.html',
  styleUrls: ['./not-allowed.component.scss']
})
export class NotAllowedComponent implements OnInit {

  constructor(private _router: Router) { }

  ngOnInit(): void {
  }

  back() {
    this._router.navigateByUrl('/')
  }

}
