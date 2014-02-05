package application.gui.animation.agentGui;

import italianRestaurant.*;

import java.awt.*;
import java.util.List;
import java.io.*;
import java.util.*;

import javax.imageio.*;


import java.awt.image.BufferedImage;

public class ItalianWaiterGui implements Gui {

    private ItalianWaiterRole agent = null;

    private int xPos = 300, yPos = 0;//default waiter position
    private int xDestination = 300, yDestination = 70;//default start position
    int homePos;
    private enum Command {noCommand, goToWaiting, GoToSeat, LeaveTable, LeaveTableAgain, atCook, atCashier};
	private Command command=Command.noCommand;
	private List<Order> orders = Collections.synchronizedList(new ArrayList<Order>());
    static final int NTABLES = 5;
    int tables;
    
    public ItalianWaiterGui() {
    }
    
    public ItalianWaiterGui(ItalianWaiterRole agent) {
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
    		
    	if (xPos == xDestination && yPos == yDestination) {
    		if (command==Command.LeaveTable) {
				synchronized(orders) {
			    	if(orders != null) {
			    		for(int i=0; i<orders.size(); i++) {
			    			if(orders.get(i).State == state.atTable) {
			    				orders.get(i).State = state.moving;
			    				orders.remove(orders.get(i));
			    			}
			    		}	
			    	}
				}
    		}
    		
    		if(agent != null) {
    			if (command==Command.goToWaiting) {
					agent.msgAtCust();
    			}
    			if (command==Command.GoToSeat) {
    				agent.msgAtTable();
    			}
    			else if (command==Command.LeaveTable) {
    				synchronized(orders) {
    			    	if(orders != null) {
    			    		for(Order order : orders) {
    			    			if(order.State == state.atTable) {
    			    				order.State = state.moving;
    			    				orders.remove(order);
    			    			}
    			    		}	
    			    	}
    				}
    				agent.msgBackAtTable();
   				}
    			else if (command==Command.LeaveTableAgain) {
    				agent.msgAtTableAgain();
    			}
    			else if(command==Command.atCook) {
        			agent.msgAtCook();
   				}
   				else if(command==Command.atCashier) {
   	    			agent.msgAtCashier();
    			}
    		}
    		command=Command.noCommand;
    	}
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, 20, 20);
        g.setColor(Color.BLACK);
        if(agent != null) {
        	g.drawString(agent.getRoleName(), xPos, yPos+40);
        }
        synchronized(orders) {
	        if(orders != null) {
	        	for(Order order : orders) {
	        		g.drawString(order.choice, xPos, yPos);
	        	}
	        }
        }
    }

    public boolean isPresent() {
        return true;
    }
    
    public void StartAt(int home) {
    	homePos = home;
    	xDestination = 300 - 30*homePos;
    	yDestination = 70;
    }
    
    public void GoToWaiting(int Pos) {
    	xDestination = 30*Pos + 40;
    	yDestination = 70;
    	
    	command = Command.goToWaiting;
    }
    
    public void DoBringToTable(int table) {
    	xDestination = 50*table + 20;
    	yDestination = 120 - 20;
    	
    	command = Command.GoToSeat;
    }

    public void GoToHome() {
    	xDestination = 300 - 30*homePos;
    	yDestination = 70;
        command = Command.noCommand;
    }
    
    public void DoGotoCook() {
        xDestination = 420;
        yDestination = 150;
        command = Command.atCook;
    }
    
    public void DoGoToTable(int table, String choice) {
    	xDestination = 50*table + 20;
		yDestination = 120 - 20;
		if(!choice.equals("none")) {
			Order order = new Order(choice);
			orders.add(order);
			order.State = state.atTable;
		}
		command = Command.LeaveTable;
    }
    
    public void DoGoToTableAgain(int table) {
    	xDestination = 50*table + 20;
		yDestination = 120 - 20;
		command = Command.LeaveTableAgain;
    }
    
    public void GotoBreak() {
    	xDestination = 400;
    	yDestination = 30;
    } 
    
    public void GoToCashier() {
    	xDestination = 20;
    	yDestination = 190;
    	command = Command.atCashier;
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
    
    public enum state {moving, atTable};
    class Order {
    	String choice;
    	public state State = state.moving;
    	
    	public Order(String choice) {
    		this.choice = choice;
    	}
    }
}
