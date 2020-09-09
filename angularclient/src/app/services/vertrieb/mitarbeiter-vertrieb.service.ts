import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Mitarbeiter} from "../../models/mitarbeiter/mitarbeiter";
import {HttpClient, HttpParams} from "@angular/common/http";
import {MitarbeiterVertrieb} from "../../models/vertrieb/mitarbeiter-vertrieb";
import {MitarbeiterDTO} from "../../models/mitarbeiter/mitarbeiter-dto";

@Injectable({
  providedIn: 'root'
})
export class MitarbeiterVertriebService {

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<MitarbeiterVertrieb[]> {
    return this.http.get<Mitarbeiter[]>('http://localhost:8080/listMitarbeiterVertrieb');
  }

  public save(mitarbeiterVertrieb: MitarbeiterVertrieb) {
    return this.http.post<Mitarbeiter>('http://localhost:8080/addMitarbeiterVertrieb', mitarbeiterVertrieb);
  }

  public delete(id: string) {
    let params = new HttpParams().set("mitarbeiterVertriebId", id);
    return this.http.post<Mitarbeiter>('http://localhost:8080/deleteMitarbeiterVertrieb', '', {params: params});
  }

  public update(mitarbeiterVertrieb: MitarbeiterVertrieb) {
    return this.http.post<Mitarbeiter>('http://localhost:8080/updateMitarbeiterVertrieb', mitarbeiterVertrieb);
  }

  public getMitarbeiterVertriebListOrderByName(): Observable<MitarbeiterDTO[]> {
    return this.http.get<MitarbeiterDTO[]>('http://localhost:8080/getMitarbeiterVertriebListOrderByName');
  }
}
