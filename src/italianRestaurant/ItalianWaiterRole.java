package italianRestaurant;

import person.*;
import application.Phonebook;
import application.gui.animation.agentGui.*;
import italianRestaurant.interfaces.*;
import italianRestaurant.ItalianMyCustomer.MyCustState;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant AmericanRestaurantWaiter Role
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the WaiterRole. A AmericanRestaurantWaiter is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class ItalianWaiterRole extends Role implements ItalianWaiter{
	static final int NTABLES = 5;//a global for the number of americanRestaurantTables.
	//Notice that we implement Customers using ArrayList, but type it
	//with List semantics.
	//private CustomerRole currCustomer;
	private boolean onbreak = false;
	public List<ItalianMyCustomer> Customers = new ArrayList<ItalianMyCustomer>();
	
	//note that americanRestaurantTables is typed with Collection semantics.
	//Later we will see how it is implemented
 
	private Semaphore atCust = new Semaphore(0,true);
	private Semaphore atTable = new Semaphore(0,true);
	private Semaphore atCook = new Semaphore(0,true);
	private Semaphore BackatTable = new Semaphore(0,true);
	private Semaphore atCashier = new Semaphore(0, true);
	private Semaphore atTableAgain = new Semaphore(0, true);
	
	private Person person;
	private ItalianCookRole cook;
	private ItalianHostRole host;
	private ItalianCashierRole cashier;
	public ItalianWaiterGui waiterGui = null;
	
	protected String RoleName = "waiter";

	public ItalianWaiterRole(Person person, String name, String title) {
		super(name);
		this.person = person;
	}

	public List getCustomers() {
		return Customers;
	}
	
	// Messages
	
	//message host uses to tell waiter that a customer 
	public void msgPleaseSitCustomer(ItalianCustomer c, int tablenum, Integer startPos){
		Customers.add(new ItalianMyCustomer(c, tablenum, startPos));
		print("Received message from host to seat " + c + " at table " + tablenum);
		//c.msgSitAtTable();
		stateChanged();
	}
	
	/*message from waiter to customer telling he/she is 
	 * to follow waiter to table. The state of the 
	 * waiter's customer is changed accordingly.
	 */
	public void msgFollowMe(ItalianCustomer c){
		for(int i=0; i<Customers.size();i++){
			if(Customers.get(i).c.equals(c)){
				print(c + " is following me to table " + Customers.get(i).table);
				ItalianMyCustomer mc = Customers.get(i);
				mc.s = MyCustState.seating;
				stateChanged();
			}
		}
	}
	
	public void msgHereIsOrder(ItalianCustomer c, String choice){
		for(int i=0; i<Customers.size();i++){
			if(Customers.get(i).c.equals(c)){
				Customers.get(i).choice = choice;
				print("Received Order from " + Customers.get(i).c + " who has chosen " + Customers.get(i).choice);
				Customers.get(i).s = MyCustState.readytoorder;
				stateChanged();
			}
		}
	}
	
	//message from Cook to AmericanRestaurantWaiter stating that order from table is done cooking
	public void msgOrderDone(String choice, int table) {
		for(int i=0; i<Customers.size(); i++) {
			if(Customers.get(i).table == table) {
				print("Received message " + choice + " from table " + table + " is done");
				Customers.get(i).s = MyCustState.pending;
				stateChanged();
			}
		}
	}
	
	public void msgFoodOut(String choice, int table) {
		for(int i=0; i<Customers.size(); i++) {
			if(Customers.get(i).table == table) {
				print("Received message " + choice + " from table " + table + " is OUT");
				Customers.get(i).s = MyCustState.orderingAgain;
				stateChanged();
			}
		}
	}
	
	public void msgCustWantsCheck(ItalianCustomer c) {
		for(int i=0; i<Customers.size();i++){
			if(Customers.get(i).c.equals(c)){
				Customers.get(i).s = MyCustState.readyforCheck;
				stateChanged();
			}
		}
	}
	
	public void msgHereIsCheck(ItalianCustomer c, Double total) {
		for(int i=0; i<Customers.size();i++){
			if(Customers.get(i).c.equals(c)){
				Customers.get(i).billtotal = total;
				Customers.get(i).s = MyCustState.paying;
				stateChanged();
			}
		}
	}
	
	/*Message from customer to waiter stating that customer is leaving.
	 * Used for waiter to also tell host the same message.
	 */
	
	public void msgLeavingTable(ItalianCustomer c) {
		for(int i=0; i<Customers.size();i++){
			if(Customers.get(i).c.equals(c)){
				ItalianMyCustomer mc = Customers.get(i);
				Customers.remove(mc); //to be removed in V2.1 to calculate orders?
				print("Telling host that " + c + " is leaving table.");
				host.msgLeavingTable(c);
				stateChanged();
			}
		}
	}
	
	public void msgAtCust() {
		atCust.release();
		stateChanged();
	}

	public void msgAtTable() {//from animation
		//print("msgAtTable() called");
		atTable.release();// = true;
		stateChanged();
	}
	
	public void msgAtCook() {
		atCook.release();
		stateChanged();
	}
	
	public void msgBackAtTable() {
		BackatTable.release();
		stateChanged();
	}
	
	public void msgAtCashier() {
		atCashier.release();
		stateChanged();
	}
	
	public void msgAtTableAgain() {//from animation
		atTableAgain.release();// = true;
		stateChanged();
	}


	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		try{
		if(Customers != null) {
			for(int i=0; i<Customers.size();i++){
				if(Customers.get(i).s == MyCustState.readytoseat) {
					//seatCustomer(Customers.get(i).c, Customers.get(i).table);
					BringCustFromWaiting(Customers.get(i));
					//Customers.remove(Customers.get(i));
					return true;
				}
			}
			for(int i=0; i<Customers.size();i++){
				if(Customers.get(i).s == MyCustState.seating) {
					seatCustomer(Customers.get(i), Customers.get(i).table);
					return true;
				}
			}
			for(int i=0; i<Customers.size();i++){
				if(Customers.get(i).s == MyCustState.readytoorder) {
					PlaceOrder(Customers.get(i));
					return true;
				}
			}
			for(int i=0; i<Customers.size();i++){
				if(Customers.get(i).s == MyCustState.pending) {
					BacktoCustomer(Customers.get(i));
				}
			}
			for(int i=0; i<Customers.size();i++){
				if(Customers.get(i).s == MyCustState.orderingAgain) {
					ReorderingCustomer(Customers.get(i));
				}
			}
			for(int i=0; i<Customers.size();i++){
				if(Customers.get(i).s == MyCustState.readyforCheck) {
					GotoCashier(Customers.get(i));
				}
			}
			for(int i=0; i<Customers.size();i++){
				if(Customers.get(i).s == MyCustState.paying) {
					ChecktoCust(Customers.get(i));
				}
			}
			for(int i=0; i<Customers.size();i++){
				if(Customers.get(i).s == MyCustState.done || Customers.get(i).s == MyCustState.finished) {
					leaveCustomer(Customers.get(i));
				}
			}
		}
		} catch (ConcurrentModificationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if (leaveRole)
		{
			Phonebook.getPhonebook().getItalianRestaurant().goingOffWork(person);
			leaveRole = false;
			return true;
		}
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions
	public void BringCustFromWaiting(ItalianMyCustomer customer) {
		print("Ready to seat " + customer.c);
		waiterGui.GoToWaiting(customer.home);
		try {
			atCust.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customer.s = MyCustState.seating;
		
		/*
		//for testing and debugging the waiter on Break button
		if(name.equals("Break")) {
			host.msgWantBreak(this);
			
		}*/
	}

	public void seatCustomer(ItalianMyCustomer customer, int table) {
		customer.c.msgSitAtTable(table);
		DoSeatCustomer(customer, table);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customer.s = MyCustState.waiting;
		//leaveCustomer(); //ERASE FOR COOK MOVEMENTS
	}

	// The animation DoXYZ() routines
	private void DoSeatCustomer(ItalianMyCustomer customer, int table) {
		//Notice how we print "customer" directly. It's toString method will do it.
		//Same with "table"
		print("Seating " + customer + " at table " + table);
		waiterGui.DoBringToTable(table);
		
	}
	
	public void leaveCustomer(ItalianMyCustomer customer) {
		if(customer.s == MyCustState.done)
			waiterGui.GoToHome();
		else if(customer.s == MyCustState.finished) {
			if(onbreak == true)
				waiterGui.GotoBreak();
			else
				waiterGui.GoToHome();
		}
	}
	
	private void PlaceOrder(ItalianMyCustomer customer) {
		customer.c.msgdoneOrdering();
		print("Going to " + cook);
		waiterGui.DoGotoCook();
		try {
			atCook.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cook.msgHereIsCustOrder(this, customer.choice, customer.table);
		customer.s = MyCustState.waiting;
	}
	
	private void BacktoCustomer(ItalianMyCustomer customer) {
		//DO Bring food to customer
		print("Bringing " + customer.choice + " back to table " + customer.table);
		waiterGui.DoGoToTable(customer.table, customer.choice);
		try {
			BackatTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customer.c.msgdoneWaitingForFood();
		customer.s = MyCustState.done;
		//customer.c.msgdoneWaitingForFood();//hack; to be commented out LATER
	}
	
	private void ReorderingCustomer(ItalianMyCustomer customer) {
		//DO Bring food to customer
		print("Telling " + customer + " to order again.");
		waiterGui.DoGoToTable(customer.table, "none");
		try {
			BackatTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customer.c.msgFoodOutReorder(customer.choice);
		customer.s = MyCustState.waiting;
		//customer.c.msgdoneWaitingForFood();//hack; to be commented out LATER
	}
	
	private void GotoCashier(ItalianMyCustomer customer) {
		print("Getting check for " + customer);
		waiterGui.GoToCashier();
		try {
			atCashier.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cashier.msgComputeBill(this, customer.c, customer.choice);
		customer.s = MyCustState.waiting;
	}
	
	public void ChecktoCust(ItalianMyCustomer customer) {
		print("Giving Check to " + customer);
		waiterGui.DoGoToTableAgain(customer.table);
		try {
			atTableAgain.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customer.c.msgHereIsBill(customer.billtotal);
		//customer.s = MyCustState.waiting;
		customer.s = MyCustState.finished;
	}

	//utilities
	public String toString() {
		return "waiter " + getName();
	}

	public void setGui(ItalianWaiterGui gui) {
		waiterGui = gui;
	}

	public ItalianWaiterGui getGui() {
		return waiterGui;
	}
	
	public void setCook(ItalianCookRole cookagent) {
		cook = cookagent;
	}

	public void setHost(ItalianHostRole hostagent) {
		host = hostagent;
	}
	
	public void setCashier(ItalianCashierRole cashieragent) {
		cashier = cashieragent;
	}
	
	public ItalianHostRole getHost() {
		return host;
	}
	
	public ItalianCookRole getCook() {
		return cook;
	}
	
	public void setBreak(boolean breaking) {
		onbreak = breaking;
	}
	
	public boolean OnBreak() {
		return onbreak;
	}
	
}

