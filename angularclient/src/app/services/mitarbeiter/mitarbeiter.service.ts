import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Mitarbeiter} from "../../models/mitarbeiter/mitarbeiter";
import {MitarbeiterStatus} from "../../models/mitarbeiter/mitarbeiter-status.enum";
import {MitarbeiterDTO} from "../../models/mitarbeiter/mitarbeiter-dto";
import {environment} from "../../../environments/environment";
import {MitarbeiterEinsatzDate} from "../../models/mitarbeiterEinsatzDate-data";

@Injectable({
  providedIn: 'root'
})
export class MitarbeiterService {

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Mitarbeiter[]> {
    return this.http.get<Mitarbeiter[]>(environment.API_URL+'/listMitarbeiter');
  }

  public save(mitarbeiter: Mitarbeiter): Observable<Mitarbeiter> {
    return this.http.post<Mitarbeiter>(environment.API_URL+'/addMitarbeiter', mitarbeiter);
  }

  public getLastEndDateForMitarbeiter(id: string): Observable<string> {
    let params = new HttpParams().set("mitarbeiterId", id);
    return this.http.get<string>(environment.API_URL+'/getLastEndDateForMitarbeiter', {params: params});
  }

  public getChanceForMitarbeiter(id: string): Observable<string> {
    let params = new HttpParams().set("mitarbeiterId", id);
    return this.http.get<string>(environment.API_URL+'/getChanceForMitarbeiter', {params: params});
  }

  public delete(id: string): Observable<Mitarbeiter> {
    let params = new HttpParams().set("mitarbeiterId", id);
    return this.http.post<Mitarbeiter>(environment.API_URL+'/deleteMitarbeiter', '', {params: params});
  }

  public update(mitarbeiter: Mitarbeiter): Observable<Mitarbeiter> {
    return this.http.post<Mitarbeiter>(environment.API_URL+'/updateMitarbeiter', mitarbeiter);
  }

  public getMitarbeiterImEinsatz(mitarbeiterStatus: MitarbeiterStatus): Observable<MitarbeiterStatus> {
    let params = new HttpParams().set("mitarbeiterStatus", mitarbeiterStatus);
    return this.http.get<MitarbeiterStatus>(environment.API_URL+'/countMitarbeiterImEinsatz', {params: params});
  }

  public getDeckungsbeitrag(): Observable<string> {
    return this.http.get<string>(environment.API_URL+'/getDeckungsbeitrag');
  }

  public getDeckungsbeitragJahr(): Observable<String[]> {
    return this.http.get<String[]>(environment.API_URL+'/getDeckungsbeitragJahr');
  }

  public getMitarbeiterEinsatzDate(month: number): Observable<MitarbeiterEinsatzDate> {
    let params = new HttpParams().set("month", String(month));
    return this.http.get<MitarbeiterEinsatzDate>(environment.API_URL+'/getMitarbeiterEinsatzDate', {params: params});
  }

  public getMitarbeiterBank(): Observable<Mitarbeiter[]> {
    return this.http.get<Mitarbeiter[]>(environment.API_URL+'/listMitarbeiterBank');
  }

  public getMitarbeiterInternBank(): Observable<Mitarbeiter[]> {
    return this.http.get<Mitarbeiter[]>(environment.API_URL+'/listMitarbeiterInternBank');
  }

  public getMitarbeiterListOrderByName(): Observable<MitarbeiterDTO[]> {
    return this.http.get<MitarbeiterDTO[]>(environment.API_URL+'/getMitarbeiterListOrderByName');
  }

}
