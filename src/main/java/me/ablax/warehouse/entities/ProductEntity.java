package me.ablax.warehouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import me.ablax.warehouse.models.ProductCategory;

@Entity
@Table
public class ProductEntity extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String productName;
    @Column(length = 2000)
    private String productDescription;
    @Column
    private String picture;
    @Column(nullable = false)
    private Double priceBuy;
    @Column(nullable = false)
    private Double priceSell;
    @Column(nullable = false)
    private Integer productCount;
    @Column(nullable = false)
    private ProductCategory productCategory;
    @Column(nullable = false, unique = true)
    private String skuCode;

    public ProductEntity(final String productName, final String productDescription, final String picture, final Double priceBuy, final Double priceSell, final Integer productCount, final ProductCategory productCategory, final String skuCode) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.picture = picture;
        this.priceBuy = priceBuy;
        this.priceSell = priceSell;
        this.productCount = productCount;
        this.productCategory = productCategory;
        this.skuCode = skuCode;
    }

    public ProductEntity() {
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(final String picture) {
        this.picture = picture;
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
