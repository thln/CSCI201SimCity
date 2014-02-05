package housing;

import java.awt.*;
import java.util.List;
import java.util.*;

import javax.swing.ImageIcon;

import person.Person;
import person.Worker;
import bank.BankTellerRole;
import application.gui.animation.*;
import application.gui.animation.agentGui.*;


public class Apartment {
	
	/*
	Toolkit tk = Toolkit.getDefaultToolkit();
	int WINDOWX = ((int) tk.getScreenSize().getWidth())/2; 
	int WINDOWY = (((int) tk.getScreenSize().getHeight())/2)*5/6;   
	*/
	ImageIcon apartment = new ImageIcon("res/apartment.png", "apartment");
	
	private Point location;
	private ApartmentPanel apartmentPanel;
	String name;
	List<Housing> aptUnits = new ArrayList<Housing>();
	
	public Apartment(String name) {
		this.name = name;
		if (name.equals("West Apartment"))
			location = new Point(600/6-apartment.getIconWidth(), 325/6-apartment.getIconHeight()/2);
		if (name.equals("East Apartment"))
			location = new Point(600*5/6, 325*5/6-apartment.getIconHeight()/2);
	}
	
	public boolean arrived(ApartmentResidentRole ARR) {
		if(ARR instanceof ApartmentResidentRole) {
			ARR.setApartment(this);
			aptUnits = apartmentPanel.getAptUnits();
			Housing house;
			for(int i=0;i<aptUnits.size();i++) {
				if(aptUnits.get(i).getOccupantName().equals(ARR.getName())) {
					house = aptUnits.get(i);
					ARR.setUnit(i, house);
					ApartmentResidentGui ARG = new ApartmentResidentGui(ARR);
					ARR.setGui(ARG);
					apartmentPanel.addGui(ARG);
					ARR.msgMoveToUnit();
					return true;
				}
			}
		}
		return false;
	}
	
	public void removeResident(ApartmentResidentRole role) {
		role.setPerson(null);
		role.getHome().setOccupied(false);
		role.setRoleInactive();
		apartmentPanel.removeGui(role.getGui());
	}
	
	public void setBuildingPanel(BuildingPanel myBuildingPanel) {
		apartmentPanel = (ApartmentPanel)myBuildingPanel;
	}
	
	public ApartmentPanel getPanel() {
		return apartmentPanel;
	}
}
