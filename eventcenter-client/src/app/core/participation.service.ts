import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Participation } from '../domain/paticipation';

@Injectable({
  providedIn: 'root'
})
export class ParticipationService {

 
  participations: Participation[] = []

  constructor(private httpClient: HttpClient) { }

  async createParticipation(participationToCreate: Participation): Promise<Participation> {
    return await this.httpClient.post('/backend/participations', participationToCreate).toPromise() as Participation;
  }

  async editUser(participationToEdit: Participation, value: Participation): Promise<Participation>{
    return await this.httpClient.patch(`/backend/users/${participationToEdit.id}`, value).toPromise() as Participation;
  }

  async deleteLocation(participationToDelete: Participation): Promise<void> {
      await this.httpClient.delete(`/backend/participations/${participationToDelete.id}`).toPromise();
  }
}
