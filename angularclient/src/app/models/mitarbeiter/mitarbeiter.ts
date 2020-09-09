import {MitarbeiterStatus} from "./mitarbeiter-status.enum";
import {MitarbeiterUnit} from "./mitarbeiter-unit.enum";

export class Mitarbeiter {
  id: string;
  mitarbeiterStatus: MitarbeiterStatus;
  stundensatzEK: string;
  name: string;
  vorname: string;
  mitarbeiterUnit: MitarbeiterUnit;
}
