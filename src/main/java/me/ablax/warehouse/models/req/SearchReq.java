package me.ablax.warehouse.models.req;

import lombok.Data;
import me.ablax.warehouse.models.ProductCategory;

@Data
public class SearchReq {

    private String searchInput;
    private ProductCategory searchCategory;
    private String searchSku;


    public String toQuery() {
        return new StringBuilder().append("?searchInput=").append(searchInput).append("&searchCategory=").append(searchCategory.toString()).append("&searchSku=").append(searchSku).toString();
    }
}
