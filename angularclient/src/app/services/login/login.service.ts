import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { finalize } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  authenticated = false;

  constructor(private http: HttpClient) { }

  authenticate(credentials, callback) {

    const headers = new HttpHeaders(credentials ? {
      authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});

    this.http.get(environment.API_URL + '/user', {headers: headers}).subscribe(response => {
      if (response['name']) {
        this.authenticated = true;
      } else {
        this.authenticated = false;
      }
      return callback && callback();
    });
  }

  logout() {
    this.http.post(environment.API_URL + '/logout', {}).pipe(finalize(() => {
      this.authenticated = false;
    })).subscribe();
  }

}
