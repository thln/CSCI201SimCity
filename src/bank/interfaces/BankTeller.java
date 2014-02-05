package bank.interfaces;

import bank.BankTellerRole.Account;

public interface BankTeller {

	String getName();

	void msgINeedMoney(double desiredCash, int accountNum);

	void msgHereIsMyDeposit(double depositAmount, int accountNum);

	void msgINeedALoan(double desiredLoanAmount, int accountNum);

	void msgPayingOffLoan(double loan, int accountNum);

	void msgWantNewAccount(BankCustomer bankCustomer);

	void msgThisLoanDenied(Account account1, double i);

	void msgThisLoanApproved(Account account1);

	void msgLeavingBank(int accountNum);
	
	void setTellerWindow(int window);
	
	int getTellerPosition();
}
