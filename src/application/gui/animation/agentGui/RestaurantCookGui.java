package application.gui.animation.agentGui;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chineseRestaurant.*;

public class RestaurantCookGui extends RestaurantGui {

	private ChineseRestaurantCookRole agent = null;

	private int xPos = 405, yPos = 140;//default cook position
	private int xDestination = 405, yDestination = 140;//default start position
	private int xHome = 405, yHome = 140;

	private final int xKitchenLocation = 385, yKitchenLocation = 110, KitchenTileWidth = 20, KitchenTileHeight = 20;

	private int cookingCount = 0;
	private Counter platingCount = new Counter();

	private enum CookState {nothing, cookingFood, gettingFood, plating}
	CookState state = CookState.nothing;
	
	private List<String> platedFood = Collections.synchronizedList(new ArrayList<String>());
	
	public RestaurantCookGui() {	
	}
	
	public RestaurantCookGui(ChineseRestaurantCookRole agent) {
		this.agent = agent;
	}
	
	public void msgGotOrder(String choice) {
		synchronized(platedFood) {
			platingCount.decrement();;

			if (choice == "Chicken")
				platedFood.remove("C");
			else if (choice == "Steak")
				platedFood.remove("St");
			else if (choice == "Salad")
				platedFood.remove("S");
			else if (choice == "Pizza")
				platedFood.remove("P");
		}
	}

	public void updatePosition() {
		if (xPos < xDestination)
			xPos++;
		else if (xPos > xDestination)
			xPos--;

		if (yPos < yDestination)
			yPos++;
		else if (yPos > yDestination)
			yPos--;

		if (xPos == xDestination && yPos == yDestination) {
			if (xPos != xHome && yPos != yHome) {
				if (state == CookState.cookingFood) {
					cookingCount++;
					state = CookState.nothing;
				}

				else if (state == CookState.gettingFood) {
					cookingCount--;
					state = CookState.nothing;
				}
				
				else if (state == CookState.plating) {
					platingCount.increment();
					state = CookState.nothing;
				}
				
				agent.msgAtDestination();
			}
		}
	}

	public void draw(Graphics2D g) {
		synchronized(platedFood) {
			g.setColor(Color.CYAN);
			g.fillRect(xPos, yPos, 20, 20);

			if (state == CookState.plating) {
				g.setColor(Color.GRAY);
				g.fillOval(xPos, yPos,(int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7) );
			}

			if (cookingCount == 1) {
				g.setColor(Color.RED);
				g.fillOval(xKitchenLocation + 13, yKitchenLocation + 85, (int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7));
			}
			else if (cookingCount == 2) {
				g.setColor(Color.RED);
				g.fillOval(xKitchenLocation + 13, yKitchenLocation + 85, (int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7));
				g.fillOval(xKitchenLocation + 33, yKitchenLocation + 85, (int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7));
			}
			else if (cookingCount == 3) {
				g.setColor(Color.RED);
				g.fillOval(xKitchenLocation + 13, yKitchenLocation + 85, (int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7));
				g.fillOval(xKitchenLocation + 33, yKitchenLocation + 85, (int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7));
				g.fillOval(xKitchenLocation + 13, yKitchenLocation + 100, (int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7));
			}
			else if (cookingCount == 4) {
				g.setColor(Color.RED);
				g.fillOval(xKitchenLocation + 13, yKitchenLocation + 85, (int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7));
				g.fillOval(xKitchenLocation + 33, yKitchenLocation + 85, (int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7));
				g.fillOval(xKitchenLocation + 13, yKitchenLocation + 100, (int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7));
				g.fillOval(xKitchenLocation + 33, yKitchenLocation + 100, (int) (KitchenTileWidth * .7), (int) (KitchenTileHeight * .7));
			}

			if (platingCount.getCount() > 0) {
				g.setColor(Color.RED);
				for (int i = 0; i < platedFood.size(); i++) {
					g.drawString(platedFood.get(i), xKitchenLocation - 17, yKitchenLocation + ((i + 1) * 15));
				}
			}
			
			if (platingCount.getCount() < 0) {
				platingCount.setCount(0);
				cookingCount = 0;
			}
		}
	}

	public boolean isPresent() {
		return true;
	}

	public void DoGetIngredients() {
		state = CookState.nothing;
		xDestination = 406;
		yDestination = 110;
	}

	public void DoGoToGrill() {
		state = CookState.cookingFood;
		xDestination = 406;
		yDestination = 170;
	}

	public void DoPickUpFood() {
		state = CookState.gettingFood;
		xDestination = 406;
		yDestination = 170;
	}

	public void DoGoToPlatingArea(String choice) {
		synchronized(platedFood) {
			state = CookState.plating;
			xDestination = 395;
			yDestination = 120;

			if (choice == "Chicken")
				platedFood.add("C");
			else if (choice == "Steak")
				platedFood.add("St");
			else if (choice == "Salad")
				platedFood.add("S");
			else if (choice == "Pizza")
				platedFood.add("P");
		}
	}

	public void DoGoToHomePosition() {
		state = CookState.nothing;
		xDestination = xHome;
		yDestination = yHome;
	}

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}
	
	private class Counter {
		private int count = 0;
		
		public void increment() {
			synchronized (this) {
				count++;
			}
		}
		public void decrement() {
			synchronized (this) {
				count--;
			}
		}
		public int getCount() {
			synchronized (this) {
				return count;
			}
		}
		
		public void setCount(int newCount) {
			synchronized (this) {
				count = newCount;
			}
		}
	}

}
