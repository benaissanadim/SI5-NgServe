import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OrderDisplayComponent} from './order-display/order-display.component';

const routes: Routes = [
  {path: '', redirectTo: '/orders', pathMatch: 'full'},
  {path: 'orders', component: OrderDisplayComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
