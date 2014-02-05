//package americanRestaurant;
//
//import javax.swing.JPanel;
//
//
//public class AmericanRestaurantTable extends JPanel {   	
// 	public int tableLength;
//     public int tableWidth;
//     public int xCoordinate;
//     public int yCoordinate;
//     public int seatNum;
//     public boolean isOccupied;
//           
//     public AmericanRestaurantTable ()  {
//     	tableLength = 50;
//     	tableWidth = 50;
//     	xCoordinate = 0;
//     	yCoordinate = 0;
//     	isOccupied = false;
//     	 setVisible(true);
//     }
// 
//     public AmericanRestaurantTable(int l, int w, int x , int y) {
//     	tableLength = l;
//     	tableWidth = w;
//     	xCoordinate = x;
//     	yCoordinate = y;
//     	isOccupied = false;
//     	 setVisible(true);
//     }
//     
//     void setIsOccupied (boolean x){
//     	isOccupied = x;
//     }
//     
//     public boolean getIsOccupied (){
//     	return isOccupied;
//     }
//     AmericanRestaurantCustomerRole occupiedBy;
//		int tableNumber;
//
//		AmericanRestaurantTable(int tableNumber) {
//			this.tableNumber = tableNumber;
//		}
//
//		public void setOccupant(AmericanRestaurantCustomerRole cust) {
//			occupiedBy = cust;
//		}
//
//		public void setUnoccupied() {
//			occupiedBy = null;
//		}
//
//		public AmericanRestaurantCustomerRole getOccupant() {
//			return occupiedBy;
//		}
//
//		public boolean isOccupied() {
//			return occupiedBy != null;
//		}
//
//		public String toString() {
//			return "table " + tableNumber;
//		}
//		
//		public void setSeatNum (int i) {
//			seatNum = i;
//		}
//		
//		public int getSeatNum () {
//			return seatNum;
//		}
//}