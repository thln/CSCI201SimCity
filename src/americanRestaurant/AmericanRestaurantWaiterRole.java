//package americanRestaurant;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.ConcurrentModificationException;
//import java.util.List;
//import java.util.Timer;
//import java.util.concurrent.Semaphore;
//
//import chineseRestaurant.ChineseRestaurant;
//import person.Person;
//import person.Role;
//import americanRestaurant.AmericanRestaurantCookRole.Order;
//import americanRestaurant.gui.AmericanWaiterGui;
//import americanRestaurant.interfaces.AmericanRestaurantCashier;
//import americanRestaurant.interfaces.AmericanRestaurantCustomer;
//import americanRestaurant.interfaces.AmericanRestaurantWaiter;
//import application.gui.animation.RestaurantPanel;
//
////We only have 2 types of agents in this prototype. A customer and an agent that
////does all the rest. Rather than calling the other agent a waiter, we called him
////the AmericanRestaurantCookRole. A AmericanRestaurantHost is the manager of a restaurant who sees that all
////is proceeded as he wishes.
//public class AmericanRestaurantWaiterRole extends Role implements AmericanRestaurantWaiter{
//
//	//Phonebook data
//	AmericanRestaurant myRestaurant;
//	
//	
//	public enum customerState
//	{WaitingInRestaurant, BeingSeated, Seated, Ordering, ReadyToOrder, WaitingForReOrder, ReOrder, 
//		WaiterAtCook, FoodReady, Ordered, WaitingForFood, FoodCooking, FoodDelivering, CookWalk,
//		OrderReady, Eating, DoneEating, ReadyForBill, WaitingForCheck, CheckReady, Paying, Leaving, Left};
//
//		public class MyCustomer {
//			AmericanRestaurantCustomer cust;
//			AmericanRestaurantTable tab;
//			String choice;
//			customerState state;
//
//			MyCustomer (AmericanRestaurantCustomer c1, AmericanRestaurantTable t1, String x1){
//				cust = c1;
//				tab = t1;
//				choice = x1;
//				state = customerState.WaitingInRestaurant;
//			}
//		}
//
//		public enum WaiterState {atCook, goToCook, leftCook};
//
//		static final int NTABLES = 4;		//a global for the number of americanRestaurantTables.
//		AmericanRestaurantTable[] americanRestaurantTables = new AmericanRestaurantTable[NTABLES];
//
//		private List<MyCustomer> customers;	
//		private AmericanRestaurantHostRole myHost;
//		public AmericanWaiterGui waiterGui;
//		RestaurantPanel panel1;
//		WaiterState waitState;
//		static List<String> itemsRemoved = Collections.synchronizedList(new ArrayList<String>());
//		private boolean onBreak = false;
//		boolean wantToBreak = false;
//		Timer breakTimer;
//		private final int breakTime = 10000;
//		private static final customerState WaitingForReOrder = null;
//
//		private final int tableWidth = 50;
//		private final int tableLength = 50;
//		private String name;
//		private Semaphore atTable;	//Way to coordinate which agents are doing what
//
//		public static class Menu {
//
//			class Item {
//				String choice;
//				int price;
//
//				Item (String c, int d) {
//					choice = c;
//					price = d;
//				}
//			}
//
//			static List<Item> items = new ArrayList<>();
//
//			Menu (){		
//				items.add(new Item("Steak", 16));
//				items.add(new Item("Chicken", 11));
//				items.add(new Item("Salad", 6));
//				items.add(new Item("Pizza", 9));
//			}
//
//			void removeItem (String choice) {				
//				for (Item i: items) {
//					if (i.choice.equals(choice)) {
//						items.remove(i);
//						return;
//					}
//
//				}
//			}
//
//			public static int GetPrice (String choice) {
//				for (Item i: items) {
//					if (i.choice.equals(choice)) {
//						items.remove(i);
//						return i.price;
//					}
//				}
//				return 0;
//			}
//
//		}
//
//		//constructor
//		public AmericanRestaurantWaiterRole(Person p1, String pName, String rName, AmericanRestaurant restaurant) {
//			super(p1, pName, rName);
//			myRestaurant = restaurant;
//			myHost = myRestaurant.americanHost;
//			customers = new ArrayList<MyCustomer>();
//			atTable = new Semaphore(0,true);
//			breakTimer = new Timer();
//		} 
//
//		// GUI MESSAGES
//
//		@Override
//		public void msgAtDoor (){
//		//	waiterGui.inProcess = false;
//			atTable.release();			
//		}
//
//		@Override
//		public void msgAtTable() {//from animation	
//			atTable.release();
//		}
//
//		@Override
//		public void msgAtCook(AmericanRestaurantCustomer c1){
//			atTable.release();
//		}
//
//		@Override
//		public void msgFoodHere(AmericanRestaurantCustomer c1){
//			MyCustomer correct = findCustomer(c1);
//			correct.state = customerState.FoodDelivering;
//			atTable.release();
//		}
//
//		//AGENT MESSAGES
//
//		/**
//		 * Scheduler.  Determine what action is called for, and do it.
//		 */
//		public boolean pickAndExecuteAnAction() {
//
//			try {
//
//				if (customers.size() != 0)	{
//
//					for (MyCustomer c1: customers) {
//
//						if (c1.state == customerState.WaitingInRestaurant) {
//							seatCustomer(c1);
//							return false;
//						}
//
//						if (c1.state == customerState.ReadyToOrder) {
//							TakeOrder(c1);
//							return false;
//						}
//
//						if (c1.state == customerState.Ordered) {
//							ProcessOrder(c1);
//							return true;
//						}
//
//						if (c1.state == customerState.ReOrder) {
//							print("Please Order Again");
//							ReOrder(c1);
//							return false;
//						}
//
//						if (c1.state == customerState.WaiterAtCook){
//							OrderFromCook(c1);
//							return false;
//						}
//
//						if (c1.state == customerState.FoodReady){
//							ServeCustomer(c1);
//							return false;
//						}
//
//						if (c1.state == customerState.ReadyForBill){
//							print("Notifying americanRestaurantCashier");
//							NotifyCashier(c1);
//							return false;
//						}
//
//						if (c1.state == customerState.CheckReady){
//							CustomerPay(c1);
//							return false;
//						}
//
//						if (c1.state == customerState.Leaving) {
//							RemoveCustomer(c1);
//							return false;
//						}
//					}
//				}
//
//				if (customers.size() == 0 && isOnBreak()) {
//					print("Going on break");
//					GoBreak();
//					return false;
//				}
//
//				return false;
//				//we have tried all our rules and found
//				//nothing to do. So return false to main loop of abstract agent
//				//and wait.
//			}
//			catch (ConcurrentModificationException e) {
//				return true;
//			}
//		}
//
//		// Actions
//
//		MyCustomer findCustomer(AmericanRestaurantCustomer c1){
//			for (MyCustomer customer : customers) {
//				if (customer.cust.equals(c1))
//					return customer;
//			}
//			return null;
//		}
//
//		MyCustomer findCustomer(AmericanRestaurantTable tab1, String choice){
//			for (MyCustomer customer : customers) {
//				if (customer.tab == tab1 && customer.choice == choice)
//					return customer;
//			}
//			return null;
//		}
//
//		private void seatCustomer(MyCustomer c1) {
//			c1.state = customerState.BeingSeated;
//			if (!(waiterGui.getXPos() == waiterGui.entranceX && waiterGui.getXPos() == waiterGui.entranceY)){
//				waiterGui.inProcess = true;
//				waiterGui.DoGoToEntrance();
//				try {
//					atTable.acquire();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//			Menu m = new Menu();
//			if (itemsRemoved.size() != 0){
//				for (String i: itemsRemoved){
//					m.removeItem(i);
//				}
//			}
//			c1.cust.msgSitAtTable(this, m);
//		//	myHost.SlideCustomers();
//			DoSeatCustomer(c1); 		
//		}
//
//		private void DoSeatCustomer(MyCustomer C1) {
//			print("Seating customer at table " + (C1.tab.getSeatNum()+1));
//			waiterGui.DoBringToTable(C1.tab); 
//			try {
//				atTable.acquire();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			waiterGui.DoLeaveCustomer();	
//	}
//
//		private void TakeOrder(MyCustomer c1) {	
//			c1.state = customerState.Ordering;
//			waiterGui.DoBringToTable(c1.tab);
//			try {
//				atTable.acquire();			
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			print("Taking Order");
//			c1.cust.msgWhatIsYourChoice();
//		}
//
//		private void ProcessOrder(MyCustomer c1){
//			print("Contacting Cook");	
//			c1.state = customerState.WaitingForFood;
//			waiterGui.WalkToCook(c1.cust);
//			try {
//				atTable.acquire();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	
//			c1.state = customerState.WaiterAtCook;
//		}
//
//		public void OrderFromCook(MyCustomer c1){
//
//			myRestaurant.americanCook.msgHereIsAnOrder(new Order(c1.choice, this, c1.tab));	
//			waiterGui.DoLeaveCustomer();
//			c1.state = customerState.WaitingForFood;
//		}
//
//		private void ReOrder(MyCustomer c1) {
//			c1.state = WaitingForReOrder;
//			Menu m = new Menu();
//
//			synchronized(itemsRemoved) {
//				for (String i: itemsRemoved){
//					m.removeItem(i);
//				}	
//			}
//
//			waiterGui.DoBringToTable(c1.tab);		
//			try {
//				atTable.acquire();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			c1.cust.msgReOrder(m);
//			waiterGui.DoLeaveCustomer();
//		}
//
//		private void ServeCustomer(MyCustomer c1) {	
//
//			if (!(waiterGui.getXPos() == waiterGui.cookX && waiterGui.getXPos() == waiterGui.cookY)){	//if not already at cook
//				waiterGui.WalkToCookForFood(c1.cust);
//				try {
//					atTable.acquire();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}	
//			c1.state = customerState.Eating;
//			print("Delivering Order");
//			//panel1.addFoodIcon(c1.choice, this);
//			waiterGui.DoBringToTable(c1.tab);		
//			try {
//				atTable.acquire();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			print("Here is your food");
//			waiterGui.DoLeaveCustomer();
//			//panel1.stopFoodIcon(this);
//			c1.cust.msgHereIsYourFood(c1.choice);
//		}
//
//		private void NotifyCashier (MyCustomer c1) {
//			if (!(waiterGui.getXPos() == waiterGui.homeX && waiterGui.getXPos() == waiterGui.homeY)){
//				waiterGui.inProcess = true;
//				waiterGui.DoGoToEntrance();
//				try {
//					atTable.acquire();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			myRestaurant.americanCashier.msgComputeBill(this, c1.cust, c1.choice);
//			c1.state = customerState.WaitingForCheck;
//		}
//
//		private void CustomerPay (MyCustomer c1) {
//			waiterGui.DoBringToTable(c1.tab);
//			try {
//				atTable.acquire();			
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			c1.cust.msgPleasePayBill();
//			waiterGui.DoLeaveCustomer();
//			c1.state = customerState.Paying;
//		}
//
//		private void RemoveCustomer(MyCustomer c1) {
//			customers.remove(c1);
//			myHost.msgTableIsClear(c1.tab);
//			print(c1.cust + " leaving table " + (c1.tab.getSeatNum()+1));
//		}
//
//		private void GoBreak () {
//			setOnBreak(false);
//			final AmericanRestaurantWaiterRole w = this;
//			waiterGui.GoBreak();
//		}
//
//
//		//utilities
//
//		public void setGui(AmericanWaiterGui gui) {
//			waiterGui = gui;
//		}
//
//		public AmericanWaiterGui getGui() {
//			return waiterGui;
//		}
//
//		public void setPanel (RestaurantPanel p) {
//			panel1 = p;
//		}
//
//		public List<MyCustomer> getCustomers() {
//			return customers;
//		}
//
//		public void	setWantToBreak(boolean x) {
//			wantToBreak = x;
//		}		
//
//		public static void RemoveItem (String choice) {
//			for (String i: itemsRemoved){
//				if(i.equals(choice))
//					itemsRemoved.add(i);
//			}	
//		}
//
//		public static void AddItem (String choice) {
//			for (String i: itemsRemoved){
//				if(i.equals(choice))
//					itemsRemoved.remove(i);
//			}	
//		}
//
//		public boolean getWantToBreak (){
//			return wantToBreak;
//		}
//
//		public void setOnBreak (boolean x) {
//			onBreak = x;
//		}
//
//		public AmericanRestaurantHostRole getMyHost() {
//			return myHost;
//		}
//
//		public void setMyHost(AmericanRestaurantHostRole myHost) {
//			this.myHost = myHost;
//		}
//
//		public boolean isOnBreak() {
//			return onBreak;
//		}
//
//		@Override
//		public void msgSeatAtTable (AmericanRestaurantCustomer c1, AmericanRestaurantTable t1){
//			print("Seat Assigned");
//			customers.add(new MyCustomer(c1, t1, "None"));
//			stateChanged();
//		}
//
//		@Override
//		public void msgReadyToOrder (AmericanRestaurantCustomer c) {		
//			MyCustomer correct = findCustomer(c);
//			correct.state = customerState.ReadyToOrder;
//			stateChanged();
//		}
//
//		@Override
//		public void msgHereIsMyChoice (AmericanRestaurantCustomer c1, String choice1){	
//			MyCustomer correct = findCustomer(c1);
//			correct.choice = choice1;
//			correct.state = customerState.Ordered;
//			stateChanged();
//		}
//
//		@Override
//		public void msgOrderIsReady (AmericanRestaurantTable tab1, String choice){
//			print("Order Ready");
//			MyCustomer correct = findCustomer(tab1,choice);
//			correct.state = customerState.FoodReady;
//			stateChanged();
//		}
//
//		@Override
//		public void msgOutOfFood (Order order1) {
//			MyCustomer correct = findCustomer(order1.tab,order1.choice);
//			correct.state = customerState.ReOrder;
//			stateChanged();
//		}
//
//		@Override
//		public void msgCanBreak() {
//			onBreak = true;
//			wantToBreak = false;		
//			stateChanged();
//		}
//
//		@Override
//		public void msgNoBreak() {
//			wantToBreak = false;
//		}
//
//		@Override
//		public void msgReadyForCheck (AmericanRestaurantCustomer c1) {
//			MyCustomer correct = findCustomer(c1);
//			correct.state = customerState.ReadyForBill;
//			stateChanged();
//		}
//
//		@Override
//		public void msgHereIsCheck (AmericanRestaurantCustomer c1, int check1) {
//			MyCustomer correct = findCustomer(c1);
//			print("Received check");
//			correct.state = customerState.CheckReady;
//			c1.setCheck(check1);
//			stateChanged();
//		}
//
//		@Override
//		public void msgDoneAndPaying(AmericanRestaurantCustomer c1) {
//			MyCustomer correct = findCustomer(c1);
//			correct.state = customerState.Leaving;
//			stateChanged();
//		}
//
//}
