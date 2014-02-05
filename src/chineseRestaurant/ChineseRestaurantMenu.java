package chineseRestaurant;

import java.util.Map;
import java.util.TreeMap;

public class ChineseRestaurantMenu {

	double lowestPricedItem = 5.99;
	
	public Map<Integer, FoodOption> menuMap = new TreeMap<Integer, FoodOption>(); {
		menuMap.put(0, new FoodOption("Chicken", 10.99));
		menuMap.put(1, new FoodOption("Steak", 15.99));
		menuMap.put(2, new FoodOption("Pizza", 8.99));
		menuMap.put(3, new FoodOption("Salad", 5.99));
	}

	
	ChineseRestaurantMenu remove(String choice) {
		if (choice == "Chicken")
			menuMap.remove(0);
		else if (choice == "Steak")
			menuMap.remove(1);
		else if (choice == "Pizza")
			menuMap.remove(2);
		else if (choice == "Salad") {
			menuMap.remove(3);
			lowestPricedItem = 8.99;
		}
		
		return this;
	}
	
	class FoodOption {
		String choice;
		double price;
		
		FoodOption(String choice, double price) {
			this.choice = choice;
			this.price = price;
		}
	}
}
