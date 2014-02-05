//package seafoodRestaurant.test.mock;
//
////import restaurant.Check;
////import restaurant.interfaces.Cashier;
////import restaurant.interfaces.Customer;
//import seafoodRestaurant.SeafoodRestaurantCheck;
//import seafoodRestaurant.interfaces.SeafoodRestaurantCashier;
//import seafoodRestaurant.interfaces.SeafoodRestaurantCustomer;
//import testing.LoggedEvent;
//import testing.Mock;
//
//
//public class SeafoodRestaurantMockCustomer extends Mock implements SeafoodRestaurantCustomer 
//{
//	
//	public SeafoodRestaurantCashier cashier;
//
//    public SeafoodRestaurantMockCustomer(String name) 
//    {
//            super(name);
//
//    }
//
//	public void HereIsYourCheck(SeafoodRestaurantCheck ch)
//	{
//		log.add(new LoggedEvent("Received HereIsYourTotal from americanRestaurantCashier. Total = "+ ch.cost));
//        if(this.getName().toLowerCase().contains("thief"))
//        {
//            //test the non-normative scenario where the customer has no money if their name contains the string "theif"
//            //americanRestaurantCashier.IAmShort(this, 0);
//        	cashier.HereIsPayment(ch, 0);
//
//        }
//        else if (this.getName().toLowerCase().contains("rich"))
//        {
//            //test the non-normative scenario where the customer overpays if their name contains the string "rich"
//            cashier.HereIsPayment(ch, 20);
//
//    	}
//        else
//        {
//            //test the normative scenario
//            cashier.HereIsPayment(ch, ch.cost);
//        }
//		
//	}
//	public void HereIsYourChange(double c, double d)
//	{
//		log.add(new LoggedEvent("Received HereIsYourChange from americanRestaurantCashier."));
//		if(d>0)
//		{
//	        log.add(new LoggedEvent("Received YouOweUs from americanRestaurantCashier. Debt = "+ d));
//		}
//		else
//		{
//			log.add(new LoggedEvent("Received HereIsYourChange from americanRestaurantCashier. Change = "+ c));
//		}
//	}
//	
//    /*
//    @Override
//    public void HereIsYourTotal(double total) {
//            log.add(new LoggedEvent("Received HereIsYourTotal from americanRestaurantCashier. Total = "+ total));
//
//            if(this.name.toLowerCase().contains("thief")){
//                    //test the non-normative scenario where the customer has no money if their name contains the string "theif"
//                    americanRestaurantCashier.IAmShort(this, 0);
//
//            }else if (this.name.toLowerCase().contains("rich")){
//                    //test the non-normative scenario where the customer overpays if their name contains the string "rich"
//                    americanRestaurantCashier.HereIsMyPayment(this, Math.ceil(total));
//
//            }else{
//                    //test the normative scenario
//                    americanRestaurantCashier.HereIsMyPayment(this, total);
//            }
//    }
//
//    @Override
//    public void HereIsYourChange(double total) {
//            log.add(new LoggedEvent("Received HereIsYourChange from americanRestaurantCashier. Change = "+ total));
//    }
//
//    @Override
//    public void YouOweUs(double remaining_cost) {
//            log.add(new LoggedEvent("Received YouOweUs from americanRestaurantCashier. Debt = "+ remaining_cost));
//    }
//    */
//}
