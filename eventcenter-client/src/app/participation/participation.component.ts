import { Component, OnInit } from '@angular/core';
import { ParticipationService } from '../core/participation.service';
import { Participation } from '../domain/paticipation';


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
