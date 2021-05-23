import { formatCurrency } from '@angular/common';
import { Component, Inject, OnInit, Optional } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Event } from '../domain/event'
import { EventService } from '../core/event.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

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

  constructor( 
    private fb: FormBuilder, 
    private eventService: EventService, 
    @Optional() public dialogRef?: MatDialogRef<EventEditorComponent>,
    @Inject(MAT_DIALOG_DATA) @Optional() private eventToEdit?: Event
  ) {}

  ngOnInit(): void {
    if(this.eventToEdit){
      this.eventForm.reset({
        title: this.eventToEdit.title,
        description: this.eventToEdit.description,
        location: this.eventToEdit.location
      })
    }
  }

  submit() : void {
    if(this.eventForm.valid){
      
      if(this.eventToEdit){
        this.eventService.editEvent(this.eventToEdit,this.eventForm.value);
      }else{
        this.eventService.createEvent(this.eventForm.value);
      }
      
      this.dialogRef?.close();
    }
  }

  close() : void {
      this.dialogRef?.close();
  }

}
