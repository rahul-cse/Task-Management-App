import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'ngx-task-management',
  template: `
  <router-outlet></router-outlet>
`,
  styleUrls: ['./task-management.component.scss']
})
export class TaskManagementComponent implements OnInit {


  constructor() { }

  ngOnInit() {
  }

}
