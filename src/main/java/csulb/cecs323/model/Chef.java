package csulb.cecs323.model;
import javax.persistence.*;
import java.util.*;

/**
 *  Chefs are people who are experts in 1-to-many types of cuisine.
 *  Recipes are cooked by one and only one chef who may be responsible for creating
 *  many recipes. A chef will always be associated with at least one recipe they create.
 *  A chef is always a single user who may not also be a food critic.
 */
@Entity
@DiscriminatorValue("Chef")
public class Chef extends User {
    private int yearsOfExperience;

    @JoinColumn(nullable = false)
    @OneToMany (mappedBy = "chef")
    private Set<Recipe> recipesCreated;

    @JoinColumn(nullable = false)
    @ManyToMany
    @JoinTable(
            name ="cuisine_chef",
            joinColumns = @JoinColumn(name="chef", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="cuisine", referencedColumnName = "name"))
    private Set<Cuisine> cuisineExpertises;

    /**
     * Default constructor, initializes recipes and cuisines to new empty sets.
     */
    public Chef()
    {
        super();
        recipesCreated = new HashSet<Recipe>();
        cuisineExpertises = new HashSet<Cuisine>();
    }

    /**
     * Returns recipes name of the recipe
     * @return recipes name of the recipe
     */
    public Set<Recipe> getRecipes() {
        return recipesCreated;
    }
    
    /**
     * Set recipes name of the recipe
     * @param recipes name of the recipe
     */
    public void setRecipes(Set<Recipe> recipes) {
        this.recipesCreated = recipes;
    }

    /**
     * Returns cuisines name of the cuisines
     * @return cuisines name of the cuisines
     */
    public Set<Cuisine> getCuisines() {
        return cuisineExpertises;
    }
    
    /**
     * Set cuisines name of the cuisines
     * @param cuisines name of the cuisines
     */
    public void setCuisines(Set<Cuisine> cuisines) {
        this.cuisineExpertises = cuisines;
    }

    /**
     * Returns yearsOfExperience number of years the chef has been working
     * @return yearsOfExperience number of years the chef has been working
     */
    public int getYearsOfExperience() { return yearsOfExperience; }
    
    /**
     * Set yearsOfExperience number of years the chef has been working
     * @param yearsOfExperience number of years the chef has been working
     */
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    /**
     * Adds a recipe to this chef's list of recipes
     * @param recipe new recipe to be added
     */
    public void addRecipe(Recipe recipe) {this.recipesCreated.add(recipe); }

    /**
     * Adds a cuisine to this chef's list of cuisines
     * @param cuisine new cuisine to be added
     */
    public void addCuisine(Cuisine cuisine) {this.cuisineExpertises.add(cuisine); }
}
