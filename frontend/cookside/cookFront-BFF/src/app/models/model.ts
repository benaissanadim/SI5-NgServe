export interface TableOrder {
  orderId: string;
  items: ItemDTO[];
}

export interface TableId {
  id: string;
}

export interface ValidationPayment {
  status: string;
  amount: number;
}

export interface ItemDTO {
  itemId: string;
  name: string;
  price: number;
}

export interface Preparation {
  id: string;
  tableId: string;
  shouldBeReadyAt: string;
  completedAt: string;
}
