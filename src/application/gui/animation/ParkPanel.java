package application.gui.animation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import application.gui.animation.agentGui.*;
import java.io.*;
import java.lang.Object.*;

public class ParkPanel extends BuildingPanel implements ActionListener{
	
	BufferedImage parkImage = null;

	public ParkPanel(String buildName, AnimationPanel ap) {
		super(buildName, ap);
		try{
			parkImage = ImageIO.read(new File("res/park.jpg"));
		} catch(IOException e){
			System.out.println("park image read error");
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		//Clear the screen by painting a rectangle the size of the frame
		//g2.setColor(new Color(215, 255, 204));
		g2.drawImage(parkImage, 0,0, 600, 325, null);
		//g2.fillRect(0, 0, 600, 325 );	
		
		g2.setColor(Color.RED);

		if(name == "name")
			g.drawString("", 600/2, 10);
		else
			g.drawString(name, 600/2, 10);
		
		//different layouts based on their type       
		/*g2.setColor(Color.BLACK);
		g2.drawString("Inventory",600/2, 60 );
		g2.fillRect(0, 80, 600 - 100, 10); //inventory
		g2.fillRect(90, 125, 600/2+20, 10); //table top
		g2.setColor(Color.CYAN);
		g2.fillRect(100, 135, 600/2, 40); //table bottom*/

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