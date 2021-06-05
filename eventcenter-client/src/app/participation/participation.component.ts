import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EventService } from '../core/event.service';
import { ParticipationService } from '../core/participation.service';
import { Participation } from '../domain/paticipation';
import { EventEditorComponent } from '../event-editor/event-editor.component';
import { Event } from '../domain/event'

@Component({
  selector: 'app-participation',
  templateUrl: './participation.component.html',
  styleUrls: ['./participation.component.scss']
})
export class ParticipationComponent implements OnInit {

  participations!: Promise<Participation[]>;
  

  constructor(
    private participationService: ParticipationService,
  ) { }

  ngOnInit(): void {
    this.getParticipations();
  }


  private getParticipations(): void {
    this.participations = this.participationService.getParticipations();
  }

  async deleteParticipation(participation: Participation): Promise<void> {
    await this.participationService.deleteParticipation(participation);
    this.getParticipations();
  }

}
