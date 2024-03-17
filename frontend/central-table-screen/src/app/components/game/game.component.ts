import {Component, OnInit} from '@angular/core';
import {GameService} from "../../services/game.service";
import {GameMove} from "../../models/game-move";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {
  private context: CanvasRenderingContext2D | null = null;
  canvaWidth = 400;
  canvaHeight = 300;

  constructor(private gameService: GameService) {
  }
ngOnInit(): void {
  const canvas = document.getElementById("myCanvas") as HTMLCanvasElement;
  this.context = canvas.getContext("2d");

  this.gameService.getMoves().subscribe((moves: GameMove) => {
    if (!moves || !Array.isArray(moves)) {
      return;
    }
    moves.forEach((move: GameMove) => {
      this.placeTile(move.seatNumber, move.color);
    });
  });
}



  ngOnDestroy() {
  }

  placeTile(seatNumber: number, color: string) {
    if (this.context) {
      this.context.fillStyle = color;
      const x = Math.floor(Math.random() * this.canvaWidth);
      const y = Math.floor(Math.random() * this.canvaHeight);
      this.context.fillRect(x, y, 100, 100);
    }
  }
}
