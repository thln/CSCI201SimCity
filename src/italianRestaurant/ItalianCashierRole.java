package italianRestaurant;

import person.*;
import application.gui.animation.agentGui.*;
import italianRestaurant.interfaces.*;

import java.util.*;
import java.lang.*;
import java.util.concurrent.Semaphore;
import market.interfaces.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Restaurant Cook Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the AmericanRestaurantCashierRole. A AmericanRestaurantCashier is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class ItalianCashierRole extends Role implements ItalianCashier{
	static final int NTABLES = 5;//a global for the number of americanRestaurantTables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	private Timer exchangetimer = new Timer();
	private Timer paytimer = new Timer();
	public List<Bill> BillOrders = Collections.synchronizedList(new ArrayList<Bill>());
	public List<Bill> BillFoods = Collections.synchronizedList(new ArrayList<Bill>());
	private List<Food> Foods = Collections.synchronizedList(new ArrayList<Food>());
	//note that americanRestaurantTables is typed with Collection semantics.
	//Later we will see how it is implemented
	private ItalianCook cook;
	private ItalianRestaurant restaurant = null;
	
	private Double cashregister;
	Double tax = 0.09;
	Double tip = 0.15;
	//private Semaphore atTable = new Semaphore(0,true);
	
	public ItalianCashierGui cashierGui = null;
	protected String RoleName = "Cashier";

	public ItalianCashierRole(String name, ItalianRestaurant restaurant) {
		super(name);
		this.restaurant = restaurant;
		//initializing food inventories when application starts
		Foods.add(new Food("Steak", 5, 15.99)); //name, computing time, price
		Foods.add(new Food("Chicken", 2, 10.99)); 
		Foods.add(new Food("Salad", 3, 5.99));
		Foods.add(new Food("Pizza", 4, 8.99));

		//initializing amount in cash register
		cashregister = 1000.00;
	}

	// Messages
	public void msgComputeBill(ItalianWaiter w, ItalianCustomer c, String choice) {
		synchronized(Foods){
		for(int i=0; i<Foods.size();i++) {
			if(Foods.get(i).type.equals(choice)) {
				BillOrders.add(new Bill(w, c, Foods.get(i)));
				print(w + " wants bill for " + c);
				stateChanged();
			}
		}
		}
	}
	
	public void msgHereIsMoney(ItalianCustomer c, Double payment) {
		synchronized(BillOrders){
		for(int i=0; i<BillOrders.size();i++){
			if(BillOrders.get(i).c.equals(c)) {
				print(c + " has given $" + payment + " as payment");
				BillOrders.get(i).change = payment - BillOrders.get(i).total;
				BillOrders.get(i).s = BillState.paying;
				stateChanged();
			}
		}
		}
	}
	
	
	//message from ItalianMarket to italianRestaurantCashier to give payment for bill for restocking
	public void msgRestockingBill(ItalianMarket market, String foodname, Double billtotal) {
		print("Received bill of " + billtotal + " from " + market + " for " + foodname);
		BillFoods.add(new Bill(market, billtotal));
		stateChanged();
	}
	
	
	public void msgPleasePayForItems(String foodname, String orderAmt, Double billtotal, SalesPerson sp) {
		print("Received bill of " + billtotal + " from " + sp.toString() + " for " + foodname);
		int order = Integer.parseInt(orderAmt);
		BillFoods.add(new Bill(sp, billtotal, foodname, order));
		stateChanged();
	}
	
	public void BillDone(Bill o) {
		//o.f.inventory--;
		o.s = BillState.done;
		print(o + " is done Computing!");
		//System.out.print(o.f.inventory + "\n");
		stateChanged();
		
	}
	
	public void ExchangeDone(Bill o) {
		String receipt = "Total $" + Double.toString(o.total);
		cashregister += o.total;
		print("Cash register now has $" + cashregister);
		synchronized(BillFoods){
		for(int i=0; i<BillFoods.size();i++){
			if(BillFoods.get(i).s == BillState.paying) {
				if(BillFoods.get(i).change <= cashregister)
					BillFoods.get(i).s = BillState.pending;
			}
		}
		}
		o.c.msgChangeAndReceipt(receipt, o.change);
		BillOrders.remove(o);
		stateChanged();
	}
	
	public void PaymentDone(Bill o) {
		cashregister -= o.total;
		o.m.msgHereIsPayment(this, o.total);
		BillFoods.remove(o);
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
		
		if(BillOrders != null) {
			synchronized(BillOrders){
				for(int i=0; i<BillOrders.size();i++){
					if(BillOrders.get(i).s == BillState.done) {
						ChecktoWaiter(BillOrders.get(i));
						return true;
					}
					else if(BillOrders.get(i).s == BillState.pending) {
						Compute(BillOrders.get(i));
						return true;				
					}
					else if(BillOrders.get(i).s == BillState.paying) {
						MoneyExchange(BillOrders.get(i));
					}
				}
			}
		}
		
		if(BillFoods != null) {
			synchronized(BillFoods){
				for(int i=0; i<BillFoods.size();i++){
					if(BillFoods.get(i).s == BillState.pending) {
						PayBill(BillFoods.get(i));
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
	
	
	private void Compute(Bill o) {
		print("Computing " + o);
		//cashierGui.DoComputing();
		o.total = o.f.price + o.f.price*tax + o.f.price*tip;
		//System.out.print(o.total + "\n");
		o.s = BillState.computed;
		BillDone(o);
		
		/*
		exchangetimer.schedule(new TimerTask() {
			public void run() {
				BillDone(o);
			}
		},
		o.f.computingTime*1000);
		*/
	}
	
	private void ChecktoWaiter(Bill o) {
		print("Giving Check to ItalianWaiter");
		//cookGui.DoPlateIt(/*o.choicename*/);
		o.w.msgHereIsCheck(o.c, o.total);
		o.s = BillState.finished;
	}
	
	private void MoneyExchange(final Bill o) {
		if(o.change < 0)
			o.c.YouOweUs(Math.abs(o.change));
		else {
		print("Exchanging given cash for change and receipt for " + o.c);
		exchangetimer.schedule(new TimerTask() {
			public void run() {
				ExchangeDone(o);
			}
		},
		4*1000);
		}
	}
	
	private void PayBill(final Bill o) {
		
		/*if americanRestaurantCashier does NOT have enough money in the cash register, 
		 * it will tell the Cook to stop restocking food until it 
		 * gets enough money from customers to pay the bill
		 */
		if(cashregister < o.total) {
			o.change = o.total - cashregister;
			o.m.msgCashierStillOwes(this, o.change);
			cook.setAllowedOrder(false);
			o.s = BillState.paying;
		}
		else {
			print("Paying " + o.total + " to " + o.m);
			paytimer.schedule(new TimerTask() {
				public void run() {
					PaymentDone(o);
				}
			}, 3*1000);
		}
	}
	
	private void GiveMoneytoMarket(Bill o) {
		o.m.msgHereIsPayment(this, o.total);
		BillFoods.remove(o);
		o.s = BillState.finished;
	}

	//utilities
	public String toString() {
		return "AmericanRestaurantCashier " + getName();
	}
	
	public void setGui(ItalianCashierGui gui) {
		cashierGui = gui;
	}

	public ItalianCashierGui getGui() {
		return cashierGui;
	}
	
	public void setCook(ItalianCook c) {
		cook = c;
	}
	
	/*Order class stores the different customer orders
	 * the waiter gives to the americanRestaurantCashier, and its state
	 * of cashiering on the americanRestaurantCashier's "grill".
	 */
	
	public enum BillState {pending, computed, done, paying, finished};
	
	public class Bill {
		public SalesPerson salesPerson;
		public ItalianMarket m;
		public ItalianWaiter w;
		public ItalianCustomer c;
		public Food f;
		public Double total = 0.0;
		public Double change;
		public BillState s;
		public int orderAmt;
		
		Bill(ItalianWaiter waiter, ItalianCustomer cust, Food food) {
			w = waiter;
			c = cust;
			f = food;
			s = BillState.pending;
		}
		
		
		Bill(ItalianMarket market, Double bill) {
			m = market;
			total = bill;
			s = BillState.pending;
		}
		
		
		Bill(SalesPerson sp, Double bill, String foodname, int orderAmt) {
			this.salesPerson = sp;
			total = bill;
			this.orderAmt = orderAmt;
			s = BillState.pending;
		}
		
		public String toString() {
			return "bill for " + c;
		}
	}
	
	public enum FoodState {ordered, delivered};
	public class Food {
		String type;
		int computingTime;
		Double price;
		
		Food(String choice, int time, Double p) {
			//name, computing time, price
			type = choice;
			computingTime = time;
			price = p;
		}
		
		public String toString() {
			return type;
		}
	}

}

