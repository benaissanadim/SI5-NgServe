import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import { OrderService } from '../services/order.service';

@Component({
  selector: 'app-order-confirmed-all',
  templateUrl: './order-confirmed-all.component.html',
  styleUrls: ['./order-confirmed-all.component.css']
})
export class OrderConfirmedComponent implements OnInit {


  table : number = 0;
  nb : number = 0;

  total : number = this.orderService.getTotal();


  constructor(private router: Router,private route: ActivatedRoute,
    private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['nb']) {
        this.nb = params['nb'];
        this.orderService.bill(this.orderService.getOrdersReady()[this.nb].name).subscribe((o) => {
        });
        this.total = this.orderService.getOrdersReady()[this.nb].price;  
        if(this.nb < this.orderService.getOrdersReady().length -1){
        setTimeout(() => {
          this.nb++;
          this.router.navigate(['/person-details-payment'],  { queryParams: { nb: this.nb} });
        }, 2000);
      }
      }else {
        this.orderService.billAll().subscribe((o) => {
          this.orderService.getTotal();
        });
      }
    });

  }

}