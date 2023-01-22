package me.ablax.warehouse.models.req;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import me.ablax.warehouse.entities.ProductEntity;
import me.ablax.warehouse.models.ProductCategory;
import org.springframework.web.multipart.MultipartFile;

@Data
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

    private Long id;
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
    @Nullable
    private MultipartFile file;

    public static ProductReq fromProductEntity(final ProductEntity productEntity){
        final ProductReq productReq = new ProductReq();
        productReq.setId(productEntity.getId());
        productReq.setProductName(productEntity.getProductName());
        productReq.setProductDescription(productEntity.getProductDescription());
        productReq.setPicture(productEntity.getPicture());
        productReq.setPriceBuy(productEntity.getPriceBuy());
        productReq.setPriceSell(productEntity.getPriceSell());
        productReq.setProductCount(productEntity.getProductCount());
        productReq.setProductCategory(productEntity.getProductCategory());
        productReq.setSkuCode(productEntity.getSkuCode());
        return productReq;
    }

}
