import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderPerPersonDTO } from 'src/app/models/OrderPerPersonDTO';
import { OrderService } from 'src/app/services/order.service';
import { TablesService } from 'src/app/services/tables.service';

@Component({
  selector: 'person-details-payment',
  templateUrl: './person-details-payment.component.html',
  styleUrls: ['./person-details-payment.component.css']
})
export class PersonDetailsPaymentComponent {

  orderReady!: OrderPerPersonDTO;
  price: number = 0;
  nb : number = 0;

  constructor(private router: Router, private orderService: OrderService, private tableService: TablesService,
    private route: ActivatedRoute) {
  }

  
  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      if (params['nb']) {
        this.nb = params['nb'];
      }
    });
    this.orderReady =  this.orderService.getOrdersReady()[this.nb];
    console.log(this.orderReady);
  }

  pay() {
    this.router.navigate(['/payment'],  { queryParams: { nb: this.nb } });
  }

}