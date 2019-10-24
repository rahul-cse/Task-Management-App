export class Tasks{
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
    startDate: Date;
    endDate: Date;
    description: string;
    entryBy: number;
    repeat:boolean;
}