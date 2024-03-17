import {Component, OnInit} from '@angular/core';
import {CartService} from "../../services/cart.service";
import {Preparation} from "../../models/dining.interface";
import {ActivatedRoute, Router} from "@angular/router";
import { TablesService } from 'src/app/services/tables.service';

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

  constructor(private cartService: CartService, private router: Router,private route: ActivatedRoute,
    private tableService: TablesService) {
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.router.navigate(['/game']);
    }, 2000)
    this.cartService.confirmOrder(
      this.tableService.getTableNumber(),
      this.tableService.getCustomerName()
    ).subscribe(
      result => {
        this.apiResponseData = result;
      },
      error => {
        // Handle API errors
      }
    );
    this.cartService.deleteCart();

  }

}