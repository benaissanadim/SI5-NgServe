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


  export interface OrderTable {
    orders: OrderPerPersonDTO[];
    nb : number;
    validated: boolean;
    orderId : string;
    status : string; 
    table : number;
    endCookingTime : string;
  }