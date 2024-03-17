import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, of} from "rxjs";
import {MenuItem, OrderReady} from "../models/menu.interface";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {TablesService} from "./tables.service";
import { OrderPerPersonDTO, OrderTable } from '../models/OrderPerPersonDTO';
import { ResultValidation } from '../models/kitchen.interface';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private eventSource: EventSource;

  private orderId: string = "";

  private shouldBeReadyAt: string = "";
  private opened: string = "";

  private ordersReady: OrderPerPersonDTO[] = [];
  private total: number = 0;
  

  constructor(private http: HttpClient, private tablesService: TablesService) {
    this.eventSource = new EventSource(environment.bffApiUrl + `/tables/orders/${this.tablesService.getTableNumber()}`);

}
updateEvent(tableNumber: number) {
  this.eventSource.close();
  this.eventSource = new EventSource(environment.bffApiUrl + `/tables/orders/${tableNumber}`);

}

 retrieveOrdersReady(): Observable<OrderTable> {
   return new Observable<OrderTable>((observer) => {
     this.eventSource.onmessage = (event) => {
       try {
         const move = JSON.parse(event.data) as OrderTable;
         this.ordersReady = move.orders;
         observer.next(move);
       } catch (error) {
             console.log(error);

         observer.error(error);
       }
     };
     this.eventSource.onerror = (error) => {
      console.log(error);

       observer.error(error);
     };
   });
  }

  update(resultValidation: ResultValidation) {
    this.shouldBeReadyAt = resultValidation.shouldBeReadyAt;
    this.opened = resultValidation.opened;
  }

  updateTotal(total: number) {
    this.total = total;
  }

  getTotal(): number {
    return this.total;
  }

  billAll(): Observable<any> {
    console.log('bill for all the group for table ', this.tablesService.getTableNumber());
    return this.http.post(environment.bffApiUrl + `/tables/bill-all/${this.tablesService.getTableNumber()}`, null);
  }

  bill(name : string): Observable<any> {
    console.log('bill for user ', name);
    return this.http.post(environment.bffApiUrl + `/tables/bill/${this.tablesService.getTableNumber()}/${name}`, null);
  }

  getOrdersReady(): OrderPerPersonDTO[] {

    return this.ordersReady;
  }

  updateOrdersReady(ordersReady: OrderPerPersonDTO[]) {
    this.ordersReady = ordersReady;
  }

  getShouldBeReadyAt(): string {
    return this.shouldBeReadyAt;
  }

  getOpened(): string {
    return this.opened;
  }

  updateCurrentOrderId(orderId: string) {
    this.orderId = orderId;
  }

  getCurrentOrderId(): string {
    return this.orderId;
  }

  validate() : Observable<any> {
    console.log('validate table ', this.tablesService.getTableNumber());
    return this.http.post( `http://localhost:4000/tables/validate/${this.tablesService.getTableNumber()}`, null);
  }


}
