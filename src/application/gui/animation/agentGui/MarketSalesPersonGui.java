package application.gui.animation.agentGui;

import market.*;

import java.awt.*;

import javax.swing.*;

public class MarketSalesPersonGui extends MarketGui{

	private SalesPersonRole agent = null;
	private boolean isPresent = true;

	//RestaurantGui gui;

    private int xPos = 120, yPos = 100;//default MarketRunner position
    private int xDestination = 150, yDestination = 100;//default start position
	
	private enum Command {noCommand};
	private Command command = Command.noCommand;

	private enum CustomerState {nothing};
	CustomerState state = CustomerState.nothing;
	
	public MarketSalesPersonGui() {
	}
	
	public MarketSalesPersonGui(SalesPersonRole c/*, RestaurantGui gui*/){
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
			if(agent != null)
				agent.msgAtDestination();
			BacktoPosition();
			command = Command.noCommand;
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.ORANGE);
		g.fillRect(xPos, yPos, 20, 20);
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
    public void DoGotoRunner() {
    	xDestination = 170;
    }
    
    public void BacktoPosition() {
    	xDestination = 150;
    	yDestination = 100;
    }
    
    public void DoExit() {
    	xDestination = 300;
    	yDestination = 325;
    }
}