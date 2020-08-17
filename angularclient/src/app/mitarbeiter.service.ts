import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Mitarbeiter} from "./mitarbeiter";

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

}
