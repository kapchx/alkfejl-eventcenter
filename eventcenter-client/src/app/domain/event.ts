import { Participation } from "./paticipation";
import { User } from "./user";

export interface Event {
    id: number;
    title: string;
    description: string;
    private: Status;
    createdAt: string;
    startAt: string;
    user: User;
    paricipation: Participation[];
    location: Location[];
}


export enum Status {
    UNPUBLISHED, ACTIVE, CANCELLED
}