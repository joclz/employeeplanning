import {Mitarbeiter} from "./mitarbeiter";
import {MitarbeiterVertrieb} from "./mitarbeiter-vertrieb";
import {EinsatzStatus} from "./einsatz-status.enum";

export class Einsatz {
  id: string;
  mitarbeiter: Mitarbeiter;
  mitarbeiterVertrieb: MitarbeiterVertrieb;
  einsatzStatus: EinsatzStatus;
  beginn: Date;
  ende: Date;
  wahrscheinlichkeit: bigint;
  zusatzkostenReise: number;
  stundensatzVK: number;
  projektnummerNettime: string;
  beauftragungsnummer: string;
  deckungsbeitrag: number;
  marge: number;
}
