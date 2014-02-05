package market.interfaces;

import market.MarketOrder;

public interface UPSman {

	//Messages
	public void msgDeliverOrder(MarketOrder o);

	//Actions
	public void deliverOrder(MarketOrder o);
}
