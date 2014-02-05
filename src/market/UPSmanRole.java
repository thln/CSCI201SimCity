package market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import chineseRestaurant.ChineseRestaurant;
import market.interfaces.UPSman;
import person.Person;
import person.Role;
import person.Worker;
import testing.EventLog;
import testing.LoggedEvent;
import application.gui.animation.agentGui.*;

public class UPSmanRole extends Role implements UPSman {

	//Data
	public List<MarketOrder> orders = Collections.synchronizedList(new ArrayList<MarketOrder>());
	private Semaphore atDestination = new Semaphore(0, true);
	Market market;
	private MarketUPSmanGui UPSmanGui;

	public EventLog log = new EventLog();

	public UPSmanRole (Person p, String pName, String rName, Market market) {
		super(p, pName, rName);
		this.market = market;
	}

	public UPSmanRole(String roleName, Market market) {
		super(roleName);
		this.market = market;
	}

	//Messages
	public void msgDeliverOrder(MarketOrder o) {
		print("Recieved an order to deliver");
		log.add(new LoggedEvent("Recieved msgDeliverOrder"));
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
			for (MarketOrder o: orders) {
				deliverOrder(o);
				return true;
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
	public void deliverOrder(MarketOrder o) {

		if (!test) {
			UPSmanGui.DoGoToSalesPerson();
			try {
				this.atDestination.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		UPSmanGui.DoExit();
		}

		print("Delivered an order to a restaurant");
		if (o.chineseRestaurant != null) {
			o.chineseRestaurant.getCook(test).msgOrderFulfillment(o.item, o.itemAmountFulfilled, o.itemAmountOrdered);
			market.getSalesPerson(test).msgOrderDelivered(o);
			orders.remove(o);
			return;
		}
//
//		if (o.seafoodRestaurant != null) {
//			//Must send then order back
//			market.getSalesPerson(test).msgOrderDelivered(o);
//			orders.remove(o);
//			return;
//		}

		if (o.italianRestaurant != null) {
			//Must sent the order back
			market.getSalesPerson(test).msgOrderDelivered(o);
			orders.remove(o);
			return;
		}

//		if (o.americanRestaurant != null) {
//			o.americanRestaurant.americanCook.msgHereIsYourOrder(o.item);
//			market.getSalesPerson(test).msgOrderDelivered(o);
//			orders.remove(o);
//			return;
//		}
		return;
	}

	public void setGui(MarketUPSmanGui gui) {
		UPSmanGui = gui;
	}

	public MarketUPSmanGui getGui() {
		return UPSmanGui;
	}
}
