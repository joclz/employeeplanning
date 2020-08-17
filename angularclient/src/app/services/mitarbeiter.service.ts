import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Mitarbeiter} from "../models/mitarbeiter";

@Injectable({
  providedIn: 'root'
})
export class MitarbeiterService {

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Mitarbeiter[]> {
    return this.http.get<Mitarbeiter[]>('http://localhost:8080/listMitarbeiter');
  }

  public save(mitarbeiter: Mitarbeiter) {
    return this.http.post<Mitarbeiter>('http://localhost:8080/addMitarbeiter', mitarbeiter);
  }

  public getLastEndDateForMitarbeiter(id: string) {
    let params = new HttpParams().set("mitarbeiterId", id);
    return this.http.get<string>('http://localhost:8080/getLastEndDateForMitarbeiter', {params: params});
  }

  public getChanceForMitarbeiter(id: string) {
    let params = new HttpParams().set("mitarbeiterId", id);
    return this.http.get<string>('http://localhost:8080/getChanceForMitarbeiter', {params: params});
  }

}
