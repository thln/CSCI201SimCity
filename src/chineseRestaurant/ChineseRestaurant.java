package chineseRestaurant;

import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Vector;

import market.Market;
import person.Person;
import person.Role;
import person.Worker;
import transportation.BusStop;
import application.Phonebook;
import application.Restaurant;
import application.WatchTime;
import application.gui.animation.BuildingPanel;
import application.gui.animation.agentGui.RestaurantCookGui;
import application.gui.animation.agentGui.RestaurantCustomerGui;
import application.gui.animation.agentGui.RestaurantWaiterGui;
import chineseRestaurant.interfaces.ChineseRestaurantCashier;
import chineseRestaurant.interfaces.ChineseRestaurantCook;
import chineseRestaurant.test.mock.ChineseRestaurantMockCashier;
import chineseRestaurant.test.mock.ChineseRestaurantMockCook;

public class ChineseRestaurant implements Restaurant {

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
	private Vector<ChineseRestaurantCustomerRole> customers = new Vector<ChineseRestaurantCustomerRole>();

	//List of Waiters
	private Vector<ChineseRestaurantWaiterRole> waiters = new Vector<ChineseRestaurantWaiterRole>();

	//Open and closing times
	public WatchTime openTime = new WatchTime(11);
	public WatchTime closeTime = new WatchTime(21);

	//Roles

	public ChineseRestaurantHostRole chineseRestaurantHostRole = new ChineseRestaurantHostRole("Host", this);
	public ChineseRestaurantCookRole chineseRestaurantCookRole = new ChineseRestaurantCookRole("Cook", this);
	public RestaurantCookGui cookGui = new RestaurantCookGui(chineseRestaurantCookRole);

	public ChineseRestaurantCashierRole chineseRestaurantCashierRole = new ChineseRestaurantCashierRole("Cashier", this);

	public ChineseRestaurantRevolvingStand theRevolvingStand = new ChineseRestaurantRevolvingStand();
	private BuildingPanel restPanel;

	//Mocks
	public ChineseRestaurantMockCook chineseRestaurantMockCook = new ChineseRestaurantMockCook("MockCook");
	public ChineseRestaurantMockCashier chineseRestaurantMockCashier = new ChineseRestaurantMockCashier("MockCashier");

	public ChineseRestaurant(String name) {
		location = new Point(335, 20);
		this.name = name;
		//chineseRestaurantCookRole.setGui(cookGui);
	}

	//Methods
	public Role arrivedAtWork(Person person, String title)  {

		if (title == "host") {
			//Setting previous bank guard role to inactive
			if ((chineseRestaurantHostRole.getPerson() != null)
					&& ((Worker) chineseRestaurantHostRole.getPerson()).workerRole != null) {
				Worker worker = (Worker) chineseRestaurantHostRole.getPerson();
				worker.roleFinishedWork();
			}
			//Setting bank guard role to new role
			chineseRestaurantHostRole.setPerson(person);
			if (isOpen()) {
				chineseRestaurantHostRole.msgRestaurantOpen();
			}
			return chineseRestaurantHostRole;
		}
		else if (title == "cook") {
			//Setting previous bank guard role to inactive
			if (chineseRestaurantCookRole.getPerson() != null 
				&& ((Worker) chineseRestaurantHostRole.getPerson()).workerRole != null) {
				Worker worker = (Worker) chineseRestaurantCookRole.getPerson();
				worker.roleFinishedWork();
			}
			//Setting cook role to new role
			chineseRestaurantCookRole.setPerson(person);
			if (isOpen()) {
				chineseRestaurantHostRole.msgRestaurantOpen();
			}
			chineseRestaurantCookRole.setGui(cookGui);
			restPanel.addGui(cookGui);
			return chineseRestaurantCookRole;
		}
		else if (title.contains("cashier")) {
			//Setting previous bank guard role to inactive
			if (chineseRestaurantCashierRole.getPerson() != null
					&& ((Worker) chineseRestaurantHostRole.getPerson()).workerRole != null) {
				Worker worker = (Worker) chineseRestaurantCashierRole.getPerson();
				worker.roleFinishedWork();
			}
			//Setting americanRestaurantCashier role to new role
			chineseRestaurantCashierRole.setPerson(person);
			if (isOpen()) {
				chineseRestaurantHostRole.msgRestaurantOpen();
			}
			return chineseRestaurantCashierRole;
		}
		else if (title == "waiter") {	
			ChineseRestaurantWaiterRole waiter = new ChineseRestaurantWaiterRole(person, person.getName(), title, this);
			RestaurantWaiterGui g = new RestaurantWaiterGui(waiter);
			restPanel.addGui(g);
			waiter.setGui(g);
			if (waiters.size() <= 12) {
				g.setHomePosition(5, (55 + (22 * waiters.size())));
			}
			else if (waiters.size() <= 24) {
				g.setHomePosition(27, (55 + (22 * (waiters.size()-12))));
			}

			waiters.add(waiter);
			chineseRestaurantHostRole.addWaiter(waiter);
			if (isOpen()) {
				chineseRestaurantHostRole.msgRestaurantOpen();
			}
			return waiter;
		}
		else if (title == "altWaiter") {
			ChineseRestaurantAltWaiterRole altWaiter = new ChineseRestaurantAltWaiterRole(person, person.getName(), title, this);
			RestaurantWaiterGui g = new RestaurantWaiterGui(altWaiter);
			restPanel.addGui(g);
			altWaiter.setGui(g);
			if (waiters.size() <= 12) {
				g.setHomePosition(5, (55 + (22 * waiters.size())));
			}
			else if (waiters.size() <= 24) {
				g.setHomePosition(27, (55 + (22 * (waiters.size()-12))));
			}

			waiters.add(altWaiter);
			chineseRestaurantHostRole.addWaiter(altWaiter);
			if (isOpen()) {
				chineseRestaurantHostRole.msgRestaurantOpen();
			}
			return altWaiter;
		}
		//for waiter and alternative waiters, you message the host
		return null;
	}

	public boolean arrived(ChineseRestaurantCustomerRole rCR) {
		RestaurantCustomerGui rCG = new RestaurantCustomerGui(rCR);
		rCR.setGui(rCG);
		if (customers.size() <= 12) {
			rCG.setHomePosition((22 * customers.size()), 10);
			rCR.gotHungry((22 * customers.size()), 10);
			restPanel.addGui(rCG);
			customers.add(rCR);
			return true;
		}
		else if (customers.size() <= 24) {
			rCG.setHomePosition((22 * (customers.size() - 12)), 32);
			rCR.gotHungry((22 * (customers.size() - 12)), 32);
			restPanel.addGui(rCG);
			customers.add(rCR);
			return true;
		}
		return false;
	}

	public void goingOffWork(Person person) {

		Worker worker = (Worker) person;
		if (worker.workerRole != null){
			if (worker.getWorkerRole().equals(chineseRestaurantHostRole)) {
				worker.roleFinishedWork();
			}
			else if (worker.getWorkerRole().equals(chineseRestaurantCashierRole)) {
				worker.roleFinishedWork();
			}
			else if (worker.getWorkerRole().equals(chineseRestaurantCookRole)) {
				worker.roleFinishedWork();
				restPanel.removeGui(cookGui);
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ChineseRestaurantRevolvingStand getRevolvingStand() {
		return theRevolvingStand;
	}

	public void setPanel(BuildingPanel panel) {
		restPanel = panel;
	}

	public ChineseRestaurantCook getCook(boolean test) {
		if (test) {
			return chineseRestaurantMockCook;
		}
		return chineseRestaurantCookRole;
	}

	public ChineseRestaurantCashier getCashier(boolean test) {
		if (test) {
			return chineseRestaurantMockCashier;
		}
		return chineseRestaurantCashierRole;
	}

	public boolean isOpen() {
	//	System.err.println("Testing chinese open");
		if (chineseRestaurantHostRole.getPerson() != null && chineseRestaurantHostRole.waiters.size() != 0 
				&& chineseRestaurantCookRole.getPerson() != null && chineseRestaurantCashierRole != null && !userClosed){
			System.err.println("Chinese restaurant now open!");
			return true;
		}
			
		else 
			return false;
	}

	public void setBuildingPanel (BuildingPanel rp) {
		restPanel = rp;
	}

	public void removeWaiter(ChineseRestaurantWaiterRole chineseRestaurantWaiterRole) {
		waiters.remove(chineseRestaurantWaiterRole);
		restPanel.removeGui(chineseRestaurantWaiterRole.getGui());
	}

	public void removeCustomer(ChineseRestaurantCustomerRole customerRole) {
		customers.remove(customerRole);
		restPanel.removeGui(customerRole.getGui());
	}

	public void closeBuilding(){
		userClosed = true;
		chineseRestaurantHostRole.msgLeaveRole();
		for (ChineseRestaurantWaiterRole w1: waiters) {
			w1.msgLeaveRole();
			restPanel.removeGui(w1.getGui());
		}
		chineseRestaurantCookRole.msgLeaveRole();
		restPanel.removeGui(cookGui);

		chineseRestaurantCashierRole.msgLeaveRole();
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
//	
	public Vector<ChineseRestaurantCustomerRole> getCustomers()
	{
		return customers;
	}
}
