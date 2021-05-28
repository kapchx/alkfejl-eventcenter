import { Component, OnInit } from '@angular/core';
import { Event } from '../domain/event'
import { EventService } from '../core/event.service';
import { MatDialog } from '@angular/material/dialog';
import { EventEditorComponent } from '../event-editor/event-editor.component';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss']
})
export class EventComponent implements OnInit {

  events!: Promise<Event[]>;

  constructor(
    private eventService: EventService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.getEvents();
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

}
