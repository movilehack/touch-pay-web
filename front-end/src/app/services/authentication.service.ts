import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor() { }

  public get role(): string {
    return sessionStorage.getItem('role')
  }

  public set role(value: string){
    sessionStorage.setItem('role', value)
  }

  public set token(value: string){
    sessionStorage.setItem('token', value)
  }

  public get token(): string {
    return sessionStorage.getItem('token')
  }

  public get isAuthenticated(): boolean {
    return this.token != null && this.token != undefined && this.token != '';
  }

  public logout(){
    localStorage.removeItem('token')
  }

}