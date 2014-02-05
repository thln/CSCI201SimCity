package application.gui.animation.agentGui;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import application.Phonebook;

public class VehicleVerticalGui extends VehicleGui {

	ImageIcon carUp = new ImageIcon("res/CarUp.png");
	ImageIcon carDown = new ImageIcon("res/CarDown.png");


    private final int leftRoadX = 168;
    private final int rightRoadX = 395;
    private final int offScreenTopLeftRoad = -50;
    private final int offScreenBottomRightRoad = 350;

	public VehicleVerticalGui() {
		xPos = rightRoadX;
		yPos = offScreenTopLeftRoad;
		yDestination = offScreenBottomRightRoad;
		me.setSize(carUp.getIconWidth(), carUp.getIconHeight());
	}

	public void updatePosition() {
		if (inBusyIntersection() || inBusyCrosswalk() || inBusyBusParking()) {
			return;
		}

		if (yPos < yDestination)
			yPos++;
		else if (yPos > yDestination)
			yPos--;

		inAnIntersection();
		inACrosswalk();	
		inBusParking();
		leftAnIntersection();
		leftACrosswalk();
		leftBusParking();


		if (yPos == offScreenBottomRightRoad || yPos == offScreenTopLeftRoad) {
			changeRoads();
		}
	}

	public void draw(Graphics2D g) {
		if (xPos == leftRoadX) {
			g.drawImage(carUp.getImage(), xPos, yPos, null);
		}
		else if (xPos == rightRoadX) {
			g.drawImage(carDown.getImage(), xPos, yPos, null);
		}
	}

	public void changeRoads() {
		if (yDestination == offScreenBottomRightRoad) {
			xPos = leftRoadX;
			yDestination = offScreenTopLeftRoad;
		}
		else {
			xPos = rightRoadX;
			yDestination = offScreenBottomRightRoad;
		}
	}

	synchronized public boolean inBusyIntersection() {
		me.setLocation(xPos, yPos-1);
		if (Phonebook.getPhonebook().intersection1.getIntersection().intersects(me)) {
			if (Phonebook.getPhonebook().intersection1.isIntersectionBusy() == true &&
					!(intersectionState == VehicleState.inIntersection1)) {
				return  true;
			}
			return false;
		}

		me.setLocation(xPos, yPos+1);
		if (Phonebook.getPhonebook().intersection2.getIntersection().intersects(me)) {
			if (Phonebook.getPhonebook().intersection2.isIntersectionBusy() == true &&
					!(intersectionState == VehicleState.inIntersection2)) {
				return  true;
			}
			return false;
		}

		me.setLocation(xPos, yPos-1);
		if (Phonebook.getPhonebook().intersection3.getIntersection().intersects(me)) {
			if (Phonebook.getPhonebook().intersection3.isIntersectionBusy() == true &&
					!(intersectionState == VehicleState.inIntersection3)) {
				return  true;
			}
			return false;
		}

		me.setLocation(xPos, yPos+1);
		if (Phonebook.getPhonebook().intersection4.getIntersection().intersects(me)) {
			if (Phonebook.getPhonebook().intersection4.isIntersectionBusy() == true &&
					!(intersectionState == VehicleState.inIntersection4)) {
				return true;
			}
			return false;
		}
		else {
			return false;
		}
	}

	synchronized public boolean inBusyCrosswalk() {
		me.setLocation(xPos, yPos-1);
		if (Phonebook.getPhonebook().crosswalk1.getCrosswalk().intersects(me)) {
			if (Phonebook.getPhonebook().crosswalk1.isCrosswalkBusy() == true &&
					!(crosswalkState == VehicleState.inCrosswalk1)) {
				return true;
			}
			return false;
		}

		me.setLocation(xPos, yPos+1);
		if (Phonebook.getPhonebook().crosswalk2.getCrosswalk().intersects(me)) {
			if (Phonebook.getPhonebook().crosswalk2.isCrosswalkBusy() == true &&
					!(crosswalkState == VehicleState.inCrosswalk2)) {
				return true;
			}
			return false;
		}

		me.setLocation(xPos, yPos-1);
		if (Phonebook.getPhonebook().crosswalk6.getCrosswalk().intersects(me)) {
			if (Phonebook.getPhonebook().crosswalk6.isCrosswalkBusy() == true &&
					!(crosswalkState == VehicleState.inCrosswalk6)) {
				return  true;
			}
			return false;
		}

		me.setLocation(xPos, yPos+1);
		if (Phonebook.getPhonebook().crosswalk7.getCrosswalk().intersects(me)) {
			if (Phonebook.getPhonebook().crosswalk7.isCrosswalkBusy() == true &&
					!(crosswalkState == VehicleState.inCrosswalk7)) {
				return  true;
			}
			return false;
		}

		me.setLocation(xPos, yPos-1);
		if (Phonebook.getPhonebook().crosswalk11.getCrosswalk().intersects(me)) {
			if (Phonebook.getPhonebook().crosswalk11.isCrosswalkBusy() == true &&
					!(crosswalkState == VehicleState.inCrosswalk11)) {
				return  true;
			}
			return false;
		}

		me.setLocation(xPos, yPos+1);
		if (Phonebook.getPhonebook().crosswalk12.getCrosswalk().intersects(me)) {
			if (Phonebook.getPhonebook().crosswalk12.isCrosswalkBusy() == true &&
					!(crosswalkState == VehicleState.inCrosswalk12)) {
				return  true;
			}
			return false;
		}
		else {
			return false;
		}
	}

	synchronized public boolean inBusyBusParking() {
		me.setLocation(xPos, yPos-1);
		if (Phonebook.getPhonebook().busParking1V.getBusParking().intersects(me)) {
			if (Phonebook.getPhonebook().busParking1V.isBusParkingBusy() == true &&
					!(busParkingState == VehicleState.inBusParking1V)) {
				return true;
			}
			return false;
		}

		me.setLocation(xPos, yPos+1);
		if (Phonebook.getPhonebook().busParking2V.getBusParking().intersects(me)) {
			if (Phonebook.getPhonebook().busParking2V.isBusParkingBusy() == true &&
					!(busParkingState == VehicleState.inBusParking2V)) {
				return true;
			}
			return false;
		}

		me.setLocation(xPos, yPos+1);
		if (Phonebook.getPhonebook().busParking3V.getBusParking().intersects(me)) {
			if (Phonebook.getPhonebook().busParking3V.isBusParkingBusy() == true &&
					!(busParkingState == VehicleState.inBusParking3V)) {
				return  true;
			}
			return false;
		}

		me.setLocation(xPos, yPos-1);
		if (Phonebook.getPhonebook().busParking4V.getBusParking().intersects(me)) {
			if (Phonebook.getPhonebook().busParking4V.isBusParkingBusy() == true &&
					!(busParkingState == VehicleState.inBusParking4V)) {
				return  true;
			}
			return false;
		}
		else {
			return false;
		}
	}
}
