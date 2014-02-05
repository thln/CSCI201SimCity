package market;

import java.awt.Point;
import java.awt.Toolkit;
import java.util.*;

import javax.swing.ImageIcon;

import market.interfaces.MarketRunner;
import market.interfaces.SalesPerson;
import market.interfaces.UPSman;
import market.test.mock.MockMarketRunner;
import market.test.mock.MockSalesPerson;
import market.test.mock.MockUPSman;
import person.Person;
import person.Role;
import person.Worker;
import transportation.BusStop;
import application.Phonebook;
import application.WatchTime;
import application.gui.animation.BuildingPanel;
import application.gui.animation.agentGui.MarketCustomerGui;
import application.gui.animation.agentGui.MarketRunnerGui;
import application.gui.animation.agentGui.MarketSalesPersonGui;
import application.gui.animation.agentGui.MarketUPSmanGui;

public class Market {

	ImageIcon market = new ImageIcon("res/market.png", "market");

	//Data
	String name;
	public boolean userClosed = false;
	public Point location; 
	//private Point closestStop;
	public int busStopNumber;

	//Open and closing times
	public WatchTime openTime = new WatchTime(9);
	public WatchTime closeTime = new WatchTime(18);

	//Roles
	public SalesPersonRole salesPersonRole = new SalesPersonRole("Sales Person", this);
	public MarketSalesPersonGui salesPersonGui = new MarketSalesPersonGui(salesPersonRole);

	public MarketRunnerRole marketRunnerRole = new MarketRunnerRole("Market Runner", this);
	public MarketRunnerGui marketRunnerGui = new MarketRunnerGui(marketRunnerRole);

	public UPSmanRole UPSmanRole = new UPSmanRole("UPS Man", this);
	public MarketUPSmanGui UPSmanGui = new MarketUPSmanGui(UPSmanRole);

	//Mocks
	public MockSalesPerson mockSalesPerson = new MockSalesPerson("MockSalesPerson");
	public MockMarketRunner mockMarketRunner = new MockMarketRunner("MockMarketRunner");
	public MockUPSman mockUPSman = new MockUPSman("MockUPSMan");

	public List <MarketCustomerGui> marketCustomerGuis = new ArrayList<MarketCustomerGui>();

	private BuildingPanel marketPanel;

	double money;
	public HashMap<Integer, Product> marketItemsForSale = new HashMap<Integer, Product>(); {
		//For people
		marketItemsForSale.put(0, new Product("Pasta", 1.99));
		marketItemsForSale.put(1, new Product("Ice Cream", 4.99));
		marketItemsForSale.put(2, new Product("Chips", 2.99));
		marketItemsForSale.put(3, new Product("Milk", 2.50));
		marketItemsForSale.put(4, new Product("Eggs", 1.50));
		marketItemsForSale.put(5, new Product("Lobster", 12.99));
		marketItemsForSale.put(6, new Product("Cheese", 3.99));
		marketItemsForSale.put(7, new Product("Car", 1000.00));
	}

	public HashMap<String, Item> inventory = new HashMap<String, Item>();
	{
		//For people
		inventory.put("Car", new Item("Car", 1000.00, 1000));
		inventory.put("Pasta", new Item("Pasta", 1.99, 1000));
		inventory.put("Ice Cream", new Item("Ice Cream", 4.99, 1000));
		inventory.put("Chips", new Item("Chips", 2.99, 1000));
		inventory.put("Milk", new Item("Milk", 2.50, 1000));
		inventory.put("Eggs", new Item("Eggs", 1.50, 1000));
		inventory.put("Lobster", new Item("Lobster", 12.99, 1000));
		inventory.put("Cheese", new Item("Cheese", 3.99, 1000));

		//For restaurants
		inventory.put("Chicken", new Item("Chicken", 10.99, 1000));
		inventory.put("Steak", new Item("Steak", 15.99, 1000));
		inventory.put("Pizza", new Item("Pizza", 8.99, 1000));
		inventory.put("Salad", new Item("Salad", 5.99, 1000));

		inventory.put("Bourbon-Glazed Salmon", new Item("Bourbon-Glazed Salmon", 10.99, 1000));
		inventory.put("Lobster Tail and Roll", new Item("Lobster Tail and Roll", 15.99, 1000));
		inventory.put("Grilled Shrimp Skewers", new Item("Grilled Shrimp Skewers", 8.99, 1000));
		inventory.put("Clam Chowder Sourdough Bowl", new Item("Clam Chowder Sourdough Bowl", 5.99, 1000));		
	}

	//Constructor
	public Market(String name) 	{
		if (name == "East Market"){
			location = new Point(530, 123);
		}
		
		if (name == "West Market"){
			location = new Point(95, 290);        
		}
		
		this.name = name;
	}


	//Methods
	public Role arrivedAtWork(Person person, String title) {
		if (title == "salesPerson") {
			//Setting previous bank guard role to inactive
			if (salesPersonRole.getPerson() != null) {
				Worker worker = (Worker) salesPersonRole.getPerson();
				worker.roleFinishedWork();
			}
			//Setting bank guard role to new role
			salesPersonRole.setPerson(person);
			if (isOpen()) {
				salesPersonRole.msgMarketOpen();
			}
			salesPersonRole.setGui(salesPersonGui);
			marketPanel.addGui(salesPersonGui);
			return salesPersonRole;
		}
		else if (title == "marketRunner") {
			//Setting previous bank guard role to inactive
			if (marketRunnerRole.getPerson() != null) {
				Worker worker = (Worker) marketRunnerRole.getPerson();
				worker.roleFinishedWork();
			}
			//Setting bank guard role to new role
			marketRunnerRole.setPerson(person);
			marketRunnerRole.setPresent(true);
			if (isOpen()) {
				salesPersonRole.msgMarketOpen();
			}
			marketRunnerRole.setGui(marketRunnerGui);
			marketPanel.addGui(marketRunnerGui);
			return marketRunnerRole;
		}
		else if (title == "UPSman") {
			//Setting previous bank guard role to inactive
			if (UPSmanRole.getPerson() != null) {
				Worker worker = (Worker) UPSmanRole.getPerson();
				worker.roleFinishedWork();
			}
			//Setting bank guard role to new role
			UPSmanRole.setPerson(person);
			if (isOpen()) {
				salesPersonRole.msgMarketOpen();
			}
			UPSmanRole.setGui(UPSmanGui);
			marketPanel.addGui(UPSmanGui);
			return UPSmanRole;
		}
		else
			return null;
	}

	public void arrived(MarketCustomerRole mCR) {
		//MarketCustomerGui rCG = (MarketCustomerGui) mCR.gui;
		MarketCustomerGui MCG = new MarketCustomerGui(mCR);
		mCR.setGui(MCG);
		MCG.setHome(marketCustomerGuis.size());
		mCR.setMarket(this);
		marketCustomerGuis.add(MCG);
		marketPanel.addGui(MCG);
		//	MCG.waitInLine();
	}

	public void goingOffWork(Person person) {
		Worker worker = (Worker) person;

		if (worker.getWorkerRole().equals(salesPersonRole)) {
			salesPersonRole.person = null;
			marketPanel.removeGui(salesPersonGui);
		}
		if (worker.getWorkerRole().equals(marketRunnerRole)) {
			marketRunnerRole.person = null;
			marketPanel.removeGui(marketRunnerGui);
		}
		if (worker.getWorkerRole().equals(UPSmanRole)) {
			UPSmanRole.person = null;
			marketPanel.removeGui(UPSmanGui);
		}
		worker.workerRole = null;
	}

	public class Item {
		public String itemName;
		public double  price;
		public int amount;

		Item(String choice, double price, int amount) 
		{
			itemName = choice;
			this.price = price;
			this.amount = amount;
		}

		public void setInventory(int newAmount) 
		{
			this.amount = newAmount;
		}
	}

	public class Product {
		public String itemName;
		public double  price;

		public Product (String choice, double price) 
		{
			itemName = choice;
			this.price = price;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public SalesPerson getSalesPerson(boolean test) {
		if (test) {
			return mockSalesPerson;
		}
		return salesPersonRole;
	}

	public MarketRunner getMarketRunner(boolean test) {
		if (test) {
			return mockMarketRunner;
		}
		return marketRunnerRole;
	}

	public UPSman getUPSman(boolean test) {
		if (test) {
			return mockUPSman;
		}
		return UPSmanRole;
	}

	public void setBuildingPanel (BuildingPanel buildingPanel) {
		marketPanel = buildingPanel;
	}

	public boolean isOpen(){
		if (salesPersonRole.getPerson() != null && marketRunnerRole.getPerson() != null && UPSmanRole != null && !userClosed)
			return true;
		else 
			return false;
	}

	public void removeCustomer(MarketCustomerRole customerRole) {
		marketPanel.removeGui(customerRole.getGui());
		marketCustomerGuis.remove(customerRole.getGui());
	}

	public void closeBuilding(){
		userClosed = true;
		salesPersonRole.msgLeaveRole();

		marketRunnerRole.msgLeaveRole();

		UPSmanRole.msgLeaveRole();
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
