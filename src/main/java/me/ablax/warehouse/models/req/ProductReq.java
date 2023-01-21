package me.ablax.warehouse.models.req;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import me.ablax.warehouse.models.ProductCategory;

public class ProductReq {
    /* ▪ Наименование на продукт - текст с дължина не по-голяма от 50 символа.
Задължително поле.
        ▪ Описание на продукт - многоредово поле с възможност за въвеждане на текст
не по-дълъг от 2000 символа. Полето не е задължително.
        ▪ Поле за качване на файл - разрешава се да се качват само jpeg, jpg и png
файлове. Полето не е задължително.
        ▪ Цена на закупуване - цената, на която е бил закупен даденият продукт.
Задължително поле.
        ▪ Цена на продаване - продажната цена на продукта. Задължително поле.
        ▪ Брой продукти - Положително число или нула. Задължително поле.
        ▪ Категория: падащо меню с 3 опции: (Хранителни стоки, Канцеларски
материали, Строителни материали)
        ▪ Код - уникален код на продукт(необходимо е да има проверка за уникалност)*/


    @NotNull
    @Size(min = 1, max = 50)
    private String productName;
    @Nullable
    @Size(max = 2000)
    private String productDescription;
    @Nullable
    private String picture;
    @NotNull
    private Double priceBuy;
    @NotNull
    private Double priceSell;
    @NotNull
    @PositiveOrZero
    private Integer productCount;
    @NotNull
    private ProductCategory productCategory;
    @NotNull
    private String skuCode;

    public ProductReq() {
    }

    public ProductReq(final String productName, final String productDescription, final String picture, final Double priceBuy, final Double priceSell, final Integer productCount, final ProductCategory productCategory, final String skuCode) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.picture = picture;
        this.priceBuy = priceBuy;
        this.priceSell = priceSell;
        this.productCount = productCount;
        this.productCategory = productCategory;
        this.skuCode = skuCode;
    }

    @Override
    public String toString() {
        return "ProductReq{" +
                "productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", picture='" + picture + '\'' +
                ", priceBuy=" + priceBuy +
                ", priceSell=" + priceSell +
                ", productCount=" + productCount +
                ", productCategory=" + productCategory +
                ", skuCode='" + skuCode + '\'' +
                '}';
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(final String productDescription) {
        this.productDescription = productDescription;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(final String picture) {
        this.picture = picture;
    }

    public Double getPriceBuy() {
        return priceBuy;
    }

    public void setPriceBuy(final Double priceBuy) {
        this.priceBuy = priceBuy;
    }

    public Double getPriceSell() {
        return priceSell;
    }

    public void setPriceSell(final Double priceSell) {
        this.priceSell = priceSell;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(final Integer productCount) {
        this.productCount = productCount;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(final ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(final String skuCode) {
        this.skuCode = skuCode;
    }
}
