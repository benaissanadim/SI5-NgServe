import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {StartScreenComponent} from "./components/start-screen/start-screen.component";
import {ProductsComponent} from "./components/products/products.component";
import {CustomerCountComponent} from "./components/customer-count/customer-count.component";
import {CartComponent} from "./components/cart/cart.component";
import {CancelOrderPopupComponent} from "./components/cancel-order-popup/cancel-order-popup.component";
import {PaymentComponent} from "./components/payment/payment.component";
import {OrderConfirmedComponent} from './components/order-confirmed/order-confirmed.component';
import {GameComponent} from "./components/game/game.component";
import {OrderInPreparationComponent} from "./components/order-in-preparation/order-in-preparation.component";
import {EnterNameComponent} from "./components/enter-name/enter-name.component";

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: StartScreenComponent},
  {path: 'enter-name', component: EnterNameComponent},
  {path: 'products', component: ProductsComponent},
  {path: 'customer-count', component: CustomerCountComponent},
  {path: 'cart', component: CartComponent},
  {path: 'cancel-order-popup', component: CancelOrderPopupComponent},
  {path: 'payment', component: PaymentComponent},
  {path: 'order-confirmed', component: OrderConfirmedComponent},
  {path: 'order-in-preparation', component: OrderInPreparationComponent},
  {path: 'game', component: GameComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
