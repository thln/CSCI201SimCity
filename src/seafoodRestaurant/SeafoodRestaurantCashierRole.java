//package seafoodRestaurant;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Collections;
////import restaurant.Menu;
//import java.util.List;
//
//import market.interfaces.SalesPerson;
//import person.Role;
//import person.Worker;
//import seafoodRestaurant.SeafoodRestaurantCheck.CheckState;
//import seafoodRestaurant.interfaces.SeafoodRestaurantCashier;
//import seafoodRestaurant.interfaces.SeafoodRestaurantCustomer;
////import seafoodRestaurant.interfaces.SeafoodRestaurantMarket;
//import seafoodRestaurant.interfaces.SeafoodRestaurantWaiter;
//import testing.EventLog;
////import restaurant.MarketAgent.Delivery;
//
////import restaurant.CustomerAgent.AgentState;
//
//public class SeafoodRestaurantCashierRole extends Role implements SeafoodRestaurantCashier
//{
//	/***** DATA *****/
//	private String name;
//	private SeafoodRestaurantMenu MenuForReference;
//	private SeafoodRestaurant seafoodRestaurant;
//	public double accumulatedRevenue = 35.00;
//	public double accumulatedCosts = 0.0;
//	public double profits = 35.00;
//	final DecimalFormat df = new DecimalFormat(); 
//	private boolean free = true;
//	public EventLog log; //necessary?
//	
//	public class MarketBill
//	{
//		public String foodItem;
//		public double finalTotal;
//		//public SeafoodRestaurantMarket market;
//		SalesPerson market;
//		MarketBill(String s, double c, SalesPerson m)
//		{
//			foodItem = s;
//			finalTotal = c;
//			market = m;
//		}
//	}
//	
//	/*
//	public enum CheckState
//	{Created, Pending, Paying, NotPaidOff, PaidOff};
//	
//	private class Check
//	{
//		String foodItem;
//		double cost;
//		double cash;
//		SeafoodRestaurantCustomerRole c;
//		SeafoodRestaurantWaiterRole w;
//		CheckState s = CheckState.Created;
//		public Check(String choice, SeafoodRestaurantCustomerRole cust, SeafoodRestaurantWaiterRole wait)
//		{
//			//STUB
//			foodItem = choice;
//			c = cust;
//			w = wait;
//		}
//	}
//	*/
//	
//	public List<SeafoodRestaurantCheck> AllChecks= Collections.synchronizedList(new ArrayList<SeafoodRestaurantCheck>()); //private
//	public List<MarketBill> MarketBills = Collections.synchronizedList(new ArrayList<MarketBill>());
//	//private List<MyCustomer> customers = 
//	//	    Collections.synchronizedList(new ArrayList<MyCustomer>()); example
//	
//	public SeafoodRestaurantCashierRole(String name, SeafoodRestaurant seafoodRestaurant)
//	{
//		super(name);
//		this.seafoodRestaurant = seafoodRestaurant;
//
//		MenuForReference = new SeafoodRestaurantMenu();
//		df.setMaximumFractionDigits(2);
//	}
//	
//	public String getName() 
//	{
//		return name;
//	}
//	
//	
//	/***** MESSAGES *****/
//	public void GiveMeCheck(String choice, SeafoodRestaurantCustomer cust, SeafoodRestaurantWaiter wait) //SeafoodRestaurantCustomerRole, SeafoodRestaurantWaiterRole
//	{
//		//STUB
//		AllChecks.add(new SeafoodRestaurantCheck(choice, cust, wait));
//		if(choice.equals("7.98"))
//		{
//			AllChecks.get(0).cost = 7.98;
//		}
//		stateChanged();
//	}
//	
//	public void HereIsPayment(SeafoodRestaurantCheck ch, double cash)
//	{
//		//ch.s = CheckState.CustomerHere;
//		//log.add(new LoggedEvent("Received HereIsMyPayment"));
//		//STUB
//		synchronized(AllChecks)
//		{
//			for(SeafoodRestaurantCheck c : AllChecks)
//			{
//				if(ch == c)
//				{
//					free = true;
//					c.s = CheckState.CustomerHere;
//					ch.cash = cash;
//					stateChanged();
//					return;
//				}
//			}
//		}
//	}
//	
//	public void MarketCost(String foodName, double cost, SalesPerson m)
//	{
//		print("I am adding a bill for " + cost + " and I have " + profits);
//		MarketBills.add(new MarketBill(foodName, cost, m));
//		stateChanged();
//	}
//	
//	
//	/***** SCHEDULER *****/
//	public boolean pickAndExecuteAnAction() 
//	{
//		synchronized(MarketBills)
//		{
//			for(MarketBill mb : MarketBills)
//			{
//				if(profits > mb.finalTotal)
//				{
//					MarketBills.remove(mb);
//					PayMarket(mb);
//					return true;
//				}
//			}
//		}
//		
//		//////FILL IN HERE
//		synchronized(AllChecks)
//		{
//			for(SeafoodRestaurantCheck c : AllChecks)
//			{
//				if(c.s == CheckState.Created)
//				{
//					ComputeCheck(c);
//					return true;
//				}
//				if(c.s == CheckState.CustomerHere)
//				{
//					GiveChange(c);
//					return true;
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
//	
//	/***** ACTIONS *****/
//	private void ComputeCheck(SeafoodRestaurantCheck ch)
//	{
//		//STUB
//		//ch.CalculateCost();
//		print("Calculating check for " + ch.c + " " + ch.w);
//		ch.s = CheckState.Pending;
//		if(!(ch.foodItem.equals("7.98")))
//		{
//			ch.cost = MenuForReference.GetPrice(ch.foodItem);
//		}
//		ch.w.ThisIsTheCheck(ch.c, ch);
//	}
//	
//	private void GiveChange(SeafoodRestaurantCheck ch)
//	{
//		//STUB
//		ch.s = CheckState.Paying;
//		print("The bill is $" + ch.cost);
//		ch.cost -= ch.cash;
//		//ch.cash = 0;
//		if(ch.cost > 0) //Not Paid Off
//		{
//			//include debt somehow in HereIsYourChange?
//			accumulatedCosts = (ch.cost); //-=, This does not take into account multiple bad people 
//			profits -= (ch.cost);
//			profits = Double.parseDouble(df.format(profits));
//			accumulatedCosts = Double.parseDouble(df.format(accumulatedCosts));
//			print("The restaurant debt is now " + accumulatedCosts );
//			ch.c.HereIsYourChange(0, ch.cost);
//			ch.s = CheckState.NotPaidOff;
//		}
//		else //Has Paid off 
//		{
//			accumulatedRevenue += (ch.cash + ch.cost);
//			profits += (ch.cash + ch.cost);
//			profits = Double.parseDouble(df.format(profits));
//			accumulatedRevenue = Double.parseDouble(df.format(accumulatedRevenue));
//			print("The restaurant revenue is now " + accumulatedRevenue + " and profits are " + profits );
//			ch.c.HereIsYourChange(-ch.cost, 0);
//			ch.s = CheckState.PaidOff;
//		}
//		ch.cash = 0;
//		if(free)
//		{
//			free = false;
//			stateChanged();
//		}
//	}
//	private void PayMarket(MarketBill mb)
//	{
//		print("PayMarket runs.");
//		print("I am paying for the bill " + mb.finalTotal + " and I have " + profits);
//		//mb.market.Paid(mb.finalTotal);
//		mb.market.msgPayment(seafoodRestaurant, mb.finalTotal);
//		mb.finalTotal = Double.parseDouble(df.format(mb.finalTotal));
//		accumulatedCosts += mb.finalTotal;
//		profits -= mb.finalTotal;
//		accumulatedCosts = Double.parseDouble(df.format(accumulatedCosts ));
//		profits = Double.parseDouble(df.format(profits));
//		print("I now have " + profits);
//		
//	}
//}
