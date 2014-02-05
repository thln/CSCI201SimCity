# List of Roles
 + All of the following roles extend from the base class Role

## Bank
* BankCustomer
* Teller
* LoanOfficer
* Guard

## Restaurant
* RestaurantCustomer
* Host
* Waiter
* Cook
* Cashier

## Market
* SalesPerson
* MarketRunner
* UPSMan
* MarketCustomer

## Home Management
* Renter
* Landlord
* Maintenance

## Transportation
* Passenger
* LeadPassenger


Notes:

--Have a control panel with a list of all people and what they are doing and make it possible to control their actions
	-show money, hungry, position, role, etc.
	-can give money, take it away, want a car

--When you add person, you can specify work start and end time

--Need a file with the information (number of people with each role) for a basic scenario

-Will have to to implement a bus rider agent, or going to car GUI step

-If we have multi-step actions and don't want them, we will either create new roles or separate actions and use state checking 

-Right now, we don't account for priority of actions that are waiting to execute. If we wanted to, we would simply add the most important action to the front of the list in Roles. Because the scheduler just iterates through the list of roles, the first action with waiting to execute state would fire first.

-People will have a "phone book" type class which has information of the agents which begin interactions
(i.e. reference to hostAgents, bankGuards) 


Sim City Design (Application, Person, Person Types)
 
Base Classes:
 + Agent
 + Person (Inherits from Agent)

Inheriting Classes from Person:
 + Worker
 + Crook
 + Deadbeat
 + Wealthy

Buildings
 + 1 apartment complex
 + 1 mansion
 + 1 park
 + 5 restaurants
 + 1 bank
 + 1 market
 + Streets

Times
 + Waking Up (Depends on Person)
 + Going to Sleep (22)
 + Bank (8) except for bank workers
 + Market (After work for workers)
 + Eat Time:
    Crook = 14
    Deadbeat = They just wander
    Worker = 
    Wealthy = 

 + Job Time: (Start, Lunch, End)
 + Bank Teller (8, 12, 16)
 + Loan Officer (8, 12, 16)
 + Bank Guard (8, 12, 16)
 + Market Runner (11, 13, 19)
 + Market Sales (11, 13, 19)
 + UPS Man (11, 13, 19)
 + Cook (10, ??, 17)
 + Waiter (10, ??, 17)
 + Host (10, ??, 17)
 + Cashier(10, ??, 17)
 + Maintenance (8,14, 17)

Decision Making
**Consult each classes’ scheduler for details**
In general, decision making is made as follows:

Workers:
    //Job Related
    -If it’s time for work, prepare for and go to work

    //Hunger Related
    -If you are hungry and don’t have food in your fridge, go to a restaurant
    -If you do have food food in your fridge, eat at home

    //Bank Related
-If you need money to go the market or restaurant, but have less than you need, go to the bank
    -If you have more cash on you than you want, deposit money in the bank

    //Market Related
    -If you don’t have food in your fridge or you want a car, go to the market

Wealthy:
Note: wealthy people are the landlords of sim city, their job is to collect rent
    //Rent related
    -If the day is monday, collect rent
    
    //Hunger, Bank, & Market
    -Follows the same rules as workers

Deadbeats:
    -At 10am, deadbeats wander to the park
    -Otherwise, randomly decide to wander to businesses that deadbeat can’t afford
Crooks:
    Note: crooks always try to rob banks, and are the dishonest customers in restaurant
    -Every day, attempt to rob the bank

    //Hungry and Market Rules the same




###Application Class

<pre><code>
class Application {
	private ArrayList<Person> population;
	private static List<Housing> allHousing;

	public AnimationPanel animPanel;
	public Bank bank;
	public Market market;
	public Restaurant restaurant;
	public Timer updateTimer;

//Scheduler
	NONE. Non-threaded file

//Actions
	public void updatePeopleTime(){
		int timeConversion = 60 * TimeManager.getSpeedOfTime();
		
		updateTimer.schedule(new TimerTask() {
			public void run() {        
				for (Person p: population){
					p.stateChanged();      
				}
				updatePeopleTime();
			}
		},
		timeConversion);
		
	}
	
	public void addPerson (String name ,int money, String type,
			String jobTitle, String jobLocation, int startT, int lunchT, int endT) {
		//last 4 parameters specifically for worker. make empty/0 for all other types
		//add any special parameters if new things needed for other types
		if (type = "Wealthy") 
		{
			//min money req?
			Wealthy newP = new Wealthy(name, money);
			allHousing.add(new Housing(newP, allHousing.size(), "Mansion"));
			newP.setHome(allHousing.get(allHousing.size() - 1));
			getPopulation().add(newP);
			newP.startThread();
		}
		else if (type = "Crook") 
		{
			Crook newP = new Crook(name, money);
			allHousing.add(new Housing(newP, allHousing.size(), "Apartment"));
			newP.setHome(allHousing.get(allHousing.size() - 1));
			getPopulation().add(newP);
			newP.startThread();
		}
		else if (type = "Worker") 
		{
			Worker newP = new Worker(name, money, jobTitle, jobLocation, startT, lunchT, endT);
			allHousing.add(new Housing(newP, allHousing.size(), "Apartment"));
			newP.setHome(allHousing.get(allHousing.size() - 1));
			getPopulation().add(newP);
			newP.startThread();	
		}
		else if (type = "Deadbeat") 
		{
			Deadbeat newP = new Deadbeat(name, money);
			allHousing.add(new Housing(newP, allHousing.size(), "Park"));
			newP.setHome(allHousing.get(allHousing.size() - 1));
			getPopulation().add(newP);
			newP.startThread();
		}
	}
	
	public void editPerson(int index, String name, int money){
		getPopulation().get(index).setMoney(money);
	}

</code></pre>



###Person Class

<pre><code>
Person extends Agent{
	//Data
	List<Role> roles; 	//contains all the customer roles
	double money;
        int newTime;
	HashMap<String, int> Inventory; 		//market list
	boolean hasCar;
	int accountNum;
	double accountBalance;
	boolean hasFoodInFridge;
	int sleepTime = 22;

 //Scheduler
        protected abstract boolean pickAndExecuteAnAction();

  //Actions
       void eatAtHome() {
        }

        void prepareForBank () {
                /*GUI call to go to business
                //Checking if customer has enough money for a car

                if(∃ cust1 ∈ Roles || cust1 = BankCustomerRole) {
                              if (no account)
                cust1.setDesire(“newAccount”);
                               if (money <= moneyMinThreshold){
                                           withdrawAmount = 100;
                                           cust1.setDesire("withdraw");
                                  }
                              if (money >= moneyMaxThreshold){
                                          depositAmount = 100;
                                          cust1.setDesire("deposit");
                                 }
                     }

                           Phonebook.Bank.bankGuardRole.msgArrivedAtBank(cust1);
                                cust1.Role = active;
                             }
                }
    }

  protected void prepareForMarket() {
                         //GUI call to go to business
                          //Checking if have enough money for car
                if (accountBalance >= (carCost + 100)) {
                        if (carStatus = noCar) {
                                carStatus = CarState.wantsCar;
                        }
                }

    	if (hasFoodInFridge = false) {
        	//choosing random item to buy from market
       if(∃ cust1 ∈ Roles || cust1 = MarketCustomerRole) {
       			Phonebook.Market.salesPersonRole.msgIWantProducts(item);                                      
                cust1.Role = active;
        }
                }
        else if (carStatus = wantsCar) {
        if(∃ cust1 ∈ Roles || cust1 = MarketCustomerRole) {
        		Phonebook.Market.salesPersonRole.msgIWantProducts("Car");	                                       
                cust1.Role = active;
        }
        protected void prepareForRestaurant() {
                //GUI call to go to business
       if(∃ cust1 ∈ Roles || cust1 = RestaurantCustomerRole) { 
          Phonebook.Restaurant.msgIWantFood(cust1, xHome, yHome);
                cust1.Role = active;
        	}
        }

        protected void goToSleep() {
				gui.goHome();

                //After arrives home
                alarmClock.schedule(new TimerTask() {
                        public void run() {
                                stateChanged();
                        }
                },
             
        protected void startHungerTimer() {
  				 gui.goHome();                
                hunger = HungerLevel.moderate;

                //After arrives home
                hungerTimer.schedule(new TimerTask() {
                              },
</code></pre>




###Worker Class

<pre><code>
	boolean shift = false;
	//Data
	protected Job myJob = null;
	protected Role workerRole = null;
	public boolean lateWorker;
    
	public Worker (String name, double money, String jobTitle, String jobPlace, int startT, int lunchT, int endT) {
		super(name, money);
		myJob = new Job(jobTitle, jobPlace ,startT, lunchT, endT, this);
	}

	public class Job {
		public String title;
		public String jobPlace;
		public int lunchBreakLength = 1; 
		public int wage;
		public Point workLocation;     //point has xCoor,yCoor
		public WatchTime startTime, lunchTime, endTime;
		public Worker myself;

		Job(String title, String jobPlace, int startT, int lunchT, int endT, Worker me) {

			myself = me;
			startTime = new WatchTime(startT, 0);
			lunchTime = new WatchTime(lunchT, 0);
			endTime = new WatchTime(endT, 0);
			this.title = title;
			this.jobPlace = jobPlace;
		}

    }


//Messages
msgHereIsPayCheck (double amount) {
    money += amount;
}

	public void roleFinishedWork(){                 //from worker role
	 //Shift is over, time to leave work
		//workerRole.person = null;
		workerRole = null;
	//	scheduleNextTask(TimeManager.getTime().dayHour, myJob.startTime.hour);
		stateChanged();
	}
	//Scheduler 

		//Run your worker role
		if (∃ workerRole){
			int currentTime = TimeManager.getTimeManager().getTime().dayHour;
			int shiftLength = (((myJob.endTime.hour - myJob.startTime.hour) % 24) + 24) % 24;
			if (workerRole.state = active) {
				//If my current time is more than the shift length since start time
				if (((((currentTime - myJob.startTime.hour) % 24) + 24) % 24) >= shiftLength){
					workerRole.msgLeaveRole();
					return workerRole.pickAndExecuteAnAction();
				}					
				else
					return workerRole.pickAndExecuteAnAction();                      
			}
		}

		//Run your customer role
		if(∃ role ∈ Roles role.state = active) {
			r.pickAndExecuteAnAction();
		}
		
		int currentTime = getTime.dayHour;
		//If no role is active, check if it's time for work
		if (((((myJob.startTime.hour - currentTime) % 24) + 24) % 24) <= 1) {
			prepareForWork();
		//	scheduleNextTask(currentTime, myJob.endTime.hour);
			  
		}
		
		//Check if you are late for work	
		int timePassed = ((((currentTime - myJob.startTime.hour) % 24) + 24) % 24);
		int maxHoursLate = 4;	//if you are more than 4 hours late, don't bother going to work
		if ((timePassed >= 0) && (timePassed < maxHoursLate)){
				prepareForWork();
			//	scheduleNextTask(currentTime, myJob.endTime.hour);
				  
		}
		
		if (workState = prepareForWork) {
		if ((!lateWorker && currentTime <= 5 && (myJob.getStartTime().hour - currentTime) <= 1)
					|| (lateWorker && currentTime >= 5 && (myJob.getStartTime().hour - currentTime) <= 1)
					|| (!lateWorker && currentTime > 5 && (myJob.getStartTime().hour + 24 - currentTime) <= 1)
					|| (lateWorker && currentTime > 5 && (myJob.getStartTime().hour + 24 - currentTime) <= 1))
			{
				workState = atWork;
				upcomingTask = false;
				prepareForWork();
				  
			}
		}

		//Bank Related (check if you need to go to the bank)
		if (money <= moneyMinThreshold || money >= moneyMaxThreshold) 
		{
			prepareForBank();
			  
		}

		//Rent Related (check if you need to pay rent)
		if (getTime.day = Monday) {
			resetRentMailbox();
			  
		}
		if (getTime.day = Sunday && !checkedMailbox) {
			prepareForRent();
			  
		}

		//Hunger Related ( check if you are hungry)
		if (hunger = hungry) {
			//If you don't have food in the fridge
			if (!hasFoodInFridge) {
		if ((TimeManager.getTimeManager().getTime().dayHour >= Phonebook.getPhonebook().getRestaurant().openTime.hour) &&
			(TimeManager.getTimeManager().getTime().dayHour < Phonebook.getPhonebook().getRestaurant().closeTime.hour)) {
				prepareForRestaurant();
			}
			else //if you do have food in the fridge
			{
				eatAtHome(); //empty method for now...
			}
		}


		//Market Related (check if you need to go to the market)
		if (!hasFoodInFridge || carStatus = CarState.wantsCar) 
		{ 
			if ((TimeManager.getTimeManager().getTime().dayHour >= Phonebook.getPhonebook().getMarket().openTime.hour) &&
					(TimeManager.getTimeManager().getTime().dayHour < Phonebook.getPhonebook().getMarket().closeTime.hour)) 
				prepareForMarket();
		}

		goToSleep();
	}
</code></pre>





###Crook Class

<pre><code>
class Crook extends Person {
	//note: these people become dishonest customers in the restaurant
	
	int time2RobDaBank = 8;
	int eatTime = 12;
	int marketTime = 15;
	
//Messages
	msgNewTime (int time) {
if (time = time2RobDaBank) 
	RobberRole.state = waitingToExecute;
		if (time = eatTime)
		then	RestaurantCustomer.state = waitingToExecute;
		if (time = marketTime && !hasFoodInFridge)
		then	MarketCustomer.state = waitingToExecute;
		}

		if (time = sleepTime)
GoToSleep();
	}
}
</code></pre>



###Deadbeat Class
<pre><code>
class Deadbeat extends Person {
	int wanderTime = 8;
	int parkTime = 20;

//Messages
	msgNewTime (int time) {
		if (time % wanderTime == 0)
			Randomly wander between Restaurant, Bank, and Market
		if (time = parkTime)
			GoToPark();
		}	

if (time == sleepTime)
	GoToSleep();
	
	}

msgWelfareCheck {
	money += 50;
}
}
</code></pre>



###Wealthy Class

<pre><code>
class Wealthy extends Person {
	int eatTime1 = 10;
	int eatTime2 = 16;
	int bankTime = 10;
	boolean needToDeposit;

//Messages
msgRentDue () {
	roles.landlord.waitingToExecute;
}

	msgNewTime (int time) {
if (time = eatTime1)
	then activate roles.restaurantCustomer;
if (time = eatTime2)
	then activate roles.restaurantCustomer;
if (time = sleepTime)
	GoToSleep();
	}
}
</code></pre>

###Role Class

<pre><code>
public abstract class Role {

	Person person;
	
	enum roleState {active, inActive, waitingToExecute}
	roleState state = roleState.inActive;
	
	Role(Person person) {
		this.person = person;
	}
	
	protected void stateChanged() {
        //calls Person scheduler
		person.pickAndExecuteAnAction();
    }

    protected abstract boolean pickAndExecuteAnAction();
    
    protected void inactivateRole() {
    	state = roleState.inActive;
    	stateChanged();
    }
}
</code></pre>