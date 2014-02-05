package person;

import chineseRestaurant.ChineseRestaurant;
import application.Phonebook;
import application.Restaurant;
import application.TimeManager;
import application.TimeManager.Day;
import application.gui.trace.AlertLog;
import application.gui.trace.AlertTag;
import person.Role.RoleState;

public class Wealthy extends Person {

	boolean needToDeposit;
	String name;

	public Wealthy(String name,  double money) {
		super(name, money);
		this.name = name;
		hasFoodInFridge = false;
		setHunger(HungerLevel.full);
		//roles.add(new LandlordRole());
	}


	//Scheduler
	public boolean pickAndExecuteAnAction() {
		if (getHunger() == HungerLevel.full) {
			startHungerTimer();
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

		//Bank Related
		if (money <= moneyMinThreshold || money >= moneyMaxThreshold && Phonebook.getPhonebook().getWestBank().isOpen() && (TimeManager.getTimeManager().getTime().day != Day.Saturday
				&& TimeManager.getTimeManager().getTime().day != Day.Sunday)) {
			prepareForBank();
			return true;
		}

		//Rent Related
		if(TimeManager.getTimeManager().getTime().day == Day.Tuesday)
		{
			resetRentMailbox();
		}
		if (TimeManager.getTimeManager().getTime().day == TimeManager.Day.Monday && !checkedMailbox) 
		{
			prepareForRentCollection();
			return true;
		}

		//Hunger Related
		if (getHunger() == HungerLevel.hungry) {
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
			if (Phonebook.getPhonebook().getWestMarket().isOpen() && (TimeManager.getTimeManager().getTime().day != Day.Saturday
					&& TimeManager.getTimeManager().getTime().day != Day.Sunday)){
				prepareForMarket();
				return true;
			}
		}

		goToSleep();
		return false;
	}

	//Actions
	public void prepareForRentCollection() 
	{
		checkedMailbox = true;
		//print("I am picking up all the rent money.");
		AlertLog.getInstance().logInfo(AlertTag.HOUSING, name, "I am picking up all the rent money.");
		money += Phonebook.getPhonebook().getHousingMaintenanceCompany().mailbox.pickUpRentMoney(this);
	}
}