package market.test.mock;

import market.interfaces.MarketCustomer;
import testing.EventLog;
import testing.LoggedEvent;
import testing.Mock;

public class MockMarketCustomer extends Mock implements MarketCustomer {

	public EventLog log = new EventLog();

	public MockMarketCustomer(String name) {
		super(name);
	}

	//Messages
	public void msgHereAreYourThings(String item, int itemAmount, double orderCost) {
		log.add(new LoggedEvent("Recieved msgHereAreYourThings"));
	}

	//Actions
	public void payBill(){
	}
	
	public void exitMarket() {
	}

	@Override
	public void msgComeIn() {
		// TODO Auto-generated method stub
		
	}
}
