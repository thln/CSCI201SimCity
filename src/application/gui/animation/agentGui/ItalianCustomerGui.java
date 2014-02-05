package application.gui.animation.agentGui;

import italianRestaurant.*;

import java.awt.*;
import java.io.*;

import javax.imageio.*;

import java.awt.image.BufferedImage;;

public class ItalianCustomerGui implements Gui{
	
	private ItalianCustomerRole agent = null;
	private boolean isPresent = true;
	//private boolean isHungry = false;
	
	//RestaurantGui gui;
	BufferedImage qmark = null;
	BufferedImage steak = null;
	BufferedImage chicken = null;
	BufferedImage salad = null;
	BufferedImage pizza = null;
	BufferedImage currImg = null;
	
    private int xPos = -40, yPos = -40;//default customer position
    private int xDestination = -40, yDestination = -40;//default start position
    public int home;
    
	private enum Command {noCommand, GoToRestaurant, GoToSeat, LeaveRestaurant, atCashier};
	private Command command=Command.noCommand;
    
    static final int NTABLES = 5;
    
    public ItalianCustomerGui() {
		try {
            qmark = ImageIO.read(new File("res/qmark.png"));
        	} catch (IOException e) {
        	}
		try {
            chicken = ImageIO.read(new File("res/chicken.png"));
        	} catch (IOException e) {
        	}
		try {
            steak = ImageIO.read(new File("res/steak.png"));
        	} catch (IOException e) {
        	}
		try {
            salad = ImageIO.read(new File("res/salad.png"));
        	} catch (IOException e) {
        	}
		try {
            pizza = ImageIO.read(new File("res/pizza.png"));
        	} catch (IOException e) {
        	}
    }
    
	public ItalianCustomerGui(ItalianCustomerRole c/*, RestaurantGui gui*/){ //AmericanRestaurantHostRole m) {
		agent = c;
		
		//this.gui = gui;
		try {
            qmark = ImageIO.read(new File("res/qmark.png"));
        	} catch (IOException e) {
        	}
		try {
            chicken = ImageIO.read(new File("res/chicken.png"));
        	} catch (IOException e) {
        	}
		try {
            steak = ImageIO.read(new File("res/steak.png"));
        	} catch (IOException e) {
        	}
		try {
            salad = ImageIO.read(new File("res/salad.png"));
        	} catch (IOException e) {
        	}
		try {
            pizza = ImageIO.read(new File("res/pizza.png"));
        	} catch (IOException e) {
        	}
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
				if (command==Command.GoToRestaurant)
					agent.msgAnimationFinishedGoToRest();
				if (command==Command.GoToSeat) 
					agent.msgAnimationFinishedGoToSeat();
				else if (command==Command.LeaveRestaurant) {
					agent.msgAnimationFinishedLeaveRestaurant();
					//System.out.println("about to call gui.setCustomerEnabled(agent);");
					//isHungry = false;
					//	gui.setCustomerEnabled(agent);
				}
				else if (command==Command.atCashier)
					agent.msgAtCashier();
				command=Command.noCommand;
			}
		}
		/*
		if (xPos == xDestination && yPos == yDestination
				& (xDestination == 20) & (yDestination == 170) && ) {
			agent.msgAtCashier();
			command=Command.noCommand;
		}*/
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(xPos, yPos, 20, 20);
		if(agent != null) {
        	g.setColor(Color.BLACK);
        	g.drawString(agent.getName(), xPos, yPos);
        }
		g.drawImage(currImg, xPos, yPos, null);
	}

	public boolean isPresent() {
		return isPresent;
	}
	/*
	public void setHungry() {
		isHungry = true;
		agent.gotHungry();
		setPresent(true);
	}*/
	/*
	public boolean isHungry() {
		return isHungry;
	}
	*/
	public void setPresent(boolean p) {
		isPresent = p;
	}
	
	public void SetHome(int startPos) {
		home = startPos;
	}
	
	public void DoGoToRestaurant() {
		xDestination = 30*home + 20;
		yDestination = 20;
		command = Command.GoToRestaurant;
	}

	public void DoGoToSeat(int seatnumber) {//later you will map seatnumber to table coordinates.
			xDestination = 50*seatnumber;
			yDestination = 120;
		command = Command.GoToSeat;
	}
	
	public void DoGotoCashier(){
		xDestination = 20;
    	yDestination = 190;
    	currImg = null;
    	command=Command.atCashier;
	}
	
	public void DoGoToJail(){
		xDestination = 125;
    	yDestination = 260;
    	//System.out.println("about to call gui.setCustomerEnabled(agent);");
		//isHungry = false;
		//gui.setCustomerEnabled(agent);
		command=Command.noCommand;
	}

	public void DoExitRestaurant() {
		//xDestination = 40;
		//yDestination = 20;
		
		xDestination = -40;
		yDestination = -40;
		
		command = Command.LeaveRestaurant;
	}
	
	public void DoExit() {
		DoExitRestaurant();
	}
	
	public void showOrder(String choice) {
		switch(choice){
			case "Steak": currImg = steak;
				break;
			case "Chicken": currImg = chicken;
				break;
			case "Salad": currImg = salad;
				break;
			case "Pizza": currImg = pizza;
				break;
			default: currImg = qmark;
				break;
		}
	}
	
	public int getXPos() {
		return xPos;
	}
    public int getYPos() {
    	return yPos;
    }
}
