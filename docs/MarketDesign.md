![Market Diagram](https://raw.github.com/usc-csci201-fall2013/team20/master/docs/InteractionDiagrams/Market%20Interaction%20Diagram.png?token=3290221__eyJzY29wZSI6IlJhd0Jsb2I6dXNjLWNzY2kyMDEtZmFsbDIwMTMvdGVhbTIwL21hc3Rlci9kb2NzL0ludGVyYWN0aW9uRGlhZ3JhbXMvTWFya2V0IEludGVyYWN0aW9uIERpYWdyYW0ucG5nIiwiZXhwaXJlcyI6MTM4NjEzODc5M30%3D--ed413a5abccf188bca22a5886b1d4e4d69b1ebe5)

# Sales Person
### Data

<pre><code>
String roleName = "Sales Person";
	String name;
	Market market;
List <MarketOrder> orders;
double money;

public class MarketOrder {

	enum orderState {open, processing, itemsFound, itemsDelivered, gaveToCustomer};
	MarketCustomer customer
	Restaurant restaurant;
	String item;
	int itemAmountOrdered;
	int itemAmountFulfilled;
	double orderCost;
	public orderState state = orderState.open;

	public MarketOrder(MarketCustomer customer1, String item, int itemAmountOrdered) {
		this.customer = customer1;
		this.item = item;
		this.itemAmountOrdered = itemAmountOrdered;
	}

	public MarketOrder(Restaurant restaurant, String item, int itemAmountOrdered) {
		this.restaurant = restaurant;
		this.item = item;
		this.itemAmountOrdered = itemAmountOrdered;
	}

}
</code></pre>

### Messages

<pre><code>

msgIWantProducts(MarketCustomer customer, String item, int numWanted) {
	orders.add(new MarketOrder(customer, item, numWanted));
}

msgIWantProducts(Restaurant restaurant, String item, int numWanted) {
	orders.add(new MarketOrder(restaurant, item, numWanted));
	stateChanged();
}

msgOrderFulfilled(MarketOrder o) {

	for (MarketOrder MO : orders) {
		if(MO = o)
			MO.state = orderState.itemsFound;
	}
}


msgOrderDelivered(MarketOrder o) {
	for (MarketOrder MO : orders) {
		if (MO = o)
			MO.state = orderState.itemsDelivered;
		}
	}
}


msgPayment(MarketCustomer customer, double payment) {
	market.money += payment;
	for (MarketOrder o : orders) {
		if (o.customer.equals(customer)) {
			orders.remove(o);
		}
	}
}


msgPayment(Restaurant restaurant, double payment) {
	market.money += payment;
	for (MarketOrder o : orders) {
		if (o.restaurant = restaurant) {
			orders.remove(o);
		}
	}
}

</code></pre>

### Scheduler

<pre><code>
If (∃ MarketOrder ∈  orders | o.state = open)
	findItems(o);

If (∃ MarketOrder ∈  orders | o.state = itemsFound)
	giveCustomerItems(o);
		
If (∃ MarketOrder ∈  orders | o.state = itemsDelivered)
	askForPayment(o);

if (leaveRole){
	((Worker) person).roleFinishedWork();
	leaveRole = false;
	return true;
}
</code></pre>

### Actions

<pre><code>
findItems(Order o) {
	o.state = orderState.pending;
	if (market.inventory.get(o.item).amount  d= 0) {
		o.restaurant.getCook(test).msgCantFulfill(o.item, 0, o.itemAmountOrdered);
		orders.remove(o);
	}
	market.marketRunner.msgHeresAnOrder(o);
}

giveCustomerItems(Order o) {
	o.state = orderState.gaveToCustomer;
	o.orderCost = market.inventory.get(o.item).price  * o.itemAmountFulfilled;
	o.customer.msgHereAreYourThings(o.item, o.itemAmountFulfilled, o.orderCost);
}

askForPayment(MarketOrder o) {
		o.state = orderState.gaveToCustomer;
		print("Asking for payment from the restaurant");
		o.restaurant.getCashier(true).msgPleasePayForItems(o.item, o.itemAmountFulfilled, o.orderCost, this);
		stateChanged();
	}

msgMarketOpen() {
If (∃ MarketOrder ∈  orders) {
		for (MarketOrder o: orders) {
			o.customer.msgComeIn();
		}
	}
}
</code></pre>

# Market Runner
### Data

<pre><code>
List <MarketOrder> orders;
String name;
Market market;
</code></pre>

### Messages

<pre><code>
msgHeresAnOrder(Order o) {
	orders.add(o);
}
</code></pre>

### Scheduler

<pre><code>
If (∃ MarketOrder ∈  orders)
		processOrder(o);
		
If (leaveRole){
	((Worker) person).roleFinishedWork();
	leaveRole = false;
		return true;
}
</code></pre>

### Actions

<pre><code>
processOrder(Order o) {

	if (o.customer != null) {
		decreaseInventoryBy(o.item, o.itemAmountOrdered);
		o.itemAmountFulfilled = o.itemAmountOrdered;
		market.salesPerson.msgOrderFulfilled(o);
		orders.remove(o);
	}
	else { //o.customerType is an instance of business
		decreaseInventoryBy(o.item, o.itemAmountOrdered);
		o.itemAmountFulfilled = o.itemAmountOrdered;
		market.UPSman.msgDeliverOrder(o);
		orders.remove(o);
	}
}

decreaseInventoryBy(String item, int amount) {
	int newAmount = market.inventory.get(item).amount - amount;
	market.inventory.get(item).setInventory(newAmount);
}
</code></pre>

# UPS Man
### Data

<pre><code>
List <MarketOrder> orders;
String roleName = "UPS man";
String name;
Market market;
</code></pre>

### Messages

<pre><code>
msgDeliverOrder(MarketOrder o) {
	orders.add(o);
}
</code></pre>

### Scheduler

<pre><code>
If (∃ MarketOrder ∈  orders)
	deliverOrder(o);
	
If (leaveRole){
	((Worker) person).roleFinishedWork();
	leaveRole = false;
	return true;
}
</code></pre>

### Actions

<pre><code>
deliverOrder(MarketOrder o) {
	o.restaurant.cook.msgOrderFullfillment(o.item, o.itemAmountFulfilled, o.itemAmountOrdered);
	o.salesPerson.msgOrderDelivered(o)
	orders.remove(o);
}
</code></pre>

# Market Customer
### Data

<pre><code>
String roleName = "Market Customer";
String item;
double bill;
String name;

enum MarketCustomerState {atMarket, waitingForOrders, recievedOrders, payed, disputingBill, waitingToOpen}
MarketCustomerState state = MarketCustomerState.atMarket;
</code></pre>

### Messages

<pre><code>
msgComeIn() {
	state = MarketCustomerState.atMarket;
}

msgHereAreYourThings(String item, int itemAmount, double orderCost) {
	state = MarketCustomerState.recievedOrders;
	this.item = item;
	this.itemAmount = itemAmount;
	bill = orderCost;
	}
</code></pre>

### Scheduler

<pre><code>
if (state = MarketCustomerState.atMarket)
	msgSalesPerson();
if (state = MarketCustomerState.recievedOrders)
	payBill();
if (state = MarketCustomerState.payed)
	exitMarket();
</code></pre>

### Actions

<pre><code>
msgSalesPerson() {
	if(!Phonebook.getPhonebook().getMarket().isOpen()) {
		state = MarketCustomerState.waitingToOpen;
		return;
	}

	if (item = "Car") {
		market.salesPersonRole.msgIWantProducts(this, "Car", 1);
		state = MarketCustomerState.waitingForOrders;
		return;
	}
	item = chooseMarketItem();
		market.salesPersonRole.msgIWantProducts(this, item, 3);
		state = MarketCustomerState.waitingForOrders;
}

String chooseMarketItem() {
	Random rand;
	int myRandomChoice;
	String item;
	do {
		myRandomChoice = rand.nextInt(10);
		myRandomChoice %= 7;
	} while (!Phonebook.getPhonebook().getMarket().marketItemsForSale.containsKey(myRandomChoice) || (person.money < Phonebook.getPhonebook().getMarket().marketItemsForSale.get(myRandomChoice).price));
		item = Phonebook.getPhonebook().getMarket().marketItemsForSale.get(myRandomChoice).itemName;
		return item;
}

payBill(){
	if (bill = market.inventory.get(item).price * itemAmount) {
		market.getSalesPerson(test).msgPayment(this, bill);
		person.money -= bill;
		state = MarketCustomerState.payed;
	}
	else {
		//message market that bill was wrong
		//for now leaving market
		exitMarket();
	}
}
</code></pre>
