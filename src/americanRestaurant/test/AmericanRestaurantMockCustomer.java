//package americanRestaurant.test;
//
//import testing.LoggedEvent;
//import testing.Mock;
//import americanRestaurant.AmericanRestaurantWaiterRole;
//import americanRestaurant.AmericanRestaurantWaiterRole.Menu;
//import americanRestaurant.interfaces.AmericanRestaurantCashier;
//import americanRestaurant.interfaces.AmericanRestaurantCustomer;
//
///**
// * A sample AmericanRestaurantMockCustomer built to unit test a AmericanRestaurantCashierRole.
// *
// * @author Monroe Ekilah
// *
// */
//public class AmericanRestaurantMockCustomer extends Mock implements AmericanRestaurantCustomer {
//
//        /**
//         * Reference to the AmericanRestaurantCashier under test that can be set by the unit test.
//         */
//        public AmericanRestaurantCashier americanRestaurantCashier;
//        int cash;
//        int check;
//        
//        public AmericanRestaurantMockCustomer(String name) {
//                super(name);
//        }
//
//        public void setName (String n) {
//        	this.name = n;
//        }
//        
//        @Override
//        public void msgPleasePayBill() {
//        	
//        int total =  20;
//  
//                log.add(new LoggedEvent("Received HereIsYourTotal from waiter. Total = "+ total));
//
//                if(this.name.toLowerCase().contains("thief")){
//                        //test the non-normative scenario where the customer has no money if their name contains the string "theif"
//                        americanRestaurantCashier.msgNotEnoughMoney(this, total, cash);
//
//                }else if (this.name.toLowerCase().contains("rich")){
//                        //test the non-normative scenario where the customer overpays if their name contains the string "rich"
//                        americanRestaurantCashier.msgHereIsMyPayment(this, (int) Math.ceil(total), total);
//
//                }else{
//                        //test the normative scenario
//                		check = 20; cash = 30;
//                        americanRestaurantCashier.msgHereIsMyPayment(this, check, cash);
//                }
//        }
//
//        @Override
//        public void msgHereIsChange(int total) { 
//              log.add(new LoggedEvent("Received HereIsYourChange from americanRestaurantCashier. Change = "+ total));
//        }
//
//        @Override
//        public void msgYouAreInDebt(int remaining_cost) {
//                log.add(new LoggedEvent("Received YouOweUs from americanRestaurantCashier. Debt = "+ remaining_cost));
//        }
//
//		@Override
//		public void gotHungry() {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void msgRestaurantFull() {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void msgSitAtTable(AmericanRestaurantWaiterRole w, Menu m) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void msgAnimationFinishedGoToSeat() {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void msgReOrder(Menu m) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void msgWhatIsYourChoice() {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void msgHereIsYourFood(String choice) {
//			// TODO Auto-generated method stub
//			
//		}
//
//
//		@Override
//		public void msgAnimationFinishedLeaveRestaurant() {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void msgPayOffDebt() {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public int getSeatNumber() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public void setCheck(int check1) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public int getCash() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public void setCash(int i) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void setDebt(int i) {
//			// TODO Auto-generated method stub
//			
//		}
//}
