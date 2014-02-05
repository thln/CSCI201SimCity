package housing;

import java.util.concurrent.Semaphore;

import person.*;
import application.gui.animation.agentGui.*;

public class HousingResidentRole extends Role{
	
	//DATA
	private HousingResidentGui gui;
	public enum ResidentState {hungry, watchingTV, peeing, sleepy, none};
	public ResidentState state;
	private Semaphore atDestination = new Semaphore(0, true);

	public HousingResidentRole (Person p1, String pName, String rName) {
		super(p1, pName, rName);
	}
	
	//Messages
	public void msgImSleepy() {
		state = ResidentState.sleepy;
		stateChanged();
	}
	
	public void msgAtDestination() {
		atDestination.release();
	}
	
	//Scheduler

	public boolean pickAndExecuteAnAction () {
		if(state == ResidentState.sleepy) {
			goToSleep();
			return true;
		}
		if(state == ResidentState.hungry) {
			goToKitchen();
			return true;
		}
		return false;
	}
		
	//ACTIONS
	public void goToSleep() {
		gui.DoGoToBed();
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
		gui.DoGoToKitchen();
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
		gui.DoGoToLivingRoom();
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
		gui.DoGoToBathroom();
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
	public void setGui (HousingResidentGui gui) {
		this.gui = gui;
	}
	
	public HousingResidentGui getGui() {
		return gui;
	}
}
