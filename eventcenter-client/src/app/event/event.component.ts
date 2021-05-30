import { Component, OnInit } from '@angular/core';
import { Event } from '../domain/event'
import { EventService } from '../core/event.service';
import { MatDialog } from '@angular/material/dialog';
import { EventEditorComponent } from '../event-editor/event-editor.component';
import { User } from '../domain/user';
import { Participation } from '../domain/paticipation';
import { Approval } from '../domain/paticipation';
import { ParticipationService } from '../core/participation.service';
import { AuthService } from '../core/auth.service';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss']
})
export class EventComponent implements OnInit {

  events!: Promise<Event[]>;
  user!: User; 
  

  constructor(
    private eventService: EventService,
    private authService: AuthService,
    private participationService: ParticipationService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.getEvents();
    this.getCurrentUser();
  }

  async startCreateEvent(): Promise<void>{
    const dialogRef = this.dialog.open(EventEditorComponent,{
      height: '400px'
    })
    await dialogRef.afterClosed().toPromise();
    this.getEvents();
  }

  
  async startEditEvent(event: Event): Promise<void>{
    const dialogRef = this.dialog.open(EventEditorComponent,{
      height: '400px',
      data: event,
    });
    await dialogRef.afterClosed().toPromise();
    this.getEvents();
  }

  private getEvents(): void {
    this.events = this.eventService.getEvents();
  }

  private getCurrentUser(): void {
    this.user = this.authService.user;
  }

  async deleteEvent(event: Event): Promise<void> {
    await this.eventService.deleteEvent(event);
    this.getEvents();
  }

  async ParticipateToEvent(user: User, event: Event): Promise<void>{
    let newparticipation = {user: user, event: event, id: 1, approval: Approval.APPLIED};
    await this.participationService.createParticipation(newparticipation);

  }

}
