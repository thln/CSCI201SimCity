package market;

import italianRestaurant.ItalianRestaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.Timer;

import market.MarketOrder.orderState;
import market.interfaces.MarketCustomer;
import market.interfaces.SalesPerson;
import person.Person;
import person.Role;
//import seafoodRestaurant.SeafoodRestaurant;
import testing.EventLog;
import testing.LoggedEvent;
//import americanRestaurant.AmericanRestaurant;
import application.Restaurant;
import application.gui.animation.agentGui.MarketSalesPersonGui;
import chineseRestaurant.ChineseRestaurant;

public class SalesPersonRole extends Role implements SalesPerson {

	Market market;

	public EventLog log = new EventLog();

	//Data
	public List<MarketOrder> orders = Collections.synchronizedList(new ArrayList<MarketOrder>());
	private Semaphore atDestination = new Semaphore(0, true);
	MarketSalesPersonGui salesPersonGui;

	//Constructors
	public SalesPersonRole(Person person, String pName, String rName, Market market) {
		super(person, pName, rName);
		this.market = market;
	}

	public SalesPersonRole(String roleName, Market market) {
		super(roleName);
		this.market = market;
	}



	//Messages
	public void msgIWantProducts(MarketCustomer customer, String item, int numWanted) {
		if (customer instanceof MarketCustomerRole) {
			print(((MarketCustomerRole) customer).person.getName() + " asked for " + numWanted + " " + item + "(s)");
		}
		log.add(new LoggedEvent("Recieved msgIWantProducts"));
		orders.add(new MarketOrder(customer, item, numWanted));
		stateChanged();
	}



	public void msgIWantProducts(ChineseRestaurant restaurant, String item, int numWanted) {
		print("Restaurant asked for " + numWanted + " " + item + "(s)");
		log.add(new LoggedEvent("Recieved msgIWantProducts"));
		orders.add(new MarketOrder(restaurant, item, numWanted));
		stateChanged();
	}

//	public void msgIWantProducts(SeafoodRestaurant restaurant, String item, int numWanted) {
//		print("Restaurant asked for " + numWanted + " " + item + "(s)");
//		log.add(new LoggedEvent("Recieved msgIWantProducts"));
//		orders.add(new MarketOrder(restaurant, item, numWanted));
//		stateChanged();
//	}

	public void msgIWantProducts(ItalianRestaurant restaurant, String item, int numWanted) {
		print("Restaurant asked for " + numWanted + " " + item + "(s)");
		log.add(new LoggedEvent("Recieved msgIWantProducts"));
		orders.add(new MarketOrder(restaurant, item, numWanted));
		if (person != null)
			stateChanged();
	}

//	public void msgIWantProducts(AmericanRestaurant restaurant, String item, int numWanted) {
//		print("Restaurant asked for " + numWanted + " " + item + "(s)");
//		log.add(new LoggedEvent("Recieved msgIWantProducts"));
//		orders.add(new MarketOrder(restaurant, item, numWanted));
//		if (person != null)
//			stateChanged();
//	}

	public void msgOrderFulfilled(MarketOrder o) {
		if (o.customer instanceof MarketCustomerRole) {
			print("An order has been fulfilled for: " + ((MarketCustomerRole) o.customer).person.getName());
		}

		for (MarketOrder MO : orders) {
			if (MO.equals(o)) {
				MO.state = orderState.itemsFound;
				stateChanged();
				return;
			}
		}
	}


	public void msgOrderDelivered(MarketOrder o) {

		print("An order has been delivered for the restaurant");

		for (MarketOrder MO : orders) {
			if (MO.equals(o)) {
				MO.state = orderState.itemsDelivered;
				stateChanged();
				return;
			}
		}
	}

	public void msgAtDestination() {
		this.atDestination.release();
	}


	public void msgPayment(MarketCustomer customer, double payment) {
		if (customer instanceof MarketCustomerRole) {
			print("Recieved payment of $" + payment + " from " + ((MarketCustomerRole) customer).person.getName());
		}

		log.add(new LoggedEvent("Recieved msgPayment"));
		market.money += payment;
		for (MarketOrder o : orders) {
			if (customer.equals(o.customer)) {
				orders.remove(o);
				return;
			}
		}
	}

	public void msgPayment(ChineseRestaurant chineseRestaurant, double payment) {
		print("Recieved payment of $" + payment + " from restaurant " + chineseRestaurant.getName());
		market.money += payment;
		for (MarketOrder o : orders) {
			if (o.chineseRestaurant.equals(chineseRestaurant)) {
				orders.remove(o);
				return;
			}
		}
	}

//	public void msgPayment(SeafoodRestaurant seafoodRestaurant, double payment) {
//		print("Recieved payment of $" + payment + " from restaurant " + seafoodRestaurant.getName());
//		market.money += payment;
//		for (MarketOrder o : orders) {
//			if (o.seafoodRestaurant.equals(seafoodRestaurant)) {
//				orders.remove(o);
//				return;
//			}
//		}
//	}

	public void msgPayment(ItalianRestaurant italianRestaurant, double payment) {
		print("Recieved payment of $" + payment + " from restaurant " + italianRestaurant.getName());
		market.money += payment;
		for (MarketOrder o : orders) {
			if (o.italianRestaurant.equals(italianRestaurant)) {
				orders.remove(o);
				return;
			}
		}
	}

//	public void msgPayment(AmericanRestaurant americanRestaurant, double payment) {
//		print("Recieved payment of $" + payment + " from restaurant " + americanRestaurant.getName());
//		market.money += payment;
//		for (MarketOrder o : orders) {
//			if (o.americanRestaurant.equals(americanRestaurant)) {
//				orders.remove(o);
//				return;
//			}
//		}
//	}

	//Scheduler
	public boolean pickAndExecuteAnAction() {
		if (!orders.isEmpty()) {
			synchronized(orders) {
				for (MarketOrder o : orders) {
					if (o.state == orderState.open) {
						findItems(o);
						return true;
					}
					if (o.state == orderState.itemsFound) {
						giveCustomerItems(o);
						return true;
					}
					if (o.state == orderState.itemsDelivered) {
						askForPayment(o);
						return true;
					}
				}
			}
		}

		if (leaveRole && orders.isEmpty()) {
			market.goingOffWork(person);
			leaveRole = false;
			return true;
		}

		return false;
	}

	//Actions
	public void findItems(MarketOrder o) {
		o.state = orderState.processing;

		if (market.inventory.get(o.item).amount == 0) {
			if (o.chineseRestaurant !=  null || test) {
				o.chineseRestaurant.getCook(test).msgCantFulfill(o.item, 0, o.itemAmountOrdered);
				orders.remove(o);
				stateChanged();
				return;
			}
		}

		//CARMEN IF YOU UNCOMMENT THIS, MAKE SURE IT DOESN'T STOP THE WHOLE FREAKING INTERACTION
		//BECUASE THE SEMAPHORE WAS NEVER RELEASED SO THE MARKET WASN'T WORKING
		if (!test){
			salesPersonGui.DoGotoRunner();
			try {
				this.atDestination.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		print("Gave Market Runner an order to find");
		if(market.getMarketRunner(test).isPresent()) {
			market.getMarketRunner(test).msgHeresAnOrder(o);
			stateChanged();
		}
		else {
			print("No market runner available!");
			stateChanged();
		}
	}

	public void giveCustomerItems(MarketOrder o) {
		o.state = orderState.gaveToCustomer;
		o.orderCost = market.inventory.get(o.item).price  * o.itemAmountFulfilled;

		if (o.customer instanceof MarketCustomerRole) {
			print("Gave order to: " + ((MarketCustomerRole) o.customer).person.getName());
		}

		o.customer.msgHereAreYourThings(o.item, o.itemAmountFulfilled, o.orderCost);
		stateChanged();
	}

	public void askForPayment(MarketOrder o) {
		o.state = orderState.gaveToCustomer;
		print("Asking for payment from the restaurant");
		if (o.chineseRestaurant != null) {
			o.chineseRestaurant.getCashier(true).msgPleasePayForItems(o.item, o.itemAmountFulfilled, o.orderCost, this);
			stateChanged();
		}
	}

	public void msgMarketOpen() {
		print("Opening market");
		if (!orders.isEmpty()) {
			for (MarketOrder o: orders) {
				if (o.customer != null) {
					o.customer.msgComeIn();
				}
			}
		}
	}

	public void setGui(MarketSalesPersonGui gui) {
		salesPersonGui = gui;
	}

	public MarketSalesPersonGui getGui() {
		return salesPersonGui;
	}
}
