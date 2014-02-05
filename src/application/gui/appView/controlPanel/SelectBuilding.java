package application.gui.appView.controlPanel;

import javax.swing.*;

import bank.BankCustomerRole;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import application.*;
import application.gui.appView.*;
import application.gui.appView.listPanel.ListPanel.Profile;
import application.gui.animation.*;
import application.gui.trace.AlertLog;
import application.gui.trace.AlertTag;
import application.gui.trace.TracePanel;

public class SelectBuilding extends JPanel implements ActionListener
{
	CityPanel animP;
	ApplicationPanel appPanel;
	Application app;
	ControlPanel cp;
	int editIndex = 0;

	JPanel mainPanel = new JPanel();
	JComboBox selectBuildingBox;
	JButton openCloseBuildingButton = new JButton("Close Building");
	JButton clearRestaurantInventoryButton = new JButton("Clear Food Inventory");
	JLabel selectBuildingLabel = new JLabel("Select a Building");
	private String[] allBuildings = {" ", "East Bank", "West Bank", "East Market", "West Market", 
			"Chinese Restaurant", "Seafood Restaurant", "American Restaurant", "Italian Restaurant", "Housing Maintenance Company"};
	private boolean eastBankIsOpen = true;
	private boolean westBankIsOpen = true;
	private boolean eastMarketIsOpen = true;
	private boolean westMarketIsOpen = true;
	private boolean chineseRestaurantIsOpen = true;
	private boolean seafoodRestaurantIsOpen = true;
	private boolean americanRestaurantIsOpen = true;
	private boolean italianRestaurantIsOpen = true;
	private boolean housingMaintenanceCompanyIsOpen = true;
	
	private JPanel restaurantInfoPanel;
	public JScrollPane scrollInfoPane =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private JLabel jobNameLabel = new JLabel("Job   Name");
	private List<JButton> workerButtons = new ArrayList<JButton>(); 

	
	public SelectBuilding(ControlPanel cp, Application app, ApplicationPanel appPanel)
	{
		this.cp = cp;
		this.app = app;
		this.appPanel = appPanel;
		this.setLayout((new BoxLayout((Container) this, BoxLayout.X_AXIS)));
		//setLayout(new GridLayout(1,0));
		
		restaurantInfoPanel = new JPanel();
		restaurantInfoPanel.setLayout(new BoxLayout((Container)restaurantInfoPanel, BoxLayout.Y_AXIS));
		scrollInfoPane.setViewportView(restaurantInfoPanel);
		scrollInfoPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
		Dimension paneDim = new Dimension(250, 500);
		scrollInfoPane.setPreferredSize(paneDim);
		scrollInfoPane.setMinimumSize(paneDim);
		scrollInfoPane.setMaximumSize(paneDim);
		//updateList();
        
		add(scrollInfoPane);
		
		
		
		GridBagConstraints gbcConstraints = new GridBagConstraints();
		gbcConstraints.fill = GridBagConstraints.VERTICAL;
		gbcConstraints.anchor = GridBagConstraints.CENTER;
		//mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(10,2));
		
		gbcConstraints.gridx = 0;
		gbcConstraints.gridy = 0;
		mainPanel.add(selectBuildingLabel, gbcConstraints);
		gbcConstraints.gridx = 0;
		gbcConstraints.gridy = 1;
		selectBuildingBox = new JComboBox(allBuildings);
		selectBuildingBox.addActionListener(this);
		mainPanel.add(selectBuildingBox, gbcConstraints);
		
		//closeBuildingButton;
		gbcConstraints.gridx = 1;
		gbcConstraints.gridy = 0;
		openCloseBuildingButton.setEnabled(false);
		openCloseBuildingButton.addActionListener(this);
		//openCloseBuildingButton.setSize(paneDim);
		mainPanel.add(openCloseBuildingButton, gbcConstraints);
		
		gbcConstraints.gridx = 1;
		gbcConstraints.gridy = 1;
		clearRestaurantInventoryButton.setVisible(false);
		mainPanel.add(clearRestaurantInventoryButton, gbcConstraints);		
		
		add(mainPanel);
	}

	///@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == selectBuildingBox)
		{
			System.out.print("Test");
			if(selectBuildingBox.getSelectedItem() == " ")
			{
				clearRestaurantInventoryButton.setVisible(false);
				clearRestaurantInventoryButton.setEnabled(false);
				openCloseBuildingButton.setEnabled(false);
			}
			else
			{
				clearRestaurantInventoryButton.setVisible(false);
				clearRestaurantInventoryButton.setEnabled(false);
				if(selectBuildingBox.getSelectedItem() == "East Bank")
				{
					if(eastBankIsOpen)
					{
						openCloseBuildingButton.setText("Close Building");
					}
					else
					{
						openCloseBuildingButton.setText("Open Building");
					}
				}
				if(selectBuildingBox.getSelectedItem() == "West Bank")
				{
					if(westBankIsOpen)
					{
						openCloseBuildingButton.setText("Close Building");
					}
					else
					{
						openCloseBuildingButton.setText("Open Building");
					}
				}
				if(selectBuildingBox.getSelectedItem() == "East Market")
				{
					if(eastMarketIsOpen)
					{
						openCloseBuildingButton.setText("Close Building");
					}
					else
					{
						openCloseBuildingButton.setText("Open Building");
					}
				}
				if(selectBuildingBox.getSelectedItem() == "West Market")
				{
				 	if(westMarketIsOpen)
					{
						openCloseBuildingButton.setText("Close Building");
					}
					else
					{
						openCloseBuildingButton.setText("Open Building");
					}
				}
				if(selectBuildingBox.getSelectedItem() == "Chinese Restaurant")
				{
					clearRestaurantInventoryButton.setVisible(true);
					clearRestaurantInventoryButton.setEnabled(true);
					if(chineseRestaurantIsOpen)
					{
						openCloseBuildingButton.setText("Close Building");
					}
					else
					{
						openCloseBuildingButton.setText("Open Building");
					}
				}	
				if(selectBuildingBox.getSelectedItem() == "Seafood Restaurant")
				{
					clearRestaurantInventoryButton.setVisible(true);
					clearRestaurantInventoryButton.setEnabled(true);
					if(seafoodRestaurantIsOpen)
					{
						openCloseBuildingButton.setText("Close Building");
					}
					else
					{
						openCloseBuildingButton.setText("Open Building");
					}
				}
				if(selectBuildingBox.getSelectedItem() == "American Restaurant")
				{
					clearRestaurantInventoryButton.setVisible(true);
					if(americanRestaurantIsOpen)
					{
						openCloseBuildingButton.setText("Close Building");
					}
					else
					{
						openCloseBuildingButton.setText("Open Building");
					}
				}
				if(selectBuildingBox.getSelectedItem() == "Italian Restaurant")
				{
					clearRestaurantInventoryButton.setVisible(true);
					if(italianRestaurantIsOpen)
					{
						openCloseBuildingButton.setText("Close Building");
					}
					else
					{
						openCloseBuildingButton.setText("Open Building");
					}
				}
				if(selectBuildingBox.getSelectedItem() == "Housing Maintenance Company")
				{
					if(housingMaintenanceCompanyIsOpen)
					{
						openCloseBuildingButton.setText("Close Building");
					}
					else
					{
						openCloseBuildingButton.setText("Open Building");
					}
				}
				updateWorkerList();
				openCloseBuildingButton.setEnabled(true);
			}
		}
		if( e.getSource() == clearRestaurantInventoryButton)
		{
			if(selectBuildingBox.getSelectedItem() == "Chinese Restaurant")
			{
				Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantCookRole.deleteInventory();
			}
			if(selectBuildingBox.getSelectedItem() == "American Restaurant")
			{
				
			}
			if(selectBuildingBox.getSelectedItem() == "Seafood Restaurant")
			{
			//	Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantCookRole.setEmpty();
			}
			if(selectBuildingBox.getSelectedItem() == "Italian Restaurant")
			{
				
			}
		}
		if (e.getSource() == openCloseBuildingButton)
		{
			if(selectBuildingBox.getSelectedItem() == "East Bank")
			{
				if(eastBankIsOpen)
				{
					eastBankIsOpen = false;
					Phonebook.getPhonebook().closeBuilding("eastBank");
					openCloseBuildingButton.setText("Open Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now closed.");

				}
				else
				{
					eastBankIsOpen = true;
					Phonebook.getPhonebook().openBuilding("eastBank");
					openCloseBuildingButton.setText("Close Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now open.");

				}
			}
			if(selectBuildingBox.getSelectedItem() == "West Bank")
			{
				if(westBankIsOpen)
				{
					westBankIsOpen = false;
					Phonebook.getPhonebook().closeBuilding("westBank");
					openCloseBuildingButton.setText("Open Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now closed.");

				}
				else
				{
					westBankIsOpen = true;
					Phonebook.getPhonebook().openBuilding("westBank");
					openCloseBuildingButton.setText("Close Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now open.");

				}
			}
			if(selectBuildingBox.getSelectedItem() == "East Market")
			{
				if(eastMarketIsOpen)
				{
					eastMarketIsOpen = false;
					Phonebook.getPhonebook().closeBuilding("eastMarket");
					openCloseBuildingButton.setText("Open Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now closed.");
				}
				else
				{
					eastMarketIsOpen = true;
					Phonebook.getPhonebook().openBuilding("eastMarket");
					openCloseBuildingButton.setText("Close Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now open.");
				}
			}
			if(selectBuildingBox.getSelectedItem() == "West Market")
			{
				if(westMarketIsOpen)
				{
					westMarketIsOpen = false;
					Phonebook.getPhonebook().closeBuilding("westMarket");
					openCloseBuildingButton.setText("Open Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now closed.");
				}
				else
				{
					westMarketIsOpen = true;
					Phonebook.getPhonebook().openBuilding("westMarket");
					openCloseBuildingButton.setText("Close Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now open.");

				}
			}
			if(selectBuildingBox.getSelectedItem() == "Chinese Restaurant")
			{
				if(chineseRestaurantIsOpen)
				{
					chineseRestaurantIsOpen = false;
					Phonebook.getPhonebook().closeBuilding("chineseRestaurant");
					openCloseBuildingButton.setText("Open Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now closed.");
				}
				else
				{
					chineseRestaurantIsOpen = true;
					Phonebook.getPhonebook().openBuilding("chineseRestaurant");
					openCloseBuildingButton.setText("Close Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now open.");
				}
			}
			if(selectBuildingBox.getSelectedItem() == "Seafood Restaurant")
			{
				if(seafoodRestaurantIsOpen)
				{
					seafoodRestaurantIsOpen = false;
					Phonebook.getPhonebook().closeBuilding("seafoodRestaurant");
					openCloseBuildingButton.setText("Open Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now closed.");
				}
				else
				{
					seafoodRestaurantIsOpen = true;
					Phonebook.getPhonebook().openBuilding("seafoodRestaurant");
					openCloseBuildingButton.setText("Close Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now open.");
				}
			}
			if(selectBuildingBox.getSelectedItem() == "American Restaurant")
			{
				if(americanRestaurantIsOpen)
				{
					americanRestaurantIsOpen = false;
					Phonebook.getPhonebook().closeBuilding("americanRestaurant");
					openCloseBuildingButton.setText("Open Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now closed.");
				}
				else
				{
					americanRestaurantIsOpen = true;
					Phonebook.getPhonebook().openBuilding("americanRestaurant");
					openCloseBuildingButton.setText("Close Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now open.");
				}
			}
			if(selectBuildingBox.getSelectedItem() == "Italian Restaurant")
			{
				if(italianRestaurantIsOpen)
				{
					italianRestaurantIsOpen = false;
					Phonebook.getPhonebook().closeBuilding("italianRestaurant");
					openCloseBuildingButton.setText("Open Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now closed.");
				}
				else
				{
					italianRestaurantIsOpen = true;
					Phonebook.getPhonebook().openBuilding("italianRestaurant");
					openCloseBuildingButton.setText("Close Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now open.");
				}
			}
			if(selectBuildingBox.getSelectedItem() == "Housing Maintenance Company")
			{
				if(housingMaintenanceCompanyIsOpen)
				{
					housingMaintenanceCompanyIsOpen = false;
					Phonebook.getPhonebook().closeBuilding("housingMaintenanceCompany");
					openCloseBuildingButton.setText("Open Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now closed.");
				}
				else
				{
					housingMaintenanceCompanyIsOpen = true;
					Phonebook.getPhonebook().openBuilding("housingMaintenanceCompany");
					openCloseBuildingButton.setText("Close Building");
					AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY,selectBuildingBox.getSelectedItem().toString(), "is now open.");
				}
			}
		}

	}
	
	public void updateWorkerList()
	{
		workerButtons.clear();
		restaurantInfoPanel.removeAll();
		validate();
		if(selectBuildingBox.getSelectedItem() == " ")
		{
			//validate();
			//restaurantInfoPanel.setVisible(false);
			return;
		}
		else
		{
			restaurantInfoPanel.setVisible(true);
			if(selectBuildingBox.getSelectedItem() == "East Bank")
			{
				String pName;
				//Bank Guard
				if(Phonebook.getPhonebook().getEastBank().bankGuardRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getEastBank().bankGuardRole.getName();
				}
				JButton button1 = new JButton(Phonebook.getPhonebook().getEastBank().bankGuardRole.getRoleName() + " " + pName);
				button1.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button1);
				restaurantInfoPanel.add(button1);
				validate();

				//Loan Officer
				if(Phonebook.getPhonebook().getEastBank().loanOfficerRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getEastBank().loanOfficerRole.getName();
				}
				JButton button2 = new JButton(Phonebook.getPhonebook().getEastBank().loanOfficerRole.getRoleName() + " " + pName);
				button2.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button2);
				restaurantInfoPanel.add(button2);
				validate();			

				//Bank Tellers
				for(int i = 0; i < Phonebook.getPhonebook().getEastBank().bankGuardRole.getTellers().size(); i++)
				{
					JButton tempbutton1 = new JButton(/*Phonebook.getPhonebook().getEastBank().bankGuardRole.getTellers().get(i).tell1.getRoleName()*/ 
							"Bank Teller #" + (i+1) + " " + Phonebook.getPhonebook().getEastBank().bankGuardRole.getTellers().get(i).tell1.getName());
					tempbutton1.setMaximumSize(new Dimension(250, 25));
					workerButtons.add(tempbutton1);
					restaurantInfoPanel.add(tempbutton1);
					validate();
				}
				
				//Bank Customers, no to name, they are interfaces right now
//				for(int i = 0; i < Phonebook.getPhonebook().getEastBank().bankGuardRole.getCustomers().size(); i++)
//				{
//					JButton tempbutton1 = new JButton(/*Phonebook.getPhonebook().getEastBank().bankGuardRole.getTellers().get(i).tell1.getRoleName()*/ 
//							"Bank Customer" + " " + (BankCustomerRole) Phonebook.getPhonebook().getEastBank().bankGuardRole.getCustomers().get(i));
//					tempbutton1.setMaximumSize(new Dimension(250, 25));
//					workerButtons.add(tempbutton1);
//					restaurantInfoPanel.add(tempbutton1);
//					validate();
//				}
			}
			if(selectBuildingBox.getSelectedItem() == "West Bank")
			{
				String pName;
				//Bank Guard
				if(Phonebook.getPhonebook().getWestBank().bankGuardRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getWestBank().bankGuardRole.getName();
				}
				JButton button1 = new JButton(Phonebook.getPhonebook().getWestBank().bankGuardRole.getRoleName() + " " + pName);
				button1.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button1);
				restaurantInfoPanel.add(button1);
				validate();

				//Loan Officer
				if(Phonebook.getPhonebook().getWestBank().loanOfficerRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getWestBank().loanOfficerRole.getName();
				}
				JButton button2 = new JButton(Phonebook.getPhonebook().getWestBank().loanOfficerRole.getRoleName() + " " + pName);
				button2.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button2);
				restaurantInfoPanel.add(button2);
				validate();			

				//Bank Tellers
				for(int i = 0; i < Phonebook.getPhonebook().getWestBank().bankGuardRole.getTellers().size(); i++)
				{
					JButton tempbutton1 = new JButton(/*Phonebook.getPhonebook().getEastBank().bankGuardRole.getTellers().get(i).tell1.getRoleName()*/ 
							"Bank Teller #" + (i+1) + " " + Phonebook.getPhonebook().getWestBank().bankGuardRole.getTellers().get(i).tell1.getName());
					tempbutton1.setMaximumSize(new Dimension(250, 25));
					workerButtons.add(tempbutton1);
					restaurantInfoPanel.add(tempbutton1);
					validate();
				}
				
				//Bank Customers, no to name, they are interfaces right now
//				for(int i = 0; i < Phonebook.getPhonebook().getWestBank().bankGuardRole.getCustomers().size(); i++)
//				{
//					JButton tempbutton1 = new JButton(/*Phonebook.getPhonebook().getWestBank().bankGuardRole.getTellers().get(i).tell1.getRoleName()*/ 
//							"Bank Customer" + " " + (BankCustomerRole) Phonebook.getPhonebook().getWestBank().bankGuardRole.getCustomers().get(i));
//					tempbutton1.setMaximumSize(new Dimension(250, 25));
//					workerButtons.add(tempbutton1);
//					restaurantInfoPanel.add(tempbutton1);
//					validate();
//				}
				//List of robbers?
			}
			if(selectBuildingBox.getSelectedItem() == "East Market")
			{
				String pName;
				//Sales Person
				if(Phonebook.getPhonebook().getEastMarket().salesPersonRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getEastMarket().salesPersonRole.getName();
				}
				JButton button1 = new JButton(Phonebook.getPhonebook().getEastMarket().salesPersonRole.getRoleName() + " " + pName);
				button1.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button1);
				restaurantInfoPanel.add(button1);
				validate();
				
				//MarketRunner
				if(Phonebook.getPhonebook().getEastMarket().marketRunnerRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getEastMarket().marketRunnerRole.getName();
				}
				JButton button2 = new JButton(Phonebook.getPhonebook().getEastMarket().marketRunnerRole.getRoleName() + " " + pName);
				button2.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button2);
				restaurantInfoPanel.add(button2);
				validate();
				
				//UPS Man
				if(Phonebook.getPhonebook().getEastMarket().UPSmanRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getEastMarket().UPSmanRole.getName();
				}
				JButton button3 = new JButton(Phonebook.getPhonebook().getEastMarket().UPSmanRole.getRoleName() + " " + pName);
				button3.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button3);
				restaurantInfoPanel.add(button3);
				validate();
				
				//Market CUstoemrs
				for(int i = 0; i < Phonebook.getPhonebook().getEastMarket().marketCustomerGuis.size(); i++)
				{
					JButton tempbutton1 = new JButton(/*Phonebook.getPhonebook().getEastBank().bankGuardRole.getTellers().get(i).tell1.getRoleName()*/ 
							"Market Customer " + Phonebook.getPhonebook().getEastMarket().marketCustomerGuis.get(i).getAgent().getName());
					tempbutton1.setMaximumSize(new Dimension(250, 25));
					workerButtons.add(tempbutton1);
					restaurantInfoPanel.add(tempbutton1);
					validate();
				}
			}
			if(selectBuildingBox.getSelectedItem() == "West Market")
			{
				String pName;
				//Sales Person
				if(Phonebook.getPhonebook().getWestMarket().salesPersonRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getWestMarket().salesPersonRole.getName();
				}
				JButton button1 = new JButton(Phonebook.getPhonebook().getWestMarket().salesPersonRole.getRoleName() + " " + pName);
				button1.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button1);
				restaurantInfoPanel.add(button1);
				validate();
				
				//MarketRunner
				if(Phonebook.getPhonebook().getWestMarket().marketRunnerRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getWestMarket().marketRunnerRole.getName();
				}
				JButton button2 = new JButton(Phonebook.getPhonebook().getWestMarket().marketRunnerRole.getRoleName() + " " + pName);
				button2.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button2);
				restaurantInfoPanel.add(button2);
				validate();
				
				//UPS Man
				if(Phonebook.getPhonebook().getWestMarket().UPSmanRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getWestMarket().UPSmanRole.getName();
				}
				JButton button3 = new JButton(Phonebook.getPhonebook().getWestMarket().UPSmanRole.getRoleName() + " " + pName);
				button3.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button3);
				restaurantInfoPanel.add(button3);
				validate();
				
				//Market CUstoemrs
				for(int i = 0; i < Phonebook.getPhonebook().getWestMarket().marketCustomerGuis.size(); i++)
				{
					JButton tempbutton1 = new JButton(/*Phonebook.getPhonebook().getEastBank().bankGuardRole.getTellers().get(i).tell1.getRoleName()*/ 
							"Market Customer " + Phonebook.getPhonebook().getWestMarket().marketCustomerGuis.get(i).getAgent().getName());
					tempbutton1.setMaximumSize(new Dimension(250, 25));
					workerButtons.add(tempbutton1);
					restaurantInfoPanel.add(tempbutton1);
					validate();
				}
			}
			if(selectBuildingBox.getSelectedItem() == "Chinese Restaurant")
			{
				String pName;
				//Host
				if(Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantHostRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantHostRole.getName();
				}
				JButton button1 = new JButton(Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantHostRole.getRoleName() + " " + pName);
				button1.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button1);
				restaurantInfoPanel.add(button1);
				validate();
				
				//Cook
				if(Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantCookRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantCookRole.getName();
				}
				JButton button2 = new JButton(Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantCookRole.getRoleName() + " " + pName);
				button2.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button2);
				restaurantInfoPanel.add(button2);
				validate();
				
				//Cashier
				if(Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantCashierRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantCashierRole.getName();
				}
				JButton button3 = new JButton(Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantCashierRole.getRoleName() + " " + pName);
				button3.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button3);
				restaurantInfoPanel.add(button3);
				validate();
				
				//Waiters
				for(int i = 0; i < Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantHostRole.waiters.size(); i++)
				{
					JButton tempbutton1 = new JButton(Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantHostRole.waiters.get(i).chineseRestaurantWaiterRole.getRoleName() 
							+ " " + Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantHostRole.waiters.get(i).chineseRestaurantWaiterRole.getName());
					tempbutton1.setMaximumSize(new Dimension(250, 25));
					workerButtons.add(tempbutton1);
					restaurantInfoPanel.add(tempbutton1);
					validate();
				}

				//Customers
				for(int i = 0; i < Phonebook.getPhonebook().getChineseRestaurant().getCustomers().size(); i++)
				{
					JButton tempbutton1 = new JButton(/*Phonebook.getPhonebook().getEastBank().bankGuardRole.getTellers().get(i).tell1.getRoleName()*/ 
							"Restaurant Customer " + Phonebook.getPhonebook().getChineseRestaurant().getCustomers().get(i).getCustomerName());
					tempbutton1.setMaximumSize(new Dimension(250, 25));
					workerButtons.add(tempbutton1);
					restaurantInfoPanel.add(tempbutton1);
					validate();
				}
			}
//			if(selectBuildingBox.getSelectedItem() == "Seafood Restaurant")
//			{
//				String pName;
//				//Host
//				if(Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.person == null)
//				{
//				 	pName = "null";
//				}
//				else
//				{
//					pName = Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.getName();
//				}
//				JButton button1 = new JButton(Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.getRoleName() + " " + pName);
//				button1.setMaximumSize(new Dimension(250, 25));
//				workerButtons.add(button1);
//				restaurantInfoPanel.add(button1);
//				validate();
//				
//				//Cook
//				if(Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantCookRole.person == null)
//				{
//				 	pName = "null";
//				}
//				else
//				{
//					pName = Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantCookRole.getName();
//				}
//				JButton button2 = new JButton(Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantCookRole.getRoleName() + " " + pName);
//				button2.setMaximumSize(new Dimension(250, 25));
//				workerButtons.add(button2);
//				restaurantInfoPanel.add(button2);
//				validate();
//				
//				//Cashier
//				if(Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantCashierRole.person == null)
//				{
//				 	pName = "null";
//				}
//				else
//				{
//					pName = Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantCashierRole.getName();
//				}
//				JButton button3 = new JButton(Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantCashierRole.getRoleName() + " " + pName);
//				button3.setMaximumSize(new Dimension(250, 25));
//				workerButtons.add(button3);
//				restaurantInfoPanel.add(button3);
//				validate();
//				
//				//Waiters
//				for(int i = 0; i < Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.MyWaiters.size(); i++)
//				{
//					JButton tempbutton1 = new JButton(Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.MyWaiters.get(i).w1.getRoleName() 
//							+ " " + Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.MyWaiters.get(i).w1.getName());
//					tempbutton1.setMaximumSize(new Dimension(250, 25));
//					workerButtons.add(tempbutton1);
//					restaurantInfoPanel.add(tempbutton1);
//					validate();
//				}
//
//				//Customers
//				for(int i = 0; i < Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.MyCustomers.size(); i++)
//				{
//					JButton tempbutton1 = new JButton(Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.MyCustomers.get(i).c.getRoleName() 
//							+ " " + Phonebook.getPhonebook().getSeafoodRestaurant().seafoodRestaurantHostRole.MyCustomers.get(i).c.getName());
//					tempbutton1.setMaximumSize(new Dimension(250, 25));
//					workerButtons.add(tempbutton1);
//					restaurantInfoPanel.add(tempbutton1);
//					validate();
//				}
//			}
//			
//			if(selectBuildingBox.getSelectedItem() == "American Restaurant")
//			{
//				String pName;
//				//Host
//				if(Phonebook.getPhonebook().getAmericanRestaurant().americanHost.person == null)
//				{
//				 	pName = "null";
//				}
//				else
//				{
//					pName = Phonebook.getPhonebook().getAmericanRestaurant().americanHost.getName();
//				}
//				JButton button1 = new JButton(Phonebook.getPhonebook().getAmericanRestaurant().americanHost.getRoleName() + " " + pName);
//				button1.setMaximumSize(new Dimension(250, 25));
//				workerButtons.add(button1);
//				restaurantInfoPanel.add(button1);
//				validate();
//				
//				//Cook
//				if(Phonebook.getPhonebook().getAmericanRestaurant().americanCook.person == null)
//				{
//				 	pName = "null";
//				}
//				else
//				{
//					pName = Phonebook.getPhonebook().getAmericanRestaurant().americanCook.getName();
//				}
//				JButton button2 = new JButton(Phonebook.getPhonebook().getAmericanRestaurant().americanCook.getRoleName() + " " + pName);
//				button2.setMaximumSize(new Dimension(250, 25));
//				workerButtons.add(button2);
//				restaurantInfoPanel.add(button2);
//				validate();
//				
//				//Cashier
//				if(Phonebook.getPhonebook().getAmericanRestaurant().americanCashier.person == null)
//				{
//				 	pName = "null";
//				}
//				else
//				{
//					pName = Phonebook.getPhonebook().getAmericanRestaurant().americanCashier.getName();
//				}
//				JButton button3 = new JButton(Phonebook.getPhonebook().getAmericanRestaurant().americanCashier.getRoleName() + " " + pName);
//				button3.setMaximumSize(new Dimension(250, 25));
//				workerButtons.add(button3);
//				restaurantInfoPanel.add(button3);
//				validate();
//				
//				//Waiters
//				for(int i = 0; i < Phonebook.getPhonebook().getAmericanRestaurant().americanHost.Waiters.size(); i++)
//				{
//					JButton tempbutton1 = new JButton(Phonebook.getPhonebook().getAmericanRestaurant().americanHost.Waiters.get(i).getRoleName() 
//							+ " " + Phonebook.getPhonebook().getAmericanRestaurant().americanHost.Waiters.get(i).getName());
//					tempbutton1.setMaximumSize(new Dimension(250, 25));
//					workerButtons.add(tempbutton1);
//					restaurantInfoPanel.add(tempbutton1);
//					validate();
//				}
//
//				//Customers
//				for(int i = 0; i < Phonebook.getPhonebook().getAmericanRestaurant().americanHost.waitingCustomers.size(); i++)
//				{
//					JButton tempbutton1 = new JButton(Phonebook.getPhonebook().getAmericanRestaurant().americanHost.waitingCustomers.get(i).getRoleName() 
//							+ " " + Phonebook.getPhonebook().getAmericanRestaurant().americanHost.waitingCustomers.get(i).getName());
//					tempbutton1.setMaximumSize(new Dimension(250, 25));
//					workerButtons.add(tempbutton1);
//					restaurantInfoPanel.add(tempbutton1);
//					validate();
//				}
//			}
			
			if(selectBuildingBox.getSelectedItem() == "Italian Restaurant")
			{
				String pName;
				//Host
				if(Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantHostRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantHostRole.getName();
				}
				JButton button1 = new JButton(Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantHostRole.getRoleName() + " " + pName);
				button1.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button1);
				restaurantInfoPanel.add(button1);
				validate();
				
				//Cook
				if(Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantCookRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantCookRole.getName();
				}
				JButton button2 = new JButton(Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantCookRole.getRoleName() + " " + pName);
				button2.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button2);
				restaurantInfoPanel.add(button2);
				validate();
				
				//Cashier
				if(Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantCashierRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantCashierRole.getName();
				}
				JButton button3 = new JButton(Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantCashierRole.getRoleName() + " " + pName);
				button3.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button3);
				restaurantInfoPanel.add(button3);
				validate();
				
				//Waiters
				for(int i = 0; i < Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantHostRole.waiters.size(); i++)
				{
					JButton tempbutton1 = new JButton(Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantHostRole.waiters.get(i).getRoleName() 
							+ " " + Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantHostRole.waiters.get(i).getName());
					tempbutton1.setMaximumSize(new Dimension(250, 25));
					workerButtons.add(tempbutton1);
					restaurantInfoPanel.add(tempbutton1);
					validate();
				}

				//Customers
				for(int i = 0; i < Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantHostRole.waitingCustomers.size(); i++)
				{
					JButton tempbutton1 = new JButton(Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantHostRole.waitingCustomers.get(i).getRoleName() 
							+ " " + Phonebook.getPhonebook().getItalianRestaurant().italianRestaurantHostRole.waitingCustomers.get(i).getName());
					tempbutton1.setMaximumSize(new Dimension(250, 25));
					workerButtons.add(tempbutton1);
					restaurantInfoPanel.add(tempbutton1);
					validate();
				}

			}
			
			if(selectBuildingBox.getSelectedItem() == "Housing Maintenance Company")
			{
				String pName;
				if(Phonebook.getPhonebook().getHousingMaintenanceCompany().maintenanceWorkerRole.person == null)
				{
				 	pName = "null";
				}
				else
				{
					pName = Phonebook.getPhonebook().getHousingMaintenanceCompany().maintenanceWorkerRole.getName();
				}
				JButton button = new JButton(Phonebook.getPhonebook().getHousingMaintenanceCompany().maintenanceWorkerRole.getRoleName() + " " + pName);
				button.setMaximumSize(new Dimension(250, 25));
				workerButtons.add(button);
				restaurantInfoPanel.add(button);
				validate();
			}
			
		}
	}
}


