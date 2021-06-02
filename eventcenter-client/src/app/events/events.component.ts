import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from '../core/auth.service';
import { EventService } from '../core/event.service';
import { ParticipationService } from '../core/participation.service';
import { EventEditorComponent } from '../event-editor/event-editor.component';
import { Event } from '../domain/event'

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {

  events!: Promise<Event[]>;
  

  constructor(
    private eventService: EventService,
    private participationService: ParticipationService,
  ) { }

  ngOnInit(): void {
    this.getEvents();
    console.log(this.events)
  }

  private getEvents(): void {
    this.events = this.eventService.getGlobalEvents();
  }

  async ParticipateToEvent(event: Event): Promise<void>{
    await this.participationService.createParticipation(event);
    this.getEvents();
  }

}
