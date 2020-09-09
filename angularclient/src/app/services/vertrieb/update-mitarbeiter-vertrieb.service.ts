import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {MitarbeiterVertrieb} from "../../models/vertrieb/mitarbeiter-vertrieb";

@Injectable({
  providedIn: 'root'
})
export class UpdateMitarbeiterVertriebService {

  private updateMitarbeiterVertriebSource = new Subject<MitarbeiterVertrieb>();

  updateMitarbeiterVertrieb$ = this.updateMitarbeiterVertriebSource.asObservable();

  updateMitarbeiterVertrieb(mitarbeiterVertrieb: MitarbeiterVertrieb) {
    this.updateMitarbeiterVertriebSource.next(mitarbeiterVertrieb);
  }
}
