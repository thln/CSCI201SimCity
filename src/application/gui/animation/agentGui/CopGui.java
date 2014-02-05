package application.gui.animation.agentGui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import application.Phonebook;

public class CopGui extends VehicleHorizontalGui {

	ImageIcon carLeft = new ImageIcon("res/CopCarLeft.png");
	ImageIcon carRight = new ImageIcon("res/CopCarRight.png");

	 private final int TopRoadY = 75;
     private final int BottomRoadY = 195;

	public CopGui() {
		xPos = 600;
		yPos = BottomRoadY;
		xDestination = -25;
	}

	public void updatePosition() {
		if (inBusyIntersection() || inBusyCrosswalk() || inBusyBusParking()) {
			return;
		}

		if (xPos < xDestination)
			xPos++;
		else if (xPos > xDestination)
			xPos--;		

		inAnIntersection();
		inACrosswalk();
		inBusParking();
		leftAnIntersection();
		leftACrosswalk();
		leftBusParking();

		if (xPos == 600 || xPos == -25) {
			changeRoads();
		}

		if (xPos == xDestination) {
			if (command == Command.block1) {
				//Do something
			}
		}
	}

	public void draw(Graphics2D g) {
		if (xPos%20 == 0 || xPos%20 == 1 || xPos%20 == 2 ||
				xPos%20 == 3 || xPos%20 == 4 || xPos%20 == 5 ||
				xPos%20 == 6 || xPos%20 == 7 || xPos%20 == 8 ||
				xPos%20 == 9 || xPos%20 == 10) {
			g.setColor(Color.RED);
			g.fillRect(xPos+18, yPos-3, 10, 5);
			g.setColor(Color.BLUE);
			g.fillRect(xPos+28, yPos-3, 10, 5);
		}
		else {
			g.setColor(Color.BLUE);
			g.fillRect(xPos+18, yPos-3, 10, 5);
			g.setColor(Color.RED);
			g.fillRect(xPos+28, yPos-3, 10, 5);
		}
			
		if (yPos == TopRoadY) {
			g.drawImage(carRight.getImage(), xPos, yPos, null);
		}
		else if (yPos == BottomRoadY) {
			g.drawImage(carLeft.getImage(), xPos, yPos, null);
		}
	}
}