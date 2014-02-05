package person;

import housing.*;

import java.awt.Point;
import java.util.*;
import java.util.concurrent.Semaphore;

import chineseRestaurant.ChineseRestaurantCustomerRole;
import italianRestaurant.*;
import bank.BankCustomerRole;
import bank.BankCustomerRole.CustomerState;
import market.MarketCustomerRole;
import person.Role;
import agent.Agent;
//import americanRestaurant.AmericanRestaurantCustomerRole;
import application.Phonebook;
import application.Restaurant;
import application.TimeManager;
import application.gui.trace.AlertLog;
import application.gui.trace.AlertTag;
import application.gui.animation.*;
import application.gui.animation.agentGui.*;
import application.gui.animation.agentGui.PersonGui.Command;

public abstract class Person extends Agent{

	//Data
	String name;
	protected Semaphore atCityDestination;
	public Housing home;
	private Timer alarmClock = new Timer();
	private Timer hungerTimer = new Timer();
	protected PersonGui gui;

	//Role Related
	public List<Role> roles = Collections.synchronizedList(new ArrayList<Role>());         //contains all the customer role
	public List<Gui> guis = Collections.synchronizedList(new ArrayList<Gui>());
	protected String currentRoleName;

	//Car Related
	public enum CarState {noCar, wantsCar, hasCar};
	public CarState carStatus = CarState.noCar;
	final int carCost = 1000;

	//Hunger Related
	public HashMap <String, Integer> Inventory = new HashMap<String, Integer>();                 //Food list
	public boolean hasFoodInFridge = false;
	public enum HungerLevel {full, moderate, hungry};
	protected HungerLevel hunger;
	protected List<String> restaurantQueue;
	int eatTime = 2;
	protected Semaphore eating = new Semaphore(0, true);
	//IMPORTANT ADD TO MESSAGES
	protected Semaphore waitingAtBus = new Semaphore(0, true);
	protected Semaphore beingTransported = new Semaphore(0, true);

	//Bank Related
	public double money;
	public String myBank;
	public int accountNum;
	public double accountBalance;
	public double desiredCash;
	public double depositAmount;
	public double loan;
	public int moneyMinThreshold = 20;
	public int moneyMaxThreshold = 200;

	//Rent Related
	public boolean checkedMailbox = false;

	//Time Related
	public int sleepTime = 22;
	protected Timer nextTask;

	Person(String name, double moneyz) {
		this.name = name;
		this.money = moneyz;
		roles.add(new ApartmentResidentRole(this, getName(), "Apartment Resident"));
		roles.add(new HousingResidentRole(this, getName(), "Housing Resident"));
		roles.add(new BankCustomerRole(this, getName(), "Bank Customer"));
		roles.add(new MarketCustomerRole(this, getName(), "Market Customer"));
		roles.add(new ChineseRestaurantCustomerRole(this, getName(), "Restaurant Customer", Phonebook.getPhonebook().getChineseRestaurant()));
		//roles.add(new AmericanRestaurantCustomerRole(this, getName(), "Restaurant Customer"));
		nextTask = new Timer();
		atCityDestination = new Semaphore(0,true);
		setHunger(HungerLevel.full);
		hasFoodInFridge = false;

		//add restaurants to queue 
		restaurantQueue = new ArrayList<String>();	
		restaurantQueue.add("Chinese Restaurant");
		restaurantQueue.add("American Restaurant");
		restaurantQueue.add("Italian Restaurant");
		//	restaurantQueue.add("Chinese Restaurant");
		restaurantQueue.add("Seafood Restaurant");
	}

	public void msgAtDestination() 
	{
		if(atCityDestination.availablePermits() < 1)
		{
			getAtDestination().release();
		}
	}

	public void msgBusIsHere()
	{
		if(waitingAtBus.availablePermits() < 1)
		{
			getWaitingAtBus().release();
		}
	}

	public void msgAtBusStopDestination()
	{
		if(beingTransported.availablePermits() < 1)
		{
			beingTransported.release();
		}
	}

	//Scheduler
	protected abstract boolean pickAndExecuteAnAction();

	//Actions
	protected void eatAtHome() {
		currentRoleName = "Housing Resident";
		int timeConversion = 60 * TimeManager.getSpeedOfTime();
		print("Going to eat at home");
		//if(getGui().getxPos() != getGui().getxHome() || getGui().getyPos() != getGui().getxHome()) {
		getGui().DoGoHome();
			try {
				this.atCityDestination.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
				//
			}
		//}
		
		//person is part of an apartment
		if(getHousing().type.toLowerCase().contains("apartment")) {
			if(getHousing().type.toLowerCase().contains("east")) {
				for (Role cust1 : roles) {
					if (cust1 instanceof ApartmentResidentRole) {
						ApartmentResidentRole ARR = (ApartmentResidentRole) cust1;
						if (Phonebook.getPhonebook().getEastApartment().arrived(ARR)) {
							currentRoleName = "EAST Apartment Resident";
							ARR.setRoleActive();
							stateChanged();
						}
						return;
					}
				}
			} else if (getHousing().type.toLowerCase().contains("west")) {
				for (Role cust1 : roles) {
					if (cust1 instanceof ApartmentResidentRole) {
						ApartmentResidentRole ARR = (ApartmentResidentRole) cust1;
						if (Phonebook.getPhonebook().getWestApartment().arrived(ARR)) {
							currentRoleName = "WEST Apartment Resident";
							cust1.setRoleActive();
							stateChanged();
						}
						return;
					}
				}
			}		

			//person is NOT part of an apartment
		} else if(getHousing().type.toLowerCase().contains("Mansion")){  //CHANGE BACK TO MANSION
			for (Role cust1 : roles) { 
				if (cust1 instanceof HousingResidentRole) {
					HousingResidentRole HRR = (HousingResidentRole) cust1;
					if (getHousing().arrived(HRR)) {
						currentRoleName = "Housing Resident";
						cust1.setRoleActive();
						stateChanged();
					}
					return;
				}
			}
		}
		
		nextTask.schedule(new TimerTask() {
			public void run() {  
				eating.release();
				hasFoodInFridge = false;  
				print("Finished eating at home");
				hunger = HungerLevel.full;
			}
		}, eatTime * timeConversion);
		try {
			eating.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
	}

	protected void prepareForBank () {

		if (home.type.equals("East Apartment"))
			gui.walk = gui.decideForBus("East Bank");
		else
			gui.walk = gui.decideForBus("West Bank");

		if (!gui.walk){
			print("Destination bus Stop: " + Phonebook.getPhonebook().getEastBank().getClosestBusStop().getBusStopNumber());
			if (home.type.equals("East Apartment"))
			{
				goToBusStop(Phonebook.getPhonebook().getEastBank().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getEastBank().location);
			}
			else 
			{
				goToBusStop(Phonebook.getPhonebook().getWestBank().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getWestBank().location);
			}
			gui.command = Command.GoToBank;
		}

		try {
			atCityDestination.acquire();
			if(!gui.walk)
			{
				atCityDestination.acquire();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();

		}

		for (Role cust1 : roles) {
			if (cust1 instanceof BankCustomerRole) 
			{
				BankCustomerRole BCR = (BankCustomerRole) cust1;
				BankCustomerGui bg = new BankCustomerGui(BCR);
				BCR.setGui(bg);

				if (this instanceof Crook){
					print("Time to rob da bank fools!");
					currentRoleName = "Bank Robber";
					BCR.setDesire("robBank");
					BCR.state = CustomerState.ready;
				}
				else {
					Do("Becoming Bank Customer");
					currentRoleName = "Bank Customer";
					if (money <= moneyMinThreshold)
						desiredCash = 100;
					else if (money >= moneyMaxThreshold)
						depositAmount = money-moneyMaxThreshold+100;

					if (accountNum != 0) {
						if (money <= moneyMinThreshold){
							BCR.setDesire("withdraw");
						}
						if (money >= moneyMaxThreshold){
							BCR.setDesire("deposit");
						}
					}
				}
				cust1.setRoleActive();
				if (home.type.equals("East Apartment"))
					Phonebook.getPhonebook().getEastBank().msgCustomerArrived(BCR);
				else
					Phonebook.getPhonebook().getWestBank().msgCustomerArrived(BCR);
				stateChanged();
				return;
			}
		}
	}

	protected void resetRentMailbox()
	{
		checkedMailbox = false;
	}

	protected void prepareForRent() {
		AlertLog.getInstance().logInfo(AlertTag.HOUSING, name, "Depositing rent for week. Rent is " + Phonebook.getPhonebook().getHousingMaintenanceCompany().mailbox.getApartmentRentCost());
		//print("Depositing rent for week. Rent is " + Phonebook.getPhonebook().getHousingMaintenanceCompany().mailbox.getApartmentRentCost());
		if( money >= Phonebook.getPhonebook().getHousingMaintenanceCompany().mailbox.getApartmentRentCost())
		{
			money -= Phonebook.getPhonebook().getHousingMaintenanceCompany().mailbox.getApartmentRentCost();
			Phonebook.getPhonebook().getHousingMaintenanceCompany().mailbox.dropRentMoney(Phonebook.getPhonebook().getHousingMaintenanceCompany().mailbox.getApartmentRentCost());
			checkedMailbox = true;
			return;
		}
		else if (accountBalance >= Phonebook.getPhonebook().getHousingMaintenanceCompany().mailbox.getApartmentRentCost())
		{
			accountBalance -= Phonebook.getPhonebook().getHousingMaintenanceCompany().mailbox.getApartmentRentCost();
			Phonebook.getPhonebook().getHousingMaintenanceCompany().mailbox.dropRentMoney(Phonebook.getPhonebook().getHousingMaintenanceCompany().mailbox.getApartmentRentCost());
			checkedMailbox = true;
		}
		else
		{
			print("Not enough money to pay rent");
			checkedMailbox = true;
			//Non Norm, making a loan
		}
	}

	protected void goToBusStop(int destinationBusStopNumber, Point location)
	{

		print("Going to bus Stop "+ gui.getClosestBusStopNumber());
		gui.doGoToBusStop();
		//Finish the GUI version of it
		try 
		{
			atCityDestination.acquire();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		print("At bus Stop "+ gui.getClosestBusStopNumber() + ". Now waiting");
		Phonebook.getPhonebook().getAllBusStops().get(gui.getClosestBusStopNumber()).waitingForBus(this);
		try
		{
			waitingAtBus.acquire();
			//waitingAtBus.acquire();
			//maybe have to do double acquires?
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		print("Telling " + Phonebook.getPhonebook().getAllBusStops().get(gui.getClosestBusStopNumber()).getCurrentBus().getName() + " that I'm getting on to go to bus stop # " + destinationBusStopNumber);
		Phonebook.getPhonebook().getAllBusStops().get(gui.getClosestBusStopNumber()).getCurrentBus().msgGettingOnBus(this, destinationBusStopNumber);
		gui.setInvisible();
		try
		{
			beingTransported.acquire();
			//beingTransported.acquire();
			//maybe have to do double acquires?
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		print("Arriving at destination Bus Stop: " + destinationBusStopNumber);
		gui.getOffBus(destinationBusStopNumber);
		gui.setxDestination(location.x);
		gui.setyDestination(location.y);
	}

	protected void prepareForMarket() {
		print("Going to market as a customer");

		if (home.type.equals("East Apartment"))
			gui.walk = gui.decideForBus("East Market");
		else
			gui.walk = gui.decideForBus("West Market");

		if (!gui.walk){
			print("Destination bus Stop: " + Phonebook.getPhonebook().getEastBank().getClosestBusStop().getBusStopNumber());
			if (home.type.equals("East Apartment"))
			{
				goToBusStop(Phonebook.getPhonebook().getEastMarket().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getEastMarket().location);
			}
			else 
			{
				goToBusStop(Phonebook.getPhonebook().getWestMarket().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getWestMarket().location);
			}
			gui.command = Command.GoToMarket;


		}
		if(gui.walk)
		{
			try {
				atCityDestination.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//Checking if have enough money for car
		if (accountBalance >= (carCost + 100)) {
			if (carStatus == CarState.noCar) {
				carStatus = CarState.wantsCar;
			}
		}

		if (hasFoodInFridge == false) {
			//choosing random item to buy from market
			for (Role cust1 : roles) {
				if (cust1 instanceof MarketCustomerRole) {
					MarketCustomerRole MCR = (MarketCustomerRole) cust1;
					MCR.setItem("");
					cust1.setRoleActive();
					if (home.type.equals("East Apartment"))
						Phonebook.getPhonebook().getEastMarket().arrived(MCR);
					else
						Phonebook.getPhonebook().getWestMarket().arrived(MCR);
					currentRoleName = "Market Customer";
					stateChanged();
					return;
				}
			}
		}
		else if (carStatus == CarState.wantsCar) {
			for (Role cust1 : roles) {
				if (cust1 instanceof MarketCustomerRole) {
					MarketCustomerRole MCR = (MarketCustomerRole) cust1;
					MCR.setItem("Car");
					cust1.setRoleActive();
					Phonebook.getPhonebook().getEastMarket().arrived(MCR);
					currentRoleName = "Market Customer";
					stateChanged();
					return;
				}
			}
		}
	}

	protected void prepareForRestaurant() {

		String choice = restaurantQueue.get(0);
		for (int i = 0; i < restaurantQueue.size(); i++){
			choice = restaurantQueue.get(i);
			if (choice.equals("Chinese Restaurant") && Phonebook.getPhonebook().getChineseRestaurant().isOpen()){
				break;
			}
			if (choice.equals("Italian Restaurant") && Phonebook.getPhonebook().getItalianRestaurant().isOpen()){
				break;
			}
			if (i == restaurantQueue.size())
				print("Bummer, no restaurants open");
		}

		print("Going to become a customer at " + choice);

		restaurantQueue.remove(choice);
		restaurantQueue.add(choice);

		//Moving this choice to back of queue

		gui.walk = gui.decideForBus(choice);

		if (!gui.walk){
			if (choice.equals("American Restaurant")){
//				print("Destination bus Stop: " + Phonebook.getPhonebook().getAmericanRestaurant().getClosestBusStop().getBusStopNumber());
//				goToBusStop(Phonebook.getPhonebook().getAmericanRestaurant().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getAmericanRestaurant().location);
			}
			if (choice.equals("Chinese Restaurant")){
				print("Destination bus Stop: " + Phonebook.getPhonebook().getChineseRestaurant().getClosestBusStop().getBusStopNumber());
				goToBusStop(Phonebook.getPhonebook().getChineseRestaurant().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getChineseRestaurant().location);
			}
			//			if (choice.contains("Seafood")){
			//				print("Destination bus Stop: " + Phonebook.getPhonebook().getSeafoodRestaurant().getClosestBusStop().getBusStopNumber());
			//				goToBusStop(Phonebook.getPhonebook().getSeafoodRestaurant().getClosestBusStop().getBusStopNumber());
			//			}
			if (choice.equals("Italian Restaurant")){
				print("Destination bus Stop: " + Phonebook.getPhonebook().getItalianRestaurant().getClosestBusStop().getBusStopNumber());
				goToBusStop(Phonebook.getPhonebook().getItalianRestaurant().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getItalianRestaurant().location);
			}
			gui.command = Command.GoToRestaurant;

		}

		try {
			atCityDestination.acquire();
			//					if (!gui.walk){
			//						try {
			//							atCityDestination.acquire();
			//						} catch (InterruptedException e) {
			//							e.printStackTrace();
			//		
			//						}
			//				}
		} catch (InterruptedException e) {
			e.printStackTrace();

		}

		for (Role cust1 : roles) {
			if (cust1 instanceof ChineseRestaurantCustomerRole) {
				ChineseRestaurantCustomerRole RCR = (ChineseRestaurantCustomerRole) cust1;
				if (Phonebook.getPhonebook().getChineseRestaurant().arrived(RCR)) {
					currentRoleName = "Chinese Restaurant Customer";
					cust1.setRoleActive();
					stateChanged();
				}
				return;
			}
			if (cust1 instanceof ItalianCustomerRole) {
				ItalianCustomerRole RCR = (ItalianCustomerRole) cust1;
				if (Phonebook.getPhonebook().getItalianRestaurant().arrived(RCR)) {
					currentRoleName = "Italian Restaurant Customer";
					cust1.setRoleActive();
					stateChanged();
				}
				return;
			}
//			if (cust1 instanceof AmericanRestaurantCustomerRole) {
//				AmericanRestaurantCustomerRole RCR = (AmericanRestaurantCustomerRole) cust1;
//				if (Phonebook.getPhonebook().getAmericanRestaurant().customerArrived(RCR)) {
//					currentRoleName = "American Restaurant Customer";
//					cust1.setRoleActive();
//					stateChanged();
//				}
//				return;
//			}
		}
	}

	protected void goToSleep() {

		gui.walk = gui.decideForBus("Home");
		if (!gui.walk){
			if (home.type.equals("East Apartment"))
			{
				goToBusStop(Phonebook.getPhonebook().getEastMarket().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getEastMarket().location);
			}
			else 
			{
				goToBusStop(Phonebook.getPhonebook().getWestMarket().getClosestBusStop().getBusStopNumber(), Phonebook.getPhonebook().getWestMarket().location);
			}
//			if (home.type.equals("East Apartment")){
//				gui.doGoToBus(Phonebook.getPhonebook().getEastMarket()//.getClosestStop().getX(),
//						Phonebook.getPhonebook().getEastMarket().getClosestStop().getY());
//			}
//			else {
//				gui.doGoToBus(Phonebook.getPhonebook().getWestMarket().getClosestStop().getX(),
//						Phonebook.getPhonebook().getWestMarket().getClosestStop().getY());
//			}
			gui.command = Command.GoHome;

		}

		try {
			atCityDestination.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//person is part of an apartment
		if(getHousing().type.toLowerCase().contains("apartment")) {
			if(getHousing().type.toLowerCase().contains("east")) {
				for (Role cust1 : roles) {
					if (cust1 instanceof ApartmentResidentRole) {
						ApartmentResidentRole ARR = (ApartmentResidentRole) cust1;
						if (Phonebook.getPhonebook().getEastApartment().arrived(ARR)) {
							currentRoleName = "EAST Apartment Resident";
							ARR.setRoleActive();
							stateChanged();
						}
						return;
					}
				}
			} else if (getHousing().type.toLowerCase().contains("west")) {
				for (Role cust1 : roles) {
					if (cust1 instanceof ApartmentResidentRole) {
						ApartmentResidentRole ARR = (ApartmentResidentRole) cust1;
						if (Phonebook.getPhonebook().getWestApartment().arrived(ARR)) {
							currentRoleName = "WEST Apartment Resident";
							cust1.setRoleActive();
							stateChanged();
						}
						return;
					}
				}
			}		

			//person is NOT part of an apartment
		} else if(getHousing().type.toLowerCase().contains("Mansion")){  //CHANGE BACK TO MANSION
			for (Role cust1 : roles) { 
				if (cust1 instanceof HousingResidentRole) {
					HousingResidentRole HRR = (HousingResidentRole) cust1;
					if (getHousing().arrived(HRR)) {
						currentRoleName = "Housing Resident";
						cust1.setRoleActive();
						stateChanged();
					}
					return;
				}
			}
		}

		//After arrives home
		alarmClock.schedule(new TimerTask() {
			public void run() {
				stateChanged();
			}
		},
		(((24 - TimeManager.getTimeManager().getTime().dayHour) + 8) * 500)); //Check this math please?
	}

	protected void startHungerTimer() {
		setHunger(HungerLevel.moderate);

		//After arrives home
		hungerTimer.schedule(new TimerTask() {
			public void run() {
				print("I'm hungry...time to eat!");
				setHunger(HungerLevel.hungry);
				stateChanged();
			}
		},
		(12000)); //Check this math please?
	}

	@Override
	public String getName() {
		return name;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public void setHome(Housing place) {
		home = place;
	}

	public String getCurrentRoleName()
	{
		return currentRoleName;
	}

	public void print(String msg)
	{
		AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, name, msg);
	}

	public Housing getHousing()
	{
		return home;
	}

	public void setGui(PersonGui g) {
		this.gui = g;
	}

	public Semaphore getAtDestination() {
		return atCityDestination;
	}

	public Semaphore getWaitingAtBus()
	{
		return waitingAtBus;
	}

	public Semaphore getBeingTransported()
	{
		return beingTransported;
	}

	public void setAtDestination(Semaphore atDestination) {
		this.atCityDestination = atDestination;
	}

	public PersonGui getGui() {
		return gui;
	}

	public HungerLevel getHunger() {
		return hunger;
	}

	public void setHunger(HungerLevel hunger) {
		this.hunger = hunger;
	}
}