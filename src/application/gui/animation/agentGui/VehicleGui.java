package application.gui.animation.agentGui;

import java.awt.Rectangle;

import agent.Agent;
import application.Phonebook;

public abstract class VehicleGui extends CityGui {

	protected Agent agent = null;
	protected boolean isPresent = true;
	protected int xPos, yPos;
	protected int xDestination, yDestination;

	Rectangle me = new Rectangle();

	protected enum Command {noCommand, block1, block2, block3, block4, block5, block6, block7, block8, block9, block10};
	protected Command command = Command.noCommand;

	protected enum VehicleState {stopped, enroute, inIntersection1, inIntersection2, inIntersection3, inIntersection4, inCrosswalk1, inCrosswalk2, inCrosswalk3, inCrosswalk4, inCrosswalk5,inCrosswalk6, inCrosswalk7, inCrosswalk8, inCrosswalk9, inCrosswalk10, inCrosswalk11, inCrosswalk12, inBusParking1H,inBusParking2H,inBusParking3H,inBusParking4H,inBusParking1V,inBusParking2V,inBusParking3V, inBusParking4V};
	VehicleState crosswalkState = VehicleState.stopped;
	VehicleState intersectionState = VehicleState.stopped;
	VehicleState busParkingState = VehicleState.stopped;


	public VehicleGui(){

	}

	//Actions
	public void goToEndOfTopRoad() {
		xDestination = 600;
	}

	public void goToEndOfBottomRoad() {
		xDestination = -50;
	}

	public void goToEndOfLeftRoad() {
		yDestination = -50;
	}

	public void goToEndOfRightRoad() {
		yDestination = 350;
	}

	public void changeRoads() {
	}


	synchronized public void inAnIntersection() {
		me.setLocation(xPos, yPos);
		if (Phonebook.getPhonebook().intersection1.getIntersection().intersects(me) &&
				!(intersectionState == VehicleState.inIntersection1)) {
			Phonebook.getPhonebook().intersection1.setIntersectionBusy(true);	
			intersectionState = VehicleState.inIntersection1;
		}
		else if (Phonebook.getPhonebook().intersection2.getIntersection().intersects(me) &&
				!(intersectionState == VehicleState.inIntersection2)) {
			Phonebook.getPhonebook().intersection2.setIntersectionBusy(true);	
			intersectionState = VehicleState.inIntersection2;
		}
		else if (Phonebook.getPhonebook().intersection3.getIntersection().intersects(me) &&
				!(intersectionState == VehicleState.inIntersection3)) {
			Phonebook.getPhonebook().intersection3.setIntersectionBusy(true);	
			intersectionState = VehicleState.inIntersection3;
		}
		else if (Phonebook.getPhonebook().intersection4.getIntersection().intersects(me) &&
				!(intersectionState == VehicleState.inIntersection4)) {
			Phonebook.getPhonebook().intersection4.setIntersectionBusy(true);	
			intersectionState = VehicleState.inIntersection4;			
		}
	}

	synchronized public void leftAnIntersection() {
		me.setLocation(xPos, yPos);
		if (!Phonebook.getPhonebook().intersection1.getIntersection().intersects(me)
				&& (intersectionState == VehicleState.inIntersection1)) {
			Phonebook.getPhonebook().intersection1.setIntersectionBusy(false);	
			intersectionState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().intersection2.getIntersection().intersects(me)
				&& (intersectionState == VehicleState.inIntersection2)) {
			Phonebook.getPhonebook().intersection2.setIntersectionBusy(false);	
			intersectionState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().intersection3.getIntersection().intersects(me) 
				&& (intersectionState == VehicleState.inIntersection3)) {
			Phonebook.getPhonebook().intersection3.setIntersectionBusy(false);	
			intersectionState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().intersection4.getIntersection().intersects(me)
				&& (intersectionState == VehicleState.inIntersection4)) {
			Phonebook.getPhonebook().intersection4.setIntersectionBusy(false);	
			intersectionState = VehicleState.enroute;	
		}
	}


	synchronized public void inACrosswalk() {
		me.setLocation(xPos, yPos);
		if (Phonebook.getPhonebook().crosswalk1.getCrosswalk().intersects(me) &&
				!(crosswalkState == VehicleState.inCrosswalk1)) {
//			System.err.println(this + "entering crosswalk 1");
			Phonebook.getPhonebook().crosswalk1.setCrosswalkBusy(true);	
			crosswalkState = VehicleState.inCrosswalk1;
		}
		else if (Phonebook.getPhonebook().crosswalk2.getCrosswalk().intersects(me) &&
				!(crosswalkState == VehicleState.inCrosswalk2)) {
			Phonebook.getPhonebook().crosswalk2.setCrosswalkBusy(true);	
			crosswalkState = VehicleState.inCrosswalk2;
		}	
		else if (Phonebook.getPhonebook().crosswalk3.getCrosswalk().intersects(me) &&
				!(crosswalkState == VehicleState.inCrosswalk3)) {
			Phonebook.getPhonebook().crosswalk3.setCrosswalkBusy(true);	
			crosswalkState = VehicleState.inCrosswalk3;
		}
		else if (Phonebook.getPhonebook().crosswalk4.getCrosswalk().intersects(me) &&
				!(crosswalkState == VehicleState.inCrosswalk4)) {
			Phonebook.getPhonebook().crosswalk4.setCrosswalkBusy(true);	
			crosswalkState = VehicleState.inCrosswalk4;
		}
		else if (Phonebook.getPhonebook().crosswalk5.getCrosswalk().intersects(me) &&
				!(crosswalkState == VehicleState.inCrosswalk5)) {
			Phonebook.getPhonebook().crosswalk5.setCrosswalkBusy(true);	
			crosswalkState = VehicleState.inCrosswalk5;
		}
		else if (Phonebook.getPhonebook().crosswalk6.getCrosswalk().intersects(me) &&
				!(crosswalkState == VehicleState.inCrosswalk6)) {
			Phonebook.getPhonebook().crosswalk6.setCrosswalkBusy(true);	
			crosswalkState = VehicleState.inCrosswalk6;
		}
		else if (Phonebook.getPhonebook().crosswalk7.getCrosswalk().intersects(me) &&
				!(crosswalkState == VehicleState.inCrosswalk7)) {
			Phonebook.getPhonebook().crosswalk7.setCrosswalkBusy(true);	
			crosswalkState = VehicleState.inCrosswalk7;
		}
		else if (Phonebook.getPhonebook().crosswalk8.getCrosswalk().intersects(me) &&
				!(crosswalkState == VehicleState.inCrosswalk8)) {
			Phonebook.getPhonebook().crosswalk8.setCrosswalkBusy(true);	
			crosswalkState = VehicleState.inCrosswalk8;
		}
		else if (Phonebook.getPhonebook().crosswalk9.getCrosswalk().intersects(me) &&
				!(crosswalkState == VehicleState.inCrosswalk9)) {
			Phonebook.getPhonebook().crosswalk9.setCrosswalkBusy(true);	
			crosswalkState = VehicleState.inCrosswalk9;
		}
		else if (Phonebook.getPhonebook().crosswalk10.getCrosswalk().intersects(me) &&
				!(crosswalkState == VehicleState.inCrosswalk10)) {
			Phonebook.getPhonebook().crosswalk10.setCrosswalkBusy(true);	
			crosswalkState = VehicleState.inCrosswalk10;
		}
		else if (Phonebook.getPhonebook().crosswalk11.getCrosswalk().intersects(me) &&
				!(crosswalkState == VehicleState.inCrosswalk11)) {
			Phonebook.getPhonebook().crosswalk11.setCrosswalkBusy(true);	
			crosswalkState = VehicleState.inCrosswalk11;
		}
		else if (Phonebook.getPhonebook().crosswalk12.getCrosswalk().intersects(me) &&
				!(crosswalkState == VehicleState.inCrosswalk12)) {
			Phonebook.getPhonebook().crosswalk12.setCrosswalkBusy(true);	
			crosswalkState = VehicleState.inCrosswalk12;
		}
	}

	synchronized public void leftACrosswalk() {
		me.setLocation(xPos, yPos);
		if (!Phonebook.getPhonebook().crosswalk1.getCrosswalk().intersects(me)
				&& (crosswalkState == VehicleState.inCrosswalk1)) {
//			System.err.println(this + "leaving crosswalk 1");
			Phonebook.getPhonebook().crosswalk1.setCrosswalkBusy(false);	
			crosswalkState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk2.getCrosswalk().intersects(me)
				&& (crosswalkState == VehicleState.inCrosswalk2)) {
			Phonebook.getPhonebook().crosswalk2.setCrosswalkBusy(false);	
			crosswalkState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk3.getCrosswalk().intersects(me)
				&& (crosswalkState == VehicleState.inCrosswalk3)) {
			Phonebook.getPhonebook().crosswalk3.setCrosswalkBusy(false);	
			crosswalkState = VehicleState.enroute;
		}
		else if (!Phonebook.getPhonebook().crosswalk4.getCrosswalk().intersects(me)
				&& (crosswalkState == VehicleState.inCrosswalk4)) {
			Phonebook.getPhonebook().crosswalk4.setCrosswalkBusy(false);	
			crosswalkState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk5.getCrosswalk().intersects(me)
				&& (crosswalkState == VehicleState.inCrosswalk5)) {
			Phonebook.getPhonebook().crosswalk5.setCrosswalkBusy(false);	
			crosswalkState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk6.getCrosswalk().intersects(me)
				&& (crosswalkState == VehicleState.inCrosswalk6)) {
			Phonebook.getPhonebook().crosswalk6.setCrosswalkBusy(false);	
			crosswalkState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk7.getCrosswalk().intersects(me)
				&& (crosswalkState == VehicleState.inCrosswalk7)) {
			Phonebook.getPhonebook().crosswalk7.setCrosswalkBusy(false);	
			crosswalkState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk8.getCrosswalk().intersects(me)
				&& (crosswalkState == VehicleState.inCrosswalk8)) {
			Phonebook.getPhonebook().crosswalk8.setCrosswalkBusy(false);	
			crosswalkState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk9.getCrosswalk().intersects(me)
				&& (crosswalkState == VehicleState.inCrosswalk9)) {
			Phonebook.getPhonebook().crosswalk9.setCrosswalkBusy(false);	
			crosswalkState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk10.getCrosswalk().intersects(me)
				&& (crosswalkState == VehicleState.inCrosswalk10)) {
			Phonebook.getPhonebook().crosswalk10.setCrosswalkBusy(false);	
			crosswalkState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk11.getCrosswalk().intersects(me)
				&& (crosswalkState == VehicleState.inCrosswalk11)) {
			Phonebook.getPhonebook().crosswalk11.setCrosswalkBusy(false);	
			crosswalkState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk12.getCrosswalk().intersects(me)
				&& (crosswalkState == VehicleState.inCrosswalk12)) {
			Phonebook.getPhonebook().crosswalk12.setCrosswalkBusy(false);	
			crosswalkState = VehicleState.enroute;	
		}
	}


	synchronized public void inBusParking() {
		me.setLocation(xPos, yPos);
		if (Phonebook.getPhonebook().busParking1H.getBusParking().intersects(me) &&
				!(busParkingState == VehicleState.inBusParking1H)) {
			Phonebook.getPhonebook().busParking1H.setBusParkingBusy(true);	
			busParkingState = VehicleState.inBusParking1H;
		}
		else if (Phonebook.getPhonebook().busParking2H.getBusParking().intersects(me) &&
				!(busParkingState == VehicleState.inBusParking2H)) {
			Phonebook.getPhonebook().busParking2H.setBusParkingBusy(true);	
			busParkingState = VehicleState.inBusParking2H;
		}	
		else if (Phonebook.getPhonebook().busParking3H.getBusParking().intersects(me) &&
				!(busParkingState == VehicleState.inBusParking3H)) {
			Phonebook.getPhonebook().busParking3H.setBusParkingBusy(true);	
			busParkingState = VehicleState.inBusParking3H;
		}
		else if (Phonebook.getPhonebook().busParking4H.getBusParking().intersects(me) &&
				!(busParkingState == VehicleState.inBusParking4H)) {
			Phonebook.getPhonebook().busParking4H.setBusParkingBusy(true);	
			busParkingState = VehicleState.inBusParking4H;
		}
		else if (Phonebook.getPhonebook().busParking1V.getBusParking().intersects(me) &&
				!(busParkingState == VehicleState.inBusParking1V)) {
			Phonebook.getPhonebook().busParking1V.setBusParkingBusy(true);	
			busParkingState = VehicleState.inBusParking1V;
		}
		else if (Phonebook.getPhonebook().busParking2V.getBusParking().intersects(me) &&
				!(busParkingState == VehicleState.inBusParking2V)) {
			Phonebook.getPhonebook().busParking2V.setBusParkingBusy(true);	
			busParkingState = VehicleState.inBusParking2V;
		}	
		else if (Phonebook.getPhonebook().busParking3V.getBusParking().intersects(me) &&
				!(busParkingState == VehicleState.inBusParking3V)) {
			Phonebook.getPhonebook().busParking3V.setBusParkingBusy(true);	
			busParkingState = VehicleState.inBusParking3V;
		}
		else if (Phonebook.getPhonebook().busParking4V.getBusParking().intersects(me) &&
				!(busParkingState == VehicleState.inBusParking4V)) {
			Phonebook.getPhonebook().busParking4V.setBusParkingBusy(true);	
			busParkingState = VehicleState.inBusParking4V;
		}
	}

	synchronized public void leftBusParking() {
		me.setLocation(xPos, yPos);
		if (!Phonebook.getPhonebook().busParking1H.getBusParking().intersects(me)
				&& (busParkingState == VehicleState.inBusParking1H)) {
			Phonebook.getPhonebook().busParking1H.setBusParkingBusy(false);	
		}
		else if (!Phonebook.getPhonebook().busParking2H.getBusParking().intersects(me)
				&& (busParkingState == VehicleState.inBusParking2H)) {
			Phonebook.getPhonebook().busParking2H.setBusParkingBusy(false);	
			busParkingState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().busParking3H.getBusParking().intersects(me)
				&& (busParkingState == VehicleState.inBusParking3H)) {
			Phonebook.getPhonebook().busParking3H.setBusParkingBusy(false);	
			busParkingState = VehicleState.enroute;
		}
		else if (!Phonebook.getPhonebook().busParking4H.getBusParking().intersects(me)
				&& (busParkingState == VehicleState.inBusParking4H)) {
			Phonebook.getPhonebook().busParking4H.setBusParkingBusy(false);	
			busParkingState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().busParking1V.getBusParking().intersects(me)
				&& (busParkingState == VehicleState.inBusParking1V)) {
			Phonebook.getPhonebook().busParking1V.setBusParkingBusy(false);	
			busParkingState = VehicleState.enroute;
		}
		else if (!Phonebook.getPhonebook().busParking2V.getBusParking().intersects(me)
				&& (busParkingState == VehicleState.inBusParking2V)) {
			Phonebook.getPhonebook().busParking2V.setBusParkingBusy(false);	
			busParkingState = VehicleState.enroute;	
		}
		else if (!Phonebook.getPhonebook().busParking3V.getBusParking().intersects(me)
				&& (busParkingState == VehicleState.inBusParking3V)) {
			Phonebook.getPhonebook().busParking3V.setBusParkingBusy(false);	
			busParkingState = VehicleState.enroute;
		}
		else if (!Phonebook.getPhonebook().busParking4V.getBusParking().intersects(me)
				&& (busParkingState == VehicleState.inBusParking4V)) {
			Phonebook.getPhonebook().busParking4V.setBusParkingBusy(false);	
			busParkingState = VehicleState.enroute;
		}
	}
}
