//package seafoodRestaurant;
//import java.util.Random;
//import java.util.Map;
//import java.util.HashMap;
//
////import restaurant.CookAgent.Food;
//
////import restaurant.CookAgent.Food;
//
//public class SeafoodRestaurantMenu 
//{
//
//	
//	public String choices[] = {"Lobster Tail and Roll", "Bourbon-Glazed Salmon", "Clam Chowder Sourdough Bowl", "Grilled Shrimp Skewers"};
//	public double prices[] = {15.99, 10.99, 4.99, 8.99};
//	public int size = 4;
//	public class Food
//	{
//		public String type;
//		public boolean Available = true;
//		public double price;
//		
//		public Food(String name, double cost)
//		{
//			type = name;
//			price = cost;
//		}
//		
//		public void setAvailable()
//		{
//			Available = true;
//		}
//		
//		public void setUnavailable()
//		{
//			Available = false;
//		}
//	}
//	
//	public Map<String, Food> FoodMenu = new HashMap<String, Food>();
//	
//	
//	public SeafoodRestaurantMenu()
//	{
//		Food clamChowderSourdoughBowl = new Food("Clam Chowder Sourdough Bowl", 4.99);
//		Food grilledShrimpSkewers = new Food("Grilled Shrimp Skewers", 8.99);
//		Food bourbonGlazedSalmon = new Food("Bourbon-Glazed Salmon", 10.99);
//		Food lobsterTailandRoll = new Food("Lobster Tail and Roll", 15.99);
//		
//		FoodMenu.put("Clam Chowder Sourdough Bowl", clamChowderSourdoughBowl);
//		FoodMenu.put("Grilled Shrimp Skewers", grilledShrimpSkewers);
//		FoodMenu.put("Bourbon-Glazed Salmon", bourbonGlazedSalmon);
//		FoodMenu.put("Lobster Tail and Roll", lobsterTailandRoll);
//		
//	}
//	
//	public String blindPick()
//	{	
//		Random randomizer = new Random();
//		int randomNumber = randomizer.nextInt(size-1);
//		//return choices[(int)Math.random()%size];
//		if(FoodMenu.get(choices[randomNumber]).Available)
//		{
//			return choices[randomNumber];
//		}
//		else
//		{
//			randomNumber = randomizer.nextInt(size-1);
//			return choices[randomNumber];
//		}
//	}
//
//	public double GetPrice(String Choice)
//	{
//		double nothing = 0;
//		if(Choice.equals("Lobster Tail and Roll"))
//		{
//			return prices[0];
//		}
//		else if(Choice.equals("Bourbon-Glazed Salmon"))
//		{
//			return prices[1];
//		}
//		else if(Choice.equals("Clam Chowder Sourdough Bowl"))
//		{
//			return prices[2];
//		}
//		else if(Choice.equals("Grilled Shrimp Skewers"))
//		{
//			return prices[3];
//		}
//		
//		return nothing;
//	}
//}
