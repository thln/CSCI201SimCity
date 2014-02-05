package chineseRestaurant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import chineseRestaurant.interfaces.ChineseRestaurantCustomer;
import application.Phonebook;
import application.gui.animation.agentGui.*;
import person.Person;
import person.Role;
import person.Worker;
/**
 * Restaurant AmericanRestaurantHost Role
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the AmericanRestaurantHostRole. A AmericanRestaurantHost is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class ChineseRestaurantHostRole extends Role {
	static final int NTABLES = 4;//a global for the number of americanRestaurantTables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public List<myCustomer> newCustomers = Collections.synchronizedList(new ArrayList<myCustomer>());
	public List<myCustomer> waitingCustomers = Collections.synchronizedList(new ArrayList<myCustomer>());
	public List<myWaiter> waiters = Collections.synchronizedList(new ArrayList<myWaiter>());

	public ChineseRestaurant chineseRestaurant;
	
	public Collection<Table> tables;
	protected String roleName = "AmericanRestaurantHost";
	//note that americanRestaurantTables is typed with Collection semantics.
	//Later we will see how it is implemented

	private String name;
	//private Semaphore atTable = new Semaphore(0,true);

	//GUI stuff
	//public HostGui hostGui = null;
	//ITALIAN IS TEMP UNTIL CHINESE ONE IS MADE
	private ItalianHostGui hostGui;

	public ChineseRestaurantHostRole(Person p1, String pName, String rName, ChineseRestaurant restaurant) 
	{
		super(p1, pName, rName);

		// make some americanRestaurantTables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}
		this.chineseRestaurant = restaurant;
	}

	public ChineseRestaurantHostRole(String roleName, ChineseRestaurant restaurant) 
	{
		super(roleName);

		// make some americanRestaurantTables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}
		this.chineseRestaurant = restaurant;
	}

	public String getMaitreDName() {
		return name;
	}

	public List getWaitingCustomers() {
		return waitingCustomers;
	}

	public Collection getTables() {
		return tables;
	}



	/**
	 *	Messages
	 */
	public void msgIWantFood(ChineseRestaurantCustomerRole cust, int xHome, int yHome) {
		synchronized(newCustomers){
			newCustomers.add(new myCustomer(cust, xHome, yHome));
			stateChanged();
		}
	}

	public void msgStaying(ChineseRestaurantCustomerRole cust, int xHome, int yHome) {
		synchronized(waitingCustomers){
			waitingCustomers.add(new myCustomer(cust, xHome, yHome));
			stateChanged();
		}
	}

	public void msgLeavingTable(ChineseRestaurantCustomer cust, ChineseRestaurantWaiterRole chineseRestaurantWaiterRole) 
	{
		for (Table table : tables) 
		{
			if (table.getOccupant() == cust) 
			{
				print(cust.getName() + " is leaving table " + table);
				table.setUnoccupied();
			}
		}

		for (myWaiter MW: waiters) 
		{
			if (MW.chineseRestaurantWaiterRole == chineseRestaurantWaiterRole) 
			{
				MW.totalCustomers--;
			}
		}

		stateChanged();
	}

	public void msgMayIGoOnBreak(ChineseRestaurantWaiterRole chineseRestaurantWaiterRole) 
	{
		print(chineseRestaurantWaiterRole.getName() + " asked to go on break");
		for (myWaiter MW: waiters) 
		{
			if (MW.chineseRestaurantWaiterRole == chineseRestaurantWaiterRole)
			{
				MW.state = myWaiterState.Working;
				//MW.askedToGoOnBreak = true;
				stateChanged();
			}
		}
	}

	public void msgOffBreak(ChineseRestaurantWaiterRole chineseRestaurantWaiterRole) {
		for (myWaiter MW: waiters) {
			if (MW.chineseRestaurantWaiterRole.equals(chineseRestaurantWaiterRole)) {
				MW.state = myWaiterState.Working;
				//MW.onBreak = false;
				stateChanged();
			}
		}
	}
	
	public void msgIAmLeavingSoon(ChineseRestaurantWaiterRole chineseRestaurantWaiterRole) {
		for (myWaiter MW: waiters)
		{
			if (MW.chineseRestaurantWaiterRole.equals(chineseRestaurantWaiterRole))
			{
				MW.state = myWaiterState.LeavingSoon;
				if (person!=null) {
					stateChanged();
				}
			}
		}
	}
	
	public void msgIAmLeavingWork(ChineseRestaurantWaiterRole chineseRestaurantWaiterRole) {
		for(myWaiter MW: waiters) {
			if (MW.chineseRestaurantWaiterRole.equals(chineseRestaurantWaiterRole)) {
				MW.leaving = true;
			}
		}
	}


	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() 
	{
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */		
		synchronized(newCustomers)
		{
			synchronized(waitingCustomers)
			{
				if (!newCustomers.isEmpty()) 
				{
					greetCustomer();
					return true;
				}
			}
		}

		synchronized(waiters)
		{
			synchronized(waitingCustomers)
			{
				for (Table table : tables) 
				{
					if (!table.isOccupied()) 
					{
						if (!waiters.isEmpty()) 
						{
							if (!waitingCustomers.isEmpty()) 
							{
								assignCustomer(waitingCustomers.get(0), table, findWaiterWithLeastCustomers());//the action
								return true;//return true to the abstract agent to re-invoke the scheduler.
							}
						}
					}
				}
			}
		}

		synchronized(waiters)
		{
			if (!waiters.isEmpty()) 
			{
				for (myWaiter MW : waiters) 
				{
					if (MW.leaving == true) {
						deleteWaiter(MW);
					}
					
					if (MW.state == myWaiterState.askedToGoOnBreak)//MW.askedToGoOnBreak == true) 
					{
						replyToBreakRequest(MW);
						return true;
					}
				}
			}
		}

		if (leaveRole) {
			chineseRestaurant.goingOffWork(person);
			leaveRole = false;
			return true;
		}
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}



	/** Actions
	 *
	 */
	private void greetCustomer() 
	{
		int fullTableIterator = 0;

		for (Table table : tables) 
		{
			if (table.isOccupied()) 
			{
				fullTableIterator++;
			}
		}

		if (fullTableIterator == NTABLES) 
		{
			informCustomerRestaurantFull(newCustomers.get(0).customer);
		}
		else 
		{
			waitingCustomers.add(newCustomers.get(0));
			newCustomers.remove(0);
		}
	}

	private void assignCustomer(myCustomer MC, Table table, ChineseRestaurantWaiterRole chineseRestaurantWaiterRole) 
	{

		print("Assigning customer " + MC.customer.getCustomerName() + " to waiter" + chineseRestaurantWaiterRole.getName());
		addCustomerToWaiter(chineseRestaurantWaiterRole);
		//waiterRole
		chineseRestaurantWaiterRole.msgPleaseSeatCustomer(table.tableNumber, MC.customer, MC.xHome, MC.yHome);
		table.setOccupant(MC.customer);
		for (myCustomer MyCust : waitingCustomers) {
			if (MyCust.equals(MC)) {
				waitingCustomers.remove(MC);
				return;
			}
		}
	}

	private void replyToBreakRequest(myWaiter MW) 
	{

		if (waiters.size() == 1) 
		{
			//MW.askedToGoOnBreak = false;
			MW.state = myWaiterState.Working;
			MW.chineseRestaurantWaiterRole.msgPermissionToBreak(false);
			print("Telling " + MW.chineseRestaurantWaiterRole.getName() + " he/she cannot go on break");
			return;
		}

		int workingWaiterCount = 0;

		//Determining how many working waiters there are
		for (myWaiter wait: waiters)
		{
			if ((wait.state != myWaiterState.onBreak) && (wait.state != myWaiterState.LeavingSoon))//wait.onBreak == false)
				workingWaiterCount++;
		}

		if (workingWaiterCount > 1) 
		{
			//MW.askedToGoOnBreak = false;
			MW.state = myWaiterState.onBreak;
			print("Allowing " + MW.chineseRestaurantWaiterRole.getName() + " to go on break");
			MW.chineseRestaurantWaiterRole.msgPermissionToBreak(true);
			//MW.onBreak = true;
		}
		else 
		{
			//MW.askedToGoOnBreak = false;
			MW.state = myWaiterState.Working;
			print("Deny " + MW.chineseRestaurantWaiterRole.getName() + " to go on break");
			MW.chineseRestaurantWaiterRole.msgPermissionToBreak(false);
		}
	}

	private void informCustomerRestaurantFull(ChineseRestaurantCustomerRole customer)
	{
		customer.msgTablesAreFull();
		for(myCustomer MC : newCustomers) 
		{
			if (MC.customer.equals(customer))
			{
				newCustomers.remove(MC);
				return;
			}
		}
		stateChanged();
	}

	//utilities

	/* GUI STUFF
	public void setGui(HostGui gui) {
		hostGui = gui;
	}

	public HostGui getGui() {
		return hostGui;
	}*/

	public void addWaiter(ChineseRestaurantWaiterRole chineseRestaurantWaiterRole) 
	{
		waiters.add(new myWaiter(chineseRestaurantWaiterRole));
		print("Hired new waiter, " + chineseRestaurantWaiterRole.getPerson().getName());
		if (person != null)
			stateChanged();
	}

	public void addCustomerToWaiter(ChineseRestaurantWaiterRole chineseRestaurantWaiterRole) 
	{
		for (myWaiter MW: waiters) {
			if (MW.chineseRestaurantWaiterRole == chineseRestaurantWaiterRole)
				MW.totalCustomers++;
		}
	}

	private ChineseRestaurantWaiterRole findWaiterWithLeastCustomers() {
		//Finding first waiter that is not on break
		myWaiter lowestWaiter = null;

		for (myWaiter lowWaiter: waiters) 
		{
			if ((lowWaiter.state != myWaiterState.onBreak) && (lowWaiter.state != myWaiterState.LeavingSoon))//lowWaiter.onBreak == false) 
			{
				lowestWaiter = lowWaiter;	
				break;
			}
		}
		
		
		//Spreading customers equally
		for (int i = 0; i < waiters.size(); i++) 
		{	
			if ((lowestWaiter.totalCustomers > waiters.get(i).totalCustomers) && (waiters.get(i).state != myWaiterState.onBreak) && (waiters.get(i).state != myWaiterState.LeavingSoon)) //.onBreak == false))
				lowestWaiter = waiters.get(i);
		}
		return lowestWaiter.chineseRestaurantWaiterRole;
	}

	private class Table {
		ChineseRestaurantCustomerRole occupiedBy;
		int tableNumber;

		Table(int tableNumber) {
			this.tableNumber = tableNumber;
		}


		void setOccupant(ChineseRestaurantCustomerRole cust) {
			occupiedBy = cust;
		}

		void setUnoccupied() {
			occupiedBy = null;
		}

		ChineseRestaurantCustomerRole getOccupant() {
			return occupiedBy;
		}

		boolean isOccupied() {
			return occupiedBy != null;
		}

		public String toString() {
			return "table " + tableNumber;
		}

	}
	
	public void deleteWaiter(myWaiter MW) {
		chineseRestaurant.removeWaiter(MW.chineseRestaurantWaiterRole);
		MW = null;
		waiters.remove(MW);
	}
	
	
	public enum myWaiterState {Working, askedToGoOnBreak, onBreak, LeavingSoon};

	public class myWaiter {
		public ChineseRestaurantWaiterRole chineseRestaurantWaiterRole;
		public int totalCustomers;
		//boolean askedToGoOnBreak = false;
		//boolean onBreak = false;
		myWaiterState state = myWaiterState.Working;
		boolean leaving = false;
		

		myWaiter(ChineseRestaurantWaiterRole chineseRestaurantWaiterRole) {
			this.chineseRestaurantWaiterRole = chineseRestaurantWaiterRole;
			totalCustomers = 0;
		}
	}
	
	private class myCustomer {
		ChineseRestaurantCustomerRole customer;
		int xHome;
		int yHome;
		boolean leaving = false;
		
		myCustomer(ChineseRestaurantCustomerRole customer, int xHome, int yHome) {
			this.customer = customer;
			this.xHome = xHome;
			this.yHome = yHome;
		}
	}

	public void msgRestaurantOpen() {
		if (newCustomers.size() != 0){
			for (myCustomer c1: newCustomers){
				c1.customer.msgComeIn();
			}
		}
	}
	//ITALIAN IS TEMP UNTIL CHINESE ONE IS MADE
	public void setGui(ItalianHostGui gui) { 
		hostGui = gui;
	}
	
	public ItalianHostGui getGui() {
		return hostGui;
	}
}

