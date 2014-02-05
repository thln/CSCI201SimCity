package application.gui.animation.agentGui;

import housing.*;

import java.awt.Color;
import java.awt.Graphics2D;

public class ApartmentResidentGui extends HousingResidentGui{
    
    private ApartmentResidentRole agent = null;
    private boolean isPresent = true;
    
    //RestaurantGui gui;

    private int xPos = 300, yPos = 300;//default HouseRenter position
    private int xDestination = 300, yDestination = 200;//default start position
    int yIncrement = 0;
    int unit;
    
    private enum Command {noCommand, inTransit};
	private Command command = Command.noCommand;

	private enum CustomerState {nothing};
	CustomerState state = CustomerState.nothing;
	
	Color myColor = Color.BLUE;
	Color transColor = new Color(0,0,0,1);
	Color currColor;
	
	public ApartmentResidentGui() {
	}
	
    public ApartmentResidentGui(ApartmentResidentRole agent) {
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
    				currColor = transColor;
    				if(agent != null)
    					agent.msgAtDestination();
    			}
    			command = Command.noCommand;
    		}
        //}
    }

    public void draw(Graphics2D g) {
        g.setColor(currColor);
        g.fillRect(xPos, yPos, 20, 20);
        g.setColor(currColor);
        if(agent != null)
        	g.drawString(agent.getName(), xPos, yPos);
    }
	
	public void DoDoToUnit(int unit) {
		currColor = myColor;
    	CheckLessThan10(unit);
    	xDestination = 60 + 50*this.unit;
    	yDestination = 50 + 80*yIncrement;
    	command = Command.inTransit;
    }
	
	public void DoExit() {
		currColor = myColor;
    	CheckLessThan10(unit);
    	xDestination = 300;
    	yDestination = 300;
    	command = Command.inTransit;
    }
    
    public void CheckLessThan10(int unit) {
    	if(unit > 9) {
    		this.unit = unit - 10;
    		yIncrement++;
    		CheckLessThan10(this.unit);
    	}
    	else
    		this.unit = unit;
    }
}
