package csulb.cecs323.model;
import javax.persistence.*;
import java.util.*;

/**
 * The FoodCritic class represents an individual user who reviews recipes.  It has the following relationships:
 * It is a subclass of User.
 * It has a one-to-many relationship with Review, which represents that FoodCritics could review multiple recipes.
 */
@Entity
@DiscriminatorValue("Critic")
public class FoodCritic extends User {
    private String currentPlatform;

    @OneToMany (mappedBy = "author", cascade = CascadeType.REMOVE)
    private Set<Review> reviewsSubmitted;

    /**
     * Default constructor, initializes sets to new empty sets.
     */
    public FoodCritic() {
        reviewsSubmitted = new HashSet<Review>();
    }
    
    /**
     * Returns currentPlatform a food-review platform
     * @return currentPlatform name of the platform
     */
    public String getCurrentPlatform() { return currentPlatform; }

    /**
     * Set currentPlatform a food-review platform
     * @param currentPlatform name of the platform
     */
    public void setCurrentPlatform(String currentPlatform) { this.currentPlatform = currentPlatform; }

    /**
     * Returns reviews, a food description, from a set of reviews
     * @return reviews of foods from a food critic
     */
    public Set<Review> getReviews() { return reviewsSubmitted; }

    /**
     * Add a new review from the food critic
     * @param review of foods
     */
    public void addReview(Review review) { this.reviewsSubmitted.add(review); }

    /**
     * Returns size number of food reviews
     * @return size number of food reviews
     */
    public int getNumberOfReviews()  {return reviewsSubmitted.size(); }

    /**
     * Set reviews, a food description, into a set of reviews
     * @param reviews of foods from a food critic
     */
    public void setReviews(Set<Review> reviews) {
        this.reviewsSubmitted = reviews;
    }
}
