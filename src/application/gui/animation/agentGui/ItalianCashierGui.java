package application.gui.animation.agentGui;

import italianRestaurant.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class ItalianCashierGui implements Gui {

    private ItalianCashierRole agent = null;
    BufferedImage img = null;

    private int xPos = 0, yPos = 240;//default AmericanRestaurantCashier position
    private int xDestination = 20, yDestination = 240;//default start position
    
    static final int NTABLES = 5;
    int tables;
    public ItalianCashierGui() {
    }
    
    public ItalianCashierGui(ItalianCashierRole agent) {
        this.agent = agent;
        /*
        try {
            img = ImageIO.read(new File("res/chef.png"));
        } catch (IOException e) {
        }*/
    }

    public void updatePosition() {
    	//for (int ix = 1; ix <= NTABLES; ix++) {
    		if (xPos < xDestination)
    			xPos++;
    		else if (xPos > xDestination)
    			xPos--;

    		if (yPos < yDestination)
    			yPos++;
    		else if (yPos > yDestination)
    			yPos--;

    		if (xPos == xDestination && yPos == yDestination
    				& (xDestination == 50*tables + 20) & (yDestination == 50 - 20)) {
    			
    		}
        //}
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        //g.drawImage(img, xPos, yPos, null);
        g.fillRect(xPos, yPos, 20, 20);
        g.setColor(Color.BLACK);
        g.drawString("AmericanRestaurantCashier", xPos, yPos+40);
    }

    public boolean isPresent() {
        return true;
    }
    
    public void DoCashiering() {
    	
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
