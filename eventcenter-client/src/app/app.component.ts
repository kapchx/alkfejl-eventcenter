import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './core/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  get isLoggedIn(): boolean {
    return this.authService.isLoggedIn;
  }

  constructor(
    private authService: AuthService,
    private router: Router,
  ){}
  
  logout(): void {
    this.authService.logout();
    this.router.navigate(['/', 'login']);
  }
}


