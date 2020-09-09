import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Mitarbeiter} from "../../models/mitarbeiter/mitarbeiter";
import {MitarbeiterStatus} from "../../models/mitarbeiter/mitarbeiter-status.enum";
import {MitarbeiterDTO} from "../../models/mitarbeiter/mitarbeiter-dto";

@Injectable({
  providedIn: 'root'
})
export class MitarbeiterService {

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Mitarbeiter[]> {
    return this.http.get<Mitarbeiter[]>('http://localhost:8080/listMitarbeiter');
  }

  public save(mitarbeiter: Mitarbeiter): Observable<Mitarbeiter> {
    return this.http.post<Mitarbeiter>('http://localhost:8080/addMitarbeiter', mitarbeiter);
  }

  public getLastEndDateForMitarbeiter(id: string): Observable<string> {
    let params = new HttpParams().set("mitarbeiterId", id);
    return this.http.get<string>('http://localhost:8080/getLastEndDateForMitarbeiter', {params: params});
  }

  public getChanceForMitarbeiter(id: string): Observable<string> {
    let params = new HttpParams().set("mitarbeiterId", id);
    return this.http.get<string>('http://localhost:8080/getChanceForMitarbeiter', {params: params});
  }

  public delete(id: string): Observable<Mitarbeiter> {
    let params = new HttpParams().set("mitarbeiterId", id);
    return this.http.post<Mitarbeiter>('http://localhost:8080/deleteMitarbeiter', '', {params: params});
  }

  public update(mitarbeiter: Mitarbeiter): Observable<Mitarbeiter> {
    return this.http.post<Mitarbeiter>('http://localhost:8080/updateMitarbeiter', mitarbeiter);
  }

  public getMitarbeiterImEinsatz(mitarbeiterStatus: MitarbeiterStatus): Observable<MitarbeiterStatus>{
    let params = new HttpParams().set("mitarbeiterStatus", mitarbeiterStatus);
    return this.http.get<MitarbeiterStatus>('http://localhost:8080/countMitarbeiterImEinsatz', {params: params});
  }

  public getDeckungsbeitrag(): Observable<string> {
    return this.http.get<string>('http://localhost:8080/getDeckungsbeitrag');
  }

  public getMitarbeiterBank(): Observable<Mitarbeiter[]> {
    return this.http.get<Mitarbeiter[]>('http://localhost:8080/listMitarbeiterBank');
  }

  public getMitarbeiterInternBank(): Observable<Mitarbeiter[]> {
    return this.http.get<Mitarbeiter[]>('http://localhost:8080/listMitarbeiterInternBank');
  }

  public getMitarbeiterListOrderByName(): Observable<MitarbeiterDTO[]> {
    return this.http.get<MitarbeiterDTO[]>('http://localhost:8080/getMitarbeiterListOrderByName');
  }

}
