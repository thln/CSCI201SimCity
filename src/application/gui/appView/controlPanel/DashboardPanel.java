package application.gui.appView.controlPanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import application.*;
import application.gui.appView.*;
import application.gui.appView.listPanel.ListPanel.Profile;



public class DashboardPanel extends JPanel implements AWTEventListener{
	
	private JLabel mainTitle = new JLabel("Title");
	private JLabel TimeLabel = new JLabel("Time: ");
	private JLabel DayLabel = new JLabel("Day: ");
	private JLabel descipt = new JLabel("Description ");
	private JLabel pop = new JLabel ("Population");
	private JLabel numberOfHomes = new JLabel ("Number of Homes");
	private JLabel markets = new JLabel ("Number of Markets");
	private JLabel banks = new JLabel ("Number of Banks");
	private JLabel homeless = new JLabel ("% Homeless");
	private JLabel unemployed = new JLabel ("% Unemployed");

	private final String title = "Team 20's City";
	private final String description = "Tripod";
	private String Day;
	private String Time;
	private int population;
	private int numHomes;
	private int occupiedHomes;
	private	int numMarkets;
	private int numBanks;
	private int numHomeless;
	private int numUnemployed;
	private Application app;
	
	public DashboardPanel(Application app){
		setBackground(getBackground());
		this.app = app;
		updateDashboard();
		setLayout(new GridLayout(0,1));
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	@Override
	public void eventDispatched(AWTEvent e) {
		updateDashboard();
	}
	
	public void updateDashboard(){
		
		population = app.getPopulationSize();
		numHomes = app.getNumberHomes();
		Day = TimeManager.getTimeManager().getTime().day.toString();
		Time = TimeManager.getTimeManager().getTime().dayHour + ":" + TimeManager.getTimeManager().getTime().dayMinute ;
		occupiedHomes = 1;
		numMarkets = 1;
		numBanks = 1;
//		numHomeless = 0;
//		numUnemployed = 0;

		mainTitle.setText("\t\t\t" + title);
		descipt.setText("\t\t\t" + description);
		DayLabel.setText("\t\t\t" + "Day: " + Day);
		TimeLabel.setText("\t\t\t" + "Time: " + Time);
		pop.setText("\t\t\t" + "Population: " + population);
		numberOfHomes.setText("\t\t\t" + "Number of Homes: " + numHomes);
		markets.setText("\t\t\t" + "Number of Markets: " + numMarkets);
		banks.setText("\t\t\t" + "Number of Banks: " + numBanks);
//		homeless = new JLabel ("\t\t\t" + "% Homeless: " + numHomeless);
//		unemployed = new JLabel ("\t\t\t" + "% Unemployed: " + numUnemployed);
		
		add(mainTitle);
		add(descipt);
		add(DayLabel);
		add(TimeLabel);
		add(pop);
		add(numberOfHomes);
		add(markets);
		add(banks);
//		add(homeless);
//		add(unemployed);
	}
}
