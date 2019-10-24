import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { TasksView } from './TasksView';
import { MatTableDataSource } from '@angular/material/table';
import { Tasks } from './Tasks';
import { MatPaginator } from '@angular/material/paginator';
import { TaskTypeService } from '../services/task-type.service';
import { TaskService } from '../services/task.service';
import { NgForm } from '@angular/forms';
import { TaskType } from '../task-type/taskType';
import { User } from '../User';
import { Department } from '../Department';
import { NbRadioComponent } from '@nebular/theme';
import { UserView } from '../UserView';

@Component({
  selector: 'ngx-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements OnInit , AfterViewInit{

  createClicked: boolean;
  viewClicked: boolean;
  editClicked: boolean;
  saveStatus: boolean;
  formSubmit: boolean;
  teamRadioChecked: boolean;
  isloading:boolean;
  validationStatus:boolean;

  clickType : string;


  todayDate:Date;

  formHeadline: String = "";
  @ViewChild('noTask',{static:false}) noTaskFound: ElementRef;
  @ViewChild('teamRadio',{static:false}) teamRadio: NbRadioComponent;
  @ViewChild('f',{static:false}) formV: NgForm;
  //@ViewChild('isloading',{static:false}) isloading: ElementRef;

  constructor(private _taskService: TaskService,private _taskTypeService : TaskTypeService) { 
  /*   this.createClicked = false;
    this.viewClicked = true;
    this.editClicked = false; */
    this.clickType = "view";
    this.saveStatus = false;
    this.formSubmit = false;
    this.teamRadioChecked = false;
    this.isloading = false;
    this.validationStatus = true;
  }


  // data 
  taskTypeList : TaskType[];
  tasksViewList : TasksView[];
  userList: User[];
  userViewList= [] ;
  deptList: Department[];
  task:Tasks;
  taskType: TaskType;
  dept:Department;
  add_editMsg : String = "";
  prevStartDate : Date;
  startDate: Date;
  task_name: string = "";

  dataSource : MatTableDataSource<TasksView>;
  
  displayedColumns: string[] = ["taskId","title","description","assignedUser","assignedDept","status","action"];
  @ViewChild(MatPaginator,{static: false}) paginator: MatPaginator;

  filterValue(filterValue: string){
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ngOnInit() {
    this._taskService.listAllTask().subscribe((data)=>{this.tasksViewList = data["data"],this.dataSource = new MatTableDataSource<TasksView>(this.tasksViewList),console.log(this.tasksViewList),this.dataSource.paginator = this.paginator},
    (error)=> {this.tasksViewList = null,this.noTaskFound.nativeElement.style.visibility = 'visible';});
    this._taskService.getUserInfo().subscribe((data)=>{this.userList = data["data"],console.log(data)});
    this._taskService.getDeptInfo().subscribe((data)=>this.deptList = data["data"]);
  }

  isLoadingFunc(){
    //this.isloading.nativeElement.style.visibility='visible';
    this.isloading = true;
    this.userList = null;
    setTimeout(()=>this.createClickedFunc(),2500);
  }

  createClickedFunc(){
    this.task = new Tasks();
    this.task.assignedType = "solo";
    this.task.repeat = false;
    this.task.entryBy = 101;
    this._taskTypeService.getAllTaskType().subscribe((data)=>{this.taskTypeList = data["data"],   this.isloading = false;});
    //this._taskService.getDeptInfo().subscribe((data)=>this.deptList = data["data"]);
    this._taskService.getUserInfo().subscribe((data)=>{this.userList = data["data"],console.log(data)});
    /* this.createClicked = true;
    this.viewClicked = false;
    this.editClicked = false; */
    this.task_name = "";
    this.clickType = "create";
    this.formSubmit = false;
    this.saveStatus = false;
    this.validationStatus = true;
    this.todayDate = new Date();
    this.todayDate.setMinutes(this.todayDate.getMinutes()+2);
    this.formHeadline = "Create New Task";
  }

  viewClickedFunc(){
    this.clickType = "view";
    this._taskService.listAllTask().subscribe((data)=>{this.tasksViewList = data["data"],this.dataSource = new MatTableDataSource<TasksView>(this.tasksViewList),console.log(this.tasksViewList),this.dataSource.paginator = this.paginator},
    (error)=> {this.tasksViewList = null,this.noTaskFound.nativeElement.style.visibility = 'visible';});
    this.task = null;
    this.validationStatus = true;
  }

  submitTaskType(form:NgForm){
    console.log(form);
    this.formSubmit = false;
    console.log(this.task);
    if(this.task.assignedTo == null || this.task.assignedToDept == null){
      console.log("validation error!");
      event.preventDefault();
    }
 
/*     let strDate :string = ""; 
    let month =  this.formatDateTime(this.task.startDate.getMonth()+1);
    let date = this.formatDateTime(this.task.startDate.getDate());
    let hour = this.formatDateTime(this.task.startDate.getHours());
    let minute = this.formatDateTime(this.task.startDate.getMinutes());
    let second = this.formatDateTime(this.task.startDate.getSeconds());
    strDate = this.task.startDate.getFullYear()+"-"+month+"-"+date+"T"+hour+":" + minute + ":" + second; */
    //console.log(strDate)
    //this.task.startDate = new Date(strDate);
    console.log(this.task.startDate);
    if(this.task.id == null){
        this._taskService.createTask(this.task).subscribe((data)=>{console.log(data["data"]),this.saveStatus=data["success"],this.formSubmit=true,this.task_name=this.task.title,
                                                          setTimeout(()=>this.viewClickedFunc(),3500)},
                                                          (error)=>{console.log(error),this.saveStatus = false;this.formSubmit=true});
        this.add_editMsg = "added new";
    }
    else{
      this.update();
    }
    
  }

  editTask(taskId:string){
    this._taskService.getOneTask(taskId).subscribe((data)=>{console.log(data), this.task = data["data"],this.prevStartDate=this.task.startDate,
                                                            this.getUserName(this.task.assignedTo),this.getDeptName(this.task.assignedToDept),
                                                            this._taskTypeService.getOneTaskType(this.task.taskTypeId).subscribe((data)=>{this.taskType = data["data"], this.task.taskTypeName = this.taskType.name})
                                                            this.clickType = "create";});
    /* this._taskService.getSingleDept(this.task.assignedToDept).subscribe((data)=>{this.dept=data["data"],console.log(this.task)},
                                                                      (error)=>{console.log(error),console.log(this.task)});  */

    this.formSubmit = false;
    this.saveStatus = false;
    console.log(this.task);
    this.formHeadline = "Edit Task";
  }


  update(){
    console.log(this.task);
    if(this.prevStartDate!=null&&this.task.startDate==null){
      console.log("validation error!");
      this.validationStatus = false;
      event.preventDefault();
    }
    else{
      if(this.prevStartDate == this.task.startDate){
        this.task.startDate=null;
      }
      this._taskService.updateOneTask(this.task).subscribe((data)=>{console.log(data),this.saveStatus=data["success"],this.formSubmit=true,this.task_name=this.task.title,setTimeout(()=>this.viewClickedFunc(),3500)},
                                                          (error)=>{console.log(error),this.saveStatus = false});
      this.add_editMsg = "edited";
    }

  }


  viewTaskDetails(taskId:string){
    this._taskService.getOneTask(taskId).subscribe((data)=>{console.log(data), this.task = data["data"]});
    this.clickType = "view-task-details";
  }

  isRepeat(){
    console.log(this.task.repeat);
    if(this.task.repeat==false)
      this.task.repeat = true;
    else
      this.task.repeat=false;
    console.log(this.task.repeat);
  }


   radioChangeAssign(val:string){
    console.log(val);
    console.log(this.task.assignedType);
    console.log(this.teamRadio);
     if(this.teamRadio.checked){
      this.task.assignedType = val;
      console.log("val:"+this.task.assignedType);
    } 
    //this.task.assignedType = val;
    //console.log("val:"+this.task.assignedType);

  } 

  getChoosenUserId(e){
    let userName = (e.target.value);
    let userId = this.userList.filter(u=>u.userName === userName)[0].id;
    this.task.assignedTo = userId;
    this.task.assignedUserName = userName;
    console.log(this.task.assignedTo);
    console.log(this.task.assignedType);
  }

  getUserListView(e){
    this.userViewList = [];
    let value = e.target.value;
    console.log(value);
    if(value.length>3){
      //this.userList.filter(u=>(u.userName.substr(0,value.length).toLowerCase === value)).forEach(uv=>this.userViewList.push(uv));
      this.userList.forEach(u=>{if(u.userName!=null){ let user = u.userName.substr(0,value.length).toLowerCase();
                                      if(user == value.toLowerCase())
                                                      {
                                                        console.log(u);
                                                        this.userViewList.push(u);
                                                      }
                                                    }
                                                  });
    }
    console.log(this.userViewList);

  }

  getChoosenDept(e){
    let deptName = (e.target.value);
    let deptId = this.deptList.filter(d=>d.name === deptName)[0].id;
    this.task.assignedToDept = deptId;
    this.task.assignedDeptName = deptName;
    console.log(this.task.assignedToDept);
    console.log(this.task.assignedType);
    this.task.assignedType = "team";
      console.log("val:"+this.task.assignedType);
  }
  

  // After Page Load
  ngAfterViewInit(): void {
    //this.noTaskFound.nativeElement.style.visibility = 'visible';
    console.log(this.noTaskFound);
  }


  formatDateTime(timeDate:number):string{
    if(timeDate<10){
      var formattedString = '0'+timeDate;
      return formattedString; 
    }
    else
      return timeDate.toString();
  }

  getUserName(userId:number){
    if(userId!=null){
      let userName = this.userList.filter(u=>u.id===userId)[0].userName;
      console.log(userName);
      this.task.assignedUserName = userName;
    }
    else
      this.task.assignedUserName = "";

  }

  getDeptName(deptId:number){
    if(deptId!=null){
      let deptName = this.deptList.filter(d=>d.id===deptId)[0].name;
      console.log(deptName);
      this.task.assignedDeptName = deptName;
    }  
    else
      this.task.assignedDeptName="";
  }


}
