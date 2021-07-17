package csulb.cecs323.model;
import javax.persistence.*;
import java.util.*;


/**
 *  Represents the amalgam of recipes offered by a culture of people.
 *  Chefs may or may not be experts in multiple types of cuisines
 *  that are constituted by common ingredients and a body of recipes.
 *  Many cultures may overlap with common recipes and ingredients,
 *  and no recipe or ingredient is bound by a single type of cuisine.
 */
@Entity
public class Cuisine {
    @Id
    private String name;

    private String region;

    private String religion;

    @ManyToMany(mappedBy = "cuisineExpertises")
    private Set<Chef> chefExperts;

    @JoinColumn(nullable = false)
    @ManyToMany(mappedBy = "cuisinesInfluenced", cascade = CascadeType.REMOVE)
    private Set<Ingredient> influencingIngredients;

    @OneToMany(mappedBy = "cuisineOrigin", cascade = CascadeType.REMOVE)
    Set<Recipe> recipesOf;

    /**
     * Default constructor, initializes ingredients and recipes to new empty sets.
     */
    public Cuisine()
    {
        influencingIngredients = new HashSet<Ingredient>();
        recipesOf = new HashSet<Recipe>();
    }

    /**
     * Returns name  of the cuisine
     * @return name of the cuisine
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of the cuisine
     * @param name of the cuisine
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns region of cuisine based upon national, state or local cuisine
     * @return region name of the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Set region of cuisine based upon national, state or local cuisine
     * @param region name of the region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Returns religion some of them have some food regulations, like Kosher
     * @return religion name of religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * Set religion some of them have some food regulations, like Kosher
     * @param religion name of the religion
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     * Returns chef who is expert in making the cuisine
     * @return chef name of chefs
     */
    public Set<Chef> getChef() {
        return chefExperts;
    }

    /**
     * Set chef who is expert in making the cuisine
     * @param chef name of the region
     */
    public void setChef(Set<Chef> chef) {
        this.chefExperts = chef;
    }

    /**
     * Returns ingredients from a set of ingredients
     * @return ingredients name of the ingredient
     */
    public Set<Ingredient> getIngredients() {
        return influencingIngredients;
    }

    /**
     * Set ingredients into a set of ingredients
     * @param ingredients name of the ingredient
     */
    public void setIngredients(Set<Ingredient> ingredients) {
        this.influencingIngredients = ingredients;
    }

    /**
     * Returns recipes, a description about how to make a dish, from a set of recipes
     * @return recipes name of the ingredient
     */
    public Set<Recipe> getRecipes() {
        return recipesOf;
    }

    /**
     * Set recipes into a set of recipes
     * @param recipes name of the region
     */
    public void setRecipes(Set<Recipe> recipes) {
        this.recipesOf = recipes;
    }
}