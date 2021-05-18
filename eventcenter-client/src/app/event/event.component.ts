import { Component, OnInit } from '@angular/core';
import { Event } from '../domain/event'

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss']
})
export class EventComponent implements OnInit {

  events: Event[] = [{
    title: 'Deszkazas',
    description: 'Kell hozza deszka',
    location: 'Itt'
  }, {
    title: 'Biciklizes',
    description: 'Meglepo, de ehez pedig bicikli kell',
    location: 'Ott'
  }]

  constructor() { }

  ngOnInit(): void {
  }

}
