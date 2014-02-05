//package americanRestaurant;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.concurrent.Semaphore;
//
//import market.Market;
//import person.Person;
//import person.Role;
//import americanRestaurant.gui.AmericanCookGui;
//import americanRestaurant.interfaces.AmericanRestaurantCashier;
//import application.gui.animation.agentGui.Gui;
//
///**
// * Restaurant Cook Agent
// */
//
//public class AmericanRestaurantCookRole extends Role {
//
//	//DATA
//
//	//Revolving Stand timer to check stand
//	Timer revolvingStandTimer = new Timer();
//
//	AmericanCookGui cookGui;
//	List<Order> PendingOrders;
//	List<Order> FinishedOrders;
//	private List<Market> markets;
//	HashMap<String,Food> foodList;
//	boolean needToOrder;
//
//
//	AmericanRestaurant myRestaurant;
//
//	public Semaphore inProcess;
//
//	public static class Order {
//		String choice;
//		AmericanRestaurantWaiterRole waiter1;
//		Timer t1;
//		AmericanRestaurantTable tab;
//		int finishTime = 0;
//
//		Order(){
//			t1 = new Timer();
//		}
//
//		Order (String c1, AmericanRestaurantWaiterRole wait1, AmericanRestaurantTable tab1)
//		{
//			this.choice = c1;
//			this.waiter1 = wait1;
//			if (choice == "Steak")
//				this.finishTime = 5500;
//			if (choice == "Chicken")
//				this.finishTime = 5000;
//			if (choice == "Salad")
//				this.finishTime = 3500;
//			if (choice == "Pizza")
//				this.finishTime = 4500;	
//
//			t1 = new Timer();
//			this.tab = tab1;
//		}
//	}
//
//	public static class Food {
//		String choice;
//		int cookingTime;
//		int amount;
//		int capacity;
//		int amountOrdered;
//		int price;
//		Timer gatherFood;
//
//		Food (String c, int time, int amt, int amtOrd, int cap, int price) {
//			choice = c;
//			cookingTime = time;
//			amount = amt;
//			capacity = cap;
//			amountOrdered = amtOrd;
//			gatherFood = new Timer();
//			this.price = price;
//		}
//
//		void setAmount  (int amt) {
//			this.amount = amt;
//		}
//
//		String getChoice (){
//			return choice;
//		}
//	}
//
//
//	//Constructor
//	public AmericanRestaurantCookRole (String name, AmericanRestaurant restaurant) {
//		super(name);
//		PendingOrders = Collections.synchronizedList(new ArrayList<Order>());
//		FinishedOrders = Collections.synchronizedList(new ArrayList<Order>());	
//		foodList = new HashMap<>();
//		myRestaurant = restaurant;
//		foodList.put("Steak", new Food("Steak", 5000, 1, 3, 5, 5));
//		foodList.put("Chicken", new Food("Chicken", 4000, 3, 3, 5, 4));
//		foodList.put("Salad", new Food("Salad", 3000, 0, 3, 5, 2));
//		foodList.put("Pizza", new Food("Pizza", 3500, 4, 3, 5, 3));
//		markets = Collections.synchronizedList(new ArrayList<Market>());
//		startRevolvingStandTimer();
//		inProcess = new  Semaphore(0, true);
//		needToOrder = true;
//	}
//
//	public AmericanRestaurantCookRole(Person p1, String pName, String rName, AmericanRestaurant restaurant) {
//		super(p1, pName, rName);
//		PendingOrders = Collections.synchronizedList(new ArrayList<Order>());
//		FinishedOrders = Collections.synchronizedList(new ArrayList<Order>());	
//		foodList = new HashMap<>();
//		foodList.put("Steak", new Food("Steak", 5000, 1, 3, 5, 5));
//		foodList.put("Chicken", new Food("Chicken", 4000, 3, 3, 5, 4));
//		foodList.put("Salad", new Food("Salad", 3000, 0, 3, 5, 2));
//		foodList.put("Pizza", new Food("Pizza", 3500, 4, 3, 5, 3));
//		myRestaurant = restaurant;
//		inProcess = new  Semaphore(0, true);
//		startRevolvingStandTimer();
//		markets = Collections.synchronizedList(new ArrayList<Market>());
//	}
//
//	// GUI MESSAGES
//
//	public void msgAtCookingArea () {
//		inProcess.release();
//	}
//
//	public void msgAtPlatingArea () {
//		inProcess.release();
//	}
//
//	// MESSAGES
//
//	public void msgHereIsAnOrder (Order order1){	
//		print("Received order");
//		PendingOrders.add(order1);
//		stateChanged();
//	}
//
//	public void msgHereIsYourOrder(String food) {
//		Food f1 = foodList.get(food);
//		String choice = food;
//		foodList.put(choice, f1);
//		AmericanRestaurantWaiterRole.AddItem(f1.choice);
//		print("Bought more " + f1.choice);
//	}
//
//	//SCHEDULER
//
//	public boolean pickAndExecuteAnAction() {
//
//		if(!myRestaurant.getRevolvingStand().isStandEmpty()) {
//			takeRevolvingStandOrder();
//			return true;
//		}
//
//		synchronized(FinishedOrders) {
//			for (Order order1: FinishedOrders) {
//				FinishOrder(order1);
//				return false;
//			}
//		}
//
//		synchronized(PendingOrders) {
//			for (Order order1: PendingOrders) {
//				CookOrder(order1);
//				return false;
//			}
//		}
//
//		synchronized(markets) {
//			if (needToOrder) {
//				for (String key: foodList.keySet()) {
//					if (foodList.get(key).amount <= 1) {
//						ContactMarket(foodList.get(key));
//					}	
//				}
//				needToOrder = false;
//			}
//		}
//		return false;
//	}
//
//	// ACTIONS
//
//	void CookOrder (final Order order1){
//
//		PendingOrders.remove(order1);
//		Food f = foodList.get(order1.choice);
//		if (f.amount <= 1) {
//			needToOrder = true;	
//			stateChanged();
//		}
//
//		if (f.amount == 0) {
//			print("I'm out of food " + f.choice);
//			AmericanRestaurantWaiterRole.RemoveItem(f.choice);
//			order1.waiter1.msgOutOfFood(order1);		
//			return;
//		}
//
//		f.amount--;
//		print("Cooking " + order1.choice);
//
//		if (!(cookGui.getXPos() == cookGui.xFridgeArea && cookGui.getXPos() == cookGui.yFridgeArea)){
//			cookGui.DoGoToFridge();
//			try {
//				inProcess.acquire();			
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		order1.t1.schedule(new TimerTask() {		//Start timer
//			public void run() {	
//				FinishedOrders.add(order1);
//				stateChanged();
//			}
//		}, order1.finishTime);
//	}
//
//	void FinishOrder (Order order1){
//		print(order1.choice + " is finished");
//		order1.waiter1.msgOrderIsReady(order1.tab, order1.choice);
//		if (!(cookGui.getXPos() == cookGui.xPlatingArea && cookGui.getXPos() == cookGui.yPlatingArea)){
//			cookGui.DoGoPlate();
//			try {
//				inProcess.acquire();			
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}	
//		FinishedOrders.remove(order1);
//	}
//
//	void ContactMarket(Food f1) {
//		for (Market m1: getMarkets()) {
//			int marketAmount = m1.inventory.get(f1.choice).amount;
//			if (marketAmount >= f1.amountOrdered) {
//				print("Ordering " + f1.choice + " from market " + m1.getName());
//				m1.salesPersonRole.msgIWantProducts(myRestaurant, f1.choice, f1.amountOrdered);					//Order food and move on
//				break;
//			}
//			else if (marketAmount > 0) {	//If this market has more than one of the desired item
//				print("Ordering partial " + f1.choice + "  from market.");
//				f1.amountOrdered -= marketAmount;
//				m1.salesPersonRole.msgIWantProducts(myRestaurant, f1.choice, f1.amountOrdered);					//Order food and move on
//				//Order food and ask another market 
//			}
//			else {
//				print("Market empty, trying another");					//Otherwise, continue looping
//			}
//		}
//
//		return;
//	}
//
//	public void setGui(AmericanCookGui g) {
//		cookGui = g;
//	}
//	
//	public AmericanCookGui getGui() {
//		return cookGui;
//	}
//
//	public void setCashier(AmericanRestaurantCashier myCashier) {
//	}
//
//	private void takeRevolvingStandOrder()
//	{
//		print("Taking order from Revolving Stand.");
//		PendingOrders.add(myRestaurant.getRevolvingStand().takeOrder());
//	}
//
//	public void startRevolvingStandTimer() {
//		revolvingStandTimer.schedule(new TimerTask() {
//			public void run() {
//				if (person != null){
//					stateChanged();
//					startRevolvingStandTimer();
//				}
//			}
//		},
//		3000);
//	}
//
//	public List<Market> getMarkets() {
//		return markets;
//	}
//
//	public void setMarkets(List<Market> markets) {
//		this.markets = markets;
//	}
//}
//
