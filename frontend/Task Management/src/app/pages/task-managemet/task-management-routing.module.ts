import { NgModule} from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { TaskManagementComponent} from "./task-management.component";
import { TaskTypeComponent } from "./task-type/task-type.component";
import { TasksComponent } from './tasks/tasks.component';


const routes: Routes = [
    {
        path: '',
        component: TaskManagementComponent,
        children: [
          {
            path: 'task-type',
            component: TaskTypeComponent,
          },
          {
            path: 'tasks',
            component: TasksComponent,
          }
          
        ],
      },
];

@NgModule({
    imports: [
      RouterModule.forChild(routes),
    ],
    exports: [
      RouterModule,
    ],
  })
  export class TaskManagemetRoutingModule {
  }