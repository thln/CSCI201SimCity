package application.gui.animation.agentGui;

import italianRestaurant.*;

import java.awt.*;

public class ItalianHostGui implements Gui {

    private ItalianHostRole agent = null;

    private int xPos = 300, yPos = 0;//default HOST position
    private int xDestination = 170, yDestination = 20;//default start position
    
    static final int NTABLES = 5;
    int tables;
    
    public ItalianHostGui() {
    }
    
    public ItalianHostGui(ItalianHostRole agent) {
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
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(xPos, yPos, 20, 20);
        
        g.setColor(Color.BLACK);
        g.drawString("AmericanRestaurantHost", xPos, yPos);
    }

    public boolean isPresent() {
        return true;
    }
    
    public void DoExit() {
    	xDestination = -20;
    	yDestination = 300;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
