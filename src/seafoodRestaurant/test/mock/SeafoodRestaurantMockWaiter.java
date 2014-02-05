//package seafoodRestaurant.test.mock;
//
////import restaurant.Check;
////import restaurant.interfaces.Cashier;
////import restaurant.interfaces.Customer;
////import restaurant.interfaces.Waiter;
//import seafoodRestaurant.SeafoodRestaurantCashierRole;
//import seafoodRestaurant.SeafoodRestaurantCheck;
//import seafoodRestaurant.SeafoodRestaurantCustomerRole;
//import seafoodRestaurant.interfaces.SeafoodRestaurantCustomer;
//import seafoodRestaurant.interfaces.SeafoodRestaurantWaiter;
//import testing.LoggedEvent;
//import testing.Mock;
//
//public class SeafoodRestaurantMockWaiter extends Mock implements SeafoodRestaurantWaiter 
//{
//	public SeafoodRestaurantCashierRole cashier;
//
//    public SeafoodRestaurantMockWaiter(String name) 
//    {
//            super(name);
//
//    }
//	
//	public void CanIGetMyCheck(SeafoodRestaurantCustomer cust)
//	{
//		
//	}
//	
//	public void ThisIsTheCheck(SeafoodRestaurantCustomer cust, SeafoodRestaurantCheck ch)
//	{
//		log.add(new LoggedEvent("Received Check from americanRestaurantCashier. AmericanRestaurantCustomer = "+ cust + " and check cost is : " + ch.cost));
//	}
//
//	@Override
//	public void OrderIsReady(String food, int table) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void OutOfFood(int table, String food) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//}
