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
  private user1!: User;

  get user(): User {
    if(!this.currentUser){
      this.currentUser = JSON.parse(
        sessionStorage.getItem(UserStorageKey) as string
      );
    }
    return this.currentUser as User;
  }

  public getCurrentUser(): User {
   /* const ru : User = {id: this.currentUser!.id,
      name: this.currentUser!.name,
      username: this.currentUser!.username,
      password: 'string',
      role: UserRole.ROLE_GUEST}*/
    return this.user1;
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
    console.log(this.isAdmin);
    this.user1 = {id: 1, name: this.user.name, username: this.user.username, password: this.user.password, role: this.user.role };

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
