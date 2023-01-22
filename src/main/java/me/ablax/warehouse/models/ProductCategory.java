package me.ablax.warehouse.models;

public enum ProductCategory {
    ALL("All", false), FOOD("Food"), OFFICE_SUPPLIES("Office supplies"), CONSTRUCTION_SUPPLIES("Construction supplies");

    private final String displayName;
    private boolean isVisible = true;

    ProductCategory(final String displayName) {
        this.displayName = displayName;
    }

    ProductCategory(final String displayName, final boolean isVisible) {
        this.displayName = displayName;
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public String getDisplayName() {
        return displayName;
    }
}
