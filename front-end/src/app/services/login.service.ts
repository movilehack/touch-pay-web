import { Injectable } from '@angular/core';
import { map, catchError } from 'rxjs/operators';
import { AuthenticationService } from './authentication.service';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Token } from '../models/token';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  public login(login: string, password: string): Observable<Token> {
    return this.http.post<Token>("api/authentication", {
      login: login,
      password: password
    }).pipe(
      map((t: Token) => {
        this.authService.token = t.token;
        this.authService.role = t.role
        return t;
      })
    )
  }
}