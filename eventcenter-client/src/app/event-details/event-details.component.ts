import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EventService } from '../core/event.service';
import { Event } from '../domain/event'

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.scss']
})
export class EventDetailsComponent implements OnInit {

  event?: Event;

  constructor( 
    private eventService: EventService,
    private route: ActivatedRoute
  ) {
    
  }

  async ngOnInit(): Promise<void> {
    const eventId = parseInt(this.route.snapshot.paramMap.get('eventId') as string);
    this.event = await this.eventService.getEvent(eventId);
  }

}
