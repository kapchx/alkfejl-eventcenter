import { User } from "./user";
import { Event } from "./event";

export interface Participation{
    id: number;
    username: string;
    eventname: string;
    approval: Approval;
    event: Event;
    user: User;
}

export enum Approval{
    APPLIED, ACCEPTED, REJECTED
}