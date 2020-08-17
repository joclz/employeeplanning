import {Component} from '@angular/core';
import {Mitarbeiter} from "../../models/mitarbeiter";
import {MitarbeiterService} from "../../services/mitarbeiter.service";

@Component({
  selector: 'app-mitarbeiter',
  templateUrl: './mitarbeiter.component.html',
  styleUrls: ['./mitarbeiter.component.css']
})
export class MitarbeiterComponent {
  mitarbeiter: Mitarbeiter
  mitarbeiterList: Mitarbeiter[];
  zeigeMitarbeiter: boolean;
  mitarbeiterHinzufuegen: boolean;
  sucheMitarbeiter: boolean;




  constructor(private mitarbeiterService: MitarbeiterService) {

  }

  listAllMitarbeiter(): void {
    this.zeigeMitarbeiter = !this.zeigeMitarbeiter;
    this.mitarbeiterHinzufuegen = false;
    this.mitarbeiterService.findAll().subscribe(data => {
      this.mitarbeiterList = data;
    })
  }

  addMitarbeiter(): void {
    this.zeigeMitarbeiter = false;
    this.mitarbeiterHinzufuegen = !this.mitarbeiterHinzufuegen;
  }

  searchMitarbeiter(): void {
    this.zeigeMitarbeiter = false;
    this.mitarbeiterHinzufuegen = false;
    this.sucheMitarbeiter = !this.sucheMitarbeiter;
  }



}
