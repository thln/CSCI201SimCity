package application.gui.animation.agentGui;

import market.*;

import java.awt.*;

import javax.swing.*;

public class MarketRunnerGui extends MarketGui{

	private MarketRunnerRole agent = null;
	private boolean isPresent = true;


    private int xPos = 340, yPos = 100;//default MarketRunner position
    private int xDestination = 300, yDestination = 100;//default start position
	
	private enum Command {noCommand, inTransit};
	private Command command = Command.noCommand;

	private enum CustomerState {nothing};
	CustomerState state = CustomerState.nothing;
	
	public MarketRunnerGui() {
	}
	
	public MarketRunnerGui(MarketRunnerRole c) {
		agent = c;
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
			if(agent != null) {
				if(command == Command.inTransit)
					agent.msgAtDestination();
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
    public void DoGoToInventory() {
    	xDestination = 600-150;
    	yDestination = 60;
    	command = Command.inTransit;
    }
    
    public void DoGoToSalesPerson() {
    	xDestination = 300;
    	yDestination = 100;
    	command = Command.inTransit;
    }
    
    public void DoExit() {
    	xDestination = 300;
    	yDestination = 325;
    }
    
    public boolean atInventory() {
    	if(xPos == 450 && yPos == 60)
    		return true;
    	return false;
    }
}