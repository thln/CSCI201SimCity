package italianRestaurant;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.*;

import person.*;
import application.*;
import transportation.BusStop;
import application.*;
import application.gui.animation.*;
import application.gui.animation.agentGui.*;

public class ItalianRestaurant implements Restaurant{

	/*
	Toolkit tk = Toolkit.getDefaultToolkit();
	int WINDOWX = ((int) tk.getScreenSize().getWidth())/2; 
	int WINDOWY = (((int) tk.getScreenSize().getHeight())/2)*5/6;   
	*/
	
	//Data
	String name;
	public boolean userClosed = false;
	public Point location; 
	//private Point closestStop;
	public int busStopNumber;

	//List of Customers
	private Vector<ItalianCustomerRole> customers = new Vector<ItalianCustomerRole>();
		
	//List of Waiters
	private Vector<ItalianWaiterRole> waiters = new Vector<ItalianWaiterRole>();

	//Open and closing times
	public WatchTime openTime = new WatchTime(11);
	public WatchTime closeTime = new WatchTime(21);

	//Roles
	public ItalianHostRole italianRestaurantHostRole = new ItalianHostRole("Host");
	public ItalianHostGui hostGui = new ItalianHostGui(italianRestaurantHostRole);
	
	public ItalianCookRole italianRestaurantCookRole = new ItalianCookRole("Cook", this);
	public ItalianCookGui cookGui = new ItalianCookGui(italianRestaurantCookRole);

	public ItalianCashierRole italianRestaurantCashierRole = new ItalianCashierRole("Cashier", this);
	public ItalianCashierGui cashierGui = new ItalianCashierGui(italianRestaurantCashierRole);
	public ItalianRevolvingStand theRevolvingStand = new ItalianRevolvingStand();
	private BuildingPanel restPanel;

	//Mocks
	//public ItalianMockCook italianRestaurantMockCook = new ItalianMockCook("MockCook");
	//public ItalianMockCashier italianRestaurantMockCashier = new ItalianMockCashier("MockCashier");

	public ItalianRestaurant(String name) {
		location = new Point(600-123, 325/6-restaurant.getIconHeight()/2);
		this.name = name;
		italianRestaurantCookRole.setGui(cookGui);
		italianRestaurantCashierRole.setGui(cashierGui);
		italianRestaurantCashierRole.setCook(italianRestaurantCookRole);
	}

	//Methods
	public Role arrivedAtWork(Person person, String title)  {

		if (title == "host") {
			//Setting previous bank guard role to inactive
			if (italianRestaurantHostRole.getPerson() != null) {
				Worker worker = (Worker) italianRestaurantHostRole.getPerson();
				worker.roleFinishedWork();
			}
			//Setting bank guard role to new role
			italianRestaurantHostRole.setPerson(person);
			italianRestaurantHostRole.setGui(hostGui);
			restPanel.addGui(hostGui);
			if (isOpen()) {
				italianRestaurantHostRole.msgRestaurantOpen();
			}
			return italianRestaurantHostRole;
		}
		else if (title == "cook") {
			//Setting previous bank guard role to inactive
			if (italianRestaurantCookRole.getPerson() != null) {
				Worker worker = (Worker) italianRestaurantCookRole.getPerson();
				worker.roleFinishedWork();
			}
			//Setting cook role to new role
			italianRestaurantCookRole.setPerson(person);
			if (isOpen()) {
				italianRestaurantHostRole.msgRestaurantOpen();
			}
			restPanel.addGui(cookGui);
			return italianRestaurantCookRole;
		}
		else if (title.contains("americanRestaurantCashier")) {
			//Setting previous bank guard role to inactive
			if (italianRestaurantCashierRole.getPerson() != null) {
				Worker worker = (Worker) italianRestaurantCashierRole.getPerson();
				worker.roleFinishedWork();
			}
			//Setting americanRestaurantCashier role to new role
			italianRestaurantCashierRole.setPerson(person);
			if (isOpen()) {
				italianRestaurantHostRole.msgRestaurantOpen();
			}
			restPanel.addGui(cashierGui);
			return italianRestaurantCashierRole;
		}
		else if (title == "waiter") {	
			ItalianWaiterRole waiter = new ItalianWaiterRole(person, person.getName(), title);
			ItalianWaiterGui g = new ItalianWaiterGui(waiter);
			restPanel.addGui(g);
			waiter.setGui(g);
			waiters.add(waiter);
			italianRestaurantHostRole.addWaiter(waiter);
			if (isOpen()) {
				italianRestaurantHostRole.msgRestaurantOpen();
			}
			return waiter;
		}
		else if (title == "altWaiter") {
			ItalianAltWaiterRole altWaiter = new ItalianAltWaiterRole(person, person.getName(), title);
			/*
			if (waiters.size() <= 12) {
				ItalianWaiterGui g = new ItalianWaiterGui(altWaiter);
				restPanel.addGui(g);
				altWaiter.setGui(g);
				//g.setHomePosition(5, (55 + (22 * waiters.size())));
			}
			else if (waiters.size() <= 24) {
				ItalianWaiterGui g = new ItalianWaiterGui(altWaiter);
				restPanel.addGui(g);
				altWaiter.setGui(g);
				//g.setHomePosition(27, (55 + (22 * (waiters.size()-12))));
			}
			*/
			ItalianWaiterGui g = new ItalianWaiterGui(altWaiter);
			restPanel.addGui(g);
			altWaiter.setGui(g);
			waiters.add(altWaiter);
			italianRestaurantHostRole.addWaiter(altWaiter);
			if (isOpen()) {
				italianRestaurantHostRole.msgRestaurantOpen();
			}
			return altWaiter;
		}
		//for waiter and alternative waiters, you message the host
		return null;
	}
	
	
	public boolean arrived(ItalianCustomerRole rCR) {
		
			//ItalianCustomerGui rCG = (ItalianCustomerGui) rCR.gui;
			ItalianCustomerGui rCG = new ItalianCustomerGui(rCR);
			rCG.SetHome(customers.size());
			rCR.setGui(rCG);
			restPanel.addGui(rCG);
			customers.add(rCR);
			rCR.gotHungry();
			return true;
		
		///return false;
	}
	

	public void goingOffWork(Person person) {
		Worker worker = (Worker) person;

		if (worker.getWorkerRole().equals(italianRestaurantHostRole)) {
			worker.roleFinishedWork();
			return;
		}
		else if (worker.getWorkerRole().equals(italianRestaurantCashierRole)) {
			worker.roleFinishedWork();
			return;
		}
		else if (worker.getWorkerRole().equals(italianRestaurantCookRole)) {
			worker.roleFinishedWork();
			return;
			//restPanel.removeGui(cookGui);
		}
		//WAITERS AND ALT WAITERS
		//finish the "leave work" in Role.java 
		//make function in host to delete waiter
		//waiters have to finish duties before finishing waiter & no assignments
		//look at onBreak code to follow
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItalianRevolvingStand getRevolvingStand() {
		return theRevolvingStand;
	}

	public void setPanel(BuildingPanel panel) {
		restPanel = panel;
	}

	public ItalianCookRole getCook(boolean test) {
//		if (test) {
//			return italianRestaurantMockCook;
//		}
		return italianRestaurantCookRole;
	}

	public ItalianCashierRole getCashier(boolean test) {
//		if (test) {
//			return italianRestaurantMockCashier;
//		}
		return italianRestaurantCashierRole;
	}

	public boolean isOpen() {
		if (italianRestaurantHostRole.getPerson() != null && italianRestaurantHostRole.waiters.size() != 0 && italianRestaurantCookRole.getPerson() != null && italianRestaurantCashierRole != null && !userClosed)
			return true;
		else 
			return false;
	}

	public void setBuildingPanel (BuildingPanel buildingPanel) {
		restPanel = buildingPanel;
	}

	public void removeWaiter(ItalianWaiterRole italianItalianWaiterRole) {
		waiters.remove(italianItalianWaiterRole);
		restPanel.removeGui(italianItalianWaiterRole.getGui());
	}

	public void removeCustomer(ItalianCustomerRole customerRole) {
		customers.remove(customerRole);
		restPanel.removeGui(customerRole.getGui());
	}

	public void closeBuilding(){
		userClosed = true;
		italianRestaurantHostRole.msgLeaveRole();
		for (ItalianWaiterRole w1: waiters) {
			w1.msgLeaveRole();
			restPanel.removeGui(w1.getGui());
		}
		italianRestaurantCookRole.msgLeaveRole();
		restPanel.removeGui(cookGui);

		italianRestaurantCashierRole.msgLeaveRole();
	}

//	public void setClosestStop(Point point) {
//		closestStop = point;
//	}
	
	public void setClosestBusStopNumber (int n) 
	{
		busStopNumber = n;
	}
	
	public BusStop getClosestBusStop ()
	{
		return Phonebook.getPhonebook().getAllBusStops().get(busStopNumber);
	}
	
//	public Point getClosestStop() {
//		return closestStop;
//	}
}

