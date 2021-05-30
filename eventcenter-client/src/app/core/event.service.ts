import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Event } from '../domain/event'


@Injectable({
  providedIn: 'root'
})
export class EventService {
  

  events: Event[] = []

  constructor(private httpClient: HttpClient) { }

  async getEvents(): Promise<Event[]> {
    return await this.httpClient.get('/backend/events').toPromise() as Event[];
  }

  async createEvent(event: Event): Promise<Event> {
    return await this.httpClient.post('/backend/events', event).toPromise() as Event;
  }

  async editEvent(eventToEdit: Event, value: Event): Promise<Event>{
      return await this.httpClient.patch(`/backend/events/${eventToEdit.id}`, value).toPromise() as Event;
  }

  async getEvent(eventId: number): Promise<Event> {
    return await this.httpClient.get(`/backend/events/${eventId}`).toPromise() as Event;
  }

  async deleteEvent(eventToDelete: Event): Promise<void>{
    await this.httpClient.delete(`/backend/events/${eventToDelete.id}`).toPromise();
  }
}
