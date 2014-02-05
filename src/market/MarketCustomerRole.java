package market;

import java.util.Random;
import java.util.concurrent.Semaphore;

import market.interfaces.MarketCustomer;
import application.Phonebook;
import application.gui.animation.agentGui.MarketCustomerGui;
import application.gui.animation.agentGui.RestaurantCustomerGui;
import person.Person;
import person.Role;
import testing.LoggedEvent;
import testing.EventLog;

public class MarketCustomerRole extends Role implements MarketCustomer {

	public EventLog log = new EventLog();

	MarketCustomerGui marketCustomerGui;
	Market myMarket;

	//Data
	public enum MarketCustomerState {atMarket, waitingForOrders, recievedOrders, payed, disputingBill, waitingToOpen}
	public MarketCustomerState state = MarketCustomerState.atMarket;
	private Semaphore atDestination = new Semaphore(0, true);
	private Market market;

	public double bill = 0;
	String item;
	int itemAmount;
	String name;

	public MarketCustomerRole(Person person, String pName, String rName) {
		super(person, pName, rName);
	}

	//Messages
	public void msgComeIn() {
		state = MarketCustomerState.atMarket;
		stateChanged();
	}

	public void msgHereAreYourThings(String item, int itemAmount, double orderCost) {
		state = MarketCustomerState.recievedOrders;
		this.item = item;
		this.itemAmount = itemAmount;
		bill = orderCost;
		log.add(new LoggedEvent("Recieved msgHereAreYourThings"));
		stateChanged();
	}

	public void msgAtDestination() {
		atDestination.release();
	}

	//Scheduler
	@Override
	public boolean pickAndExecuteAnAction() {
		if (state == MarketCustomerState.atMarket) {
			msgSalesPerson();
			return true;
		}
		if (state == MarketCustomerState.recievedOrders) {
			payBill();
			return true;
		}

		if (state == MarketCustomerState.payed) {
			exitMarket();
			return true;
		}

		return false;
	}

	//Actions
	public void msgSalesPerson() {
		if (person.home.type.equals("East Apartment"))
			myMarket = Phonebook.getPhonebook().getEastMarket();
		else
			myMarket = Phonebook.getPhonebook().getWestMarket();
		if(!myMarket.isOpen()) {
			print("Waiting for the market to open");
			state = MarketCustomerState.waitingToOpen;
			return;
		}

		marketCustomerGui.waitInLine();
		try {
			this.atDestination.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (item == "Car") {
			myMarket.salesPersonRole.msgIWantProducts(this, "Car", 1);
			print("Arrived at the market");
			state = MarketCustomerState.waitingForOrders;
			return;
		}
		item = chooseMarketItem();

		marketCustomerGui.DoGoToSalesPerson();
		try {
			this.atDestination.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		myMarket.salesPersonRole.msgIWantProducts(this, item, 3);
		print("Asking sales person for: " + item);
		state = MarketCustomerState.waitingForOrders;

	}

	private String chooseMarketItem() {
		Random rand = new Random();
		int myRandomChoice;
		String item;
		do {
			myRandomChoice = rand.nextInt(10);
			myRandomChoice %= 7;
		} while (!myMarket.marketItemsForSale.containsKey(myRandomChoice) || (person.money < myMarket.marketItemsForSale.get(myRandomChoice).price));
		item = myMarket.marketItemsForSale.get(myRandomChoice).itemName;
		return item;
	}

	public void payBill(){
		if (bill == myMarket.inventory.get(item).price * itemAmount) {
			print("Paying my bill");
			if (!test) {
				marketCustomerGui.DoGoToSalesPerson();
				try {
					this.atDestination.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myMarket.getSalesPerson(test).msgPayment(this, bill);
				person.money -= bill;
				state = MarketCustomerState.payed;
			}
			else {
				//message market that bill was wrong
				//for now leaving market
				exitMarket();
			}
		}
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void exitMarket() {
		print("Leaving Market");
		marketCustomerGui.DoExit();
		try {
			this.atDestination.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		state = MarketCustomerState.atMarket;
		person.hasFoodInFridge = true;
		market.removeCustomer(this);
		this.setRoleInactive();
	}

	public void setGui(MarketCustomerGui gui) {
		this.marketCustomerGui = gui;
	}

	public MarketCustomerGui getGui() {
		return marketCustomerGui;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

}
