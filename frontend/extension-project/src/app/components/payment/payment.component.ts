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
      this.router.navigate(['/order-confirmed'], { queryParams: { apiResponse: this.apiResponseData  } });
    }, 1000);
    this.route.queryParams.subscribe(params => {
      if (params['apiResponse']) {
        this.apiResponseData = params['apiResponse'];
      }
    });
  }

}
