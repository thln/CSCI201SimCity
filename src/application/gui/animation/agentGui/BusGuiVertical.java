package application.gui.animation.agentGui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import transportation.BusAgent;
import application.Phonebook;

public class BusGuiVertical extends VehicleGui {

	private BusAgent agent = null;
	private boolean isPresent = true;
	private boolean checkedStation = false;
	ImageIcon busUp = new ImageIcon("res/busUp.png");
	ImageIcon busDown = new ImageIcon("res/busDown.png");

	private final int stopTopY = (int) Phonebook.getPhonebook().getAllBusStops().get(1).getBusStopLocation().getY()+8;
	private final int stopBottomY = (int) Phonebook.getPhonebook().getAllBusStops().get(4).getBusStopLocation().getY()+8;
    private final int stopLeftX = 168;
    private final int stopRightX = 395;

	private final int waitTime = 1500;

	private int yDestination = stopBottomY;//Stop 4
	private int xDestination = xPos;
	
    private final int offScreenTopLeftRoad = -50;
    private final int offScreenBottomRightRoad = 350;

	private enum Command {noCommand, wait, stop1, stop2, stop3, stop4};
	private Command command = Command.stop4;
	public int lastStop = 3;

	private Timer busStop = new Timer();

	public BusGuiVertical(BusAgent bus){
		agent = bus;
		xPos = stopLeftX;
		yPos = offScreenBottomRightRoad;
		me.setSize(busUp.getIconWidth(), busDown.getIconHeight()-5);
		//me.setSize(25, 25);
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

	//	System.out.println("My posisiton = " + xPos + " , " + yPos + "and destination = " + xDestination + " , " + yDestination );
		if (yPos == yDestination) {
			if (command == Command.stop1) {
				command = Command.wait;
//				System.err.println("Arrived at bus stop 1");
				busStop.schedule(new TimerTask() {
					public void run() {
						agent.msgAtBusStop(1);
						lastStop = 1;
					}
				},
				waitTime);
			}
			else if (command == Command.stop2) {
				command = Command.wait;
				busStop.schedule(new TimerTask() {
					public void run() {
						agent.msgAtBusStop(2);
						lastStop = 2;
					}
				},
				waitTime);
			}
			else if (command == Command.stop3) {
				command = Command.wait;
				busStop.schedule(new TimerTask() {
					public void run() {
						agent.msgAtBusStop(3);	
						lastStop = 3;
					}
				},
				waitTime);
			}
			else if (command == Command.stop4) {
				command = Command.wait;
				busStop.schedule(new TimerTask() {
					public void run() {
						agent.msgAtBusStop(4);
						lastStop = 4;
					}
				},
				waitTime);
			}
		}
		else {
			if(agent.getCheckedStation()) {
				agent.msgLeavingStation();
			}
		}
	}

	public void draw(Graphics2D g) {
		if (xPos == stopLeftX) {
			g.drawImage(busUp.getImage(), xPos, yPos, null);
		}
		else {
			g.drawImage(busDown.getImage(), xPos, yPos, null);
		}

		g.setColor(Color.GRAY);
		if(xPos == stopLeftX && yPos == stopTopY) {
			g.fillRect(xPos-4, yPos+4, 5, 20);
		}
		else if(xPos == stopRightX && yPos == stopTopY) {
			g.fillRect(xPos+20, yPos+4, 5, 20);
		}

		else if(xPos == stopLeftX && yPos == stopBottomY) {
			g.fillRect(xPos-4, yPos+4, 5, 20);
		}
		else if(xPos == stopRightX && yPos == stopBottomY) {
			g.fillRect(xPos+20, yPos+4, 5, 20);
		}
	}

	//Actions
	public void goToNextBusStop() {
		if (lastStop == 1) {
			goToEndOfLeftRoad();
			return;
		}
		if (lastStop == 2) {
			goToStop3();
			return;
		}
		if (lastStop == 3) {
			goToEndOfRightRoad();
			return;
		}
		if (lastStop == 4) {
			goToStop1();
			return;
		}
	}

	public void goToStop1() {
		command = Command.stop1;
		yDestination = stopTopY;
	}

	public void goToStop2() {
		command = Command.stop2;
		yDestination = stopTopY;
	}

	public void goToStop3() {
		command = Command.stop3;
		yDestination = stopBottomY;
	}

	public void goToStop4() {
		command = Command.stop4;
		xDestination = stopLeftX;
		yDestination = stopBottomY;
	}

	public void goToEndOfRightRoad() {
		yDestination = offScreenBottomRightRoad;
	}

	public void goToEndOfLeftRoad() {
		yDestination = offScreenTopLeftRoad;
	}

	public void changeRoads() {
		if (yDestination == offScreenBottomRightRoad) {
			xPos = stopLeftX;
			goToStop4();
		}
		else {
			xPos = stopRightX;
			goToStop2();
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
