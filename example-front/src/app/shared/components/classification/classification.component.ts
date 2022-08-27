import { ISimulationClassification } from './../../models/simulation-classification.model';
import {
  ChangeDetectionStrategy,
  Component,
  Input,
  OnInit,
} from '@angular/core';
import { FormControl } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';
import {
  ISearchResponse,
  ISearchResultItem,
} from '../../models/search-simulador';
import { ClassificationService } from '../../services';
import { FieldUtilsService } from '../../utils/field-utils.service';

@Component({
  selector: 'app-classification',
  templateUrl: './classification.component.html',
  styleUrls: ['./classification.component.scss'],
})
export class ClassificationComponent implements OnInit {
  classifications$ = new BehaviorSubject<
    ISearchResponse<ISimulationClassification>
  >({ content: [], page: 0, size: 999, numberOfElements: 0 });

  @Input('control') control = new FormControl();

  constructor(private classificationService: ClassificationService, public fieldUtils: FieldUtilsService) {}

  ngOnInit(): void {
    this.getClassifications({query: ''});
  }

  getClassifications(q: any) {
    this.classificationService
      .getAllClassifications(q.query)
      .subscribe((data) => this.classifications$.next(data));
  }
}
