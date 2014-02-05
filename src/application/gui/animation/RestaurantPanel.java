package application.gui.animation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

//import americanRestaurant.AmericanRestaurantTable;
//import americanRestaurant.gui.AmericanCookGui;
//import americanRestaurant.gui.FoodGui;
import application.gui.animation.agentGui.Gui;

public class RestaurantPanel extends BuildingPanel implements ActionListener{
	
	//Italian Restaurant Data
	static final int NTABLES = 5;
    BufferedImage fridge = null;
    
    //American Restaurant Data
    static int americanNTables = 4;
	//public AmericanRestaurantTable[] tables = new AmericanRestaurantTable[americanNTables]; 		//declare list of tables
	static boolean isFood = true;
	ImageIcon grillImage, fridgeImage;
	public enum guiCookState {cooking, gathering, plating};
	public guiCookState cookState;
	ImageIcon fireImage = new ImageIcon ("res/fire.png");
	//private static List<FoodGui> foodGuis = new ArrayList<FoodGui>();
	
	
	public RestaurantPanel(String buildName, AnimationPanel ap) {
		super(buildName, ap);
        try {
            fridge = ImageIO.read(new File("res/fridge.png"));
        	} catch (IOException e) {
        	}
		// TODO Auto-generated constructor stub
	}
	
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;

		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(getBackground());

		g2.fillRect(0, 0, 600, 325 );	
		g2.setColor(Color.RED);

		if(name == "name")
			g.drawString("", 600/2, 10);
		else
			g.drawString(name, 600/2, 10);

		//different layouts based on their type       
		g2.setColor(Color.BLACK);
		
		
		if (name.toLowerCase().contains("chinese")) { //for Kristi's restaurant
			int xTableLocation = 200;
			int yTableLocation = 250;
			int TableWidth = 50;
			int TableHeight = 50;

			int xKitchenLocation = 385;
			int yKitchenLocation = 110;
			int KitchenTileWidth = 20;
			int KitchenTileHeight = 20;

			int xBreakRoomLocation = 310;
			int yBreakRoomLocation = 10;
			int BreakRoomWidth = 75;
			int BreakRoomHeight = 40;

			int xCashierLocation = 275;
			int yCashierLocation = 305;
			int CashierWidth = 50;
			int CashierHeight = 40;

			int xJailLocation = 405;
			int yJailLocation = 7;
			int JailWidth = 40;
			int JailHeight = 60;
			int JailBarsWidth = 3;

			int xLobbyLocation = 0;
			int yLobbyLocation = 10;
			int LobbyWidth = 300;
			int LobbyHeight = 50;

			//AmericanRestaurantTable 1
			g2.setColor(Color.PINK);
			g2.fillRect(xTableLocation - 150, yTableLocation, TableWidth, TableHeight);

			//AmericanRestaurantTable 2
			g2.setColor(Color.PINK);
			g2.fillRect(xTableLocation, yTableLocation, TableWidth, TableHeight);//200 and 250 need to be table params

			//AmericanRestaurantTable 3
			g2.setColor(Color.PINK);
			g2.fillRect(xTableLocation + 150, yTableLocation, TableWidth, TableHeight);

			//AmericanRestaurantTable 4
			g2.setColor(Color.PINK);
			g2.fillRect(xTableLocation, yTableLocation-175, TableWidth, TableHeight);//200 and 250 need to be table params


			//Kitchen
			g2.setColor(Color.WHITE);
			g2.fillRect(xKitchenLocation, yKitchenLocation, KitchenTileWidth, KitchenTileHeight);
			g2.fillRect(xKitchenLocation, yKitchenLocation + 40, KitchenTileWidth, KitchenTileHeight);
			g2.fillRect(xKitchenLocation + 20, yKitchenLocation + 20, KitchenTileWidth, KitchenTileHeight);
			g2.fillRect(xKitchenLocation + 20, yKitchenLocation + 60, KitchenTileWidth, KitchenTileHeight);
			g2.fillRect(xKitchenLocation + 40, yKitchenLocation, KitchenTileWidth, KitchenTileHeight);
			g2.fillRect(xKitchenLocation + 40, yKitchenLocation + 40, KitchenTileWidth, KitchenTileHeight);

			g2.setColor(Color.BLACK);
			g2.fillRect(xKitchenLocation, yKitchenLocation + 20, KitchenTileWidth, KitchenTileHeight);
			g2.fillRect(xKitchenLocation, yKitchenLocation + 60, KitchenTileWidth, KitchenTileHeight);
			g2.fillRect(xKitchenLocation + 20, yKitchenLocation, KitchenTileWidth, KitchenTileHeight);
			g2.fillRect(xKitchenLocation + 20, yKitchenLocation + 40, KitchenTileWidth, KitchenTileHeight);
			g2.fillRect(xKitchenLocation + 40, yKitchenLocation + 20, KitchenTileWidth, KitchenTileHeight);
			g2.fillRect(xKitchenLocation + 40, yKitchenLocation + 60, KitchenTileWidth, KitchenTileHeight);

			//Refrigerator
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRect(xKitchenLocation, yKitchenLocation - 20, KitchenTileWidth * 3, KitchenTileHeight);
			g2.setColor(Color.BLACK);
			g2.drawString("Fridge", xKitchenLocation + 5, yKitchenLocation - 5);

			//Plating Area
			g2.setColor(Color.ORANGE);
			g2.fillRect(xKitchenLocation - 20, yKitchenLocation, KitchenTileWidth, (KitchenTileHeight * 3) + 5);

			//Grill
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRect(xKitchenLocation, yKitchenLocation + 80, KitchenTileWidth * 3, KitchenTileHeight * 2);

			g2.setColor(Color.BLACK);
			g2.fillOval(xKitchenLocation + 13, yKitchenLocation + 85, (int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7));
			g2.fillOval(xKitchenLocation + 33, yKitchenLocation + 85, (int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7));
			g2.fillOval(xKitchenLocation + 13, yKitchenLocation + 100, (int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7));
			g2.fillOval(xKitchenLocation + 33, yKitchenLocation + 100, (int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7));

			//Break Room
			g2.setColor(Color.GRAY);
			g2.fillRect(xBreakRoomLocation, yBreakRoomLocation, BreakRoomWidth, BreakRoomHeight);
			g2.setColor(Color.BLACK);
			g2.drawString("Break Room", xBreakRoomLocation, yBreakRoomLocation + 35);

			//AmericanRestaurantCashier
			g2.setColor(Color.BLACK);
			g2.fillRect(xCashierLocation, yCashierLocation, CashierWidth, CashierHeight);
			g2.setColor(Color.GREEN);
			g2.drawString("$$$", xCashierLocation + 12, yCashierLocation + 35);

			//Jail
			g2.setColor(Color.WHITE);
			g2.fillRect(xJailLocation, yJailLocation, JailWidth, JailHeight);
			g2.setColor(Color.GRAY);
			g2.fillRect(xJailLocation, yJailLocation, JailBarsWidth, JailHeight);
			g2.fillRect(xJailLocation + 10, yJailLocation, JailBarsWidth, JailHeight);
			g2.fillRect(xJailLocation + 20, yJailLocation, JailBarsWidth, JailHeight);
			g2.fillRect(xJailLocation + 30, yJailLocation, JailBarsWidth, JailHeight);
			g2.fillRect(xJailLocation + 40, yJailLocation, JailBarsWidth, JailHeight);
			g2.setColor(Color.RED);
			g2.drawString("Jail", xJailLocation + 12, yJailLocation + 75);

			//Lobby
			g2.setColor(Color.BLUE);
			g2.fillRect(xLobbyLocation, yLobbyLocation, LobbyWidth, LobbyHeight);
			g2.setColor(Color.WHITE);
			g2.drawString("Lobby", xLobbyLocation + 2, yLobbyLocation + 45);
		}
		else if (name.toLowerCase().contains("italian")) { //for Carmen's restaurant
			g2.setColor(Color.CYAN);
			g2.fillRect(600-200, 10, 150, 50);
			g2.setColor(Color.PINK);
			g2.fillRect(0, 10, 200, 50); //lobby
			
			g2.drawImage(fridge, 500, 100, null);
			
			g2.setColor(Color.darkGray);
			g2.fillRect(10, 220, 40, 10);
			//cook's grill
			g2.fillRect(600-350, 325-80, 280, 5);
			g2.fillRect(600-350, 325-45, 280, 5);
			for(int j=0;j<15;j++)
				g2.fill3DRect(600-70-20*j, 325-80, 10, 40, true);
			
			g2.setColor(Color.LIGHT_GRAY);
			g2.fill3DRect(100, 325-95, 90, 15, true);
			g2.fill3DRect(100, 325-20, 90, 5, true);
			for(int k=0;k<5;k++)
				g2.fill3DRect(100+20*k, 325-80, 10, 60, true);
			
			//labels
			g2.setColor(Color.BLACK);
	        g2.drawString("Lobby", 10, 20);
	        g2.drawString("Break Room", 600 - 200, 20);
			//Here is the table
			g2.setColor(Color.ORANGE);
			
			for(int ix=1; ix<= NTABLES; ix++) {
	        	g2.fill3DRect(50*ix, 120, 40, 40, true);
	        }
		}
		else if (name.toLowerCase().contains("american")) { //for Josh's restaurant

			grillImage = new ImageIcon("res/grill.png");
			fridgeImage = new ImageIcon("res/fridge.gif");
			
			for (int i = 0; i < americanNTables; i++){
				g2.setColor(Color.ORANGE);
//				int x = tables[i].xCoordinate;
//				int y = tables[i].yCoordinate;
//				int l = tables[i].tableLength;
//				int w = tables[i].tableWidth;

//				g2.fillRect(x,y,l,w);
			}
			
			g.drawImage(grillImage.getImage(), 387, 215, this);
			g.drawImage(fridgeImage.getImage(), 470, 300, this);

			if (cookState == guiCookState.cooking)
				g.drawImage(fireImage.getImage(), 401, 200, this);
					
			for(Gui gui : guis) {
				if (gui.isPresent()) {
					gui.updatePosition();
				}
			}

			if (isFood) {
//				for (FoodGui f1: foodGuis) {
//					if (!f1.getStop() && f1.isPresent())
//						f1.updatePosition();
//				}
			}	
				
			for(Gui gui : guis) {
				if (gui.isPresent()) {
//					if (gui instanceof AmericanCookGui && cookState == guiCookState.gathering)
//						((AmericanCookGui) gui).drawIngredients(g2, this);
//					gui.draw(g2);
				}
			}

			if (isFood) {
//				for (FoodGui f1: foodGuis) {
//					if (f1.isPresent())
//						((FoodGui) f1).drawIcon(g, this);
//				}
			}
		}
		else if (name.toLowerCase().contains("mexican")) { //for Henry's restaurant
			
		}
		else if (name.toLowerCase().contains("burger")) { //for Nishant's restaurant
			
		} 
		 
		synchronized(guis){
			for(Gui gui : guis) {
				if (gui.isPresent()) {
					gui.updatePosition();
				}
			}

			for(Gui gui : guis) {
				if (gui.isPresent()) {
					gui.draw(g2);
				}
			}
			
		}
	}
	


	public void displayBuildingPanel() {
		myCity.displayBuildingPanel(this);
	}
}