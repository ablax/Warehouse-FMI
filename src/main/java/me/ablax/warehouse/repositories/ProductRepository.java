package me.ablax.warehouse.repositories;

import me.ablax.warehouse.entities.ProductEntity;
import me.ablax.warehouse.models.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long>, CrudRepository<ProductEntity, Long> {

    Page<ProductEntity> findAllBySkuCodeContainsIgnoreCase(final String sku, Pageable pageable);

    Page<ProductEntity> findAllByProductCategoryAndProductNameContainsIgnoreCase(final ProductCategory category, final String name, Pageable pageable);
    Page<ProductEntity> findAllByProductNameContainsIgnoreCase(final String name, Pageable pageable);

}
