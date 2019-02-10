import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Transaction } from '../models/transaction';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  constructor(private http: HttpClient) { }
  private page = 0;

  public loadTransactionNotRated(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`api/transaction?page=${++this.page}&pageSize=30`)
  }
}