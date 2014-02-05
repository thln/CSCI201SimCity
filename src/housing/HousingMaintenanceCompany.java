package housing;

import person.Person;
import person.Role;
import person.Worker;
import application.Phonebook;
import application.WatchTime;
import application.gui.animation.agentGui.*;

public class HousingMaintenanceCompany 
{
	//DATA
	String name;
	
	//Open and closing times
	//Different times for company?
	public WatchTime openTime = new WatchTime(8);
	public WatchTime closeTime = new WatchTime(17);
	public Mailbox mailbox = new Mailbox();

	//ROLES
	public MaintenanceWorkerRole maintenanceWorkerRole = new MaintenanceWorkerRole("Maintenance Worker");
	public HousingMaintenanceGui maintenanceWorkerGui = new HousingMaintenanceGui(maintenanceWorkerRole);
	
	public HousingMaintenanceCompany(String name) 
	{
		this.name = name;
	}
	
	public Role arrivedAtWork(Person person, String title) 
	{
		if (title == "maintenance worker") 
		{
			//Setting previous maintenance worker role to inactive
			if (maintenanceWorkerRole.getPerson() != null) 
			{
				Worker worker = (Worker) maintenanceWorkerRole.getPerson();
				worker.roleFinishedWork();
			}
			//person.getGui().DoGoHome();
			//Setting maintenance Worker role to new role
			maintenanceWorkerRole.setPerson(person);
			maintenanceWorkerRole.setGui(maintenanceWorkerGui);
			return maintenanceWorkerRole;
		}
		else
		{
			return null;
		}
	}
	
	public void goingOffWork(Person person)
	{
		Worker worker = (Worker) person;
		if (worker.getWorkerRole().equals(maintenanceWorkerRole)) 
		{
			maintenanceWorkerRole = null;
		}

	}
	
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
}
