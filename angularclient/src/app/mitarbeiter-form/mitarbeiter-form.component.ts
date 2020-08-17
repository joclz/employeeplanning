import {Component} from '@angular/core';
import {Mitarbeiter} from "../mitarbeiter";
import {ActivatedRoute, Router} from "@angular/router";
import {MitarbeiterService} from "../mitarbeiter.service";

@Component({
  selector: 'app-mitarbeiter-form',
  templateUrl: './mitarbeiter-form.component.html',
  styleUrls: ['./mitarbeiter-form.component.css']
})
export class MitarbeiterFormComponent {

  mitarbeiter: Mitarbeiter;

  constructor(private route: ActivatedRoute, private router: Router, private mitarbeiterService: MitarbeiterService) {
    this.mitarbeiter = new Mitarbeiter();
  }

  onSubmit() {
    this.mitarbeiterService.save(this.mitarbeiter).subscribe(() => this.gotoMitarbeiterList());
  }

  gotoMitarbeiterList() {
    this.router.navigate(['/mitarbeiter']);
  }

}
