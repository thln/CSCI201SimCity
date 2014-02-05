package market.interfaces;

public interface MarketCustomer {

	//Messages
	public void msgHereAreYourThings(String item, int itemAmount, double orderCost);

	//Actions
	public void payBill();

	public void exitMarket();

	public void msgComeIn();

}
