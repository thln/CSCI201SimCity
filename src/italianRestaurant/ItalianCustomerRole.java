package italianRestaurant;

import person.*;
import application.gui.animation.agentGui.*;
import italianRestaurant.interfaces.*;

import java.util.*;
import java.util.Timer;
import java.util.concurrent.Semaphore;

import javax.swing.*;

/**
 * Restaurant customer agent.
 */
public class ItalianCustomerRole extends Role implements ItalianCustomer{
	 
	int tablenum;
	private int hungerLevel = 5;        // determines length of meal
	Timer eattimer = new Timer();
	Timer waittimer = new Timer(); ///////////////////////////////////
	private ItalianCustomerGui customerGui;
	public List<String> Menu = Collections.synchronizedList(new ArrayList<String>());
	private Map<String, Double> foodMap = new HashMap<String, Double>(); 
	private Semaphore atRestaurant = new Semaphore(0, true);
	private Semaphore atCashier = new Semaphore(0, true);
	private String choice;
	private Double cash;
	private Double bill;
	Random rn = new Random();

	// agent correspondents
	private ItalianWaiterRole waiter;
	private ItalianHostRole host;
	private ItalianCashierRole cashier;
	
	protected String RoleName = "Restaurant Customer";

	//    private boolean isHungry = false; //hack for gui
	public enum AgentState
	{DoingNothing, WaitingInRestaurant, BeingSeated, Seated, OrderingFood, Eating, WaitingBill, Paying, Leaving};
	private AgentState state = AgentState.DoingNothing;//The start state

	public enum AgentEvent 
	{none, gotHungry, followWaiter, seated, doneOrdering, doneWaiting, doneEating, donePaying, senttoJail, doneLeaving};
	AgentEvent event = AgentEvent.none;

	
	public ItalianCustomerRole(String name){
		super(name);
		
		/*initializing a full menu for use by the customer*/
		Menu.add("Steak");
		Menu.add("Chicken");
		Menu.add("Salad");
		Menu.add("Pizza");
		foodMap.put("Steak", 15.99);
		foodMap.put("Chicken", 10.99);
		foodMap.put("Salad", 5.99);
		foodMap.put("Pizza", 8.99);
	
	/*initializing random cash values based on the customer's name, or else just randomly*/
		if(name.toLowerCase().contains("thief")){
			cash = rn.nextDouble()*7.0+0.0;
		} else if (name.toLowerCase().contains("rich")){
			cash = rn.nextDouble()*50.0 + 30.0;
		} else if(name.toLowerCase().contains("cheap")){
				cash = rn.nextDouble()*3.0+7.0;
		} else {
			cash = rn.nextDouble()*70.0 + 5.0;
		}
	}

	/**
	 * hack to establish connection to AmericanRestaurantWaiter agent.
	 */
	
	
	public void setWaiter(ItalianWaiterRole w) {
		this.waiter = w;
	}
	
	public void setHost(ItalianHostRole h) {
		this.host = h;
	}
	
	public ItalianHostRole getHost() {
		return host;
	}
	
	public ItalianWaiterRole getWaiter() {
		return waiter;
	}
	
	public void setCashier(ItalianCashierRole c) {
		this.cashier = c;
	}
	/*
	public String getCustomerName() {
		return name;
	}*/
	// Messages

	public void gotHungry() {//from animation
		print("I'm hungry");
		event = AgentEvent.gotHungry;
		stateChanged();
	}
	
	public void msgComeIn() {//from AmericanRestaurantHost
		print("Allowed to come in. I'm hungry");
		event = AgentEvent.gotHungry;
		stateChanged();
	}
	
	public void msgSitAtTable(int table) {
		print("Received msgSitAtTable");
		tablenum = table;
		event = AgentEvent.followWaiter;
		stateChanged();
	}

	public void msgAnimationFinishedGoToSeat() {
		//from animation
		print("Seated. Ready to Order Food");
		
		event = AgentEvent.seated;
		stateChanged();
	}
	
	public void msgdoneOrdering() {
		print("Done Ordering Food");
		event = AgentEvent.doneOrdering;
		stateChanged();
	}
	
	public void msgFoodOutReorder(String OutChoice) {
		//removes choice from the menu customer is given
		for(int i=0; i<Menu.size();i++) {
			if(Menu.get(i).equals(OutChoice)) {
				Menu.remove(Menu.get(i));
			}
		}
		
		//choose whether or not to reorder or leave
		//if() {
			
		//}
		///else{
			print("Reordering Food");
			event = AgentEvent.seated;
			state = AgentState.BeingSeated;
			stateChanged();
	//	}
	}
	
	public void msgdoneWaitingForFood() {
		print("Done waiting for food");
		//restart button implemented here
		event = AgentEvent.doneWaiting;
		stateChanged();
	}
	
	public void msgHereIsBill(Double total) {
		print("Received Bill from waiter.");
		bill = total;
		System.out.print(bill + "\n");
		event = AgentEvent.doneWaiting;
		stateChanged();
	}
	
	//received from Cashier to indicate he is done paying for bill
	public void msgChangeAndReceipt(String receipt, Double change) {
		
		event = AgentEvent.donePaying;
		print("Receipt: " + receipt);
		print("Received " + change + " as change.");
		stateChanged();
	}
	
	public void YouOweUs(Double remaining_cost) {
		event = AgentEvent.senttoJail;
		bill = remaining_cost;
		print("I still owe " + bill);
		stateChanged();
	}
	
	public void msgAnimationFinishedLeaveRestaurant() {
		//from animation
		event = AgentEvent.doneLeaving;
		stateChanged();
	}
	
	public void msgAnimationFinishedGoToRest() {
		atRestaurant.release();
		stateChanged();
	}
	
	public void msgAtCashier() {
		atCashier.release();
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() {
		//	CustomerRole is a finite state machine

		if (state == AgentState.DoingNothing && event == AgentEvent.gotHungry ){
			state = AgentState.WaitingInRestaurant;
			goToRestaurant();
			return true;
		}
		if (state == AgentState.WaitingInRestaurant && event == AgentEvent.followWaiter ){
			state = AgentState.BeingSeated;
			SitDown();
			return true;
		}
		if (state == AgentState.BeingSeated && event == AgentEvent.seated){
			state = AgentState.Seated;
			OrderFood();
			return true;
		}
		
		if (state == AgentState.Seated && event == AgentEvent.doneOrdering){
			state = AgentState.OrderingFood;
			WaitForFood();
			return true;
		}
		
		if(state == AgentState.OrderingFood && event == AgentEvent.doneWaiting) {
			state = AgentState.Eating;
			EatFood();
			return true;
		}

		if (state == AgentState.Eating && event == AgentEvent.doneEating){
			state = AgentState.WaitingBill;
			GetCheck();
			return true;
		}
		if (state == AgentState.WaitingBill && event == AgentEvent.doneWaiting){
			state = AgentState.Paying;
			PayBill();
			return true;
		}
		if (state == AgentState.Paying && event == AgentEvent.donePaying){
			state = AgentState.Leaving;
			leaveTable();
			return true;
		}
		if (state == AgentState.Paying && event == AgentEvent.senttoJail){
			state = AgentState.DoingNothing;
			goToJail();
			return true;
		}
		if (state == AgentState.Leaving && event == AgentEvent.doneLeaving){
			state = AgentState.DoingNothing;
			//no action
			return true;
		}
		
		if (leaveRole)
		{
			((Worker) person).roleFinishedWork();
			leaveRole = false;
			return true;
		}
		
		return false;
	}

	// Actions

	private void goToRestaurant() {
		print("Going to restaurant");
		
		customerGui.DoGoToRestaurant();
		try {
			atRestaurant.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(customerGui.home > 5) {
			state = AgentState.Paying;
			event = AgentEvent.donePaying;
			stateChanged();
		}
		else {
			//Time for how long customer will wait. If too long, then leaves.
			waittimer.schedule(new TimerTask() {
				public void run() {
					if(state == AgentState.WaitingInRestaurant && event == AgentEvent.gotHungry) {
					print("Waited too long.");
					state = AgentState.Paying;
					event = AgentEvent.donePaying;
					stateChanged();
					}
				}
			},
			20000);
			host.msgIWantFood(this, customerGui.home);//send our instance, so he can respond to us
		}
	}

	private void SitDown() {
		print("Being seated. Going to table");
		customerGui.DoGoToSeat(tablenum);
		waiter.msgFollowMe(this); //waiter tells customer to follow him to table
	}
	
	private void OrderFood() {
		print("Now Ordering Food");
		//print(cash + "");
		print(5.99+6*(0.09 + 0.15) + "");
		if(cash < (5.99+5.99*(0.09 + 0.15)) ) {
			int y = JOptionPane.showConfirmDialog(null,
				"You do not have enough cash.\n Proceed to order anyways?", 
				"Not Enough Cash", JOptionPane.YES_NO_OPTION);
			if (y == 0) {
				DoOrderFood("Choice");
			}
			else {
				state = AgentState.Paying;
				event = AgentEvent.donePaying;
				waiter.getGui().GoToHome();
			}
		}
		else if(cash > (5.99+5.99*(0.09 + 0.15)) && cash < (8.99+5.99*(0.09 + 0.15)) ) {
			DoOrderFood("Salad");
		}	
		else {
			DoOrderFood("Choice");
		}
        
	}
	
	private void DoOrderFood(String foodchoice){
		//if(foodchoice.equals("Choice")) {
		//brings up menu for user of program to choose food
		
		/*
		switch(name) {
			case "Steak": choice = name; cash = 100.0;
			break;
			case "Chicken": choice = name; cash = 100.0;
			break;
			case "Salad": choice = name; cash = 100.0;
			break;
			case "Pizza": choice = name; cash = 100.0;
			break;
			default: {
			*/
				String foodlist = "";
				for(int j=0; j<Menu.size();j++) {
					foodlist = foodlist + Menu.get(j) + "    " + Double.toString(foodMap.get(Menu.get(j))) + "\n";
				} 
				choice = JOptionPane.showInputDialog(null, "Enter a food choice from the following:\n (case-sensitive) \n" + foodlist);
			/*	
			}
			break;
		}
		}
		else
			choice = foodchoice;
		*/
		print("I chose " + choice);
		waiter.msgHereIsOrder(this, choice);
		customerGui.showOrder("");
	}
	
	private void WaitForFood() {
		print("Waiting for Food");
		//pause button called here to stop scheduler?
		 
	}

	private void EatFood() {
		print("Eating Food");
		//This next complicated line creates and starts a timer thread.
		//We schedule a deadline of getHungerLevel()*1000 milliseconds.
		//When that time elapses, it will call back to the run routine
		//located in the anonymous class created right there inline:
		//TimerTask is an interface that we implement right there inline.
		//Since Java does not all us to pass functions, only objects.
		//So, we use Java syntactic mechanism to create an
		//anonymous inner class that has the public method run() in it.
		customerGui.showOrder(choice);
		eattimer.schedule(new TimerTask() {
			public void run() {
				print("Done eating");
				event = AgentEvent.doneEating;
				//isHungry = false;
				stateChanged();
			}
		},
		5000);//getHungerLevel() * 1000);//how long to wait before running task
		
	}
	
	private void GetCheck(){
		print("Ready for Check");
		//ask waiter to bring check to him
		waiter.msgCustWantsCheck(this);
	}
	
	private void PayBill() {
		print("Paying Bill");
		//go to cook to pay for bill
		customerGui.DoGotoCashier();
		try {
			atCashier.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cashier.msgHereIsMoney(this, cash);
	}

	private void leaveTable() {
		print("Leaving.");
		if(tablenum > 0)
			waiter.msgLeavingTable(this);
		customerGui.DoExitRestaurant();
	}
	
	private void goToJail() {
		print("Going to Jail");
		customerGui.DoGoToJail();
	}
	// Accessors, etc.
	
	public int getHungerLevel() {
		return hungerLevel;
	}

	public void setHungerLevel(int hungerLevel) {
		this.hungerLevel = hungerLevel;
		//could be a state change. Maybe you don't
		//need to eat until hunger lever is > 5?
	}

	public String toString() {
		return "customer " + getName();
	}

	public void setGui(ItalianCustomerGui g) {
		customerGui = g;
	}

	public ItalianCustomerGui getGui() {
		return customerGui;
	}
}

