import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { OrderPerPersonDTO } from 'src/app/models/OrderPerPersonDTO';
import { OrderService } from 'src/app/services/order.service';
import { TablesService } from 'src/app/services/tables.service';

@Component({
  selector: 'app-bill-splitting',
  templateUrl: './bill-splitting.component.html',
  styleUrls: ['./bill-splitting.component.css']
})
export class BillSplittingComponent {

  ordersReady: OrderPerPersonDTO[] = [];
  price: number = 0;

  constructor(private router: Router, private orderService: OrderService, private tableService: TablesService) {
  }

  retrieveOrders() {
    this.ordersReady = this.orderService.getOrdersReady();
    this.price = this.orderService.getTotal();
  }
  ngOnInit() {
    this.retrieveOrders();
  }


  all() {
    this.router.navigate(['/payment']);
  }

  one_by_one() {
    this.router.navigate(['/person-details-payment'],  { queryParams: { nb: 0 } });
  }

}