import { HttpClient } from '@angular/common/http';
import { Injectable, Optional } from '@angular/core';
import { User, UserRole } from '../domain/user';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse extends User {
  token: string;
}

const UserStorageKey = "user";
const TokenStorageKey = "token";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUser: User | null = null;

  get user(): User {
    if(!this.currentUser){
      this.currentUser = JSON.parse(
        sessionStorage.getItem(UserStorageKey) as string
      );
    }
    return this.currentUser as User;
  }

  private currentToken: string | null = null;

  get token(): string {
    if(!this.currentToken){
      this.currentToken = sessionStorage.getItem(TokenStorageKey)
    }
    return this.currentToken as string;
  }

  get isLoggedIn(): boolean {
    return !!this.token;
  }

  get isAdmin(): boolean {
    return this.user.role === UserRole.ROLE_ADMIN;
  }

  constructor(private httpClient: HttpClient){}

  async login(loginRequest: LoginRequest): Promise<void>{
    const user = await this.httpClient
    .post<LoginResponse>('/backend/api/auth', loginRequest)
    .toPromise();
    this.setUser(user);
  }

  logout(): void {
    this.setUser(null);
  }
  
  private setUser(user: LoginResponse | null): void {
      if (user) {
        sessionStorage.setItem(TokenStorageKey, user.token);
        sessionStorage.setItem(UserStorageKey, JSON.stringify(user));
        document.cookie = `${TokenStorageKey}=${user.token}`;
        document.cookie = `${UserStorageKey}=${JSON.stringify(user)}`;
      } else {
        sessionStorage.removeItem(TokenStorageKey);
        sessionStorage.removeItem(UserStorageKey);
      }
      this.currentUser = user;
      this.currentToken = user?.token || null;
  }

}
