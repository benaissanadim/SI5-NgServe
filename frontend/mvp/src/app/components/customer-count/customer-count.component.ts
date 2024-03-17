import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {CartService} from "../../services/cart.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-customer-count',
  templateUrl: './customer-count.component.html',
  styleUrls: ['./customer-count.component.css']
})
export class CustomerCountComponent {
  numbers = [1, 2, 3, 4, 5, 6, 7, 8];
  selectedOption = 'take-in';

  constructor(private location: Location, private router: Router, private cartService: CartService, private snackBar: MatSnackBar) {
  }

  selectOption(option: string) {
    this.selectedOption = option;
    if (option === 'take-in') {
      this.cartService.setTakeAway(false);
    } else {
      this.cartService.setTakeAway(true)
    }
  }

  validate() {
    if (this.selectedOption === 'take-away') {
      this.cartService.createOrder();
      this.router.navigate(['/products']);
      return;
    }
    this.cartService.getFreeTable().subscribe(
      tableNumber => {
        this.cartService.createOrder();
        this.router.navigate(['/products']);
      },
      error => {
        console.error("Take-in not available:", error)
        this.snackBar.open("There is no table available", 'close', {
          duration: 2000
        });
      }
    )
  }

  back() {
    this.location.back();
  }
}
