import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {TableId, TableOrder} from "../models/dining.interface";

@Injectable({
  providedIn: 'root'
})
export class TablesService {

  constructor(private http: HttpClient) {
  }

  setCustomerCount(count: number) {
    sessionStorage.setItem('customerCount', count.toString());
  }

  setTableNumber(tableNumber: number) {
    sessionStorage.setItem('tableNumber', tableNumber.toString());
  }

  takeTable() {
    console.log("taking table");
    return this.http.post<TableOrder>(`${environment.diningApiUrl}/tableOrders`, {//TODO: verify if the table is taken
      tableId: sessionStorage.getItem('tableNumber'),
      customersCount: sessionStorage.getItem('customerCount')
    });
  }

  reserveTable() {
    console.log("reserving table");
    return this.http.post<TableId>(`${environment.bffApiUrl}/tables/reserve`, null);

  }
}
