import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/auth.guard';
import { EventDetailsComponent } from './event-details/event-details.component';
import { EventComponent } from './event/event.component';
import { EventsComponent } from './events/events.component';
import { LocationComponent } from './location/location.component';
import { LoginComponent } from './login/login.component';
import { ParticipationComponent } from './participation/participation.component';
import { RegisterComponent } from './register/register.component';
import { UserEditorComponent } from './user-editor/user-editor.component';

const routes: Routes = [{
  path: 'events',
  component:  EventComponent,
  canActivate: [AuthGuard]
},{
  path: 'global-events',
  component:  EventsComponent,
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
  path: 'Profile_Settings',
  component: UserEditorComponent
},{
  path: 'participations',
  component: ParticipationComponent
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
