import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { IDistributionCenter } from '../../models/search-simulador';
import { StatusService } from '../../services/status/status.service';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.scss']
})
export class StatusComponent implements OnInit {
  status$ = new BehaviorSubject<{id: number, name: string}[]>([]);
  @Input('control') control = new FormControl();

  constructor(private status: StatusService) { }

  ngOnInit(): void {
    this.search();
  }

  search(): void {
    this.status
      .search()
      .subscribe(
        (data) => {
          this.status$.next(data);
        },
        (err) => this.status$.next([])
      );
  }

}
