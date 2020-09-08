import {EinsatzStatus} from "./einsatz-status.enum";

export class EinsatzDTO {
  mitarbeiterId: bigint;
  mitarbeiterVertriebId: bigint;
  einsatzStatus: EinsatzStatus;
  beginn: Date;
  ende: Date;
  wahrscheinlichkeit: bigint;
  zusatzkostenReise: number;
  stundensatzVK: number;
  projektnummerNettime: string;
  beauftragungsnummer: string;
}
