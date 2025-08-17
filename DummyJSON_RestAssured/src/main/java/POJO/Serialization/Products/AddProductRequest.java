package POJO.Serialization.Products;

import java.util.List;
import POJO.Deserialization.Products.Dimensions;

public class AddProductRequest {
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
    public String returnPolicy;
    public int minimumOrderQuantity;
    public String thumbnail;
    public List<String> images;
} 