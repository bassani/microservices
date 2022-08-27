import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MultiSelect } from 'primeng/multiselect';
import { BehaviorSubject, from, Subscription } from 'rxjs';
import { debounceTime, switchMap, tap } from 'rxjs/operators';
import { UsersService } from '../../services/users/users.service';

@Component({
  selector: 'app-users-select',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements AfterViewInit {
  users: any = []
  filter: string = '';
  users$ = new BehaviorSubject<{content: any[], status: 'READY' | 'LOAD'}>({content: [], status: 'READY'})
  @Input('control') control: FormControl = new FormControl('')
  @ViewChild('multiselect') multiselect: MultiSelect;
  subscription: Subscription;
  constructor(public userService: UsersService) { }

  ngAfterViewInit(): void {
    this.subscription = this.multiselect.onFilter.pipe(
      debounceTime(400),
      switchMap((val: any) => {
        this.filter = val.filter;
        this.users$.next({content: [], status: 'LOAD'})
        return this.userService.findUsers(val.filter);
      }),
      tap(data => {
        this.users$.next({content: data, status: 'READY'});
      })
    ).subscribe(data => {
      this.multiselect.options = data;
      this.multiselect.options.sort((a, b) => {
        let fa = a.name.toLowerCase(),
        fb = b.name.toLowerCase();
        if (fa < fb) {
          return -1;
        }
        if (fa > fb) {
            return 1;
        }
        return 0;
      });
      this.multiselect.activateFilter()
    });


  }


}
