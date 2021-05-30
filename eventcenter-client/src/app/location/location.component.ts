import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LocationService } from '../core/location.service';
import { Location } from '../domain/location';
import { LocationEditorComponent } from '../location-editor/location-editor.component';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.scss']
  
})
export class LocationComponent implements OnInit {

  locations!: Promise<Location[]>;

  constructor(
    private locationService: LocationService,
    private dialog: MatDialog
  ){ }

  ngOnInit(): void {
    this.getLocations();
  }

  private getLocations(): void {
    this.locations = this.locationService.getLocations();
  }

  async startCreateEvent(): Promise<void>{
    const dialogRef = this.dialog.open(LocationEditorComponent,{
      height: '400px'
    })
    await dialogRef.afterClosed().toPromise();
    this.getLocations();
  }

  async deleteLocation(location: Location): Promise<void> {
    await this.locationService.deleteLocation(location);
    this.getLocations();
  }

}
