//package seafoodRestaurant;
//
//import java.util.ArrayList;
//import java.util.ConcurrentModificationException;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.concurrent.Semaphore;
//
//
//
//
//
//
//import application.Phonebook;
//import application.gui.animation.agentGui.SeafoodRestaurantWaiterGui;
//import person.Person;
////import application.gui.animation.agentGui.SeafoodRestaurantWaiterGui;
//import person.Role;
//import person.Worker;
////import seafoodRestaurant.interfaces.Check;
//import seafoodRestaurant.interfaces.SeafoodRestaurantCustomer;
//import seafoodRestaurant.interfaces.SeafoodRestaurantWaiter;
//import chineseRestaurant.ChineseRestaurantCashierRole.Check;
////import restaurant.CustomerAgent.AgentEvent;
////import restaurant.CustomerAgent.AgentState;
////import restaurant.HostAgent.CustomerState;
////import restaurant.gui.WaiterGui;
////import restaurant.interfaces.Cashier;
////import restaurant.interfaces.Customer;
////import restaurant.interfaces.Waiter;
//
///**
// * Restaurant Waiter Agent
// */
////We only have 2 types of agents in this prototype. A customer and an agent that
////does all the rest. Rather than calling the other agent a waiter, we called him
////the HostRole. A Host is the manager of a restaurant who sees that all
////is proceeded as he wishes.
//
////This is now a Waiter Agent. We are implementing the Host separately. 9/18/13
//public class SeafoodRestaurantWaiterRole extends Role implements SeafoodRestaurantWaiter
//{
//	//static final int NTABLES = 3;//a global for the number of Tables.
//	//protected int NTABLES = 1;
//	protected int xCordFrontDesk = 25;
//	protected int yCordFrontDesk = 25;
//	protected int waiterNumber; 
//	//Notice that we implement waitingCustomers using ArrayList, but type it
//	//with List semantics.
//	public List<MyCustomer> MyCustomers = new ArrayList<MyCustomer>();
//	public boolean AtHomeboolean = true;
//	//public boolean OnBreak = false;
//	//note that Tables is typed with Collection semantics.
//	//Later we will see how it is implemented
//
//	//Agent Correspondents
//	//protected SeafoodRestaurantHostRole host;
//	//protected SeafoodRestaurantCookRole cook;
//	//protected SeafoodRestaurantCashierRole Cashier;
//	
//	Timer relaxTimer = new Timer();
//	
//	//Checks for state, since there are only two states, boolean works
//	//protected boolean bringingCustomer = false; 
//	protected String name;
//	protected boolean onBreak = false;
//	protected boolean requestingBreak = false;
//	protected Semaphore atTable = new Semaphore(0,true);
//	protected Semaphore receivingOrder = new Semaphore(0, true);
//	protected Semaphore atKitchen = new Semaphore(0, true);
//	protected Semaphore atCustomerLobby = new Semaphore(0, true);
//	protected Semaphore atCashier = new Semaphore(0, true);
//	protected Semaphore receivingCheck = new Semaphore(0, true);
//	
//	public SeafoodRestaurantWaiterGui waiterGui = null;
//	
//	public enum myCustomerState 
//	{Waiting, Seated, readyToOrder, TakingOrder, OrderReceived, OrderSent, OutOfOrder, DeliveringMeal, Eating, AskedForCheck, GettingCheck, WaitingForCashierCheck, CheckReceived, GivenCheckToCustomer, Leaving, Left};
//
//	//WAITER ON BREAK STUFF ******************************
//	public enum WaiterState 
//	{Working, WantToGoOnBreak, AskedHost, CannotGoOnBreak, OnWayToBreak, InBreakRoom, Relaxing, WantToGoOffBreak};
//	public WaiterState state = WaiterState.Working;
//	
//	class MyCustomer
//	{//switch into just Customers?
//		public SeafoodRestaurantCustomerRole c;
//		int table;
//		String choice;
//		protected myCustomerState state = myCustomerState.Waiting;
//		public SeafoodRestaurantCheck CustomersCheck;
//		public int xCordCustomerLobby;
//		public int yCordCustomerLobby;
//		public int initPosition = 20;
//		public MyCustomer(SeafoodRestaurantCustomerRole cust, int t)
//		{
//			c = cust;
//			table = t;
//			xCordCustomerLobby = initPosition;
//			yCordCustomerLobby = initPosition + (initPosition * cust.getCustomerNumber());
//			
//		}
//	}
//	
//
//	public SeafoodRestaurantWaiterRole(Person person, String name, String title) 
//	{
//		super(name);
//		this.person = person;
//		//this.waiterNumber = n; //I removed this from the constructor
//		
//		if(name.equals("OnBreak"))
//		{
//			//state = WaiterState.OnBreak;
//			state = WaiterState.WantToGoOnBreak;
////			WantToGoOnBreak();
//		}
//	}
//
//	public String getMaitreDName() 
//	{
//		return name;
//	}
//
//	public String getName() 
//	{
//		return getName();
//	}
//
//	public List getWaitingCustomers() 
//	{
//		return MyCustomers;
//	}
//	
//
//	
//	
//	
//	
//	///// Messages
//
//	//WAITER ON BREAK STUFF ******************************
//	public void WantGoOnBreak() 
//	{//from animation
//		print("I want to go on break.");
//		state = WaiterState.WantToGoOnBreak;
//		requestingBreak = true;
//		stateChanged();
//	}
//	
//	//WAITER ON BREAK STUFF ******************************
//	public void WantToGoOffBreak()
//	{
//		print("I want to go off break.");
//		state = WaiterState.WantToGoOffBreak;
//		stateChanged();
//	}
//	
//	//WAITER ON BREAK STUFF ******************************
//	public void AllowedToGoOnBreak(boolean answer)
//	{
//		if(answer)
//		{
//			state = WaiterState.OnWayToBreak;
//			//onBreak = true;
//			//requestingBreak = false;
//			stateChanged();
//		}
//		else if (!answer)
//		{
//			state = WaiterState.CannotGoOnBreak;
//			//onBreak = false;
//			stateChanged();
//		}
//	}
//	
//	public void CanIGetMyCheck(SeafoodRestaurantCustomer cust)
//	{
//		for(MyCustomer mc : MyCustomers)
//		{
//			if(mc.c == cust)
//			{
//				mc.state = myCustomerState.AskedForCheck;
//				stateChanged();
//			}
//		}
//	}
//	
//	public void pleaseSeatCustomer(SeafoodRestaurantCustomerRole cust, int table)
//	{
//		MyCustomers.add(new MyCustomer(cust,table));
//		stateChanged();
//	}
//	
//	public void ReadyToOrder(SeafoodRestaurantCustomerRole c)
//	{
//		for(MyCustomer mc : MyCustomers)
//		{
//			if(mc.c == c)
//			{
//				mc.state = myCustomerState.readyToOrder;
//				stateChanged();
//			}
//		}
//	}
//
//	public void myChoiceIs(String TheOrder, SeafoodRestaurantCustomer cust)
//	{
//		for(MyCustomer mc : MyCustomers)
//		{
//			if(mc.c == cust)
//			{
//				mc.choice = TheOrder;
//				mc.state = myCustomerState.OrderReceived;
//				receivingOrder.release();
//				stateChanged();
//			}
//		}
//		
//	}
//	
//	public void OrderIsReady(String food, int table)
//	{
//		for(MyCustomer mc : MyCustomers)
//		{
//			if(mc.table == table && mc.choice == food)
//			{
//				mc.state = myCustomerState.DeliveringMeal;
//				stateChanged();
//			}
//			
//		}
//	}
//	
//	public void iAmLeavingTable(SeafoodRestaurantCustomer cust) 
//	{
//		for(MyCustomer mc : MyCustomers)
//		{
//			if(mc.c == cust)
//			{
//				mc.state = myCustomerState.Leaving;
//			}
//		}
//	stateChanged();		
//	}
//
//	public void OutOfFood(int table, String food)
//	{
//		//Fill this in
//		for(MyCustomer mc : MyCustomers)
//		{
//			if(mc.table == table && mc.choice == food)
//			{
//				mc.state = myCustomerState.OutOfOrder;
//				stateChanged();
//			}
//			
//		}
//	}
//
//	//Cashier Messages
//	public void ThisIsTheCheck(SeafoodRestaurantCustomer cust, SeafoodRestaurantCheck ch)
//	{
//		//print("Tried giving check.");
//		for( MyCustomer mc : MyCustomers)
//		{
//			if(mc.c == cust)
//			{
//			//	print("Got correct customer.");
//				mc.CustomersCheck = ch;
//				mc.state = myCustomerState.CheckReceived;
//				receivingCheck.release();
//				stateChanged();
//				//Add a State?
//			}
//		}
//	}
//	
//	
//	
//	//Semaphore release messages
//	public void msgAtTable() 
//	{//from animation
//		//print("msgAtTable() called");
//		if(atTable.availablePermits() <1 )
//		{
//		atTable.release();// = true;
//		stateChanged();	
//		}
//	}
//	
//	public void msgAtKitchen()
//	{
//		if(atKitchen.availablePermits() <1 )
//		{
//			atKitchen.release();
//			stateChanged();
//		}
//	}
//	
//	public void msgAtCustomerLobby()
//	{
//			atCustomerLobby.release();
//			//AtHomeboolean = true;
//			stateChanged();
//	}
//	
//	public void msgAtHomePosition()
//	{
//		AtHomeboolean = true;
//		stateChanged();
//	}
//	
//	public void msgNotAtFrontDesk()
//	{
//		//AtFrontDesk = false;
//		//stateChanged();
//	}
//	
//	public void msgAtBreakRoom()
//	{
//		state = WaiterState.InBreakRoom;
//	}
//	
//	public void msgAtCashier()
//	{
//		if(atCashier.availablePermits() < 1)
//		{
//		atCashier.release();
//		}
//	}
//	
//	
//	
//	
//	/**
//	 * Scheduler.  Determine what action is called for, and do it.
//	 */
//	public boolean pickAndExecuteAnAction() 
//	{
//		/* Think of this next rule as:
//            Does there exist a table and customer,
//            so that table is unoccupied and customer is waiting.
//            If so seat him at the table.
//		 */
//	
//		if (leaveRole) 
//		{
//			AskHostToLeave();
//		}
//		
//	try
//	{
//		for(MyCustomer mc : MyCustomers)
//		{
//			
//				//print("On Customer: "+ mc.c);
//				//WAITER ON BREAK STUFF ******************************
//				if(mc.state == myCustomerState.Waiting && state == WaiterState.Working)
//				{
//					//print("I need to seat someone " + mc.c);
//					seatCustomer(mc);//the action
//					return true;//return true to the abstract agent to reinvoke the scheduler.
//				}
//			
//				if(mc.state == myCustomerState.readyToOrder)
//				{
//					TakeOrder(mc);
//					return true;
//				}
//				
//				if(mc.state == myCustomerState.OrderReceived)
//				{
//					SendOrder(mc);
//					return true;
//				}
//				
//				if(mc.state == myCustomerState.OutOfOrder)
//				{
//					RejectCustomerOrder(mc);
//					return true;
//				}
//				
//				if(mc.state == myCustomerState.DeliveringMeal)
//				{
//					DeliverMeal(mc);
//					return true;
//				}
//				
//				if(mc.state == myCustomerState.AskedForCheck)
//				{
//					RetrieveCheck(mc);
//					return true;
//				}
//				
//				if(mc.state == myCustomerState.CheckReceived)
//				{
//					DeliverCheck(mc);
//					return true;
//				}
//				
//				if(mc.state == myCustomerState.Leaving)
//				{
//					clearTable(mc);
//					return true;
//				}
//				//else
//				//{
//				//	relax();
//				//	return true;
//				//}
//			}
//		
//		//WAITER ON BREAK STUFF ******************************
//		//Asking Host
//		if(state == WaiterState.WantToGoOnBreak && MyCustomers.isEmpty())
//		{
//			AskHost();
//			return true;
//		}
//		
//		if(state == WaiterState.InBreakRoom)
//		{
//			relax();
//			return true;
//		}
//		
//		//WAITER ON BREAK STUFF ******************************
//		//rejected
//		if(state == WaiterState.CannotGoOnBreak)
//		{
//			TellHost();
//			return true;
//		}
//		
//		//WAITER ON BREAK STUFF ******************************
//		////coming back from break
//		if(state == WaiterState.WantToGoOffBreak)
//		{
//			TellHost();
//			return true;
//		}
//		
//		//WAITER ON BREAK STUFF ******************************
//		//actually on break
//		if(state == WaiterState.OnWayToBreak)
//		{
//			GoOnBreak();
//			return true;
//		}
//	}
//	catch(ConcurrentModificationException e)
//	{
//		return false;
//	}
//		
//		return false;
//		//we have tried all our rules and found
//		//nothing to do. So return false to main loop of abstract agent
//		//and wait.
//	}
//
//	
//	
//	
//	
//	
//	///// Actions
//	//WAITER ON BREAK STUFF ******************************
//	protected void AskHost()
//	{
//		state = WaiterState.AskedHost;
//		Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.CanIGoOnBreak(this);
//	}
//	
//	//WAITER ON BREAK STUFF ******************************
//	protected void TellHost()
//	{
//		state = WaiterState.Working;
//		print("I am coming back to work.");
//		requestingBreak = false;
//		onBreak = false;
//		waiterGui.setOffBreakbool();
//		waiterGui.GoHomePosition();
//		Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.BackToWork(this);
//		//stateChange
//	}
//	
//	protected void seatCustomer(MyCustomer mc) 
//	{
//		AtHomeboolean = false;
//		if(waiterGui.getXPos() != mc.xCordCustomerLobby && waiterGui.getYPos() != mc.yCordCustomerLobby)
//		{
//			print("Aha!");
//			waiterGui.GoToCustomerLobby(mc.c.getCustomerNumber());
//			try 
//			{
//				atCustomerLobby.acquire();
//				//atFrontDesk.acquire();
//			} catch (InterruptedException e) 
//			{
//				e.printStackTrace();
//			}
//		}
//		print("Here!");
//		SeafoodRestaurantMenu menuforCust = new SeafoodRestaurantMenu();
//		mc.c.followMe(menuforCust, this, mc.table);
//		DoSeatCustomer(mc.c, mc.table);
//		try 
//		{
//			atTable.acquire();
//		} catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//		//Leaving Customers
//		atCustomerLobby.tryAcquire();
//		waiterGui.GoHomePosition();
//		mc.state = myCustomerState.Seated;
//
//	}
//
//	// The animation DoXYZ() routines
//	protected void DoSeatCustomer(SeafoodRestaurantCustomerRole customer, int table) {
//		//Notice how we print "customer" directly. It's toString method will do it.
//		//Same with "table"
//		print("Seating " + customer + " at table " + table);
//		atCustomerLobby.tryAcquire();
//		waiterGui.GoToTable(customer); 
//
//	}
//
//	public void TakeOrder(MyCustomer mc)
//	{
//		
//		DoGoToTable(mc.c);
//		try 
//		{
//			atTable.acquire();
//		}
//		catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//		mc.c.WhatDoYouWant();
//		mc.state = myCustomerState.TakingOrder;	
//		try 
//		{
//			receivingOrder.acquire();
//		}
//		catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//	}
//	
//	protected void DoGoToTable(SeafoodRestaurantCustomerRole cust)
//	{
//		//REMEMBER TO DO THIS
//		//AFFECTS GUI!!!!!!!!!
//		print("Going to Table" + cust.getCurrentTable() + " Customer " + cust);
//		atCustomerLobby.tryAcquire();
//		waiterGui.GoToTable(cust);
//		
//	}
//	
//	public void SendOrder(MyCustomer mc)
//	{
//		DoGoToCook(); //REMEMBER TO PASS CHEF AS A PARAMETER
//		try 
//		{
//			atKitchen.acquire();
//		} catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//		waiterGui.GoHomePosition();
//		Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantCookRole.pleaseCook(mc.choice, mc.table, this);
//		print("Message 7 - Sent Order");
//		mc.state = myCustomerState.OrderSent;
//	}
//	
//	protected void DoGoToCook() //REMEMBER TO PASS CHEF AS A PARAMETER
//	{
//		//REMEMBER TO DO THIS
//		//AFFECTS GUI!!!!!!
//		print("Going to Kitchen");
//		atCustomerLobby.tryAcquire();
//		waiterGui.GoToKitchen();
//	}
//	
//	public void RejectCustomerOrder(MyCustomer mc)
//	{
//		DoGoToCook();
//		try 
//		{
//			atKitchen.acquire();
//		} catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//		//print ("At Kitchen " + atTable.toString());
//		///Do we need to carry the order
//		waiterGui.DoDeliver(" :( ");
//		DoGoToTable(mc.c);
//		try 
//		{
//			atTable.acquire();
//			atTable.acquire();
//		} catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//		print("We are unfortunately out of " + mc.choice);
//		mc.c.OutOfChoice(mc.choice);
//		//print("We are unfortunately out of " + mc.choice);
//		//atFrontDesk.tryAcquire();
//		waiterGui.DoDeliver("");
//		mc.state = myCustomerState.Seated;
//		//mc.state = myCustomerState.Leaving;
//		//atFrontDesk.tryAcquire();
//		waiterGui.GoHomePosition();
//	}
//	
//	public void DeliverMeal(MyCustomer mc)
//	{
//		DoGoToCook();
//		try 
//		{
//			atKitchen.acquire();
//		} catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//		//print ("At Kitchen " + atTable.toString());
//		///Do we need to carry the order
//		waiterGui.DoDeliver(mc.choice);
//		Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantCookRole.PickedUpOrder(mc.choice);
//		DoGoToTable(mc.c);
//		try 
//		{
//			atTable.acquire();
//			atTable.acquire();
//		} catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//		//Do we need to pass in a "food" item
//		//print ("At table " + atTable.toString());
//		mc.c.HereIsYourOrder(mc.choice);
//		atCustomerLobby.tryAcquire();
//		waiterGui.DoDeliver("");
//		print("Message 9 Sent - Delivering Meal");
//		mc.state = myCustomerState.Eating;
//		atCustomerLobby.tryAcquire();
//		waiterGui.GoHomePosition();
//	}
//	
//	public void RetrieveCheck(MyCustomer mc)
//	{
//		
//		DoGoToCashier();
//		try 
//		{
//			atCashier.acquire();
//		} catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//		//Line originally here
//		print("At Cashier");
//		waiterGui.DoDeliver("Check");
//		mc.state = myCustomerState.GettingCheck;
//		Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantCashierRole.GiveMeCheck(mc.choice, mc.c, this);
//		try 
//		{
//			receivingCheck.acquire();
//		}
//		catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//	}
//
//	protected void DoGoToCashier() //REMEMBER TO PASS CHEF AS A PARAMETER
//	{
//		//REMEMBER TO DO THIS
//		//AFFECTS GUI!!!!!!
//		print("Going to Cashier");
//		atCustomerLobby.tryAcquire();
//		waiterGui.GoToCashier();
//	}
//	
//	public void DeliverCheck(MyCustomer mc)
//	{
//		DoGoToTable(mc.c);
//		try 
//		{
//			atTable.acquire();
//		}
//		catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//		mc.c.HereIsYourCheck(mc.CustomersCheck);
//		waiterGui.DoDeliver("");
//		print("Delivered Check");
//		mc.state = myCustomerState.GivenCheckToCustomer;
//		atCustomerLobby.tryAcquire();
//		waiterGui.GoHomePosition();
//	}
//	
//	public void clearTable(MyCustomer mc)
//	{
//		//No longer using Tables in Waiter Agent
//		//for (Table table : Tables) 
//		//{
//			//if (table.getOccupant() == mc.c) 
//			//{
//				print(mc.c + " leaving table " + mc.table);
//				//table.setUnoccupied();
//				mc.state = myCustomerState.Left;
//				Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.TableIsFree(mc.table, this);
//				print("Message 11 Sent, " + mc.c + " has left, " + mc.table + " is free");
//				MyCustomers.remove(mc);
//			//}
//		//}
//	}
//	
//	//WAITER ON BREAK STUFF ******************************
//	public void GoOnBreak()
//	{
//		onBreak = true;
//		requestingBreak = false;
//		doGoOnBreak();
//	}
//	
//	//WAITER ON BREAK STUFF ******************************
//	protected void doGoOnBreak()
//	{
//		waiterGui.GoToBreakRoom();
//		/*
//		timer.schedule(new TimerTask() 
//		{
//			public void run() 
//			{
//				state = WaiterState.WantToGoOffBreak;
//				stateChanged();
//			}
//		},
//		10000);
//		*/
//	}	
//	
//	protected void AskHostToLeave()	{
//	Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.msgIAmLeavingSoon(this);
//	if(MyCustomers.isEmpty())
//	{
//		Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.msgIAmLeavingWork(this);
//		((Worker) person).roleFinishedWork();
//		leaveRole = false;
//	}
//}
//	
//	public void relax()
//	{
//		//Hack, make the click button work
//		state = WaiterState.Relaxing;
//		relaxTimer.schedule(new TimerTask() 
//		{
//			public void run() 
//			{
//				print("It's been a while, I should get back to work.");
//				state = WaiterState.WantToGoOffBreak;
//				stateChanged();
//			}
//		},
//		10000);
//		//doRelax();
//	}
//	
//	public void doRelax()
//	{
//		waiterGui.DoRelax();
//	}
//	
//	
//	//utilities
//
//	public void setGui(SeafoodRestaurantWaiterGui gui) 
//	{
//		waiterGui = gui;
//	}
//
//	public SeafoodRestaurantWaiterGui getGui() 
//	{
//		return waiterGui;
//	}
//	
//	public boolean isOnBreak()
//	{
//		return onBreak;
//	}
//	
//	public boolean isRequestingBreak()
//	{
//		return requestingBreak;
//	}
//
//}
//
