import {Component, OnInit} from '@angular/core';
import {animate, style, transition, trigger} from "@angular/animations";
import {TableService} from "../table.service";
import {Preparation} from "../models/model";

@Component({
  selector: 'app-order-display',
  templateUrl: './order-display.component.html',
  styleUrls: ['./order-display.component.css'],
  animations: [
    trigger('fadeInAnimation', [
      transition(':enter', [
        style({opacity: 0}),
        animate('1s', style({opacity: 1}))
      ]),
    ])
  ]
})
export class OrderDisplayComponent implements OnInit {
  currentOrders: Preparation[] = [];
  preparingOrders: Preparation[] = [];
  statusMessage = '';

  constructor(private tableService: TableService) {
  }

  Number(value: string): number {
    return Number(value);
  }

  ngOnInit(): void {
    this.fetchReadyToBeServedOrders();
    this.fetchPreparationStartedOrders();
  }

  fetchReadyToBeServedOrders(): void {
    this.tableService.getReadyToBeServedOrders().subscribe(orders => {
      this.currentOrders = orders;
      console.log("current orders");
      console.log(this.currentOrders);
    }, error => {
      this.statusMessage = 'Error fetching ready-to-be-served orders.';
    });
  }

  fetchPreparationStartedOrders(): void {
    this.tableService.getPreparationStartedOrders().subscribe(orders => {
      this.preparingOrders = orders;
      console.log("preparing orders");
      console.log(this.preparingOrders);
    }, error => {
      this.statusMessage = 'Error fetching orders in preparation.';
    });
  }
}
