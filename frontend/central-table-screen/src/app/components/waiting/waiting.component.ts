import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService } from 'src/app/services/order.service';
import { interval, Subscription } from 'rxjs';
import { OrderPerPersonDTO } from 'src/app/models/OrderPerPersonDTO';

@Component({
  selector: 'app-waiting',
  templateUrl: './waiting.component.html',
  styleUrls: ['./waiting.component.css']
})
export class WaitingComponent implements OnInit {
  shouldBeReadyAt: string = '';
  ready :string = '';
  private opened: string = '';
  progressPercentage: number = 0;
  private timerSubscription!: Subscription;
  readyAt: string = '';
  ordersReady: OrderPerPersonDTO[] = [];
  interval : any;
  status = "ORDER_IN_PROGRESS"
  endTime : string = "";
  price: number = 0;

  getReadyAt() {
    this.readyAt = this.shouldBeReadyAt.split('T')[1].split('.')[0];

  }

  constructor(private router: Router, private orderService: OrderService) {}

  ngOnInit() {
    this.retrieveOrders();
    this.interval =interval(2500).subscribe(() => {
      this.retrieveOrders();
    });

    
    // Calculate initial progress percentage
    this.updateProgressPercentage();

    this.timerSubscription = interval(1000).subscribe(() => {
      this.updateProgressPercentage();
    });
  }

  ngOnDestroy() {
    // Unsubscribe from the timer when the component is destroyed
    this.timerSubscription.unsubscribe();
    this.interval.unsubscribe();
  }

  retrieveOrders() {
    this.orderService.retrieveOrdersReady().subscribe((ordersReady) => {
      this.ordersReady = ordersReady.orders;
      this.status = ordersReady.status;
      if(ordersReady.endCookingTime != null){
        this.endTime = ordersReady.endCookingTime.substring(0, 8);
        this.progressPercentage = 100;
        this.timerSubscription.unsubscribe();
      }
      this.price = 0;
      for(let i = 0; i < this.ordersReady.length; i++){
        this.price += this.ordersReady[i].price;
      }
      this.orderService.updateOrdersReady(this.ordersReady);
      this.orderService.updateTotal(this.price);
    });
  }

  private updateProgressPercentage() {
    this.shouldBeReadyAt = this.orderService.getShouldBeReadyAt();
    this.opened = this.orderService.getOpened();
    const now = new Date();
    const start = new Date(this.opened);
    const end = new Date(this.shouldBeReadyAt);
    start.setHours(start.getHours() + 1);
    end.setHours(end.getHours() + 1);

    const totalDuration = end.getTime() - start.getTime();
    const elapsed = now.getTime() - start.getTime();

    this.progressPercentage = (elapsed / totalDuration) * 100;
    this.ready = this.shouldBeReadyAt.substring(11, 19);
  }

  bill(){
    this.router.navigate(["/bill-splitting"]);
  }
}