import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MenuItem} from "../models/menu.interface";
import {environment} from "../../environments/environment";
import {Preparation, TableOrder, TableWithOrderDTO} from "../models/dining.interface";
import {catchError, forkJoin, map, mergeMap, Observable, of, switchMap, tap, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartItemsWithCount: { item: MenuItem, count: number }[] = [];

  private orderId: string = "";

  private tableNumber: number = 101; //default value for take-away

  private takeAway: boolean = false;

  constructor(private http: HttpClient) {
    console.log("Creating virtual tables 101 102 103 for take-away orders")
    this.http.post(`${environment.diningApiUrl}/tables`, {tableId: 101}).subscribe(
      success => console.log("Table 101 created"),
      error => console.log("Table 101 already exists")
    )
    this.http.post(`${environment.diningApiUrl}/tables`, {tableId: 102}).subscribe(
      success => console.log("Table 102 created"),
      error => console.log("Table 102 already exists")
    )
    this.http.post(`${environment.diningApiUrl}/tables`, {tableId: 103}).subscribe(
      success => console.log("Table 103 created"),
      error => console.log("Table 103 already exists")
    )
  }

  getOrderId() {
    return this.orderId;
  }

  setTakeAway(takeAway: boolean) {
    this.takeAway = takeAway;
  }

  gerOrderId() {
    return this.orderId;
  }

  getTakeAway() {
    return this.takeAway;
  }

  getTableNumber() {
    return this.tableNumber;
  }

  addToCart(menuItem: MenuItem) {
    const existingCartItem = this.cartItemsWithCount.find((cartItem) => cartItem.item.shortName === menuItem.shortName);
    if (existingCartItem) {
      existingCartItem.count++;
    } else {
      this.cartItemsWithCount.push({item: menuItem, count: 1});
    }
  }

  getItemCount() {
    let totalCount = 0;
    for (let cartItem of this.cartItemsWithCount) {
      totalCount += cartItem.count;
    }
    return totalCount;
  }

  getCartItemsWithCount() {
    return this.cartItemsWithCount;
  }

  getTotalPrice(): number {
    let totalPrice = 0;
    for (const cartItem of this.cartItemsWithCount) {
      totalPrice += cartItem.item.price * cartItem.count;
    }
    return totalPrice;
  }

  confirmOrder(): Observable<Preparation[]> {
    return this.addItemsToOrder(this.orderId).pipe(
      switchMap(() => {
        console.debug("Items added to order");
        console.log("[POST] Prepare order")
        return this.http.post<Preparation[]>(`${environment.diningApiUrl}/tableOrders/${this.orderId}/prepare`, {});
      }),
      catchError(error => {
        console.error("Error during items addition to order:", error);
        return throwError(error);
      })
    );
  }

  billAndLiberateTable() {
    console.log("[POST] Billing and liberating table")
    return this.http.post<TableOrder>(`${environment.diningApiUrl}/tableOrders/${this.orderId}/bill`, {});
  }

  getFreeTable(): Observable<number> {
    console.log("[GET] Getting free table")
    return this.http.get<TableWithOrderDTO[]>(`${environment.diningApiUrl}/tables`).pipe(
      map(tables => {
        const freeTable = tables.find(table => !table.taken && table.number < 100);
        if (freeTable) {
          this.tableNumber = freeTable.number;
          return freeTable.number;
        } else {
          throw new Error("No available table");
        }
      }),
      catchError(err => throwError(err.message || "An error occurred"))
    );
  }

  createOrder() {
    console.log(`[POST] Creating order for table ${this.tableNumber}`)
    this.http.post<TableOrder>(`${environment.diningApiUrl}/tableOrders`, {
      tableId: this.tableNumber,
      customersCount: 4
    }).subscribe(
      tableOrder => {
        console.log(`Order created for table ${this.tableNumber}: ${tableOrder.id}`);
        this.orderId = tableOrder.id;
      }
    );
  }

  addItemsToOrder(tableOrderId: string) {
    const postItemsObservables: Observable<any>[] = this.cartItemsWithCount.map(cartItemWithCount => {
      console.log(`[POST] Adding ${cartItemWithCount.count}x ${cartItemWithCount.item.shortName} to order`)
      return this.http.post(`${environment.diningApiUrl}/tableOrders/${tableOrderId}`, {
        "id": cartItemWithCount.item.id,
        "shortName": cartItemWithCount.item.shortName,
        "howMany": cartItemWithCount.count
      });
    });
    return forkJoin(postItemsObservables);
  }

  removeFromCart(item: MenuItem) {
    const existingCartItem = this.cartItemsWithCount.find((cartItem) => cartItem.item.shortName === item.shortName);
    if (existingCartItem) {
      existingCartItem.count--;
      if (existingCartItem.count === 0) {
        this.cartItemsWithCount = this.cartItemsWithCount.filter(cartItem => cartItem.item.shortName !== item.shortName);
      }
    }
  }

  deleteCart() {
    this.cartItemsWithCount = [];
  }

  cancelOrder() {
    this.billAndLiberateTable().subscribe(
      success => console.log("Table billed and liberated"),
      error => console.error("Error during billing and liberation:", error)
    )
    this.cartItemsWithCount = [];
    this.orderId = "";
    this.tableNumber = 101;
    this.takeAway = false;
  }
}
