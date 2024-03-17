export interface OrderPerPersonDTO {
    name: string;
    items: ItemDTO[];
    price: number;
    billed: boolean;
  }

  export interface ItemDTO {
    id: string;
    shortName: string;
    howMany: number;
  }