package me.ablax.warehouse.services;


import me.ablax.warehouse.entities.ProductEntity;
import me.ablax.warehouse.models.ProductCategory;
import me.ablax.warehouse.models.req.ProductReq;
import me.ablax.warehouse.models.req.SearchReq;
import me.ablax.warehouse.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;
import java.util.Collections;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createOrUpdateProduct(final ProductReq productReq) {
        final ProductEntity productEntity;

        if (productReq.getId() == null) {
            productEntity = new ProductEntity();
        } else {
            productEntity = productRepository.findById(productReq.getId()).get();
        }

        productEntity.setProductName(productReq.getProductName());
        productEntity.setProductDescription(productReq.getProductDescription());
        if (productReq.getPicture() != null) {
            productEntity.setPicture(productReq.getPicture());
        }
        productEntity.setPriceBuy(productReq.getPriceBuy());
        productEntity.setPriceSell(productReq.getPriceSell());
        productEntity.setProductCount(productReq.getProductCount());
        productEntity.setProductCategory(productReq.getProductCategory());
        productEntity.setSkuCode(productReq.getSkuCode());

        productRepository.save(productEntity);
    }

    public Page<ProductEntity> getAllProducts(final SearchReq searchReq, int page, int size) {
        if (!StringUtils.isEmpty(searchReq.getSearchSku())) {
            return productRepository.findAllBySkuCodeContainsIgnoreCase(searchReq.getSearchSku(), PageRequest.of(page, size));
        }
        if (searchReq.getSearchCategory() != null) {
            if (searchReq.getSearchCategory() == ProductCategory.ALL) {
                return productRepository.findAllByProductNameContainsIgnoreCase(searchReq.getSearchInput(), PageRequest.of(page, size));
            } else {
                return productRepository.findAllByProductCategoryAndProductNameContainsIgnoreCase(searchReq.getSearchCategory(), searchReq.getSearchInput(), PageRequest.of(page, size));
            }
        }
        return Page.empty();
    }


    public ProductReq getProduct(final String id) {
        return ProductReq.fromProductEntity(productRepository.findById(Long.parseLong(id)).orElse(new ProductEntity()));
    }

    public void deleteProduct(final String id) {
        productRepository.deleteById(Long.parseLong(id));
    }
}
