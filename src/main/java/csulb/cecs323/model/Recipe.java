package csulb.cecs323.model;
import javax.persistence.*;
import java.time.Duration;
import java.util.*;

/**
 * The recipe class is a instruction containing all steps for a chef to make cuisine. It shows step by step instructions
 * about what ingredients a chef should use and how much he should use, ingredientAmount. Each recipe has unique steps,
 * ingredients, and ingredient amount for each cuisine, and different chefs can all have access to the recipe.
 * Each cuisine can have many recipes. And a recipe will be reviewed by food critics.
 */
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "chef_id"}))
@Entity
public class Recipe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private Duration prepTime;
    
    @Column(nullable = false)
    private Duration cookTime;
    
    private Integer difficultyRating;
    
    @Column(nullable = false)
    private int numberOfServings;

    @OneToMany (mappedBy = "recipeRated", cascade = CascadeType.REMOVE)
    private Set<Review> reviews;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Chef chef;
    
    @JoinColumn(nullable = false)
    @OneToMany (mappedBy = "recipe", cascade = CascadeType.REMOVE)
    private Set<Step> steps;

    @OneToMany (mappedBy = "recipe", cascade = CascadeType.REMOVE)
    private Set<IngredientAmount> ingredientAmounts;

    @ManyToOne
    private Cuisine cuisineOrigin;
    
    /**
     * Creates a toString representation of a Recipe object by using data from all of its members.
     * Handles nullables using empty strings given by a ternary evaluation.
     * @return Format: name description difficulty serves prep cook chef cuisine ingredientList orderedStepList
     */
    @Override
    public String toString(){
        String ingredientAmountsString = "";
        String stepsString = "";
        
        for(IngredientAmount ia : ingredientAmounts){
            ingredientAmountsString = ingredientAmountsString+"\n"+ia.toString();
        }
        
        List<Step> stepList = new ArrayList(steps);
        stepList.sort(Comparator.comparingInt(Step::getOrderNumber));
        for(Step s : stepList){
            stepsString = stepsString + "\n" + s.getOrderNumber()+"\tTime: "+s.getTime()+"\n"+s.getDescription();
        }
        
        
        return name+"\n"+description+
               (difficultyRating == null ? "": "\nDifficulty: "+difficultyRating)+
               "\nServes: "+numberOfServings+
               "\nPrep Time: "+prepTime+
               "\nCook Time: "+cookTime+
               "\nChef: "+chef.getFirstName()+" "+chef.getLastName()+
               (cuisineOrigin == null ? "" : "\nCuisine: "+cuisineOrigin.getName())+
               (ingredientAmountsString.equals("") ? "" : "\nIngredients: "+ingredientAmountsString)+
               (stepsString.equals("") ? "" : "\nSteps: "+stepsString);
    }
    
    /**
     * Default constructor, initializes sets to new empty sets.
     */
    public Recipe() {
        reviews = new HashSet<Review>();
        steps = new HashSet<Step>();
        ingredientAmounts = new HashSet<IngredientAmount>();
    }

    /**
     * Returns id an unique identifier made for each recipe
     * @return id an unique identifier made for each recipe
     */
    public Long getId() { return id; }
    
    /**
     * Set id an unique identifier made for each recipe
     * @param id an unique identifier made for each recipe
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Returns name of the recipe
     * @return name of the recipe
     */
    public String getName() { return name; }
    
    /**
     * Set name of the recipe
     * @param name of the recipe
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns description for the recipe
     * @return description for the recipe
     */
    public String getDescription() { return description; }
    
    /**
     * Set description for the recipe
     * @param description for the recipe
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Returns prepTime needed to prepare the recipe
     * @return prepTime needed to prepare the recipe
     */
    public Duration getPrepTime() { return prepTime; }
    
    /**
     * Set prepTime needed to prepare the recipe
     * @param prepTime needed to prepare the recipe
     */
    public void setPrepTime(Duration prepTime) { this.prepTime = prepTime; }

    /**
     * Returns cookTime needed to cook the recipe
     * @return cookTime needed to cook the recipe
     */
    public Duration getCookTime() { return cookTime; }
    
    /**
     * Set cookTime needed to cook the recipe
     * @param cookTime needed to cook the recipe
     */
    public void setCookTime(Duration cookTime) { this.cookTime = cookTime; }

    /**
     * Returns difficultyRating a difficulty scale for making the recipe
     * @return difficultyRating a difficulty scale for making the recipe
     */
    public int getDifficultyRating() { return difficultyRating; }
    
    /**
     * Set difficultyRating a difficulty scale for making the recipe
     * @param difficultyRating a difficulty scale for making the recipe
     */
    public void setDifficultyRating(int difficultyRating) { this.difficultyRating = difficultyRating;  }

    /**
     * Returns numberOfServings the amounts of recipe are served
     * @return numberOfServings the amounts of recipe are served
     */
    public int getNumberOfServings() { return numberOfServings; }
    
    /**
     * Set numberOfServings the amounts of recipe are served
     * @param numberOfServings the amounts of recipe are served
     */
    public void setNumberOfServings(int numberOfServings) { this.numberOfServings = numberOfServings; }

    /**
     * Returns reviews for a recipe made by a food critic
     * @return reviews for a recipe made by a food critic
     */
    public Set<Review> getReviews() { return reviews; }
    
    /**
     * Set reviews for a recipe made by a food critic
     * @param reviews for a recipe made by a food critic
     */
    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Returns chef who creates the recipe
     * @return chef who creates the recipe
     */
    public Chef getChef() {
        return chef;
    }
    
    /**
     * Set chef who creates the recipe
     * @param chef who creates the recipe
     */
    public void setChef(Chef chef) {
        this.chef = chef;
    }

    /**
     * Returns steps different movements of making the recipe
     * @return steps different movements of making the recipe
     */
    public Set<Step> getSteps() {
        return steps;
    }
    
    /**
     * Set steps different movements of making the recipe
     * @param steps different movements of making the recipe
     */
    public void setSteps(Set<Step> steps) {
        this.steps = steps;
    }

    /**
     * Returns ingredientAmount amount of each ingredient needed in the recipe
     * @return ingredientAmount amount of each ingredient needed in the recipe
     */
    public Set<IngredientAmount> getIngredientAmount() {
        return ingredientAmounts;
    }
    
    /**
     * Set ingredientAmount amount of each ingredient needed in the recipe
     * @param ingredientAmount amount of each ingredient needed in the recipe
     */
    public void setIngredientAmount(Set<IngredientAmount> ingredientAmount) { this.ingredientAmounts = ingredientAmount; }

    /**
     * Adds a connection to ingredient using the join table ingredientAmount
     * @param ingredientAmount new connection to be added
     */
    public void addIngredientAmount(IngredientAmount ingredientAmount) {this.ingredientAmounts.add(ingredientAmount); }

    /**
     * Returns cuisine cooked by using the recipe
     * @return cuisine cooked by using the recipe
     */
    public Cuisine getCuisine() { return cuisineOrigin; }
    
    /**
     * Set cuisine cooked by using the recipe
     * @param cuisine cooked by using the recipe
     */
    public void setCuisine(Cuisine cuisine) { this.cuisineOrigin = cuisine; }
}
