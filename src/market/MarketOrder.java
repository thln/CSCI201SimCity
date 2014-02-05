package market;

import italianRestaurant.ItalianRestaurant;
import market.interfaces.MarketCustomer;
//import seafoodRestaurant.SeafoodRestaurant;
//import americanRestaurant.AmericanRestaurant;
import chineseRestaurant.ChineseRestaurant;

public class MarketOrder {

	public enum orderState {open, processing, itemsFound, itemsDelivered, gaveToCustomer};
	MarketCustomer customer = null;
	boolean restaurant = false;
	ChineseRestaurant chineseRestaurant = null;
	ItalianRestaurant italianRestaurant = null;
//	SeafoodRestaurant seafoodRestaurant = null;
//	AmericanRestaurant americanRestaurant = null;
	
	String item;
	int itemAmountOrdered;
	int itemAmountFulfilled;
	double orderCost;
	public orderState state = orderState.open;

	public MarketOrder(MarketCustomer customer1, String item, int itemAmountOrdered) {
		this.customer = customer1;
		this.item = item;
		this.itemAmountOrdered = itemAmountOrdered;
	}

	public MarketOrder(ChineseRestaurant restaurant1, String item, int itemAmountOrdered) {
		this.chineseRestaurant = restaurant1;
		restaurant = true;
		this.item = item;
		this.itemAmountOrdered = itemAmountOrdered;
	}
	
//	public MarketOrder(SeafoodRestaurant restaurant1, String item, int itemAmountOrdered) {
//		this.seafoodRestaurant = restaurant1;
//		restaurant = true;
//		this.item = item;
//		this.itemAmountOrdered = itemAmountOrdered;
//	}
	
	public MarketOrder(ItalianRestaurant restaurant1, String item, int itemAmountOrdered) {
		this.italianRestaurant = restaurant1;
		restaurant = true;
		this.item = item;
		this.itemAmountOrdered = itemAmountOrdered;
	}
	
//	public MarketOrder(AmericanRestaurant restaurant1, String item, int itemAmountOrdered) {
//		this.americanRestaurant = restaurant1;
//		restaurant = true;
//		this.item = item;
//		this.itemAmountOrdered = itemAmountOrdered;
//	}

}
