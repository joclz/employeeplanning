import {Mitarbeiter} from "../mitarbeiter/mitarbeiter";
import {MitarbeiterVertrieb} from "../vertrieb/mitarbeiter-vertrieb";
import {EinsatzStatus} from "./einsatz-status.enum";

export interface Einsatz {
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
