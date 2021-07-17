package csulb.cecs323.model;
import java.io.Serializable;

/**
 *  This class allows IngredientAmount to have a composite key just like the Step class.
 *
 *  CITATION: https://stackoverflow.com/a/18890592
 *  Followed steps illustrated by author of this post to implement a composite-key helper class.
 */
public class IngredientAmountPK implements Serializable{
    
    private String ingredient;
    private Long recipe;
    
    public IngredientAmountPK(String ingredient, Long recipe) {
        this.ingredient = ingredient;
        this.recipe = recipe;
    }

    /**
     * Returns ingredient, a non-null string, name of the ingredient
     * @return ingredient name of the ingredient
     */
    public String getIngredient() { return ingredient; }

    /**
     * Set ingredient, a non-null string, name of the ingredient
     * @param ingredient name of the ingredient
     */
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Returns recipe, a description for making a dish
     * @return recipe description for making a dish
     */
    public Long getRecipe() {
        return recipe;
    }

    /**
     * Set recipe, a description for making a dish
     * @param recipe description for making a dish
     */
    public void setRecipe(Long recipe) { this.recipe = recipe; }
}
