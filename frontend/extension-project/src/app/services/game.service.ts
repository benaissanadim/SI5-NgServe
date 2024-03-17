import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private http: HttpClient) { }

  paint(color: string, tableNumber: number, seatNumber: number) {
    return this.http.post(`${environment.bffApiUrl}/paint`, {
      color: color,
      tableNumber: tableNumber,
      seatNumber: seatNumber
    });
  }
}

