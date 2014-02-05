package market.test.mock;

import market.MarketOrder;
import market.interfaces.MarketRunner;
import testing.EventLog;
import testing.LoggedEvent;
import testing.Mock;

public class MockMarketRunner extends Mock implements MarketRunner {

	public EventLog log = new EventLog();
	
	public MockMarketRunner(String name) {
		super(name);
	}

	//Messages
	public void msgHeresAnOrder(MarketOrder o) {
		log.add(new LoggedEvent("Recieved msgHeresAnOrder"));
	}


	//Actions
	public void processOrder(MarketOrder o) {
	}

	public void decreaseInventoryBy(String item, int amount) {
	}
	
	public boolean isPresent() {
		return false;
	}
}
