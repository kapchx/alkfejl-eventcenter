import { Approval, Participation } from "./paticipation";

export interface User {
    id: number;
    name: string;
    username: string;
    password: string;
    role: UserRole;
   // participation: Participation[];
  //  events: Event[];
}

export enum UserRole {
    ROLE_GUEST, ROLE_USER, ROLE_ADMIN
}