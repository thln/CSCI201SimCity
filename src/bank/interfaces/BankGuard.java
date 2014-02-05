package bank.interfaces;

public interface BankGuard {

	void msgRobbingBank(BankCustomer bankCustomerRole);

	void msgCustomerLeavingBank(BankTeller myTeller);

	void msgArrivedAtBank(BankCustomer bankCustomerRole);

	void msgTellerLeavingWork(BankTeller bankTellerRole);
}
