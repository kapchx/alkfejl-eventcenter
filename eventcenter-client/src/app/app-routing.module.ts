import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventComponent } from './event/event.component';
import { MenuComponent } from './menu/menu.component';

const routes: Routes = [{
  path: 'events',
  component:  EventComponent,
}, {
  path: '**',
  redirectTo: '/events'
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
