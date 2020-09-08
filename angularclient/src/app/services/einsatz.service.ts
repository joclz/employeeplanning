import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {EinsatzStatus} from "../models/einsatz-status.enum";
import {EinsatzSuche} from "../models/einsatz-suche";
import {Einsatz} from "../models/einsatz";
import {EinsatzDTO} from "../models/einsatz-dto";

@Injectable({
  providedIn: 'root'
})
export class EinsatzService {

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Einsatz[]> {
    return this.http.get<Einsatz[]>('http://localhost:8080/listEinsaetze');
  }

  public save(einsatzDto: EinsatzDTO) {
    return this.http.post<EinsatzDTO>('http://localhost:8080/addEinsatz', einsatzDto);
  }

  public delete(id: string) {
    let params = new HttpParams().set("einsatzId", id);
    return this.http.post<Einsatz>('http://localhost:8080/deleteEinsatz', '', {params: params});
  }

  public update(Einsatz: Einsatz) {
    return this.http.post<Einsatz>('http://localhost:8080/updateEinsatz', Einsatz);
  }

  public findEinsaetzeByEinsatzStatus(status: EinsatzStatus) {
    let params = new HttpParams().set("status", status);
    return this.http.get<Einsatz[]>('http://localhost:8080/findEinsaetzeByEinsatzStatus', {params: params});
  }

  public findEinsaetzeByMitarbeiterVertrieb(id: string) {
    let params = new HttpParams().set("mitarbeiterVertriebId", id);
    return this.http.get<Einsatz[]>('http://localhost:8080/findEinsaetzeByMitarbeiterVertrieb', {params: params});
  }

  public findEinsaetzeBySuchkriterien(einsatzSuche: EinsatzSuche) {
    return this.http.post<Einsatz[]>('http://localhost:8080/findEinsaetzeBySuchkriterien', einsatzSuche);
  }

  public getEinsatzById(id: string) {
    let params = new HttpParams().set("einsatzId", id);
    return this.http.get<Einsatz[]>('http://localhost:8080/getEinsatzById', {params: params});
  }

}
