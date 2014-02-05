package housing;

//import housing.Housing.housingState;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import housing.Housing.housingState;
import housing.interfaces.MaintenanceWorker;
import application.gui.animation.agentGui.*;
import application.Phonebook;
import person.Person;
import person.Role;

public class MaintenanceWorkerRole extends Role  implements MaintenanceWorker
{
	//List of housing
	//Need to talk more about housing. 
	//Dynamic number of housing? Cant go through X a day
	//Or automatically X number of housing every day?

	//public enum housingState {UrgentWorkOrder, CheckUpNeeded, RecentlyChecked};
	//public housingState state;
	//state = housingState.CheckUpNeeded;

	//Data

	String name; 
	private Timer FixingTimer  = new Timer();
	public enum maintenanceState {Working, CheckingHouse, RefreshList};
	public maintenanceState state = maintenanceState.Working;
	//private Semaphore workingOnResidence = new Semaphore(0, true);
	private HousingMaintenanceGui gui;

	/* Shit isn't really necessary
	class WorkOrder 
	{
		int HomeNumber;
		WorkOrder(int n) 
		{
			HomeNumber = n;
		} 
	};*/
	//List  WorkOrders;

	public MaintenanceWorkerRole (Person person, String pName, String rTitle)  
	{
		super(person, pName, rTitle);
		//this.RoleName = rTitle;
		//this.name = pName;
	}
	
	public MaintenanceWorkerRole (String name)
	{
		super(name);
	}

	//Messages
	public void msgNeedMaintenance(Housing houseNeedMain) 
	{
		synchronized(Phonebook.getPhonebook().getAllHousing(test)) 
		{
			for (Housing h : Phonebook.getPhonebook().getAllHousing(test)) 
			{
				if (h == houseNeedMain) 
				{
					h.state = housingState.UrgentWorkOrder;
					stateChanged();
				}
			}
		}
	}

	public void msgRefreshHousingList()
	{
		if(!Phonebook.getPhonebook().getAllHousing(test).isEmpty())
		{
			state = maintenanceState.RefreshList;
			stateChanged();
		}
	}
	
	//Scheduler
	public boolean pickAndExecuteAnAction() 
	{
		if (state == maintenanceState.RefreshList)
		{
			resetHousingCheck();
			return true;
		}
		
		if (state == maintenanceState.Working) 
		{
			if(!Phonebook.getPhonebook().getAllHousing(test).isEmpty())
			{
				synchronized(Phonebook.getPhonebook().getAllHousing(test)) 
				{
					for (Housing h : Phonebook.getPhonebook().getAllHousing(test)) 
					{
						if (h.state == housingState.UrgentWorkOrder) 
						{
							checkHousing(h);
							return true;
						}
					}
	
					for(Housing h : Phonebook.getPhonebook().getAllHousing(test)) 
					{
						if (h.state == housingState.CheckUpNeeded) 
						{
							checkHousing(h);
							return true;
						}
	
					}
					resetHousingCheck();
					return true;
				}
			}
		}
		return false;
	}

	//Actions
	public void checkHousing(final Housing h) 
	{
		//All good now, I believe
		state = maintenanceState.CheckingHouse;
		h.state = housingState.Checking;
		print("Checking housing " + h.housingNumber + " in state " + h.state);
		FixingTimer.schedule(new TimerTask() 
		{
			public void run() 
			{
				state = maintenanceState.Working;
				h.state = housingState.RecentlyChecked;
				print("Done checking " + h.housingNumber + " in state " + h.state);
				stateChanged();
			}
		},
		2000);
	}

	public void resetHousingCheck() 
	{
		state = maintenanceState.Working;
		synchronized(Phonebook.getPhonebook().getAllHousing(test)) 		
		{
			for (Housing h : Phonebook.getPhonebook().getAllHousing(test)) 
			{	
				print("All houses are finished! Rechecking houses");
				h.state = housingState.CheckUpNeeded;
			}
		}
	}
	
	public void setGui(HousingMaintenanceGui gui) {
		this.gui = gui;
	}
	
	public HousingMaintenanceGui getGui() {
		return this.gui;
	}
}
