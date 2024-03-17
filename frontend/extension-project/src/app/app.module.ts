import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {StartScreenComponent} from './components/start-screen/start-screen.component';
import {ProductsComponent} from './components/products/products.component';
import {HttpClientModule} from "@angular/common/http";
import {CustomerCountComponent} from './components/customer-count/customer-count.component';
import {CancelOrderPopupComponent} from './components/cancel-order-popup/cancel-order-popup.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule} from "@angular/material/button";
import {CartComponent} from './components/cart/cart.component';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {PaymentComponent} from './components/payment/payment.component';
import {IngredientDialogComponent} from './components/ingredient-dialog/ingredient-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {OrderConfirmedComponent} from './components/order-confirmed/order-confirmed.component';
import {ConfigHeaderComponent} from "./components/config-header/config-header.component";
import {GameComponent} from "./components/game/game.component";
import {OrderInPreparationComponent} from "./components/order-in-preparation/order-in-preparation.component";
import {FormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import { EnterNameComponent } from './components/enter-name/enter-name.component';

@NgModule({
  declarations: [
    AppComponent,
    StartScreenComponent,

    ProductsComponent,
    CustomerCountComponent,
    CartComponent,
    CancelOrderPopupComponent,
    IngredientDialogComponent,
    OrderConfirmedComponent,
    PaymentComponent,
    ConfigHeaderComponent,
    GameComponent,
    OrderInPreparationComponent,
    EnterNameComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatSnackBarModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    FormsModule,
    MatInputModule
  ],
  providers: [],
  exports: [
    ConfigHeaderComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
