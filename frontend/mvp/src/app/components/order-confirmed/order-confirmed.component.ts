import {Component} from '@angular/core';
import {CartService} from "../../services/cart.service";
import {Preparation} from "../../models/dining.interface";
import {Router} from "@angular/router";

@Component({
  selector: 'app-order-confirmed',
  templateUrl: './order-confirmed.component.html',
  styleUrls: ['./order-confirmed.component.css']
})
export class OrderConfirmedComponent {

  preparations: Preparation[] | null = null;

  orderError: boolean = false;

  orderId : string = "";

  constructor(private cartService: CartService, private router: Router) {}

  ngOnInit(): void {
    setTimeout(() => {
      this.router.navigate(['/home']);
    }, 10000)
    this.cartService.confirmOrder().subscribe(
      preparations => {
        this.preparations = preparations;
        this.orderId = this.cartService.gerOrderId();
        if (this.cartService.getTakeAway()) {
          this.liberateTable();
        }
      },
      error => {
        console.error("Error during order confirmation:", error)
        this.orderError = true;
        this.liberateTable();
      }
    );
    this.cartService.deleteCart();
  }

  uniqueItems(items: any[]): any[] {
    const uniqueSet = new Set(items.map(item => item.shortName));
    return [...uniqueSet];
  }

  liberateTable() {
    // only if table number > 100
    if (this.cartService.getTableNumber() < 100) {
      return;
    }

    this.cartService.billAndLiberateTable().subscribe(
      success => console.log("Table billed and liberated"),
      error => console.error("Error during billing and liberation:", error)
    )
  }

  isTakeAway() {
    return this.cartService.getTakeAway();
  }

  getTableNumber() {
    return this.cartService.getTableNumber();
  }

}
