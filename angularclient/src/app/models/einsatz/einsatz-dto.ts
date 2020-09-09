import {EinsatzStatus} from "./einsatz-status.enum";

export class EinsatzDTO {
  id: string;
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
