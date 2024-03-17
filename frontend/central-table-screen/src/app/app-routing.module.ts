import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomerCountComponent} from "./components/customer-count/customer-count.component";
import {OrdersPreparationComponent} from "./components/orders-preparation/orders-preparation.component";
import {WaitingComponent} from "./components/waiting/waiting.component";
import {BillSplittingComponent} from "./components/bill-splitting/bill-splitting.component";
import { PaymentComponent } from './components/payment/payment.component';
import { OrderConfirmedComponent } from './order-confirmed-all/order-confirmed-all.component';
import { PersonDetailsPaymentComponent } from './person-details-payment/person-details-payment.component';
const routes: Routes = [
  {path: '', redirectTo: '/customer-count', pathMatch: 'full'},
  {path: 'customer-count', component: CustomerCountComponent},
  {path: 'customer-ordering', component: OrdersPreparationComponent},
  {path: 'waiting', component: WaitingComponent},
  {path: 'bill-splitting', component: BillSplittingComponent},
  {path: 'payment', component: PaymentComponent},
  {path: 'order-paid', component: OrderConfirmedComponent},
  {path: 'person-details-payment', component: PersonDetailsPaymentComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }