import { Injectable } from '@angular/core';
import { Event } from './domain/event'


@Injectable({
  providedIn: 'root'
})
export class EventService {

  events: Event[] = [{
    title: 'Deszkazas',
    description: 'Kell hozza deszka',
    location: 'Itt'
  }, {
    title: 'Biciklizes',
    description: 'Meglepo, de ehez pedig bicikli kell',
    location: 'Ott'
  }]

  constructor() { }

  getEvents(): Event[] {
    return this.events
  }

  createEvent(event: Event): void {
    this.events.push(event);
  }
}
