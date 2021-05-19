import { formatCurrency } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Event } from '../domain/event'
import { EventService } from '../event.service';

@Component({
  selector: 'app-event-editor',
  templateUrl: './event-editor.component.html',
  styleUrls: ['./event-editor.component.scss']
})
export class EventEditorComponent implements OnInit {

  
  eventForm: FormGroup =this.fb.group({
    title: ['', [Validators.required, Validators.minLength(2) ]],
    location: ['' ,[Validators.required]],
    description: ['']
  })

  get title(): FormControl {
    return this.eventForm.get('title') as FormControl;
  }

  get location(): FormControl {
    return this.eventForm.get('location') as FormControl;
  }

  constructor( private fb: FormBuilder, private eventService: EventService) {}

  ngOnInit(): void {}

  submit() : void {
    if(this.eventForm.valid){
      this.eventService.createEvent(this.eventForm.value);
    }
  }

}
