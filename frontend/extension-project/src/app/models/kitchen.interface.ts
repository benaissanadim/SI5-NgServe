// Internal Interfaces
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
export interface PreparedItem_Public {
    id: string;
    shortName: string;
    shouldStartAt: string;
    startedAt: string;
    finishedAt: string;
}

export interface ItemsToBeCooked_Public {
    shortName: string;
    howMany: number;
}

export interface PreparationRequestDTO_Public {
    tableId: number;
    itemsToBeCookedList: ItemsToBeCooked_Public[];
}

export interface Preparation_Public {
    id: string;
    tableId: number;
    shouldBeReadyAt: string;
    completedAt: string;
    takenForServiceAt: string;
    preparedItems: PreparedItem_Public[];
}

export interface Recipe {
    shortName: string;
    post: string;
    cookingSteps: string[];
    meanCookingTimeInSec: number;
}
