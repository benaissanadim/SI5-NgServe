import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {TableId, TableOrder} from "../models/dining.interface";

@Injectable({
  providedIn: 'root'
})
export class TablesService {

  private tableNumber: number = 1; //if you change the default value here, you must also change it in config-header.component.ts
  private seatNumber: number = 1; //same
  private customerName: string = '';

  constructor(private http: HttpClient) {
  }

  getTableNumber() {
    return this.tableNumber;
  }

  getSeatNumber() {
    return this.seatNumber;
  }

  reserveTable() {
    console.log("reserving table");
    return this.http.post<TableId>(`${environment.bffApiUrl}/tables/reserve`, null);
  }

  updateCurrentConfig(tableNumber: number, seatNumber: number) {
    console.log(`updating current config to table ${tableNumber}`);
    this.tableNumber = tableNumber;
    this.seatNumber = seatNumber;
  }

  setCustomerName(customerName: string) {
    this.customerName = customerName;
  }

  getCustomerName() {
    return this.customerName;
  }
}
