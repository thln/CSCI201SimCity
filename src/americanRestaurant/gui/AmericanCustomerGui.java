//package americanRestaurant.gui;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//
//import americanRestaurant.AmericanRestaurantCustomerRole;
//import application.gui.animation.RestaurantPanel;
//import application.gui.animation.agentGui.Gui;
//import application.gui.animation.agentGui.RestaurantGui;
//
//public class AmericanCustomerGui implements Gui{
//
//	private AmericanRestaurantCustomerRole agent = null;
//	private boolean isPresent = false;
//	private boolean isHungry = false;
//
//	//private CookAgent host;
//	RestaurantGui gui;
//
//	private int xPos = 0, yPos = 0;
//	private int xDestination, yDestination;
//	private enum Command {noCommand, GoToSeat, LeaveRestaurant};
//	private Command command=Command.noCommand;
//
//	public static final int xTable = 200;
//	public static final int yTable = 250;
//	public static final int widthTable = 20;
//	public static final int lengthTable = 20;
//	public static final int xHome = -40;
//	public static final int yHome = -40;
//	public static int xEntrance = 0;
//	public static int yEntrance = 0;
//	
//	RestaurantPanel panel1;
//	
//
//	public AmericanCustomerGui(AmericanRestaurantCustomerRole c, RestaurantPanel p1){ //CookAgent m) {
//		agent = c;
//		xDestination = xEntrance;
//		yDestination = yEntrance;
//		panel1 = p1;
//	}
//
//	public void updatePosition() {
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
//			if (command==Command.GoToSeat) agent.msgAnimationFinishedGoToSeat();
//			else if (command==Command.LeaveRestaurant) {
//				agent.msgAnimationFinishedLeaveRestaurant();
//				isHungry = false;
//			}
//			command=Command.noCommand;
//		}
//	}
//
//	public void draw(Graphics2D g) {
//		g.setColor(Color.GREEN);
//		g.fillRect(xPos, yPos, lengthTable, widthTable);
//	}
//
//	public void drawFood(Graphics g) {
//		
//	}
//	
//	public boolean isPresent() {
//		return isPresent;
//	}
//	public void setHungry() {
//		isHungry = true;
//		agent.gotHungry();
//		setPresent(true);
//	}
//	public boolean isHungry() {
//		return isHungry;
//	}
//
//	public void setPresent(boolean p) {
//		isPresent = p;
//	}
//
//	public void DoGoToSeat(int seatNumber) {//later you will map seatnumber to table coordinates.
//		xDestination = panel1.tables[seatNumber].xCoordinate;
//		yDestination = panel1.tables[seatNumber].yCoordinate;
//		command = Command.GoToSeat;
//	}
//	
//	public void DoGoToEntrance() {
//		xDestination = xEntrance;
//		yDestination = yEntrance;
//	}
//
//	public void DoExitRestaurant() {
//		xDestination = xHome;
//		yDestination = yHome;
//		command = Command.LeaveRestaurant;
//	}
//	
//	public void setHomePosition(int custs) {
//		xEntrance = 0 + custs*40;
//		xDestination = xEntrance;
//	}
//
//	@Override
//	public void DoExit() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public int getXPos() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getYPos() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//}
