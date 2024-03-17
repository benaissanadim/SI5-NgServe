import { Component } from '@angular/core';
import {GameService} from "../../services/game.service";
import {TablesService} from "../../services/tables.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent {
  color: string = '#000';

  constructor(private gameService: GameService, private tablesService: TablesService,
    private router: Router) {
  }

  paint() {
    this.gameService.paint(this.color, this.tablesService.getTableNumber(), this.tablesService.getSeatNumber()).subscribe( );
  }

  products(){
    this.router.navigate(['/products']);
  }

}
