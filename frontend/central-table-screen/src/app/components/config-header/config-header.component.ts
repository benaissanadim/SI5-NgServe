import { Component } from '@angular/core';
import {TablesService} from "../../services/tables.service";
import {Router} from "@angular/router";
import { OrderService } from 'src/app/services/order.service';
import { GameService } from 'src/app/services/game.service';

@Component({
  selector: 'app-config-header',
  templateUrl: './config-header.component.html',
  styleUrls: ['./config-header.component.css']
})
export class ConfigHeaderComponent {
  tableNumber: number = 1; //if you change the default value here, you must also change it in config-header.component.ts

  constructor(private tablesService: TablesService, private orderService: OrderService, private router: Router) {
  }

  updateCurrentConfig() {
    this.tablesService.updateCurrentConfig(this.tableNumber);
    this.orderService.updateEvent(this.tableNumber);
    this.router.navigate(['/']);
  }

}
