package bank.mock;

import bank.BankTellerRole.Account;
import bank.interfaces.BankCustomer;
import bank.interfaces.BankTeller;
import testing.LoggedEvent;
import testing.Mock;


public class BankTellerMock extends Mock implements BankTeller {
	
	public Account account1; 
	
	public BankTellerMock(String name) {
		super(name);
	}

	@Override
	public void msgINeedMoney(double desiredCash, int accountNum) {
		log.add(new LoggedEvent("AmericanRestaurantCustomer wants to withdraw from account."));
	}

	@Override
	public void msgHereIsMyDeposit(double depositAmount, int accountNum) {
		log.add(new LoggedEvent("Received deposit of $" +depositAmount));
	}

	@Override
	public void msgINeedALoan(double desiredLoanAmount, int accountNum) {
		log.add(new LoggedEvent("AmericanRestaurantCustomer requested loan."));
	}

	@Override
	public void msgPayingOffLoan(double loan, int accountNum) {
		log.add(new LoggedEvent("AmericanRestaurantCustomer paid off loan."));
	}

	@Override
	public void msgWantNewAccount(BankCustomer bankCustomer) {
		log.add(new LoggedEvent("AmericanRestaurantCustomer wants new account."));
	}

	@Override
	public void msgThisLoanDenied(Account account1, double i) {
		log.add(new LoggedEvent("My customer's loan was denied, but can apply for a loan of less than " + i + " dollars"));
	}

	@Override
	public void msgThisLoanApproved(Account account1) {
		log.add(new LoggedEvent("My customer's loan was approved"));
	}

	@Override
	public void msgLeavingBank(int accountNum) {
		log.add(new LoggedEvent("AmericanRestaurantCustomer left bank."));
	}
	
	public void setTellerWindow(int window) {
		// TODO Auto-generated method stub
	}
	
	public int getTellerPosition() {
		// TODO Auto-generated method stub
		return 0;
	}
}
