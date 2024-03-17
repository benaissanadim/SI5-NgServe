import {Component} from '@angular/core';
import {MenuService} from '../../services/menu.service';
import {Category, MenuItem} from "../../models/menu.interface";
import {CartService} from "../../services/cart.service";
import {Router, ActivatedRoute} from "@angular/router";
import { IngredientDialogComponent } from '../ingredient-dialog/ingredient-dialog.component';
import {MatDialog} from '@angular/material/dialog';
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

  currentCategory: Category = {} as Category; // Initialisation avec un objet vide

  constructor(
    public dialog: MatDialog,
    private menuService: MenuService, private cartService: CartService, private router: Router, 
    private route: ActivatedRoute) {
  }

  ngOnInit() {
   this.onInit();
  }

  onInit(){
    this.menuService.getCategories().subscribe((categories) => {
      this.categories = categories;
      this.currentCategory = categories[0];
      this.menuService.getMenuItems(this.currentCategory.name).subscribe((menuItems) => {
        this.gridItems = menuItems;
        this.totalItems = this.gridItems.length;
        this.initResponsive();
        this.filterAndCalculatePages();
      },
      error => {
      }
      );

    }, 
    error => {
      // Handle API errors
    }
    );
    this.route.queryParams.subscribe(params => {
      if (params['apiResponse']) {
        this.apiResponseData = params['apiResponse'];
      }
    });
  }

  back() {
    this.onInit();
    this.secondPage = false;
  }

  changeCategory(category: Category) {
    if (category.hasSubCategory) {
      this.menuService.getSubCategories(category.name).subscribe((subCategories) => {
        this.categories = subCategories;
        this.currentCategory = subCategories[0];
        this.menuService.getMenuItems(this.currentCategory.name).subscribe((menuItems) => {
          this.gridItems = menuItems;
          this.totalItems = this.gridItems.length;
          this.filterAndCalculatePages();
        });
      },
      error => {
        // Handle API errors
      }
      );
      this.secondPage = true;
    } else {
      const newCategory = this.categories.find((cat) => cat.name === category.name);
      if (newCategory) {
        this.currentCategory = newCategory;
        this.menuService.getMenuItems(this.currentCategory.name).subscribe((menuItems) => {
          this.gridItems = menuItems;
          this.totalItems = this.gridItems.length;
          this.filterAndCalculatePages();
        },  error => {
          // Handle API errors
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
    this.totalItems = this.currentItems.length;
    this.maxPages = Math.ceil(this.totalItems / this.itemsPerPage);
    this.currentPage = 1;
    this.updateCurrentPageItems();
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
  }

  getInfo(item: MenuItem) {
    this.dialog.open(IngredientDialogComponent, {
      data: {ingredients: item.ingredientList}
    });
  }

  cancelOrder() {
    const dialogRef = this.dialog.open(CancelOrderPopupComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'yes') {
        if(this.apiResponseData != null){
          this.cartService.cancelOrder(this.apiResponseData).subscribe();
          };
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