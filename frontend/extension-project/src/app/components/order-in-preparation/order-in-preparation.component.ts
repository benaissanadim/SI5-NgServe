import {Component, Input, OnInit} from '@angular/core';
import {Preparation_Public} from "../../models/kitchen.interface";

@Component({
  selector: 'app-order-in-preparation',
  templateUrl: './order-in-preparation.component.html',
  styleUrls: ['./order-in-preparation.component.css']
})
export class OrderInPreparationComponent implements OnInit {

  @Input() preparation: Preparation_Public | undefined;

  constructor() {
  }

  ngOnInit(): void {
  }


  getProgressPercentage(): number {
    if (!this.preparation) {
      return 0;
    }
    const now = new Date();
    const start = new Date(this.preparation.shouldBeReadyAt);
    const end = this.preparation.completedAt ? new Date(this.preparation.completedAt) : now;

    const totalDuration = end.getTime() - start.getTime();
    const elapsed = now.getTime() - start.getTime();

    return (elapsed / totalDuration) * 100;
  }
}
