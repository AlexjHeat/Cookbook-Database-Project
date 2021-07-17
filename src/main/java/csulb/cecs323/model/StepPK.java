package csulb.cecs323.model;
import java.io.Serializable;

/**
 *  This class allows Step to have a composite key which is a pairing of Recipe's FK
 *  and a step object / row's orderNumber instance variable / value.
 *
 *  CITATION: https://stackoverflow.com/a/18890592
 *  Followed steps illustrated by author of this post to implement a composite-key helper class.
 */
public class StepPK implements Serializable {
	private int orderNumber;
	private Long recipe;
	
	public StepPK(int orderNumber, Long recipeID){
		this.orderNumber = orderNumber;
		this.recipe = recipeID;
	}

	@Override
	public boolean equals(Object object){
		if( object instanceof StepPK){
			StepPK pk = (StepPK)object;
			return orderNumber == pk.orderNumber && recipe == pk.recipe;
		}
		else
			return false;
	}

	@Override
	public int hashCode() {
		int result = orderNumber;
		result = 31 * result + (int) (recipe ^ (recipe >>> 32));
		return result;
	}

	/**
	 * Set orderNumber, a number identifying an order placed by a user
	 * @param orderNumber, a number identifying an order placed by a user
	 */
	public void setOrderNumber(int orderNumber) { this.orderNumber = orderNumber; }
	
	/**
	 * Set recipe description for making the dish
	 * @param recipe description for making the dish
	 */
	public void setRecipe(Long recipe) {
		this.recipe = recipe;
	}
	
	/**
	 * Returns orderNumber, a number identifying an order placed by a user
	 * @return orderNumber, a number identifying an order placed by a user
	 */
	public int getOrderNumber() {
		return orderNumber;
	}
	
	/**
	 * Returns recipe description for making the dish
	 * @return recipe description for making the dish
	 */
	public long getRecipe() {
		return recipe;
	}
}
