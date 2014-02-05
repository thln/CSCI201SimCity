package bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import bank.interfaces.BankCustomer;
import bank.interfaces.BankGuard;
import bank.interfaces.BankTeller;
import application.Phonebook;
import person.Person;
import person.Role;
import person.Worker;
import application.gui.animation.agentGui.*;

public class BankGuardRole extends Role implements BankGuard {

	//DATA

	int customersInBank = 0;
	public List <BankCustomer> customers;
	List <BankCustomer> robbers;
	List <MyTeller> tellers; 

	protected String roleName = "Bank Guard";
	private BankGuardGui gui = null;
	private Semaphore atDestination = new Semaphore(0, true);
	public enum TellerState {available, busy};

	public class MyTeller {
		public TellerState state;
		public BankTeller tell1;

		MyTeller (BankTeller t1) {
			tell1 = t1;
			state = TellerState.available;
		}
	}

	public BankGuardRole (String name, Person p1, String roleName) {
		super(p1, name, roleName);
		customers = Collections.synchronizedList(new ArrayList<BankCustomer>());
		robbers = Collections.synchronizedList(new ArrayList<BankCustomer>());
		tellers = Collections.synchronizedList(new ArrayList<MyTeller>());
	}

	public BankGuardRole (String name) {
		super(name);
		customers = Collections.synchronizedList(new ArrayList<BankCustomer>());
		robbers = Collections.synchronizedList(new ArrayList<BankCustomer>());
		tellers = Collections.synchronizedList(new ArrayList<MyTeller>());
	}

	//MESSAGES

	public void msgTellerCameToWork (BankTeller t1) {

		tellers.add(new MyTeller(t1));
		if (this.person != null)
			print("Teller size = " + tellers.size());
	}

	public void msgTellerLeavingWork(BankTeller t1) {
		if (t1 instanceof Role)
			print("Teller role removed " + ((Role) t1).getPerson().getName());
		tellers.remove(findTeller(t1));
		print("tellers = " + tellers.size());
		if (person != null) {
			stateChanged();
		}
	}


	public void msgRobbingBank(BankCustomer cust1) {
		robbers.add(cust1);
		stateChanged();
	}

	public void msgArrivedAtBank(BankCustomer c1) {
		customersInBank++;
		try {
			print("New customer " + ((BankCustomerRole) c1).getName() + " arrived");
		}
		catch (Exception e) {
		}
		customers.add(c1);		
		stateChanged();
	}

	public void msgCustomerLeavingBank (BankTeller t1) {
		customersInBank--;
		print("Customer leaving, teller became available");
		MyTeller correct = findTeller(t1);
		correct.state = TellerState.available;
		if (person != null) {
			stateChanged();
		}
	}

	public void msgAtDestination() {
		this.atDestination.release();
	}


	//SCHEDULER
	public boolean pickAndExecuteAnAction() {

		synchronized (robbers) {
			for (BankCustomer cust1: robbers) {
				catchRobber(cust1);
				return true;
			}
		}

		synchronized (customers){
			if (customers.size() != 0){
				for (BankCustomer cust1: customers) {
					return assignToTeller(cust1); 
				}
			}
		}

		if (leaveRole && tellers.size() == 0){
			leaveRole = false;			
			Phonebook.getPhonebook().getEastBank().goingOffWork(this.person);
			return true;
		}

		return false;
	}

	//ACTIONS

	private MyTeller findTeller (BankTeller t1) {
		synchronized (tellers) {
			for (MyTeller teller: tellers) {
				if (teller.tell1.equals(t1)) {
					stateChanged();
					return teller;
				}
			}
		}
		return null;
	}

	private void catchRobber(BankCustomer robber1) {
		boolean caught;
		Random rand = new Random();
		//90% chance Robber is caught, 10% he gets away;
		int chance = rand.nextInt(2);
		if (chance == 1){
			caught = false;
			try {
				Thread.sleep(1000);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
		else {
			caught = true;
		}

		//GUI animation
		gui.DoCatchRobber();
		try {
			this.atDestination.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (caught){
			robber1.msgCaughtYou();
		}
		if (!caught) {
			double spoils = Phonebook.getPhonebook().getEastBank().vault/10;
			Phonebook.getPhonebook().getEastBank().vault -= spoils;
			robber1.msgGotAway(spoils); 
		}
		robbers.remove(robber1);
	}

	private boolean assignToTeller(BankCustomer cust1) {
		synchronized(tellers){
			for (MyTeller teller1: tellers) {
				if (teller1.state == TellerState.available) {
					if (teller1.tell1 instanceof Role)
						print("Assigning " + ((Role) cust1).getPerson().getName() + " to teller " + teller1.tell1.getName());
					cust1.msgGoToTeller(teller1.tell1);
					if (!test){
						gui.GoToTellers();
						try {
							this.atDestination.acquire();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					teller1.state = TellerState.busy;
					customers.remove(cust1);
					return false;
				}
			}	
		}
		int waitPlace = 0;
		for(int i=0; i < customers.size(); i++) {
			if(customers.get(i).equals(cust1))
				waitPlace = i;
		}
		cust1.setWaitPlace(waitPlace+1);
		cust1.msgNoTellerAvailable();
		return false;
	}

	public List <BankCustomer> getCustomers() {
		return customers;
	}

	public List<MyTeller> getTellers() {
		return tellers;
	}

	public void msgBankOpen() {
		if (customers.size() != 0){
			for (BankCustomer c1: customers){
				c1.msgComeIn();
			}
		}
	}

	public void setGui(BankGuardGui gui) {
		this.gui = gui;
	}
}
