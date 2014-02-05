package application.gui.animation;

import java.awt.*;

import javax.swing.*;

public class Building {
	
	public BuildingPanel myBuildingPanel;
	private String name;
	ImageIcon myImage;
	int xLocation;
	int yLocation;
	
	public Building() {
		
	}
	
	public Building(String name, ImageIcon myImage, int xLocation, int yLocation) {
		this.name = name;
		this.myImage = myImage;
		this.xLocation = xLocation;
		this.yLocation = yLocation;
	}
	
	//Getters and Setters
	public BuildingPanel getMyBuildingPanel() {
		return myBuildingPanel;
	}

	public void setMyBuildingPanel(BuildingPanel myBuildingPanel) {
		this.myBuildingPanel = myBuildingPanel;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public ImageIcon getMyImage() {
		return myImage;
	}

	public void setMyImage(ImageIcon myImage) {
		this.myImage = myImage;
	}
	
	public int getxLocation() {
		return xLocation;
	}

	public void setxLocation(int xLocation) {
		this.xLocation = xLocation;
	}

	public int getyLocation() {
		return yLocation;
	}

	public void setyLocation(int yLocation) {
		this.yLocation = yLocation;
	}
	
	public void setLocation(int x, int y) {
		setxLocation(x);
		setyLocation(y);
	}
	
	public Rectangle getRect() {
		return (new Rectangle(xLocation, yLocation, myImage.getIconWidth(), myImage.getIconHeight()));
	}
	
	//Display building methods
	public void displayBuilding() {
		myBuildingPanel.displayBuildingPanel();
	}
}
