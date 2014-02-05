package application.gui.animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import application.Phonebook;
//import application.gui.animation.agentGui.BusGuiHorizontal;
//import application.gui.animation.agentGui.BusGuiVertical;
import application.gui.animation.agentGui.CopGui;
import application.gui.animation.agentGui.Gui;
import application.gui.animation.agentGui.VehicleHorizontalGui;
import application.gui.animation.agentGui.VehicleVerticalGui;

public class CityPanel extends JPanel implements ActionListener, MouseListener {
	
	private final int WINDOWX = 600;
    private final int WINDOWY = 325;
	
	AnimationPanel animationPanel;
	//private List<Gui> guis = Collections.synchronizedList(new ArrayList<Gui>());
	private List<CityGui> cGuis = Collections.synchronizedList(new ArrayList<CityGui>());
	public ArrayList<Building> buildings = new ArrayList<Building>();
	Dimension Msize = new Dimension(75, 75);
	Dimension Bsize = new Dimension(75, 75);

	public BufferedImage background = null;

	ImageIcon bank = new ImageIcon("res/bank.png", "bank");
	ImageIcon restaurant = new ImageIcon("res/restaurant.png", "restaurant");
	ImageIcon market = new ImageIcon("res/market.png", "market");
	ImageIcon house = new ImageIcon("res/mansion.png", "mansion");
	ImageIcon apartment = new ImageIcon("res/apartment.png", "apartment");
	ImageIcon rave = new ImageIcon("res/rave.jpeg");
	ImageIcon park = new ImageIcon("res/grass.jpg", "park");
	ImageIcon hollywoodSign = new ImageIcon("res/hollywoodSign.png");
	ImageIcon oneWaySignLeft = new ImageIcon("res/OneWayLeft.png");
	ImageIcon oneWaySignRight = new ImageIcon("res/OneWayRight.png");
	ImageIcon oneWaySignUp = new ImageIcon("res/OneWayUp.png");
	ImageIcon oneWaySignDown = new ImageIcon("res/OneWayDown.png");
	ImageIcon bench = new ImageIcon("res/bench.png");
	ImageIcon fountain = new ImageIcon("res/fountain.png");

	public ImageIcon roadHorizontal = new ImageIcon("res/roadsHorizontal.png");
	public ImageIcon roadVertical = new ImageIcon("res/roadsVertical.png");
	public ImageIcon busStop = new ImageIcon("res/bus_stop.png");	
	//public BusGuiHorizontal busA = new BusGuiHorizontal();
	//public BusGuiVertical busB = new BusGuiVertical();

	public ImageIcon tree = new ImageIcon("res/tree.png");
	public ImageIcon bush = new ImageIcon("res/bush.png");
	//public BusGuiHorizontal busA = new BusGuiHorizontal();
	//public BusGuiVertical busB = new BusGuiVertical();
	public VehicleHorizontalGui carA = new VehicleHorizontalGui();
	public VehicleVerticalGui carB = new VehicleVerticalGui();
	public CopGui copCar = new CopGui();

	public CityPanel(AnimationPanel animationPanel) {

		this.animationPanel = animationPanel;
		addMouseListener(this);

		setPreferredSize(new Dimension(600, 325));
		setMaximumSize(new Dimension(600,325));
		setMinimumSize(new Dimension(600, 325));
		setBorder(BorderFactory.createLoweredBevelBorder());
		setVisible(true);
		setLayout(null);

		try {
			background = ImageIO.read(new File("res/tiles.png"));
		} catch (IOException e) {
		}

		addBuilding("Chinese Restaurant", (WINDOWX / 2)+20, 5);
//        addBuilding("Mexican Restaurant", WINDOWX-590, WINDOWY-90);
//        addBuilding("American Restaurant", WINDOWX-395, WINDOWY-90);
//        addBuilding("Italian Restaurant", WINDOWX-123, 20);
//        addBuilding("Seafood Restaurant", WINDOWX-175, 100+market.getIconHeight()+2);

        addBuilding("East Market", WINDOWX - 85, 108);
        addBuilding("West Market", WINDOWX - 520, 275);

        addBuilding("East Bank", (WINDOWX / 2), 260);
        addBuilding("West Bank", (WINDOWX / 2)-105, 10);

        addBuilding("Mansion", 25, 115);
        addBuilding("West Apartment", 0, 0);
        addBuilding("East Apartment", 510, WINDOWY-55);
        addBuilding("Park",(WINDOWX/2)-80,(WINDOWY/2)-47);



		addGui(carA);
		addGui(carB);
		addGui(copCar);

		Timer timer = new Timer(10, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(getBackground());
		g2.fillRect(0, 0, 600, 325 );
		g2.drawImage(background, 0, 0, 600, 325, null);

        //Horizontal Roads
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 70, 600, 39);
        g2.fillRect(0, 190, 600, 39);

        //Vertical Roads
        g2.fillRect(160, 0, 39, 325);
        g2.fillRect(380, 0, 39, 325);

		//Intersections
//		g2.setColor(Color.PINK);
//		g2.fillRect((int) Phonebook.getPhonebook().intersection1.getIntersection().getX(),
//				(int) Phonebook.getPhonebook().intersection1.getIntersection().getY(),
//				(int) Phonebook.getPhonebook().intersection1.getIntersection().getWidth(),
//				(int) Phonebook.getPhonebook().intersection1.getIntersection().getHeight());
//		g2.fillRect((int) Phonebook.getPhonebook().intersection2.getIntersection().getX(),
//				(int) Phonebook.getPhonebook().intersection2.getIntersection().getY(),
//				(int) Phonebook.getPhonebook().intersection2.getIntersection().getWidth(),
//				(int) Phonebook.getPhonebook().intersection2.getIntersection().getHeight());
//		g2.fillRect((int) Phonebook.getPhonebook().intersection3.getIntersection().getX(),
//				(int) Phonebook.getPhonebook().intersection3.getIntersection().getY(),
//				(int) Phonebook.getPhonebook().intersection3.getIntersection().getWidth(),
//				(int) Phonebook.getPhonebook().intersection3.getIntersection().getHeight());
//		g2.fillRect((int) Phonebook.getPhonebook().intersection4.getIntersection().getX(),
//				(int) Phonebook.getPhonebook().intersection4.getIntersection().getY(),
//				(int) Phonebook.getPhonebook().intersection4.getIntersection().getWidth(),
//				(int) Phonebook.getPhonebook().intersection4.getIntersection().getHeight());

		//Crosswalks
		drawCrosswalks(g2);

		//Bus Stops
		g2.drawImage(busStop.getImage(), 127, 28, null);
        g2.drawImage(busStop.getImage(), 127, 230, null);
        g2.drawImage(busStop.getImage(), 420, 28, null);
        g2.drawImage(busStop.getImage(), 420, 230, null);
		
		//Bus Parking
//		g2.setColor(Color.CYAN);
//		g2.fillRect((int) Phonebook.getPhonebook().busParking1H.getBusParking().getX(),
//				(int) Phonebook.getPhonebook().busParking1H.getBusParking().getY(),
//				(int) Phonebook.getPhonebook().busParking1H.getBusParking().getWidth(),
//				(int) Phonebook.getPhonebook().busParking1H.getBusParking().getHeight());
//		g2.fillRect((int) Phonebook.getPhonebook().busParking1V.getBusParking().getX(),
//				(int) Phonebook.getPhonebook().busParking1V.getBusParking().getY(),
//				(int) Phonebook.getPhonebook().busParking1V.getBusParking().getWidth(),
//				(int) Phonebook.getPhonebook().busParking1V.getBusParking().getHeight());
//		g2.fillRect((int) Phonebook.getPhonebook().busParking2H.getBusParking().getX(),
//				(int) Phonebook.getPhonebook().busParking2H.getBusParking().getY(),
//				(int) Phonebook.getPhonebook().busParking2H.getBusParking().getWidth(),
//				(int) Phonebook.getPhonebook().busParking2H.getBusParking().getHeight());
//		g2.fillRect((int) Phonebook.getPhonebook().busParking2V.getBusParking().getX(),
//				(int) Phonebook.getPhonebook().busParking2V.getBusParking().getY(),
//				(int) Phonebook.getPhonebook().busParking2V.getBusParking().getWidth(),
//				(int) Phonebook.getPhonebook().busParking2V.getBusParking().getHeight());
//		g2.fillRect((int) Phonebook.getPhonebook().busParking3H.getBusParking().getX(),
//				(int) Phonebook.getPhonebook().busParking3H.getBusParking().getY(),
//				(int) Phonebook.getPhonebook().busParking3H.getBusParking().getWidth(),
//				(int) Phonebook.getPhonebook().busParking3H.getBusParking().getHeight());
//		g2.fillRect((int) Phonebook.getPhonebook().busParking3V.getBusParking().getX(),
//				(int) Phonebook.getPhonebook().busParking3V.getBusParking().getY(),
//				(int) Phonebook.getPhonebook().busParking3V.getBusParking().getWidth(),
//				(int) Phonebook.getPhonebook().busParking3V.getBusParking().getHeight());
//		g2.fillRect((int) Phonebook.getPhonebook().busParking4H.getBusParking().getX(),
//				(int) Phonebook.getPhonebook().busParking4H.getBusParking().getY(),
//				(int) Phonebook.getPhonebook().busParking4H.getBusParking().getWidth(),
//				(int) Phonebook.getPhonebook().busParking4H.getBusParking().getHeight());
//		g2.fillRect((int) Phonebook.getPhonebook().busParking4V.getBusParking().getX(),
//				(int) Phonebook.getPhonebook().busParking4V.getBusParking().getY(),
//				(int) Phonebook.getPhonebook().busParking4V.getBusParking().getWidth(),
//				(int) Phonebook.getPhonebook().busParking4V.getBusParking().getHeight());

        //One Way Signs
        g2.drawImage(oneWaySignLeft.getImage(), 330, 220, null);
        g2.drawImage(oneWaySignRight.getImage(), 270, 30, null);
        g2.drawImage(oneWaySignUp.getImage(), 200, 140, null);
        g2.drawImage(oneWaySignDown.getImage(), 420, 110, null);

		//Benches
        g2.drawImage(bench.getImage(), 5, 285, null);
        g2.drawImage(bench.getImage(), 530, 40, null);
        g2.drawImage(bench.getImage(), 440, 110, null);
        g2.drawImage(bench.getImage(), 530, 230, null);

		//Fountain
        g2.drawImage(fountain.getImage(), 215, 275, null);

		//Drawing all buildings
		for (int i=0; i<buildings.size(); i++ ) {
			Building b = buildings.get(i);
			g2.drawImage(b.getMyImage().getImage(), b.getxLocation(), b.getyLocation(), null);
		}

		//Park
        g2.drawImage(hollywoodSign.getImage(), 230, 138, null);
        g2.drawImage(bush.getImage(), 214, 105, null);
        g2.drawImage(bush.getImage(), 244, 105, null);
        g2.drawImage(bush.getImage(), 274, 105, null);
        g2.drawImage(bush.getImage(), 304, 105, null);
        g2.drawImage(bush.getImage(), 334, 105, null);

        g2.drawImage(bush.getImage(), 214, 165, null);
        g2.drawImage(bush.getImage(), 244, 165, null);
        g2.drawImage(bush.getImage(), 274, 165, null);
        g2.drawImage(bush.getImage(), 304, 165, null);
        g2.drawImage(bush.getImage(), 334, 165, null);

        //Mansion trees
        g2.drawImage(tree.getImage(), 0, 100, null);
        g2.drawImage(tree.getImage(), 0, 125, null);
        g2.drawImage(tree.getImage(), 0, 150, null);
        g2.drawImage(tree.getImage(), 125, 100, null);
        g2.drawImage(tree.getImage(), 125, 125, null);
        g2.drawImage(tree.getImage(), 125, 150, null);

		//Drawing all People guis
		synchronized (cGuis) {
			for(CityGui gui : cGuis) {
				if (gui.myGui.isPresent()) {
					gui.myGui.updatePosition();
				}
			}
			for(CityGui gui : cGuis) {
				if (gui.myGui.isPresent()) {
					gui.myGui.draw(g2);
				}
			}
		}
	}
	
	public class CityGui {
		Gui myGui;
		Gui collidingGui;
		public CityGui(Gui gui) {
			myGui = gui;
		}
	}
	
	public boolean notColliding(CityGui gui1, CityGui gui2) {
		synchronized (cGuis) {
			for(CityGui gui : cGuis) {
				if(gui.myGui.getXPos() == gui2.myGui.getXPos() || gui.myGui.getYPos() == gui2.myGui.getYPos()) {
					gui.collidingGui = gui2.myGui;
					return false;
				}
			}
		}
		return true;
	}
	public void addGui(Gui gui) {
		cGuis.add(new CityGui(gui));   
	}
	
	/*
	public void addGui(Gui gui) {
		guis.add(gui);   
	}*/

	public void addBuilding(String name, int x, int y) {
		Building building = new Building();

		//Bank building
		if (name.toLowerCase().contains("bank")) {
			building.setMyImage(bank);
			building.setLocation(x, y);
		}
		//Market building
		else if (name.toLowerCase().contains("market")) {
			building.setMyImage(market);
			building.setLocation(x, y);
		}
		//Restaurant building
		else if (name.toLowerCase().contains("restaurant")) {
			building.setMyImage(restaurant);
			building.setLocation(x, y);

		}
		//House building
		else if (name.toLowerCase().contains("mansion")) {
			building.setMyImage(house);
			building.setLocation(x, y);
		}

		//Apartment building
		else if (name.toLowerCase().contains("apartment")) {
			building.setMyImage(apartment);
			building.setLocation(x, y);
		}

		//Park in the Middle
		else if (name.toLowerCase().contains("park")){
			building.setMyImage(park);
			building.setLocation(x,y);
		}

		building.setName(name);
		buildings.add(building);
	}

	public String toString() {
		return "City JPanel";
	}

	public ArrayList<Building> getBuildings() {
		return buildings;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//If any of the icons are clicked, it will find and display its corresponding animation panel
		for ( int i = 0; i<buildings.size(); i++ ) {
			Building b = buildings.get(i);
			if (b.getRect().contains(e.getX(), e.getY())) {
				b.displayBuilding();
				return;
			}
		}

		animationPanel.displayBlankBuildingPanel();
	}



	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void drawCrosswalks(Graphics2D g2) {
		//Road
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getX(),
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getY(),
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getHeight());
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getX(),
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getY(),
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getHeight());
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getX(),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getY(),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight());
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getX(),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getY(),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight());
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getX(),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getY(),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight());
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getX(),
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getY(),
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getHeight());
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getX(),
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getY(),
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getHeight());
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getX(),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getY(),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight());
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getX(),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getY(),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight());
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getX(),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getY(),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight());
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getX(),
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getY(),
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getHeight());
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getX(),
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getY(),
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getHeight());




		//Details
		g2.setColor(Color.WHITE);

		//Crosswalk 1
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20*2)),
				(int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20*4)),
				(int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20*6)),
				(int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20*8)),
				(int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20*10)),
				(int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20*12)),
				(int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20*14)),
				(int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20*16)),
				(int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20*18)),
				(int) (Phonebook.getPhonebook().crosswalk1.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getHeight());

		//Crosswalk 2
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20*2)),
				(int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20*4)),
				(int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20*6)),
				(int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20*8)),
				(int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20*10)),
				(int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20*12)),
				(int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20*14)),
				(int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20*16)),
				(int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20*18)),
				(int) (Phonebook.getPhonebook().crosswalk2.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getHeight());

		//Crosswalk 3
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20*2)),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20*4)),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20*6)),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20*8)),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20*10)),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20*12)),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20*14)),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20*16)),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20*18)),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getHeight()/20);

		//Crosswalk 4
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20*2)),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20*4)),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20*6)),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20*8)),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20*10)),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20*12)),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20*14)),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20*16)),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20*18)),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getHeight()/20);

		//Crosswalk 5
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20*2)),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20*4)),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20*6)),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20*8)),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20*10)),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20*12)),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20*14)),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20*16)),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20*18)),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getHeight()/20);

		//Crosswalk 6
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20*2)),
				(int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20*4)),
				(int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20*6)),
				(int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20*8)),
				(int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20*10)),
				(int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20*12)),
				(int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20*14)),
				(int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20*16)),
				(int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20*18)),
				(int) (Phonebook.getPhonebook().crosswalk6.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getHeight());

		//Crosswalk 7
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20*2)),
				(int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20*4)),
				(int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20*6)),
				(int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20*8)),
				(int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20*10)),
				(int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20*12)),
				(int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20*14)),
				(int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20*16)),
				(int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20*18)),
				(int) (Phonebook.getPhonebook().crosswalk7.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getHeight());

		//Crosswalk 8
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20*2)),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20*4)),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20*6)),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20*8)),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20*10)),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20*12)),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20*14)),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20*16)),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20*18)),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getHeight()/20);

		//Crosswalk 9
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20*2)),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20*4)),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20*6)),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20*8)),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20*10)),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20*12)),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20*14)),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20*16)),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20*18)),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getHeight()/20);

		//Crosswalk 10
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20*2)),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20*4)),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20*6)),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20*8)),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20*10)),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20*12)),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20*14)),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20*16)),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20);
		g2.fillRect((int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getX(),
				(int) (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getY() + (Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20*18)),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getWidth(),
				(int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getHeight()/20);

		//Crosswalk 11
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20*2)),
				(int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20*4)),
				(int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20*6)),
				(int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20*8)),
				(int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20*10)),
				(int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20*12)),
				(int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20*14)),
				(int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20*16)),
				(int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20*18)),
				(int) (Phonebook.getPhonebook().crosswalk11.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getHeight());

		//Crosswalk 12
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20*2)),
				(int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20*4)),
				(int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20*6)),
				(int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20*8)),
				(int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20*10)),
				(int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20*12)),
				(int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20*14)),
				(int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20*16)),
				(int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getHeight());
		g2.fillRect((int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getX() + (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20*18)),
				(int) (Phonebook.getPhonebook().crosswalk12.getCrosswalk().getY()),
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getWidth()/20,
				(int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getHeight());
	
	}
}
