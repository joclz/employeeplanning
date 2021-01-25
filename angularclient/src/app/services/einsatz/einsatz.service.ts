import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {EinsatzStatus} from "../../models/einsatz/einsatz-status.enum";
import {EinsatzSucheDto} from "../../models/einsatz/einsatz-suche-dto";
import {Einsatz} from "../../models/einsatz/einsatz";
import {EinsatzDTO} from "../../models/einsatz/einsatz-dto";
import {PartialEinsaetzeDTO} from "../../models/einsatz/partialeinsaetze-dto";
import {environment} from "../../../environments/environment";

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
    if (einsatzSucheDTO.einsatzStatus.toString() != '') {
      einsaetzeFilter.push("einsatzStatus::" + einsatzSucheDTO.einsatzStatus.toString().toUpperCase());
    }
    if (einsatzSucheDTO.wahrscheinlichkeitVon.valueOf() != 0) {
      einsaetzeFilter.push("wahrscheinlichkeit>=" + einsatzSucheDTO.wahrscheinlichkeitVon.valueOf());
    }
    if (einsatzSucheDTO.wahrscheinlichkeitBis.valueOf() != 0) {
      einsaetzeFilter.push("wahrscheinlichkeit<=" + einsatzSucheDTO.wahrscheinlichkeitBis.valueOf());
    }
    return einsaetzeFilter;
  }

  public getEinsatzById(id: string): Observable<Einsatz[]> {
    let params = new HttpParams().set("einsatzId", id);
    return this.http.get<Einsatz[]>(environment.API_URL + '/getEinsatzById', {params: params});
  }

}
