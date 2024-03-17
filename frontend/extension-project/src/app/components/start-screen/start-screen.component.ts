import {Component, HostListener} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-start-screen',
  templateUrl: './start-screen.component.html',
  styleUrls: ['./start-screen.component.css']
})
export class StartScreenComponent {

  constructor(private router: Router) {
  }

  @HostListener('document:touchstart', ['$event'])
  onTouchStart(event: TouchEvent) {
    this.router.navigate(['/enter-name']);
  }
}
