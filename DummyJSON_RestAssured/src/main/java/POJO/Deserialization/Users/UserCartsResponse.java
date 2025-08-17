package POJO.Deserialization.Users;

import lombok.Data;
import java.util.List;

@Data
public class UserCartsResponse {
    private List<Cart> carts;
    private int total;
    private int skip;
    private int limit;

    @Data
    public static class Cart {
        private int id;
        private List<Product> products;
        private double total;
        private double discountedTotal;
        private int userId;
        private int totalProducts;
        private int totalQuantity;

        @Data
        public static class Product {
            private int id;
            private String title;
            private double price;
            private int quantity;
            private double total;
            private double discountPercentage;
            private double discountedTotal;
            private String thumbnail;
        }
    }
} 