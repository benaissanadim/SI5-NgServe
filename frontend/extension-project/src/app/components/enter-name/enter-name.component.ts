import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {TablesService} from "../../services/tables.service";

@Component({
  selector: 'app-enter-name',
  templateUrl: './enter-name.component.html',
  styleUrls: ['./enter-name.component.css']
})
export class EnterNameComponent {
  customerName: string = '';

  constructor(private router: Router, private tablesService: TablesService) {
  }

  validate() {
    if (this.customerName.length == 0) {
      return;
    }
    this.tablesService.setCustomerName(this.customerName);
    this.router.navigate(['/products']);
  }
}