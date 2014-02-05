package housing;

import java.awt.*;

import application.gui.animation.*;
import application.gui.animation.agentGui.*;
import person.Person;

public class Housing 
{
	//public enum differentHousingTypes {Apartment, Park, Mansion};
	//public static differentHousingTypes housingStructure;
	public String type;
	public String name;
	public enum housingState {UrgentWorkOrder, CheckUpNeeded, RecentlyChecked, Checking};
	public housingState state;
	public Person occupant;
	int housingNumber;
	int xCordinate;
	int yCordinate;
	boolean occupied = false;
	private HousingPanel housingPanel;
	
	public Housing(String name)
	{
		state = housingState.CheckUpNeeded;
		this.name = name;
	}
	
	public Housing(Person newP, int Address, String type)
	{
		occupant = newP;
		housingNumber = Address;
		state = housingState.CheckUpNeeded;
		this.type = type;
		/*
		if(type.toLowerCase().contains(("apartment")))
		{
			//housingStructure = differentHousingTypes.Apartment;
			type = "Apartment";
			//some X and Y coordinate stuff
		}
		else if(type.toLowerCase().contains(("park")))
		{
			//housingStructure = differentHousingTypes.Park;
			type = "Park";
			//some X and Y Coordinate stuff
		}
		else if(type.toLowerCase().contains(("mansion")))
		{
			//housingStructure = differentHousingTypes.Mansion;
			type = "Mansion";
			//some X and Y Coordinate stuff
		}
		*/
		
	}
	
	public boolean arrived(HousingResidentRole HRR) {
		HousingResidentGui HRG = new HousingResidentGui(HRR);
		HRR.setGui(HRG);
		housingPanel.addGui(HRG);
		return false;
	}
	
	//Utilities 
	
	public int getHousingNumber()
	{
		return housingNumber;
	}
	
	public int getXCordinate()
	{
		return xCordinate;
	}
	
	public int getYCordinate()
	{
		return yCordinate;
	}
	
	public String getOccupantName()
	{
		return occupant.getName();
	}
	
	public void setBuildingPanel(HousingPanel myBuildingPanel) {
		housingPanel = myBuildingPanel;
	}
	
	public HousingPanel getPanel() {
		return housingPanel;
	}
	
	public void setOccupied(boolean occupy) {
		occupied = occupy;
	}
	
	public boolean isOccupied() {
		if(occupied)
			return true;
		return false;
	}
}
