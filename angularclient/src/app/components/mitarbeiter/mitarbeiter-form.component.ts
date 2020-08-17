import {Component} from '@angular/core';
import {Mitarbeiter} from "../../models/mitarbeiter";
import {ActivatedRoute, Router} from "@angular/router";
import {MitarbeiterService} from "../../services/mitarbeiter.service";
import {MitarbeiterUnit} from "../../models/mitarbeiter-unit.enum";
import {MitarbeiterStatus} from "../../models/mitarbeiter-status.enum";

@Component({
  selector: 'app-mitarbeiter-form',
  templateUrl: './mitarbeiter-form.component.html',
  styleUrls: ['./mitarbeiter-form.component.css']
})
export class MitarbeiterFormComponent {

  mitarbeiter: Mitarbeiter;

  mitarbeiterUnit = MitarbeiterUnit;
  public mitarbeiterUnits = [];

  mitarbeiterStatus = MitarbeiterStatus;
  public mitarbeiterStatusList = [];

  constructor(private route: ActivatedRoute, private router: Router, private mitarbeiterService: MitarbeiterService) {
    this.mitarbeiter = new Mitarbeiter();
    this.mitarbeiterUnits = Object.keys(this.mitarbeiterUnit);
    this.mitarbeiterStatusList = Object.keys(this.mitarbeiterStatus);
  }

  onSubmit() {
    this.mitarbeiterService.save(this.mitarbeiter).subscribe();
  }

}
