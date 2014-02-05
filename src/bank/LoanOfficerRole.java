package bank;

import person.Person;
import person.Role;
import person.Worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.Phonebook;
import bank.BankTellerRole.Account;
import bank.interfaces.BankTeller;
import bank.interfaces.LoanOfficer;

public class LoanOfficerRole extends Role implements LoanOfficer {


	//Data

	String name;
	protected String RoleName = "Loan Officer";
	public enum LoanState {requesting, open, closed}
	public class Loan {
		Account account1;
		LoanState state = LoanState.requesting;
		BankTeller teller1;

		Loan(Account acc1, BankTeller t1) {
			account1 = acc1;
			teller1 = t1;
		}

		public LoanState getState() {
			return state;
		}
	}

	public List<Loan> loans;

	public LoanOfficerRole(String name, Person p1, String roleName) {
		super(p1, name, roleName);
		loans = new ArrayList<>();
	}

	public LoanOfficerRole(String roleName) {
		super(roleName);
		loans = new ArrayList<>();
	}

	//Messages
	public void msgIsLoanApproved(Account account1, BankTeller t1) {
		print("Received loan request");
		loans.add(new Loan(account1, t1));
		stateChanged();
	}

	// Scheduler

	public boolean pickAndExecuteAnAction () {

		for (Loan loan1: loans) {
			if (loan1.state == LoanState.requesting)
				ProcessLoan(loan1);
			return false;
		}

		if (leaveRole && (((BankGuardRole) Phonebook.getPhonebook().getEastBank().getBankGuard(test)).tellers.size () == 0 || 
				((BankGuardRole) Phonebook.getPhonebook().getEastBank().getBankGuard(test)).person == null)){
			Phonebook.getPhonebook().getEastBank().goingOffWork(this.person);
			try{
			((Worker) person).roleFinishedWork();
			}
			catch (Exception e){
				
			};	
			leaveRole = false;
			return true;
		}

		return false;
	}


	//Actions

	void ProcessLoan (Loan loan1) {

		if (loan1.account1.processingMoney <= (loan1.account1.creditScore * 10))		//You are allowed a loan 10 times your credit score
			loan1.teller1.msgThisLoanApproved(loan1.account1);
		else
			loan1.teller1.msgThisLoanDenied(loan1.account1, loan1.account1.creditScore*10); 

		loans.remove(loan1);
	}

	public List<Loan> getLoans() {
		return loans;
	}

}
