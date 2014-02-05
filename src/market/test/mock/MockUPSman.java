package market.test.mock;

import market.MarketOrder;
import market.interfaces.UPSman;
import testing.EventLog;
import testing.LoggedEvent;
import testing.Mock;

public class MockUPSman extends Mock implements UPSman {

	public EventLog log = new EventLog();
	
	public MockUPSman(String name) {
		super(name);
	}

	//Messages
	public void msgDeliverOrder(MarketOrder o) {
		log.add(new LoggedEvent("Recieved msgDeliverOrder"));
	}

	//Actions
	public void deliverOrder(MarketOrder o) {
	}
}
