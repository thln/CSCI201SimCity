package application.gui.animation.agentGui;

import market.*;

import java.awt.*;

import javax.swing.*;

public class MarketUPSmanGui extends MarketGui{

	private UPSmanRole agent = null;
	private boolean isPresent = true;

	//RestaurantGui gui;

    private int xPos = 600, yPos = 200;//default MarketRunner position
    private int xDestination = 600-100, yDestination = 200;//default start position
	
	private enum Command {noCommand, inTransit};
	private Command command = Command.noCommand;

	private enum CustomerState {nothing};
	CustomerState state = CustomerState.nothing;
	
	public MarketUPSmanGui() {
	}
	
	public MarketUPSmanGui(UPSmanRole c/*, RestaurantGui gui*/){
		agent = c;
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
			if(command == Command.inTransit) {
			if(agent != null)
				agent.msgAtDestination();
				//DoExit();
			}
			command = Command.noCommand;
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(new Color(102, 51, 0));
		g.fillRect(xPos, yPos, 30, 30);
		g.setColor(Color.YELLOW);
		g.drawString("UPS", xPos, yPos+20);
		g.setColor(Color.BLACK);
        if(agent != null)
        	g.drawString(agent.getName(), xPos, yPos);
	}

	public boolean isPresent() {
		return isPresent;
	}

	public void setPresent(boolean p) {
		isPresent = p;
	}
	
	public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
	
	//Actions
	
	public void DoGoToSalesPerson() { 
		//goes to salesperson to pick up deliveries
    	xDestination = 300;
    	yDestination = 200;
    	command = Command.inTransit;
	}
	
	public void DoExit() { 
		//going outside of market to deliver order
		xDestination = 600;
		yDestination = 200;
	}
}