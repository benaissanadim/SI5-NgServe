import {Injectable} from '@angular/core';
import {HttpClient,HttpErrorResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import { catchError } from 'rxjs/operators';
import {of} from 'rxjs';
import {Category, MenuItem} from '../models/menu.interface';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  constructor(private http: HttpClient) {
  }

  getMenuItems(category: string): Observable<MenuItem[]> {
      return this.http.get<MenuItem[]>(`${environment.bffApiUrl}/menu/categories/${category}/items`)
        .pipe(
          catchError((error: HttpErrorResponse) => {
            if (error.status === 404) {
              console.log('Aucun élément de menu trouvé.');
              return of([] as MenuItem[]);
            }
            return of([] as MenuItem[]);

          })
        );
    }


  getCategories() : Observable<Category[]> {
    return this.http.get<Category[]>(`${environment.bffApiUrl}/menu/categories`);
  }

  getSubCategories(category : string) : Observable<Category[]> {
    return this.http.get<Category[]>(`${environment.bffApiUrl}/menu/categories/`+ category+ `/sub`);
  }

}
