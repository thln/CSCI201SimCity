package application.gui.animation;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.gui.animation.agentGui.*;

public class BankPanel extends BuildingPanel implements ActionListener{
	
	public BankPanel(String buildName, AnimationPanel ap) {
		super(buildName, ap);
		// TODO Auto-generated constructor stub
	}
	
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;
		
		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(new Color(184, 229, 227));

		g2.fillRect(0, 0, 600, 325 );	
		g2.setColor(Color.RED);

		if(name == "name")
			g.drawString("", 600/2, 10);
		else
			g.drawString(name, 600/2, 10);

		//different layouts based on their type       
		g2.setColor(Color.BLACK);
			
			//teller window
			g2.fillRect(600 - 100, 0, 20, 325*3/4);
			//waiting line
			g2.fillRect(600 - 175, 50, 5, 325/2);
			g2.drawString("Queue", 600 - 200, 50);
			//chair to sit at loan officer
			g2.fillRect(600/4, 325/2, 10, 60); //back of chair
			g2.fillRect(600/4 -20, 325/2, 20, 10);
			g2.fillRect(600/4 -20, 325/2+50, 20, 10);

			for (int j=1; j<5;j++) {
				g2.setColor(Color.BLACK);
				g2.fillRect(600 - 100, 50*j, 100, 10); //teller windows
				g2.setColor(Color.RED);
				g2.drawString(""+j,600 - 95, 50*j);
			}

			//Vault
			g2.setColor(Color.CYAN);
			g2.fillRect(10, 10, 150, 100);
			g2.setColor(Color.BLACK);
			g2.drawString("Bank Vault", 50, 50);

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
}
