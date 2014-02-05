package bank.mock;


import bank.interfaces.BankCustomer;
import bank.interfaces.BankGuard;
import bank.interfaces.BankTeller;
import testing.LoggedEvent;
import testing.Mock;


public class BankGuardMock extends Mock implements BankGuard {

	public BankGuardMock(String name) {
		super(name);
	}

	@Override
	public void msgRobbingBank(BankCustomer bankCustomerRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgCustomerLeavingBank(BankTeller myTeller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgArrivedAtBank(BankCustomer bankCustomerRole) {
		log.add(new LoggedEvent("New customer arrived at Bank"));
	}

	@Override
	public void msgTellerLeavingWork(BankTeller bankTellerRole) {
		// TODO Auto-generated method stub
		
	}

}