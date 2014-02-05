package italianRestaurant;

import person.*;
import application.Phonebook;
import application.gui.animation.agentGui.*;
import italianRestaurant.interfaces.*;
import italianRestaurant.ItalianRestaurantOrder.OrderState;
import italianRestaurant.ItalianFood.FoodState;
import market.*;
import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Cook Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the AmericanRestaurantCookRole. A Cook is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class ItalianCookRole extends Role implements ItalianCook{
	static final int NTABLES = 5;//a global for the number of americanRestaurantTables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	Random rn = new Random();
	boolean AllowedtoOrder = true;
	private Timer cookingtimer = new Timer();
	private Timer revolvingStandTimer = new Timer();
	
	private Semaphore atMarket = new Semaphore(0, true);
	private Semaphore atFridge = new Semaphore(0, true);
	public List<ItalianRestaurantOrder> Orders = Collections.synchronizedList(new ArrayList<ItalianRestaurantOrder>());
	//public List<ItalianMarket> Markets = Collections.synchronizedList(new ArrayList<ItalianMarket>());
	//public List<ItalianMarket> visitedMarkets = Collections.synchronizedList(new ArrayList<ItalianMarket>());
	public List<Market> Markets = Collections.synchronizedList(new ArrayList<Market>());
	public List<Market> visitedMarkets = Collections.synchronizedList(new ArrayList<Market>()); 
	
	private List<ItalianFood> Foods = Collections.synchronizedList(new ArrayList<ItalianFood>());
	
	private ItalianRestaurant restaurant = null;
	//note that americanRestaurantTables is typed with Collection semantics.
	//Later we will see how it is implemented
	 
	//private Semaphore atTable = new Semaphore(0,true);
	protected String RoleName = "Cook";
	public ItalianCookGui cookGui = null;

	public ItalianCookRole(Person person, String name, String title, ItalianRestaurant restaurant) {
		super(person, name, title);
		this.restaurant = restaurant;
		//initializing food inventories when application starts
		/*
		Foods.add(new Food("Steak", 5, 4, 3)); //name, cooktime, inventory, low
		Foods.add(new Food("Chicken", 2, 3, 2)); 
		Foods.add(new Food("Salad", 3, 2, 1));
		Foods.add(new Food("Pizza", 4, 3, 2));
		*/
		
		
		Foods.add(new ItalianFood("Steak", 5, 12, 3)); //name, cooktime, inventory, low
		Foods.add(new ItalianFood("Chicken", 2, 10, 2)); 
		Foods.add(new ItalianFood("Salad", 3, 10, 1));
		Foods.add(new ItalianFood("Pizza", 4, 12, 2));
		
	}
	
	public ItalianCookRole(String name, ItalianRestaurant restaurant) {
		super(name);
		this.restaurant = restaurant;
		Foods.add(new ItalianFood("Steak", 5, 12, 3)); //name, cooktime, inventory, low
		Foods.add(new ItalianFood("Chicken", 2, 10, 2)); 
		Foods.add(new ItalianFood("Salad", 3, 10, 1));
		Foods.add(new ItalianFood("Pizza", 4, 12, 2));
	}

	// Messages
	public void msgHereIsCustOrder(ItalianWaiter w, String choice, int table) {
		synchronized(Foods) {
			for(int i=0; i<Foods.size();i++) {
				if(Foods.get(i).type.equals(choice)) {
					Orders.add(new ItalianRestaurantOrder(w, Foods.get(i), table));
					print("Received order from " + w + " for " + Foods.get(i));
					stateChanged();
				}
			}
		}
	}
	
	public void FoodDone(ItalianRestaurantOrder o) {
		o.s = OrderState.done;
		print(o.food + " is Done!");
		System.out.print(o.food.inventory + "\n");
		stateChanged();
		
	}
	
	//message from market saying food is restocked and delivered to cook
	public void msgOrderDone(ItalianMarket market, String foodname, int foodamt){
		
		synchronized(Foods) {
			for(int i=0; i<Foods.size();i++) {
				if(Foods.get(i).type.equals(foodname)){
					Foods.get(i).inventory+= foodamt;
					//Foods.get(i).inventory++;
					//Foods.get(i).inventory = Foods.get(i).capacity;
					
					if(Foods.get(i).inventory == Foods.get(i).capacity) {
						print("Restocked " + foodname + " to capacity from the ItalianMarket!");
						//if any markets were visited, refresh the list for reuse to restock other foods
						for(int n=0; n<visitedMarkets.size();n++) {
							Markets.add(visitedMarkets.get(n));
						}
						visitedMarkets.clear();
						
						Foods.get(i).fs = FoodState.inStock;
						System.out.print(Foods.get(i).inventory);
						stateChanged();
					}
					else {
						print("Order partially filled. Restocking the rest from another market");
						Markets.remove(market);
						Foods.get(i).fs = FoodState.isLow;
						stateChanged();
					}
				}
			}
		}
	}
	
	/*message from market saying they are out of stock
	 * of the certain food item and that cook has to go to
	 * a different market for restocking
	 */
	public void msgOutofChoice(ItalianMarket market, String foodname){
		
		print("I received no " + foodname);
		
		Markets.remove(market);
		synchronized(Foods){
		for(int i=0; i<Foods.size();i++) {
			if(Foods.get(i).type.equals(foodname)){
				Foods.get(i).fs = FoodState.isLow;
				stateChanged();
			}
			}
		}
	}
	
	public void msgatMarket() {
		//System.out.print("REELEASEED!");
		atMarket.release();
		stateChanged();
	}
	public void msgatFridge() {
		atFridge.release();
		stateChanged();
	}
	
	public void setAllowedOrder(boolean allowed) {
		AllowedtoOrder = allowed;
	}
	
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		
		if(!Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().isStandEmpty()) {
			takeRevolvingStandOrder();
			return true;
		}
		
		if(Orders != null) {
			synchronized(Orders) {
			for(int i=0; i<Orders.size();i++){
				if(Orders.get(i).s == OrderState.done) {
					TryPlateIt(Orders.get(i));
					return true;
				}
				else if(Orders.get(i).s == OrderState.pending) {
						TryCookIt(Orders.get(i));
						return true;				
				}
			}
			}
		}
		
		//checks whether cook is allowed to order (determined by americanRestaurantCashier)
		if(AllowedtoOrder == true) {
			
			//goes to market to restock as soon as inventory levels have hit a low level
			synchronized(Foods) {
				for(int n=0; n<Foods.size();n++){
					if((Foods.get(n).inventory == Foods.get(n).low && Foods.get(n).fs == FoodState.inStock) || Foods.get(n).fs == FoodState.isLow) {
						OrderFoodThatLow(Foods.get(n));
						return true;
					}
				}
			}
		}
		
		startRevolvingStandTimer();
		
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
	public void startRevolvingStandTimer() {
		revolvingStandTimer.schedule(new TimerTask() {
			public void run() {
				stateChanged();
			}
		},
		300);
	}
	
	public void takeRevolvingStandOrder() {
		synchronized(Orders) {
			ItalianRestaurantOrder o = Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().takeOrder();
			synchronized(Foods) {
				for(int i=0; i<Foods.size();i++) {
					if(Foods.get(i).type.equals(o.choice)) {
						o.food = Foods.get(i);
					}
				}
			}
			Orders.add(o);
		}
	}
	
	private void TryCookIt(final ItalianRestaurantOrder o) {
		if(o.food.inventory == 0) {
			print("OUT of " + o.food);
			o.w.msgFoodOut(o.food.type, o.table);
			o.s = OrderState.finished;
		}
		else {
			print("Cooking " + o);
			System.out.print(o.food.inventory + "\n");
			cookGui.GoToFridge();
			try {
				atFridge.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cookGui.DoCooking(o.food.type, o.table);
			o.s = OrderState.cooking;
			o.food.inventory--;
		
			cookingtimer.schedule(new TimerTask() {
				public void run() {
					FoodDone(o);
				}
			},
			o.food.cookingTime*1000);
		}
		
	}
	
	private void TryPlateIt(ItalianRestaurantOrder o) {
			print("Plating Food");
			cookGui.DoPlateIt(o.food.type, o.table);
			o.w.msgOrderDone(o.food.type, o.table);
		o.s = OrderState.finished;
	}
	
	private void OrderFoodThatLow(ItalianFood f){
		if(Markets.size()>0) {
			if(f.fs == FoodState.inStock) {
				//print("Ordering " + f + " that is low");
			}
			else if(f.fs == FoodState.isLow) {
				//print("Ordering " + f + " that is low");
			}
		cookGui.GotoMarket();
	
		try {
			atMarket.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int orderAmount = f.capacity-f.inventory;
		String choice = f.type;
		print("Requesting " + Markets.get(0).getName() + " for " + orderAmount + " " + choice + "(s)");
		Markets.get(0).salesPersonRole.msgIWantProducts(Phonebook.getPhonebook().getItalianRestaurant(), choice, orderAmount);
		
		//Markets.get(0).msgOrderforMarket(this, f.type, f.capacity-f.inventory);
		visitedMarkets.add(Markets.get(0));
		f.fs = FoodState.ordered;
		}
		else {
			print("NO markets left to restock from");
			f.fs = FoodState.ordered;
		}
	}

	//utilities
	
	public String toString() {
		return "Cook " + getName();
	}
	
	public void setGui(ItalianCookGui gui) {
		cookGui = gui;
	}

	public ItalianCookGui getGui() {
		return cookGui;
	}
	
	public void addMarket(ItalianCashier cashier) {
		/*
		int i = rn.nextInt(100);
		String randName = Integer.toString(i);
		ItalianMarketRole market = new ItalianMarketRole(randName);
		*/
		Market market = Phonebook.getPhonebook().getEastMarket();
		if(visitedMarkets.get(0).equals(Phonebook.getPhonebook().getEastMarket()))
			market = Phonebook.getPhonebook().getWestMarket();
		//market.setCashier(cashier);
		Markets.add(market);
		//market.startThread();
		print("added " + market.getName() + "to list of Markets to request from");
	}

}

