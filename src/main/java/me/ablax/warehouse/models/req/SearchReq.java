package me.ablax.warehouse.models.req;

import lombok.Data;
import me.ablax.warehouse.models.ProductCategory;

@Data
public class SearchReq {

    private String searchInput;
    private ProductCategory searchCategory;
    private String searchSku;
    private int page;
    private int size;

    public String toQuery() {
        if (searchCategory == null) return "?";
        return "?searchInput=" + searchInput + "&searchCategory=" + searchCategory + "&searchSku=" + searchSku + "&page=" + page + "&size=" + size;
    }

    public String toQuery(int newPage) {
        if (searchCategory == null) return "?";
        return "?searchInput=" + searchInput + "&searchCategory=" + searchCategory + "&searchSku=" + searchSku + "&page=" + newPage + "&size=" + size;
    }
}
