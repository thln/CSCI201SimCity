package application.gui.animation.agentGui;

import java.awt.*;

import application.Phonebook;

public interface Gui {
	
	/*
	Toolkit tk = Toolkit.getDefaultToolkit();
	int WINDOWX = ((int) tk.getScreenSize().getWidth())/2; 
	int WINDOWY = (((int) tk.getScreenSize().getHeight())/2)*5/6;   
	*/

    public void updatePosition();
    public void draw(Graphics2D g);
    public boolean isPresent();
    public void DoExit();
    public int getXPos();
    public int getYPos();
}
