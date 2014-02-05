package application.gui.animation.agentGui;

import bank.*;

import java.awt.*;

import javax.swing.*;

public class BankGuardGui extends BankGui{

	private BankGuardRole agent = null;
	private boolean isPresent = true;

	//RestaurantGui gui;

    private int xPos = 340, yPos = 320;//default BankGuard position
    private int xDestination = 340, yDestination = 260;//default start position
	
	private enum Command {inTransit,toTellers, catchRobber, noCommand};
	private Command command = Command.noCommand;

	private enum CustomerState {nothing};
	CustomerState state = CustomerState.nothing;
	
	public BankGuardGui() {
	}
	/*
	public BankGuardGui(BankGuardRole c, RestaurantGui gui){
		//agent = c;
		//this.gui = gui;
	}*/

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
				if(command == Command.toTellers)
					BacktoPosition();
				else if(command == Command.catchRobber)
					BacktoPosition();
			}
			command = Command.noCommand;
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.ORANGE);
		g.fillRect(xPos, yPos, 20, 20);
		g.setColor(Color.BLACK);
		g.drawString("Guard", xPos, yPos);
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
    public void GoToTellers() {
    	xDestination = 370;
    	yDestination = 220;
    	command = Command.toTellers;
    }
    
    public void BacktoPosition() {
    	xDestination = 340;
    	yDestination = 260;
    	command = Command.inTransit;
    }
    
    public void DoCatchRobber() {
    	xDestination = 100;
    	yDestination = 40;
    	command = Command.catchRobber;
    }
    
    public void BringToJail() {
    	xDestination = 300;
    	yDestination = -20;
    	command = Command.inTransit;
    }
    
    public void DoExit() {
    	xDestination = 300;
    	yDestination = 300;
    }
    
    public void setPerson(BankGuardRole c) {
    	agent = c;
    }
}