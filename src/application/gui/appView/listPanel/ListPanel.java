package application.gui.appView.listPanel;

import javax.swing.*;

import person.Crook;
import person.Deadbeat;
import person.Person;
import person.Wealthy;
import person.Worker;
import person.Worker.Job;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

import application.*;
import application.gui.appView.*;
import application.gui.appView.controlPanel.*;


public class ListPanel extends JPanel implements ActionListener{
	private Application app;
	
	//Scroll Panel
	public JScrollPane pane =
	            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	                    		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private JPanel listPane;
	private List<JButton> buttons = new ArrayList<JButton>(); 
	
	//Info Panel
	private JPanel infoPersonPane;
	//private JPanel infoBuildingPane;
	private List<Profile> people = new ArrayList<Profile>();
	private JTextArea personInfoArea = new JTextArea();
	
	private JLabel infoName = new JLabel("Name: ");
	private JLabel infoType = new JLabel("Type: ");
	private JLabel infoMoney = new JLabel("Money: ");
	private JLabel infoHousing = new JLabel("Housing: ");
	private JLabel infoJobLoc = new JLabel ("Job Location: ");
	private JLabel infoJobTitle = new JLabel ("Job Title: ");
	private JLabel infoStartTime = new JLabel ("Start Time: ");
	private JLabel infoLunchTime = new JLabel ("Lunch Time: ");
	private JLabel infoEndTime = new JLabel ("End Time: ");	
	
	//private JLabel infoTest = new JLabel("test: ");
	
	public ListPanel(ApplicationPanel appPanel, Application app){
		this.app = app;
		//setLayout(new GridLayout(1,2));
		this.setLayout((new BoxLayout((Container) this, BoxLayout.X_AXIS)));
		
		listPane = new JPanel();
		listPane.setLayout(new BoxLayout((Container)listPane, BoxLayout.Y_AXIS));
		pane.setViewportView(listPane);
        pane.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        //Dimension paneDim = new Dimension((1/4)*(this.getWidth()),this.getHeight());
		Dimension paneDim = new Dimension(140, 500);
        pane.setPreferredSize(paneDim);
		pane.setMinimumSize(paneDim);
		pane.setMaximumSize(paneDim);
		updateList();
        
		add(pane);
		
		infoPersonPane = new JPanel();
		infoPersonPane.setLayout(new GridLayout(0,1));
		infoName.setVisible(false);
		infoType.setVisible(false);
		infoMoney.setVisible(false);
		infoHousing.setVisible(false);
		infoJobLoc.setVisible(false);
		infoJobTitle.setVisible(false);
		infoStartTime.setVisible(false);
		infoLunchTime.setVisible(false);
		infoEndTime.setVisible(false);
		infoPersonPane.add(infoName);
		infoPersonPane.add(infoType);
		infoPersonPane.add(infoMoney);
		infoPersonPane.add(infoHousing);
		infoPersonPane.add(infoJobLoc);
		infoPersonPane.add(infoJobTitle);
		infoPersonPane.add(infoStartTime);
		infoPersonPane.add(infoLunchTime);
		infoPersonPane.add(infoEndTime);
		add(infoPersonPane);
		
//		infoBuildingPane = new JPanel();
//		infoBuildingPane.setLayout(new GridLayout(0,1));
//		infoBuildingPane.add(infoTest);
//		add(infoBuildingPane);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int i = 0;
		for(JButton temp:buttons){
			if(e.getSource() == temp){
				updateInfoPane(people.get(i),i);				
			}
			i++;
		}
	}

	public void addPerson (String name ,int money, String type,
			String jobTitle, String jobLocation, String housing, int startT, int lunchT, int endT){
		if(name != null){
			JButton button = new JButton(name);
				if(type.equals("Wealthy")){
					button.setBackground(Color.white);
				}
				else if(type.equals("Crook")){
					button.setBackground(Color.lightGray);
				}
				else if(type.equals("Worker")){
					button.setBackground(Color.cyan);
				}
				else if(type.equals("Deadbeat")){
					button.setBackground(Color.orange);
				}
				else{
					button.setBackground(Color.green);
				}
			Dimension paneSize = listPane.getSize();
			Dimension buttonSize = new Dimension(paneSize.width, (1/7)* (paneSize.height));
			button.setPreferredSize(buttonSize);
			button.addActionListener(this);
			buttons.add(button);
			listPane.add(button);
			people.add(new Profile(name, money, type, jobTitle,jobLocation, housing, startT,lunchT,endT));
			//validate();
		}
	}
	
	public void updateInfoPane(Profile pf, int index){ //updates person info on the right side 
		if(pf != null){
			infoName.setVisible(true);
			infoType.setVisible(true);
			infoMoney.setVisible(true);
			infoHousing.setVisible(true);
			infoName.setText("Name: " + pf.getName());
			infoType.setText("Type: " + pf.getType());
			infoMoney.setText("Money: " + pf.getMoney());
			infoHousing.setText("Housing: " + pf.getHousing());
			if(pf.getType() == "Worker")
			{
				infoJobLoc.setVisible(true);
				infoJobTitle.setVisible(true);
				infoStartTime.setVisible(true);
				infoLunchTime.setVisible(true);
				infoEndTime.setVisible(true);
				infoJobLoc.setText("Job Location: " + pf.getJobLocation());
				infoJobTitle.setText("Job Title: " + pf.getJobTitle());
				infoStartTime.setText("Start Time: " + pf.getStartT());
				infoLunchTime.setText("Lunch Time: " + pf.getLunchT());
				infoEndTime.setText("End Time: " + pf.getEndT());
			}
			else
			{
				infoJobLoc.setVisible(false);
				infoJobTitle.setVisible(false);
				infoStartTime.setVisible(false);
				infoLunchTime.setVisible(false);
				infoEndTime.setVisible(false);
			}

		}
	}
	
	public void updateList(){ //updates button list on the left side
		people.clear();
		buttons.clear();
		listPane.removeAll();
		for(int i = 0; i<app.getPopulationSize();i++){
			Person temp = app.getPerson(i);
			JButton button = new JButton(temp.getName());
			if(temp instanceof Crook){
				people.add(new Profile(temp.getName(), (int) temp.getMoney(), "Crook", temp.getCurrentRoleName(), null, temp.getHousing().type, 0,0,0));
				button.setBackground(Color.lightGray);
			}
			else if(temp instanceof Deadbeat){
				people.add(new Profile(temp.getName(), (int) temp.getMoney(), "Deadbeat", temp.getCurrentRoleName(), null, temp.getHousing().type, 0,0,0));
				button.setBackground(Color.orange);
			}
			else if(temp instanceof Worker){
				Job tempJob = ((Worker) temp).getJob();
				people.add(new Profile(temp.getName(), (int) temp.getMoney(), "Worker", tempJob.getTitle(), tempJob.getJobLoc(), temp.getHousing().type,
						tempJob.getStartTime().getTimeHour(),tempJob.getLunchTime().getTimeHour(),tempJob.getEndTime().getTimeHour()));
				button.setBackground(Color.white);
			}
			else if(temp instanceof Wealthy){
				people.add(new Profile(temp.getName(), (int) temp.getMoney(), "Wealthy", temp.getCurrentRoleName(), null, temp.getHousing().type, 0,0,0));
				button.setBackground(Color.green);
			}
			else {
				//if the person is none of the types of the type can't be detected, output a black button to indicate an error
				people.add(new Profile(temp.getName(), (int) temp.getMoney(), "None", temp.getCurrentRoleName(), null, temp.getHousing().type, 0,0,0));
				button.setBackground(Color.black);
			}
            button.setMaximumSize(new Dimension(125, 25));
			button.addActionListener(this);
			buttons.add(button);
			listPane.add(button);
			validate();
		}
		
	}
	public void editProfile(Profile pf, int index){
		
	}
	
	public int findIndex(String name, String type){
		int index = 0;
		for (index = 0; index<people.size(); index++){
			if(people.get(index).getName()== name && people.get(index).getType()== type){
				break;
			}
		}
		if(index == people.size()){
			index = -1;
		}
		return index;
	}
	public Profile getProfile(int index){
		return people.get(index);
	}
	public String getName(int index){
		return people.get(index).getName();
	}
	public int getListSize(){
		return people.size();
	}
	
	public class Profile{
		String name;
		String type;
		int money;
		String jobTitle;
		String jobLocation;
		String housing;
		int startT;
		int lunchT;
		int endT;
		int current_location;
		int home_location;
		int index;
		
		public Profile (String name ,int money, String type,
				String jobTitle, String jobLocation, String housing, int startT, int lunchT, int endT){
			this.name = name;
			this.money = money;
			this.type = type;
			this.jobTitle = jobTitle;
			this.jobLocation = jobLocation;
			this.housing = housing;
			this.startT = startT;
			this.lunchT = lunchT;
			this.endT = endT;
		}
		public Profile(){
			this.name = null;
			this.money = 0;
			this.type = null;
			this.jobTitle = null;
			this.jobLocation = null;
			this.housing = null;
			this.startT = 0;
			this.lunchT = 0;
			this.endT = 0;
			
		}
		
		public int getIndex(){
			return index;
		}
		
		public void setIndex(int index){
			this.index = index;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getMoney() {
			return money;
		}

		public void setMoney(int money) {
			this.money = money;
		}

		public String getJobTitle() {
			return jobTitle;
		}

		public void setJobTitle(String jobTitle) {
			this.jobTitle = jobTitle;
		}

		public String getJobLocation() {
			return jobLocation;
		}

		public void setJobLocation(String jobLocation) {
			this.jobLocation = jobLocation;
		}

		public String getHousing()
		{
			return housing;
		}
		
		public void setHousing(String h)
		{
			this.housing = h;
		}
		
		public int getStartT() {
			return startT;
		}

		public void setStartT(int startT) {
			this.startT = startT;
		}

		public int getLunchT() {
			return lunchT;
		}

		public void setLunchT(int lunchT) {
			this.lunchT = lunchT;
		}

		public int getEndT() {
			return endT;
		}

		public void setEndT(int endT) {
			this.endT = endT;
		}

		public int getCurrent_location() {
			return current_location;
		}

		public void setCurrent_location(int current_location) {
			this.current_location = current_location;
		}

		public int getHome_location() {
			return home_location;
		}

		public void setHome_location(int home_location) {
			this.home_location = home_location;
		}
	}
	
}
