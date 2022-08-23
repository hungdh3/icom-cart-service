package com.icom.cart.entity;

public enum ItemState {
    ITEM_CREATED("ITEM_CREATED"),
    ITEM_VALIDATED("ITEM_VALIDATED"),
    ITEM_INVALIDATED("ITEM_INVALIDATED");

    private final String text;

    ItemState(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
