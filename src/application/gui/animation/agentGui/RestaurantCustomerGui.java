package application.gui.animation.agentGui;

import java.awt.*;

import javax.swing.JLabel;

import chineseRestaurant.*;

public class RestaurantCustomerGui extends RestaurantGui{

	private ChineseRestaurantCustomerRole agent = null;
	private boolean isPresent = true;
	private boolean isHungry = false;

	//RestaurantGui gui;

	private int xPos, yPos;
	private int xDestination, yDestination;
	private int xHome, yHome;
	private enum Command {noCommand, GoToSeat, GoToCashier, LeaveRestaurant, GoToRestaurant};
	private Command command = Command.noCommand;

	public static final int xTable = 200;
	public static final int yTable = 250;

	private enum CustomerState {nothing, readyToOrder, ordered, gotFood, askForCheck};
	CustomerState state = CustomerState.nothing;

	private String choice;
	
	public RestaurantCustomerGui() {
	}
	
	public RestaurantCustomerGui(ChineseRestaurantCustomerRole c/*, RestaurantGui gui*/){ //AmericanRestaurantHostRole m) {
		agent = c;
		xPos = -20;
		yPos = -20;
		//this.gui = gui;
	}

	public void updatePosition() {
		if (xPos < xDestination)
			xPos++;
		else if (xPos > xDestination)
			xPos--;

		if (yPos < yDestination)
			yPos++;
		else if (yPos > yDestination)
			yPos--;

		if (xPos == xDestination && yPos == yDestination) {
			if (command == Command.GoToRestaurant) {
//				agent.gotHungry(xHome, yHome);
			}
			if (command == Command.GoToSeat){
				agent.msgAnimationFinishedGoToSeat();
			}
			else if (command == Command.GoToCashier) {
				agent.msgAnimationFinishedGoToCashier();
			}
			else if (command == Command.LeaveRestaurant) {
				agent.msgAnimationFinishedLeaveRestaurant();
				System.out.println("about to call gui.setCustomerEnabled(agent);");
				isHungry = false;
//				gui.setCustomerEnabled(agent);
			}
			command = Command.noCommand;
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(xPos, yPos, 20, 20);
		g.setColor(Color.BLACK);
		if(agent != null)
			g.drawString(agent.getName(), xPos, yPos);
		if (state == CustomerState.readyToOrder) {
			g.setColor(Color.RED);
			g.drawString(" ?", xPos + 3, yPos + 15);
		}

		if (state == CustomerState.ordered) {
			g.setColor(Color.RED);
			g.drawString(" ?", xPos + 3, yPos + 15);
			g.drawString(choice, xPos, yPos + 40);
		}

		if (state == CustomerState.gotFood) {
			g.setColor(Color.WHITE);
			g.drawString(choice, xPos, yPos + 40);
		}

		if (state == CustomerState.askForCheck) {
			g.setColor(Color.RED);
			g.drawString(" ?", xPos + 3, yPos + 15);
			g.drawString("Check", xPos, yPos + 40);
		}
	}

	public boolean isPresent() {
		return isPresent;
	}

	public void setHungry() {
		command = Command.GoToRestaurant;
		isHungry = true;
		setPresent(true);
		xDestination = xHome;
		yDestination = yHome;
	}

	public boolean isHungry() {
		return isHungry;
	}

	public void setPresent(boolean p) {
		isPresent = p;
	}

	public void DoGoToSeat(int tableNumber) {//later you will map seatnumber to table coordinates.
		if (tableNumber == 1)
		{
			xDestination = 50;
			yDestination = 250;
		}
		else if (tableNumber == 2)	
		{
			xDestination = xTable;
			yDestination = yTable;
		}
		else if (tableNumber == 3)	
		{
			xDestination = 350;
			yDestination = 250;
		}
		else //if (tableNumber == 4)	
		{
			xDestination = 200;
			yDestination = 75;
		}
		command = Command.GoToSeat;
	}

	public void DoReadyToOrder() {
		state = CustomerState.readyToOrder;
	}

	public void DoPlaceOrder(String choice) {
		this.choice = choice;
		state = CustomerState.ordered;
	}

	public void DoEatFood(String choice) {
		this.choice = choice;
		state = CustomerState.gotFood;
	}

	public void DoAskForCheck() {
		state = CustomerState.askForCheck;
	}

	public void DoGoToCashier() {
		state = CustomerState.nothing;
		xDestination = 285;
		yDestination = 305;
		command = Command.GoToCashier;
	}

	public void DoExitRestaurant() {
		state = CustomerState.nothing;
		xDestination = -20;
		yDestination = -20;
		command = Command.LeaveRestaurant;
	}


	public void DoGoToJail() {
		state = CustomerState.nothing;
		xDestination = 415;
		yDestination = 35;
	}

	public void setHomePosition(int x, int y) {
		xHome = x;
		yHome = y;
		xDestination = xHome;
		yDestination = yHome;
	}
}
