package application.gui.animation.agentGui;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import application.Phonebook;

public class VehicleHorizontalGui extends VehicleGui {

	ImageIcon carLeft = new ImageIcon("res/CarLeft.png");
	ImageIcon carRight = new ImageIcon("res/CarRight.png");

	private final int TopRoadY = 75;
    private final int BottomRoadY = 195;
    private final int offScreenLeftBottomRoad = -50;
    private final int offScreenRightTopRoad = 650;

	public VehicleHorizontalGui() {
		xPos = offScreenLeftBottomRoad;
		yPos = TopRoadY;
		xDestination = 600;
		xDestination = offScreenRightTopRoad;
		me.setSize(carLeft.getIconWidth(), carLeft.getIconHeight());
		//me.setSize(25, 25);
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

		if (xPos == offScreenRightTopRoad || xPos == offScreenLeftBottomRoad) {
			changeRoads();
		}

		if (xPos == xDestination) {
			if (command == Command.block1) {
				//Do something
			}
		}
	}

	public void draw(Graphics2D g) {
		if (yPos == TopRoadY) {
			g.drawImage(carRight.getImage(), xPos, yPos, null);
		}
		else if (yPos == BottomRoadY) {
			g.drawImage(carLeft.getImage(), xPos, yPos, null);
		}
	}

	public void changeRoads() {
		if (xDestination == offScreenRightTopRoad) {
			yPos = BottomRoadY;
			xDestination = offScreenLeftBottomRoad;
		}
		else {
			yPos = TopRoadY;
			xDestination = offScreenRightTopRoad;
		}
	}

	synchronized public boolean inBusyIntersection() {
		me.setLocation(xPos+1, yPos);

		if (Phonebook.getPhonebook().intersection1.getIntersection().intersects(me)) {
			if (Phonebook.getPhonebook().intersection1.isIntersectionBusy() == true &&
					!(intersectionState == VehicleState.inIntersection1)) {
				return  true;
			}
			return false;
		}

		me.setLocation(xPos+1, yPos);
		if (Phonebook.getPhonebook().intersection2.getIntersection().intersects(me)) {
			if (Phonebook.getPhonebook().intersection2.isIntersectionBusy() == true &&
					!(intersectionState == VehicleState.inIntersection2)) {
				return  true;
			}
			return false;
		}

		me.setLocation(xPos-1, yPos);
		if (Phonebook.getPhonebook().intersection3.getIntersection().intersects(me)) {
			if (Phonebook.getPhonebook().intersection3.isIntersectionBusy() == true &&
					!(intersectionState == VehicleState.inIntersection3)) {
				return  true;
			}
			return false;
		}

		me.setLocation(xPos-1, yPos);
		if (Phonebook.getPhonebook().intersection4.getIntersection().intersects(me)) {
			if (Phonebook.getPhonebook().intersection4.isIntersectionBusy() == true &&
					!(intersectionState == VehicleState.inIntersection4)) {
				return  true;
			}
			return false;
		}
		else {
			return false;
		}
	}

	synchronized public boolean inBusyCrosswalk() {

		me.setLocation(xPos+1, yPos);
		if (Phonebook.getPhonebook().crosswalk3.getCrosswalk().intersects(me)) {
			if (Phonebook.getPhonebook().crosswalk3.isCrosswalkBusy() == true &&
					!(crosswalkState == VehicleState.inCrosswalk3)) {
				return true;
			}
			return false;
		}

		me.setLocation(xPos+1, yPos);
		if (Phonebook.getPhonebook().crosswalk4.getCrosswalk().intersects(me)) {
			if (Phonebook.getPhonebook().crosswalk4.isCrosswalkBusy() == true &&
					!(crosswalkState == VehicleState.inCrosswalk4)) {
				return true;
			}
			return false;
		}

		me.setLocation(xPos+1, yPos);
		if (Phonebook.getPhonebook().crosswalk5.getCrosswalk().intersects(me)) {
			if (Phonebook.getPhonebook().crosswalk5.isCrosswalkBusy() == true &&
					!(crosswalkState == VehicleState.inCrosswalk5)) {
				return true;
			}
			return false;
		}

		me.setLocation(xPos-1, yPos);
		if (Phonebook.getPhonebook().crosswalk8.getCrosswalk().intersects(me)) {
			if (Phonebook.getPhonebook().crosswalk8.isCrosswalkBusy() == true &&
					!(crosswalkState == VehicleState.inCrosswalk8)) {
				return true;
			}
			return false;
		}

		me.setLocation(xPos-1, yPos);
		if (Phonebook.getPhonebook().crosswalk9.getCrosswalk().intersects(me)) {
			if (Phonebook.getPhonebook().crosswalk9.isCrosswalkBusy() == true &&
					!(crosswalkState == VehicleState.inCrosswalk9)) {
				return true;
			}
			return false;
		}

		me.setLocation(xPos-1, yPos);
		if (Phonebook.getPhonebook().crosswalk10.getCrosswalk().intersects(me)) {
			if (Phonebook.getPhonebook().crosswalk10.isCrosswalkBusy() == true &&
					!(crosswalkState == VehicleState.inCrosswalk10)) {
				return true;
			}
			return false;
		}
		else {
			return false;
		}
	}

	synchronized public boolean inBusyBusParking() {
		me.setLocation(xPos+1, yPos);
		if (Phonebook.getPhonebook().busParking1H.getBusParking().intersects(me)) {
			if (Phonebook.getPhonebook().busParking1H.isBusParkingBusy() == true &&
					!(busParkingState == VehicleState.inBusParking1H)) {
				//System.err.println(this+" I'm about to go into busy bus parking 1 true");
				return true;
			}
		//	System.err.println(this+" I'm about to go into busy bus parking 1 false");
			return false;
		}

		me.setLocation(xPos+1, yPos);
		if (Phonebook.getPhonebook().busParking2H.getBusParking().intersects(me)) {
			if (Phonebook.getPhonebook().busParking2H.isBusParkingBusy() == true &&
					!(busParkingState == VehicleState.inBusParking2H)) {
				//System.err.println(this+" I'm about to go into busy bus parking 2 true");
				return  true;
			}
			//System.err.println(this+" I'm about to go into busy bus parking 2 false");
			return false;
		}

		me.setLocation(xPos-1, yPos);
		if (Phonebook.getPhonebook().busParking3H.getBusParking().intersects(me)) {
			if (Phonebook.getPhonebook().busParking3H.isBusParkingBusy() == true &&
					!(busParkingState == VehicleState.inBusParking3H)) {
			//	System.err.println(this+" I'm about to go into busy bus parking 3 true");
				return  true;
			}
		//	System.err.println(this+" I'm about to go into busy bus parking 3 false");
			return false;
		}

		me.setLocation(xPos-1, yPos);
		if (Phonebook.getPhonebook().busParking4H.getBusParking().intersects(me)) {
			if (Phonebook.getPhonebook().busParking4H.isBusParkingBusy() == true &&
					!(busParkingState == VehicleState.inBusParking4H)) {
			//	System.err.println(this+" I'm about to go into busy bus parking 4 true");
				return  true;
			}
		//	System.err.println(this+" I'm about to go into busy bus parking 4 false");
			return false;
		}
		else {
			return false;
		}
	}
}
