package market.interfaces;

import market.MarketOrder;

public interface MarketRunner {

	//Messages
	public void msgHeresAnOrder(MarketOrder o);

	//Actions
	public void processOrder(MarketOrder o);
	
	public void decreaseInventoryBy(String item, int amount);
	
	public boolean isPresent();
}
