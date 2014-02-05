//package seafoodRestaurant;
//
////import restaurant.gui.CustomerGui;
////import restaurant.interfaces.Customer;
//import seafoodRestaurant.interfaces.SeafoodRestaurantCustomer;
////import restaurant.gui.RestaurantGui;
//import agent.Agent;
//import application.Phonebook;
//import application.gui.animation.agentGui.RestaurantCustomerGui;
//import application.gui.animation.agentGui.SeafoodRestaurantCustomerGui;
//
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.concurrent.Semaphore;
//
//import chineseRestaurant.ChineseRestaurantCustomerRole.AgentEvent;
//import chineseRestaurant.ChineseRestaurantCustomerRole.AgentState;
//import person.Person;
//import person.Role;
//
//
///**
// * Restaurant customer agent.
// */
//public class SeafoodRestaurantCustomerRole extends Role implements SeafoodRestaurantCustomer 
//{
//	private int hungerLevel = 5;        // determines length of meal
//	Timer timer = new Timer();
//	private SeafoodRestaurantCustomerGui customerGui;
//	private int currentTable;
//	private SeafoodRestaurantMenu myMenu;
//	private String myOrder;
//	private Semaphore waitingForWaiter = new Semaphore(0, true);
//	//private Semaphore GoingToCashier = new Semaphore(0, true);
//	private int DecidingFoodTime = 8000;
//	private int SpeakingFoodTime = 2000;
//	private int EatingFoodTime = 5000;
//	private int customerNumber;
//	private SeafoodRestaurantCheck myCheck;
//	private double Cash = 20.00; //8.00; //4.00;
//	private double Debt = 0.00; //What Do I do with debt
//	public boolean DineAndDash = false; // true; //false;
//	public boolean WaitingToBeSeated = false;
//	
//	// agent correspondents
//	private SeafoodRestaurantWaiterRole waiter;
//	//private SeafoodRestaurantHostRole host;
//	//private SeafoodRestaurantCashierRole Cashier;
//	//CashierRole
//	
//	//    private boolean isHungry = false; //hack for gui
//	public enum AgentState
//	{DoingNothing, WaitingInRestaurant, BeingSeated, Seated, ChoosingOrder, ReadyToOrder, GivingOrder, SayingOrder, Ordering, WaitingForFood, FoodReceived, Eating, AskedForCheck, WaitingForCheck, CheckReceived, GoingToCashier, Paying, donePaying, Leaving};
//	private AgentState state = AgentState.DoingNothing;//The start state
//
//	public enum AgentEvent 
//	{none, gotHungry, followMe, seated, doneEating, atCashier, doneLeaving};
//	AgentEvent event = AgentEvent.none;
//
//	/**
//	 * Constructor for CustomerRole class
//	 *
//	 * @param name name of the customer
//	 * @param gui  reference to the customergui so the customer can send it messages
//	 */
//	public SeafoodRestaurantCustomerRole(String name, SeafoodRestaurantHostRole h, SeafoodRestaurantCashierRole cas, int n) //CashierRole
//	{
//		super(name);
//		
//		//this.host = h;
//		//this.cashier = cas;
//		this.customerNumber = n;
//	}
//
//	public SeafoodRestaurantCustomerRole(Person p1, String pName, String rName) 
//	{
//		super(p1, pName, rName);
//		state = AgentState.DoingNothing; 
//		event = AgentEvent.gotHungry;
//		//this.money = p1.money;
//		//gui = new RestaurantCustomerGui(this);
//	}
//	
//	/**
//	 * hack to establish connection to AmericanRestaurantWaiter agent.
//	 */
//	public void setWaiter(SeafoodRestaurantWaiterRole waiter) 
//	{
//		this.waiter = waiter;
//	}
//	
////	public void setHost(SeafoodRestaurantHostRole host)
////	{
////		this.host = host;
////	}
//
//	public String getCustomerName() 
//	{
//		return getName();
//	}
//	
//	
//	
//	
//	///// Messages
//
//	public void gotHungry() 
//	{//from animation
//		print("I'm hungry");
//		event = AgentEvent.gotHungry;
//		stateChanged();
//	}
//
//	public void followMe(SeafoodRestaurantMenu m, SeafoodRestaurantWaiterRole w, int t) 
//	{
//		WaitingToBeSeated = false;
//		print("Message 3 Sent - Following AmericanRestaurantWaiter");
//		myMenu = m;
//		waiter = w;
//		currentTable = t;
//		event = AgentEvent.followMe;
//		stateChanged();
//	}
//	
//	public void WhatDoYouWant()
//	{
//		print("Message 5 Sent - Ordering Food");
//		waitingForWaiter.release();
//		//state = AgentState.GivingOrder;
//		stateChanged();
//	}
//	
//	public void OutOfChoice(String o)
//	{
//		myMenu.FoodMenu.get(o).setUnavailable();
//		print("Okay, I will reorder.");
//		state = AgentState.ReadyToOrder;
//		stateChanged();
//		/*
//		print("Okay, I will leave.");
//		//Hack, should reorder
//		state = AgentState.Eating;
//		event = AgentEvent.doneEating;
//		stateChanged();	
//		*/	
//		
//	}
//
//	public void HereIsYourOrder(String o)
//	{
//		state = AgentState.FoodReceived;
//		stateChanged();
//	}
//	
//	//AmericanRestaurantCashier Stuff
//	public void HereIsYourCheck(SeafoodRestaurantCheck ch)
//	{
//		myCheck = ch;
//		customerGui.DoOrder("Check");
//		state = AgentState.CheckReceived;
//		stateChanged();
//	}
//	
//	//AmericanRestaurantCashier stuff, Getting Change
//	public void HereIsYourChange(double c, double d)
//	{
//		Cash = c;
//		Debt = d; //+=
//		print("I am receiving $" + c + " as change and I have $" + d + " as my debt");
//		print("I have $" + Cash + " and $" + Debt + " as debt");
//		//What happens if you have a debt?
//		state = AgentState.donePaying;
//		stateChanged();
//	}
//	
//	public void ImpatientNoMoreSeats()
//	{
//		print("It's a full restaurant, I think I'm just going to leave.");
//		state = AgentState.donePaying;
//		event = AgentEvent.atCashier;
//		stateChanged();
//	}
//	
//	public void msgAnimationFinishedGoToSeat() 
//	{
//		//from animation
//		event = AgentEvent.seated;
//		stateChanged();
//	}
//	
//	public void msgAnimationFinishedGoToCashier()
//	{
//		event = AgentEvent.atCashier;
//		stateChanged();
//	}
//	
//	public void msgAnimationFinishedLeaveRestaurant() 
//	{
//		//from animation
//		event = AgentEvent.doneLeaving;
//		stateChanged();
//	}
//	
//	
//	
//	
//	
//	/**
//	 * Scheduler.  Determine what action is called for, and do it.
//	 */
//	public boolean pickAndExecuteAnAction() 
//	{
//		//	CustomerRole is a finite state machine
//		if (state == AgentState.DoingNothing && event == AgentEvent.gotHungry )
//		{
//			state = AgentState.WaitingInRestaurant;
//			goToRestaurant();
//			return true;
//		}
//		if (state == AgentState.WaitingInRestaurant && event == AgentEvent.followMe )
//		{
//			state = AgentState.BeingSeated;
//			SitDown();
//			return true;
//		}
//		if (state == AgentState.BeingSeated && event == AgentEvent.seated)
//		{
//			state = AgentState.ChoosingOrder;
//			choosingOrderTime();
//			return true;
//		}
//		if(state == AgentState.ReadyToOrder && event == AgentEvent.seated)
//		{
//			state = AgentState.GivingOrder;
//			chooseOrder();
//			return true;
//		}
//		if(state == AgentState.GivingOrder && event == AgentEvent.seated)
//		{
//			state = AgentState.SayingOrder;
//			givingOrderTime();
//			return true;
//		}
//		if(state == AgentState.Ordering && event == AgentEvent.seated)
//		{
//			state = AgentState.WaitingForFood;
//			IWantToOrder(myOrder);
//			return true;
//		}
//		if(state == AgentState.FoodReceived && event == AgentEvent.seated)
//		{
//			state = AgentState.Eating;
//			EatFood();
//			return true;
//		}
//		//NEED TO GO THROUGH Seated, ChoosingOrder, Ordering, waitingforFood, then go to Eating
//		if (state == AgentState.Eating && event == AgentEvent.doneEating)
//		{
//			///change from here
//			state = AgentState.AskedForCheck;
//			AskForCheck();
//			return true;
//			/*
//			state = AgentState.Leaving;
//			leaveTable();
//			return true;
//			*/
//		}
//		
//		if(state == AgentState.CheckReceived && event == AgentEvent.doneEating)
//		{
//			state = AgentState.GoingToCashier;
//			HeadToCashier();
//			return true;
//			
//		}
//		
//		if(state == AgentState.GoingToCashier && event == AgentEvent.atCashier)
//		{
//			state = AgentState.Paying;
//			PayForFood();
//			return true;
//			
//		}
//		
//		if(state == AgentState.donePaying && event == AgentEvent.atCashier)
//		{
//			state = AgentState.Leaving;
//			leaveTheRestaurant();
//			return true;
//		}
//		
//		if (state == AgentState.Leaving && event == AgentEvent.doneLeaving)
//		{
//			state = AgentState.DoingNothing;
//			event = AgentEvent.none;
//			//no action
//			return true;
//		}
//		return false;
//	}
//
//	
//	
//	
//	
//	///// Actions
//
//	private void goToRestaurant() 
//	{
//		customerGui.DoEnterRestaurant();
//		print("Going to restaurant");
//		//waiter.msgIWantFood(this);//send our instance, so he can respond to us
//		Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.IWantFood(this);
//		WaitingToBeSeated = true;
//	}
//
//	private void SitDown() 
//	{
//		print("Being seated. Going to table");
//		customerGui.DoGoToSeat(currentTable);//hack; only one table
//	}
//	
//	private void choosingOrderTime()
//	{
//		timer.schedule(new TimerTask() 
//		{
//			public void run() 
//			{
//				state = AgentState.ReadyToOrder;
//				stateChanged();
//			}
//		},
//		DecidingFoodTime);
//		
//	}
//	
//	private void chooseOrder()
//	{
//		myOrder = myMenu.blindPick();
//		if((Cash<myMenu.FoodMenu.get("Clam Chowder Sourdough Bowl").price) && !DineAndDash)
//		{
//			print("Oh man, I can't even afford a Clam Chowder Sourdough Bowl. I should leave.");
//			myOrder = "";
//			customerGui.DoOrder("");
//			waiter.iAmLeavingTable(this);
//			state = AgentState.donePaying;
//			event = AgentEvent.atCashier;
//			stateChanged();
//			return;
//		}
//		if((Cash<myMenu.FoodMenu.get("Grilled Shrimp Skewers").price) && !DineAndDash && !(myMenu.FoodMenu.get("Clam Chowder Sourdough Bowl").Available))
//		{
//			print("I only have enough for a Clam Chowder Sourdough Bowl. But they're out! I'll leave.");
//			myOrder = "";
//			customerGui.DoOrder("");
//			waiter.iAmLeavingTable(this);
//			state = AgentState.donePaying;
//			event = AgentEvent.atCashier;
//			stateChanged();
//			return;
//		}		
//		if((Cash<myMenu.FoodMenu.get("Grilled Shrimp Skewers").price) && !DineAndDash && (myMenu.FoodMenu.get("Clam Chowder Sourdough Bowl").Available))
//		{
//			print("I only have enough for a Clam Chowder Sourdough Bowl. I'll get that.");
//			myOrder = "Clam Chowder Sourdough Bowl";
//		}
//		
//		
//		waiter.ReadyToOrder(this);
//		
//		
//		//Hack to Check different foods
//		if(getName().equals("ClamChowderSourdough"))
//		{
//			myOrder = "Clam Chowder Sourdough Bowl";
//		}
//		if(getName().equals("grilledShrimpskewers"))
//		{
//			myOrder = "Grilled Shrimp Skewers";
//		}
//		if(getName().equals("bourbonGlazedSalmon"))
//		{
//			myOrder = "Bourbon-Glazed Salmon";
//		}
//		if(getName().equals("lobsterTailAndRoll"))
//		{
//			myOrder = "Lobster Tail and Roll";
//		}
//		
//		print("Message 4 Sent - Chosen Order");
//		try 
//		{
//			waitingForWaiter.acquire();
//		} catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//	}
//	
//	private void givingOrderTime()
//	{
//		timer.schedule(new TimerTask() 
//		{
//			public void run() 
//			{
//				state = AgentState.Ordering;
//				stateChanged();
//			}
//		},
//		SpeakingFoodTime);
//	}
//	
//	private void IWantToOrder(String order)
//	{
//				state = AgentState.WaitingForFood;
//				waiter.myChoiceIs(order, this);
//				customerGui.DoOrder(order+"?");
//				print("Message 6 Sent - Gave Order: " + order);
//				stateChanged();
//	}
//
//	private void EatFood() 
//	{
//		customerGui.DoOrder(myOrder);
//		print("Eating Food");
//		//This next complicated line creates and starts a timer thread.
//		//We schedule a deadline of getHungerLevel()*1000 milliseconds.
//		//When that time elapses, it will call back to the run routine
//		//located in the anonymous class created right there inline:
//		//TimerTask is an interface that we implement right there inline.
//		//Since Java does not all us to pass functions, only objects.
//		//So, we use Java syntactic mechanism to create an
//		//anonymous inner class that has the public method run() in it.
//		timer.schedule(new TimerTask() 
//		{
//			Object cookie = 1;
//			public void run() 
//			{
//				print("Done eating, cookie=" + cookie);
//				event = AgentEvent.doneEating;
//				//isHungry = false;
//				stateChanged();
//			}
//		},
//		EatingFoodTime);//getHungerLevel() * 1000);//how long to wait before running task
//	}
//	
//	private void AskForCheck()
//	{
//		customerGui.DoOrder("Check?");
//		waiter.CanIGetMyCheck(this);
//	}
//	
//	private void HeadToCashier()
//	{
//		currentTable = 0;
//		myMenu = null;
//		myOrder = "Check";
//		waiter.iAmLeavingTable(this);
//		customerGui.GoToCashier();
//	}
//	
//	private void PayForFood()
//	{
//		myOrder = "";
//		customerGui.DoOrder("");
//		if(Debt > 0)
//		{
//			myCheck.cost += Debt;
//			print("I have a debt. I will add it to the bill.");
//		}
//		print("I am giving the americanRestaurantCashier $" + Cash);
//		Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantCashierRole.HereIsPayment(myCheck, Cash);
//		Cash = 0;
//	}
//
//	private void leaveTheRestaurant() 
//	{
//		//customerGui.DoOrder("");
//		//Breaking up this method
//		//currentTable = 0;
//		//myMenu = null;
//		//myOrder = "";
//		print("Leaving.");
//		//waiter.iAmLeavingTable(this);
//		print("Message 10 Sent - I am leaving");
//		customerGui.DoExit();
//	}
//
//	// Accessors, etc.
//
//	public String getName() 
//	{
//		return getName();
//	}
//	
//	public int getHungerLevel() 
//	{
//		return hungerLevel;
//	}
//
//	public void setCash(double number)
//	{
//		Cash = number;
//	}
//	
//	public void setHungerLevel(int hungerLevel) 
//	{
//		this.hungerLevel = hungerLevel;
//		//could be a state change. Maybe you don't
//		//need to eat until hunger lever is > 5?
//	}
//
//	public void setCurrentTable(int table)
//	{
//		currentTable = table;
//	}
//	
//	public int getCurrentTable()
//	{
//		return currentTable;
//	}
//	
//	public String toString() 
//	{
//		return "customer " + getName();
//	}
//
//	public void setGui(SeafoodRestaurantCustomerGui g) 
//	{
//		customerGui = g;
//	}
//
//	public SeafoodRestaurantCustomerGui getGui() 
//	{
//		return customerGui;
//	}
//	
//	public int getCustomerNumber()
//	{
//		return customerNumber;
//	}
//
//	public void msgComeIn() {
//		stateChanged();
//	}
//}
//
