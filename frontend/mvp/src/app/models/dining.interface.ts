export interface TableCreationDTO {
    tableId: number;
}

export interface TableWithOrderDTO {
    number: number;
    taken: boolean;
    tableOrderId: string;
}

export interface StartOrderingDTO {
    tableId: number;
    customersCount: number;
}

export interface CookedItem {
    id: string;
    shortName: string;
}

export interface OrderingItem {
    id: string;
    shortName: string;
}

export interface OrderingLine {
    item: OrderingItem;
    howMany: number;
    sentForPreparation: boolean;
}

export interface Preparation {
    id: string;
    shouldBeReadyAt: string;
    preparedItems: CookedItem[];
}

export interface TableOrder {
    id: string;
    tableNumber: number;
    customersCount: number;
    opened: string;
    lines: OrderingLine[];
    preparations: Preparation[];
    billed: string;
}

export interface ItemDTO {
    id: string;
    shortName: string;
    howMany: number;
}
