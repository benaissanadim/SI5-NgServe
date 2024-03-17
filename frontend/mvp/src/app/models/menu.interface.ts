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
  menuItems: MenuItem[];
  subCategories : Category[];
}

export interface Ingredient {
  id: string;
  name: string;
}
