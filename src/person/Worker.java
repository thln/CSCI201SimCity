package person;

import java.awt.Point;
import java.util.TimerTask;

import chineseRestaurant.ChineseRestaurant;
import person.Role.RoleState;
import application.Phonebook;
import application.Restaurant;
import application.TimeManager;
import application.TimeManager.Day;
import application.gui.animation.agentGui.PersonGui.Command;
import application.WatchTime;

public class Worker extends Person {

	boolean shift = false;
	//Data
	public Job myJob = null;
	public Role workerRole = null;
	public boolean leavingWork = false;
	public boolean getPayCheck = false;

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

		public WatchTime getStartTime() {
			return startTime;
		}

		public void setStartTime(int t) {
			startTime.setTime(t, 0);
		}

		public WatchTime getLunchTime() {
			return lunchTime;
		}

		public void setLunchTime(int t) {
			lunchTime.setTime(t, 0);
		}

		public WatchTime getEndTime() {
			return endTime;
		}

		void setEndTime(int t) {
			endTime.setTime(t, 0);
		}

		void setTitle(String title) {
			this.title = title;
		}

		public String getTitle(){
			return title;
		}
		public String getJobLoc(){
			return jobPlace;
		}
	}


	//Messages
	void msgHereIsPayCheck (double amount) {
		money += amount;
	}

	public synchronized void roleFinishedWork(){                 //from worker role
		print("Shift is over, time to leave work");
		workerRole.leaveRole = false;
		leavingWork = false;
		workerRole.setPerson(null);	
		workerRole = null;
		//	scheduleNextTask(TimeManager.getTimeManager().getTime().dayHour, myJob.startTime.hour);
		stateChanged();
	}



	//Scheduler
	public boolean pickAndExecuteAnAction() {

		//Run your worker role
		if (workerRole != null)
		{
			int currentTime = TimeManager.getTimeManager().getTime().dayHour;
			int shiftLength = (((myJob.endTime.hour - myJob.startTime.hour) % 24) + 24) % 24;
			if (workerRole.getState() == RoleState.active) {
				//If my current time is more than the shift length since start time
				if (!((((((currentTime - myJob.startTime.hour) % 24) + 24) % 24) >= shiftLength)
						&& (((((currentTime - myJob.startTime.hour) % 24) + 24) % 24) > 0)
						&& ((((currentTime - myJob.startTime.hour) % 24) + 24) % 24) < 18)){
					return workerRole.pickAndExecuteAnAction();   
				}
				else if (!leavingWork){
					leavingWork = true;
					print("Off work at time = " + currentTime + " and shift length = " + shiftLength + " startTime = " + myJob.startTime.hour);
					workerRole.msgLeaveRole();
					return workerRole.pickAndExecuteAnAction();
				}	
				else {
					return workerRole.pickAndExecuteAnAction();
				}
			}
		}

		//Run your customer role
		synchronized (roles) {
			if (!roles.isEmpty()) {                                
				for (Role r : roles) {
					if (r.getState() == RoleState.active) {
						return r.pickAndExecuteAnAction();
					}
				}
			}
		}

		int currentTime = TimeManager.getTimeManager().getTime().dayHour;

		//make sure user has not closed your business
		//No work on Saturday or Sunday UNLESS you work at the Chinese Restaurant
		//Weekend will not go to work, go to work if Chinese Restaurant is open
		if (workIsOpen() && ((TimeManager.getTimeManager().getTime().day != Day.Saturday
				&& TimeManager.getTimeManager().getTime().day != Day.Sunday)|| myJob.jobPlace == "Chinese Restaurant")) 
		{	

			//If no role is active, check if it's time for work
			if (((((myJob.startTime.hour - currentTime) % 24) + 24) % 24) <= 1) {
				prepareForWork();
				//	scheduleNextTask(currentTime, myJob.endTime.hour);
				return true;
			}

			//Check if you are late for work	
			int timePassed = ((((currentTime - myJob.startTime.hour) % 24) + 24) % 24);
			int maxHoursLate = 4;	//if you are more than 4 hours late, don't bother going to work
			if ((timePassed >= 0) && (timePassed < maxHoursLate)){
				print("Late for work, gotta hurry!");
				prepareForWork();
				//	scheduleNextTask(currentTime, myJob.endTime.hour);
				return true;
			} 
		}

		//Bank Related (check if you need to go to the bank)
		//Check if there is a bank open before you go
		//DO NOT GO TO BANK ON WEEKEND
		if (home.type == "East Apartment"){
			if (Phonebook.getPhonebook().getEastBank().isOpen() && (TimeManager.getTimeManager().getTime().day != Day.Saturday
					&& TimeManager.getTimeManager().getTime().day != Day.Sunday)){ // || Phonebook.getPhonebook().getEastBank().isOpen()){	
				if (money <= moneyMinThreshold || money >= moneyMaxThreshold) 
				{
					prepareForBank();
					return true;
				}
			}
		}
		if (home.type == "West Apartment"){
			if (Phonebook.getPhonebook().getWestBank().isOpen() && (TimeManager.getTimeManager().getTime().day != Day.Saturday
					&& TimeManager.getTimeManager().getTime().day != Day.Sunday)){ // || Phonebook.getPhonebook().getEastBank().isOpen()){	
				if (money <= moneyMinThreshold || money >= moneyMaxThreshold) 
				{
					prepareForBank();
					return true;
				}
			}
		}

		//Rent Related (check if you need to pay rent)
		if (TimeManager.getTimeManager().getTime().day == Day.Tuesday) {
			resetRentMailbox();
			resetPayCheck();
			return true;
		}
		if (TimeManager.getTimeManager().getTime().day == Day.Monday && !checkedMailbox) {
			prepareForRent();
			return true;
		}
		
		if(TimeManager.getTimeManager().getTime().day == Day.Monday && !getPayCheck)
		{
			receivePayCheck();
			return true;
		}

		//Hunger Related ( check if you are hungry)
		if (getHunger() == HungerLevel.hungry) {
			hunger = HungerLevel.full;
			//If you don't have food in the fridge
			if (!hasFoodInFridge) {
				for (Restaurant r: Phonebook.getPhonebook().restaurants){
					//Not on weekend UNLESS its the Chinese restaurant and its open 
					if ((r.isOpen() && (TimeManager.getTimeManager().getTime().day != Day.Saturday
							&& TimeManager.getTimeManager().getTime().day != Day.Sunday)) ||
							(r instanceof ChineseRestaurant && r.isOpen()))
					{
						prepareForRestaurant();
						return true;
					}
				}			
			}
			else //if you do have food in the fridge
			{
				eatAtHome(); //empty method for now...
				return true;
			}
		}


		//Market Related (check if you need to go to the market)
		// Will not go to market if weekend
		if (!hasFoodInFridge || carStatus == CarState.wantsCar) 
		{ 
			if (home.type == "East Apartment") 
			{
				if (Phonebook.getPhonebook().getEastMarket().isOpen()  && (TimeManager.getTimeManager().getTime().day != Day.Saturday
						&& TimeManager.getTimeManager().getTime().day != Day.Sunday)) // || Phonebook.getPhonebook().getEastMarket().isOpen())
				{
					print("Going to market");
					prepareForMarket();
					return true;
				}
			}
			if (home.type == "West Apartment") 
			{
				if (Phonebook.getPhonebook().getWestMarket().isOpen()  && (TimeManager.getTimeManager().getTime().day != Day.Saturday
						&& TimeManager.getTimeManager().getTime().day != Day.Sunday)) // || Phonebook.getPhonebook().getEastMarket().isOpen())
				{
					print("Going to market");
					prepareForMarket();
					return true;
				}
			}
		}

		goToSleep();
		return false;
	}


	//Actions
	//	private void scheduleNextTask(int currentTime, int nextTaskTime) {
	//		int timeConversion = 60 * TimeManager.getSpeedOfTime();
	//		//print("Next task time = " + nextTaskTime + " Current time = " + currentTime);
	//		print("Timer length = ms" + ((((nextTaskTime - currentTime) % 24) + 24) % 24) * timeConversion);
	//		nextTask.schedule(new TimerTask() {
	//			public void run() {        
	//				stateChanged();                
	//			}
	//		},
	//		((((nextTaskTime - currentTime) % 24) + 24) % 24) * timeConversion);
	//	}

	public void receivePayCheck()
	{
		getPayCheck = true;
		print("Getting weekly paycheck for $100.");
		money += 100;
		return;
	}
	
	public void resetPayCheck()
	{
		getPayCheck = false;
	}
	
	public void prepareForWork() {
		currentRoleName = myJob.title;
		print("Preparing for work as " + myJob.title + " at " + myJob.jobPlace);

		gui.walk = gui.decideForBus(myJob.jobPlace);
		if (!gui.walk){
			if (myJob.jobPlace.contains("Chinese")){
				print("Destination bus Stop: " + Phonebook.getPhonebook().getChineseRestaurant().getClosestBusStop().getBusStopNumber());
				goToBusStop(Phonebook.getPhonebook().getChineseRestaurant().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getChineseRestaurant().location);
				gui.command = Command.GoToRestaurant;

			}
			if (myJob.jobPlace.contains("Italian")){
				print("Destination bus Stop: " + Phonebook.getPhonebook().getItalianRestaurant().getClosestBusStop().getBusStopNumber());
				goToBusStop(Phonebook.getPhonebook().getItalianRestaurant().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getItalianRestaurant().location);
				gui.command = Command.GoToRestaurant;
			}
			if (myJob.jobPlace.contains("West Bank")){
				print("Destination bus Stop: " + Phonebook.getPhonebook().getWestBank().getClosestBusStop().getBusStopNumber());
				goToBusStop(Phonebook.getPhonebook().getWestBank().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getWestBank().location);
				gui.command = Command.GoToBank;

			}
			if (myJob.jobPlace.contains("East Bank")){
				print("Destination bus Stop: " + Phonebook.getPhonebook().getEastBank().getClosestBusStop().getBusStopNumber());
				goToBusStop(Phonebook.getPhonebook().getEastBank().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getEastBank().location);
				gui.command = Command.GoToBank;
			}
			if (myJob.jobPlace.contains("West Market")){
				print("Destination bus Stop: " + Phonebook.getPhonebook().getWestMarket().getClosestBusStop().getBusStopNumber());
				goToBusStop(Phonebook.getPhonebook().getWestMarket().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getWestMarket().location);
				gui.command = Command.GoToMarket;
			}
			if (myJob.jobPlace.contains("East Market")){
				print("Destination bus Stop: " + Phonebook.getPhonebook().getEastMarket().getClosestBusStop().getBusStopNumber());
				goToBusStop(Phonebook.getPhonebook().getEastMarket().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getEastMarket().location);
			
				gui.command = Command.GoToMarket;
			}
			
		}

		if (!gui.walk){
			try {
				atCityDestination.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (myJob.jobPlace == "Chinese Restaurant") 
		{
			workerRole = Phonebook.getPhonebook().getChineseRestaurant().arrivedAtWork(this, myJob.title);
			workerRole.setRoleActive();
			return;
		}

		if (myJob.jobPlace.equals("East Bank")) 
		{
			workerRole = Phonebook.getPhonebook().getEastBank().arrivedAtWork(this, myJob.title);
			workerRole.setRoleActive();
			return;
		}

		if (myJob.jobPlace.equals("West Bank")) 
		{
			print("test working");
			workerRole = Phonebook.getPhonebook().getWestBank().arrivedAtWork(this, myJob.title);
			workerRole.setRoleActive();
			return;
		}

		if (myJob.jobPlace == "East Market") 
		{
			workerRole = Phonebook.getPhonebook().getEastMarket().arrivedAtWork(this, myJob.title);
			workerRole.setRoleActive();
			return;
		}

		if (myJob.jobPlace == "West Market") 
		{
			workerRole = Phonebook.getPhonebook().getWestMarket().arrivedAtWork(this, myJob.title);
			workerRole.setRoleActive();
			return;
		}

		if (myJob.jobPlace == "housing maintenance company")
		{
			workerRole = Phonebook.getPhonebook().getHousingMaintenanceCompany().arrivedAtWork(this, myJob.title);
			workerRole.setRoleActive();
			return;
		}

		return;
	}

	public boolean workIsOpen() {
		if (myJob.jobPlace.equals("East Bank")) {
			if (!Phonebook.getPhonebook().getEastBank().userClosed)
				return true;
			else
				return false;
		}

		if (myJob.jobPlace.equals("West Bank")) {
			if (!Phonebook.getPhonebook().getWestBank().userClosed)
				return true;
			else
				return false;
		}

		if (myJob.jobPlace == "East Market") {
			if (!Phonebook.getPhonebook().getEastMarket().userClosed)
				return true;
			else
				return false;
		}

		if (myJob.jobPlace == "West Market") {
			if (!Phonebook.getPhonebook().getWestMarket().userClosed)
				return true;
			else
				return false;
		}

		if (myJob.jobPlace == "Chinese Restaurant") {
			if (!Phonebook.getPhonebook().getChineseRestaurant().userClosed)
				return true;
			else
				return false;
		}

//		if (myJob.jobPlace == "American Restaurant") 
//		{
//			if (!Phonebook.getPhonebook().getAmericanRestaurant().userClosed)
//				return true;
//			else
//				return false;
//		}
		if (myJob.jobPlace == "Italian Restaurant") 
		{
			if (!Phonebook.getPhonebook().getItalianRestaurant().userClosed)
				return true;
			else
				return false;
		}
//		if (myJob.jobPlace == "Seafood Restaurant") 
//		{
//			if (!Phonebook.getPhonebook().getSeafoodRestaurant().userClosed)
//				return true;
//			else
//				return false;
//		}
		return true;
	}

	public void setWorkerRole(Role workerRole) 
	{
		this.workerRole = workerRole;
	}

	public Role getWorkerRole() 
	{
		return workerRole;
	}

	public Job getJob() 
	{
		return myJob;
	}
}