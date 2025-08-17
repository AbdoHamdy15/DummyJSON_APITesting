package POJO.Serialization.Recipes;

import lombok.Data;

@Data
public class Addrecipes {
    private String name ;
    private String ingredients;
    private String cuisine;
    private String difficulty;
    private int servings;
    private int cookTimeMinutes;
    private int prepTimeMinutes;
    private int caloriesPerServing;
}
