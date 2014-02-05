//package seafoodRestaurant;
//
//import application.Phonebook;
//import person.Person;
//import seafoodRestaurant.SeafoodRestaurantWaiterRole.MyCustomer;
//import seafoodRestaurant.SeafoodRestaurantWaiterRole.myCustomerState;
//import seafoodRestaurant.interfaces.SeafoodRestaurantWaiter;
//import chineseRestaurant.ChineseRestaurantWaiterRole;
//import chineseRestaurant.interfaces.ChineseRestaurantWaiter;
//
//public class SeafoodRestaurantAltWaiterRole extends SeafoodRestaurantWaiterRole implements SeafoodRestaurantWaiter 
//{
//	protected String RoleName = "Alternative AmericanRestaurantWaiter";
//	
//	public SeafoodRestaurantAltWaiterRole(Person person, String name, String title) 
//	{
//		super(person, name, title);
//	}
//
//	public void SendOrder(MyCustomer mc)
//	{
//		DoGoToCook(); //REMEMBER TO PASS CHEF AS A PARAMETER
//		try 
//		{
//			atKitchen.acquire();
//		} catch (InterruptedException e) 
//		{
//			e.printStackTrace();
//		}
//		waiterGui.GoHomePosition();
//		//cook.pleaseCook(mc.choice, mc.table, this);
//		Phonebook.getPhonebook().getSeafoodRestaurant().getRevolvingStand().newOrder(mc.choice, mc.table, this);
//		print("Message 7 - Sent Order To Revolving Stand");
//		mc.state = myCustomerState.OrderSent;
//	}
//
////Change
//	///ADD TO THIS
//}
