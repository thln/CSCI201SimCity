package application.gui.animation.agentGui;

import market.*;

import java.awt.*;

import javax.swing.*;

public class MarketCustomerGui extends MarketGui {

    private MarketCustomerRole agent = null;
    private boolean isPresent = true;
    
    //RestaurantGui gui;

    private int xPos = 300, yPos = 300;//default MarketCustomer position
    private int xDestination = 300, yDestination = 260;//default start position
    public int home = 9000;
    private int yIncrement = 0;
    private enum Command {noCommand, inTransit};
	private Command command = Command.noCommand;

	private enum CustomerState {nothing};
	CustomerState state = CustomerState.nothing;
    
	public MarketCustomerGui() {
	}
	
    public MarketCustomerGui(MarketCustomerRole agent/*, RestaurantGui gui*/) {
        this.agent = agent;
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
    			if(command != Command.noCommand) {
	    			if(agent != null)
	    				agent.msgAtDestination();
	    		}
    			command = Command.noCommand;
    		}
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
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
    
    public void setHome(int home) {
    	int oldhome = this.home;
    	this.home = home;
    	/*
    	if(oldhome != 9000) {
    		try {
    			agent.atDestination.acquire();
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		DoGoToSalesPerson();
    	}*/
    }
    
    //Actions
    public void DoGoToSalesPerson() {
    	xDestination = 120;
    	yDestination = 220;
    	command = Command.inTransit;
    }
    
    public void waitInLine() {
    	checkLessThan7();
    	xDestination = 120 + 30*(home+1);
    	yDestination = 220 + 30*yIncrement;
    	command = Command.inTransit;
    }
    
    public void checkLessThan7() {
    	if(home > 6) {
    		home = home - 7;
    		checkLessThan7();
    		yIncrement++;
    	}
    	else 
    		home = home;
    }
    
    public void DoExit() {
    	xDestination = 300;
    	yDestination = 350;
    	command = Command.inTransit;
    }
    
    public MarketCustomerRole getAgent()
    {
    	return agent;
    }
}
