package POJO.Serialization.Products;

import java.util.List;
import POJO.Deserialization.Products.Dimensions;

public class UpdateProductRequest {
    public String title;
    public String description;
    public String category;
    public Double price;
    public Double discountPercentage;
    public Double rating;
    public Integer stock;
    public List<String> tags;
    public String brand;
    public String sku;
    public Double weight;
    public Dimensions dimensions;
    public String warrantyInformation;
    public String shippingInformation;
    public String availabilityStatus;
    public String returnPolicy;
    public Integer minimumOrderQuantity;
    public String thumbnail;
    public List<String> images;
} 