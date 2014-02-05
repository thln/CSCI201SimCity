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

public class HousingPanel extends BuildingPanel implements MouseListener{
	
	private String type = "";
	BufferedImage arrow = null;
	Rectangle arrowRect;
	ApartmentPanel apartment;
	
	public HousingPanel(String buildName, AnimationPanel ap) {
		super(buildName, ap);
		
		addMouseListener(this);
		try {
            arrow = ImageIO.read(new File("res/arrow.gif"));
        } catch (IOException e) {
        }
		arrowRect = new Rectangle(0, 325/2-50, arrow.getWidth(), arrow.getHeight());
	}
	
	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
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

		//different layouts based on their type       
		g2.setColor(Color.BLACK);
		//drawing the different rooms
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(0, 0, 150, 100); //bathroom
		g2.fillRect(0, 325-120, 200, 120); //living room

		//bed
		g2.fillRect(600 - 100, 325 - 120, 60, 100);
		g2.setColor(Color.PINK);
		g2.fillRect(600 - 100, 325 - 80, 60, 70);
		g2.setColor(Color.WHITE);
		g2.fillRect(600 - 90, 325 - 110, 40, 20);

		//KITCHEN/////
		//fridge
		g2.fillRect(600*3/4, 20, 40, 20);
		//dining room table
		g2.setColor(Color.ORANGE);
		g2.fillOval(600/2, 20, 60, 50);

		//labelings
		g2.setColor(Color.BLACK);
		g2.drawString("Bathroom", 20, 20);
		g2.drawString("Living Room", 20, 325 - 100);
		g2.drawString("Kitchen", 600 - 100, 130);
		g2.drawString("table", 600/2+20, 50);

		//stove
		g2.fillRect(600 - 100, 20, 60, 55);
		g2.setColor(Color.RED);
		g2.fillOval(600-90, 30, 15, 15);
		g2.fillOval(600-65, 30, 15, 15);
		g2.fillOval(600-90, 50, 15, 15);
		g2.fillOval(600-65, 50, 15, 15);

		if(type.toLowerCase().contains("apartment")) {
			g2.drawImage(arrow, 0, 325/2-50, null);
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
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setAptBuilding(ApartmentPanel ap) {
		apartment = ap;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(arrowRect.contains(e.getX(), e.getY())) {
			apartment.displayBuildingPanel();
		}
	}
	
	public void addGui(Gui gui) {
		guis.add(gui);
	}

	public void mousePressed(MouseEvent e) { }

	public void mouseReleased(MouseEvent e) { }

	public void mouseEntered(MouseEvent e) { }

	public void mouseExited(MouseEvent e) { }
	
}
