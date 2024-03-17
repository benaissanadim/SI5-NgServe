import { Component, OnInit, ChangeDetectionStrategy  } from '@angular/core';
import {Router} from "@angular/router";
import {GameMove} from "../../models/game-move";
import {OrderService} from "../../services/order.service";
import { OrderPerPersonDTO } from 'src/app/models/OrderPerPersonDTO';
import { TablesService } from 'src/app/services/tables.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Preparation, ResultValidation } from 'src/app/models/kitchen.interface';
import { GameService } from 'src/app/services/game.service';


@Component({
  selector: 'app-orders-preparation',
  templateUrl: './orders-preparation.component.html',
  styleUrls: ['./orders-preparation.component.css']})
export class OrdersPreparationComponent implements OnInit {

  ordersReady: OrderPerPersonDTO[] = [];
  customerCount: number = 1;
  private interval: any; // Define the interval variable

  constructor(private router: Router, private orderService: OrderService, private tableService: TablesService
    ,private snackBar: MatSnackBar, private gameService: GameService) {
  }

  ngOnInit() {
    this.getCustomer();
    this.interval = setInterval(() => {
      this.retrieveOrders();
    }, 1000);
  }

  retrieveOrders() {
    this.orderService.retrieveOrdersReady().subscribe((ordersReady) => {
        this.ordersReady = ordersReady.orders;
  });
  }

  getCustomer(){
    this.customerCount = this.tableService.getCoustomerCount();
  }

  ngOnDestroy() {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

  everybodyReady() {
    if(this.ordersReady.length == this.customerCount && this.customerCount != 0){
      this.orderService.validate().subscribe((o) => {
        this.orderService.update(o);
      });
    this.gameService.updateEvent(this.tableService.getTableNumber());
    this.router.navigate(['/waiting']);

    }else{
      this.openSnackBar("Not everybody is ready", "OK");
    }
  }

    openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000
    });
}

}
