import {Component} from '@angular/core';
import {TablesService} from "../../services/tables.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-config-header',
  templateUrl: './config-header.component.html',
  styleUrls: ['./config-header.component.css']
})
export class ConfigHeaderComponent {
  tableNumber: number = 1;
  seatNumber: number = 1;

  constructor(private tablesService: TablesService, private router: Router) {
  }

  updateCurrentConfig() {
    this.tablesService.updateCurrentConfig(this.tableNumber, this.seatNumber);
    this.router.navigate(['/']);
  }

}