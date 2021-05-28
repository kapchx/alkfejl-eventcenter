import { Participation } from "./paticipation";
import { User } from "./user";
import { Location } from "./location";

export interface Event {
    id: number;
    title: string;
    description: string;
    startAt: string;
    locations: Location[];
}


export enum Status {
    UNPUBLISHED, ACTIVE, CANCELLED
}