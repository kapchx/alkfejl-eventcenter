import { Component, OnInit } from '@angular/core';
import { Event } from '../domain/event'
import { EventService } from '../event.service';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss']
})
export class EventComponent implements OnInit {

  events!: Event[];

  constructor(
    private eventService: EventService
  ) { }

  ngOnInit(): void {
    this.events = this.eventService.getEvents();
  }

}
