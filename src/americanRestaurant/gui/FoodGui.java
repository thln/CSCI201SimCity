//package americanRestaurant.gui;
//
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//
//import javax.swing.ImageIcon;
//
//import americanRestaurant.AmericanRestaurantWaiterRole;
//import application.gui.animation.RestaurantPanel;
//import application.gui.animation.agentGui.Gui;
//
//public class FoodGui implements Gui{
//
//	boolean isPresent;
//	RestaurantPanel panel1;
//	AmericanRestaurantWaiterRole waiter1;
//	String choice1;
//	private boolean stop = false;
//	ImageIcon foodImage;
//
//	private int xPos, yPos;
//	private int xDestination, yDestination;
//	private enum FoodState {atCook, atTable};
//
//	public FoodGui(AmericanRestaurantWaiterRole w1, RestaurantPanel p, String choice){
//		waiter1 = w1;
//		panel1 = p;
//		choice1 = choice;
//		xPos = w1.getGui().getXPos();
//		yPos = w1.getGui().getYPos()-15;
//		isPresent = true;
//		
//		if (choice == "Steak")
//			foodImage = new ImageIcon("res/steak.png");
//		if (choice == "Chicken")
//			foodImage = new ImageIcon("res/chicken.jpg");
//		if (choice == "Salad")
//			foodImage = new ImageIcon("res/salad.gif");
//		if (choice == "Pizza")
//			foodImage = new ImageIcon("res/pizza.jpeg");
//	}
//
//	public void updatePosition() {
//		if (xPos < waiter1.getGui().getXPos())
//			xPos++;
//		else if (xPos > waiter1.getGui().getXPos())
//			xPos--;
//
//		if (yPos < waiter1.getGui().getYPos())
//			yPos++;
//		else if (yPos > waiter1.getGui().getYPos())
//			yPos--;	
//	}
//
//	public boolean isPresent() {
//		return isPresent;
//	}
//
//	public void setPresent(boolean p) {
//		isPresent = p;
//	}
//
//	public void DoGoToSeat(int seatNumber) {//later you will map seatnumber to table coordinates.
//	//	xDestination = AnimationPanel.tables[seatNumber].xCoordinate;
//	//	yDestination = AnimationPanel.tables[seatNumber].yCoordinate;
//	}
//
//	public void drawIcon (Graphics g, RestaurantPanel p1) {
//		g.drawImage(foodImage.getImage(), xPos, yPos, p1);
//	}
//	
//	public void draw(Graphics2D g) {
//	}
//	
//	public void setStop(boolean e) {
//		stop = e;
//	}
//	
//	public boolean getStop () {
//		return stop;
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
