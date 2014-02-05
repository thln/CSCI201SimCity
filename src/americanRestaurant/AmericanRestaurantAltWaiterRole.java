//package americanRestaurant;
//
//import person.Person;
//import americanRestaurant.AmericanRestaurantCookRole.Order;
//import americanRestaurant.interfaces.AmericanRestaurantWaiter;
//
//public class AmericanRestaurantAltWaiterRole extends AmericanRestaurantWaiterRole implements AmericanRestaurantWaiter {
//	//private RevolvingStand theRevolvingStand;
//	protected String RoleName = "Alternative AmericanRestaurantWaiter";
//
//	public AmericanRestaurantAltWaiterRole(Person p1, String pName, String rName, AmericanRestaurant restaurant) {
//		super(p1, pName, rName, restaurant);
//	}
//
//	@Override
//	public void OrderFromCook(MyCustomer c1){
//		print("Placing order on Revolving Stand.");
//		myRestaurant.getRevolvingStand().newOrder(new Order(c1.choice, this, c1.tab));
////		print("stand size = " + myRestaurant.getRevolvingStand().getSize());
//		waiterGui.DoLeaveCustomer();
//		c1.state = customerState.WaitingForFood;
//	}
//
//}
