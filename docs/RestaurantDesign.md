![Revolving Stand Diagram NonNorm](https://raw.github.com/usc-csci201-fall2013/team20/014aeaa5a00d787532f65047f6429105c3027724/docs/InteractionDiagrams/RevolvingStand.png?token=3290221__eyJzY29wZSI6IlJhd0Jsb2I6dXNjLWNzY2kyMDEtZmFsbDIwMTMvdGVhbTIwLzAxNGFlYWE1YTAwZDc4NzUzMmY2NTA0N2Y2NDI5MTA1YzMwMjc3MjQvZG9jcy9JbnRlcmFjdGlvbkRpYWdyYW1zL1Jldm9sdmluZ1N0YW5kLnBuZyIsImV4cGlyZXMiOjEzODYxMDI4MjR9--4c0b4137fab3fb854e1d6f224efa2481911872e1)

# Customer 

### Data

<pre><code>
Map<String, Food> foodMap {
        foodMap.put("Chicken", Food("Chicken"));
        foodMap.put("Steak", Food("Steak"));
        foodMap.put("Pizza", Food("Pizza"));
        foodMap.put("Salad", Food("Salad"));
}
 
timer cookTimer;
        	
List<Order> myOrders;
List<myMarket> markets;
List<Stock> stockFulfillment;
 
class Order {
        	WaiterRole waiter;
        	int table
        	String choice;
        	enum orderStatus {Open, Cooking, Done};
}
 
class Food {
    String choice;
        	int cookTime;
        	int quantity = 2;
        	int capacity = 5;
        	int threshold = 2;
        	int amountOrdered = 0;
                    	
        	if (choice == "Chicken")
                    	cookTime = 5000;
        	else if (choice == "Steak")
                    	cookTime = 5000;
        	else if (choice == "Pizza")
                    	cookTime = 5000;
        	else if (choice == "Salad")
                    	cookTime = 5000;
}
        	
class Stock {
        	String choice;
        	int quantity;
        	int orderedAmount;
        	MarketRole market;
}
        	
class myMarket {
        	MarketRole market;
        	Map<String, Boolean> availableChoices {
                    	availableChoices.put("Chicken", true); 
                    	availableChoices.put("Steak", true); 
                    	availableChoices.put("Pizza", true); 
                    	availableChoices.put("Salad", true); 
        	}                  	
}
</code></pre>

### Messages

<pre><code>
msgHeresAnOrder(Order order) {
        	myOrders.add(order);
}
 
msgCantFulfill(String choice, int amount, int orderedAmount, MarketRole market) {
        	stockFulfillment.add(Stock(choice, amount, orderedAmount, market));
}
 
msgOrderFulfillment(String choice, int amount, int orderedAmount, MarketRole market) {
        	stockFulfillment.add(Stock(choice, amount, orderedAmount, market));
}
</code></pre>

### Scheduler

<pre><code>
if there exists an Order o in myOrders &&
if o.state == Open
        	then
                    	cookOrder(o);
 
if there exists an Order o in myOrders &&
if o.state == Done
        	then
                    	doneCooking(o);
 
if there exists a Stock s in stockFulfillment
        	then
                    	updateStock();
 
if inventoryChecker == 500
        	then
                    	checkInventory();
</code></pre>

### Actions

<pre><code>
cookOrder(final Order o) {
        	
        	if(!isInStock(o.choice)) {
                    	checkInventory(o.choice);
                    	waiter.msgOrderNotAvailable(o.choice, o.customer);
                    	myOrders.remove(o);
                    	//Do not complete the rest of the action
        	}
 
        	foodMap.get(o.choice).quantity--;
        	checkInventory(o.choice);
        	
        	o.state = Cooking;
        	cookTimer.setTimeFromCookTimeMap(); 	
        	cookTimer.start();
                    	
        	//When timer finishes
        	msgOrderDone(o);
}
 
doneCooking(Order o) {
        	o.waiter.msgOrderIsReady(o);
        	myOrders.remove(o);
}
        	
checkInventory(String choice) {
        	if (foodMap.get(choice).stockOnHand < foodMap.get(choice).threshold) {
                    	int orderAmount;
                    	orderAmount = foodMap.get(choice).capacity - stockOnHand;
                    	foodMap.get(choice).amountOrdered = orderAmount;
                                            	
                    	myMarket myMark = markets.marketThatHasChoiceAvailable();
                    	myMark.market.msgOutofItems(choice, orderAmount);
        	}
}
        	
updateStock() {
        	//No stock is fulfilled
        	if (stockFulfillment.first().quantity == 0) {
                     	myMarket MM = markets.find(stockFulfillment.first().market);
                    	foodMap.get(stockFulfillment.first().choice).amountOrdered -=              stockFulfillment.first().orderedAmount;
                                	
                    	//Setting order availability for the choice at market to false
                    	MM.availableChoices.put(stockFulfillment.first().choice, false);
                    	checkInventory(stockFulfillment.first().choice);
                    	stockFulfillment.removefirst();
        	}
        	else {
                    	//Updating stock
                    	foodMap.get(stockFulfillment.first().choice).quantity += stockFulfillment.first().quantity;                       	
                    	foodMap.get(stockFulfillment.first().choice).amountOrdered -= stockFulfillment.first().orderedAmount;
 
                    	//If only part of the order was fulfilled
                    	if (stockFulfillment.first().quantity < stockFulfillment.first().orderedAmount) {    	
                                	myMarket MM = markets.find(stockFulfillment.first().market);
                                	//Setting order availability for the choice at market to false
                                	MM.availableChoices.put(stockFulfillment.first().choice, false);
                    	}
 
                    	stockFulfillment.remove(0);
        	}
}
</code></pre>

# Host
### Data

<pre><code>
List<myCustomer> waitingCustomers;
List<myWaiter> waiters;
List<Table> tables;
 
class myWaiter {
        	WaiterRole waiter;
        	int totalCustomers;
        	bool askedToGoOnBreak;
        	bool onBreak;
}
 
class Table {
        	CustomerRole occupiedBy;
        	int tableNumber;      	
}
 
class myCustomer {
        	CustomerRole customer;
        	boolean informed = false;
}
</code></pre>

### Messages

<pre><code>
msgIWantFood(myCustomer cust) {
        	newCustomers.add(cust);
}
 
msgStaying(myCustomer cust) {
        	waitingCustomers.add(cust);
}
 
msgLeavingTable(CustomerRole cust) {
        	Table t = tables.find(cust);
        	t.setUnoccupied();
}
 
msgMayIGoOnBreak(WaiterRole waiter) {
        	myWaiter MW = waiters.find(waiter);
        	MW.askedToGoOnBreak = true;
}
 
msgOffBreak(WaiterRole waiter) {
        	myWaiter MW = waiter.find(waiter);
        	MW.onBreak = false;
}
</code></pre>

### Scheduler

<pre><code>
if there exists a Customer customer in newCustomers
        	then
                    	greetCustomer();
 
if there exists a Table t in tables && t.isUnoccupied  &&
if there exists a myWaiter MW in waiters && MW.waiter.onBreak is false &&
if there exists a Customer customer in waitingCustomers
        	then
                    	assignCustomer(customer, t, waiters.findWaiterWithLeastCustomers());
 
if there exists a myWaiter MW in waiters &&
if MW.askedToGoOnBreak is true
        	then
                    	replyToBreakRequest(MW);
</code></pre>

### Actions

<pre><code>
greetCustomer() {
        	if (tables.allTablesFull()) {
                    	informCustomerRestaurantFull(newCustomers.get(0));
        	}
        	else {
                    	waitingCustomers.add(newCustomers.get(0));
                    	newCustomers.remove(0);
        	}
}
 
informCustomerRestaurantFull(myCustomer MC) {
                    	MC.informed = true;
                    	MC.customer.msgTablesAreFull();
}
 
assignCustomer(CustomerRole customer, Table table, WaiterRole waiter) {
        	waiter.msgPleaseSeatCustomer(table.tableNumber, customer);
        	myWaiter MW = waiters.find(waiter)
        	MW.totalCustomers++;
        	table.setOccupant(customer);
        	waitingCustomers.remove(customer);
}
 
replyToBreakRequest(myWaiter MW) {
        	if (waiters.size() == 1) {
                    	MW.askedToGoOnBreak = false;
                    	MW.waiter.msgPermissionToBreak(false);
                    	//Do not execute the rest of the action
        	}
 
        	if (workingWaiters > 1) {
                    	MW.askedToGoOnBreak = false;
                    	MW.waiter.msgPermissionToBreak(true);
                    	MW.onBreak = true;
        	}
        	else {
                    	MW.askedToGoOnBreak = false;
                    	MW.waiter.msgPermissionToBreak(false);
        	}      	
}
</code></pre>

# Waiter
### Data

<pre><code>
List<myCustomer> myCustomers;
List<Order> readyOrders;
 
Menu menu;
 
enum breakStatus{working, askedToGoOnBreak, waitingForReply, receivedReply, onBreak, goOffBreak};
breakStatus state = breakStatus.working;
boolean PermissionToBreak = false;
timer breakTimer;
 
class myCustomer {
        	CustomerRole customer;
        	int tableNumber;
        	String choice;
 
        	enum customerState {Waiting, Seated, ReadyToOrder, RequestedToOrder, Ordered, Reorder, WaitingForFood, FoodReady, GotFood, WantCheck, GaveCheck, Finished};
        	customerState state = customerState.Waiting;
}
 
class Order {
        	WaiterRole waiter;
        	int table
        	String choice;
};
 
class Menu {
        	Map<Integer, String> menuMap {
                    	0 = "Chicken";
                    	1 = "Steak";
                    	2 = "Pizza";
                    	3 = "Salad";
        	}
};
</code></pre>

### Messages

<pre><code>
msgPleaseSeatCustomer(int tableNumber, CustomerRole customer) {
        	myCustomers.add(myCustomer(customer, tableNumber));
}
 
msgReadyToOrder(CustomerRole customer) {
        	myCustomer MC = myCustomers.find(customer);
        	MC.state = ReadyToOrder;
}
 
msgHeresMyOrder(CustomerRole customer, String choice) {
        	myCustomer MC = myCustomers.find(customer);
        	MC.setChoice(choice);
        	MC.state = Ordered;
}
 
msgOrderIsReady(Order order) {
        	readyOrders.add(order);
}
 
msgOrderIsNotAvailable(Order order) {
        	myCustomer MC = myCustomers.find(o.customer);
        	MC.state = Reorder;
}
 
msgIWantMyCheck(CustomerRole customer) {
        	myCustomer MC = myCustomers.find(customer);
        	MC.state = WantCheck;
}
 
msgLeavingTable(CustomerRole customer) {       	
        	myCustomer MC = myCustomers.find(customer);
        	MC.state = Finished;
}
 
msgWantToGoOnBreak() {
        	askToGoOnBreak = true;
}
 
msgPermissionToBreak(boolean reply){
        	PermissionToBreak = reply;
}
 
msgHereIsCheck(int tableNumber, double checkAmount) {
        	myCustomer MC = myCustomers.find(tableNumber);
        	MC.CheckAmount = checkAmount;
}
</code></pre>

### Scheduler

<pre><code>
if state is askedToGoOnBreak
        	then
                    	askToGoOnBreak();
 
if PermissionToBreak is false && state is receivedReply
        	then
                    	breakDenied();
 
if there does not exist a myCustomer in myCustomers && PermissionToBreak is true && state is receivedReply
        	then
                    	goOnBreak();
 
if state is goOffBreak
        	then
                    	goOffBreak();
 
if there exists a myCustomer myCust in myCustomers &&
if myCust state is waiting
        	then
                    	seatCustomer(myCust);
 
if there exists a myCustomer myCust in myCustomers &&
if myCust state is readyToOrder
        	then
                    	takeOrder(myCust);
 
if there exists a myCustomer myCust in myCustomers &&
if myCust state is Ordered
        	then
                    	placeOrder(myCust);
 
if there exists a myCustomer myCust in myCustomers &&
myCust state is Reorder
        	then
                    	retakeOrder(myCust);
 
if there exists and Order o in readyOrders
        	then
                    	deliverOrder(o);
 
if there exists a myCustomer myCust in myCustomers &&
myCust state is WantCheck
        	then
                    	giveCheck(myCust);
 
if there exists a myCustomer myCust in myCustomers &&
if myCust state is Finished
        	then
                    	clearTable(myCust);
</code></pre>

### Actions

<pre><code>
seatCustomer(myCustomer MC) {
        	MC.customer.msgPleaseFollowMe(MC.tableNumber, menu, this);
        	MC.setSeated();
}
 
takeOrder(myCustomer MC) {
        	MC.state = RequestedToOrder;
        	MC.customer.msgWhatWouldYouLike();
}
 
placeOrder(myCustomer MC) {
        	MC.state = WaitingForFood;
        	cook.msgHeresAnOrder(Order(MC.customer, MC.tableNumber, MC.choice, this));
}
 
retakeOrder(myCustomer MC) {
        	MC.state = Seated;
        	MC.customer.msgPleaseReorder(menu.remove(MC.choice));
}
 
deliverOrder(o) {
        	Order order = readyOrders.find(o);
        	order.customer.msgHeresYourOrder(order.choice);
 
        	myCustomer MC = myCustomers.find(order.customer);
        	MC.state = GotFood;
        	readyOrders.remove(0);
}
 
giveCheck(myCustomer MC) {
                    	MC.customer.msgHeresYourCheck(MC.CheckAmount);
                    	MC.state = GaveCheck;
}
 
clearTable(myCustomer MC) {
        	host.msgLeavingTable(MC.customer);
        	myCustomers.remove(MC);
}
 
askToGoOnBreak() {
        	host.msgMayIGoOnBreak(this);
        	state = waitingForReply;
}
        	
goOnBreak() {
        	state = onBreak;
        	PermissionToBreak = false;
}
 
goOffBreak() {
        	host.msgOffBreak(this);
        	state = working;
}
        	
breakDenied() {
        	state = working;
}
</code></pre>

# Cook
### Data

<pre><code>
Map<String, Food> foodMap {
        	foodMap.put("Chicken", Food("Chicken"));
        	foodMap.put("Steak", Food("Steak"));
        	foodMap.put("Pizza", Food("Pizza"));
        	foodMap.put("Salad", Food("Salad"));
}
 
timer cookTimer;
        	
List<Order> myOrders;
List<myMarket> markets;
List<Stock> stockFulfillment;
 
class Order {
        	WaiterRole waiter;
        	int table
        	String choice;
 
        	enum orderStatus {Open, Cooking, Done};
}
 
class Food {
        	String choice;
        	int cookTime;
        	int quantity = 2;
        	int capacity = 5;
        	int threshold = 2;
        	int amountOrdered = 0;
                    	
        	if (choice == "Chicken")
                    	cookTime = 5000;
        	else if (choice == "Steak")
                    	cookTime = 5000;
        	else if (choice == "Pizza")
                    	cookTime = 5000;
        	else if (choice == "Salad")
                    	cookTime = 5000;
}
        	
class Stock {
        	String choice;
        	int quantity;
        	int orderedAmount;
        	MarketRole market;
}
        	
class myMarket {
        	MarketRole market;
        	Map<String, Boolean> availableChoices {
                    	availableChoices.put("Chicken", true);
                    	availableChoices.put("Steak", true);
                    	availableChoices.put("Pizza", true);
                    	availableChoices.put("Salad", true);
        	}                  	
}
</code></pre>

### Messages

<pre><code>
msgHeresAnOrder(Order order) {
        	myOrders.add(order);
}
 
msgCantFulfill(String choice, int amount, int orderedAmount, MarketRole market) {
        	stockFulfillment.add(Stock(choice, amount, orderedAmount, market));
}
 
msgOrderFulfillment(String choice, int amount, int orderedAmount, MarketRole market) {
        	stockFulfillment.add(Stock(choice, amount, orderedAmount, market));
}
</code></pre>

### Scheduler

<pre><code>
if there exists an Order o in myOrders &&
if o.state == Open
        	then
                    	cookOrder(o);
 
if there exists an Order o in myOrders &&
if o.state == Done
        	then
                    	doneCooking(o);
 
if there exists a Stock s in stockFulfillment
        	then
                    	updateStock();
 
if inventoryChecker == 500
        	then
                    	checkInventory();
</code></pre>

### Actions

<pre><code>
cookOrder(final Order o) {
        	
        	if(!isInStock(o.choice)) {
                    	checkInventory(o.choice);
                    	waiter.msgOrderNotAvailable(o.choice, o.customer);
                    	myOrders.remove(o);
                    	//Do not complete the rest of the action
        	}
 
        	foodMap.get(o.choice).quantity--;
        	checkInventory(o.choice);
        	
        	o.state = Cooking;
        	cookTimer.setTimeFromCookTimeMap(); 	
        	cookTimer.start();
                    	
        	//When timer finishes
        	msgOrderDone(o);
}
 
doneCooking(Order o) {
        	o.waiter.msgOrderIsReady(o);
        	myOrders.remove(o);
}
        	
checkInventory(String choice) {
        	if (foodMap.get(choice).stockOnHand < foodMap.get(choice).threshold) {
                    	int orderAmount;
                    	orderAmount = foodMap.get(choice).capacity - stockOnHand;
                    	foodMap.get(choice).amountOrdered = orderAmount;
                                            	
                    	myMarket myMark = markets.marketThatHasChoiceAvailable();
                    	myMark.market.msgOutofItems(choice, orderAmount);
        	}
}
        	
updateStock() {
        	//No stock is fulfilled
        	if (stockFulfillment.first().quantity == 0) {
                    	myMarket MM = markets.find(stockFulfillment.first().market);
                    	foodMap.get(stockFulfillment.first().choice).amountOrdered -= stockFulfillment.first().orderedAmount;
                                	
                    	//Setting order availability for the choice at market to false
                    	MM.availableChoices.put(stockFulfillment.first().choice, false);
                    	checkInventory(stockFulfillment.first().choice);
                    	stockFulfillment.removefirst();
        	}
        	else {
                    	//Updating stock
                    	foodMap.get(stockFulfillment.first().choice).quantity += stockFulfillment.first().quantity;                       	
                    	foodMap.get(stockFulfillment.first().choice).amountOrdered -= stockFulfillment.first().orderedAmount;
 
                    	//If only part of the order was fulfilled
                    	if (stockFulfillment.first().quantity < stockFulfillment.first().orderedAmount) {    	
                                	myMarket MM = markets.find(stockFulfillment.first().market);
                                	//Setting order availability for the choice at market to false
                                	MM.availableChoices.put(stockFulfillment.first().choice, false);
                    	}
 
                    	stockFulfillment.remove(0);
        	}
}
</code></pre> 

# Cashier
### Data

<pre><code>
List<Check> Checks
List<Payment> Payments
 
Map<String, Double> foodPrices {
        	foodPrices.put("Chicken", 10.99);
        	foodPrices.put("Steak", 15.99);
        	foodPrices.put("Pizza", 8.99);
        	foodPrices.put("Salad", 5.99);
}
 
class Check {
        	String choice;
        	int tableNumber;
        	WaiterRole waiter;
}
 
class Payment {
        	String choice;
        	double payment;
        	CustomerRole customer;
}
</code></pre>

### Messages

<pre><code>
msgComputeBill(String choice, int tableNumber, WaiterRole waiter) {
        	Checks.add(Check(choice, tableNumber, waiter));
}
 
msgPayment(String choice, double amount, CustomerRole customer) {
        	Payments.add(Payment(choice, amount, customer));
}
</code></pre>

### Scheduler

<pre><code>
if there exists a Check in Checks
	then
		ComputeBill();

if there exists a Payment in Payments
	then
		GiveChange();
</code></pre>

### Actions

<pre><code>
ComputeBill() {
    double checkAmount = foodPrices.get(Checks.first().choice);
    Checks.first().waiter.msgHereIsCheck(Checks.first().tableNumber, checkAmount);
    Checks.removeFirst();
}
 
GiveChange() {
    if (Payments.first().payment < foodPrices.get(Payments.first().choice)) {
        Payments.first().customer.msgGoToJail();
        Payments.removeFirst();
        //Do not execute the rest of method
    }
                    	
    double change;
    change = Payments.first().payment - foodPrices.get(Payments.first().choice);
    Payments.first().customer.msgHeresYourChange(change);
    Payments.removeFirst();
}
</code></pre>

#Revolving Stand

### Data

List<Order> AllOrders = Collections.synchronizedList(new ArrayList<Order>());

### Actions

newOrder(Order o) {
	AllOrders.add(o);
}

takeOrder() {
	Order o = AllOrders.get(0);
	AllOrders.remove(0);
	return o;
}

#Alt Waiter

### Data

String RoleName = "Alternative Waiter";
Restaurant restaurant;

### Messages
/////
### Scheduler
/////
### Actions
placeOrder(myCustomer MC) {
	isInLobby = false;
	for (myCustomer myCust : myCustomers) {
		if (myCust.customer == MC.customer) {
			myCust.setWaitingForFood();
		}
	}
	waiterGui.DoGoToKitchen();
	try {
		atDestination.acquire();
	} 
	catch (InterruptedException e) {
		e.printStackTrace();
	}
	waiterGui.DoLeaveCustomer();

	//cook.msgHeresAnOrder(MC.tableNumber, MC.choice, this);
	restaurant.revolvingStand.newOrder(new Order(MC.tableNumber, MC.choice, this));
	}