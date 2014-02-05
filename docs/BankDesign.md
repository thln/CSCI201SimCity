#Bank Design

###Opening a New Account
![New Account Diagram](https://raw.github.com/usc-csci201-fall2013/team20/master/docs/InteractionDiagrams/BankNew.png?token=3290221__eyJzY29wZSI6IlJhd0Jsb2I6dXNjLWNzY2kyMDEtZmFsbDIwMTMvdGVhbTIwL21hc3Rlci9kb2NzL0ludGVyYWN0aW9uRGlhZ3JhbXMvQmFua05ldy5wbmciLCJleHBpcmVzIjoxMzg2MDkyNjg1fQ%3D%3D--08b1310e1d4e1c95829357f684c05de51e6230d5)
###Depositing into an Account
![Bank Deposit Diagram](https://raw.github.com/usc-csci201-fall2013/team20/master/docs/InteractionDiagrams/BankDeposit.png?token=3290221__eyJzY29wZSI6IlJhd0Jsb2I6dXNjLWNzY2kyMDEtZmFsbDIwMTMvdGVhbTIwL21hc3Rlci9kb2NzL0ludGVyYWN0aW9uRGlhZ3JhbXMvQmFua0RlcG9zaXQucG5nIiwiZXhwaXJlcyI6MTM4NjEzODg0Mn0%3D--d34b4a5961f0e8a6bb9935e5efd5c4eef7d14127)
###Withdrawing from an Account
![Bank Withdrawal Diagram](https://raw.github.com/usc-csci201-fall2013/team20/master/docs/InteractionDiagrams/BankWidthdraw.png?token=3290221__eyJzY29wZSI6IlJhd0Jsb2I6dXNjLWNzY2kyMDEtZmFsbDIwMTMvdGVhbTIwL21hc3Rlci9kb2NzL0ludGVyYWN0aW9uRGlhZ3JhbXMvQmFua1dpZHRoZHJhdy5wbmciLCJleHBpcmVzIjoxMzg2MTM4OTQ5fQ%3D%3D--6d0fff1b72bb37a8daf13118014f2594ffc448ac)
###Processing a New Loan
![New Loan Diagram](https://raw.github.com/usc-csci201-fall2013/team20/master/docs/InteractionDiagrams/BankNew.png?token=3290221__eyJzY29wZSI6IlJhd0Jsb2I6dXNjLWNzY2kyMDEtZmFsbDIwMTMvdGVhbTIwL21hc3Rlci9kb2NzL0ludGVyYWN0aW9uRGlhZ3JhbXMvQmFua05ldy5wbmciLCJleHBpcmVzIjoxMzg2MTM4OTA5fQ%3D%3D--44fa3a95eae152e7dfc5437d1f0094c8e30ba693)
###Paying Off a Loan
![Paying a Loan Diagram](https://raw.github.com/usc-csci201-fall2013/team20/master/docs/InteractionDiagrams/BankPayLoan.png?token=3290221__eyJzY29wZSI6IlJhd0Jsb2I6dXNjLWNzY2kyMDEtZmFsbDIwMTMvdGVhbTIwL21hc3Rlci9kb2NzL0ludGVyYWN0aW9uRGlhZ3JhbXMvQmFua1BheUxvYW4ucG5nIiwiZXhwaXJlcyI6MTM4NjEzODkzMH0%3D--9ed4096ab59a33301922beb6b943b941d84cf3d2)

# BankCustomerRole

<pre><code>
//people request loans when desired cash is less than account balance
</code></pre>

### Data

<pre><code>
enum BankCustomerDesire {none, withdraw, deposit, wantLoan, closeLoan, openAccount, closeAccount, robBank, leaveBank};
enum CustomerState {atBank, none, waiting, ready};
BankCustomerDesire desire = openAccount;
BankTeller myTeller;
GuardRole guard1;
double desiredLoanAmount;
CustomerState state = atBank;
String RoleName = "Bank Customer";
</code></pre>

### Messages

<pre><code>
msgComeIn() {
}

msgGoToTeller(BankTeller tell1) {
	myTeller = tell1;
	state = CustomerState.ready;
}

msgNoTellerAvailable(){
	state = CustomerState.waiting;
}

HereIsYourMoney(double amount) {
	person.money += amount;
	desire = BankCustomerDesire.leaveBank;
	state = CustomerState.ready;
}

HereIsNewAccount (int accountNum) {
	person.accountNum = accountNum;
	if (person.money <= person.moneyMinThreshold)
		desire = BankCustomerDesire.withdraw;
	else if (person.money >= person.moneyMaxThreshold)
		desire = BankCustomerDesire.deposit;
	else	
		desire = BankCustomerDesire.leaveBank;
		state = CustomerState.ready;
}

msgBankrupt() {
	state = CustomerState.ready;
	desire = BankCustomerDesire.leaveBank;
}

msgInsufficentFunds(){
	state = CustomerState.ready;
	desire = BankCustomerDesire.wantLoan;
}

msgDepositReceived() {
	desire = BankCustomerDesire.leaveBank;
	state = CustomerState.ready;
}

msgYourLoanWasApproved() {
	desire = BankCustomerDesire.withdraw;
	state = CustomerState.ready;
}

msgYourLoanWasDenied(double amount) {
	//decide whether or not to request another loan
	state = CustomerState.ready;
	desire = BankCustomerDesire.leaveBank;
}

msgLoanClosed() {
	state = CustomerState.ready;
	desire = BankCustomerDesire.leaveBank;
}	

msgCaughtYou() {
	state = CustomerState.ready;
}

msgGotAway() {
	state = CustomerState.ready;
}
</code></pre>
	
### Scheduler

<pre><code>
if (state == CustomerState.waiting) {

}
if (state == CustomerState.atBank) {
	messageGuard();
}
if (state == CustomerState.ready) {
	if(desire == openAccount)
	openAccount();
	
	if (desire == withdraw)
	withdrawCash();

	if (desire == deposit)
	depositCash();

	if (desire == wantLoan)
	requestLoan();

	if (desire == closeLoan)
	payOffLoan();
	
	if(desire == leaveBank)
	leaveBank();
	
	if (desire == robBank)
	robBank();
}
</code></pre>

### Actions

<pre><code>
messageGuard () {
	bank.BankGuard.msgArrivedAtBank(this);
	state = CustomerState.waiting;
}

withdrawCash() {
	myTeller.INeedMoney(desiredCashAmount,accountNum);
	state = CustomerState.waiting;
}

depositCash () {
	person.money -= person.depositAmount;
	myTeller.msgHereIsMyDeposit(person.depositAmount, person.accountNum);
	state = CustomerState.waiting;
}

requestLoan() {
	desiredLoanAmount = 10*desiredCashAmount;
	myTeller.msgINeedALoan(desiredLoanAmount, person.accountNum);
	state = CustomerState.waiting;
}

payOffLoan() {
	person.money -= person.loan;
	myTeller.PayingOffLoan(person.loan, person.accountNum);
	state = CustomerState.waiting;
}

openAccount () {
	myTeller.WantNewAccount(this);
	state = CustomerState.waiting;
}

leaveBank () {	
	//GUI operation
	desire = BankCustomerDesire.none;
	state = CustomerState.waiting;	
	myTeller.msgLeavingBank(person.accountNum);
	bank.bankGuard.msgCustomerLeavingBank(myTeller);
	myTeller = null;
	this.setRoleInactive();
}

robBank () {
	//Animation();
	guard1.RobbingBank(this);
	state = CustomerState.waiting;
}
</code></pre>

# Teller
### Data
<pre><code>
int balanceMinimum = 5;
String name;
List<Account> myAccounts;
enum AccountState {neutral, newAccount, waiting, depositing, withdrawing, requestingLoan, 
		closingLoan, loanApproved, loanDenied, bankEmpty, leavingBank}
class Account {
	BankCustomer customer;
	int accountNum; 		//the hash key
	double loan = 0;
	double balance = 0;
	double creditScore = 150;
	double processingMoney = 0;
	AccountState state;
}

List<Account> accounts;
BankRole bank;
LoanOfficerRole myLoaner;
</code></pre>

### Messages

<pre><code>
msgWantNewAccount (BankCustomer cust1) {
	Account a1 = new Account(cust1);
	bank.accounts.add(a1);
	a1.setState(AccountState.newAccount);
	myAccounts.add(a1);
}

msgINeedMoney(double desiredAmount, int accountNum) {
	Account correct = findMyAccount (accountNum);
	correct.processingMoney = desiredAmount;
	correct.state = AccountState.withdrawing;
}

msgHereIsMyDeposit(double amount, int accountNum) {
	Account correct = findMyAccount (accountNum);
	correct.processingMoney = amount;
	correct.state = AccountState.depositing;

msgINeedALoan(double desiredLoan, int accountNum) {
	Account correct = findMyAccount (accountNum);
	correct.processingMoney = desiredLoan;
	correct.state = AccountState.requestingLoan;
}

msgPayingOffLoan(double loan, int accountNum) {
	Account correct = findMyAccount (accountNum);
	correct.processingMoney = loan;
	correct.state = AccountState.closingLoan;stateChanged();
}

msgThisLoanApproved(Account account1) {
	account1.state = AccountState.loanApproved;
}

msgThisLoanDenied (Account account1, double possibleLoan) {
	account1.state = AccountState.loanDenied;
	account1.processingMoney = possibleLoan;
}

msgLeavingBank(int accountNum) {
	Account correct = findMyAccount (accountNum);
	myAccounts.remove(correct);
}
</code></pre>

### Scheduler

<pre><code>
If (∃ Account ∈  accounts | Account.state == new)
	OpenAccount(Account);

If (∃ Account ∈  accounts | Account.state == withdrawing)
	WithdrawMoney(Account);

If (∃ Account ∈  accounts | Account.state == depositing)
	DepositMoney(Account);

If (∃ Account ∈  accounts | Account.state == requestingLoan)
	RequestLoan(Account);

If (∃ Account ∈  accounts | Account.state == closingLoan)
	CloseLoan(Account);

If (∃ Account ∈  accounts | Account.state == loanApproved)
	ApproveLoan(Account);

If (∃ Account ∈  accounts | Account.state == loanDenied)
	DenyLoan(Account);

If (∃ Account ∈  accounts | Account.state == closingLoan)
	CloseLoan(Account);
	
If (leaveRole){
	leaveRole = false;
	if (((Role) bank.bankGuard.person != null)
				bank.bankGuard.msgTellerLeavingWork(this);
			try {
				((Worker) person).roleFinishedWork();	
			}
			catch (Exception e){

			}
		}
}
</code></pre>

### Actions

<pre><code>
Account FindAccount (int accountNum) {
	for (Account a: myAccounts) {
		if (a.getAccountNum() == accNum) {
			return a;		
		}
	}
	return findPhonebookAccount(accNum);
}

Account findPhonebookAccount (int accNum) {
	for (Account a: bank.accounts) {
		if (a.getAccountNum() == accNum) {
			myAccounts.add(a);		//Anytime we must search through the global list, 		
			return a;						//we need add account to the local one for easy future access
		}
	}
	return null;
}

OpenAccount (Account account1) {
	//generate key (accountNum)
	int hashKey = ++bank.accountNumKeyList;
	account1.setAccountNum(hashKey);
	account1.customer.msgHereIsNewAccount(account1.getAccountNum());
	account1.state = AccountState.neutral;
}

WithdrawMoney(Account account1) {
	if (account1.balance > (account1.processingMoney + balanceMinimum)) {
		bank.vault -= account1.processingMoney;
		account1.state = neutral;
		account1.customer.HereIsYourMoney(account1.processingMoney);
	}
	else
		account1.customer.msgInsufficientFunds();
	account1.processingMoney = 0;
	account1.state = AccountState.neutral;
}

DepositMoney(Account account1) {
	bank.vault += account1.processingMoney;
	account1.balance +=  account1.processingMoney;
	account1.creditScore += account1.processingMoney/10;	//every time you deposit money, your credit goes up…the bank can trust that you have money
	account1.customer.msgDepositReceived();
	account1.state = neutral;
}

RequestLoan (Account account1) {
	LoanOfficer.msgIsLoanApproved(account1, this);
	account1.state = waiting;
}

CloseLoan (Account account1) {	
	account1.creditScore += account1.loan/10;
	account1.loan = 0;
	account1.state = neutral;
	account1.customer.LoanClosed();
}

ApproveLoan (Account account1) {
	account1.state = neutral;
	account1.loan = account1.processingMoney;
	account1.balance += account1.loan;
	account1.customer.YourLoanWasApproved();
}

DenyLoan (Account account1) {
	account1.state = neutral;	
	account1.customer.YourLoanWasDenied(account1.processingMoney);	//loan denied, but given your credit score you can have a loan of size (processingMoney)
}
</code></pre>

# Loan Officer
### Data

<pre><code>
String name;
String RoleName = "Loan Officer";
enum LoanState {requesting, open, closed};
class Loan {
	Account account1;
	LoanState state = requesting;
	Teller teller1;
}
List<Loan> loans;
</code></pre>

### Messages

<pre><code>
msgIsLoanApproved(Account account1, Teller t1) {
	loans.add( new Loan(account1, t1));
}
</code></pre>


### Scheduler

<pre><code>
If (∃ Loan ∈  loans | Loan.state == requesting)
	ProcessLoan(Loan);
If (leaveRole){
	try{
		((Worker) person).roleFinishedWork();
	}
	catch (Exception e){
		
	};
	leaveRole = false;
}
</code></pre>

### Actions

<pre><code>
ProcessLoan (Loan loan1) {

	if (loan1.account1.processingMoney <= (loan1.account1.creditScore * 10))		//You are allowed a loan 10 times your credit score
		loan1.teller1.msgThisLoanApproved(loan1.account1);
	else
		loan1.teller1.msgThisLoanDenied(loan1.account1, loan1.account1.creditScore*10); 

	loans.remove(loan1);
}
</code></pre>

# Guard

### Data

<pre><code>
List<BankCustomer> customers;
List<BankCustomer> robbers;
List<MyTeller> tellers;		//initialized at beginning
String roleName = "Bank Guard";
class MyTeller {
	TellerState state;
	BankTeller tell1;
}
enum tellerState {available, busy};
</code></pre>

### Messages

<pre><code>
msgTellerCameToWork (BankTeller t1) {
	tellers.add(new MyTeller(t1));
}

msgTellerLeavingWork(BankTeller t1) {
	if (t1 instanceof Role)
	tellers.remove(t1);
}

msgRobbingBank(BankCustomer cust1) {
	robbers.add(cust1);
}

msgArrivedAtBank(BankCustomer c1) {
	customers.add(c1);
}

msgCustomerLeavingBank (BankTeller t1) {
	MyTeller correct = findTeller(t1);
	correct.state = available;
}
</code></pre>

### Scheduler

<pre><code>
If (∃ BankCustomer ∈  robbers) 
	catchRobber(BankCustomer);

If (∃ BankCustomer ∈  customers)
assignToTeller(BankCustomer); 
if (leaveRole){
	leaveRole = false;
	try {
		((Worker) person).roleFinishedWork();	
	}
	catch (Exception e){

	}
}

</code></pre>

### Actions

<pre><code>
findTeller (BankTeller t1) {
	for (MyTeller teller1: tellers) {
	if (teller.tell1 = t1)
		return teller;
	}
}

catchRobber(BankCustomer robber1) {
//95% chance Robber is caught, 5% he gets away;
	if (caught)
		robber1.CaughtYou();
	if (!caught)
		robber1.GotAway();	
}

assignToTeller(BankCustomer cust1) {
	for (MyTeller teller1: tellers) {
		if (teller1.state = available && bank.isOpen()) {
			cust1.GoToTeller(teller1.tell1);
			teller1.state = busy;
			customers.remove(cust1);
		}
	}
	cust1.msgNoTellerAvailable();
}

msgBankOpen() {
	if (customers.size() != 0){
		for (BankCustomer c1: customers){
			c1.msgComeIn();
		}
	}
}
</code></pre>