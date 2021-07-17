package csulb.cecs323.model;
import javax.persistence.*;
import java.util.*;

/**
 * The review class is a review of a recipe written by a food critic user on the website.  A food critic can write
 * many reviews, and each review has one and only one author.  A review refers to only one recipe.  If a food critic
 * wishes to write a new review on a recipe they have already reviewed, they can do so, and the new review will be
 * saved as an update to the old review (while the old review remains in the database).
 */
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"author_id", "recipeRated_id", "dateCompleted"}))
@Entity
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    @ManyToOne
    private FoodCritic author;

    @Column(nullable = false)
    private Date dateCompleted;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private float rating;
    
    //Recursive relationship
    @OneToOne
    private Review previous;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Recipe recipeRated;

    /**
     * Returns foodCritic who writes the review
     * @return foodCritic who writes the review
     */
    public FoodCritic getFoodCritic() { return author; }
    
    /**
     * Set foodCritic who writes the review
     * @param foodCritic who writes the review
     */
    public void setFoodCritic(FoodCritic foodCritic) { this.author = foodCritic; }

    /**
     * Returns dateCompleted the date that food critic writes the review
     * @return dateCompleted the date that food critic writes the review
     */
    public Date getDateCompleted() { return dateCompleted; }
    
    /**
     * Set dateCompleted the date that food critic writes the review
     * @param dateCompleted the date that food critic writes the review
     */
    public void setDateCompleted(Date dateCompleted) { this.dateCompleted = dateCompleted; }

    /**
     * Returns rating a ranking scale based on food quality
     * @return rating a ranking scale based on food quality
     */
    public float getRating() { return rating; }
    
    /**
     * Set rating a ranking scale based on food quality
     * @param rating a ranking scale based on food quality
     */
    public void setRating(float rating) { this.rating = rating; }

    /**
     * Returns previous the previous review
     * @return previous the previous review
     */
    public Review getPrevious() { return previous; }
    
    /**
     * Set previous the previous review
     * @param previous the previous review
     */
    public void setPrevious(Review previous) { this.previous = previous; }

    /**
     * Returns description of the review
     * @return description of the review
     */
    public String getDescription() {  return description; }
    
    /**
     * Set description of the review
     * @param description of the review
     */
    public void setDescription(String description) {  this.description = description; }

    /**
     * Returns id a number representing each review
     * @return id a number representing each review
     */
    public Long getId() { return id; }
    
    /**
     * Set id a number representing each review
     * @param id a number representing each review
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns recipe that food critic writes a review to
     * @return recipe that food critic writes a review to
     */
    public Recipe getRecipe() {
        return recipeRated;
    }
    
    /**
     * Set recipe that food critic writes a review to
     * @param recipe that food critic writes a review to
     */
    public void setRecipe(Recipe recipe) { this.recipeRated = recipe; }
}
