import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {Mitarbeiter} from "../models/mitarbeiter";

@Injectable({
  providedIn: 'root'
})
export class UpdateMitarbeiterService {

  private updateMitarbeiterSource = new Subject<Mitarbeiter>();

  updateMitarbeiter$ = this.updateMitarbeiterSource.asObservable();

  updateMitarbeiter(mitarbeiter: Mitarbeiter) {
    this.updateMitarbeiterSource.next(mitarbeiter);
  }
}
