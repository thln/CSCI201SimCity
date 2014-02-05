package application.gui.animation.agentGui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

import person.Person;
import application.Phonebook;

public class PersonGui extends CityGui {

	private Person agent = null;
	private boolean isHungry = false;
	public boolean walk = true;

	public boolean raveMode = false;

	Random rand = new Random();
	int red = rand.nextInt(255);
	int blue  = rand.nextInt(255);
	int green  = rand.nextInt(255);
	Color myColor;
	Color transColor;
	Color currColor;

	//Bus stops
	private int startStopX;
	private int startStopY;
	private int endStopX;
	private int endStopY;

	//Walking
	private int nextCrosswalkX;
	private int nextCrosswalkY;

	private int xPos, yPos;//default person position
	private int xDestination, yDestination;//default start position
	private int xHome, yHome;
	public enum Command {noCommand, GoToRestaurant, GoToMarket, GoToBank, GoToBusStop, GoOnBus, GoHome};
	public Command command = Command.noCommand;

	int currentBlock, destinationBlock;

	private enum PersonState {nothing, enroute, walkingToCrosswalk, inCrosswalk1, inCrosswalk2, inCrosswalk3, inCrosswalk4, inCrosswalk5, inCrosswalk6, inCrosswalk7, inCrosswalk8, inCrosswalk9, inCrosswalk10, inCrosswalk11, inCrosswalk12};
	PersonState state = PersonState.nothing;

	private String choice;

	public PersonGui(Person p) {
		this.agent = p;

		if(p.home.type.equals("West Apartment")) {
			xHome = 25;
			yHome = 20;                        

		}
		if(p.home.type.equals("East Apartment")) {
			xHome = 540;
			yHome = 290;                        
		}
		if (p.home.type.equals("Mansion")){
			xHome = 70;
			yHome = 140;
		}
		setxPos(xHome);
		setyPos(yHome);
		setxDestination(xHome);
		setyDestination(yHome);

		myColor = new Color(red, blue, green);
		transColor = new Color(0,0,0,1);
		setDefaultColor();
	}

	public void updatePosition() {

		if (inBusyCrosswalk()) {
			return;
		}	
		//if (!inBusyIntersection()) {
		if (state == PersonState.walkingToCrosswalk) {		
			if (xPos == nextCrosswalkX && yPos == nextCrosswalkY) {
				decideForBus("next");			
				return;
				//agent.print("In block " + currentBlock + " and going to position " + nextCrosswalkX + " ," + nextCrosswalkY);
				//agent.print("At croswalk in block" + currentBlock + "going to " + destinationBlock);
			}
			else if (destinationBlock - currentBlock == 1) {
				xPos++;
				return;
			}
			else if (destinationBlock - currentBlock == -1){
				xPos--;
				return;
			}
			else if (destinationBlock - currentBlock == 3){
				yPos++;
				return;
			}
			else if (destinationBlock - currentBlock == -3){
				yPos--;
				return;
			}
		}



		if (getxPos() < getxDestination())
			setxPos(getxPos() + 1);
		else if (getxPos() > getxDestination())
			setxPos(getxPos() - 1);

		if (getyPos() < getyDestination())
			setyPos(getyPos() + 1);
		else if (getyPos() > getyDestination())
			setyPos(getyPos() - 1);

		inACrosswalk();
		leftACrosswalk();

		if (xPos == getxDestination() && yPos == getyDestination() && command != Command.noCommand) {
			if(agent != null) {

				if (command == Command.GoToRestaurant) {
					agent.msgAtDestination();
					currColor = transColor;
				}
				if (command == Command.GoToMarket) {
					agent.msgAtDestination();
					currColor = transColor;
				}

				if (command == Command.GoToBank) {
					agent.msgAtDestination();
					currColor = transColor;
				}
				if (command == Command.GoHome) {
					agent.msgAtDestination();
					currColor = transColor;			
				}

				if (command == Command.GoToBusStop) {
					agent.msgAtDestination();
					System.out.println("Reached bus stop");
					//currColor = transColor;
				}
				command = Command.noCommand;
			}

		}
	}

	public void draw(Graphics2D g) {

		if (command != Command.noCommand) {
			currColor = myColor;
		}

		if (xPos == xHome && yPos == yHome) {
			g.setColor(transColor);
		}
		else {
			g.setColor(currColor);
		}

		if (raveMode){
			Random rand = new Random();
			int red = rand.nextInt(255);
			int blue  = rand.nextInt(255);
			int green  = rand.nextInt(255);
			Color myColor = new Color(red, blue, green);
			g.setColor(myColor);
		}
		else if (!raveMode) {
			if (xPos == xHome && yPos == yHome) {
				g.setColor(transColor);
			}
			else {
				g.setColor(currColor);
			}
		}


		g.fillRect(xPos, yPos, 20, 20);

		if(agent != null) {
			g.drawString(agent.getName(), xPos, yPos);
		}
		else
			g.drawString("testGui", xPos, yPos);
	}

	public void setHungry() {
		isHungry = true;
		setPresent(true);
		setxDestination(getxHome());
		setyDestination(yHome);
	}

	public boolean isHungry() {
		return isHungry;
	}

	public void DoGoToBusStop(int stopNum) {//later you will map stop number to map coordinates.
		setxDestination(100);
		setyDestination(100);
	}

	public void DoGoHome() { 
		//	agent.print("Called again");
		setxDestination(xHome);
		setyDestination(yHome);
		setDefaultColor();
		command = Command.GoHome;
	}

	public void doGoToBusStop() {
		xDestination = Phonebook.getPhonebook().getAllBusStops().get(getClosestBusStopNumber()).getBusStopLocation().x;
		yDestination = Phonebook.getPhonebook().getAllBusStops().get(getClosestBusStopNumber()).getBusStopLocation().y;
		System.out.println("Going to bus stop");
		setDefaultColor();
		command = Command.GoToBusStop;
		//Phonebook.getPhonebook().getAllBusStops().get(index);
		//getClosestBusStop, use Phonebook to get point
		//set XDestination and yDestination
	}

	public boolean decideForBus(String location) {
		if (location.equals("East Bank")) {
			xDestination = (int) Phonebook.getPhonebook().getEastBank().location.getX();
			yDestination = (int) Phonebook.getPhonebook().getEastBank().location.getY();
			command = Command.GoToBank;
		}
		if (location.equals("West Bank")) {
			xDestination = (int) Phonebook.getPhonebook().getWestBank().location.getX();
			yDestination = (int) Phonebook.getPhonebook().getWestBank().location.getY();
			command = Command.GoToBank;
		}
		if (location.equals("East Market")) {
			xDestination = (int) Phonebook.getPhonebook().getEastMarket().location.getX();
			yDestination = (int) Phonebook.getPhonebook().getEastMarket().location.getY();
			command = Command.GoToMarket;
		}
		if (location.contains("West Market")) {
			xDestination = (int) Phonebook.getPhonebook().getWestMarket().location.getX();
			yDestination = (int) Phonebook.getPhonebook().getWestMarket().location.getY();
			command = Command.GoToMarket;
		}
		if (location.equals("Chinese Restaurant")) {
			xDestination = (int) Phonebook.getPhonebook().getChineseRestaurant().location.getX();
			yDestination = (int) Phonebook.getPhonebook().getChineseRestaurant().location.getY();
			command = Command.GoToRestaurant;
		}
		if (location.equals("American Restaurant")) {
//			xDestination = (int) Phonebook.getPhonebook().getAmericanRestaurant().location.getX();
//			yDestination = (int) Phonebook.getPhonebook().getAmericanRestaurant().location.getY();
			command = Command.GoToRestaurant;
		}
		if (location.equals("Home")) {
			xDestination = xHome;
			yDestination = yHome;
			command = Command.GoHome;
		}

		currentBlock = returnCurrentBlock (xPos, yPos);
		destinationBlock = returnCurrentBlock (xDestination, yDestination);
		if (currentBlock == destinationBlock) {
			state = PersonState.enroute;
			return true;
		}
		destinationBlock = Phonebook.getPhonebook().blocks.get(currentBlock).doIWalk(destinationBlock);
		if (destinationBlock == 0){
			return false;
		}	
		else
			walkToLocation();
		return true;
	}


	public int getClosestBusStopNumber() {
		if(returnCurrentBlock(xPos, yPos) == 1 || returnCurrentBlock(xPos, yPos) == 2 || returnCurrentBlock(xPos, yPos) == 4 ||
				returnCurrentBlock(xPos, yPos) == 5 )
		{
			return 1;

		}
		else if(returnCurrentBlock(xPos, yPos) == 3 || returnCurrentBlock(xPos, yPos) == 6)
		{
			return 2;

		}
		else if(returnCurrentBlock(xPos, yPos) == 7 || returnCurrentBlock(xPos, yPos) == 8 )
		{
			return 4;
		}
		else
		{//forth bus stop
			return 3;
		}
	}		

	public void walkToLocation(){
		popToMiddle();	

		if (destinationBlock == 1)
		{
			nextCrosswalkX = (int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getX();		
			nextCrosswalkY = (int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getY();
		}
		if (destinationBlock == 2){
			nextCrosswalkX = (int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getX();			
			nextCrosswalkY = (int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getY();
		}
		if (destinationBlock == 3){
			nextCrosswalkX = (int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getX();		
			nextCrosswalkY = (int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getY();
		}

		if (destinationBlock == 4){
			nextCrosswalkX = (int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getX();		
			nextCrosswalkY = (int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getY();
		}
		if (destinationBlock == 5){
			nextCrosswalkX = (int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getX();			
			nextCrosswalkY = (int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getY();
		}
		if (destinationBlock == 6){
			nextCrosswalkX = (int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getX();			
			nextCrosswalkY = (int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getY();
		}

		if (destinationBlock == 7){
			nextCrosswalkX = (int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getX();			
			nextCrosswalkY = (int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getY();
		}
		if (destinationBlock == 8){
			nextCrosswalkX = (int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getX();			
			nextCrosswalkY = (int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getY();
		}
		if (destinationBlock == 9){
			nextCrosswalkX = (int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getX();		
			nextCrosswalkY = (int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getY();
		}
	}

	public int returnCurrentBlock (int xPos, int yPos){
		if (xPos < 160 && yPos < 70){
			return 1;
		}
		else if ((xPos > 199 && xPos < 380) && yPos < 70){
			return 2;
		}
		else if (xPos > 419 && yPos < 70){
			return 3;
		}

		else if (xPos < 160 && (yPos > 100 && yPos < 190)){
			return 4;
		}
		else if ((xPos > 199 && xPos < 380) && (yPos > 109 && yPos < 190)){
			return 5;
		}
		else if (xPos > 419 && (yPos > 109 && yPos < 190)){
			return 6;
		}

		else if (xPos < 160 && yPos > 229){
			return 7;
		}
		else if ((xPos > 199 && xPos < 380) && yPos > 229){
			return 8;
		}
		else if (xPos > 419 && yPos > 229){
			return 9;
		}

		else
			return 0;

		//		if (xPos < 600/3-325/20 && yPos < 325/3-325/20){
		//			return 1;
		//		}
		//		if ((xPos > 600/3+325/20 && xPos < 600*2/3-325/20) && yPos < 325/3-325/20){
		//			return 2;
		//		}
		//		if (xPos > 600*2/3+325/20 && yPos < 325/3-325/20){
		//			return 3;
		//		}
		//
		//		if (xPos < 600/3-325/20 && (yPos > 325/3+325/20 && yPos < 325*2/3-325/20)){
		//			return 4;
		//		}
		//		if ((xPos > 600/3+325/20 && xPos < 600*2/3-325/20) && (yPos > 325/3+325/20 && yPos < 325*2/3-325/20)){
		//			return 5;
		//		}
		//		if (xPos > 600*2/3+325/20 && (yPos > 325/3+325/20 && yPos < 325*2/3-325/20)){
		//			return 6;
		//		}
		//
		//
		//		if (xPos < 600/3-325/20 && yPos > 325*2/3+325/20){
		//			return 7;
		//		}
		//		if ((xPos > 600/3+325/20 && xPos < 600*2/3-325/20) && yPos > 325*2/3+325/20){
		//			return 8;
		//		}
		//		if (xPos > 600*2/3+325/20 && yPos > 325*2/3+325/20){
		//			return 9;
		//		}
		//
		//		else
		//			return 0;

		//		if (xPos < 600/3 && yPos < 325/3){
		//			return 1;
		//		}
		//		if ((xPos > 600/3 && xPos < 600*2/3) && yPos < 325/3){
		//			return 2;
		//		}
		//		if (xPos > 600*2/3 && yPos < 325/3){
		//			return 3;
		//		}
		//
		//		if (xPos < 600/3 && (yPos > 600*2/3 && yPos < 600*2/3)){
		//			return 4;
		//		}
		//		if ((xPos > 600/3 && xPos < 600*2/3) && (yPos > 600/3 && yPos < 600*2/3)){
		//			return 5;
		//		}
		//		if (xPos > 600*2/3 && (yPos > 325/3 && yPos < 600*2/3)){
		//			return 6;
		//		}
		//		if (xPos < 600/3 && yPos > 600*2/3){
		//			return 7;
		//		}
		//		if ((xPos > 600/3 && xPos < 600*2/3) && yPos > 600*2/3){
		//			return 8;
		//		}
		//		if (xPos > 600*2/3 && yPos > 600*2/3){
		//			return 9;
		//		}
		//
		//		else
		//			return 0;
	}

	//public void findStartStop() {
	//	if (xPos <= 600/2 && yPos <= 325/2){
	//		startStopX = (int) Phonebook.getPhonebook().busStops.get(0).getX();
	//		startStopY = (int) Phonebook.getPhonebook().busStops.get(0).getY();
	//	}
	//	if (xPos >= 600/2 && yPos <= 325/2){
	//		startStopX = (int) Phonebook.getPhonebook().busStops.get(0).getX();
	//		startStopY = (int) Phonebook.getPhonebook().busStops.get(0).getY();
	//	}
	//	if (xPos <= 600/2 && yPos >= 325/2){
	//		startStopX = (int) Phonebook.getPhonebook().busStops.get(0).getX();
	//		startStopY = (int) Phonebook.getPhonebook().busStops.get(0).getY();
	//	}
	//	else {
	//		startStopX = (int) Phonebook.getPhonebook().busStops.get(0).getX();
	//		startStopY = (int) Phonebook.getPhonebook().busStops.get(0).getY();
	//	}
	//}

	public void popToMiddle(){

		currentBlock = returnCurrentBlock(xPos, yPos);
		if (currentBlock == 1)
		{
			xPos = (int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getX();			//Pop to middle of block1
			yPos = (int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getY();
			state = PersonState.walkingToCrosswalk;
		}
		if (currentBlock == 2){
			xPos = (int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getX();			//Pop to middle of block1
			yPos = (int) Phonebook.getPhonebook().crosswalk1.getCrosswalk().getY();
			state = PersonState.walkingToCrosswalk;
		}
		if (currentBlock == 3){
			xPos = (int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getX();			//Pop to middle of block1
			yPos = (int) Phonebook.getPhonebook().crosswalk2.getCrosswalk().getY();
			state = PersonState.walkingToCrosswalk;
		}

		if (currentBlock == 4){
			xPos = (int) Phonebook.getPhonebook().crosswalk3.getCrosswalk().getX();			//Pop to middle of block1
			yPos = (int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getY();
			state = PersonState.walkingToCrosswalk;
		}
		if (currentBlock == 5){
			xPos = (int) Phonebook.getPhonebook().crosswalk4.getCrosswalk().getX();			//Pop to middle of block1
			yPos = (int) Phonebook.getPhonebook().crosswalk6.getCrosswalk().getY();
			state = PersonState.walkingToCrosswalk;
		}
		if (currentBlock == 6){
			xPos = (int) Phonebook.getPhonebook().crosswalk5.getCrosswalk().getX();			//Pop to middle of block1
			yPos = (int) Phonebook.getPhonebook().crosswalk7.getCrosswalk().getY();
			state = PersonState.walkingToCrosswalk;
		}

		if (currentBlock == 7){
			xPos = (int) Phonebook.getPhonebook().crosswalk8.getCrosswalk().getX();			//Pop to middle of block1
			yPos = (int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getY();
			state = PersonState.walkingToCrosswalk;
		}
		if (currentBlock == 8){
			xPos = (int) Phonebook.getPhonebook().crosswalk9.getCrosswalk().getX();			//Pop to middle of block1
			yPos = (int) Phonebook.getPhonebook().crosswalk11.getCrosswalk().getY();
			state = PersonState.walkingToCrosswalk;
		}
		if (currentBlock == 9){
			xPos = (int) Phonebook.getPhonebook().crosswalk10.getCrosswalk().getX();			//Pop to middle of block1
			yPos = (int) Phonebook.getPhonebook().crosswalk12.getCrosswalk().getY();
			state = PersonState.walkingToCrosswalk;
		}
		else {
			//agent.print("No pop");
		}
		//xPos -= 10;
		//yPos = 30;
		//System.err.println("Name is " + agent.getName() + " and Block = " + currentBlock + "and position = " + xPos + " , " + yPos );
		//System.err.println(agent.getName() + " has Destination block = " + destinationBlock);
	}

	public void setDefaultColor() {
		if (raveMode) {
			Random rand = new Random();
			int red = rand.nextInt(255);
			int blue  = rand.nextInt(255);
			int green  = rand.nextInt(255);
			currColor = new Color (red, blue, green);
		}
		else
			currColor = myColor;
	}


	synchronized public boolean inBusyCrosswalk() {
		//Horizontal Crosswalks
		Rectangle meLeft = new Rectangle(xPos+1, yPos, 25, 25);
		Rectangle meRight = new Rectangle(xPos-1, yPos, 25, 25);

		if (Phonebook.getPhonebook().crosswalk1.getCrosswalk().intersects(meLeft) ||
				Phonebook.getPhonebook().crosswalk1.getCrosswalk().intersects(meRight)) {
			if (Phonebook.getPhonebook().crosswalk1.isCrosswalkBusy() == true &&
					!(state == PersonState.inCrosswalk1)) {
				return true;
			}
			return false;
		}

		if (Phonebook.getPhonebook().crosswalk2.getCrosswalk().intersects(meLeft) ||
				Phonebook.getPhonebook().crosswalk2.getCrosswalk().intersects(meRight)) {
			if (Phonebook.getPhonebook().crosswalk2.isCrosswalkBusy() == true &&
					!(state == PersonState.inCrosswalk2)) {
				return true;
			}
			return false;
		}
		if (Phonebook.getPhonebook().crosswalk6.getCrosswalk().intersects(meLeft) ||
				Phonebook.getPhonebook().crosswalk6.getCrosswalk().intersects(meRight)) {
			if (Phonebook.getPhonebook().crosswalk6.isCrosswalkBusy() == true &&
					!(state == PersonState.inCrosswalk6)) {
				return  true;
			}
			return false;
		}

		if (Phonebook.getPhonebook().crosswalk7.getCrosswalk().intersects(meLeft) ||
				Phonebook.getPhonebook().crosswalk7.getCrosswalk().intersects(meRight)) {
			if (Phonebook.getPhonebook().crosswalk7.isCrosswalkBusy() == true &&
					!(state == PersonState.inCrosswalk7)) {
				return  true;
			}
			return false;
		}

		if (Phonebook.getPhonebook().crosswalk11.getCrosswalk().intersects(meLeft) ||
				Phonebook.getPhonebook().crosswalk11.getCrosswalk().intersects(meRight)) {
			if (Phonebook.getPhonebook().crosswalk11.isCrosswalkBusy() == true &&
					!(state == PersonState.inCrosswalk11)) {
				return  true;
			}
			return false;
		}

		if (Phonebook.getPhonebook().crosswalk12.getCrosswalk().intersects(meLeft) ||
				Phonebook.getPhonebook().crosswalk12.getCrosswalk().intersects(meRight)) {
			if (Phonebook.getPhonebook().crosswalk12.isCrosswalkBusy() == true &&
					!(state == PersonState.inCrosswalk12)) {
				return  true;
			}
			return false;
		}

		//Vertical Crosswalks
		Rectangle meUp = new Rectangle(xPos, yPos-1, 25, 25);
		Rectangle meDown = new Rectangle(xPos, yPos+1, 25, 25);
		if (Phonebook.getPhonebook().crosswalk3.getCrosswalk().intersects(meUp) ||
				Phonebook.getPhonebook().crosswalk3.getCrosswalk().intersects(meDown)) {
			if (Phonebook.getPhonebook().crosswalk3.isCrosswalkBusy() == true &&
					!(state == PersonState.inCrosswalk3)) {
				return true;
			}
			return false;
		}

		if (Phonebook.getPhonebook().crosswalk4.getCrosswalk().intersects(meUp) ||
				Phonebook.getPhonebook().crosswalk4.getCrosswalk().intersects(meDown)) {
			if (Phonebook.getPhonebook().crosswalk4.isCrosswalkBusy() == true &&
					!(state == PersonState.inCrosswalk4)) {
				return true;
			}
			return false;
		}

		if (Phonebook.getPhonebook().crosswalk5.getCrosswalk().intersects(meUp) ||
				Phonebook.getPhonebook().crosswalk5.getCrosswalk().intersects(meDown)) {
			if (Phonebook.getPhonebook().crosswalk5.isCrosswalkBusy() == true &&
					!(state == PersonState.inCrosswalk5)) {
				return true;
			}
			return false;
		}

		if (Phonebook.getPhonebook().crosswalk8.getCrosswalk().intersects(meUp) ||
				Phonebook.getPhonebook().crosswalk8.getCrosswalk().intersects(meDown)) {
			if (Phonebook.getPhonebook().crosswalk8.isCrosswalkBusy() == true &&
					!(state == PersonState.inCrosswalk8)) {
				return true;
			}
			return false;
		}

		if (Phonebook.getPhonebook().crosswalk9.getCrosswalk().intersects(meUp) ||
				Phonebook.getPhonebook().crosswalk9.getCrosswalk().intersects(meDown)) {
			if (Phonebook.getPhonebook().crosswalk9.isCrosswalkBusy() == true &&
					!(state == PersonState.inCrosswalk9)) {
				return true;
			}
			return false;
		}

		if (Phonebook.getPhonebook().crosswalk10.getCrosswalk().intersects(meUp) ||
				Phonebook.getPhonebook().crosswalk10.getCrosswalk().intersects(meDown)) {
			if (Phonebook.getPhonebook().crosswalk10.isCrosswalkBusy() == true &&
					!(state == PersonState.inCrosswalk10)) {
				return true;
			}
			return false;
		}
		return false;
	}

	public void inACrosswalk() {
		Rectangle me = new Rectangle(xPos, yPos, 25, 25);

		if (Phonebook.getPhonebook().crosswalk1.getCrosswalk().intersects(me) &&
				!(state == PersonState.inCrosswalk1)) {
			Phonebook.getPhonebook().crosswalk1.setCrosswalkBusy(true);	
			state = PersonState.inCrosswalk1;
		}
		else if (Phonebook.getPhonebook().crosswalk2.getCrosswalk().intersects(me) &&
				!(state == PersonState.inCrosswalk2)) {
			Phonebook.getPhonebook().crosswalk2.setCrosswalkBusy(true);	
			state = PersonState.inCrosswalk2;
		}	
		else if (Phonebook.getPhonebook().crosswalk3.getCrosswalk().intersects(me) &&
				!(state == PersonState.inCrosswalk3)) {
			Phonebook.getPhonebook().crosswalk3.setCrosswalkBusy(true);	
			state = PersonState.inCrosswalk3;
		}
		else if (Phonebook.getPhonebook().crosswalk4.getCrosswalk().intersects(me) &&
				!(state == PersonState.inCrosswalk4)) {
			Phonebook.getPhonebook().crosswalk4.setCrosswalkBusy(true);	
			state = PersonState.inCrosswalk4;
		}
		else if (Phonebook.getPhonebook().crosswalk5.getCrosswalk().intersects(me) &&
				!(state == PersonState.inCrosswalk5)) {
			Phonebook.getPhonebook().crosswalk5.setCrosswalkBusy(true);	
			state = PersonState.inCrosswalk5;
		}
		else if (Phonebook.getPhonebook().crosswalk6.getCrosswalk().intersects(me) &&
				!(state == PersonState.inCrosswalk6)) {
			Phonebook.getPhonebook().crosswalk6.setCrosswalkBusy(true);	
			state = PersonState.inCrosswalk6;
		}
		else if (Phonebook.getPhonebook().crosswalk7.getCrosswalk().intersects(me) &&
				!(state == PersonState.inCrosswalk7)) {
			Phonebook.getPhonebook().crosswalk7.setCrosswalkBusy(true);	
			state = PersonState.inCrosswalk7;
		}
		else if (Phonebook.getPhonebook().crosswalk8.getCrosswalk().intersects(me) &&
				!(state == PersonState.inCrosswalk8)) {
			Phonebook.getPhonebook().crosswalk8.setCrosswalkBusy(true);	
			state = PersonState.inCrosswalk8;
		}
		else if (Phonebook.getPhonebook().crosswalk9.getCrosswalk().intersects(me) &&
				!(state == PersonState.inCrosswalk9)) {
			Phonebook.getPhonebook().crosswalk9.setCrosswalkBusy(true);	
			state = PersonState.inCrosswalk9;
		}
		else if (Phonebook.getPhonebook().crosswalk10.getCrosswalk().intersects(me) &&
				!(state == PersonState.inCrosswalk10)) {
			Phonebook.getPhonebook().crosswalk10.setCrosswalkBusy(true);	
			state = PersonState.inCrosswalk10;
		}
		else if (Phonebook.getPhonebook().crosswalk11.getCrosswalk().intersects(me) &&
				!(state == PersonState.inCrosswalk11)) {
			Phonebook.getPhonebook().crosswalk11.setCrosswalkBusy(true);	
			state = PersonState.inCrosswalk11;
		}
		else if (Phonebook.getPhonebook().crosswalk12.getCrosswalk().intersects(me) &&
				!(state == PersonState.inCrosswalk12)) {
			Phonebook.getPhonebook().crosswalk12.setCrosswalkBusy(true);	
			state = PersonState.inCrosswalk12;
		}
	}

	public void leftACrosswalk() {
		Rectangle me = new Rectangle(xPos, yPos, 25, 25);

		if (!Phonebook.getPhonebook().crosswalk1.getCrosswalk().intersects(me)
				&& (state == PersonState.inCrosswalk1)) {
			Phonebook.getPhonebook().crosswalk1.setCrosswalkBusy(false);	
			state = PersonState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk2.getCrosswalk().intersects(me)
				&& (state == PersonState.inCrosswalk2)) {
			Phonebook.getPhonebook().crosswalk2.setCrosswalkBusy(false);	
			state = PersonState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk3.getCrosswalk().intersects(me)
				&& (state == PersonState.inCrosswalk3)) {
			Phonebook.getPhonebook().crosswalk3.setCrosswalkBusy(false);	
			state = PersonState.enroute;
		}
		else if (!Phonebook.getPhonebook().crosswalk4.getCrosswalk().intersects(me)
				&& (state == PersonState.inCrosswalk4)) {
			Phonebook.getPhonebook().crosswalk4.setCrosswalkBusy(false);	
			state = PersonState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk5.getCrosswalk().intersects(me)
				&& (state == PersonState.inCrosswalk5)) {
			Phonebook.getPhonebook().crosswalk5.setCrosswalkBusy(false);	
			state = PersonState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk6.getCrosswalk().intersects(me)
				&& (state == PersonState.inCrosswalk6)) {
			Phonebook.getPhonebook().crosswalk6.setCrosswalkBusy(false);	
			state = PersonState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk7.getCrosswalk().intersects(me)
				&& (state == PersonState.inCrosswalk7)) {
			Phonebook.getPhonebook().crosswalk7.setCrosswalkBusy(false);	
			state = PersonState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk8.getCrosswalk().intersects(me)
				&& (state == PersonState.inCrosswalk8)) {
			Phonebook.getPhonebook().crosswalk8.setCrosswalkBusy(false);	
			state = PersonState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk9.getCrosswalk().intersects(me)
				&& (state == PersonState.inCrosswalk9)) {
			Phonebook.getPhonebook().crosswalk9.setCrosswalkBusy(false);	
			state = PersonState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk10.getCrosswalk().intersects(me)
				&& (state == PersonState.inCrosswalk10)) {
			Phonebook.getPhonebook().crosswalk10.setCrosswalkBusy(false);	
			state = PersonState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk11.getCrosswalk().intersects(me)
				&& (state == PersonState.inCrosswalk11)) {
			Phonebook.getPhonebook().crosswalk11.setCrosswalkBusy(false);	
			state = PersonState.enroute;	
		}
		else if (!Phonebook.getPhonebook().crosswalk12.getCrosswalk().intersects(me)
				&& (state == PersonState.inCrosswalk12)) {
			Phonebook.getPhonebook().crosswalk12.setCrosswalkBusy(false);	
			state = PersonState.enroute;	
		}
	}

	public void setInvisible()
	{
		currColor = transColor;
	}

	public void getOffBus(int busStopNumber)
	{
		xPos = Phonebook.getPhonebook().getAllBusStops().get(busStopNumber).getBusStopLocation().x;
		yPos = Phonebook.getPhonebook().getAllBusStops().get(busStopNumber).getBusStopLocation().y;
		currColor = myColor;
	}

	public void setHomeLocation(int x, int y) {
		setxHome(x);
		yHome = y;
	}

	public String toString() {
		return "Person Gui";
	}

	public int getxDestination() {
		return xDestination;
	}

	public void setxDestination(int xDestination) {
		this.xDestination = xDestination;
	}

	public int getxHome() {
		return xHome;
	}

	public void setxHome(int xHome) {
		this.xHome = xHome;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getyHome() {
		return yHome;
	}

	public void setRaveMode() {
		if (raveMode) {
			raveMode = false;
		}
		else
			raveMode = true;
	}


	public int getyDestination() {
		return yDestination;
	}

	public void setyDestination(int yDestination) {
		this.yDestination = yDestination;

	}
}
