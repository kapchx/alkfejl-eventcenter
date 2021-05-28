import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/auth.guard';
import { EventDetailsComponent } from './event-details/event-details.component';
import { EventComponent } from './event/event.component';
import { LocationComponent } from './location/location.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [{
  path: 'events',
  component:  EventComponent,
  canActivate: [AuthGuard]
}, {
  path: 'events/:eventId',
  component: EventDetailsComponent,
  canActivate: [AuthGuard]
},{
  path: 'locations',
  component:  LocationComponent,
  canActivate: [AuthGuard]
},{
  path: 'login',
  component: LoginComponent
},{
  path: 'register',
  component: RegisterComponent
},{
  path: '**',
  redirectTo: '/events'
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
