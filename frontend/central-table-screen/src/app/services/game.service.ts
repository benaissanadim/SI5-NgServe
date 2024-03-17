import { GameMove } from "../models/game-move";
import { environment } from "../../environments/environment";
import { TablesService } from "./tables.service";
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private eventSource: EventSource;

  constructor(private tableService: TablesService) {
    const tableNumber = this.tableService.getTableNumber();
     this.eventSource = new EventSource(`http://localhost:4000/changes/${tableNumber}`);
  }

  updateEvent(tableNumber: number) {
    this.eventSource.close();
    this.eventSource = new EventSource(`http://localhost:4000/changes/${tableNumber}`);
  }

getMoves(): Observable<GameMove> {

    return new Observable<GameMove>((observer) => {
      this.eventSource.onmessage = (event) => {
        const move = JSON.parse(event.data) as GameMove;
        observer.next(move);
      };
      this.eventSource.onerror = (error) => {
        observer.error(error);
      };
    });
  }
}


