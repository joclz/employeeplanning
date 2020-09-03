import {Component} from '@angular/core';
import {Mitarbeiter} from "./models/mitarbeiter";
import {UpdateMitarbeiterService} from "./services/update-mitarbeiter.service";
import {Einsatz} from "./models/einsatz";
import {MitarbeiterVertrieb} from "./models/mitarbeiter-vertrieb";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [UpdateMitarbeiterService]
})
export class AppComponent {
  title: string = 'Mitarbeiter-Planung';

  listMitarbeiter: boolean = false;
  addMitarbeiter: boolean = false;
  searchMitarbeiter: boolean = false;
  updateMitarbeiter: boolean = false;
  mitarbeiter: Mitarbeiter;

  listMitarbeiterVertrieb: boolean = false;
  addMitarbeiterVertrieb: boolean = false;
  updateMitarbeiterVertrieb: boolean = false;
  mitarbeiterVertrieb: MitarbeiterVertrieb;

  listEinsatz: boolean = false;
  addEinsatz: boolean = false;
  searchEinsatz: boolean = false;
  updateEinsatz: boolean = false;
  einsatz: Einsatz;

  constructor(private updateMitarbeiterService: UpdateMitarbeiterService) {
    updateMitarbeiterService.updateMitarbeiter$.subscribe(data =>
      this.onClickUpdateMitarbeiter(data)
    );
  }

  onClickListMitarbeiter() {
    this.resetComponents();
    this.listMitarbeiter = true;
  }

  onClickAddMitarbeiter() {
    this.resetComponents();
    this.addMitarbeiter = true;
  }

  onClickUpdateMitarbeiter(mitarbeiter: Mitarbeiter) {
    this.resetComponents();
    this.mitarbeiter = mitarbeiter;
    this.updateMitarbeiter = true;
  }

  onClickSearchMitarbeiter() {
    this.resetComponents();
    this.searchMitarbeiter = true;
  }

  onClickListMitarbeiterVertrieb() {
    this.resetComponents();
    this.listMitarbeiterVertrieb = true;
  }

  onClickAddMitarbeiterVertrieb() {
    this.resetComponents();
    this.addMitarbeiterVertrieb = true;
  }

  onClickUpdateMitarbeiterVertrieb(mitarbeiterVertieb: MitarbeiterVertrieb) {
    this.resetComponents();
    this.mitarbeiterVertrieb = mitarbeiterVertieb;
    this.updateMitarbeiterVertrieb = true;
  }

  onClickListEinsatz() {
    this.resetComponents();
    this.listEinsatz = true;
  }

  onClickAddEinsatz() {
    this.resetComponents();
    this.addEinsatz = true;
  }

  onClickSearchEinsatz() {
    this.resetComponents();
    this.searchEinsatz = true;
  }

  onClickUpdateEinsatz(einsatz: Einsatz) {
    this.resetComponents();
    this.einsatz = einsatz;
    this.updateEinsatz = true;
  }

  private resetComponents() {
    if (this.mitarbeiter) {
      this.mitarbeiter = undefined;
    }
    this.listMitarbeiter = false;
    this.addMitarbeiter = false;
    this.searchMitarbeiter = false;
    this.updateMitarbeiter = false;

    this.listMitarbeiterVertrieb = false;
    this.addMitarbeiterVertrieb = false;
    this.updateMitarbeiterVertrieb = false;

    this.listEinsatz = false;
    this.addEinsatz = false;
    this.searchEinsatz = false;
    this.updateEinsatz = false;
  }
}
