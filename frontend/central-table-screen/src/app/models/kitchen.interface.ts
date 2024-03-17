// Internal Interfaces
export interface ResultValidation {
    opened: string;
    shouldBeReadyAt: string;
    orderId: string;
}

export interface Recipe_Internal {
    shortName: string;
    post: string;
    cookingSteps: string[];
    meanCookingTimeInSec: number;
}

export interface PreparedItem_Internal {
    id: string;
    shortName: string;
    recipe: Recipe_Internal;
    shouldStartAt: string;
    startedAt: string;
    finishedAt: string;
}

// Public Interfaces
export interface PreparedItems {
    id: string;
    shortName: string;
}

export interface ItemsToBeCooked_Public {
    shortName: string;
    howMany: number;
}

export interface PreparationRequestDTO_Public {
    tableId: number;
    itemsToBeCookedList: ItemsToBeCooked_Public[];
}

export interface Preparation {
    id: string;
    tableId: number;
    shouldBeReadyAt: string;
    preparedItems: PreparedItems[];
}

export interface Recipe {
    shortName: string;
    post: string;
    cookingSteps: string[];
    meanCookingTimeInSec: number;
}
