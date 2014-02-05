//package americanRestaurant.gui;
//
//import java.awt.Color;
//import java.awt.Graphics2D;
//
//import javax.swing.ImageIcon;
//
//import americanRestaurant.AmericanRestaurantCookRole;
//import application.gui.animation.RestaurantPanel;
//import application.gui.animation.RestaurantPanel.guiCookState;
//import application.gui.animation.agentGui.Gui;
//
//public class AmericanCookGui implements Gui {
//
//	boolean isPresent;
//	private AmericanRestaurantCookRole agent = null;
//
//	private int xPos, yPos;
//	private int xSize = 20, ySize = 20;
//	private int xDestination, yDestination;
//	RestaurantPanel panel1;
//	ImageIcon ingredientsImage = new ImageIcon ("res/ingredients.png");
//	public static final int xPlatingArea = 400;
//	public static final int yPlatingArea = 300;
//	public static final int xCookingArea = 400;
//	public static final int yCookingArea = 250;
//	public static final int xFridgeArea = 460;
//	public static final int yFridgeArea = 300;
//	
//
//	public AmericanCookGui(AmericanRestaurantCookRole c, RestaurantPanel p1){
//		panel1 = p1;
//		xDestination = xPlatingArea;
//		yDestination = yPlatingArea;
//		xPos = xPlatingArea;
//		yPos = yPlatingArea;
//		agent = c;
//		isPresent = true;
//	}
//
//	public void updatePosition() {
//		
//		if (xPos < xDestination)
//			xPos++;
//		else if (xPos > xDestination)
//			xPos--;
//
//		if (yPos <  yDestination)
//			yPos++;
//		else if (yPos >  yDestination)
//			yPos--;	
//		
//		if (xPos == xDestination && yPos == yDestination) {
//			if ((xDestination == xCookingArea && yDestination == yCookingArea))  {
//				agent.msgAtCookingArea();
//				panel1.cookState = guiCookState.cooking;
//			}
//			if ((xDestination == xPlatingArea && yDestination == yPlatingArea)) {
//				agent.msgAtPlatingArea();
//			}
//			if ((xDestination == xFridgeArea && yDestination == yFridgeArea)) {
//				panel1.cookState = guiCookState.gathering;
//				DoGoCook();
//			}
//		}
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
//	public void DoGoCook() {
//		xDestination = xCookingArea;
//		yDestination = yCookingArea;
//	}
//	
//	public void DoGoToFridge() {
//		xDestination = xFridgeArea;
//		yDestination = yFridgeArea;
//	}
//	
//	public void DoGoPlate() {
//		panel1.cookState = guiCookState.plating;
//		xDestination = xPlatingArea;
//		yDestination = yPlatingArea;
//	}
//
//	public void draw(Graphics2D g) {
//		g.setColor(Color.RED);
//		g.fillRect(xPos, yPos, xSize, ySize);
//	}
//	
//	public void drawIngredients(Graphics2D g, RestaurantPanel p1) {
//		g.drawImage(ingredientsImage.getImage(), xPos+20, yPos+20, p1);
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
//	@Override
//	public void DoExit() {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
