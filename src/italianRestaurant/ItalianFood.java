package italianRestaurant;


public class ItalianFood {
	public String type;
	public int cookingTime;
	public int low;
	public int inventory;
	public int capacity;
	public FoodState fs;
	public enum FoodState {inStock, isLow, ordered, orderingAgain, delivered};
	
	ItalianFood(String choice, int time, int invent, int lownum) {
		type = choice;
		cookingTime = time;
		inventory = invent;
		low = lownum;
		capacity = 20;
		fs = FoodState.inStock;
	}
	
	public String toString() {
		return type;
	}
}