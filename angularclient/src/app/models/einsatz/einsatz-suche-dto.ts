import {MitarbeiterStatus} from "../mitarbeiter/mitarbeiter-status.enum";
import {EinsatzStatus} from "./einsatz-status.enum";

export class EinsatzSucheDto {
  mitarbeiterVertriebId: string;
  mitarbeiterId: string;
  mitarbeiterStatus: MitarbeiterStatus;
  einsatzStatus: EinsatzStatus;
  beginnVon: Date;
  beginnBis: Date;
  endeVon: Date;
  endeBis: Date;
  wahrscheinlichkeitVon: number;
  wahrscheinlichkeitBis: number;
}
