import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MitarbeiterService} from "../../services/mitarbeiter.service";

@Component({
  selector: 'app-mitarbeiter-search',
  templateUrl: './mitarbeiter-search.component.html',
  styleUrls: ['./mitarbeiter-search.component.css']
})
export class MitarbeiterSearchComponent {

  idLastEndDate: string;
  idChance: string;

  date: string;
  chance: string;

  constructor(private route: ActivatedRoute, private router: Router, private mitarbeiterService: MitarbeiterService) {
  }

  getLastEndDateForMitarbeiter() {
    this.mitarbeiterService.getLastEndDateForMitarbeiter(this.idLastEndDate).subscribe(result => this.date = result);
  }

  getChanceForMitarbeiter() {
    this.mitarbeiterService.getChanceForMitarbeiter(this.idChance).subscribe(result => this.chance = result);
  }

}
