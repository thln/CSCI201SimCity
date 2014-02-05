package application.gui.animation.agentGui;

import housing.*;

import java.awt.*;

import javax.swing.*;

public class HousingMaintenanceGui extends HousingGui{

	private MaintenanceWorkerRole agent = null;
	private boolean isPresent = true;

	//RestaurantGui gui;

    private int xPos = 300, yPos = 300;//default MarketCustomer position
    private int xDestination = 330, yDestination = 260;//default start position
	
	private enum Command {noCommand};
	private Command command = Command.noCommand;

	private enum CustomerState {nothing};
	CustomerState state = CustomerState.nothing;
	
	public HousingMaintenanceGui() {
	}

	public HousingMaintenanceGui(MaintenanceWorkerRole c/*, RestaurantGui gui*/){
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
		g.setColor(Color.GREEN);
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
    public void DoGoToKitchen() {
    	xDestination = 510;
    	yDestination = 85;
    }
    
    public void DoGoToBedRoom() {
    	xDestination = 450;
    	yDestination = 200;
    }
    
    public void DoGoToBathroom() {
    	xDestination = 100;
    	yDestination = 50;
    }
    
    public void DoGoToLivingRoom() {
    	xDestination = 100;
    	yDestination = 200;
    }
    
    public void DoExit() {
    	xDestination = 300;
    	yDestination = 300;
    }
}