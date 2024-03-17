import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { of } from 'rxjs';


import { Category, Ingredient, MenuItem } from '../models/menu.interface';
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  private bffUrl = `${environment.bffApiUrl}/menu`;
  private menu: Category[]=[]; // Correction : Retirez "Category[]" car cela n'est pas nécessaire ici

  constructor(private http: HttpClient) {
  }

  getMenu(): Observable<Category[]> {
    console.log("[GET] Retrieving menu");
     return this.http.get<Category[]>(`${environment.menuApiUrl}/menu`)
        .pipe(
          tap(menuItems => this.menu = menuItems) // Stocke la réponse dans la propriété 'menu'
        );
  }

getMenuItems(category: string): Observable<MenuItem[]> {
    const selectedCategory = this.menu.find(cat => cat.id === category);

    if (selectedCategory) {
      return of(selectedCategory.menuItems);
    } else {
      return of([]); // Retourner un tableau vide si la catégorie n'est pas trouvée
    }
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${environment.menuApiUrl}/menu`);}
getSubCategories(category: string): Observable<Category[]> {
  return  of(this.getSubCategoriesByName(category, this.menu));
}
 getSubCategoriesByName(categoryName: string, categories: Category[]): Category[] {
     let subCategoriesByName: Category[] = [];

     for (const category of categories) {
       if (category.name === categoryName) {
         subCategoriesByName = category.subCategories;
       }

       if (category.subCategories.length > 0) {
         subCategoriesByName = subCategoriesByName.concat(this.getSubCategoriesByName(categoryName, category.subCategories));
       }
     }

     return subCategoriesByName;
   }



}
