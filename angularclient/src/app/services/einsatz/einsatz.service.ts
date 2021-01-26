import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {EinsatzStatus} from "../../models/einsatz/einsatz-status.enum";
import {EinsatzSucheDto} from "../../models/einsatz/einsatz-suche-dto";
import {Einsatz} from "../../models/einsatz/einsatz";
import {EinsatzDTO} from "../../models/einsatz/einsatz-dto";
import {PartialEinsaetzeDTO} from "../../models/einsatz/partialeinsaetze-dto";
import {environment} from "../../../environments/environment";
import {DatePipe} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class EinsatzService {

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Einsatz[]> {
    return this.http.get<Einsatz[]>(environment.API_URL + '/listEinsaetze');
  }

  public getPartialEinsaetze(params: HttpParams): Observable<PartialEinsaetzeDTO> {
    return this.http.get<PartialEinsaetzeDTO>(environment.API_URL + '/partialEinsaetze', {params: params});
  }

  public save(einsatzDTO: EinsatzDTO): Observable<EinsatzDTO> {
    return this.http.post<EinsatzDTO>(environment.API_URL + '/addEinsatz', einsatzDTO);
  }

  public delete(id: string): Observable<Einsatz> {
    let params = new HttpParams().set("einsatzId", id);
    return this.http.post<Einsatz>(environment.API_URL + '/deleteEinsatz', '', {params: params});
  }

  public update(einsatzDTO: EinsatzDTO): Observable<EinsatzDTO> {
    return this.http.post<EinsatzDTO>(environment.API_URL + '/updateEinsatz', einsatzDTO);
  }

  public findEinsaetzeByEinsatzStatus(status: EinsatzStatus): Observable<Einsatz[]> {
    let params = new HttpParams().set("status", status);
    return this.http.get<Einsatz[]>(environment.API_URL + '/findEinsaetzeByEinsatzStatus', {params: params});
  }

  public findEinsaetzeByMitarbeiterVertrieb(id: string): Observable<Einsatz[]> {
    let params = new HttpParams().set("mitarbeiterVertriebId", id);
    return this.http.get<Einsatz[]>(environment.API_URL + '/findEinsaetzeByMitarbeiterVertrieb', {params: params});
  }

  public findEinsaetzeBySuchkriterien(einsatzSucheDTO: EinsatzSucheDto): Observable<Einsatz[]> {
    return this.http.post<Einsatz[]>(environment.API_URL + '/findEinsaetzeBySuchkriterien', einsatzSucheDTO);
  }

  public writeEinsaetzeFilter(einsatzSucheDTO: EinsatzSucheDto): string[] {
    let einsaetzeFilter: Array<string> = [];
    if (einsatzSucheDTO.mitarbeiterId.toString() != '') {
      einsaetzeFilter.push(("mitarbeiter.id::" + einsatzSucheDTO.mitarbeiterId.toString()));
    }
    if (einsatzSucheDTO.mitarbeiterVertriebId.toString() != '') {
      einsaetzeFilter.push(("mitarbeiterVertrieb.id::" + einsatzSucheDTO.mitarbeiterVertriebId.toString()));
    }
    if (einsatzSucheDTO.einsatzStatus != null && einsatzSucheDTO.einsatzStatus.toString() != '') {
      einsaetzeFilter.push("einsatzStatus::" + einsatzSucheDTO.einsatzStatus.toUpperCase());
    }
    if (einsatzSucheDTO.mitarbeiterStatus != null && einsatzSucheDTO.mitarbeiterStatus.toString() != '') {
      einsaetzeFilter.push(("mitarbeiter.mitarbeiterStatus::" + einsatzSucheDTO.mitarbeiterStatus.toUpperCase()));
    }
    if (einsatzSucheDTO.wahrscheinlichkeitVon != null && einsatzSucheDTO.wahrscheinlichkeitVon.valueOf() != 0) {
      einsaetzeFilter.push("wahrscheinlichkeit>=" + einsatzSucheDTO.wahrscheinlichkeitVon.valueOf());
    }
    if (einsatzSucheDTO.wahrscheinlichkeitBis != null && einsatzSucheDTO.wahrscheinlichkeitBis.valueOf() != 0) {
      einsaetzeFilter.push("wahrscheinlichkeit<=" + einsatzSucheDTO.wahrscheinlichkeitBis.valueOf());
    }
    if (einsatzSucheDTO.beginnVon != null && einsatzSucheDTO.beginnVon.valueOf() != 0) {
      einsaetzeFilter.push("beginn>=" + this.dateformatter(einsatzSucheDTO.beginnVon));
    }
    if (einsatzSucheDTO.beginnBis != null && einsatzSucheDTO.beginnBis.valueOf() != 0) {
      einsaetzeFilter.push("beginn<=" + this.dateformatter(einsatzSucheDTO.beginnBis));
    }
    if (einsatzSucheDTO.endeVon != null && einsatzSucheDTO.endeVon.valueOf() != 0) {
      einsaetzeFilter.push("ende>=" + this.dateformatter(einsatzSucheDTO.endeVon));
    }
    if (einsatzSucheDTO.endeBis != null && einsatzSucheDTO.endeBis.valueOf() != 0) {
      einsaetzeFilter.push("ende<=" + this.dateformatter(einsatzSucheDTO.endeBis));
    }
    return einsaetzeFilter;
  }

  dateformatter(date: Date): String {
    let datepipe = new DatePipe('en-US');
    return datepipe.transform(date, 'dd-MM-yyyy')
  }

  public getEinsatzById(id: string): Observable<Einsatz[]> {
    let params = new HttpParams().set("einsatzId", id);
    return this.http.get<Einsatz[]>(environment.API_URL + '/getEinsatzById', {params: params});
  }

}
