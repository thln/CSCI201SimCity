package application.gui.animation.agentGui;


import sun.applet.resources.MsgAppletViewer;

import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

import chineseRestaurant.*;

public class RestaurantWaiterGui extends RestaurantGui {

	private ChineseRestaurantWaiterRole agent = null;
	//RestaurantGui gui;

	private int xPos = -20, yPos = 0;//default waiter position
	private int xDestination = -20, yDestination = -20;//default start position
	private int xHome = -20, yHome = -20;

	public static int xTable = 200;
	public static int yTable = 250;

	private boolean onBreak = false;

	private boolean deliveringOrder = false;
	private String currentDelivery;
	
	public RestaurantWaiterGui() {
	}
	
	public RestaurantWaiterGui(ChineseRestaurantWaiterRole agent) {
		this.agent = agent;
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
			if (xPos == 340 && yPos == 10) {
				enableOffBreakBox();
				return;
			}

			if (xPos != xHome && yPos != yHome) {
				agent.msgAtDestination();
				deliveringOrder = false;
			}
		}

		if (xPos == xHome && yPos == yHome) {
			agent.msgIsInLobby();
			//if (agent.isOnBreak()) {
		//		denyBreak();
		//	}
		}

	}

	public void draw(Graphics2D g) {
		g.setColor(Color.MAGENTA);
		g.fillRect(xPos, yPos, 20, 20);

		if (deliveringOrder) {
			g.setColor(Color.RED);
			g.drawString(currentDelivery, xPos, yPos - 5);
		}
		g.setColor(Color.BLACK);
		if(agent != null)
			g.drawString(agent.getName(), xPos, yPos+30);

	}

	public boolean isPresent() {
		return true;
	}

	public void askForBreak() {
		onBreak = true;
	//	agent.msgWantToGoOnBreak();
	}
	
	private void enableOffBreakBox() {
		//gui.enableOffBreakBox(agent);
	}
	
//	private void enableOnBreakBox() {
//		gui.enableOnBreakBox(agent);
//	}
//
//	private void enableOnBreakBoxSelected() {
//		gui.enableOnBreakBoxSelected(agent);
//	}
	
	
	public void setOffBreak() {
	//	agent.msgGoOffBreak();
	}
	
	public void denyBreak() {
		onBreak = false;
		//gui.enableOnBreakBox(agent);
	}

	public boolean isOnBreak() {
		if (onBreak == true)
			return true;

		return false;
	}
	
	public void DoPickUpCustomer(int x, int y) {
		xDestination = x + 20;
		yDestination = y + 20;
	}

	public void DoBringToTable(ChineseRestaurantCustomerRole customer, int tableNumber) {

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
		else // if (tableNumber == 4)	
		{
			xDestination = 200;
			yDestination = 75;
		}

		xDestination += 20;
		yDestination -= 20;
	}

	public void DoTakeOrder(int tableNumber) {

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

		xDestination += 20;
		yDestination -= 20;
	}

	public void DoGoToKitchen() {
		xDestination = 370;
		yDestination = 200; 
	}
	
	public void DoGoToPlatingArea() {
		xDestination = 340;
		yDestination = 120; 
	}

	public void DoGoOnBreak() {
		xDestination = 340;
		yDestination = 10;
	}

	public void DoDeliverOrder(int tableNumber, String choice) {

		deliveringOrder = true;
		currentDelivery = choice;

		if (tableNumber == 1) {
			xDestination = 50;
			yDestination = 250;
		}
		else if (tableNumber == 2) {
			xDestination = xTable;
			yDestination = yTable;
		}
		else if (tableNumber == 3) {
			xDestination = 350;
			yDestination = 250;
		}
		else //if (tableNumber == 4)	
		{
			xDestination = 200;
			yDestination = 75;
		}

		xDestination += 20;
		yDestination -= 20;
	}

	public void DoLeaveCustomer() {
		xDestination = xHome;
		yDestination = yHome;
	}

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}
	
	public void setHomePosition(int x, int y) {
		xHome = x;
		yHome = y;
		xDestination = xHome;
		yDestination = yHome;
	}
}
