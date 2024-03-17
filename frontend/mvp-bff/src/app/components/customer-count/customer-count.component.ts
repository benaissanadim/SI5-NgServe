import {Component} from '@angular/core';
import {TablesService} from "../../services/tables.service";
import {Router} from "@angular/router";
import {TableOrder} from "../../models/dining.interface";
import {Location} from "@angular/common";
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-customer-count',
  templateUrl: './customer-count.component.html',
  styleUrls: ['./customer-count.component.css']
})
export class CustomerCountComponent {
  selectedOption = 'take-away';

  tableNumber: any  = null ;

  constructor(private location: Location, private tablesService: TablesService, private router: Router,
    private snackBar: MatSnackBar
    ) {
  }

  selectOption(option: string) {
    this.selectedOption = option;
    sessionStorage.setItem('diningOption', option);
  }

  validate() {
    if(this.selectedOption === 'take-in' && this.tableNumber == null) {
      this.tablesService.reserveTable().subscribe(
        result => {
          console.log('API Response:', result);
          if(result == null){
          this.openSnackBar('no table is available' , 'Close');
          this.selectedOption = "take-away";
          }
          else{
            this.tableNumber = result.id;
            this.router.navigate(['/products'], { queryParams: { apiResponse: this.tableNumber  } });
          }
        },
        error => {
        }
      );
    }
    else{
      this.router.navigate(['/products']);
    }
  }

  back() {
    this.location.back();
  }

  confirmOrder() {
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000
    });
  }
}
