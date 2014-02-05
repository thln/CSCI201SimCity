package application.gui.animation;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;

import application.gui.animation.agentGui.*;

public class MarketPanel extends BuildingPanel implements ActionListener{
	
	BufferedImage register = null;
	
	public MarketPanel(String buildName, AnimationPanel ap) {
		super(buildName, ap);
		// TODO Auto-generated constructor stub
		try {
            register = ImageIO.read(new File("res/register.png"));
        	} catch (IOException e) {
        	}
	}
	
	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}

	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;

		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(new Color(255, 255, 204));

		g2.fillRect(0, 0, 600, 325 );	
		g2.setColor(Color.RED);

		if(name == "name")
			g.drawString("", 600/2, 10);
		else
			g.drawString(name, 600/2, 10);

		//different layouts based on their type       
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 80, 600 - 100, 10); //inventory
		
		//g2.fillRect(90, 125, 600/2+20, 10); 
		g2.setColor(new Color(255, 176, 102));
		g2.fillRect(100, 170, 310, 20); //table bottom
		g2.drawString("Inventory",600/2, 60 );
		
		g2.setColor(new Color(153, 76, 0));//table top
		int [] x = {50, 100, 400, 450};
		int [] y = {125, 170, 170, 125};
		g2.fillPolygon(x, y, 4);
		
		g2.setColor(new Color(51, 25, 0));//table sides
		int [] x2 = {50, 60, 100, 100};
		int [] y2 = {125, 160, 190, 170};
		g2.fillPolygon(x2, y2, 4);
		int [] x3 = {400, 400, 440, 450};
		int [] y3 = {170, 190, 160, 125};
		g2.fillPolygon(x3, y3, 4);
		
		g2.drawImage(register, 200, 100, null);
		
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
	
	public void addGui(Gui gui) {
		guis.add(gui); 
	}

	public void removeGui(Gui gui) {
		guis.remove(gui); 
	}
}