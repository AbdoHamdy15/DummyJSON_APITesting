package POJO.Deserialization.Products;

import java.util.List;

public class ProductResponse {
    public int id;
    public String title;
    public String description;
    public String category;
    public double price;
    public double discountPercentage;
    public double rating;
    public int stock;
    public List<String> tags;
    public String brand;
    public String sku;
    public double weight;
    public Dimensions dimensions;
    public String warrantyInformation;
    public String shippingInformation;
    public String availabilityStatus;
    public List<Review> reviews;
    public String returnPolicy;
    public int minimumOrderQuantity;
    public Meta meta;
    public String thumbnail;
    public List<String> images;
} 