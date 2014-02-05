package bank.interfaces;

public interface BankCustomer {

	void msgGoToTeller(BankTeller tell1);

	void msgCaughtYou();

	void msgGotAway(double spoils);

	void msgLoanClosed();

	void msgYourLoanWasApproved();

	void msgYourLoanWasDenied(double processingMoney);

	void msgDepositReceived();

	void msgInsufficientFunds();

	void msgBankrupt();

	void msgHereIsYourMoney(double processingMoney);

	void msgHereIsNewAccount(int accountNum);

	void msgNoTellerAvailable();

	void msgComeIn();
	
	void setWaitPlace(int waitPlace);
}
