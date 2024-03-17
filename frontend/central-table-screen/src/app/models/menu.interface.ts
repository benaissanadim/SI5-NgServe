export interface OrderReady {
  customerName: string;
  menuItems: {
      quantity: number;
      menuItem: MenuItem;
  }[];
}

export interface MenuItem {
  id: string;
  fullName: string;
  shortName: string;
  price: number;
  ingredientList: Ingredient[];
  image: string;
}

export interface Category {
  id: string;
  name: string;
  image: string;
  hasSubCategory: boolean;
}

export interface Ingredient {
  id: string;
  name: string;
}
