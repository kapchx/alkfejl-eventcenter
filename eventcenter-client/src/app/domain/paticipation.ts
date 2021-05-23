import { User } from "./user";

export interface Participation{
    id: number;
    approval: Approval;
    createdAt: string;
    event: Event;
    user: User;
}

export enum Approval{
    APPLIED, ACCEPTED, REJECTED
}