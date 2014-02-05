package application;

import javax.swing.ImageIcon;

import person.Role;

public interface Restaurant {
	
	ImageIcon restaurant = new ImageIcon("res/restaurant.png", "restaurant");
	boolean isOpen();

}
