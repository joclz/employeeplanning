import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Mitarbeiter} from "../../models/mitarbeiter/mitarbeiter";
import {HttpClient, HttpParams} from "@angular/common/http";
import {MitarbeiterVertrieb} from "../../models/vertrieb/mitarbeiter-vertrieb";
import {MitarbeiterDTO} from "../../models/mitarbeiter/mitarbeiter-dto";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class MitarbeiterVertriebService {

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<MitarbeiterVertrieb[]> {
    return this.http.get<Mitarbeiter[]>(environment.API_URL+'/listMitarbeiterVertrieb');
  }

  public save(mitarbeiterVertrieb: MitarbeiterVertrieb) {
    return this.http.post<Mitarbeiter>(environment.API_URL+'/addMitarbeiterVertrieb', mitarbeiterVertrieb);
  }

  public delete(id: string) {
    let params = new HttpParams().set("mitarbeiterVertriebId", id);
    return this.http.post<Mitarbeiter>(environment.API_URL+'/deleteMitarbeiterVertrieb', '', {params: params});
  }

  public update(mitarbeiterVertrieb: MitarbeiterVertrieb) {
    return this.http.post<Mitarbeiter>(environment.API_URL+'/updateMitarbeiterVertrieb', mitarbeiterVertrieb);
  }

  public getMitarbeiterVertriebListOrderByName(): Observable<MitarbeiterDTO[]> {
    return this.http.get<MitarbeiterDTO[]>(environment.API_URL+'/getMitarbeiterVertriebListOrderByName');
  }
}
