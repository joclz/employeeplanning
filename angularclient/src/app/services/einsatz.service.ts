import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {EinsatzStatus} from "../models/einsatz-status.enum";
import {EinsatzSucheType} from "../models/einsatz-suche-type";
import {Einsatz} from "../models/einsatz";

@Injectable({
  providedIn: 'root'
})
export class EinsatzService {

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Einsatz[]> {
    return this.http.get<Einsatz[]>('http://localhost:8080/listEinsaetze');
  }

  public save(Einsatz: Einsatz) {
    return this.http.post<Einsatz>('http://localhost:8080/addEinsatz', Einsatz);
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
    return this.http.get<Einsatz>('http://localhost:8080/findEinsaetzeByEinsatzStatus', {params: params});
  }

  public findEinsaetzeByMitarbeiterVertrieb(id: string) {
    let params = new HttpParams().set("mitarbeiterVertriebId", id);
    return this.http.get<Einsatz>('http://localhost:8080/findEinsaetzeByMitarbeiterVertrieb', {params: params});
  }

  public findEinsaetzeBySuchkriterien(einsatzSucheType: EinsatzSucheType) {
    let params = new HttpParams().set("einsatzSucheType", JSON.stringify(einsatzSucheType));
    return this.http.get<Einsatz>('http://localhost:8080/findEinsaetzeBySuchkriterien', {params: params});
  }

  public getEinsatzById(id: string) {
    let params = new HttpParams().set("einsatzId", id);
    return this.http.get<Einsatz>('http://localhost:8080/getEinsatzById', {params: params});
  }

}
