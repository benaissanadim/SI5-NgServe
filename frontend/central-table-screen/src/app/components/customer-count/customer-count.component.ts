import { Component } from '@angular/core';
import {Router} from "@angular/router";
import { OrderService } from 'src/app/services/order.service';
import { TablesService } from 'src/app/services/tables.service';

@Component({
  selector: 'app-customer-count',
  templateUrl: './customer-count.component.html',
  styleUrls: ['./customer-count.component.css']
})
export class CustomerCountComponent {
  constructor(private tablesService: TablesService,private router: Router,
    private orderService :OrderService) { }

  validate(customerCount: number){
    console.log("here");
    this.tablesService.startOrder(customerCount).subscribe(
      result => {
        
        if(result == null){
        }
        else{
          this.orderService.updateCurrentOrderId(result.orderId);
          this.router.navigate(['/customer-ordering']);
        }
      },
      error => {
      }
    );

  }
}
