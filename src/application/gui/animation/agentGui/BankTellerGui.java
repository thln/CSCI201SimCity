package application.gui.animation.agentGui;

import bank.*;

import java.awt.*;
import javax.swing.*;

public class BankTellerGui extends BankGui{

	private BankTellerRole agent = null;
	private boolean isPresent = true;

    private int xPos = 600, yPos = 30;//default bank teller position
    private int xDestination = 600, yDestination = 50;//default start position
	private int tellerPosition;
    
	private enum Command {noCommand};
	private Command command = Command.noCommand;

	private enum CustomerState {nothing};
	CustomerState state = CustomerState.nothing;
	
	public BankTellerGui() {
	}
	
	public BankTellerGui(BankTellerRole c){
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
			if(agent != null)
				agent.msgAtDestination();
			command = Command.noCommand;
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(xPos, yPos, 20, 20);
		g.setColor(Color.BLACK);
		g.drawString("Teller " + tellerPosition, xPos, yPos);
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
	public void DoGoToWindow(/*int window*/) {
		xDestination = 525;
    	//yDestination = 20*window+30*(window-1);
		yDestination = 20*tellerPosition + 30*(tellerPosition - 1);
	}
	
	public void DoGoToVault() {
    	xDestination = 120;
    	yDestination = 70;
    }
	
    public void DoExit() {
    	xDestination = 300;
    	yDestination = 300;
    }

	public void setTellerPosition(int i) {
		tellerPosition = i;
	}
}