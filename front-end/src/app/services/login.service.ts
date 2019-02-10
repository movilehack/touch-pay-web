import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { AuthenticationService } from './authentication.service';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  public login(login: string, password: string): Observable<string> {

    return this.http.post<string>('api/sign/in', {
      email: login,
      password
    }).pipe(
      map((t: string) => {
        this.authService.token = t;
        return t;
      })
    );
  }
}
