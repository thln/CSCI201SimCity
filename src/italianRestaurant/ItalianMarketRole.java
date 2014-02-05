package italianRestaurant;

import person.*;
import application.gui.animation.agentGui.*;
import italianRestaurant.interfaces.*;

import java.util.*;
/**
 * Restaurant Market Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the MarketAgent. A Market is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class ItalianMarketRole extends Role implements ItalianMarket{
	static final int NTABLES = 5;//a global for the number of americanRestaurantTables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	private Timer restockingtimer = new Timer();
	private List<Order> Orders = Collections.synchronizedList(new ArrayList<Order>());
	private List<Food> Foods = Collections.synchronizedList(new ArrayList<Food>());
	//note that americanRestaurantTables is typed with Collection semantics.
	//Later we will see how it is implemented
	 
	//private Semaphore atTable = new Semaphore(0,true);
	
	public ItalianMarketGui marketGui = null;
	private ItalianCashier cashier;

	public ItalianMarketRole(String name) {
		super(name);
		Foods.add(new Food("Steak", 11, 15, 15.99)); //name, restocking time, inventory, low, price
		Foods.add(new Food("Chicken", 12, 20, 10.99)); 
		Foods.add(new Food("Salad", 10, 100, 5.99));
		Foods.add(new Food("Pizza", 13, 100, 8.99));
		
	}

	// Messages
	
	//message from waiter to market to fulfill inventory
	public void msgOrderforMarket(ItalianCook c, String choice, int amount) {
		int foodamount;
		synchronized(Foods){
		for(int i=0; i<Foods.size();i++) {
			if(Foods.get(i).type.equals(choice)) {
				if(Foods.get(i).inventory - amount > 0) {
					foodamount = amount;
				}
				else {
					foodamount = Foods.get(i).inventory;
				}
				Orders.add(new Order(c, Foods.get(i), foodamount, Foods.get(i).inventory - foodamount));
				print("Filling order from " + c + " for " + foodamount + " more " + Foods.get(i));
				stateChanged();
			}
		}
		}
		/*
		for(int i=0; i<Orders.size();i++){
			if(Orders.get(i).s == OrderState.pending)
				TryRestockOrder(Orders.get(i));
		}*/
	}
	
	public void RestockDone(Order o) {
		o.food.inventory = o.food.inventory - o.food.orderamt;
		o.s = OrderState.done;
		print("Done restocking " + o.food);
		System.out.print(o.food.inventory + "\n");
		//o.c.msgOrderDone(o.food.type);
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
		
		if(Orders != null) {
			synchronized(Orders) {
			for(int i=0; i<Orders.size();i++){
				if(Orders.get(i).s == OrderState.done) {
					DeliverOrder(Orders.get(i));
					return true;
				}
				else if(Orders.get(i).s == OrderState.pending) {
						TryRestockOrder(Orders.get(i));
						return true;				
				}
			}
			}
		}	

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions
	
	
	private void TryRestockOrder(final Order o) {
		
		if(o.food.inventory == 0) {
			print("OUT of " + o.food);
			o.s = OrderState.finished;
			o.c.msgOutofChoice(this, o.food.type);
			return;
		}
		else {
			print("Restocking " + o);
			System.out.print(o.food.inventory + "\n");
			//marketGui.DoRestocking();
			o.s = OrderState.restocking;
		
			restockingtimer.schedule(new TimerTask() {
				public void run() {
					RestockDone(o);
				}
			},
			o.food.stockingTime*1000);
		
		}
		
	}
	
	private void DeliverOrder(Order o) {
		print("Delivering " + o);
		//marketGui.DoPlateIt(/*o.choicename*/);
		cashier.msgRestockingBill(this, o.food.type, o.food.orderamt*o.food.price);
		o.c.msgOrderDone(this, o.food.type, o.food.orderamt);
		o.s = OrderState.finished;
	}
	
	public void msgHereIsPayment(ItalianCashier cashier, Double payment) {
		synchronized(Orders) {
		for(int i=0; i<Orders.size();i++){
			if(Orders.get(i).food.orderamt*Orders.get(i).food.price == payment) {
				print("Received payment of " + payment + " from " + cashier);
				Orders.remove(Orders.get(i));
				stateChanged();
			}
		}
		}	
	}
	
	public void msgCashierStillOwes(ItalianCashier cashier, Double leftover) {
		print(cashier + " still owes me " + leftover);
	}

	//utilities
	public String toString() {
		return "Market \"" + getName() + "\"";
	}
	
	public void setGui(ItalianMarketGui gui) {
		marketGui = gui;
	}

	public ItalianMarketGui getGui() {
		return marketGui;
	}
	
	public void setCashier(ItalianCashier c) {
		cashier = c;
	}
	
	/*Order class stores the different food orders
	 * the cook gives to the market for filling.
	 */
	
	private enum OrderState {pending, restocking, done, finished};
	
	private class Order {
		ItalianCook c;
		//String choicename;
		int leftover = 0;
		Food food;
		OrderState s;
		/*
		Order(Cook cook, Food f, int amount) {
			c = cook;
			food = f;
			f.orderamt = amount;
			s = OrderState.pending;
		}*/
		Order(ItalianCook cook, Food f, int amount, int left) {
			c = cook;
			food = f;
			f.orderamt = amount;
			leftover = left;
			s = OrderState.pending;
		}
		public String toString() {
			return "Order " + food;
		}
	}
	
	//public enum FoodState {ordered, delivered};
	private class Food {
		String type;
		int stockingTime;
		int orderamt;
		int inventory;
		int capacity;
		Double price;
		
		Food(String choice, int time, int invent, Double p) {
			type = choice;
			stockingTime = time;
			inventory = invent;
			capacity = 20;
			price = p;
		}
		
		public String toString() {
			return type;
		}
	}
}

