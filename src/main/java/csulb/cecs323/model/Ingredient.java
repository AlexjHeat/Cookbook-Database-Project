package csulb.cecs323.model;
import javax.persistence.*;
import java.util.*;

/**
 *  Ingredients are individual food items that form the constitution of cuisines and recipes.
 *  A cuisine must be associated with at least a single ingredient, however,
 *  ingredients may belong to many or no cuisines. Ingredients may also belong to many or no recipes,
 *  and recipes may not have any ingredients at all. Ingredients are joined to recipes through
 *  a junction table that specifies how much of the ingredient is required for said recipe.
 */
@Entity
public class Ingredient {
    @Id
    private String name;

    private String type;

    private String description;

    @ManyToMany
    @JoinTable(
            name ="cuisine_ingredient",
            joinColumns = @JoinColumn(name="ingredient", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(name="cuisine", referencedColumnName = "name"))
    private Set<Cuisine> cuisinesInfluenced;

    @OneToMany(mappedBy = "ingredient")
    private Set<IngredientAmount> ingredientAmountPerRecipe;

    public Ingredient() {
        cuisinesInfluenced = new HashSet<Cuisine>();
        ingredientAmountPerRecipe = new HashSet<IngredientAmount>();
    }

    /**
     * Returns name which is the non-null string of the ingredient
     * @return name of the ingredient
     */
    public String getName() {
        return name;
    }

    /**
     * Set name which is the non-null string of the ingredient
     * @param name of the ingredient
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns type which is the non-null string of the type of the ingredient
     * @return type of the ingredient
     */
    public String getType() {
        return type;
    }

    /**
     * Set type which is the non-null string of the type of the ingredient
     * @param type of the ingredient
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns description which is the non-null string describing the ingredient
     * @return description of the ingredient
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description which is the non-null string describing the ingredient
     * @param description of the ingredient
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns cuisines that is contained in a set of cuisine
     * @return cuisines name of the cuisine
     */
    public Set<Cuisine> getCuisines() {
        return cuisinesInfluenced;
    }

    /**
     * Set cuisines that is contained in a set of cuisine
     * @param cuisines name of the cuisine
     */
    public void setCuisines(Set<Cuisine> cuisines) {
        this.cuisinesInfluenced = cuisines;
    }

    /**
     * Returns IngredientAmount a specific amount contained in a set of IngredientAmount
     * @return ingredientAmount for a cuisine
     */
    public Set<IngredientAmount> getIngredientAmount() {
        return ingredientAmountPerRecipe;
    }

    /**
     * Set IngredientAmount a specific amount contained in a set of IngredientAmount
     * @param ingredientAmount for a cuisine
     */
    public void setIngredientAmount(Set<IngredientAmount> ingredientAmount) { this.ingredientAmountPerRecipe = ingredientAmount; }

    /**
     * Adds a new connection to Recipe using ingredientAmount as the join table
     * @param ingredientAmount new connection to be added
     */
    public void addIngredientAmount(IngredientAmount ingredientAmount) {this.ingredientAmountPerRecipe.add(ingredientAmount); }

    /**
     * Adds a new cuisine
     * @param cuisine new connection to be added
     */
    public void addCuisine(Cuisine cuisine) {this.cuisinesInfluenced.add(cuisine); }

}
