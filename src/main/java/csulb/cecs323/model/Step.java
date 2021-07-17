package csulb.cecs323.model;
import javax.persistence.*;
import java.time.Duration;

/**
 * The Step class is a series of steps to make a recipe and they are unique to that recipe. It's a instruction of how to
 * prepare the recipe. Each step will have different instruction descriptions and times for cooking a recipe. And steps
 * disappear when a recipe disappears.
 */
@Entity
@IdClass(StepPK.class)
public class Step {
	@Id
	private int orderNumber;

	@ManyToOne
	@Id
	@JoinColumn(nullable = false)
	private Recipe recipe;

	@Column(nullable = false)
	private String description;
	
	private Duration time;

	/**
	 * Returns orderNumber, a number identifying an order placed by a user
	 * @return orderNumber, a number identifying an order placed by a user
	 */
	public int getOrderNumber() {
		return orderNumber;
	}
	
	/**
	 * Set orderNumber, a number identifying an order placed by a user
	 * @param orderNumber, a number identifying an order placed by a user
	 */
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * Returns description for a recipe
	 * @return description for a recipe
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set description for a recipe
	 * @param description for a recipe
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns time needed to make the recipe
	 * @return time needed to make the recipe
	 */
	public Duration getTime() {
		return time;
	}
	/**
	 * Set time needed to make the recipe
	 * @param time needed to make the recipe
	 */
	public void setTime(Duration time) {
		this.time = time;
	}

	/**
	 * Returns recipe description for making the dish
	 * @return recipe description for making the dish
	 */
	public Recipe getRecipe() {
		return recipe;
	}
	
	/**
	 * Set recipe description for making the dish
	 * @param recipe description for making the dish
	 */
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
