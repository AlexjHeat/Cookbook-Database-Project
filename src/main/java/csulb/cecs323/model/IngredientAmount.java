package csulb.cecs323.model;
import javax.persistence.*;

/**
 * The IngredientAmount class is an association class to the Ingredient class. When all different ingredients are been
 * using for making a recipe, IngredientAmount tells how many should be used. Also, each ingredient may have different
 * amounts and units. It helps to understand what and how many ingredients are needed in a recipe.
 */
@Entity
@IdClass(IngredientAmountPK.class)
public class IngredientAmount {

    @JoinColumn(nullable = false)
    @Id
    @ManyToOne
    private Ingredient ingredient;

    @JoinColumn(nullable = false)
    @ManyToOne
    @Id
    private Recipe recipe;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private String units;
    
    /**
     * Returns a toString representation of an IngredientAmount object
     * @return this.ingredient.name \t this.amount this.units
     */
    @Override
    public String toString(){
        return ingredient.getName()+"\t"+amount+" "+units;
    }
    
    /**
     * Returns ingredient, a non-null string, name of the ingredient
     * @return ingredient name of the ingredient
     */
    public Ingredient getIngredient() { return ingredient; }

    /**
     * Set ingredient, a non-null string, name of the ingredient
     * @param ingredient name of the ingredient
     */
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Returns recipe, a description for making a dish
     * @return recipe description for making a dish
     */
    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * Set recipe, a description for making a dish
     * @param recipe description for making a dish
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * Returns amount of ingredient need to be used
     * @return amount of ingredient need to be used
     */
    public float getAmount() {
        return amount;
    }

    /**
     * Set amount of ingredient need to be used
     * @param amount of ingredient need to be used
     */
    public void setAmount(float amount) {
        this.amount = amount;
    }

    /**
     * Returns units of measurement used in cooking, such as teaspoon, cup, pint
     * @return units of measurement used in cooking
     */
    public String getUnits() {
        return units;
    }

    /**
     * Set units of measurement used in cooking, such as teaspoon, cup, pint
     * @param units of measurement used in cooking
     */
    public void setUnits(String units) {
        this.units = units;
    }

}