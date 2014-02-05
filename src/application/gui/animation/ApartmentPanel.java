package application.gui.animation;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import housing.*;
import application.gui.animation.agentGui.*;

public class ApartmentPanel extends BuildingPanel implements MouseListener{
	private List<Housing> housing = Collections.synchronizedList(new ArrayList<Housing>());
	private List<Rectangle> aptRects = Collections.synchronizedList(new ArrayList<Rectangle>());
	
	BufferedImage doorRight;
	BufferedImage doorLeft;
	BufferedImage wall;
	int xdoorPos = 600/2;
	int ydoorPos = 325-75;
	
	public ApartmentPanel(String buildName, AnimationPanel ap) {
		super(buildName, ap);
		
		addMouseListener(this);
		try {
            doorRight = ImageIO.read(new File("res/aptdoorR.png"));
        } 
		catch (IOException e) {
        }
		try {
            doorLeft = ImageIO.read(new File("res/aptdoorL.png"));
        } 
		catch (IOException e) {
        }
		try {
            wall = ImageIO.read(new File("res/brickwall.jpg"));
        } 
		catch (IOException e) {
        }
	}
	
	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		
		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(new Color(255, 229, 204));	
		
		g2.fillRect(0, 0, 600, 325 );	
		g2.setColor(Color.RED);

		if(name == "name")
			g.drawString("", 600/2, 10);
		else
			g.drawString(getName(), 600/2, 10);
		
		g2.setColor(new Color(139,69,19));
		
		g2.fill3DRect(0, ydoorPos-325*1/10-10, 600, 10, false);
		
		for(int w=0; w < 6; w++)
			//g2.drawImage(wall, 0 + wall.getWidth()*w, ydoorPos, null);
		g2.drawImage(wall, 0+600/6*w, ydoorPos-325*1/10, 600/6, doorLeft.getHeight()+325*1/10, null);
		g2.drawImage(doorRight, xdoorPos, ydoorPos, null);
		g2.drawImage(doorLeft, xdoorPos-doorLeft.getWidth(), ydoorPos, null);
		
        int rows = housing.size()/10;
        int cols = 10;
        int xremainder = housing.size() - cols*rows;
        int counter = 0;
        Font myFont = new Font("Times", Font.PLAIN, 12);
        
        //fills up a grid w/ specified rows and columns
		//xloc is x location, yloc is y location of apt
		for (int iy = 1; iy <= rows; iy++) {
        	for(int ix=1; ix <= cols; ix++) {
        		Housing currHouse = housing.get(counter);
        		if(currHouse.isOccupied())
        			g2.setColor(Color.RED);
        		else
        			g2.setColor(Color.ORANGE);
        		g2.fill3DRect(600/12*ix, 80*iy-50, 45, 45, true);
        		if(aptRects.get(counter).getX() == 0 && aptRects.get(counter).getY() == 0)
        			aptRects.get(counter).setLocation(600/12*ix, 80*iy-50);
        		if(currHouse.isOccupied())
        			g2.setColor(Color.WHITE);
        		else
        			g2.setColor(Color.BLACK);
        		g2.drawString(counter+"", 600/12*ix+600/120, 80*iy-35);
        		g2.setFont(myFont);
       			g2.drawString(currHouse.getOccupantName(), 600/12*ix+600/120, 80*iy-15);
       			counter++;
        	}
        }
		//creates the remainder apartments
        for(int ix=1; ix<= xremainder; ix++) {
        	Housing currHouse = housing.get(counter);
    		if(currHouse.isOccupied())
    			g2.setColor(Color.RED);
    		else
    			g2.setColor(Color.ORANGE);
        	//g2.fillRect(50*ix, 80*(rows+1)-50, 45, 45);
    		g2.fill3DRect(600/12*ix, 80*(rows+1)-50, 45, 45, true);
    		if(aptRects.get(counter).getX() == 0 && aptRects.get(counter).getY() == 0)
    			aptRects.get(counter).setLocation(600/12*ix, 80*(rows+1)-50);
    		if(currHouse.isOccupied())
    			g2.setColor(Color.WHITE);
    		else
    			g2.setColor(Color.BLACK);
        	g2.drawString(counter+"", 600/12*ix+600/120, 80*(rows+1)-35);
        	g2.setFont(myFont);
       		g2.drawString(currHouse.getOccupantName(), 600/12*ix+600/120, 80*(rows+1)-15);
       		counter++;
        }
        
        synchronized(guis){
			for(Gui gui : guis) {
				if (gui.isPresent()) {
					gui.updatePosition();
				}
			}

			for(Gui gui : guis) {
				if (gui.isPresent()) {
					gui.draw(g2);
				}
			}
			
		}
	}
	
	public void displayBuildingPanel() {
		myCity.displayBuildingPanel(this);
	}
	
	public void addAptUnit(Housing house) {
		HousingPanel hp = new HousingPanel(house.getOccupantName() + "'s House", myCity);
		hp.setType("apartment");
		hp.setAptBuilding(this);
		
		housing.add(house);
		house.setBuildingPanel(hp);
		myCity.addBuildingPanel(hp);
		aptRects.add(new Rectangle(0, 0, 45, 45));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//If any of the houseIcons are clicked, it will display the appropriate panel
		for(int i=0; i<aptRects.size();i++) {
			if(aptRects.get(i).contains(e.getX(), e.getY()) ) {
				housing.get(i).getPanel().displayBuildingPanel();
			}
		}
	}
	
	public List<Housing> getAptUnits() {
		return housing;
	}

	public void mousePressed(MouseEvent e) { }

	public void mouseReleased(MouseEvent e) { }

	public void mouseEntered(MouseEvent e) { }

	public void mouseExited(MouseEvent e) { }
	
}
