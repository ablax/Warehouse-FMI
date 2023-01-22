package me.ablax.warehouse.repositories;

import me.ablax.warehouse.entities.ProductEntity;
import me.ablax.warehouse.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findAllBySkuCodeContainsIgnoreCase(final String sku);

    List<ProductEntity> findAllByProductCategoryAndProductNameContainsIgnoreCase(final ProductCategory category, final String name);
    List<ProductEntity> findAllByProductNameContainsIgnoreCase(final String name);

}
