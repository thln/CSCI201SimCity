package italianRestaurant.test.mock;

import italianRestaurant.interfaces.ItalianMarket;

public class MockItalianCook {
	
	String name;
	boolean allowed;
	
	public MockItalianCook(String name) {
		this.name = name;
	}
	
	public void msgOrderDone(ItalianMarket market, String foodname, int foodamt) {
		
	}
	public void msgOutofChoice(ItalianMarket market, String foodname) {
		
	}
	
	public void setAllowedOrder(boolean allowed) {
		this.allowed = allowed;
	}
}
