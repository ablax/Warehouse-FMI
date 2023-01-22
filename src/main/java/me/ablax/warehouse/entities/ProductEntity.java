package me.ablax.warehouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import me.ablax.warehouse.models.ProductCategory;

@Entity
@Table
@Data
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

}
