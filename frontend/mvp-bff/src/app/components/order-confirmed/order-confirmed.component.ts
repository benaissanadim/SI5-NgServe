import {Component, OnInit} from '@angular/core';
import {CartService} from "../../services/cart.service";
import {Preparation} from "../../models/dining.interface";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-order-confirmed',
  templateUrl: './order-confirmed.component.html',
  styleUrls: ['./order-confirmed.component.css']
})
export class OrderConfirmedComponent implements OnInit {

  preparations: Preparation[] | null = null;

  orderError: boolean = false;
  apiResponseData: any ;
  table : number = 0;

  constructor(private cartService: CartService, private router: Router,private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['apiResponse']) {
        this.table = params['apiResponse'];
      }
    });
    setTimeout(() => {
      this.router.navigate(['/home']);
    }, 25000)
    if(this.table == 0){
    this.cartService.confirmOrderOut().subscribe(
      result => {    
        this.apiResponseData = result;
        console.log('API Response:', result);
      },
      error => {
        // Handle API errors
        console.error('catching ', error);
      }
    );
    } else if (this.table !== null && this.table !== undefined) {
      this.cartService.confirmOrderIn(this.table).subscribe(
        result => {    
          this.apiResponseData = result;
        },
        error => {
          console.info('API Error:', error);
        }
      );
    }

    this.cartService.deleteCart();

  }

}