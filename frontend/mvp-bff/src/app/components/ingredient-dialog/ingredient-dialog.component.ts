import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {Ingredient} from "../../models/menu.interface";

@Component({
  selector: 'app-ingredient-dialog',
  template: `
    <h2 mat-dialog-title>Ingredients</h2>
    <mat-dialog-content>
      <ul>
        <li *ngFor="let ingredient of data.ingredients">{{ ingredient.name }}</li>
      </ul>
    </mat-dialog-content>
    <mat-dialog-actions>
      <button mat-button mat-dialog-close>Close</button>
    </mat-dialog-actions>
  `
})
export class IngredientDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: { ingredients: Ingredient[] }) {
  }
}
