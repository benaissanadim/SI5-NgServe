import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Preparation} from "./models/model";


@Injectable({
  providedIn: 'root'
})
export class TableService {

  private readonly BASE_URL = 'http://localhost:3002';

  constructor(private http: HttpClient) {
  }

  getReadyToBeServedOrders(): Observable<Preparation[]> {
    return this.http.get<Preparation[]>(`${this.BASE_URL}/preparations?state=readyToBeServed`);
  }

  getPreparationStartedOrders(): Observable<Preparation[]> {
    return this.http.get<Preparation[]>(`${this.BASE_URL}/preparations?state=preparationStarted`);
  }

}
