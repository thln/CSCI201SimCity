package person;

import java.util.List;
import java.util.Random;

import person.Role.RoleState;
import application.Phonebook;
import application.TimeManager;
import application.TimeManager.Day;

public class Deadbeat extends Person {

	public Deadbeat(String name,  double money) {
		super(name, money);
	}

	public void msgWelfareCheck() {
		money += 50;
	}


	protected boolean pickAndExecuteAnAction() {
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
		//Start day in park for a couple hours
		if (TimeManager.getTimeManager().getTime().dayHour == 10) {
			visitPark();
			return true;
		}

		//Otherwise, randomly wander to a sim city business
		Random rand = new Random();
		int myRandomChoice = rand.nextInt(3);
		if (myRandomChoice == 0){
			if (Phonebook.getPhonebook().getEastBank().isOpen() && (TimeManager.getTimeManager().getTime().day != Day.Saturday
					&& TimeManager.getTimeManager().getTime().day != Day.Sunday)){ // || Phonebook.getPhonebook().getEastBank().isOpen()){	
				prepareForBank();
			}
		}
		if (myRandomChoice == 1)
			if (Phonebook.getPhonebook().getEastMarket().isOpen()&& (TimeManager.getTimeManager().getTime().day != Day.Saturday
					&& TimeManager.getTimeManager().getTime().day != Day.Sunday)){
				prepareForMarket();
			}
		if (myRandomChoice == 2){
			if (Phonebook.getPhonebook().getChineseRestaurant().isOpen() && (TimeManager.getTimeManager().getTime().day != Day.Saturday
					&& TimeManager.getTimeManager().getTime().day != Day.Sunday)) {	
				prepareForRestaurant();
			}
		}


		goToSleep();
		return false;
	}


	//Actions
	public void visitPark() {
		//Go visit the park (GUI)
	}
}