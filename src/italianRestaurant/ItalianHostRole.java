package italianRestaurant;

import person.*;
import application.gui.animation.agentGui.*;
import italianRestaurant.interfaces.*;

import java.util.*;
import java.util.concurrent.Semaphore;

import javax.swing.*;

/**
 * Restaurant AmericanRestaurantHost Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the AmericanRestaurantHostRole. A AmericanRestaurantHost is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class ItalianHostRole extends Role {
	static final int NTABLES = 5;//a global for the number of americanRestaurantTables.
	Random rn = new Random(3469117);
	public List<ItalianWaiterRole> waiters = Collections.synchronizedList(new ArrayList<ItalianWaiterRole>());
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	
	public List<ItalianCustomerRole> waitingCustomers
	= Collections.synchronizedList(new ArrayList<ItalianCustomerRole>());
	public List<Integer> startPoses
	= Collections.synchronizedList(new ArrayList<Integer>());
	public Collection<Table> tables;
	//note that americanRestaurantTables is typed with Collection semantics.
	//Later we will see how it is implemented
	
	private Semaphore atTable = new Semaphore(0,true);

	public ItalianHostGui hostGui = null;
	protected String RoleName = "Host";
	
	public ItalianHostRole(String name) {
		super(name);
		// make some americanRestaurantTables
		tables = Collections.synchronizedList(new ArrayList<Table>(NTABLES));
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}
	}

	// Messages
	public void msgIWantFood(ItalianCustomerRole cust, int startPos) {
		waitingCustomers.add(cust);
		startPoses.add(new Integer(startPos));
		stateChanged();
	}
	
	public void msgWantBreak(ItalianWaiterRole waiter) {
		if(waiters.size() > 1) {
			print(waiter + "wants to go on break.");
			//waiter.wanttogoonbreak = true;
			waiter.setBreak(true);
			waiters.remove(waiter);
		}
		else {
			JOptionPane.showMessageDialog(null, 
					"You must keep at least one waiter not on break.", 
					"Break Error", JOptionPane.ERROR_MESSAGE);
		}
		stateChanged();
	}
	
	public void msgBackOnDuty(ItalianWaiterRole waiter) {
		waiter.setBreak(false);
		waiters.add(waiter);
		waiter.getGui().GoToHome();
		stateChanged();
	}
	
	public void msgLeavingTable(ItalianCustomer cust) {
		for (Table table : tables) {
			if (table.getOccupant() == cust) {
				print(table + " is now unoccupied");
				table.setUnoccupied();
				stateChanged();
			}
		}
	}

	public void msgAtTable() {//from animation
		//print("msgAtTable() called");
		atTable.release();// = true;
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
		synchronized(tables){	
			for (Table table : tables) {
				if (!table.isOccupied()) {
					if (!waitingCustomers.isEmpty()) {
						setWaiter(waitingCustomers.get(0));
						WaiterseatCustomer(waitingCustomers.get(0), table, startPoses.get(0));
						return true;//return true to the abstract agent to reinvoke the scheduler.
					}
				}
			}
			
		}
		
		if (leaveRole)
		{
			((Worker) person).roleFinishedWork();
			leaveRole = false;
			return true;
		}

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions
	private void WaiterseatCustomer(ItalianCustomerRole customer, Table table, Integer startPos) {
		//customer.msgSitAtTable(); //waiter sends this message
		/*
		try {
			atTable.acquire();
			System.out.print(";A;A;;A;;AA;A;A;A;A;");
			//System.out.print("\n"+customer.waiter.getName()+"\n");
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		table.setOccupant(customer);
		waitingCustomers.remove(customer);
		startPoses.remove(startPos);
		customer.getWaiter().msgPleaseSitCustomer(customer, table.tableNumber, startPos);
	}

	//utilities
	public String toString() {
		return "host " + getName();
	}

	public void setGui(ItalianHostGui gui) {
		hostGui = gui;
	}

	public ItalianHostGui getGui() {
		return hostGui;
	}

	private class Table {
		ItalianCustomerRole occupiedBy;
		int tableNumber;

		Table(int tableNumber) {
			this.tableNumber = tableNumber;
		}

		void setOccupant(ItalianCustomerRole cust) {
			occupiedBy = cust;
		}

		void setUnoccupied() {
			occupiedBy = null;
		}

		ItalianCustomerRole getOccupant() {
			return occupiedBy;
		}

		boolean isOccupied() {
			return occupiedBy != null;
		}

		public String toString() {
			return "table " + tableNumber;
		}
	}
	
/*
 * There will be a specification that will appropriate the # of waiters 
 * necessary for the # of open americanRestaurantTables. (Ex. 1 waiter per 3 americanRestaurantTables, etc.)
 * Waiters will be randomly chosen OR HARDCODED for NOW --- 
 * later will have a mechanism to see which waiter is available (not on break,etc.)
 * 	--- and that waiter will be used in the restaurant as needed.
 */
	public void addWaiter(ItalianWaiterRole waiter){
		waiter.getGui().StartAt(waiters.size());
		waiters.add(waiter);
	}
	
	public void removeWaiter(ItalianWaiterRole waiter) {
		waiters.remove(waiter);
	}
	
	public void setWaiter(ItalianCustomerRole c){
		//for now, randomly generated numbers assign a waiter to the customer
		if(waiters.size() > 0) {
		int i = rn.nextInt(waiters.size());
		c.setWaiter(waiters.get(i));
		}
		else {
			JOptionPane.showMessageDialog(null,
				    "You must add a waiter in order to add a customer.",
				    "AmericanRestaurantCustomer Error",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void setCook(ItalianCookRole cookagent){
		//for now, randomly generated numbers assign a waiter to the customer
		synchronized(waiters){
		for(int i=0; i < waiters.size(); i++) {
			waiters.get(i).setCook(cookagent);
		}
		}
	}
	
	public void msgRestaurantOpen() {
		if (waitingCustomers.size() != 0){
			for (ItalianCustomer c1: waitingCustomers){
				c1.msgComeIn();
			}
		}
	}
}

