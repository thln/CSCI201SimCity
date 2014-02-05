package market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import market.interfaces.MarketRunner;
import person.Person;
import person.Role;
import testing.EventLog;
import testing.LoggedEvent;
import application.gui.animation.agentGui.MarketRunnerGui;

public class MarketRunnerRole extends Role implements MarketRunner {

	//MarketRunnerGui marketRunnerGui = (MarketRunnerGui) gui;
	MarketRunnerGui marketRunnerGui;

	//Data
	String name;
	Market market;

	public List<MarketOrder> orders = Collections.synchronizedList(new ArrayList<MarketOrder>());
	private Semaphore atDestination = new Semaphore(0, true);
	boolean present = false;
	public EventLog log = new EventLog();

	public MarketRunnerRole(Person person, String pName, String rName, Market market) {
		super(person, pName, rName);
		this.market = market;
	}

	public MarketRunnerRole(String roleName, Market market) {
		super(roleName);
		this.market = market;
	}

	//Messages
	public void msgHeresAnOrder(MarketOrder o) {
		print("Recieved an order to fulfill");
		log.add(new LoggedEvent("Recieved msgHeresAnOrder"));
		orders.add(o);
		if (person != null)
			stateChanged();
	}

	public void msgAtDestination() {
		this.atDestination.release();
	}

	//Scheduler
	public boolean pickAndExecuteAnAction() {
		if (!orders.isEmpty()) {
			for (MarketOrder o: orders){
				processOrder(o);
				return true;
			}
		}

		if (leaveRole  && orders.isEmpty() && market.salesPersonRole.orders.isEmpty()) {
			market.goingOffWork(person);
			leaveRole = false;
			return true;
		}

		return false;
	}

	//Actions
	public void processOrder(MarketOrder o) {
		print("getting order from inventory");
		//if(!marketRunnerGui.atInventory()) {
		//CARMEN IF YOU UNCOMMENT THIS, MAKE SURE IT DOESN'T STOP THE WHOLE FREAKING INTERACTION
		//BECUASE THE SEMAPHORE WAS NEVER RELEASED SO THE MARKET WASN'T WORKING
		if (!test){
			marketRunnerGui.DoGoToInventory();
			try {
				this.atDestination.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (o.customer != null) {
			decreaseInventoryBy(o.item, o.itemAmountOrdered);
			o.itemAmountFulfilled = o.itemAmountOrdered;
			if (o.customer instanceof MarketCustomerRole) {
				print("Fulfilled order for customer: " + ((MarketCustomerRole) o.customer).getName());
			}

			if (!test) {
				marketRunnerGui.DoGoToSalesPerson();
				try {
					this.atDestination.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			market.getSalesPerson(test).msgOrderFulfilled(o);
			orders.remove(o);
		}
		else { //o.customerType is an instance of business
			decreaseInventoryBy(o.item, o.itemAmountOrdered);
			o.itemAmountFulfilled = o.itemAmountOrdered;
			print("Fulfilled order for restaurant for: " + o.item);

			if (!test) {
				marketRunnerGui.DoGoToSalesPerson();
				try {
					this.atDestination.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			market.getUPSman(test).msgDeliverOrder(o);
			orders.remove(o);
		}
	}

	public void decreaseInventoryBy(String item, int amount) {
		int newAmount = market.inventory.get(item).amount - amount;
		market.inventory.get(item).setInventory(newAmount);
	}

	public void setGui(MarketRunnerGui gui) {
		marketRunnerGui = gui;
	}

	public void setPresent(boolean present) {
		this.present = present;
	}

	public boolean isPresent() {
		if(present)
			return true;
		return false;
	}
}
