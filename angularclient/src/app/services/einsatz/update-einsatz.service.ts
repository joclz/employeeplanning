import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {Einsatz} from "../../models/einsatz/einsatz";

@Injectable({
  providedIn: 'root'
})
export class UpdateEinsatzService {

  private updateEinsatzSource = new Subject<Einsatz>();

  updateEinsatz$ = this.updateEinsatzSource.asObservable();

  updateEinsatz(Einsatz: Einsatz) {
    this.updateEinsatzSource.next(Einsatz);
  }
}
