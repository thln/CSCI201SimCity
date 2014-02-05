package italianRestaurant;

import italianRestaurant.interfaces.*;

/*MyCustomer Class stores details of each specific customer
 * each waiter has to deal with in the program.
 */
public class ItalianMyCustomer {
	ItalianCustomer c;
	String choice;
	int table;
	Integer home;
	Double billtotal = 0.0;
	//stores the states in which the waiter's MyCustomer can be in
	public enum MyCustState {readytoseat, seating, readytoorder, ordered, pending, orderingAgain, waiting, readyforCheck, paying, done, finished};
	public MyCustState s;
	
	ItalianMyCustomer(ItalianCustomer cust, int tablenum, Integer startPos){
		c = cust;
		table = tablenum;
		home = startPos;
		s = MyCustState.readytoseat; //initializes customer as waiting, before being seated
	}
	
	public String toString() {
		return "" + c;
	}
}
