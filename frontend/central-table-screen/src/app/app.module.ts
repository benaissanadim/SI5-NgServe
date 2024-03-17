import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CustomerCountComponent} from './components/customer-count/customer-count.component';
import {MatButtonModule} from "@angular/material/button";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {OrdersPreparationComponent} from './components/orders-preparation/orders-preparation.component';
import {ConfigHeaderComponent} from './components/config-header/config-header.component';
import {FormsModule} from "@angular/forms";
import {GameComponent} from './components/game/game.component';
import {WaitingComponent} from './components/waiting/waiting.component';
import {HttpClientModule} from "@angular/common/http";
import {BillSplittingComponent} from './components/bill-splitting/bill-splitting.component';
import {MatIconModule} from "@angular/material/icon";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import { MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import { PaymentComponent } from './components/payment/payment.component';
import { OrderConfirmedComponent } from './order-confirmed-all/order-confirmed-all.component';

@NgModule({
  declarations: [
    AppComponent,
    CustomerCountComponent,
    OrdersPreparationComponent,
    ConfigHeaderComponent,
    GameComponent,
    WaitingComponent,
    BillSplittingComponent,
    PaymentComponent,
    OrderConfirmedComponent
  ],
  imports: [
    MatSnackBarModule,
    BrowserModule,
    AppRoutingModule,
    MatButtonModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    MatIconModule,
    MatProgressBarModule,
    MatProgressSpinnerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
