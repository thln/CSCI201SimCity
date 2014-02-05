package application.gui.appView.controlPanel;


import javax.imageio.ImageIO;
//import javax.sound.sampled.AudioFormat;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
//import javax.sound.sampled.DataLine;
//import javax.sound.sampled.LineUnavailableException;
//import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
//import javax.swing.text.NumberFormatter;

import person.Person;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
//import java.text.NumberFormat;
//import java.util.ArrayList;
//
//import sun.audio.*;
//
//import java.io.*;

import application.*;
//import application.gui.appView.listPanel.ListPanel.Profile;

public class AddPanel extends JPanel implements ActionListener {
	private DashboardPanel dashboard;
	public static JPanel mainPanel = new JPanel();
	private JLabel nameLabel = new JLabel("Name: ");
	private JLabel typeLabel = new JLabel("Select Type");
	private JLabel startingMoneyLabel = new JLabel("Starting Cash: ");
	private JLabel housingLabel = new JLabel("Housing: ");
	private JLabel jobTypeLabel = new JLabel("Job Type: ");
	private JLabel jobLocationLabel = new JLabel("Job Location: ");
	private JLabel jobStartTimeLabel = new JLabel("Job Start Time: ");
	private JLabel jobLunchTimeLabel = new JLabel("Job Lunch Time: ");
	private JLabel jobEndTimeLabel = new JLabel("Job End Time: ");
	private JLabel resultLabel = new JLabel(" ");
	private JTextField nameTextField = new JTextField(10);
	private JTextField moneyIntField = new JTextField(10);
	private JTextField jobSTimeField = new JTextField(10);
	private JTextField jobLTimeField = new JTextField(10);
	private JTextField jobETimeField = new JTextField(10);
	private JButton addButton = new JButton("Add");
	private JButton raveButton = new JButton("Rave Mode");
	private JButton updateButton = new JButton("Update Time");
	private ControlPanel cp;
	private Application app;
	private JComboBox socialClassBox;
	private JComboBox jobLocationBox;
	private JComboBox jobTypeBox;
	private JComboBox housingBox;

	
	private boolean raveMode = false;	
	boolean rave = false;

	private String[] personType = {" ", /*"Deadbeat", "Crook", */ "Worker", "Wealthy"};
	private String[] jobLocation = {" ","East Bank", "West Bank", "East Market", "West Market",
			"Chinese Restaurant", "Seafood Restaurant", "American Restaurant", "Italian Restaurant", "Housing"};
	private String[] emptyList = {" "};
	private String[] restaurantJobs = {" ", "Host", "Cook", "Cashier", "Waiter", "Alt Waiter"};
	private String[] marketJobs = {" ", "UPS Man", "Sales Person", "Market Runner"};
	private String[] bankJobs = {" ", "Bank Guard", "Bank Teller", "Loan Officer"};
	private String[] housingJobs = {" ", "Maintenance Worker"};
	private String[] apartmentHousing = {" ", "East Apartment", "West Apartment"};
	private String[] mansionHousing = {" ", "Mansion"};
	private String[] parkHousing = {" ", "Park" };
	
	//}
	//}
	
	public AddPanel(ControlPanel cp, Application app)
	{
		
		//initialization
		this.cp = cp;
		this.app = app;
		setLayout(new GridLayout(1,0));
		dashboard = new DashboardPanel(app);
		add(dashboard);
		GridBagConstraints gbcConstraints = new GridBagConstraints();
		gbcConstraints.fill = GridBagConstraints.VERTICAL;
		gbcConstraints.anchor = GridBagConstraints.CENTER;
		mainPanel.setLayout(new GridLayout(11, 2));

		//name
		gbcConstraints.gridx = 0;
		gbcConstraints.gridy = 0;
		mainPanel.add(nameLabel,gbcConstraints);
		gbcConstraints.gridx = 0;
		gbcConstraints.gridy = 1;
		mainPanel.add(nameTextField,gbcConstraints);

		//Social Class
		gbcConstraints.gridx = 1;
		gbcConstraints.gridy = 0;
		mainPanel.add(typeLabel,gbcConstraints);
		gbcConstraints.gridx = 1;
		gbcConstraints.gridy = 1;
		socialClassBox = new JComboBox(personType);
		socialClassBox.setSelectedIndex(0);
		socialClassBox.addActionListener(this);
		mainPanel.add(socialClassBox,gbcConstraints);

		//Starting Cash
		gbcConstraints.gridx = 2;
		gbcConstraints.gridy = 0;
		mainPanel.add(startingMoneyLabel, gbcConstraints);
		gbcConstraints.gridx = 2;
		gbcConstraints.gridy = 1;
		mainPanel.add(moneyIntField, gbcConstraints);

		//Housing Location
		gbcConstraints.gridx = 3;
		gbcConstraints.gridy = 0;
		housingLabel.setVisible(true);
		mainPanel.add(housingLabel, gbcConstraints);
		gbcConstraints.gridx = 3;
		gbcConstraints.gridy = 1;
		housingBox = new JComboBox(emptyList);
		housingBox.setSelectedIndex(0);
		housingBox.addActionListener(this);
		housingBox.setVisible(true);
		mainPanel.add(housingBox, gbcConstraints);
		
		//Job Location
		gbcConstraints.gridx = 4;
		gbcConstraints.gridy = 0;
		jobLocationLabel.setVisible(false);
		mainPanel.add(jobLocationLabel, gbcConstraints);
		gbcConstraints.gridx = 4;
		gbcConstraints.gridy = 1;
		jobLocationBox = new JComboBox(emptyList);
		jobLocationBox.setSelectedIndex(0);
		jobLocationBox.addActionListener(this);
		jobLocationBox.setVisible(false);
		mainPanel.add(jobLocationBox, gbcConstraints);

		//JobTypes
		gbcConstraints.gridx = 5;
		gbcConstraints.gridy = 0;
		jobTypeLabel.setVisible(false);
		mainPanel.add(jobTypeLabel, gbcConstraints);		
		gbcConstraints.gridx = 5;
		gbcConstraints.gridy = 1;
		jobTypeBox = new JComboBox(emptyList);
		jobTypeBox.setSelectedIndex(0);
		jobTypeBox.addActionListener(this);
		jobTypeBox.setVisible(false);
		mainPanel.add(jobTypeBox,gbcConstraints);

		//Start, Lunch, End Times
		gbcConstraints.gridx = 6;
		gbcConstraints.gridy = 0;
		jobStartTimeLabel.setVisible(false);
		mainPanel.add(jobStartTimeLabel, gbcConstraints);
		gbcConstraints.gridx = 6;
		gbcConstraints.gridy = 1;
		jobSTimeField.setVisible(false);
		mainPanel.add(jobSTimeField, gbcConstraints);

		gbcConstraints.gridx = 7;
		gbcConstraints.gridy = 0;
		jobLunchTimeLabel.setVisible(false);
		mainPanel.add(jobLunchTimeLabel, gbcConstraints);
		gbcConstraints.gridx = 7;
		gbcConstraints.gridy = 1;
		jobLTimeField.setVisible(false);
		mainPanel.add(jobLTimeField, gbcConstraints);

		gbcConstraints.gridx = 8;
		gbcConstraints.gridy = 0;
		jobEndTimeLabel.setVisible(false);
		mainPanel.add(jobEndTimeLabel, gbcConstraints);
		gbcConstraints.gridx = 8;
		gbcConstraints.gridy = 1;
		jobETimeField.setVisible(false);
		mainPanel.add(jobETimeField, gbcConstraints);

		//The bottom stuff
		gbcConstraints.gridx = 9;
		gbcConstraints.gridy = 0;
		mainPanel.add(resultLabel,gbcConstraints);
		gbcConstraints.gridx = 9;
		gbcConstraints.gridy = 1;
		addButton.addActionListener(this);
		raveButton.addActionListener(this);
		updateButton.addActionListener(this);
		mainPanel.add(addButton,gbcConstraints);
		mainPanel.add(raveButton,gbcConstraints);
		mainPanel.add(updateButton,gbcConstraints);


		add(mainPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == updateButton)
		{
			dashboard.updateDashboard();
		}
		if(e.getSource() == socialClassBox)
		{
			if(socialClassBox.getSelectedItem() == "Worker")
			{
				jobLocationBox.removeAllItems();
				for(int i = 0; i < jobLocation.length; i++)
				{
					showJobLocationFields(true);
					jobLocationBox.addItem(jobLocation[i]);
				}
				
				housingBox.removeAllItems();
				for(int i = 0; i < apartmentHousing.length; i++)
				{
					housingBox.addItem(apartmentHousing[i]);
				}
			}
			else if(socialClassBox.getSelectedItem() == "Wealthy")
			{
				jobLocationBox.removeAllItems();
				showJobLocationFields(false);
				jobLocationBox.addItem(" ");
				housingBox.removeAllItems();		
				for(int i = 0; i < mansionHousing.length; i++)
				{
					housingBox.addItem(mansionHousing[i]);
				}
			}
			else if(socialClassBox.getSelectedItem() == "Deadbeat")
			{
				jobLocationBox.removeAllItems();
				showJobLocationFields(false);
				jobLocationBox.addItem(" ");
				housingBox.removeAllItems();
				for(int i = 0; i < parkHousing.length; i++)
				{
					housingBox.addItem(parkHousing[i]);
				}
			}
			else
			{
				jobLocationBox.removeAllItems();
				housingBox.removeAllItems();
				showJobLocationFields(false);
				jobLocationBox.addItem(" ");
				housingBox.addItem(" ");
			}
		}

		if(e.getSource() == jobLocationBox)
		{
			if(jobLocationBox.getSelectedItem() == "Chinese Restaurant" || jobLocationBox.getSelectedItem() == "American Restaurant" ||
					jobLocationBox.getSelectedItem() == "Italian Restaurant" || jobLocationBox.getSelectedItem() == "Seafood Restaurant")
			{
				jobTypeBox.removeAllItems();
				showJobInformationFields(true);
				for(int i = 0; i < restaurantJobs.length; i++)
				{
					jobTypeBox.addItem(restaurantJobs[i]);
				}
			}
			else if(jobLocationBox.getSelectedItem() == "East Bank" || jobLocationBox.getSelectedItem() == "West Bank")
			{
				jobTypeBox.removeAllItems();
				showJobInformationFields(true);
				for(int i = 0; i < bankJobs.length; i++)
				{
					jobTypeBox.addItem(bankJobs[i]);
				}				
			} 
			else if(jobLocationBox.getSelectedItem() == "East Market" || jobLocationBox.getSelectedItem() == "West Market")
			{
				jobTypeBox.removeAllItems();
				showJobInformationFields(true);
				for(int i = 0; i < marketJobs.length; i++)
				{
					jobTypeBox.addItem(marketJobs[i]);
				}
			} 
			else if(jobLocationBox.getSelectedItem() == "Housing")
			{
				jobTypeBox.removeAllItems();
				showJobInformationFields(true);
				for(int i = 0; i < housingJobs.length; i++)
				{
					jobTypeBox.addItem(housingJobs[i]);
				}
			} 
			else
			{
				jobTypeBox.removeAllItems();
				showJobInformationFields(false);
				jobTypeBox.addItem(" ");
			}
		}

		if (e.getSource() == addButton)
		{
			resultLabel.setText(" ");
			int money = -1;
			int jobStartTime = -1;
			int jobLunchTime = -1;
			int jobEndTime = -1; 
			try
			{
				//yourInt = Integer.parseInt(args[0]);
				money = Integer.parseInt(moneyIntField.getText());
				if(socialClassBox.getSelectedItem() == "Worker")
				{
					jobStartTime = Integer.parseInt(jobSTimeField.getText());
					jobLunchTime = Integer.parseInt(jobLTimeField.getText());
					jobEndTime = Integer.parseInt(jobETimeField.getText());
				}
				else
				{
					jobStartTime = -1;
					jobLunchTime = 0;
					jobEndTime = 0;
				}
			}
			catch ( NumberFormatException e1 )
			{
				resultLabel.setText("Please fix fields.");
				return;
			}
			//if( (jobStartTime < 25) && (jobLunchTime < 25) && (jobEndTime < 25) && (jobStartTime < jobEndTime) )
			//{
				
			//}
			
			if(allFieldsEntered() && (jobStartTime < 25) && (jobLunchTime < 25)
					&& (jobEndTime < 25) && (jobStartTime < jobEndTime))
			{
				jobStartTime = jobStartTime*100;
				jobLunchTime = jobLunchTime*100;
				jobEndTime = jobEndTime*100;
				
				app.addPerson(nameTextField.getText(), money, (String) socialClassBox.getSelectedItem(), 
						(String) jobTypeBox.getSelectedItem(), (String) jobLocationBox.getSelectedItem(),
						(String) housingBox.getSelectedItem(), jobStartTime, jobLunchTime, jobEndTime);
				cp.getAppPanel().getListPanel().updateList();
			}
			else
			{
				resultLabel.setText("Please fill in fields.");
				return;				 
			}

			//cp.getAppPanel().getListPanel().addPerson(name, 500, type, null, null, 0, 0, 0);
			cp.getAppPanel().getListPanel().updateList();

			app.printLastPop();
			dashboard.updateDashboard();
			//System.out.println(type);
			//System.out.println(app.getPopulationSize());
		}
		if (e.getSource() == raveButton) {
			for (Person p : app.getPopulation()) {
				p.getGui().setRaveMode();
			}
			if (!rave){
				rave = true;
				try {
					app.animPanel.cityPanel.background = ImageIO.read(new File("res/rave.jpeg"));
					Phonebook.getPhonebook().getRadioStation().startRaveMusic();
				} catch (IOException e1) {
				}
				return;
			}
			if (rave){
				rave = false;
				try {
					app.animPanel.cityPanel.background = ImageIO.read(new File("res/concrete.jpg"));
					Phonebook.getPhonebook().getRadioStation().startBGMusic();
				} catch (IOException e1) {
				}
				return;
			}
		}
	}
	
	public void showJobLocationFields(boolean show)
	{
		jobLocationLabel.setVisible(show);
		jobLocationBox.setVisible(show);
	}

	public void showJobInformationFields(boolean show)
	{
		jobTypeLabel.setVisible(show);
		jobTypeBox.setVisible(show);
		jobStartTimeLabel.setVisible(show);
		jobSTimeField.setVisible(show);
		jobLunchTimeLabel.setVisible(show);
		jobLTimeField.setVisible(show);
		jobEndTimeLabel.setVisible(show);
		jobETimeField.setVisible(show);
	}

	public boolean allFieldsEntered()
	{
		if(nameTextField.getText()  != "" && housingBox.getSelectedIndex() != 0)
			// && moneyIntField.getText() != "" && jobSTimeField.getText() != ""
			//&& jobLTimeField.getText() != "" && jobETimeField.getText() != "" && socialClassBox.getSelectedIndex() != 0
			// && jobTypeBox.getSelectedIndex() != 0  && jobLocationBox.getSelectedIndex() != 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean nonWorkerFieldsEntered()
	{
		if(nameTextField.getText() != "" && moneyIntField.getText() != "")
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void setApplication(Application app){
		this.app = app; 
	}
	
	public void dashboardUpdate()
	{
		dashboard.updateDashboard();
	}


}
