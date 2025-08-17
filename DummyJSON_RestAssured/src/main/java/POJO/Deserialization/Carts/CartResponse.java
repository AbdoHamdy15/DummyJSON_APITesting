package POJO.Deserialization.Carts;

import java.util.List;

public class CartResponse {
    public int id;
    public List<CartProductResponse> products;
    public double total;
    public double discountedTotal;
    public int userId;
    public int totalProducts;
    public int totalQuantity;
} 