import { Injectable } from '@angular/core';
import { Event } from '../domain/event'


@Injectable({
  providedIn: 'root'
})
export class EventService {
  

  events: Event[] = []

  constructor() { }

  getEvents(): Event[] {
    return this.events
  }

  createEvent(event: Event): void {
    this.events.push(event);
  }

  editEvent(eventToEdit: Event, value: Event): void{
      this.events.splice(this.events.indexOf(eventToEdit), 1, value)
  }

  getEvent(eventId: number): Event {
    return this.events.find((event) => event.id === eventId) as Event;
  }
}
