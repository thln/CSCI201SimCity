package application.gui.animation;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

import application.Phonebook;
import housing.*;
import application.gui.animation.agentGui.*;

public class AnimationPanel extends JPanel implements MouseListener, ActionListener {

	/*
	Toolkit tk = Toolkit.getDefaultToolkit();
	int WINDOWX = ((int) tk.getScreenSize().getWidth())/2; 
	int WINDOWY = (((int) tk.getScreenSize().getHeight())/2)*5/6;   
	 */

	public CityPanel cityPanel;
	JPanel buildingPanels;
	CardLayout cardLayout;
	ArrayList<Building> buildings;
	List<BuildingPanel> buildingPanelsList = Collections.synchronizedList(new ArrayList<BuildingPanel>());

	//begin list of mechanisms for testing agent guis
	JButton testbutton = new JButton("test");
	JButton testbutton2 = new JButton("test2");
	MarketRunnerGui market = new MarketRunnerGui();
	BankLoanerGui bank = new BankLoanerGui();
	HousingMaintenanceGui house = new HousingMaintenanceGui();
	RestaurantCookGui restaurant = new RestaurantCookGui();
	ItalianCookGui italian = new ItalianCookGui();
	//BusGui bus = new BusGui();
	//CarGui car = new CarGui();
	//PersonGui person = new PersonGui();
	//end list of testing mechanisms

	public AnimationPanel() {
		setVisible(true);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Animation"));

		//here we have the main city view
		cityPanel = new CityPanel(this);
		cityPanel.setPreferredSize(new Dimension(600, 325));
		cityPanel.setMaximumSize(new Dimension(600,325));
		cityPanel.setMinimumSize(new Dimension(600, 325));
		cityPanel.setVisible(true);

		cardLayout = new CardLayout();

		buildingPanels = new JPanel();
		buildingPanels.setLayout(cardLayout);
		buildingPanels.setMinimumSize(new Dimension(600, 325));
		buildingPanels.setMaximumSize(new Dimension(600, 325));
		buildingPanels.setPreferredSize(new Dimension(600, 325));

		//Adding a blank building panel
		BuildingPanel blank = new BuildingPanel("", this);
		buildingPanels.add(blank, blank.getName());

		//Create the BuildingPanel for each Building object
		buildings = cityPanel.getBuildings();
		for ( int i=0; i<buildings.size(); i++ ) {
			Building b = buildings.get(i);
			String name = b.getName();
			BuildingPanel panel = null;
			if(name.toLowerCase().contains("restaurant")) {
				panel = new RestaurantPanel(name, this );
			}
			else if(name.toLowerCase().contains("market")) {
				panel = new MarketPanel(name, this );
			}
			else if(name.toLowerCase().contains("mansion")) {
				panel = new HousingPanel(name, this );
			}
			else if(name.toLowerCase().contains("apartment")) {
				panel = new ApartmentPanel(name, this);
			}
			else if(name.toLowerCase().contains("bank")) {
				panel = new BankPanel(name, this );
			}
			else if(name.toLowerCase().contains("park")){
				panel = new ParkPanel(name, this);
			}

			b.setMyBuildingPanel(panel);
			setBuildingInPhonebook(b);
			buildingPanels.add(panel, name);
			buildingPanelsList.add(panel);
			add(BorderLayout.NORTH, cityPanel);
			add(BorderLayout.SOUTH, buildingPanels);
			Timer paintTimer = new Timer(10, this);
			paintTimer.start();
		}
	}

	public void displayBuildingPanel(BuildingPanel bp) {
		cardLayout.show(buildingPanels, bp.getName());
	}

	public void displayBlankBuildingPanel() {
		cardLayout.show(buildingPanels, "");
	}

	public void setBuildingInPhonebook(Building building) {
		if(building.getName().toLowerCase().contains("restaurant")) {
			if (building.getName().toLowerCase().contains("chinese")) {
				Phonebook.getPhonebook().getChineseRestaurant().setBuildingPanel(building.myBuildingPanel);
			}
			if (building.getName().toLowerCase().contains("italian")) {
				Phonebook.getPhonebook().getItalianRestaurant().setBuildingPanel(building.myBuildingPanel);
			}
//			if (building.getName().toLowerCase().contains("american")) {
//				Phonebook.getPhonebook().getAmericanRestaurant().setBuildingPanel(building.myBuildingPanel);
//			}
		}
		if (building.getName().toLowerCase().contains("market")) {
			if(building.getName().toLowerCase().contains("east"))
				Phonebook.getPhonebook().getEastMarket().setBuildingPanel(building.myBuildingPanel);
			if(building.getName().toLowerCase().contains("west"))
				Phonebook.getPhonebook().getWestMarket().setBuildingPanel(building.myBuildingPanel);
		}
		if (building.getName().toLowerCase().contains("bank")) {
			if(building.getName().toLowerCase().contains("east"))
				Phonebook.getPhonebook().getEastBank().setBuildingPanel(building.myBuildingPanel);
			if(building.getName().toLowerCase().contains("west"))
				Phonebook.getPhonebook().getWestBank().setBuildingPanel(building.myBuildingPanel);
		}
		if (building.getName().toLowerCase().contains("apartment")) {
			if(building.getName().toLowerCase().contains("east"))
				Phonebook.getPhonebook().getEastApartment().setBuildingPanel(building.myBuildingPanel);
			if(building.getName().toLowerCase().contains("west"))
				Phonebook.getPhonebook().getWestApartment().setBuildingPanel(building.myBuildingPanel);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(testbutton)) {
			italian.DoCooking("Steak", 1);
		}
		if(e.getSource().equals(testbutton2)) {
			italian.DoPlateIt("Steak", 1);
		}
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public String toString() {
		return "Animation Panel";
	}

	public ArrayList<Building> getBuildings() {
		return buildings;
	}

	//to be used for the adding the people and transportation guis to the city Panel
	public void addGui(Gui gui) {
		if (gui instanceof CityGui) {
			cityPanel.addGui(gui);
		}
	}

	public void addBuildingPanel(BuildingPanel panel) {
		buildingPanels.add(panel, panel.name);
		buildingPanelsList.add(panel);
	}

	public int get600(){
		return 600;
	}
	public int get325(){
		return 325;
	}

	public void setHousingPanel(Housing housing) {
		if(housing.type.toLowerCase().contains("apartment")) {

			//assigning apartment unit to either east or west apartment
			for(Building building : buildings) {
				String name = building.getName();
				if(name.toLowerCase().contains("apartment")) {
					if(name.toLowerCase().contains("east")) {
						if(housing.type.toLowerCase().contains("east")) {
							ApartmentPanel Apt = (ApartmentPanel) building.myBuildingPanel;
							Apt.addAptUnit(housing);
						}
					}
					else if(name.toLowerCase().contains("west")) {
						if(housing.type.toLowerCase().contains("west")) {
							ApartmentPanel Apt = (ApartmentPanel) building.myBuildingPanel;
							Apt.addAptUnit(housing);
						}
					}
				}
			} //for
		}//end if
		else if(housing.type.toLowerCase().contains("mansion")) {
			for(Building building : buildings) {
				String name = building.getName();
				if(name.toLowerCase().contains("mansion")) {
					HousingPanel hp = (HousingPanel) building.myBuildingPanel;
					housing.setBuildingPanel(hp);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		synchronized(buildingPanelsList) {
			if(!buildingPanelsList.isEmpty()) {
				for(BuildingPanel building: buildingPanelsList) {
					if (building != null) {
						if(!building.isVisible()) {
							synchronized(building.guis) {
								for(Gui gui : building.guis) {
									gui.updatePosition();
								}
							}
						}
					}
				}
			}
		}
	}
}
