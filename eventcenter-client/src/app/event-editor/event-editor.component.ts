import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-event-editor',
  templateUrl: './event-editor.component.html',
  styleUrls: ['./event-editor.component.scss']
})
export class EventEditorComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  submit(e : any) : void {
    console.log(e)
  }

}
