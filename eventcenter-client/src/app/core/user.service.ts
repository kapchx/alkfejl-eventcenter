import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../domain/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  users: User[] = []

  constructor(private httpClient: HttpClient) { }

  async getUsers(): Promise<User[]> {
    return await this.httpClient.get('/backend/events').toPromise() as User[];
  }

  async createUser(user: User): Promise<User> {
    return await this.httpClient.post('/backend/users/register', user).toPromise() as User;
  }

  async editUser(userToEdit: User, value: User): Promise<User>{
      return await this.httpClient.patch(`/backend/users/${userToEdit.id}`, value).toPromise() as User;
  }

  async deleteUser(userToDelete: User): Promise<void>{
    await this.httpClient.delete(`/backend/users/${userToDelete.id}`).toPromise();
}
}
