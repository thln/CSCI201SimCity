package bank.test;

import person.Worker;
import application.Phonebook;
import bank.BankTellerRole;
import bank.BankTellerRole.Account;
import bank.BankTellerRole.AccountState;
import bank.mock.BankCustomerMock;
import bank.mock.BankGuardMock;
import bank.mock.LoanOfficerMock;
import junit.framework.TestCase;

public class TellerTest extends TestCase 
{

	BankGuardMock guard;
	BankCustomerMock customer;
	LoanOfficerMock officer;
	BankTellerRole teller;
	Worker person;

	public void setUp() throws Exception {
		super.setUp();
		person = new Worker("Josh", 200, "bankTeller", "bank", 800, 1200, 1500);
		guard = new BankGuardMock ("mockguard");
		customer = new BankCustomerMock ("mockcustomer");
		officer = new LoanOfficerMock ("mockofficer");
		officer = (LoanOfficerMock) Phonebook.getPhonebook().getEastBank().getLoanOfficer(true);		
		teller = new BankTellerRole (person.getName(), person, "bankteller");	
		teller.test = true;
	}

	public void testOneCustomerArrivesWantsDepositOpensAccount() {
		try {
			setUp();
		}	catch (Exception e) {
			e.printStackTrace();
		}
		double depositAmt = 200;
		double vault = Phonebook.getPhonebook().getEastBank().vault;

		//check preconditions in teller's personal list of accounts
		assertEquals("Teller should have 0 accounts in list. It doesn't.", teller.getAccounts().size(), 0);

		Phonebook.getPhonebook().getEastBank().accountNumKeyList = 1000;
		assertEquals("Account hash key should be at base number 3000. It isn't.", 
				Phonebook.getPhonebook().getEastBank().accountNumKeyList, 1000);

		//Step 1: AmericanRestaurantCustomer arrives and wants to open new account
		teller.msgWantNewAccount(customer);

		//Step 1 post-conditions
		assertEquals("Teller should have 1 account in list. It doesn't.", teller.getAccounts().size(), 1);

		assertTrue("New account should have the state 'newAccount' ", 
				teller.getAccounts().get(0).getState() == AccountState.newAccount);


		//Step 2: Call scheduler, should execute method "openAccount"
		assertFalse("Teller's scheduler should have returned false, but didn't.", 
				teller.pickAndExecuteAnAction());

		//Step 2 post-conditions
		assertEquals("The list of account hash key should have increased ", 
				Phonebook.getPhonebook().getEastBank().accountNumKeyList, 1001);

		assertEquals("New Account's 'accountNum' should be set to this new hashKey",
				teller.getAccounts().get(0).getAccountNum(), Phonebook.getPhonebook().getEastBank().accountNumKeyList);	

		assertTrue("AmericanRestaurantMockCustomer should have logged an event for new account, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("New Account created"));

		assertTrue("New account should have the state 'neutral' ", 
				teller.getAccounts().get(0).getState() == AccountState.neutral);

		//Step 3: AmericanRestaurantCustomer wants to deposit money into new account
		teller.msgHereIsMyDeposit(depositAmt, teller.getAccounts().get(0).accountNum);

		//Step 3 post-conditions
		assertTrue("Account should have the state 'depositing' ", 
				teller.getAccounts().get(0).getState() == AccountState.depositing);
		
		double credit = teller.getAccounts().get(0).creditScore;

		//Step 4: Call scheduler, should execute method "depositMoney"
		assertFalse("Tellers's scheduler should have returned false, but didn't.", 
				teller.pickAndExecuteAnAction());


		//Step 4 post-conditions

		assertEquals("Bank vault should be equal to the original vault amount + deposit amount",
				Phonebook.getPhonebook().getEastBank().vault, vault + depositAmt);	

		assertEquals("Account balance should have increased by the deposit amount",
				teller.getAccounts().get(0).balance, depositAmt);

		//credit changes
		assertEquals("Account credit score should have increased by 1/10 of the deposit amount",
				teller.getAccounts().get(0).creditScore, credit + (depositAmt/10));

		assertTrue("AmericanRestaurantMockCustomer should have logged an event for deposit received, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), 
				customer.log.containsString("Your deposit of $" + depositAmt + " was received."));

		assertTrue("New account should have the state 'neutral' ", 
				teller.getAccounts().get(0).getState() == AccountState.neutral);

		//Step 5: customer leaves bank
		teller.msgLeavingBank(teller.getAccounts().get(0).getAccountNum());

		//Step 5 post-conditions

		assertEquals("Teller should have 0 accounts in list. It doesn't.", teller.getAccounts().size(), 0);

		assertFalse("Tellers's scheduler should have returned false, but didn't.", 
				teller.pickAndExecuteAnAction());
	}

	public void testTwoCustomerWithdrawalSuccess() {
		try {
			setUp();
		}	catch (Exception e) {
			e.printStackTrace();
		}

		double withdrawAmt = 200;
		double vault = Phonebook.getPhonebook().getEastBank().vault;

		//Create account in Phonebook.Bank's list of accounts to reference
		Account a = new Account(customer);
		a.balance = 500;
		a.accountNum = 2000;
		a.processingMoney = withdrawAmt;
		Phonebook.getPhonebook().getEastBank().accounts.add(a);

		//Preconditions
		assertEquals("Teller should have 0 accounts in list. It doesn't.", teller.getAccounts().size(), 0);

		//Step 1: AmericanRestaurantCustomer arrives and wants to withdraw
		teller.msgINeedMoney(withdrawAmt, a.accountNum);

		//Step 1 post-conditions
		assertEquals("Teller should have 1 account in list. It doesn't.", teller.getAccounts().size(), 1);

		assertTrue("New account should have the state 'withdrawing' ", 
				teller.getAccounts().get(0).getState() == AccountState.withdrawing);


		//Step 2: Call scheduler, should execute method "withdrawMoney"
		assertFalse("Teller's scheduler should have returned false, but didn't.", 
				teller.pickAndExecuteAnAction());

		//Step 2 post-conditions
		assertEquals("Bank vault should be equal to the original vault amount minus withdrawal amount",
				Phonebook.getPhonebook().getEastBank().vault, vault - withdrawAmt);	

		assertTrue("Account should have the state 'neutral' ", 
				teller.getAccounts().get(0).getState() == AccountState.neutral);

		assertTrue("AmericanRestaurantMockCustomer should have logged an event for withdrawal success, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Withdrawal succeeded"));


		//Step 3: customer leaves bank
		teller.msgLeavingBank(teller.getAccounts().get(0).getAccountNum());

		//Step 3 post-conditions	
		assertEquals("Teller should have 0 accounts in list. It doesn't.", teller.getAccounts().size(), 0);

		//Make sure unit tests don't affect real data
		Phonebook.getPhonebook().getEastBank().accounts.remove(a);
	}

	public void testThreeCustomerWantsWithdrawalButNeedsLoanGetsLoan() {
		try {
			setUp();
		}	catch (Exception e) {
			e.printStackTrace();
		}

		double withdrawAmt = 500;
		double vault = Phonebook.getPhonebook().getEastBank().vault;

		//Create account in Phonebook.Bank's list of accounts to reference
		Account a = new Account(customer);
		double balance = 200;
		a.balance = balance;
		a.creditScore = 70;
		a.accountNum = 3000;
		a.processingMoney = withdrawAmt;
		Phonebook.getPhonebook().getEastBank().accounts.add(a);

		//check preconditions in teller's personal list of accounts
		assertEquals("Teller should have 0 accounts in list. It doesn't.", teller.getAccounts().size(), 0);

		//Step 1: AmericanRestaurantCustomer arrives and wants to withdraw
		teller.msgINeedMoney(withdrawAmt, a.accountNum);

		//Step 1 post-conditions
		assertEquals("Teller should have 1 account in list. It doesn't.", teller.getAccounts().size(), 1);

		assertTrue("New account should have the state 'withdrawing' ", 
				teller.getAccounts().get(0).getState() == AccountState.withdrawing);


		//Step 2: Call scheduler, should execute method "withdrawMoney"
		assertFalse("Teller's scheduler should have returned false, but didn't.", 
				teller.pickAndExecuteAnAction());

		//Step 2 post-conditions	
		assertTrue("AmericanRestaurantMockCustomer should have logged an event for lack of account funds, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(),
				customer.log.containsString("Account balance is too low for a withdrawal. Must open loan."));

		assertTrue("New account should have the state 'neutral' ", 
				teller.getAccounts().get(0).getState() == AccountState.neutral);

		//Step 3: customer messages to request loan
		double desiredLoan = withdrawAmt*10;
		teller.msgINeedALoan(desiredLoan, a.accountNum);

		//Step 3 post-conditions
		assertTrue("New account should have the state 'withdrawing' ", 
				teller.getAccounts().get(0).getState() == AccountState.requestingLoan);

		//Step 4: Call scheduler, should execute method "requestLoan"
		assertFalse("Teller's scheduler should have returned false, but didn't.", 
				teller.pickAndExecuteAnAction());

		//Step 4 post-conditions
		assertTrue("New account should have the state 'waiting' ", 
				teller.getAccounts().get(0).getState() == AccountState.waiting);

		assertTrue("Mock loanOfficer should have logged an event for loan request, but his last event logged reads instead: " 
				+ officer.log.getLastLoggedEvent().toString(), 
				officer.log.containsString("Received loan request"));
		
		//Step 5: Loan officer messages -- loan approved
		teller.msgThisLoanApproved(a);

		//Step 5 post-conditions
		assertTrue("New account should have the state 'loanApproved' ", 
				teller.getAccounts().get(0).getState() == AccountState.loanApproved);

		//Step 6: Call scheduler, should execute method "approveLoan"
		assertFalse("Teller's scheduler should have returned false, but didn't.", 
				teller.pickAndExecuteAnAction());

		//Step 6 post-conditions
		assertTrue("New account should have the state 'neutral' ", 
				teller.getAccounts().get(0).getState() == AccountState.neutral);

		assertEquals("Account's balance should have increased by desired loan amount",
				teller.getAccounts().get(0).balance, balance+desiredLoan);	

		assertTrue("AmericanRestaurantMockCustomer should have logged an event for lack of account funds, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(),
				customer.log.containsString("Loan approved. Try withdrawal again."));

		//Step 7: AmericanRestaurantCustomer tries to withdraw again

		teller.msgINeedMoney(withdrawAmt, a.accountNum);

		//Step 7 post-conditions
		assertEquals("Teller should have 1 account in list. It doesn't.", teller.getAccounts().size(), 1);

		assertTrue("Account should have the state 'withdrawing' ", 
				teller.getAccounts().get(0).getState() == AccountState.withdrawing);


		//Step 8: Call scheduler, should execute method "withdrawMoney"
		assertFalse("Teller's scheduler should have returned false, but didn't.", 
				teller.pickAndExecuteAnAction());

		//Step 8 post-conditions
		assertEquals("Bank vault should be equal to the original vault amount minus withdrawal amount",
				Phonebook.getPhonebook().getEastBank().vault, vault - withdrawAmt);	

		assertTrue("New account should have the state 'neutral' ", 
				teller.getAccounts().get(0).getState() == AccountState.neutral);

		assertTrue("AmericanRestaurantMockCustomer should have logged an event for withdrawal success, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Withdrawal succeeded"));

		//Step 9: customer leaves bank
		teller.msgLeavingBank(teller.getAccounts().get(0).getAccountNum());

		//Step 9 post-conditions

		assertEquals("Teller should have 0 accounts in list. It doesn't.", teller.getAccounts().size(), 0);

		//Make sure unit tests don't affect real data
		Phonebook.getPhonebook().getEastBank().accounts.remove(a);
	}

	public void testFourCustomerWantsWithdrawalNeedsLoanButDenied() {
		try {
			setUp();
		}	catch (Exception e) {
			e.printStackTrace();
		}

		double withdrawAmt = 500;

		//Create account in Phonebook.Bank's list of accounts to reference
		Account a = new Account(customer);
		double balance = 200;
		a.balance = balance;
		a.creditScore = 30;
		a.accountNum = 4000;
		a.processingMoney = withdrawAmt;
		Phonebook.getPhonebook().getEastBank().accounts.add(a);

		//check preconditions in teller's personal list of accounts
		assertEquals("Teller should have 0 accounts in list. It doesn't.", teller.getAccounts().size(), 0);

		//Step 1: AmericanRestaurantCustomer arrives and wants to withdraw
		teller.msgINeedMoney(withdrawAmt, a.accountNum);

		//Step 1 post-conditions
		assertEquals("Teller should have 1 account in list. It doesn't.", teller.getAccounts().size(), 1);

		assertTrue("New account should have the state 'withdrawing' ", 
				teller.getAccounts().get(0).getState() == AccountState.withdrawing);


		//Step 2: Call scheduler, should execute method "withdrawMoney"
		assertFalse("Teller's scheduler should have returned false, but didn't.", 
				teller.pickAndExecuteAnAction());

		//Step 2 post-conditions	
		assertTrue("AmericanRestaurantMockCustomer should have logged an event for lack of account funds, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(),
				customer.log.containsString("Account balance is too low for a withdrawal. Must open loan."));

		assertTrue("New account should have the state 'neutral' ", 
				teller.getAccounts().get(0).getState() == AccountState.neutral);

		//Step 3: customer messages to request loan
		double desiredLoan = withdrawAmt*10;
		teller.msgINeedALoan(desiredLoan, a.accountNum);

		//Step 3 post-conditions
		assertTrue("New account should have the state 'requestingLoan' ", 
				teller.getAccounts().get(0).getState() == AccountState.requestingLoan);

		//Step 4: Call scheduler, should execute method "requestLoan"
		assertFalse("Teller's scheduler should have returned false, but didn't.", 
				teller.pickAndExecuteAnAction());

		//Step 4 post-conditions
		assertTrue("New account should have the state 'waiting' ", 
				teller.getAccounts().get(0).getState() == AccountState.waiting);
		
		assertTrue("Mock loanOfficer should have logged an event for loan request, but his last event logged reads instead: " 
				+ officer.log.getLastLoggedEvent().toString(), 
				officer.log.containsString("Received loan request"));

		//Step 5: Loan officer messages -- loan denied
		teller.msgThisLoanDenied(a, a.creditScore*10);

		//Step 5 post-conditions
		assertTrue("New account should have the state 'loanDenied' ", 
				teller.getAccounts().get(0).getState() == AccountState.loanDenied);

		//Step 6: Call scheduler, should execute method "denyLoan"
		assertFalse("Teller's scheduler should have returned false, but didn't.", 
				teller.pickAndExecuteAnAction());

		//Step 6 post-conditions
		assertTrue("New account should have the state 'neutral' ", 
				teller.getAccounts().get(0).getState() == AccountState.neutral);

		assertTrue("AmericanRestaurantMockCustomer should have logged an event for poor credit score, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(),
				customer.log.containsString("Your credit score is too low for the requested loan."));

		//Step 7: customer leaves bank
		teller.msgLeavingBank(teller.getAccounts().get(0).getAccountNum());

		//Step 7 post-conditions

		assertEquals("Teller should have 0 accounts in list. It doesn't.", teller.getAccounts().size(), 0);

		//Make sure unit tests don't affect real data
		Phonebook.getPhonebook().getEastBank().accounts.remove(a);
	}

	public void testFiveCustomPaysOffLoan() {
		try {
			setUp();
		}	catch (Exception e) {
			e.printStackTrace();
		}

		double loan = 500;
		double creditScore = 70;
		//Create account in Phonebook.Bank's list of accounts to reference
		Account a = new Account(customer);
		double balance = 200;
		a.balance = balance;
		a.creditScore = creditScore;
		a.accountNum = 5000;
		a.loan = loan;
		a.processingMoney = loan;
		Phonebook.getPhonebook().getEastBank().accounts.add(a);

		//check preconditions in teller's personal list of accounts
		assertEquals("Teller should have 0 accounts in list. It doesn't.", teller.getAccounts().size(), 0);

		//Step 1: AmericanRestaurantCustomer arrives and wants to pay off loan
		teller.msgPayingOffLoan(a.loan, a.accountNum);


		//Step 1 post-conditions
		assertEquals("Teller should have 1 account in list. It doesn't.", teller.getAccounts().size(), 1);

		assertTrue("New account should have the state 'closingLoan' ", 
				teller.getAccounts().get(0).getState() == AccountState.closingLoan);

		//Step 2: Call scheduler, should execute method "closeLoan"
		assertFalse("Teller's scheduler should have returned false, but didn't.", 
				teller.pickAndExecuteAnAction());

		//Step 2 post-conditions

		//credit changes
		assertEquals("Account credit score should have increased by 1/10 of the deposit amount",
				teller.getAccounts().get(0).creditScore, creditScore + loan/10);

		assertTrue("New account should have the state 'neutral' ", 
				teller.getAccounts().get(0).getState() == AccountState.neutral);

		assertTrue("AmericanRestaurantMockCustomer should have logged an event for lack of account funds, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(),
				customer.log.containsString("Loan payed for and closed"));

		//Step 3: customer leaves bank
		teller.msgLeavingBank(teller.getAccounts().get(0).getAccountNum());

		//Step 3 post-conditions

		assertEquals("Teller should have 0 accounts in list. It doesn't.", teller.getAccounts().size(), 0);

		//Make sure unit tests don't affect real data
		Phonebook.getPhonebook().getEastBank().accounts.remove(a);
	}
}
