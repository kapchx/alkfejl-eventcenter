import { Component, Inject, OnInit, Optional } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Event } from '../domain/event'
import { EventService } from '../core/event.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { LocationService } from '../core/location.service';
import { Location } from '../domain/location'

@Component({
  selector: 'app-event-editor',
  templateUrl: './event-editor.component.html',
  styleUrls: ['./event-editor.component.scss']
})
export class EventEditorComponent implements OnInit {

  locations!: Promise<Location[]>;

  eventForm: FormGroup =this.fb.group({
    title: ['', [Validators.required, Validators.minLength(2) ]],
    locations: ['' ,[Validators.required]],
    startAt: ['' ,[Validators.required]],
    description: ['']
  })

  get title(): FormControl {
    return this.eventForm.get('title') as FormControl;
  }

  get location(): FormControl {
    return this.eventForm.get('locations') as FormControl;
  }

  constructor( 
    private fb: FormBuilder, 
    private eventService: EventService, 
    private locationService: LocationService,
    @Optional() public dialogRef?: MatDialogRef<EventEditorComponent>,
    @Inject(MAT_DIALOG_DATA) @Optional() private eventToEdit?: Event,
   
  ) {
    this.getLocations();
  }

  ngOnInit(): void {
    if(this.eventToEdit){
      this.eventForm.reset({
        title: this.eventToEdit.title,
        description: this.eventToEdit.description,
        locations: this.eventToEdit.locations,
        startAt: this.eventToEdit.startAt
      })
    }
  }

  geteventTOEdit(): Event | undefined{
    return this.eventToEdit;
  }

  async submit() : Promise<void> {
    if(this.eventForm.valid){
      console.log(this.eventForm.value);
      if(this.eventToEdit){
        await this.eventService.editEvent(this.eventToEdit,this.eventForm.value);
      }else{
        await this.eventService.createEvent(this.eventForm.value);
      }
      
      this.dialogRef?.close();
    }
  }


  close() : void {
      this.dialogRef?.close();
  }

  private getLocations(): void {
    this.locations = this.locationService.getLocations();
  }

  /*
   <form (submit)="addLocation()">
        <mat-form-field class ="full-width" appearance="fill">
            <mat-label>Location</mat-label>
            <mat-select 
                required 
                matInput 
                type="text" 
                name="locations"
                formControlName="locations"
            >
                <mat-option 
                    *ngFor="let location of locations | async" 
                    [value] =  "location"
                >
                    {{ location.name }}
                </mat-option>
                <button 
                    [routerLink]="['/', 'locations']" 
                    mat-button 
                    color="primary"
                    class ="full-width"
                >
                    <mat-icon>add</mat-icon>
                    Add location
                </button>
            </mat-select>
            <mat-error *ngIf="location.invalid">
                {{location.errors! | locationErrors}}
            </mat-error>
        </mat-form-field>
    </form>

  */

}
