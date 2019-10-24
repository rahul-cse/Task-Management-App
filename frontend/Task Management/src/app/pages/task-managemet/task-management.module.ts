import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskTypeComponent } from './task-type/task-type.component';
import { TaskManagementComponent } from './task-management.component';
import { TaskManagemetRoutingModule } from './task-management-routing.module';
import { NbTreeGridModule, NbButtonModule, NbInputModule, NbCardModule, NbSelectModule, NbRadioModule, NbListModule, NbUserModule, NbCheckboxModule } from '@nebular/theme';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { FormsModule } from '@angular/forms';
import { TaskTypeService } from './services/task-type.service';
import { MatButtonModule, MatTableModule, MatPaginatorModule, MatCardModule, MatCheckboxModule, MatFormFieldModule, MatInputModule } from '@angular/material';
import { OwlDateTimeModule, OwlNativeDateTimeModule,DateTimeAdapter, OWL_DATE_TIME_FORMATS, OWL_DATE_TIME_LOCALE } from 'ng-pick-datetime';
import { TasksComponent } from './tasks/tasks.component';
import { TaskService } from './services/task.service';
import { MY_CUSTOM_FORMATS } from './myCustomFormat';


@NgModule({
  declarations: [TaskManagementComponent,TaskTypeComponent, TasksComponent],
  imports: [
    CommonModule,
    NbCardModule,
    NbSelectModule,
    NbTreeGridModule,
    NbRadioModule,
    Ng2SmartTableModule,
    NbButtonModule,
    NbInputModule,
    NbListModule,
    NbUserModule,
    TaskManagemetRoutingModule,
    FormsModule,
    MatButtonModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
    MatCheckboxModule

  ],
  providers: [TaskTypeService, TaskService]

})
export class TaskManagementModule { }
