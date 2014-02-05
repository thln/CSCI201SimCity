package bank.mock;

import bank.BankTellerRole.Account;
import bank.interfaces.BankTeller;
import bank.interfaces.LoanOfficer;
import testing.LoggedEvent;
import testing.Mock;


public class LoanOfficerMock extends Mock implements LoanOfficer {

	public LoanOfficerMock(String name) {
		super(name);
	}

	@Override
	public void msgIsLoanApproved(Account account1, BankTeller bankTeller) {
		log.add(new LoggedEvent("Received loan request"));
	}
	

}