package application.gui.animation.agentGui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;

import transportation.BusAgent;
import application.Phonebook;
import application.gui.animation.agentGui.VehicleGui.VehicleState;

public class BusGuiHorizontal extends VehicleGui {

	private BusAgent agent = null;
	private boolean isPresent = true;
	private boolean checkedStation = false;	
	ImageIcon busLeft = new ImageIcon("res/busLeft.png");
	ImageIcon busRight = new ImageIcon("res/busRight.png");

	private final int stopLeftX = (int) Phonebook.getPhonebook().getAllBusStops().get(1).getBusStopLocation().getX()-15;
	private final int stopRightX = (int) Phonebook.getPhonebook().getAllBusStops().get(2).getBusStopLocation().getX();
	  private final int stopTopY = 75;
      private final int stopBottomY = 195;
      private final int offScreenLeftBottomRoad = -50;
      private final int offScreenRightTopRoad = 650;

	private final int waitTime = 1500;

	private int xDestination = stopLeftX;//Stop 1

	private enum Command {noCommand, wait, stop1, stop2, stop3, stop4};
	private Command command = Command.stop1;
	private int lastStop = 4;

	private Timer busStop = new Timer();
	private Semaphore wait = new Semaphore(0, true);

	public BusGuiHorizontal(BusAgent bus){
		agent = bus;
		me.setSize(busLeft.getIconWidth(), busLeft.getIconHeight());
		xPos = offScreenLeftBottomRoad;
		yPos = stopTopY;//default bus position
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
			if (command == Command.stop1) {
				command = Command.wait;
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
		else
		{
			if(agent.getCheckedStation())
			{
				agent.msgLeavingStation();
			}
			//checkedStation = false;
		}
	}

	public void draw(Graphics2D g) {
		if (yPos == stopTopY) {
			g.drawImage(busRight.getImage(), xPos, yPos, null);
		}
		else {
			g.drawImage(busLeft.getImage(), xPos, yPos, null);
		}

		g.setColor(Color.GRAY);
		if(xPos == stopLeftX && yPos == stopTopY) {
			g.fillRect(xPos+4, yPos-4, 20, 5);
		}
		else if(xPos == stopRightX && yPos == stopTopY) {
			g.fillRect(xPos+4, yPos-4, 20, 5);
		}

		else if (xPos == stopLeftX && yPos == stopBottomY) {
			g.fillRect(xPos+4, yPos+22, 20, 5);
		}
		else if(xPos == stopRightX && yPos == stopBottomY) {
			g.fillRect(xPos+4, yPos+22, 20, 5);
		}
	}

	//Actions
	public void goToNextBusStop() {
		if (lastStop == 1) {
			goToStop2();
			return;
		}
		if (lastStop == 2) {
			goToEndOfTopRoad();
			return;
		}
		if (lastStop == 3) {
			goToStop4();
			return;
		}
		if (lastStop == 4) {
			goToEndOfBottomRoad();
			return;
		}
	}

	public void goToStop1() {
		command = Command.stop1;
		xDestination = stopLeftX;
	}

	public void goToStop2() {
		command = Command.stop2;
		xDestination = stopRightX;
	}

	public void goToStop3() {
		command = Command.stop3;
		xDestination = stopRightX;
	}

	public void goToStop4() {
		command = Command.stop4;
		xDestination = stopLeftX;
	}

	public void goToEndOfTopRoad() {
		xDestination = offScreenRightTopRoad;
	}

	public void goToEndOfBottomRoad() {
		xDestination = offScreenLeftBottomRoad;
	}

	public void changeRoads() {
		if (xDestination == offScreenRightTopRoad) {
			yPos = stopBottomY;
			goToStop3();
		}
		else {
			yPos = stopTopY;
			goToStop1();
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
//				System.err.println(this+" I'm about to go into busy bus parking 1 true");
				return true;
			}
			return false;
		}

		me.setLocation(xPos+1, yPos);
		if (Phonebook.getPhonebook().busParking2H.getBusParking().intersects(me)) {
			if (Phonebook.getPhonebook().busParking2H.isBusParkingBusy() == true &&
					!(busParkingState == VehicleState.inBusParking2H)) {
				return  true;
			}
			return false;
		}

		me.setLocation(xPos-1, yPos);
		if (Phonebook.getPhonebook().busParking3H.getBusParking().intersects(me)) {
			if (Phonebook.getPhonebook().busParking3H.isBusParkingBusy() == true &&
					!(busParkingState == VehicleState.inBusParking3H)) {
				return  true;
			}
			return false;
		}

		me.setLocation(xPos-1, yPos);
		if (Phonebook.getPhonebook().busParking4H.getBusParking().intersects(me)) {
			if (Phonebook.getPhonebook().busParking4H.isBusParkingBusy() == true &&
					!(busParkingState == VehicleState.inBusParking4H)) {
				return  true;
			}
			return false;
		}
		else {
			return false;
		}
	}
}
