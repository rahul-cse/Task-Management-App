import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators'
import { TaskType } from '../task-type/taskType';
import 'rxjs/add/operator/catch';  
import 'rxjs/add/observable/throw';

@Injectable({
  providedIn: 'root'
})
export class TaskTypeService {

  getAllUrl : string = "http://localhost:8080/api/task/list-taskType";
  getOneUrl : string = "http://localhost:8080/api/task/get-taskType/"
  postUrl : string = "http://localhost:8080/api/task/create-taskType";
  editUrl : string = "http://localhost:8080/api/task/edit-taskType/";
  //success : boolean = false;

  constructor(private _http: HttpClient) { }

  getAllTaskType():Observable<any>{
      return this._http.get(this.getAllUrl);
      //return this._http.get(this.getAllUrl).subscribe((data)=>{console.log(data),success = data["success"], taskTypeList = data["data"]});
  }



  saveTaskType(taskType:TaskType):Observable<any>{

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'my-auth-token'
      })
    };
    return this._http.post(this.postUrl,taskType,httpOptions)
          .catch(this.errorHandler);;
    
  }

  getOneTaskType(taskTypeId: number):Observable<any>{
        return this._http.get(this.getOneUrl+taskTypeId);
  }


  updateTaskType(taskType:TaskType):Observable<any>{
    return this._http.put(this.editUrl+taskType.id,taskType)
    .catch(this.errorHandler);
  }
  errorHandler(error: HttpErrorResponse): Observable<any> {
    return Observable.throw(error.message); 
  }
  

}
