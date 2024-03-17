import {Component} from '@angular/core';
import {Location} from '@angular/common';
import {CartService} from "../../services/cart.service";
import {MenuItem} from "../../models/menu.interface";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  items = [
    {name: 'Item 1', price: 10.00, quantity: 1, imageUrl: 'item-image-url-1'},
    // ... autres items
  ];
  apiResponseData: any = null;


  constructor(
    private location: Location,
    private cartService: CartService,
    private snackBar: MatSnackBar,
    private router: Router, private route: ActivatedRoute
  ) {
  }



  ngOnInit(){
    this.route.queryParams.subscribe(params => {
      if (params['apiResponse']) {
        this.apiResponseData = params['apiResponse'];
        console.log(this.apiResponseData);
      }
    });
  }

  back() {
    this.location.back();
  }

  getItemCount() {
    return this.cartService.getItemCount();
  }

  getCartItemsWithCount() {
    return this.cartService.getCartItemsWithCount();
  }

  getTotalPrice() {
    return this.cartService.getTotalPrice();
  }

  decrement(item: MenuItem) {
    this.cartService.removeFromCart(item);
  }

  increment(item: MenuItem) {
    this.cartService.addToCart(item)
  }

  goToPayment() {
    if (this.getItemCount() === 0) {

      this.openSnackBar("Your cart is empty", "Fermer");
      return;
    }
    console.log(this.apiResponseData);
    this.router.navigate(['/payment'],  { queryParams: { apiResponse: this.apiResponseData  } });
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000
    });
  }
}
