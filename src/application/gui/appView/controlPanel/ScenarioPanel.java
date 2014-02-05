package application.gui.appView.controlPanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import application.*;
import application.gui.appView.*;
import application.gui.appView.listPanel.ListPanel.Profile;

public class ScenarioPanel extends JPanel implements ActionListener {
	
	JPanel mainPanel;
	//JTextField nameField;
	//JTextField moneyField;
	//JButton saveButton;
	//JComboBox selectPerson;
	ApplicationPanel appPanel;
	Application app;
	ControlPanel cp;
	boolean ScenarioRunning;
	JButton runFullScenario = new JButton("Run Full Scenario");
	JButton runScenarioA = new JButton("Run Scenario A");
	JButton runScenarioB = new JButton("Run Scenario B");
	JButton runScenarioC = new JButton("Run Scenario C");
	JButton runScenarioD = new JButton("Run Scenario D");
	JButton stopRunningScenario = new JButton("End Running Scenario");
	//JButton closeBuilding;
	//JButton openBuilding;
	//int editIndex = 0;
	
	public ScenarioPanel(ControlPanel cp, Application app, ApplicationPanel appPanel){
		this.cp = cp;
		this.app = app;
		this.appPanel = appPanel;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(7,1));
		JLabel selectLabel = new JLabel("         Select A Scenario");
		mainPanel.add(selectLabel);
		
		runFullScenario.addActionListener(this);
		runScenarioA.addActionListener(this);
		runScenarioB.addActionListener(this);
		runScenarioC.addActionListener(this);
		runScenarioD.addActionListener(this);
		stopRunningScenario.addActionListener(this);
		stopRunningScenario.setVisible(false);
		
		runFullScenario.setPreferredSize(new Dimension (300, 40));
		
		mainPanel.add(runFullScenario);
		mainPanel.add(runScenarioA);
		mainPanel.add(runScenarioB);
		mainPanel.add(runScenarioC);
		mainPanel.add(runScenarioD);
		mainPanel.add(stopRunningScenario);
		
		//selectPerson = new JComboBox();
		//selectPerson.setSelectedIndex(0);
		//selectPerson.addActionListener(this);
		//updateNamesList();
		//mainPanel.add(selectPerson);
		
		//JLabel nameLabel = new JLabel("Name");
		//nameField = new JTextField(10);
		//mainPanel.add(nameLabel);
		//mainPanel.add(nameField);
		
		//JLabel moneyLabel = new JLabel("Money");
		//moneyField = new JTextField(10);
		//mainPanel.add(moneyLabel);
		//mainPanel.add(moneyField);
		
		//saveButton = new JButton("Save");
		//saveButton.addActionListener(this);
		//mainPanel.add(saveButton);
		
//		closeBuilding = new JButton("Close Building");
//		closeBuilding.addActionListener(this);
//		mainPanel.add(closeBuilding);
		
//		openBuilding = new JButton("Open Building");
//		openBuilding.addActionListener(this);
//		mainPanel.add(openBuilding);
		
		
		add(mainPanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		runFullScenario.setEnabled(false);
		runScenarioA.setEnabled(false);
		runScenarioB.setEnabled(false);
		runScenarioC.setEnabled(false);
		runScenarioD.setEnabled(false);
		stopRunningScenario.setEnabled(true);
		stopRunningScenario.setVisible(true);
		
		if(e.getSource() == runFullScenario)
		{
			stopRunningScenario.setText("Stop Full Scenario");
			app.runFullScenario();
		}
		if(e.getSource() == runScenarioA)
		{
			stopRunningScenario.setText("Stop Scenario A");
			app.runScenarioA();
		}
		if(e.getSource() == runScenarioB)
		{
			stopRunningScenario.setText("Stop Scenario B");
			app.runScenarioB();
		}
		if(e.getSource() == runScenarioC)
		{
			stopRunningScenario.setText("Stop Scenario C");
			app.runScenarioC();
		}
		if(e.getSource() == runScenarioD)
		{
			stopRunningScenario.setText("Stop Scenario D");
			app.runScenarioD();
		}
		if(e.getSource() == stopRunningScenario)
		{
			runFullScenario.setEnabled(true);
			runScenarioA.setEnabled(true);
			runScenarioB.setEnabled(true);
			runScenarioC.setEnabled(true);
			runScenarioD.setEnabled(true);
			stopRunningScenario.setEnabled(false);
			stopRunningScenario.setVisible(false);
			app.stopRunningScenario();
			System.exit(0);
		}
		
		appPanel.updateInfo();
		
		// TODO Auto-generated method stub
//		if (e.getSource() == saveButton){
//			app.editPerson(selectPerson.getSelectedIndex(), nameField.getText(), Integer.parseInt(moneyField.getText()));
//		}
//		
//		if(e.getSource() == selectPerson){
//			editIndex = selectPerson.getSelectedIndex();
//			updateTextFields();
//		}
//		
//		if (e.getSource() == closeBuilding){
//			Phonebook.getPhonebook().closeBuilding("eastBank");
//		}
//		
//		if (e.getSource() == openBuilding){
//			Phonebook.getPhonebook().openBuilding("eastBank");
//		}
	}
	
	public void updateNamesList(){
		//TODO Fix this Function
		if(app.getPopulationSize()> 0){
			for(int i = 0; i<app.getPopulationSize(); i++){
				String tempName = app.getPerson(i).getName();
				//selectPerson.addItem((String) tempName);
			}
			validate();
		}
	}
	
	public void updateTextFields(){
		if(appPanel.getListPanel().getListSize()>0){
//			nameField = new JTextField(appPanel.getListPanel().getProfile(editIndex).getName(), 10);
//			nameField = new JTextField(Integer.toString(appPanel.getListPanel().getProfile(editIndex).getMoney()), 10);
		}
	}

}
