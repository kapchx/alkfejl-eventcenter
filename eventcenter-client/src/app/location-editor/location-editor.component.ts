import { Optional } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { LocationService } from '../core/location.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-location-editor',
  templateUrl: './location-editor.component.html',
  styleUrls: ['./location-editor.component.scss']
})
export class LocationEditorComponent implements OnInit {




  locationForm: FormGroup =this.fb.group({
    name: ['', [Validators.required, Validators.minLength(2) ]],
  })

  get title(): FormControl {
    return this.locationForm.get('title') as FormControl;
  }

 

  constructor( 
    private fb: FormBuilder, 
    private locationService: LocationService,
    @Optional() public dialogRef?: MatDialogRef<LocationEditorComponent>,
  ) {
  }

  ngOnInit(): void {
  }

 

  async submit() : Promise<void> {
    if(this.locationForm.valid){
        await this.locationService.createLocation(this.locationForm.value);
      this.dialogRef?.close();
    }
  }


  close() : void {
      this.dialogRef?.close();
  }

}
