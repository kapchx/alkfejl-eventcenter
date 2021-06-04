import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Location } from '../domain/location'

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  locations: Location[] = []

  constructor(private httpClient: HttpClient) { }

  async getLocations(): Promise<Location[]> {
    return await this.httpClient.get('/backend/locations').toPromise() as Location[];
  }

  async createLocation(locationToCreate: Location): Promise<Location> {
    return await this.httpClient.post('/backend/locations', locationToCreate).toPromise() as Location;
  }

  async deleteLocation(locationToDelete: Location): Promise<void>{
      await this.httpClient.delete(`/backend/locations/${locationToDelete.id}`).toPromise();
  }
}
