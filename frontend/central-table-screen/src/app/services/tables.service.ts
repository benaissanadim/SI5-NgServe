import { HttpClient } from '@angular/common/http';
import {Injectable} from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TablesService {

  private tableNumber: number = 1; //if you change the default value here, you must also change it in config-header.component.ts
  private customerCount: number = 0;

  constructor(private http: HttpClient) {
  }

  getTableNumber(): number {
    return this.tableNumber;
  }

  updateCurrentConfig(tableNumber: number) {
    this.tableNumber = tableNumber;
  }

  updateCustomerCount(customerCount: number) {
    this.customerCount = customerCount;
  }

  startOrder(customerCount: number) : Observable<any>  {
    this.customerCount = customerCount;
    const dto = {
      tableId: this.tableNumber,
      customersCount: customerCount
    }
    console.log("starting order with count", customerCount + "for table", this.tableNumber + "..");
    return this.http.post<any>(environment.bffApiUrl + `/tables/start`, dto);
  }

  getCoustomerCount(): number {
    return this.customerCount;
  }
}
