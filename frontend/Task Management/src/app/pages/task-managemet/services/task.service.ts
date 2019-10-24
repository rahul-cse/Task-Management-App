import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Tasks } from '../tasks/Tasks';
import { Observable } from 'rxjs';
import { TaskPersist } from '../tasks/TaskPersist';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  getAllTaskUrl : string = "http://localhost:8080/api/task/list-all-tasks";
  getOneTaskByIdUrl : string = "http://localhost:8080/api/task/get-task/"
  createTaskUrl : string = "http://localhost:8080/api/task/create-task";
  editUrl : string = "http://localhost:8080/api/task/update-task/";
  userInfoUrl: string ="http://localhost:8080/api/task/getUserInfo/";
  deptInfoUrl: string = "http://localhost:8080/api/task/getDeptInfo/";
  singleDeptInfoUrl: string = "http://localhost:8080/api/task/getDept/"



  taskPersist: TaskPersist;


  uuId:string = "cc9d847d-3d62-40c9-8006-a3f5021f1970";

  constructor(private _http: HttpClient) { }

  createTask(task: Tasks):Observable<any>{
    
    let c = JSON.stringify(task);
    this.taskPersist = JSON.parse(c);
    this.taskPersist.startDate = this.getStringDateTime(task.startDate);
    this.taskPersist.endDate = this.getStringDateTime(task.endDate);
    console.log(this.taskPersist);
    return this._http.post(this.createTaskUrl,this.taskPersist);
  }

  listAllTask():Observable<any>{
    return this._http.get(this.getAllTaskUrl)
    .catch(this.errorHandler);
  }

  getOneTask(taskId:string):Observable<any>{
    //let params = new HttpParams().set('uuId',this.uuId).set('task_id',taskId);
    //return this._http.get(this.getOneTaskByIdUrl,{params: params})
    return this._http.get(this.getOneTaskByIdUrl+this.uuId+"/"+taskId)
    .catch(this.errorHandler);
  }

  updateOneTask(task:Tasks):Observable<any>{
    console.log("in service");
    let c = JSON.stringify(task);
    this.taskPersist = JSON.parse(c);
    this.taskPersist.startDate = this.getStringDateTime(task.startDate);
    this.taskPersist.endDate = this.getStringDateTime(task.endDate);
    return this._http.put(this.editUrl+this.uuId+"/"+task.id,task)
    .catch(this.errorHandler);
  }

  getUserInfo():Observable<any>{
    return this._http.get(this.userInfoUrl)
    .catch(this.errorHandler);
  }

  getDeptInfo():Observable<any>{
    return this._http.get(this.deptInfoUrl)
    .catch(this.errorHandler);
  }

  getSingleDept(depId:number):Observable<any>{
    return this._http.get(this.singleDeptInfoUrl+depId)
    .catch(this.errorHandler);
  }



  
  errorHandler(error: HttpErrorResponse): Observable<any> {
    return Observable.throw(error.message);
  }






  formatDateTime(timeDate:number):string{
    if(timeDate<10){
      var formattedString = '0'+timeDate;
      return formattedString; 
    }
    else
      return timeDate.toString();
  }

  getStringDateTime(datetime:Date):string{
    if(datetime!=null){
      let strDate :string = ""; 
      let month =  this.formatDateTime(datetime.getMonth()+1);
      let date = this.formatDateTime(datetime.getDate());
      let hour = this.formatDateTime(datetime.getHours());
      let minute = this.formatDateTime(datetime.getMinutes());
      let second = this.formatDateTime(datetime.getSeconds());
      strDate = datetime.getFullYear()+"-"+month+"-"+date+"T"+hour+":" + minute + ":" + second;
      return strDate;
    }
    else
      return null;
    
  }



}
