package application.gui.animation.agentGui;

import bank.*;

import java.awt.*;

import javax.swing.*;

public class BankLoanerGui extends BankGui{

	private LoanOfficerRole agent = null;
	private boolean isPresent = true;

	//RestaurantGui gui;

    private int xPos = 0, yPos = 170;//default Loan Officer position
    private int xDestination = 50, yDestination = 170;//default start position
	
	private enum Command {noCommand};
	private Command command = Command.noCommand;

	private enum CustomerState {nothing};
	CustomerState state = CustomerState.nothing;
	
	public BankLoanerGui() {
	}
	
	public BankLoanerGui(LoanOfficerRole c/*, RestaurantGui gui*/){
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
			command = Command.noCommand;
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect(xPos, yPos, 20, 20);
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
    public void DoGoToTellers() {
    	xDestination = 550;
    	yDestination = 220;
    }
    
    public void BacktoPosition() {
    	xDestination = 50;
    	yDestination = 170;
    }
    
    public void DoExit() {
    	xDestination = 300;
    	yDestination = 300;
    }
}