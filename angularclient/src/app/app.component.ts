import {Component} from '@angular/core';
import {Mitarbeiter} from "./models/mitarbeiter";
import {UpdateMitarbeiterService} from "./services/update-mitarbeiter.service";

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

  private resetComponents() {
    if (this.mitarbeiter) {
      this.mitarbeiter = undefined;
    }
    this.listMitarbeiter = false;
    this.addMitarbeiter = false;
    this.searchMitarbeiter = false;
    this.updateMitarbeiter = false;
  }
}
