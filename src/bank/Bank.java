package bank;

import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;

import chineseRestaurant.ChineseRestaurantWaiterRole;
import market.MarketCustomerRole;
import bank.BankGuardRole.MyTeller;
import bank.BankTellerRole.Account;
import bank.interfaces.BankCustomer;
import bank.interfaces.BankGuard;
import bank.interfaces.BankTeller;
import bank.interfaces.LoanOfficer;
import bank.mock.BankGuardMock;
import bank.mock.BankTellerMock;
import bank.mock.LoanOfficerMock;
import application.Phonebook;
import application.WatchTime;
import person.Person;
import person.Role;
import person.Worker;
import transportation.BusStop;
import application.gui.animation.*;
import application.gui.animation.agentGui.BankCustomerGui;
import application.gui.animation.agentGui.BankGuardGui;
import application.gui.animation.agentGui.BankLoanerGui;
import application.gui.animation.agentGui.BankTellerGui;
import application.gui.animation.agentGui.MarketCustomerGui;
import application.gui.animation.agentGui.MarketSalesPersonGui;
import application.gui.animation.agentGui.RestaurantWaiterGui;
import application.gui.trace.AlertLog;
import application.gui.trace.AlertTag;

public class Bank {

	/*
	Toolkit tk = Toolkit.getDefaultToolkit();
	int WINDOWX = ((int) tk.getScreenSize().getWidth())/2; 
	int WINDOWY = (((int) tk.getScreenSize().getHeight())/2)*5/6;   
	 */  

	//Data
	String name;
	public boolean userClosed = false;
	//private Point closestStop;
	public Point location;
	public int busStopNumber;

	//Open and closing times
	public WatchTime openTime = new WatchTime(8);
	public WatchTime closeTime = new WatchTime(17);

	//Data
	public double vault;
	public double vaultMinimum;
	public List<Account> accounts;
	public int accountNumKeyList = 3000;
	private Vector<BankTellerRole> tellers = new Vector<BankTellerRole>();

	//Roles
	public BankGuardRole bankGuardRole;
	public BankGuardGui bankGuardGui = new BankGuardGui();

	public LoanOfficerRole loanOfficerRole;
	public BankLoanerGui loanOfficerGui = new BankLoanerGui(loanOfficerRole);

	//Mocks roles for test
	public BankGuardMock bankGuardMock = new BankGuardMock("Bank Guard");
	public LoanOfficerMock loanOfficerMock = new LoanOfficerMock("Loan Officer");
	public List <BankTellerMock> mockTellers = new ArrayList<>();
	private BuildingPanel bankPanel;
	private ImageIcon bank = new ImageIcon("res/bank.png", "bank");

	//Constructor
	public Bank(String name) {
		bankGuardRole  = new BankGuardRole("Bank Guard");
		loanOfficerRole =  new LoanOfficerRole("Loan Officer");

		if (name.equals("East Bank")){
			location = new Point(315, 275);
		}
		else if (name.equals("West Bank"))
			location = new Point(215, 25);

		this.name = name;
		vault = 10000;
		vaultMinimum = 1000;
		accounts = Collections.synchronizedList(new ArrayList<Account>());

		BankTellerMock t2 = new BankTellerMock ("BankTellerMock");
		mockTellers.add(t2);	
	}


	//Methods
	public Role arrivedAtWork(Person person, String title) {

		if (title == "bankGuard") {
			//Setting previous bank guard role to inactive
			if (bankGuardRole.getPerson() != null) {
				System.err.println(person.getName() + " kicking off" + bankGuardRole.getName() + "from work");
				((Worker) bankGuardRole.getPerson()).roleFinishedWork();
			}
			//Setting bank guard role to new role
			bankGuardRole.setPerson(person);
			bankGuardRole.setGui(bankGuardGui);
			bankGuardGui.setPerson(bankGuardRole);
			//AlertLog.getInstance().logError(AlertTag.BANK, getName(), "bankguard role just set person: " + person.getName());
			if (isOpen()) {
				bankGuardRole.msgBankOpen();
			}
			bankPanel.addGui(bankGuardGui);
			return bankGuardRole;
		}
		else if (title == "loanOfficer") {
			//Setting previous bank guard role to inactive
			if (loanOfficerRole.getPerson() != null) {
				((Worker) loanOfficerRole.getPerson()).roleFinishedWork();
			}
			//Setting bank guard role to new role
			loanOfficerRole.setPerson(person);
			if (isOpen()) {
				bankGuardRole.msgBankOpen();
			}
			bankPanel.addGui(loanOfficerGui);
			return loanOfficerRole;
		}
		else if (title.equals("bankTeller")) {
			if (tellers.size() < 4) {
				BankTellerRole t1 = new BankTellerRole(person.getName(), person, title);
				BankTellerGui g = new BankTellerGui(t1);
				bankGuardRole.msgTellerCameToWork(t1);

				t1.setGui(g);
				setTellerPosition(t1, g);
				g.DoGoToWindow();
				tellers.add(t1);
				if (isOpen()) {
					bankGuardRole.msgBankOpen();
				}

				bankPanel.addGui(g);
				return t1;
			}
			else
				return null;
		}
		else
			return null;
	}

	public void goingOffWork(Person person) {
		Worker worker = (Worker) person;

		if (worker.getWorkerRole().equals(bankGuardRole)) {
			((Worker) bankGuardRole.getPerson()).roleFinishedWork();
			bankPanel.removeGui(bankGuardGui);
		}
		else if (worker.getWorkerRole().equals(loanOfficerRole)) {
			((Worker) loanOfficerRole.getPerson()).roleFinishedWork();
			bankPanel.removeGui(loanOfficerGui);
		}
		else if (worker.getWorkerRole() instanceof BankTellerRole){
			BankTellerRole BTR = (BankTellerRole) worker.getWorkerRole();
			bankPanel.removeGui(BTR.getGui());
			//bankPanel.removeGui(worker.getWorkerRole().gui);
		}
	}

	public void msgCustomerArrived(BankCustomerRole BCR) {
		BankCustomerGui BCG= new BankCustomerGui(BCR);
		BCR.setGui(BCG);
		bankPanel.addGui(BCG);
	}

	public void setTellerPosition(BankTeller t1, BankTellerGui g) {
		if (tellers.size() == 0) {
			t1.setTellerWindow(1);
			g.setTellerPosition(1);
		}
		else if (tellers.size() == 1) {
			t1.setTellerWindow(2);
			g.setTellerPosition(2);
		}
		else if (tellers.size() == 2) {
			t1.setTellerWindow(3);
			g.setTellerPosition(3);
		}
		else if (tellers.size() == 3) {
			t1.setTellerWindow(4);
			g.setTellerPosition(4);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen(){
		if (loanOfficerRole.getPerson() != null && bankGuardRole.getPerson() != null && bankGuardRole.getTellers().size() > 0 && !userClosed){
			return true;
		}
		else 
			return false;
	}


	public BankGuard getBankGuard(boolean test) {
		if (test)
			return bankGuardMock;
		else
			return bankGuardRole;
	}

	public LoanOfficer getLoanOfficer(boolean test) {
		if (test)
			return loanOfficerMock;
		else 
			return loanOfficerRole;
	}

	public void setBuildingPanel(BuildingPanel myBuildingPanel) {
		bankPanel = myBuildingPanel;
	}

	public void removeCustomer(BankCustomerRole customerRole) {
		bankPanel.removeGui(customerRole.getGui());
	}

	public void closeBuilding(){
		userClosed = true;
		bankGuardRole.msgLeaveRole();
		for (MyTeller t1: bankGuardRole.tellers){
			((Role) t1.tell1).msgLeaveRole();
		}
		loanOfficerRole.msgLeaveRole();
	}

	//	public void setClosestStop (Point p) {
	//		closestStop = p;
	//	}

	public void setClosestBusStopNumber (int n) 
	{
		busStopNumber = n;
	}

	public BusStop getClosestBusStop ()
	{
		return Phonebook.getPhonebook().getAllBusStops().get(busStopNumber);
	}


	//	public Point getClosestStop() {
	//		return closestStop;
	//	}
}
