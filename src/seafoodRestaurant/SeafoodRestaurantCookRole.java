//package seafoodRestaurant;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import person.Role;
//import person.Worker;
////import seafoodRestaurant.interfaces.SeafoodRestaurantMarket;
//import seafoodRestaurant.interfaces.SeafoodRestaurantWaiter;
//import application.Phonebook;
//import application.Restaurant;
//import application.gui.animation.AnimationPanel;
//import seafoodRestaurant.SeafoodRestaurantOrder;
//import seafoodRestaurant.SeafoodRestaurantOrder.OrderState;
//
//public class SeafoodRestaurantCookRole extends Role implements Restaurant
//{
//	
//	/***** DATA *****/
//	public enum cookState
//	{Cooking, Plating, Ordering};
//	cookState S = cookState.Cooking;
//	
//	private class Food
//	{
//		public int amount;
//		public int low;
//		public boolean isLow = false;
//		//Hack
//		//Add in a max size
//		public int MaximumSize = 5;
//		public int OrderSize = 10;
//		public int cookingtimer;
//		public String type;
//		
//		public Food(String type1, int amount1, int low1, int cookingtimer1)
//		{
//			type = type1;
//			amount = amount1;
//			low = low1;
//			cookingtimer = cookingtimer1;
//		}
//		
//		public void UseFood()
//		{
//			amount--;
//		}
//		
//		public void AddFood(int number)
//		{
//			amount=+number;
//		}
//	}
//	
//	private AnimationPanel animPanel;
//	private Timer CookTimer  = new Timer();
//	private List <SeafoodRestaurantOrder> orders = Collections.synchronizedList(new ArrayList<SeafoodRestaurantOrder>());
//	//private List <SeafoodRestaurantMarket> markets = Collections.synchronizedList(new ArrayList<SeafoodRestaurantMarket>());
//	private Map<String, Integer> RecipeBook  = new HashMap<String, Integer>();
//	private Map<String, Food> FoodInventory = new HashMap<String, Food>();
//	private String name;
//	//private int CurrentMarket = 0;
//	
//	private SeafoodRestaurant restaurant;
////	private String name;
//	
//	public SeafoodRestaurantCookRole(String name, SeafoodRestaurant seafoodRestaurant)
//	{
//		super(name);
//		this.restaurant = seafoodRestaurant;
//		
//		RecipeBook.put("Clam Chowder Sourdough Bowl",3000);
//		RecipeBook.put("Grilled Shrimp Skewers",5000);
//		RecipeBook.put("Bourbon-Glazed Salmon",7000);
//		RecipeBook.put("Lobster Tail and Roll",8000);
//		
//		Food clamChowderSourdoughBowl = new Food("Clam Chowder Sourdough Bowl", 10, 1, 3000);
//		Food grilledShrimpSkewers = new Food("Grilled Shrimp Skewers", 10, 1, 5000);
//		Food bourbonGlazedSalmon = new Food("Bourbon-Glazed Salmon", 10, 1, 7000);
//		Food lobsterTailandRoll = new Food("Lobster Tail and Roll", 10, 1, 8000);
//		
//		FoodInventory.put("Clam Chowder Sourdough Bowl", clamChowderSourdoughBowl);
//		FoodInventory.put("Grilled Shrimp Skewers", grilledShrimpSkewers);
//		FoodInventory.put("Bourbon-Glazed Salmon", bourbonGlazedSalmon);
//		FoodInventory.put("Lobster Tail and Roll", lobsterTailandRoll);
//	}
//	
//	public String getName() 
//	{
//		return name;
//	}
//	
//	public void setAnimationPanel(AnimationPanel aP)
//	{
//		this.animPanel = aP;
//	}
//	
//	
//	/***** MESSAGES *****/
//	public void pleaseCook(String food, int table, SeafoodRestaurantWaiterRole w)
//	{
//		/////FILL IN
//		orders.add(new SeafoodRestaurantOrder(food, table, w));
//		stateChanged();
//	}
//	
//	public void WeCanSupply(String food, int quantity)
//	{
//		//STUB
//		//Fix, order from other market if necessary
//		if(quantity < FoodInventory.get(food).OrderSize)
//		{
//			//What to do here?
//			print("Looks like we have to order more.");
//			FoodInventory.get(food).OrderSize -= quantity;
//			S = cookState.Ordering;
//			stateChanged();
//		}
//		else
//		{
//			print ("Alright, cool. We don't have to order more.");
//			FoodInventory.get(food).OrderSize = 10;
//			FoodInventory.get(food).isLow = false;
//		}
//	}
//	
//	public void deliverFood(String food, int quantity)
//	{
//		//STUB
//		FoodInventory.get(food).amount += quantity;
//		print("Awesome, now I have " + FoodInventory.get(food).amount + " " + food );
//	}
//	
//	public void PickedUpOrder(String food)
//	{
//		//Need to implement how to remove string
//		//animPanel.removeFromPlated(food);
//	}
//	
//	
//	
//	
//	/***** SCHEDULER *****/
//	
//	public boolean pickAndExecuteAnAction() {
//		//////FILL IN HERE
//		
//		if(S == cookState.Ordering)
//		{
////			CurrentMarket++;
////			if(CurrentMarket == 3)
////			{
////				CurrentMarket = 0;
////			}
//			if(FoodInventory.get("Lobster Tail and Roll").isLow)
//			{
//				//CurrentMarket++;
//				OrderFood("Lobster Tail and Roll");
//				//return true;
//			}
//			if(FoodInventory.get("Bourbon-Glazed Salmon").isLow)
//			{
//				//CurrentMarket++;
//				OrderFood("Bourbon-Glazed Salmon");
//				//return true;
//			}
//			if(FoodInventory.get("Grilled Shrimp Skewers").isLow)
//			{
//				//CurrentMarket++;
//				OrderFood("Grilled Shrimp Skewers");
//				//return true;
//			}
//			if(FoodInventory.get("Clam Chowder Sourdough Bowl").isLow)
//			{
//				//CurrentMarket++;
//				OrderFood("Clam Chowder Sourdough Bowl");
//				//return true;
//			}
//			return true;
//		}
//		
//		if(!Phonebook.getPhonebook().getChineseRestaurant().getRevolvingStand().isStandEmpty()) {
//			takeRevolvingStandOrder();
//			return true;
//		}
//		
//		synchronized(orders)
//		{
//			for( SeafoodRestaurantOrder order : orders)
//			{
//				if(order.state == OrderState.Cooking && S == cookState.Plating)
//				{
//					PlateIt(order);
//					return true;
//				}
//				
//				if(order.state == OrderState.Pending)
//				{
//					CookIt(order);
//					return true;
//				}
//			}
//		}
//		
//		if (leaveRole)
//		{
//			((Worker) person).roleFinishedWork();
//			leaveRole = false;
//			return true;
//		}
//		
//		return false;
//		//we have tried all our rules and found
//		//nothing to do. So return false to main loop of abstract agent
//		//and wait.
//	}
//	
//	
//	/***** ACTIONS *****/
//	public void PlateIt(SeafoodRestaurantOrder o)
//	{
//		/////FILL IN HERE
//		S = cookState.Cooking;
//		DoPlate(o);
//		o.w.OrderIsReady(o.food, o.table);
//		print("Message 8 Sent, Food is Ready" + name);
//		/////Why is the print coming from a "AmericanRestaurantCookRole address"
//		o.state = OrderState.Finished;
//	}
//	
//	private void DoPlate(SeafoodRestaurantOrder o)
//	{
//		////Animate plate?
//		//need to add
//		//animPanel.addToPlated(o.food);
//	}
//	
//	public void CookIt(SeafoodRestaurantOrder o)
//	{
//		
//		//Out of Food
//		if(FoodInventory.get(o.food).amount == 0)
//		{
//			orders.remove(o);
//			print("We are out of " + o.food);
//			o.w.OutOfFood(o.table, o.food);
//			return;
//		}
//		
//		FoodInventory.get(o.food).UseFood();
//		print("Using " + o.food +", inventory is now " + FoodInventory.get(o.food).amount);
//		
//		//Check Food Inventory for low
//		if(FoodInventory.get(o.food).amount <= FoodInventory.get(o.food).low)
//		{
//			FoodInventory.get(o.food).isLow = true;
//			OrderFood(o.food);
//		}
//	
//		o.state = OrderState.Cooking;
//		//animPanel.addToCooking(o.food);
//		CookTimer.schedule(new TimerTask() 
//		{
//			public void run() 
//			{
//				//o.state = FoodState.Ready;
//				S = cookState.Plating;
//				stateChanged();
//			}
//		},
//		FoodInventory.get(o.food).cookingtimer);
//	}
//	
//	public void OrderFood(String foodItem)
//	{
//		S = cookState.Cooking;
//		print("We are low on " + foodItem + ". Let's order " + FoodInventory.get(foodItem).OrderSize + " more from " + Phonebook.getPhonebook().getEastMarket().getName() + "!");
//		//Implement a mechanism to choose between markets
//		Phonebook.getPhonebook().getEastMarket().salesPersonRole.msgIWantProducts(Phonebook.getPhonebook().getSeafoodRestaurant(), foodItem, FoodInventory.get(foodItem).OrderSize);
//	}
//	
//	private void takeRevolvingStandOrder()
//	{
//		print("Taking order from Revolving Stand.");
//		orders.add(Phonebook.getPhonebook().getSeafoodRestaurant().getRevolvingStand().takeOrder());
//	}
//	
////	public void addMarket(SeafoodRestaurantMarketRole m)
////	{
////		markets.add(m);
////	}
//		
//	public void setLow()
//	{
//		FoodInventory.get("Lobster Tail and Roll").amount = 2;
//		FoodInventory.get("Bourbon-Glazed Salmon").amount = 2;
//		FoodInventory.get("Grilled Shrimp Skewers").amount = 2;
//		FoodInventory.get("Clam Chowder Sourdough Bowl").amount = 2;
//	}
//	
//	public void setEmpty()
//	{
//		FoodInventory.get("Lobster Tail and Roll").amount = 0;
//		FoodInventory.get("Bourbon-Glazed Salmon").amount = 0;
//		FoodInventory.get("Grilled Shrimp Skewers").amount = 0;
//		FoodInventory.get("Clam Chowder Sourdough Bowl").amount = 0;	
//		FoodInventory.get("Lobster Tail and Roll").isLow = true;
//		OrderFood("Lobster Tail and Roll");
//		FoodInventory.get("Bourbon-Glazed Salmon").isLow = true;
//		OrderFood("Bourbon-Glazed Salmon");
//		FoodInventory.get("Grilled Shrimp Skewers").isLow = true;
//		OrderFood("Grilled Shrimp Skewers");
//		FoodInventory.get("Clam Chowder Sourdough Bowl").isLow = true;
//		OrderFood("Clam Chowder Sourdough Bowl");
//	}
//
//	@Override
//	public boolean isOpen() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//}
