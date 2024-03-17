import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent {

  apiResponseData: any = null;

  constructor(private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    setTimeout(() => {
      this.route.queryParams.subscribe(params => {
        if (params['nb']) {
          this.router.navigate(['/order-paid'], {queryParams: {nb: params['nb']}});
        }
        else{
          this.router.navigate(['/order-paid']);
        }
      });
    }, 2000);
  }

}
