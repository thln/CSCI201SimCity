package housing;

import java.util.concurrent.Semaphore;

import person.*;
import application.gui.animation.agentGui.*;

public class ApartmentResidentRole extends HousingResidentRole{
	
	//DATA
	private Apartment apartment;
	private ApartmentResidentGui gui;
	private HousingResidentGui HRR;
	public enum PersonState {moving, none};
	public PersonState pState = PersonState.none;
	private Semaphore atDestination = new Semaphore(0, true);
	private Housing myHome;
	int aptUnit;

	public ApartmentResidentRole (Person p1, String pName, String rName) {
		super(p1, pName, rName);
	}
	
	//Messages
	public void msgMoveToUnit() {
		pState = PersonState.moving;
		stateChanged();
	}
	
	public void msgAtDestination() {
		atDestination.release();
	}
	
	//Scheduler

	public boolean pickAndExecuteAnAction () {
		if(pState == PersonState.moving) {
			GoToUnit(aptUnit);
			return true;
		}
		if(state == ResidentState.sleepy) {
			goToSleep();
			return true;
		}
		if(state == ResidentState.hungry) {
			goToKitchen();
			return true;
		}
		if(state == ResidentState.watchingTV) {
			goToLivingRoom();
			return true;
		}
		if(state == ResidentState.peeing) {
			goToBathroom();
			return true;
		}
		
		if (leaveRole) {
			apartment.removeResident(this);
			leaveRole = false;
			return true;
		}
		return false;
	}
		
	//ACTIONS
	public void GoToUnit(int unit) {
		gui.DoDoToUnit(unit);
		try {
			this.atDestination.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HRR = new HousingResidentGui(this);
		myHome.getPanel().addGui(HRR);
		myHome.setOccupied(true);
		/*************TEMP STATE for testing guis***********/
		state = ResidentState.sleepy;
		/*************END TEMP STATE*********************/
		pState = PersonState.none;
	}
	
	public void goToSleep() {
		HRR.DoGoToBed();
		try {
			this.atDestination.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*************TEMP STATE for testing guis***********/
		state = ResidentState.hungry;
		/*************END TEMP STATE*********************/
	}
	
	public void goToKitchen() {
		HRR.DoGoToKitchen();
		try {
			this.atDestination.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			/*************TEMP STATE for testing guis***********/
			state = ResidentState.watchingTV;
			/*************END TEMP STATE*********************/
	}
	
	public void goToLivingRoom() {
		HRR.DoGoToLivingRoom();
		try {
			this.atDestination.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			/*************TEMP STATE for testing guis***********/
			state = ResidentState.peeing;
			/*************END TEMP STATE*********************/
	}
	
	public void goToBathroom() {
		HRR.DoGoToBathroom();
		try {
			this.atDestination.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			/*************TEMP STATE for testing guis***********/
			state = ResidentState.sleepy;
			/*************END TEMP STATE*********************/
	}
	
	//utilities
	public void setGui (ApartmentResidentGui gui) {
		this.gui = gui;
	}
	
	public ApartmentResidentGui getGui() {
		return gui;
	}
	
	public void setUnit(int aptUnit, Housing home) {
		this.aptUnit = aptUnit;
		this.myHome = home;
	}
	
	public Housing getHome() {
		return this.myHome;
	}
	
	public void setApartment(Apartment apt) {
		this.apartment = apt;
	}
}
