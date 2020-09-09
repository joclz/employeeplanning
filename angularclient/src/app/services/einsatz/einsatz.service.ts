import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {EinsatzStatus} from "../../models/einsatz/einsatz-status.enum";
import {EinsatzSucheDto} from "../../models/einsatz/einsatz-suche-dto";
import {Einsatz} from "../../models/einsatz/einsatz";
import {EinsatzDTO} from "../../models/einsatz/einsatz-dto";

@Injectable({
  providedIn: 'root'
})
export class EinsatzService {

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Einsatz[]> {
    return this.http.get<Einsatz[]>('http://localhost:8080/listEinsaetze');
  }

  public save(einsatzDTO: EinsatzDTO): Observable<EinsatzDTO> {
    return this.http.post<EinsatzDTO>('http://localhost:8080/addEinsatz', einsatzDTO);
  }

  public delete(id: string): Observable<Einsatz> {
    let params = new HttpParams().set("einsatzId", id);
    return this.http.post<Einsatz>('http://localhost:8080/deleteEinsatz', '', {params: params});
  }

  public update(einsatzDTO: EinsatzDTO): Observable<EinsatzDTO> {
    return this.http.post<EinsatzDTO>('http://localhost:8080/updateEinsatz', einsatzDTO);
  }

  public findEinsaetzeByEinsatzStatus(status: EinsatzStatus): Observable<Einsatz[]> {
    let params = new HttpParams().set("status", status);
    return this.http.get<Einsatz[]>('http://localhost:8080/findEinsaetzeByEinsatzStatus', {params: params});
  }

  public findEinsaetzeByMitarbeiterVertrieb(id: string): Observable<Einsatz[]> {
    let params = new HttpParams().set("mitarbeiterVertriebId", id);
    return this.http.get<Einsatz[]>('http://localhost:8080/findEinsaetzeByMitarbeiterVertrieb', {params: params});
  }

  public findEinsaetzeBySuchkriterien(einsatzSucheDTO: EinsatzSucheDto): Observable<Einsatz[]> {
    return this.http.post<Einsatz[]>('http://localhost:8080/findEinsaetzeBySuchkriterien', einsatzSucheDTO);
  }

  public getEinsatzById(id: string): Observable<Einsatz[]> {
    let params = new HttpParams().set("einsatzId", id);
    return this.http.get<Einsatz[]>('http://localhost:8080/getEinsatzById', {params: params});
  }

}
