package POJO.Deserialization.Products;

import java.util.List;

public class ProductsListResponse {
    public List<ProductResponse> products;
    public int total;
    public int skip;
    public int limit;
} 