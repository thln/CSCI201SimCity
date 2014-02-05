//package americanRestaurant;
//
//import java.util.Random;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import chineseRestaurant.ChineseRestaurant;
//import chineseRestaurant.ChineseRestaurantCustomerRole.AgentEvent;
//import chineseRestaurant.ChineseRestaurantCustomerRole.AgentState;
//import person.Person;
//import person.Role;
//import americanRestaurant.AmericanRestaurantWaiterRole.Menu;
//import americanRestaurant.interfaces.AmericanRestaurantCashier;
//import americanRestaurant.interfaces.AmericanRestaurantCustomer;
//import americanRestaurant.interfaces.AmericanRestaurantWaiter;
//import application.Phonebook;
//import application.gui.animation.RestaurantPanel;
//
///**
// * Restaurant customer agent.
// */
//public class AmericanRestaurantCustomerRole extends Role implements AmericanRestaurantCustomer {
//
//	//data
//	
//	AmericanRestaurant myRestaurant;
//	AmericanRestaurantWaiter myWaiter;
//	static final int decidingTime = 2000;
//	static final int maxCash = 300;
//	static final int eatingTime = 3000;
//	boolean waiting = false;
//	private boolean dishonest = false;
//
//	private int cash, check, debt;
//	String choice;
//	private int seatNumber = 0;
//	Menu myMenu;
//
//	Timer timer;
//	Timer deciding;
//	Timer eating;
//	private int hungerLevel = 5;        // determines length of meal
//
//	//private AmericanCustomerGui customerGui;
//
//	// agent correspondents
//
//	public enum CustomerState {DoingNothing, WaitingInRestaurant, BeingSeated, Seated, ReadyToOrder, Ordered, WaitingForFood,
//		OrderReady, Eating, PayingBill, DoneEating, Leaving};
//
//		private CustomerState state = CustomerState.DoingNothing;//The start state
//
//		public enum AgentEvent  {none, gotHungry, followHost, seated, ordering, reOrder, eating, readyForCheck, checkReady, 
//			billPaid, doneEating, doneLeaving, noMoney};
//
//			AgentEvent event = AgentEvent.none;
//
//			/**
//			 * Constructor for AmericanRestaurantCustomerRole class
//			 *
//			 * @param name name of the customer
//			 * @param gui  reference to the customergui so the customer can send it messages
//			 */
//
//			public AmericanRestaurantCustomerRole(){
//				super("name");
//			}
//
//			public AmericanRestaurantCustomerRole(Person p1, String name, String rName){
//				super(p1, name, rName);
//				cash = (int) p1.money;
//				timer = new Timer ();
//				deciding = new Timer();
//				eating = new Timer();
//				debt = 0;	
//				event = AgentEvent.gotHungry;
//				myRestaurant = Phonebook.getPhonebook().getAmericanRestaurant();
//			}
//
//			public int getSeatNumber(){
//				return seatNumber;
//			}
//
//			public void setSeatNumber (int x){
//				seatNumber = x;
//			}
//
//			// MESSAGES
//
//			public void gotHungry() {//from animation
//				print("I'm hungry");
//				if (isDishonest())
//					cash = 0;
//				if (!isDishonest())
//					cash = new Random().nextInt(maxCash); 
//				print("I have " + cash + " bucks.");
//				event = AgentEvent.gotHungry;
//				stateChanged();
//			}
//
//			public void msgRestaurantFull () {
//				int decision;
//				decision = new Random().nextInt(2);
//				if (decision == 0) {
//					print("I won't wait.");
//					myRestaurant.americanHost.msgWontWait(this);
//					//customerGui.DoExitRestaurant();
//				}
//				if (decision == 1) {
//					print("I will wait.");
//					waiting = true;
//					myRestaurant.americanHost.msgWillWait(this);
//				}
//			}
//
//			public void msgSitAtTable(AmericanRestaurantWaiterRole w, Menu m) {
//				print("Received msgSitAtTable");
//				this.myWaiter = w;
//				event = AgentEvent.followHost;
//				myMenu = m;
//				stateChanged();
//			}
//
//			public void msgAnimationFinishedGoToSeat() {
//				//from animation
//				event = AgentEvent.seated;
//				stateChanged();
//			}
//
//			public void msgReOrder(Menu m) {
//				myMenu = m;
//				event = AgentEvent.reOrder;
//				stateChanged();
//			}
//
//			public void msgWhatIsYourChoice(){
//				event = AgentEvent.ordering;
//				stateChanged();
//			}
//
//			public void msgHereIsYourFood(String choice){
//				event = AgentEvent.eating;
//				stateChanged();
//			}
//
//			public void msgPleasePayBill () {
//				event = AgentEvent.checkReady;
//				stateChanged();
//			}
//
//			public void msgHereIsChange (int change) {
//				print("Received change");
//				this.setCash(change);
//				event = AgentEvent.billPaid;
//				stateChanged();
//			}
//
//			public void msgAnimationFinishedLeaveRestaurant() {
//				//from animation
//				event = AgentEvent.doneLeaving;
//				stateChanged();
//			}
//
//			public void msgYouAreInDebt(int debt) {
//				this.debt = debt;
//			}
//
//			public void msgPayOffDebt (){
//				myRestaurant.americanCashier.msgPayDebt(this, debt, cash);
//				print("Paying my debt.");
//			}
//
//			public void msgComeIn() {
//				stateChanged();
//			}
//
//			//SCHEDULER
//			/**
//			 * Scheduler.  Determine what action is called for, and do it.
//			 */
//			public boolean pickAndExecuteAnAction() {
//				//	AmericanRestaurantCustomerRole is a finite state machine
//
//				if (getCustomerState() == CustomerState.DoingNothing && event == AgentEvent.gotHungry ){
//					state = CustomerState.WaitingInRestaurant;
//					goToRestaurant();
//					return false;
//				}
//				if (getCustomerState() == CustomerState.WaitingInRestaurant && event == AgentEvent.followHost ){
//					state = CustomerState.BeingSeated;
//					SitDown();
//					return false;
//				}
//				if (getCustomerState() == CustomerState.BeingSeated && event == AgentEvent.seated){
//					state = CustomerState.ReadyToOrder;
//					DecideFood();
//					return false;
//				}
//
//				if (getCustomerState() == CustomerState.ReadyToOrder && event == AgentEvent.ordering){
//					state = CustomerState.Ordered;
//					OrderFood();
//					return false;
//				}
//
//				if (getCustomerState() == CustomerState.ReadyToOrder && event == AgentEvent.noMoney){
//					state = CustomerState.DoingNothing;
//					return false;
//				}
//
//				if (getCustomerState() == CustomerState.Ordered && event == AgentEvent.reOrder) {
//					state = CustomerState.ReadyToOrder;
//					DecideFood();
//					return false;
//				}
//
//				if (getCustomerState() == CustomerState.Ordered && event == AgentEvent.eating){
//					state = CustomerState.Eating;
//					EatFood();
//					return false;
//				}
//
//				if (getCustomerState() == CustomerState.Eating && event == AgentEvent.checkReady){
//					state = CustomerState.PayingBill;
//					PayBill();
//					return false;
//				}
//
//				if (getCustomerState() == CustomerState.PayingBill && event == AgentEvent.billPaid){
//					state = CustomerState.DoingNothing;
//					//no action
//					return false;
//				}
//				return false;
//			}
//
//			// Actions
//
//			private void goToRestaurant() {
//				print("Going to restaurant");	
//				//	customerGui.DoGoToEntrance();
//				myRestaurant.americanHost.msgIWantToEat(this);//send our instance, so he can respond to us
//			}
//
//			private void SitDown() {
//				print("Being seated. Going to table");
//				event = AgentEvent.seated;
//				stateChanged();
//				//	customerGui.DoGoToSeat(seatNumber);
//			}
//
//			private void DecideFood () {
//
//				print ("Deciding");	
//				int size = Menu.items.size();
//				int i = 0;
//
//				for (i = 0; i < size; i++){
//					int item = new Random().nextInt(size);		//randomly select from a choice in the menu			
//					choice = Menu.items.get(item).choice;
//					if (isDishonest()) 						//If dishonest, don't check to see if enough cash			
//						break;		
//					if (Menu.items.get(item).price <= cash){
//						break;
//					}
//					print("Can't afford");				
//				}
//
//				if (i == size) {
//					print("Not enough money for anything.");
//					myWaiter.msgDoneAndPaying(this);
//					//	customerGui.DoExitRestaurant();
//					event = AgentEvent.noMoney;
//					stateChanged();
//					return;
//				}
//
//				deciding.schedule(new TimerTask() {
//					public void run() {			
//						BeckonWaiter();
//						stateChanged();
//					}
//				}, decidingTime);		//Time to decide food from menu		
//
//			}
//
//			private void BeckonWaiter() {
//				print ("Beckoning AmericanRestaurantWaiter");
//				myWaiter.msgReadyToOrder(this);
//			}
//
//			private void OrderFood() {
//				myWaiter.msgHereIsMyChoice(this,choice);
//			}
//
//			private void EatFood() {
//				print("Eating " + choice);
//				final AmericanRestaurantCustomer me = this;
//				//This next complicated line creates and starts a timer thread.
//				//We schedule a deadline of getHungerLevel()*1000 milliseconds.
//				//When that time elapses, it will call back to the run routine
//				//located in the anonymous class created right there inline:
//				//TimerTask is an interface that we implement right there inline.
//				//Since Java does not all us to pass functions, only objects.
//				//So, we use Java syntactic mechanism to create an
//				//anonymous inner class that has the public method run() in it.
//				timer.schedule(new TimerTask() {
//					Object cookie = 1;
//					public void run() {
//						print("Done eating " + choice);
//						//		panel1.deleteFoodIcon(myWaiter);
//						event = AgentEvent.readyForCheck;
//						myWaiter.msgReadyForCheck(me);
//						stateChanged();
//					}
//				},
//				eatingTime);
//			}
//
//			private void PayBill() {
//				print("Going to americanRestaurantCashier.");
//				myWaiter.msgDoneAndPaying(this);
//				//	customerGui.DoExitRestaurant();
//				if (isDishonest()) {
//					myRestaurant.americanCashier.msgNotEnoughMoney(this, check, cash);
//					print("Woops...not enough money.");
//					setDishonest(false);
//					state = CustomerState.DoingNothing;
//					return;
//				}
//				if (!isDishonest())
//					myRestaurant.americanCashier.msgHereIsMyPayment(this, check, cash);
//			}
//
//			// Accessors, etc.
//
//			public int getHungerLevel() {
//				return hungerLevel;
//			}
//
//			public void setHungerLevel(int hungerLevel) {
//				this.hungerLevel = hungerLevel;
//				//could be a state change. Maybe you don't
//				//need to eat until hunger lever is > 5?
//			}
//
//			public String toString() {
//				return "customer " + getName();
//			}
//
//			//			public void setGui(AmericanCustomerGui g) {
//			//				customerGui = g;
//			//			}
//			//
//			//			public AmericanCustomerGui getGui() {
//			//				return customerGui;
//			//			}
//
//			public CustomerState getCustomerState() {
//				return state;
//			}
//
//			public void setState(CustomerState state) {
//				this.state = state;
//			}
//
//			public int getCheck() {
//				return check;
//			}
//
//			public void setCheck(int check) {
//				this.check = check;
//			}
//
//			public int getCash() {
//				return cash;
//			}
//
//			public void setCash(int cash) {
//				this.cash = cash;
//			}
//
//			public void setDebt (int debt) {
//				this.debt = debt;
//			}
//
//			public int getDebt () {
//				return debt;
//			}
//
//			public boolean isDishonest() {
//				return dishonest;
//			}
//
//			public void setDishonest(boolean dishonest) {
//				this.dishonest = dishonest;
//			}
//
//}
//
