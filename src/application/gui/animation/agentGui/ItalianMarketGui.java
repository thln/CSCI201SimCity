package application.gui.animation.agentGui;
import italianRestaurant.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class ItalianMarketGui implements Gui {

    private ItalianMarketRole agent = null;
    BufferedImage img = null;

    private int xPos = 450, yPos = 100;//default cook position
    private int xDestination = 375, yDestination = 100;//default start position
    
    static final int NTABLES = 5;
    int tables;
    
    public ItalianMarketGui() {
    }
    
    public ItalianMarketGui(ItalianMarketRole agent) {
        //this.agent = agent;
        
        try {
            img = ImageIO.read(new File("res/market.png"));
        } catch (IOException e) {
        }
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
    		//	agent.msgAtTable();
    		}
        //}
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.CYAN);
        g.drawImage(img, xPos, yPos, null);
        //g.fillRect(xPos, yPos, 20, 20);
    }

    public boolean isPresent() {
        return true;
    }
    
    public void DoRestocking() {
    	
    	/*
    	switch (Ochoice){
    	case "Steak": System.out.print(Ochoice);
    	break;
    	case "Chicken": System.out.print(Ochoice);
    	break;
    	case "Salad": System.out.print(Ochoice);
    	break;
    	case "Pizza": System.out.print(Ochoice);
    	break;
    	default:
    	break;
    	}
    	*/
    }
    
    public void DoPlateIt() {
    	
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
