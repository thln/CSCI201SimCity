//package restaurant;
//
//import agent.Agent;
//import interfaces.Cashier;
//import interfaces.Market;
//import restaurant.CookAgent.Food;
//import restaurant.gui.AnimationPanel;
//import restaurant.gui.WaiterGui;
//
//import java.util.*;
//import java.util.concurrent.Semaphore;
//
///**
// * Restaurant Cook Agent
// */
//
//public class MarketAgent extends Role implements Market {
//
//
//	//DATA
//	private HashMap <String, Food> inventory;
//	private List <Food> desiredFood;
//	private AmericanRestaurantCookRole CookCharles;
//	private AmericanRestaurantCashier myCashier;
//	final int gatherTime = 10000;
//	int bankAccount;
//
//	MarketAgent() {
//		inventory = new HashMap<>();
//		inventory.put("Steak", new Food("Steak", 3000, 0, 0, 50, 5));
//		inventory.put("Chicken", new Food("Chicken", 2500, 0, 0, 50, 4));
//		inventory.put("Salad", new Food("Salad", 1000, 0, 0, 50, 2));
//		inventory.put("Pizza", new Food("Pizza", 1500, 0, 0, 50, 3));
//		desiredFood = Collections.synchronizedList(new ArrayList<Food>());
//		bankAccount = 0;
//	}
//
//	void setInventory (String choice, int amt) {
//		inventory.get(choice).amount = amt;
//	}
//
//	HashMap<String,Food> getInventory (){
//		return inventory;
//	}
//
//	//MESSAGES
//
//	public void msgHereIsAnOrder(Food f1, AmericanRestaurantCookRole c1){
//		this.CookCharles = c1;
//		desiredFood.add(f1);
//		stateChanged();
//	}
//
//	public void msgHereIsPayment (int bill) {
//		Do("Received americanRestaurantCashier payment");
//		bankAccount += bill;
//	}
//
//	//SCHEDULER
//
//	public boolean pickAndExecuteAnAction() {
//
//		synchronized(desiredFood) {
//			for (Food f1: desiredFood) {
//				if (f1.amountOrdered > 0) {
//					StartTimer(f1);
//					return true;
//				}
//			}
//			return false;
//		}
//	}
//
//	//ACTIONS
//
//	private void StartTimer(Food f1) {
//		desiredFood.remove(f1);
//		final Food food1 = f1;
//		f1.gatherFood.schedule(new TimerTask() {		//Start timer
//			public void run() {	
//				SellFood(food1);
//			}
//		}, gatherTime);
//	}
//
//	public void SellFood(Food dFood) {				//dFood is desired food, iFood is inventory
//
//		int bill; 
//
//		Do("Selling Food");
//		Food iFood = inventory.get(dFood.choice);
//		if (iFood.amount >= dFood.amountOrdered) {
//			bill = dFood.amountOrdered*iFood.price;
//			dFood.amount = dFood.amountOrdered;
//			iFood.amount = iFood.amount - dFood.amountOrdered;
//			myCashier.msgPayMarketBill(this, bill);
//			CookCharles.msgHereIsYourOrder(dFood);
//			return;
//		}
//		if (iFood.amount < dFood.amountOrdered) {
//			bill = iFood.amount*iFood.price;
//			dFood.amount = iFood.amount;
//			dFood.amountOrdered = dFood.amountOrdered-iFood.amount;
//			iFood.amount = 0;
//			myCashier.msgPayMarketBill(this, bill);
//			CookCharles.msgHereIsPartOfYourOrder(dFood);
//			return;
//		}
//		return;
//	}
//
//	public void setCashier (AmericanRestaurantCashier cashier1) {
//		myCashier = cashier1;
//	}
//}
