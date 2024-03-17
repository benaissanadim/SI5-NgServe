import {Component, ElementRef, QueryList, Renderer2, ViewChildren} from '@angular/core';
import {MenuService} from '../../services/menu.service';
import {Category, MenuItem} from "../../models/menu.interface";
import {CartService} from "../../services/cart.service";
import {ActivatedRoute, Router} from "@angular/router";

import {IngredientDialogComponent} from '../ingredient-dialog/ingredient-dialog.component';
import {MatDialog} from '@angular/material/dialog';
import {of} from 'rxjs';
import { CancelOrderPopupComponent } from '../cancel-order-popup/cancel-order-popup.component';

const BREAKPOINTS = {
  low: 600,
  tablet: 768,
  desktop: 1024,
  largeDesktop: 1280
};
@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent {
  categories: Category[] = []; //list of categories
  subCategories: Category[] = []; //list of categories


  gridItems: MenuItem[] = [];
  currentItems: MenuItem[] = [];

  secondPage = false;

  apiResponseData: any = null;
  currentPage: number = 1;
  itemsPerPage: number = 6;
  totalItems: number = 12;
  maxPages: number = Math.ceil(this.totalItems / this.itemsPerPage);
  displayedItems: (MenuItem)[] = [];
  @ViewChildren('item-name') itemNames!: QueryList<ElementRef>;

  currentCategory: Category = {} as Category;

  constructor(private renderer: Renderer2, private route: ActivatedRoute,private el: ElementRef, public dialog: MatDialog, private menuService: MenuService, private cartService: CartService, private router: Router) {
  }

  ngOnInit() {
   this.onInit();
   console.log(this.displayedItems);
  }

  onInit(){
    this.menuService.getCategories().subscribe((categories) => {
      this.categories = categories;
      this.currentCategory = categories[0];
      this.menuService.getMenuItems(this.currentCategory.name).subscribe((menuItems) => {
        this.gridItems = menuItems;
        this.totalItems = this.gridItems.length;
        this.initResponsive();
        this.filterAndCalculatePages();});

      console.log(this.currentCategory);
      of(this.currentCategory.menuItems).subscribe((Items) => {
        this.gridItems = Items;
        this.gridItems.sort((a, b) => a.fullName.localeCompare(b.fullName));
        if(this.gridItems!==null ){
          this.totalItems = this.gridItems.length;
          this.filterAndCalculatePages();

        }
      });
    });
  }

  back() {
    this.onInit();
    this.secondPage = false;
  }
    checkScrollingNames(): void {
      this.itemNames.forEach((itemRef: ElementRef) => {
        const el = itemRef.nativeElement;

        if (this.textOverflows(el)) {
          this.renderer.addClass(el, 'scrolling');
        } else {
          this.renderer.removeClass(el, 'scrolling');
        }
      });
    }

  changeCategory(category: Category) {
    if (category.subCategories !== null) {
      of(category.subCategories).subscribe((subCategories) => {
        this.categories = subCategories;
        this.currentCategory = subCategories[0];
        of( subCategories[0].menuItems).subscribe((Items) => {
          this.gridItems = Items;
          this.gridItems.sort((a, b) => a.fullName.localeCompare(b.fullName));
          if(this.gridItems!==null ){
            this.totalItems = this.gridItems.length;
            this.filterAndCalculatePages();
            setTimeout(() => {
              this.checkScrollingNames();
            });
          }
        });
      });
      this.secondPage = true;
    } else {
      const newCategory = this.categories.find((cat) => cat.name === category.name);
      if (newCategory) {
        this.currentCategory = newCategory;
        of(newCategory.menuItems).subscribe((Items) => {
          this.gridItems = Items;
          this.gridItems.sort((a, b) => a.fullName.localeCompare(b.fullName));
          if(this.gridItems!==null ){
            this.totalItems = this.gridItems.length;
            this.filterAndCalculatePages();
            setTimeout(() => {
              this.checkScrollingNames();
            });
          }
        });
        this.filterAndCalculatePages();
      }
    }
  }

  addItemToCart(menuItem: MenuItem) {
    this.cartService.addToCart(menuItem);
  }

  getItemCount() {
    return this.cartService.getItemCount();
  }

  showCart() {
    this.router.navigate(['/cart'],  { queryParams: { apiResponse: this.apiResponseData  } });
  }

  setCurrentCategoryType(categoryType: Category) {
    this.currentCategory = categoryType;
    this.filterAndCalculatePages();
  }

  private filterAndCalculatePages() {
    this.currentItems = this.gridItems;
    if(this.currentItems!==null ){
      this.totalItems = this.currentItems.length;
      this.maxPages = Math.ceil(this.totalItems / this.itemsPerPage);
      this.currentPage = 1;
      this.updateCurrentPageItems();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.maxPages) {
      this.currentPage++;
      this.updateCurrentPageItems();
    }
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updateCurrentPageItems();
    }
  }

   private updateCurrentPageItems() {
     const startIndex = (this.currentPage - 1) * this.itemsPerPage;
     const endIndex = startIndex + this.itemsPerPage;
     this.displayedItems = this.currentItems.slice(startIndex, endIndex);
     this.checkScrollingNames();
   }
  getInfo(item: MenuItem) {
    console.log('ingredientList', item.ingredientList);
    this.dialog.open(IngredientDialogComponent, {
      data: {ingredients: item.ingredientList}
    });
  }
   textOverflows(element: HTMLElement): boolean {
      const span = this.renderer.createElement('span');
      this.renderer.appendChild(document.body, span);
      this.renderer.setStyle(span, 'font', 'inherit');
      this.renderer.setStyle(span, 'white-space', 'nowrap');
      span.textContent = element.textContent || "";

      const textWidth = span.offsetWidth;
      this.renderer.setStyle(span, 'display', 'none');
      this.renderer.removeChild(document.body, span);

      const margin = 10;

      return textWidth > element.offsetWidth - margin;
    }

  cancelOrder() {
    const dialogRef = this.dialog.open(CancelOrderPopupComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'yes') {
        this.cartService.deleteCart();
        this.router.navigate(['/']);
          } else {
      }
    });
  
  }



  private initResponsive() {
    const lowMediaQuery = window.matchMedia(`(max-width: ${BREAKPOINTS.low}px)`);
    const tabletMediaQuery = window.matchMedia(`(min-width: ${BREAKPOINTS.tablet}px)`);
    const desktopMediaQuery = window.matchMedia(`(min-width: ${BREAKPOINTS.desktop}px)`);
    const largeDesktopMediaQuery = window.matchMedia(`(min-width: ${BREAKPOINTS.largeDesktop}px)`);

    lowMediaQuery.addEventListener('change', () => {
      this.adjustItemsPerPage(lowMediaQuery, tabletMediaQuery, desktopMediaQuery, largeDesktopMediaQuery);
    });
    tabletMediaQuery.addEventListener('change', () => {
      this.adjustItemsPerPage(lowMediaQuery, tabletMediaQuery, desktopMediaQuery, largeDesktopMediaQuery);
    });
    desktopMediaQuery.addEventListener('change', () => {
      this.adjustItemsPerPage(lowMediaQuery, tabletMediaQuery, desktopMediaQuery, largeDesktopMediaQuery);
    });
    largeDesktopMediaQuery.addEventListener('change', () => {
      this.adjustItemsPerPage(lowMediaQuery, tabletMediaQuery, desktopMediaQuery, largeDesktopMediaQuery);
    });

    this.adjustItemsPerPage(lowMediaQuery, tabletMediaQuery, desktopMediaQuery, largeDesktopMediaQuery);
  }

  private adjustItemsPerPage(lowMq: MediaQueryList, tabletMq: MediaQueryList, desktopMq: MediaQueryList, largeDesktopMq: MediaQueryList) {
    if (largeDesktopMq.matches) {
      this.itemsPerPage = 12;
    } else if (desktopMq.matches) {
      this.itemsPerPage = 10;
    } else if (tabletMq.matches) {
      this.itemsPerPage = 8;
    } else if (lowMq.matches) {
      this.itemsPerPage = 4;
      this.itemsPerPage = 2;
    } else {
      this.itemsPerPage = 6;
    }
    this.filterAndCalculatePages();
  }
}
