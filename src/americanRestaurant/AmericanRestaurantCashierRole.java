//package americanRestaurant;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Timer;
//import java.util.concurrent.Semaphore;
//
//import market.Market;
//import bank.BankGuardRole.MyTeller;
//import bank.interfaces.BankCustomer;
//import person.Person;
//import person.Role;
//import testing.EventLog;
//import americanRestaurant.AmericanRestaurantWaiterRole.Menu;
//import americanRestaurant.AmericanRestaurantWaiterRole.MyCustomer;
//import americanRestaurant.interfaces.AmericanRestaurantCashier;
//import americanRestaurant.interfaces.AmericanRestaurantCustomer;
//import americanRestaurant.interfaces.AmericanRestaurantHost;
//import americanRestaurant.interfaces.AmericanRestaurantWaiter;
//
//public class AmericanRestaurantCashierRole extends Role implements AmericanRestaurantCashier{
//	//DATA
//	AmericanRestaurant myRestaurant;
//	
//	public enum checkState {ready, paying, unpaid, waitingForCust, inDebt, paidDebt};
//
//	public class MyCheck {	
//		private AmericanRestaurantCustomer cust1;
//		private int check;
//		int cash;
//		private checkState state;
//		private AmericanRestaurantWaiter wait1;
//		String choice;
//
//
//		public MyCheck (AmericanRestaurantWaiter w1, AmericanRestaurantCustomer c1, int check1, int cash1, String choice1) {
//			this.setCust1(c1);
//			this.wait1 = w1;
//			this.setCheck(check1);
//			this.cash = cash1;
//			this.choice = choice1;
//			setState(checkState.ready);
//		}
//
//		public checkState getState() {
//			return state;
//		}
//
//
//		public void setState(checkState state) {
//			this.state = state;
//		}
//
//
//		public int getCheck() {
//			return check;
//		}
//
//
//		public void setCheck(int check) {
//			this.check = check;
//		}
//
//
//		public AmericanRestaurantCustomer getCust1() {
//			return cust1;
//		}
//
//
//		public void setCust1(AmericanRestaurantCustomer cust1) {
//			this.cust1 = cust1;
//		}
//
//
//		public void setCash(int cash2) {
//			this.cash = cash2;
//		}
//	}
//
//	enum marketBillState {pending, unpaid};
//	
//	public class MarketBill {
//		private int bill;
//		private Market market1;
//		marketBillState state;
//	
//
//		public MarketBill(Market m1, int bill1) {
//			bill = bill1;
//			market1 = m1;
//			state = marketBillState.pending;
//		}
//
//		public Market getMarket(){
//			return market1;
//		}
//
//		public int getBill() {
//			return bill;
//		}
//	}
//
//	AmericanRestaurantHost myHost;
//	private List<MyCheck> checks;
//	public EventLog log;
//	private List<MarketBill> marketBills;
//	private int cashRegister;			//holds all of the money in the restaurant
//
//	//Constructor
//
//	public AmericanRestaurantCashierRole (String name, AmericanRestaurant restaurant) {
//		super(name);
//		myRestaurant = restaurant;
//		checks = Collections.synchronizedList(new ArrayList<MyCheck>());
//		log = new EventLog();
//		setCashRegister(120);	
//		marketBills = Collections.synchronizedList(new ArrayList<MarketBill>());
//	}
//	
//	public AmericanRestaurantCashierRole(Person p1, String pName, String rName, AmericanRestaurant restaurant) {
//		super(p1, pName, rName);
//		myRestaurant = restaurant;
//		checks = Collections.synchronizedList(new ArrayList<MyCheck>());
//		log = new EventLog();
//		setCashRegister(120);
//		marketBills = Collections.synchronizedList(new ArrayList<MarketBill>());
//	}
//
//	// MESSAGES
//
//	@Override
//	public void msgComputeBill(AmericanRestaurantWaiter w, AmericanRestaurantCustomer c1, String choice) {
//		checks.add(new MyCheck(w, c1, 0, 0, choice));
//		stateChanged();
//	}
//
//	@Override
//	public void msgHereIsMyPayment(AmericanRestaurantCustomer c1, int check, int cash) {
//		//log.add(new LoggedEvent("Received ReadyToPay"));
//		MyCheck correct = findCheck(c1);
//		correct.setState(checkState.paying);
//		correct.setCheck(check);
//		correct.setCash(cash);
//		stateChanged();
//	}
//
//	@Override
//	public void msgNotEnoughMoney (AmericanRestaurantCustomer c1, int check, int cash) {
//		//log.add(new LoggedEvent("Received NotEnoughMoney"));
//		MyCheck correct = findCheck(c1);
//		correct.setState(checkState.unpaid);
//		correct.setCheck(check);
//		correct.setCash(cash);
//		stateChanged();
//	}
//
//
//
//	@Override
//	public void msgPayDebt (AmericanRestaurantCustomer c1, int debt, int cash) {
//		//log.add(new LoggedEvent("Received PayDebt"));
//		MyCheck correct = findCheck(c1);
//		correct.setState(checkState.paidDebt);
//		stateChanged();
//	}
//
//	public void msgPayMarketBill (Market m1, int bill) {
//		//log.add(new LoggedEvent("Received PayMarketBill"));
//		print("Received bill of $" + bill + " from market.");
//		marketBills.add(new MarketBill(m1,bill));
//		stateChanged();
//	}
//
//	//SCHEDULER
//
//	public boolean pickAndExecuteAnAction() {
//
//		synchronized (checks) {
//			if (checks.size() > 0){
//				for (MyCheck check1: checks) {
//
//					if (check1.getState() == checkState.ready) {
//						ComputeCheck(check1);
//						return true;
//					}
//
//					if (check1.getState() == checkState.unpaid) {
//						DishonestCustomer(check1);
//						return true;
//					}
//
//
//					if (check1.getState() == checkState.paying) {
//						ComputeChange(check1);
//						return true;
//					}
//
//					if (check1.getState() == checkState.paidDebt) {
//						RemoveDebt(check1);
//						return true;
//					}
//				}
//			}
//		}
//		
//		synchronized (marketBills) {
//			if (marketBills.size() > 0) {
//				for (MarketBill mBill: marketBills) {
//					if (mBill.state == marketBillState.pending || getCashRegister() >= mBill.getBill())
//						PayMarketBill(mBill);
//					return false;
//				}
//			}
//		}
//
//		return false;
//
//	}
//
//	// ACTIONS
//
//	private MyCheck findCheck(AmericanRestaurantCustomer c1) {
//		for (MyCheck check1: checks){
//			if (check1.getCust1().equals(c1))
//				return check1;
//		}			
//		return null;
//	}
//
//	private void ComputeCheck(MyCheck check1) {
//		print("Computing check");
//		int check = Menu.GetPrice(check1.choice);
//		check1.wait1.msgHereIsCheck(check1.cust1, check);
//		check1.state = checkState.waitingForCust;
//	}
//
//	private void ComputeChange (MyCheck check1) {
//		print("Computing change");
//		setCashRegister(getCashRegister() + check1.check);
//		int change = check1.cash - check1.check;
//		check1.getCust1().msgHereIsChange(change);
//		checks.remove(check1);
//	}
//
//	private void DishonestCustomer (MyCheck check1) {
//		print(check1.getCust1().getName() + " is in debt. Pay next time...");
//		int debt = check1.cash - check1.getCheck();
//		check1.getCust1().msgYouAreInDebt(debt);
//		myHost.msgWatchThisCust(check1.getCust1());
//		check1.setState(checkState.inDebt);
//	}
//
//	private void RemoveDebt (MyCheck check1) {
//		print("Debt paid.");
//		setCashRegister(getCashRegister() + check1.getCheck());
//		check1.getCust1().setCash(check1.getCust1().getCash() - check1.getCheck());
//		check1.getCust1().setDebt(0);
//		checks.remove(check1);
//		myHost.msgDebtPaid(check1.getCust1());
//	}
//
//	private void PayMarketBill (MarketBill mBill) {
//		if (getCashRegister() >= mBill.bill) {
//			if (mBill.state == marketBillState.unpaid)
//				print("Paying debt to market.");
//			else
//				print("Paid Market Bill");
//			setCashRegister(getCashRegister() - mBill.bill);
//			mBill.market1.salesPersonRole.msgPayment(myRestaurant, mBill.bill);
//			marketBills.remove(mBill);
//			
//			return;
//		}
//		else if (getCashRegister() < mBill.bill) {
//			print("Not enough money to pay market bill...must wait for customers to pay their bills and increase cash flow.");
//			mBill.state = marketBillState.unpaid;
//			return;
//		}
//	}
//
//	public List<MyCheck> getChecks() {
//		return checks;
//	}
//
//	public void setChecks(List<MyCheck> checks) {
//		this.checks = checks;
//	}
//
////	public List<MarketBill> getMarketBills(){
////		return marketBills;
////	}
//
//	public int getCashRegister() {
//		return cashRegister;
//	}
//
//	public void setCashRegister(int cashRegister) {
//		this.cashRegister = cashRegister;
//	}
//}
