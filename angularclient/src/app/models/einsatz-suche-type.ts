import {MitarbeiterStatus} from "./mitarbeiter-status.enum";
import {EinsatzStatus} from "./einsatz-status.enum";

export class EinsatzSucheType {

  mitarbeiterVertriebId: string;
  mitarbeiterId: string;
  mitarbeiterStatus: MitarbeiterStatus;
  einsatzStatus: EinsatzStatus;
  beginnVon: Date;
  beginnBis: Date;
  endeVon: Date;
  endeBis: Date;

}
