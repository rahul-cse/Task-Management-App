import { Component, OnInit, ViewChild } from '@angular/core';
import { NbComponentShape, NbComponentSize, NbComponentStatus } from '@nebular/theme';
import { NgForm } from '@angular/forms';
import { TaskType } from './taskType';
import { TaskTypeService } from '../services/task-type.service';
import { LocalDataSource } from 'ng2-smart-table';
import { MatTableDataSource, MatPaginator } from '@angular/material';
import { id } from '@swimlane/ngx-charts/release/utils';

@Component({
  selector: 'ngx-task-type',
  templateUrl: './task-type.component.html',
  styleUrls: ['./task-type.component.scss']
})
export class TaskTypeComponent implements OnInit {

  createClicked: boolean;
  viewClicked: boolean;
  editClicked: boolean;
  saveStatus: boolean;
  formSubmit: boolean;

  // identifying submit event
  count:number = 0;



/*   settings ={

    actions: true,
    
    columns: {
      index:{
        title: 'SL#',
        type: 'text',
        valuePrepareFunction: (value,row,cell) => {
          return cell.row.index;
         },
         filter: false,
        },
      tasktypename: {
        title: 'Task Type Name',
        type: 'string',
        filter: false,
      },
      areaName: {
        title: 'Area Name',
        type: 'string',
        filter: false,
      },
      description: {
        title: 'Description',
        type: 'string',
        filter: false,
      },
      
    }
  } */

  dataSource : MatTableDataSource<TaskType>;
  
  displayedColumns: string[] = ["id","name","description","action"];
  @ViewChild(MatPaginator,{static: false}) paginator: MatPaginator;

  
  // data //
  taskType = new TaskType();
  taskTypeList: TaskType[];
  formHeadline: String = "";
  add_editMsg : String = "";

  //source: LocalDataSource;
  constructor(private _taskTypeService : TaskTypeService) { 
    this.createClicked = false;
    this.viewClicked = true;
    this.editClicked = false;
    this.saveStatus = false;
    this.formSubmit = false;
  /*   setTimeout(() => {
      this.dataSource.paginator = this.paginator;
    }, 0); */
  }

  ngOnInit() {
    this._taskTypeService.getAllTaskType().subscribe((data)=>{console.log(data),this.taskTypeList = data["data"],this.dataSource = new MatTableDataSource<TaskType>(this.taskTypeList),this.dataSource.paginator = this.paginator;});
  
  }
  
  
  createClickedFunc(){
    this.taskType = new TaskType();
    this.createClicked = true;
    this.viewClicked = false;
    this.editClicked = false;
    this.formSubmit = false;
    this.saveStatus = false;
    this.formHeadline = "Create New Task Type";
  }

  viewClickedFunc(){
    this.createClicked =false;
    this.editClicked = false;
    //this.taskType = null;
    this.findAll();
  }

  editClickedFunc(){
    this.editClicked = true;
    this.createClicked = false;
    this.viewClicked = false;
  }



  // saving data * * * * * * * *

  //@ViewChild('f')  taskTypeForm : NgForm;
  submitTaskType(form:NgForm,taskType:TaskType){
    console.log(form);
    console.log(this.taskType);
    this.formSubmit = false;
    if(this.taskType.id==null){
      this._taskTypeService.saveTaskType(this.taskType).subscribe((data)=>{this.saveStatus=data["success"],this.formSubmit = true,console.log(this.saveStatus)
                                                                  ,console.log(data),this.viewClickedFunc();},
                                                                  (error)=>{this.saveStatus=false,this.formSubmit = true,console.log(error)});
      this.add_editMsg = "added new";
    }  
    else
      this.update();
  }

  findAll(){
    this.taskTypeList=null;
    this.dataSource = null;
    this._taskTypeService.getAllTaskType().subscribe((data)=>{console.log(data),this.taskTypeList = data["data"],this.dataSource = new MatTableDataSource<TaskType>(this.taskTypeList),this.dataSource.paginator = this.paginator,
    setTimeout(()=>this.viewClicked = true,3500); });
    //setTimeout(()=>this.viewClickedFunc(),3500);
    //this.saveStatus = false;
    
   /*  for(let i = 0 ; i < this.taskTypeList.length ; i++){
      
    var obj = {
      name:'<a href="'+this.taskTypeList[i].id+'">Link</a>',          
      
    } */
    //this.sourceData.add(this.taskTypeList[i],obj);
    //this.sourceData.concat(this.taskTypeList[i]);        
  }      
  
    //this._taskTypeService.getAllTaskType(success,taskTypeList);
    //console.log(taskTypeList);
    //return success;
  


   editTaskType(taskTypeId : number){
     console.log("edit"+taskTypeId);
     this._taskTypeService.getOneTaskType(taskTypeId).subscribe((data)=>{this.taskType = data["data"],console.log(this.taskType)});
     this.createClickedFunc();
     this.formHeadline = "Edit Task Type";
     this.saveStatus = false;
   }


   update(){
      this.formSubmit = false;
      this._taskTypeService.updateTaskType(this.taskType).subscribe((data)=>{console.log(data),this.saveStatus=data["success"],this.formSubmit = true,this.viewClickedFunc();},
                                                                    (error)=>{this.saveStatus=false,this.formSubmit = true,console.log(error)});
      console.log("updating....")
      console.log(this.taskType);
      this.add_editMsg = "edited";
   }

  


}
