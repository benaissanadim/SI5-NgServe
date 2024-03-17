import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MenuItem} from "../models/menu.interface";
import {environment} from "../../environments/environment";
import {TableOrder, TableWithOrderDTO} from "../models/dining.interface";
import {catchError, forkJoin, mergeMap, Observable, tap, throwError} from "rxjs";
import { TablesService } from './tables.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartItemsWithCount: { item: MenuItem, count: number }[] = [];

  private orderId: string = "";

  constructor(private http: HttpClient) {
  }

  deleteCart() {
    this.cartItemsWithCount = [];
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

  confirmOrder(tableId: number , name : string) : Observable<any> {
    const transformedCartItems = this.cartItemsWithCount.map(cartItem => {
      return {
        id: cartItem.item.id,
        shortName: cartItem.item.shortName,
        howMany: cartItem.count
      };
    });

    const addToTable = {
      name : name,
      items: transformedCartItems,
      price : this.getTotalPrice(),
      billed : false
    }
    console.log("adding to table "+ tableId +" order with name " + name)
    return this.http.post(`${environment.bffApiUrl}/tables/addToTable/`+ tableId, addToTable);
  }

  cancelOrder(orderId: string) {
    console.log("canceling order")
    return this.http.post(`${environment.bffApiUrl}/tables/`+ orderId + '/leave', null);
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


}