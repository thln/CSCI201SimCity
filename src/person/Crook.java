package person;

import java.util.Timer;
import java.util.TimerTask;

import chineseRestaurant.ChineseRestaurant;
import person.Person.HungerLevel;
import person.Role.RoleState;
import bank.BankCustomerRole;
import application.Phonebook;
import application.Restaurant;
import application.TimeManager;
import application.TimeManager.Day;

public class Crook extends Person {
	//These people become dishonest customers in the restaurant

	//Robbing data
	enum RobState {robbedToday, waitingToRob, readyToRob};
	RobState robState = RobState.readyToRob;
	Timer robTimer = new Timer();

	public Crook(String name, double money) {
		super(name, money);
		hunger = HungerLevel.hungry;
	}

	public boolean pickAndExecuteAnAction() {
		
		if (getHunger() == HungerLevel.full) {
			startHungerTimer();
			return true;
		}

		if (robState == RobState.robbedToday){
			robState = RobState.waitingToRob;
			startRobTimer();
			return true;
		}

		synchronized (roles) {
			if (!roles.isEmpty()) {
				for (Role r : roles) {
					if (r.getState() == RoleState.active) {
						return r.pickAndExecuteAnAction();
					}
				}
			}
		}

		//If no role is active
		//Checking the time

		//If it's time to rob the bank, go ahead
		if (robState == RobState.readyToRob && Phonebook.getPhonebook().getEastBank().isOpen() && (TimeManager.getTimeManager().getTime().day != Day.Saturday
				&& TimeManager.getTimeManager().getTime().day != Day.Sunday)){
			robState = RobState.robbedToday;		//reset state for new timer
			prepareForBank();
			return true;
		}

		//Rent Related
		if(TimeManager.getTimeManager().getTime().day == Day.Tuesday)
		{
			resetRentMailbox();
			return true;
		}
		if(TimeManager.getTimeManager().getTime().day == Day.Monday && !checkedMailbox)
		{
			prepareForRent();
			return true;
		}

		//Hunger Related
		
		if (getHunger() == HungerLevel.hungry && robState == RobState.robbedToday) {
			//If you don't have food in the fridge
			if (!hasFoodInFridge) {
				for (Restaurant r: Phonebook.getPhonebook().restaurants){
					if (r.isOpen() && (TimeManager.getTimeManager().getTime().day != Day.Saturday
							&& TimeManager.getTimeManager().getTime().day != Day.Sunday) ||
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

		//Market Related
		if (!hasFoodInFridge || carStatus == CarState.wantsCar) {
			if (money <= moneyMinThreshold && !hasFoodInFridge) {
				if (Phonebook.getPhonebook().getEastBank().isOpen()&& (TimeManager.getTimeManager().getTime().day != Day.Saturday
						&& TimeManager.getTimeManager().getTime().day != Day.Sunday)){ // || Phonebook.getPhonebook().getEastBank().isOpen()){	
					return true;
				}
			}
			else {
				if (Phonebook.getPhonebook().getEastMarket().isOpen()&& (TimeManager.getTimeManager().getTime().day != Day.Saturday
						&& TimeManager.getTimeManager().getTime().day != Day.Sunday)){
					return true;
				}
			}
		}

		if (robState == RobState.robbedToday)
			goToSleep(); 
		return false;
	}

	//actions

	protected void startRobTimer() {
		robTimer.schedule(new TimerTask() {
			public void run() {
				robState = RobState.readyToRob;
				stateChanged();
			}
		},
		(10000)); 
	}
}

