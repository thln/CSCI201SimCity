//package seafoodRestaurant;
//
//import java.util.*;
//
//import application.Phonebook;
//import chineseRestaurant.ChineseRestaurantWaiterRole;
//import chineseRestaurant.ChineseRestaurantHostRole.myWaiter;
//import person.Role;
//import person.Worker;
//
////import restaurant.CustomerAgent.AgentState;
////import restaurant.WaiterAgent.Table;
////import restaurant.WaiterAgent.MyCustomer;
////import restaurant.WaiterAgent.myCustomerState;
//
///** 
// * Restaurant AmericanRestaurantHostRole
//  */
////This is the AmericanRestaurantHost Agent
//
//public class SeafoodRestaurantHostRole extends Role
//{
//	/***** DATA *****/
//	public enum CustomerState 
//	{Waiting,Seated, Left};
//	
//	public class MyCustomer
//	{
//		public SeafoodRestaurantCustomerRole c;
//		private CustomerState state = CustomerState.Waiting;
//		public MyCustomer(SeafoodRestaurantCustomerRole cust)
//		{
//			c = cust;
//		}
//	}
//
//	public enum WaiterState {Working, Asked, onBreak, LeavingSoon, Leaving};
//	//public boolean CheckingWaiters = false;
//	
//	public class MyWaiter
//	{
//		public SeafoodRestaurantWaiterRole w1;
//		//used possibly to go through the waiterlist and reorganize the customers
//		public int NumberOfCustomers;
//		private WaiterState state = WaiterState.Working;
//		//boolean leaving = false;
//		//public boolean Working = true;
//		public MyWaiter(SeafoodRestaurantWaiterRole w)
//		{
//			w1 = w;
//		}
//	}
//	
//	private class Table 
//	{
//		SeafoodRestaurantCustomerRole occupiedBy;
//		int tableNumber;
//
//		Table(int tableNumber) 
//		{
//			this.tableNumber = tableNumber;
//		}
//
//		void setOccupant(SeafoodRestaurantCustomerRole cust) 
//		{
//			occupiedBy = cust;
//		}
//
//		void setUnoccupied() 
//		{
//			occupiedBy = null;
//		}
//
//		SeafoodRestaurantCustomerRole getOccupant()
//		{
//			return occupiedBy;
//		}
//
//		boolean isOccupied() 
//		{
//			return occupiedBy != null;
//		}
//
//		public String toString() 
//		{
//			return "table " + tableNumber;
//		}
//	}
//	
//	public List<MyCustomer> MyCustomers = Collections.synchronizedList(new ArrayList<MyCustomer>());
//	public List<MyWaiter> MyWaiters = Collections.synchronizedList(new ArrayList<MyWaiter>());
//	public Collection<Table> tables;
//	private int NTABLES = 1; //Number of americanRestaurantTables!! Check, possible race conditions here?
//	private int MAXTABLES = 9;
//	private int currentWaiter = 0;
//	private int currentNumberOfCustomers = 0;
//	//private int NWAITERS = 1;
//	//private String name;
//	
//	public SeafoodRestaurantHostRole(String name) 
//	{
//		super(name);
//
//		//this.name = name;
//		// make some americanRestaurantTables
//		tables = new ArrayList<Table>(NTABLES);
//		for (int ix = 1; ix <= NTABLES; ix++) 
//		{
//			tables.add(new Table(ix));//how you add to a collections
//		}
//
//		//make waiters
//		/*
//		for (int ix = 1; ix <= NWAITERS; ix++) 
//		{
//			MyWaiters.add(new MyWaiter("Joe"));//how you add to a collections
//		}
//		*/
//		
//	}
//	
//	public String getName() 
//	{
//		return getName();
//	}
//
//	public Collection getTables() 
//	{
//		return tables;
//	}
//	
//
//	
//	
//	/***** MESSAGES *****/
//	public void addTables()
//	{
//		//ONLY 9 TABLES ALLOWED ATM
//		if(NTABLES < MAXTABLES)
//		{
//			NTABLES++;
//			tables.add(new Table(NTABLES));
//			print("Number of americanRestaurantTables " + NTABLES);
//			stateChanged();
//		}
//	}
//	
//	public void newWaiter(SeafoodRestaurantWaiterRole wa)
//	{
//		MyWaiters.add(new MyWaiter(wa));
//		print(MyWaiters.size() + " Number of Waiters");
//		stateChanged();
//	}
//	
//	//WAITER ON BREAK STUFF ******************************
//	public void CanIGoOnBreak(SeafoodRestaurantWaiterRole wa)
//	{
//		//Pass in to check new waiter
//		synchronized(MyWaiters)
//		{
//			for(MyWaiter mw : MyWaiters)
//			{
//				if(mw.w1 == wa)
//				{
//					mw.state = WaiterState.Asked;
//					stateChanged();
//				}
//			}
//		}
//	}
//	
//	public void msgIAmLeavingSoon(SeafoodRestaurantWaiterRole wa)
//	{
//		//Pass in to check new waiter
//			synchronized(MyWaiters)
//			{
//				for(MyWaiter mw : MyWaiters)
//				{
//					if(mw.w1 == wa)
//					{
//						mw.state = WaiterState.LeavingSoon;
//						stateChanged();
//					}
//				}
//			}
//	}
//
//	public void msgIAmLeavingWork(SeafoodRestaurantWaiterRole wa) 
//	{
//		for(MyWaiter MW: MyWaiters) 
//		{
//			if (MW.w1.equals(wa)) 
//			{
//				MW.state = WaiterState.Leaving;
//				stateChanged();
//				//MW.leaving = true;
//			}
//		}
//	}
//	
//	//WAITER ON BREAK STUFF ******************************
//	public void BackToWork(SeafoodRestaurantWaiterRole wa)
//	{
//		synchronized(MyWaiters)
//		{
//			for(MyWaiter mw : MyWaiters)
//			{
//				if(mw.w1 == wa)
//				{
//					mw.state = WaiterState.Working;
//					print(mw.w1.getName() + ", welcome back to work.");
//					//mw.Working = true;
//					stateChanged();
//				}
//			}
//		}
//		
//	}
//	
//	public void IWantFood(SeafoodRestaurantCustomerRole cust)
//	{
//		///////FILL IN HERE
//		MyCustomers.add(new MyCustomer(cust));
//		print("Message 1 Sent - Someone's hungry");
//		stateChanged();
//	}
//	
//	public void TableIsFree(int t, SeafoodRestaurantWaiterRole w)
//	{
//		///////FILL IN HERE
//		for(Table table : tables)
//		{
//			if(table.tableNumber == t)
//			{
//				synchronized(MyCustomers)
//				{
//					for(MyCustomer mc : MyCustomers)
//					{
//						if(mc.c == table.getOccupant())
//						{
//							synchronized(MyWaiters)
//							{
//								for(MyWaiter mw : MyWaiters)
//								{
//									if(mw.w1 == w)
//									{
//										table.setUnoccupied();
//										mw.NumberOfCustomers--;
//										updateNumberOfCustomers();
//										mc.state = CustomerState.Left;
//										stateChanged();
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//	}
//	
//	
//	
//	/**
//	 * Scheduler.  Determine what action is called for, and do it.
//	 */
//	
//	public boolean pickAndExecuteAnAction() 
//	{
//		//////FILL IN HERE
//		//WAITER ON BREAK STUFF ******************************
//		synchronized(MyWaiters)
//		{
//			for(MyWaiter mw : MyWaiters)
//			{
//				if (mw.state == WaiterState.Leaving) 
//				{
//					deleteWaiter(mw);
//				}
//				
//				if(mw.state == WaiterState.Asked)
//				{					
//					CheckWaiters(mw);
//					return true;
//				}
//			}
//		}
//		
//		synchronized(MyCustomers)
//		{
//			for(MyCustomer mc : MyCustomers)
//			{
//				//Checking all MyCustomers in list for anyone who is waiting
//				if(mc.state == CustomerState.Waiting)
//				{
//					for(Table table : tables)
//					{
//						//Checking all Tables in list for any empty americanRestaurantTables
//						if(!(table.isOccupied()))
//						{
//							//if there is an empty table and waiting customer, seat customer
//							seatCustomer(mc, table);
//							return true;
//						}
//					}
//				}
//			}
//		}
//		
//		if (leaveRole)
//		{
//			((Worker) person).roleFinishedWork();
//			leaveRole = false;
//			return true;
//		}
//		
//		return false;
//		//we have tried all our rules and found
//		//nothing to do. So return false to main loop of abstract agent
//		//and wait.
//	}
//	
//	/***** ACTIONS *****/
//	//WAITER ON BREAK STUFF ******************************
//	private void CheckWaiters(MyWaiter mw)
//	{
//		synchronized(MyWaiters)
//		{
//			for(MyWaiter waiter : MyWaiters)
//			{
//				if(waiter.state == WaiterState.Working)
//				{
//					mw.w1.AllowedToGoOnBreak(true);
//					print(mw.w1.getName() + ", you are allowed to go on break.");
//					mw.state = WaiterState.onBreak;
//					//should I?
//					//stateChanged();
//					return;
//				}
//			}
//		}
//		
//		mw.w1.AllowedToGoOnBreak(false);
//		print(mw.w1.getName() + ", you aren't allowed to go on break.");
//		mw.state = WaiterState.Working;
//		stateChanged();
//	}
//	
//	private void seatCustomer(MyCustomer mc, Table t)
//	{
//		/////FILL IN HERE
//		//Add in way to look through waiter list
//		if(MyWaiters.isEmpty())
//		{
//			//print("We currently have no waiters available.");
//		}
//	
//		else 
//		{
//			
//			if(MyWaiters.get(currentWaiter).w1.AtHomeboolean && MyWaiters.get(currentWaiter).state == WaiterState.Working)
//			{
//				print("The current waiter is number" + currentWaiter);
//			MyWaiters.get(currentWaiter).NumberOfCustomers++;
//			updateNumberOfCustomers();
//			MyWaiters.get(currentWaiter).w1.pleaseSeatCustomer(mc.c, t.tableNumber);
//			print("Message 2 Sent " + mc.c.getName() + " " + mc.state);
//			//print("currentWaiter number : " + currentWaiter + " and size of waiterlist : " + MyWaiters.size());
//				mc.state = CustomerState.Seated;
//				for(Table table : tables)
//				{
//					if(table == t)
//					{
//						table.setOccupant(mc.c);
//					}
//				}
//			}
//			//keeps track of current waiter, if currentwaiter is last waiter on list, resets to first waiter
//			//automatically divide the customers among the number of waiters
//			currentWaiter++;
//			if(currentWaiter >= MyWaiters.size())
//			{
//				currentWaiter = 0;
//			}
//		}
//		//commented out 10/30/13
//		//stateChanged();
//	}
//	
//	private void updateNumberOfCustomers()
//	{
//		currentNumberOfCustomers = 0;
//		synchronized(MyWaiters)
//		{
//			for(MyWaiter mw : MyWaiters)
//			{
//				currentNumberOfCustomers += mw.NumberOfCustomers;
//			}
//		}
//		print("The current Number of Customers is " + currentNumberOfCustomers);
//	}
//
//	public void deleteWaiter(MyWaiter mw) 
//	{
//		Phonebook.getPhonebook().getSeafoodRestaurant().removeWaiter(mw.w1);
//		mw = null;
//		MyWaiters.remove(mw);
//	}
//	
//	public boolean FullRestaurant()
//	{		
//		if(currentNumberOfCustomers == NTABLES)
//		{
//			return true;
//		}
//		
//		return false;
//	}
//
//	public void msgRestaurantOpen() {
//		if (MyCustomers.size() != 0){
//			for (MyCustomer c1: MyCustomers){
//				c1.c.msgComeIn();
//			}
//		}
//	}
//}
