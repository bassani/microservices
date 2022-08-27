import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { BehaviorSubject, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import {
  IDistributionCenter,
  ISearchResponse,
} from '../../models/search-simulador';
import { CdService } from '../../services/cd/cd.service';
import { FieldUtilsService } from '../../utils/field-utils.service';
import { PAGED_BASE } from '../../utils/mock';

@Component({
  selector: 'app-cd-select',
  templateUrl: './cd-select.component.html',
  styleUrls: ['./cd-select.component.scss'],
})
export class CdSelectComponent implements OnInit {
  suggestions$ = new BehaviorSubject<IDistributionCenter[]>([]);
  @Input() control: FormControl = new FormControl();
  constructor(private cdService: CdService, public fieldUtils: FieldUtilsService) {}

  ngOnInit(): void {
    this.search();
  }

  search(): void {
    this.cdService
      .search()
      .pipe(
        map((data) => {
          return data.content;
        }),
        catchError(err => of([]))
      )
      .subscribe(
        (data) => {
          this.suggestions$.next(data);
        }
      );
  }
}
