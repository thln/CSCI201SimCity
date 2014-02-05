//package americanRestaurant.gui;
//
//
//import java.awt.Color;
//import java.awt.Graphics2D;
//import java.util.ArrayList;
//
//import javax.swing.ImageIcon;
//
//import americanRestaurant.AmericanRestaurantTable;
//import americanRestaurant.AmericanRestaurantWaiterRole;
//import americanRestaurant.interfaces.AmericanRestaurantCustomer;
//import application.gui.animation.RestaurantPanel;
//import application.gui.animation.agentGui.Gui;
//import application.gui.animation.agentGui.RestaurantGui;
//
//public class AmericanWaiterGui implements Gui {
//
//	private AmericanRestaurantWaiterRole agent = null;
//	public int seatNumber = 0;
//	public boolean inProcess = false;
//	public boolean isFood = false;
//	public ImageIcon food = null;
//	private RestaurantGui gui;
//	enum CustomerState {waiterOrdering, foodCooking, waiterGettingFood};
//
//
//	class customerInfo {
//		AmericanRestaurantCustomer cust;
//		CustomerState state1;
//
//		customerInfo (AmericanRestaurantCustomer c1){
//			this.cust = c1;
//		}
//	}
//
//	private ArrayList<customerInfo> customers;
//
//	public int xSize = 20;
//	public int ySize = 20;
//	private int xPos = 10, yPos = 300;//default waiter position
//	private int xDestination, yDestination;//default start position
//	public final int cookX = 380;
//	public final int cookY= 300;
//	public final int entranceX = 20;
//	public final int entranceY = 20;
//	public int homeX = 15;
//	public final int homeY = 300;
//	RestaurantPanel panel1;
//	private final int breakZoneX = 10, breakZoneY = 300;
//
//	public AmericanWaiterGui (AmericanRestaurantWaiterRole waiter, RestaurantPanel p1) {
//		xDestination = homeX;
//		yDestination = homeY;
//		this.agent = waiter;
//		panel1 = p1;
//		customers = new ArrayList<>();
//	}
//
//	public void updatePosition() { 	
//
//		if (xPos < xDestination)
//			xPos++; 	
//		else if (xPos > xDestination)
//			xPos--;
//
//		if (yPos < yDestination)
//			yPos++;
//		else if (yPos > yDestination)
//			yPos--;
//
//		if (xPos == xDestination && yPos == yDestination) {
//			if ((xDestination ==  panel1.tables[seatNumber].xCoordinate) && 
//					(yDestination == panel1.tables[seatNumber].yCoordinate)){ 
//				agent.msgAtTable();
//				return;
//			}
//			if ((xDestination == entranceX && yDestination == entranceY && inProcess)) {
//				agent.msgAtDoor();
//				return;
//			}
//
//			if (xDestination == cookX && yDestination == cookY) {
//				for (customerInfo info1: customers) {
//					if (info1.state1 == CustomerState.waiterOrdering) {
//						info1.state1 = CustomerState.foodCooking;
//						agent.msgAtCook(info1.cust);
//						return;
//					}
//
//					if (info1.state1 == CustomerState.waiterGettingFood) {	
//						agent.msgFoodHere(info1.cust);
//						customers.remove(info1);
//						return;
//					}
//				}
//			}
//		}
//	}  	
//
//	public void draw(Graphics2D g) {
//		g.setColor(Color.MAGENTA);
//		g.fillRect(xPos, yPos, xSize, ySize);
//	}
//
//	public boolean isPresent() {
//		return true;
//	}
//
//	public int getxDestination (){
//		return xDestination;
//	}
//
//	public int getyDestination (){
//		return yDestination;
//	}
//	public void DoBringToTable(AmericanRestaurantTable t1) {
//		seatNumber = t1.getSeatNum();
//		xDestination = panel1.tables[seatNumber].xCoordinate; 
//		yDestination = panel1.tables[seatNumber].yCoordinate;
//	}
//
//	public void BringFoodToTable(AmericanRestaurantCustomer c1) {
//		seatNumber = c1.getSeatNumber();
//		xDestination = panel1.tables[seatNumber].xCoordinate; 
//		yDestination = panel1.tables[seatNumber].yCoordinate;
//
//	}
//
//	public void WalkToCook(AmericanRestaurantCustomer c1) {
//		customerInfo info1 = new customerInfo(c1);
//		info1.state1 = CustomerState.waiterOrdering;
//		customers.add(info1);
//		xDestination = cookX;
//		yDestination = cookY;
//		updatePosition();
//	}
//
//	public void WalkToCookForFood(AmericanRestaurantCustomer c1) {
//		for (customerInfo info1: customers) {
//			if (info1.cust.equals(c1)){
//				info1.state1 = CustomerState.waiterGettingFood;
//				break;
//			}
//		}
//		xDestination = cookX;
//		yDestination = cookY;
//	}
//
//	public void DoLeaveCustomer(){
//		xDestination = homeX;
//		yDestination = homeY;
//	}
//
//	public void DoGoToEntrance() {
//		xDestination = entranceX;
//		yDestination = entranceY;
//	}
//	
//	public void GoBreak() {
//		xDestination = breakZoneX;
//		yDestination = breakZoneY;
//	}
//
//	public void GoBack() {
//		xDestination = homeX;
//		yDestination = homeY;
//	}
//
//	public int getXPos() {
//		return xPos;
//	}
//
//	public int getYPos() {
//		return yPos;
//	}
//
//	public void setHomePosition(int waiters) {
//		homeX += waiters*40;
//		xDestination = homeX;
//	}
//
//	@Override
//	public void DoExit() {
//		// TODO Auto-generated method stub
//		
//	}
//}
