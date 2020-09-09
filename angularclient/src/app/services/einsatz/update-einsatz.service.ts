import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {EinsatzDTO} from "../../models/einsatz/einsatz";

@Injectable({
  providedIn: 'root'
})
export class UpdateEinsatzService {

  private updateEinsatzSource = new Subject<EinsatzDTO>();

  updateEinsatz$ = this.updateEinsatzSource.asObservable();

  updateEinsatz(Einsatz: EinsatzDTO) {
    this.updateEinsatzSource.next(Einsatz);
  }
}
