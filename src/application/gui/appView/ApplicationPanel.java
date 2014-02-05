package application.gui.appView;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import application.*;
import application.gui.*;
import application.gui.animation.*;
import application.gui.animation.agentGui.*;
import application.gui.appView.controlPanel.*;
import application.gui.appView.listPanel.*;


public class ApplicationPanel extends JPanel{
	
	private ControlPanel cp;
	private ListPanel lp;
	private ApplicationGui appGui;
	public ApplicationPanel(ApplicationGui appGui, Application app){
		cp = new ControlPanel(this, app);
		lp = new ListPanel(this, app);
		this.appGui = appGui;
		setLayout(new GridLayout(2,1));
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		//Control Panel
		c.gridx = 0;
		c.gridy = 0;
		
		cp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(cp);		
		
		//InfoPanel
		//TODO set size so that info panel is 75% and controPanel is 25% of total height
		c.gridx = 0;
		c.gridy = 1;
		
		lp.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		add(lp,c);
	}
	
	public ControlPanel getControlPanel(){
		return cp;
	}
	
	public ListPanel getListPanel(){
		return lp;
	}
	
	public void updateGroups(){
		System.out.println("Here");
	}
	public ApplicationGui getAppGui(){
		return appGui;
	}
	
	public void updateInfo()
	{
		lp.updateList();
		cp.getAddPanel().dashboardUpdate();
		
	}
}
