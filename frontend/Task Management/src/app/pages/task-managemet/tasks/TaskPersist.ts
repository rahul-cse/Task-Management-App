export class TaskPersist{
    id : number;
    title : string;
    taskTypeId: number;
    taskTypeName:string;
    areaId: number  = 6113;
    assignedType: string;
    assignedTo: number;
    assignedUserName: string;
    assignedToDept: number;
    assignedDeptName:string;
    startDate: string;
    endDate: string;
    description: string;
    entryBy: number;
    repeat: boolean;
}