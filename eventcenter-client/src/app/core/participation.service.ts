import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Approval, Participation } from '../domain/paticipation';
import { Event } from '../domain/event'

@Injectable({
  providedIn: 'root'
})
export class ParticipationService {

 
  participations: Participation[] = []

  constructor(private httpClient: HttpClient) { }

  async createParticipation(paricipationEvent: Event): Promise<Participation> {
    return await this.httpClient.post('/backend/participations', {event:paricipationEvent}).toPromise() as Participation;
  }

  async SetParticipationStatusToAccepted(participationToEdit: Participation): Promise<Participation>{
    return await this.httpClient.patch(`/backend/participations/${participationToEdit.id}`, {approval: Approval.ACCEPTED}).toPromise() as Participation;
  }
  async SetParticipationStatusToDenied(participationToEdit: Participation): Promise<Participation>{
    return await this.httpClient.patch(`/backend/participations/${participationToEdit.id}`, {approval: Approval.REJECTED}).toPromise() as Participation;
  }

  async deleteLocation(participationToDelete: Participation): Promise<void> {
      await this.httpClient.delete(`/backend/participations/${participationToDelete.id}`).toPromise();
  }
}
