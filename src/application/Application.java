package application;

import housing.Housing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import person.Crook;
import person.Deadbeat;
import person.Person;
import person.Wealthy;
import person.Worker;
//import americanRestaurant.AmericanRestaurantCookRole;
import transportation.BusAgent;
import application.gui.animation.AnimationPanel;
import application.gui.animation.agentGui.BusGuiHorizontal;
import application.gui.animation.agentGui.BusGuiVertical;
import application.gui.animation.agentGui.PersonGui;


public class Application extends JPanel {

	private ArrayList<Person> population = new ArrayList<Person>();
	private static List<Housing> allHousing = Collections.synchronizedList(new ArrayList<Housing>());

	public AnimationPanel animPanel;
	public Timer updateTimer = new Timer();

	//public static Phonebook phonebook = new Phonebook(bank, market, restaurant, allHousing);


	public Application(AnimationPanel ap) {

		animPanel = ap;
		//Initialize the building reference's to one another
		//AmericanRestaurantCookRole cook = Phonebook.getPhonebook().getAmericanRestaurant().americanCook;
		//cook.getMarkets().add(Phonebook.getPhonebook().getEastMarket());
		//cook.getMarkets().add(Phonebook.getPhonebook().getWestMarket());

		//Adding markets to the cook in Chinese Restaurant
		Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantCookRole.addMarket(Phonebook.getPhonebook().getEastMarket());
		Phonebook.getPhonebook().getChineseRestaurant().chineseRestaurantCookRole.addMarket(Phonebook.getPhonebook().getWestMarket());

		//Buses
		BusAgent horizontal = new BusAgent("Horizontal");
		BusAgent vertical = new BusAgent("Vertical");
		BusGuiHorizontal busA = new BusGuiHorizontal(horizontal);
		BusGuiVertical busB = new BusGuiVertical(vertical);	
		horizontal.setHGui(busA);
		vertical.setVGui(busB);
		animPanel.cityPanel.addGui(busA);
		animPanel.cityPanel.addGui(busB);
		horizontal.startThread();
		vertical.startThread();
		updatePeopleTime();
	}

	public void updatePeopleTime(){
		int timeConversion = 60 * TimeManager.getSpeedOfTime();

		updateTimer.schedule(new TimerTask() {
			public void run() {        
				for (Person p: population){
					if (p.stateChange.availablePermits() <= 1){
						p.stateChanged();    
					}
				}
				updatePeopleTime();
			}
		},
		timeConversion);

	}

	public void addPerson (String name ,int money, String type,
			String jobTitle, String jobLocation, String housing, int startT, int lunchT, int endT) {
		//last 4 parameters specifically for worker. make empty/0 for all other types
		//add any special parameters if new things needed for other types
		if (type.equals("Wealthy")) 
		{
			//min money req?
			Wealthy newP = new Wealthy(name, money);
			allHousing.add(new Housing(newP, allHousing.size(), housing));
			newP.setHome(allHousing.get(allHousing.size() - 1));
			animPanel.setHousingPanel(allHousing.get(allHousing.size() - 1));
			getPopulation().add(newP);
			PersonGui pg = new PersonGui(newP);
			newP.setGui(pg);
			animPanel.addGui(pg);
			newP.startThread();
		}
		else if (type.equals("Crook")) 
		{
			Crook newP = new Crook(name, money);
			allHousing.add(new Housing(newP, allHousing.size(), housing));
			animPanel.setHousingPanel(allHousing.get(allHousing.size() - 1));
			newP.setHome(allHousing.get(allHousing.size() - 1));
			getPopulation().add(newP);
			PersonGui pg = new PersonGui(newP);
			newP.setGui(pg);
			animPanel.addGui(pg);
			newP.startThread();
		}
		else if (type.equals("Worker")) 
		{
			Worker newP = new Worker(name, money, jobTitle, jobLocation, startT, lunchT, endT);
			allHousing.add(new Housing(newP, allHousing.size(), housing));
			animPanel.setHousingPanel(allHousing.get(allHousing.size() - 1));
			newP.setHome(allHousing.get(allHousing.size() - 1));
			getPopulation().add(newP);
			PersonGui pg = new PersonGui(newP);
			newP.setGui(pg);
			animPanel.addGui(pg);
			newP.startThread();	
		}
		else if (type.equals("Deadbeat")) 
		{
			Deadbeat newP = new Deadbeat(name, money);
			allHousing.add(new Housing(newP, allHousing.size(), housing));
			animPanel.setHousingPanel(allHousing.get(allHousing.size() - 1));
			newP.setHome(allHousing.get(allHousing.size() - 1));
			getPopulation().add(newP);
			PersonGui pg = new PersonGui(newP);
			newP.setGui(pg);
			animPanel.addGui(pg);
			newP.startThread();
		}
		//runHousing();
		//runGui();
	}

	public void editPerson(int index, String name, int money){
		getPopulation().get(index).setMoney(money);
	}

	//function used to test gui functionality
	public void printLastPop(){
		//System.out.println(getPopulation().get(getPopulation().size()-1).getName());
	}

	public int getPopulationSize() {
		return getPopulation().size();
	}

	public int getNumberHomes(){
		return allHousing.size();
	}
	public Person getPerson(int personIndex){
		return getPopulation().get(personIndex);
	}

	public ArrayList<Person> getPopulation() {
		return population;
	}

	public void setPopulation(ArrayList<Person> population) {
		this.population = population;
	}

	public void runHousing(){
		for (Housing housing : allHousing) {
			animPanel.setHousingPanel(housing);
		}
	}

	public void runGui(){
		for (Person person : getPopulation()) {
			PersonGui pg = new PersonGui(person);
			person.setGui(pg);
			animPanel.addGui(pg);
		}
	}

	public void runFullScenario (){
		//String name, int money, String jobTitle, String jobPlace, int startT, int lunchT, int endT

		//East Bank Workers
		//SHIFT 1
		Worker bank1a = new Worker("Alex", 100, "bankTeller", "East Bank", 0, 6, 13);
		Worker bank1b = new Worker("Ben", 100, "loanOfficer", "East Bank", 0, 6, 13);
		Worker bank1c = new Worker("Caitlyn", 100, "bankGuard", "East Bank", 0, 6, 13);

		//SHIFT 2
		Worker bank2a = new Worker("Andy", 100, "bankTeller", "East Bank", 12, 18, 1);
		Worker bank2b = new Worker("Billy", 100, "loanOfficer", "East Bank", 12, 18, 1);
		Worker bank2c = new Worker("Courtney", 100, "bankGuard", "East Bank", 12, 18, 1);

		//West Bank Workers
		//SHIFT 1
		Worker bank3a = new Worker("Albert", 100, "bankTeller", "West Bank", 0, 6, 13);
		Worker bank3b = new Worker("Bailey", 100, "loanOfficer", "West Bank", 0, 6, 13);
		Worker bank3c = new Worker("Corey", 100, "bankGuard", "West Bank", 0, 6, 13);

		//SHIFT 2
		Worker bank4a = new Worker("Ashley", 100, "bankTeller", "West Bank", 12, 18, 1);
		Worker bank4b = new Worker("Brenna", 100, "loanOfficer", "West Bank", 12, 18, 1);
		Worker bank4c = new Worker("Catherine", 100, "bankGuard", "West Bank", 12, 18, 1);



		//East Market Workers
		//SHIFT 1
		Worker market1d = new Worker("Derrick", 10, "marketRunner", "East Market", 0, 600, 13);
		Worker market1e = new Worker("Erin", 1000, "salesPerson", "East Market", 0, 600, 13);
		Worker market1f = new Worker("Fred", 10, "UPSman", "East Market", 0, 600, 13);        

		//SHIFT 2
		Worker market2d = new Worker("Daniel", 100, "marketRunner", "East Market", 12, 400, 1);
		Worker market2e = new Worker("Elle", 200, "salesPerson", "East Market", 12, 400, 1);
		Worker market2f = new Worker("Frenchy", 100, "UPSman", "East Market", 12, 400, 1);    

		//West Market Workers
		//SHIFT 1
		Worker market3d = new Worker("Dan", 10, "marketRunner", "West Market", 0, 600, 13);
		Worker market3e = new Worker("Elpha", 100, "salesPerson", "West Market", 0, 600, 13);
		Worker market3f = new Worker("Fitz", 10, "UPSman", "West Market", 0, 600, 13);        

		//SHIFT 2
		Worker market4d = new Worker("Diego", 100, "marketRunner", "West Market", 12, 400, 1);
		Worker market4e = new Worker("Elmo", 200, "salesPerson", "West Market", 12, 400, 1);
		Worker market4f = new Worker("Francine", 100, "UPSman", "West Market", 12, 400, 1);        


		//Chinese Restaurant Workers
		//SHIFT 1
		Worker rest1h = new Worker("Henry", 100, "host", "Chinese Restaurant", 0, 1800, 13);
		Worker rest1g = new Worker("Greg", 100, "cashier", "Chinese Restaurant", 0, 1800, 13);        
		Worker rest1i = new Worker("Iris", 100, "cook", "Chinese Restaurant", 0, 1800, 13);
		Worker rest1j = new Worker("Josh", 100, "waiter", "Chinese Restaurant", 0, 1800, 13);
		Worker rest1k = new Worker("Kristi", 100, "altWaiter", "Chinese Restaurant", 0, 1800, 13);
		//SHIFT 2
		Worker rest2g = new Worker("Gil", 100, "cashier", "Chinese Restaurant", 12, 600, 1);
		Worker rest2h = new Worker("Hannah", 100, "host", "Chinese Restaurant", 12, 600, 1);
		Worker rest2i = new Worker("Isaac", 100, "cook", "Chinese Restaurant", 12, 600, 1);
		Worker rest2j = new Worker("Jacob", 100, "waiter", "Chinese Restaurant", 12, 600, 1);
		Worker rest2k = new Worker("Ken", 100, "altWaiter", "Chinese Restaurant", 12, 600, 1);

		Worker house1 = new Worker("Parker", 100, "maintenance worker","housing maintenance company", 0, 600, 12);

		//American Restaurant Workers
		//SHIFT 1 (make sure to revert work start times)
		Worker rest3h = new Worker("Holly", 100, "host", "American Restaurant", 0, 1800, 12);
		Worker rest3g = new Worker("Gerald", 100, "cashier", "American Restaurant", 0, 1800, 12);        
		Worker rest3i = new Worker("India", 100, "cook", "American Restaurant", 0, 1800, 12);
		Worker rest3j = new Worker("Jan", 100, "waiter", "American Restaurant", 0, 1800, 12);
		Worker rest3k = new Worker("Kris", 100, "altWaiter", "American Restaurant", 0, 1800, 12);

		//!!!!Important -- Need to initialize setters 
		//ex. waiter.setHost, waiter.setCook, waiter.setHost, 
		//Do this when person walks in for work***

		//Standard Wealthy Person
		Wealthy wealthy1 = new Wealthy("Tam Henry", 100);
		Wealthy wealthy2 = new Wealthy("Kristi Hupka", 100);
		Wealthy wealthy3 = new Wealthy("Josh Greenburger", 100);
		Wealthy wealthy4 = new Wealthy("Keith DeRuiter", 100);

		//Standard Crook
		Crook crook1 = new Crook("Vinny", 250);

		//Standard Deadbeat
		//Deadbeat w = new Deadbeat("Walter", 0);

		//Adding housing

		//Shift 1
		allHousing.add(new Housing(bank1a, allHousing.size(), "East Apartment"));
		bank1a.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(bank1b, allHousing.size(), "West Apartment"));
		bank1b.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(bank1c, allHousing.size(), "East Apartment"));
		bank1c.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market1d, allHousing.size(), "West Apartment"));
		market1d.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market1e, allHousing.size(), "East Apartment"));
		market1e.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market1f, allHousing.size(), "West Apartment"));
		market1f.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market4d, allHousing.size(), "West Apartment"));
		market4d.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market4e, allHousing.size(), "East Apartment"));
		market4e.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market4f, allHousing.size(), "West Apartment"));
		market4f.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest1g, allHousing.size(), "East Apartment"));
		rest1g.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest1h, allHousing.size(), "West Apartment"));
		rest1h.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest1i, allHousing.size(), "East Apartment"));
		rest1i.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest1j, allHousing.size(), "West Apartment"));
		rest1j.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest1k, allHousing.size(), "East Apartment"));
		rest1k.setHome(allHousing.get(allHousing.size() - 1));

		allHousing.add(new Housing(bank3a, allHousing.size(), "East Apartment"));
		bank3a.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(bank3b, allHousing.size(), "West Apartment"));
		bank3b.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(bank3c, allHousing.size(), "East Apartment"));
		bank3c.setHome(allHousing.get(allHousing.size() - 1));

		allHousing.add(new Housing(rest3g, allHousing.size(), "East Apartment"));
		rest3g.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest3h, allHousing.size(), "West Apartment"));
		rest3h.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest3i, allHousing.size(), "East Apartment"));
		rest3i.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest3j, allHousing.size(), "West Apartment"));
		rest3j.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest3k, allHousing.size(), "East Apartment"));
		rest3k.setHome(allHousing.get(allHousing.size() - 1));


		//Shift 2

		allHousing.add(new Housing(bank2a, allHousing.size(), "West Apartment"));
		bank2a.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(bank2b, allHousing.size(), "East Apartment"));
		bank2b.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(bank2c, allHousing.size(), "West Apartment"));
		bank2c.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market2d, allHousing.size(), "East Apartment"));
		market2d.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market2e, allHousing.size(), "West Apartment"));
		market2e.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market2f, allHousing.size(), "East Apartment"));
		market2f.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market3d, allHousing.size(), "East Apartment"));
		market3d.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market3e, allHousing.size(), "West Apartment"));
		market3e.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market3f, allHousing.size(), "East Apartment"));
		market3f.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest2g, allHousing.size(), "West Apartment"));
		rest2g.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest2h, allHousing.size(), "East Apartment"));
		rest2h.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest2i, allHousing.size(), "West Apartment"));
		rest2i.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest2j, allHousing.size(), "East Apartment"));
		rest2j.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest2k, allHousing.size(), "West Apartment"));
		rest2k.setHome(allHousing.get(allHousing.size() - 1));

		allHousing.add(new Housing(house1, allHousing.size(), "West Apartment"));
		house1.setHome(allHousing.get(allHousing.size() - 1));

		allHousing.add(new Housing(bank4a, allHousing.size(), "East Apartment"));
		bank4a.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(bank4b, allHousing.size(), "West Apartment"));
		bank4b.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(bank4c, allHousing.size(), "East Apartment"));
		bank4c.setHome(allHousing.get(allHousing.size() - 1));

		//People
		allHousing.add(new Housing(wealthy1, allHousing.size(), "Mansion"));
		wealthy1.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(wealthy2, allHousing.size(), "Mansion"));
		wealthy2.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(wealthy3, allHousing.size(), "Mansion"));
		wealthy3.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(wealthy4, allHousing.size(), "Mansion"));
		wealthy4.setHome(allHousing.get(allHousing.size() - 1));

		allHousing.add(new Housing(crook1, allHousing.size(), "East Apartment"));
		crook1.setHome(allHousing.get(allHousing.size() - 1));

		Phonebook.getPhonebook().setHousingList(allHousing);


		//Adding to Vector
		//Shift 1
		getPopulation().add(bank1a);
		getPopulation().add(bank1b);
		getPopulation().add(bank1c);
		getPopulation().add(market1d);
		getPopulation().add(market1e);
		getPopulation().add(market1f);
		getPopulation().add(rest1g);
		getPopulation().add(rest1h);
		getPopulation().add(rest1i);
		getPopulation().add(rest1j);
		getPopulation().add(rest1k);
		getPopulation().add(bank3a);
		getPopulation().add(bank3b);
		getPopulation().add(bank3c);

		getPopulation().add(rest3g);
		getPopulation().add(rest3h);
		getPopulation().add(rest3i);
		getPopulation().add(rest3j);
		getPopulation().add(rest3k);

		//Shift 2
		getPopulation().add(bank2a);
		getPopulation().add(bank2b);
		getPopulation().add(bank2c);
		getPopulation().add(market2d);
		getPopulation().add(market2e);
		getPopulation().add(market2f);
		
		getPopulation().add(rest2g);
		getPopulation().add(rest2h);
		getPopulation().add(rest2i);
		getPopulation().add(rest2j);
		getPopulation().add(rest2k);
		getPopulation().add(house1);
		getPopulation().add(bank4a);
		getPopulation().add(bank4b);
		getPopulation().add(bank4c);

		getPopulation().add(market3d);
		getPopulation().add(market3e);
		getPopulation().add(market3f);
		getPopulation().add(market4d);
		getPopulation().add(market4e);
		getPopulation().add(market4f);
		
		//People
		getPopulation().add(wealthy1);
		getPopulation().add(wealthy2);

		getPopulation().add(wealthy3);
		getPopulation().add(wealthy4);
		getPopulation().add(crook1);

		runHousing();
		runGui();

		//Starting Threads
		//Shift 1
		bank1a.startThread();
		bank1b.startThread();	
		bank1c.startThread();
		market1d.startThread();
		market1e.startThread();
		market1f.startThread();
		rest1g.startThread();
		rest1h.startThread();
		rest1i.startThread();
		rest1j.startThread();
		rest1k.startThread();
		bank3a.startThread();
		bank3b.startThread();	
		bank3c.startThread();

		market3d.startThread();
		market3e.startThread();
		market3f.startThread();

		market4d.startThread();
		market4e.startThread();
		market4f.startThread();

		//Shift 2
		bank2a.startThread();
		bank2b.startThread();
		bank2c.startThread();
		market2d.startThread();
		market2e.startThread();
		market2f.startThread();
		rest2g.startThread();
		rest2h.startThread();
		rest2i.startThread();
		rest2j.startThread();
		rest2k.startThread();
		house1.startThread();
		bank4a.startThread();
		bank4b.startThread();	
		bank4c.startThread();		

		//People
		wealthy1.startThread();
		wealthy2.startThread();
		//
		//		wealthy3.startThread();
		//		wealthy4.startThread();

		crook1.startThread();
	}

	/*
	 * All workplaces (markets, all restaurants, banks) fully employed.
Day starts and all workers go to work.
One not-working person eats at home, then visits all the workplaces by walking.
Roads should have appropriate complexity [e.g. intersections with stop signs and/or signals]
	 */
	public void runScenarioA () {
		//String name, int money, String jobTitle, String jobPlace, int startT, int lunchT, int endT

		//East Bank Workers
		//SHIFT 1
		Worker bank1a = new Worker("Alex", 100, "bankTeller", "East Bank", 0, 6, 23);
		Worker bank1b = new Worker("Ben", 100, "loanOfficer", "East Bank", 0, 6, 23);
		Worker bank1c = new Worker("Caitlyn", 100, "bankGuard", "East Bank", 0, 6, 23);

		//West Bank Workers
		//SHIFT 1
		Worker bank3a = new Worker("Albert", 100, "bankTeller", "West Bank", 0, 6, 23);
		Worker bank3b = new Worker("Bailey", 100, "loanOfficer", "West Bank", 0, 6, 23);
		Worker bank3c = new Worker("Corey", 100, "bankGuard", "West Bank", 0, 6, 23);

		//West Market Workers
		//SHIFT 1
		Worker market3d = new Worker("Dan", 10, "marketRunner", "West Market", 0, 600, 23);
		Worker market3e = new Worker("Elpha", 1000, "salesPerson", "West Market", 0, 600, 23);
		Worker market3f = new Worker("Fitz", 10, "UPSman", "West Market", 0, 600, 23);        
		
		//Chinese Restaurant Workers
		//SHIFT 1
		Worker rest1h = new Worker("Henry", 100, "host", "Chinese Restaurant", 0, 1800, 23);
		Worker rest1g = new Worker("Greg", 100, "cashier", "Chinese Restaurant", 0, 1800, 23);        
		Worker rest1i = new Worker("Iris", 100, "cook", "Chinese Restaurant", 0, 1800, 23);
		Worker rest1j = new Worker("Josh", 100, "waiter", "Chinese Restaurant", 0, 1800, 23);
		Worker rest1k = new Worker("Kristi", 100, "altWaiter", "Chinese Restaurant", 0, 1800, 23);

		//American Restaurant Workers
		//SHIFT 1 (make sure to revert work start times)
		Worker rest3h = new Worker("Holly", 100, "host", "American Restaurant", 0, 1800, 12);
		Worker rest3g = new Worker("Gerald", 100, "cashier", "American Restaurant", 0, 1800, 12);        
		Worker rest3i = new Worker("India", 100, "cook", "American Restaurant", 0, 1800, 12);
		Worker rest3j = new Worker("Jan", 100, "waiter", "American Restaurant", 0, 1800, 12);
		Worker rest3k = new Worker("Kris", 100, "altWaiter", "American Restaurant", 0, 1800, 12);

		//!!!!Important -- Need to initialize setters 
		//ex. waiter.setHost, waiter.setCook, waiter.setHost, 
		//Do this when person walks in for work***

		//Standard Wealthy Person
		Wealthy wealthy1 = new Wealthy("Tam Henry", 300);

		//Adding housing

		//Shift 1
		allHousing.add(new Housing(bank1a, allHousing.size(), "West Apartment"));
		bank1a.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(bank1b, allHousing.size(), "West Apartment"));
		bank1b.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(bank1c, allHousing.size(), "West Apartment"));
		bank1c.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market3d, allHousing.size(), "West Apartment"));
		market3d.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market3e, allHousing.size(), "West Apartment"));
		market3e.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(market3f, allHousing.size(), "West Apartment"));
		market3f.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest1g, allHousing.size(), "West Apartment"));
		rest1g.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest1h, allHousing.size(), "West Apartment"));
		rest1h.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest1i, allHousing.size(), "West Apartment"));
		rest1i.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest1j, allHousing.size(), "West Apartment"));
		rest1j.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest1k, allHousing.size(), "West Apartment"));
		rest1k.setHome(allHousing.get(allHousing.size() - 1));

		allHousing.add(new Housing(bank3a, allHousing.size(), "West Apartment"));
		bank3a.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(bank3b, allHousing.size(), "West Apartment"));
		bank3b.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(bank3c, allHousing.size(), "West Apartment"));
		bank3c.setHome(allHousing.get(allHousing.size() - 1));

		allHousing.add(new Housing(rest3g, allHousing.size(), "West Apartment"));
		rest3g.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest3h, allHousing.size(), "West Apartment"));
		rest3h.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest3i, allHousing.size(), "West Apartment"));
		rest3i.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest3j, allHousing.size(), "West Apartment"));
		rest3j.setHome(allHousing.get(allHousing.size() - 1));
		allHousing.add(new Housing(rest3k, allHousing.size(), "West Apartment"));
		rest3k.setHome(allHousing.get(allHousing.size() - 1));

		//People
		allHousing.add(new Housing(wealthy1, allHousing.size(), "Mansion"));
		wealthy1.setHome(allHousing.get(allHousing.size() - 1));

		Phonebook.getPhonebook().setHousingList(allHousing);


		//Adding to Vector
		//Shift 1
		getPopulation().add(bank1a);
		getPopulation().add(bank1b);
		getPopulation().add(bank1c);
		getPopulation().add(market3d);
		getPopulation().add(market3e);
		getPopulation().add(market3f);
		getPopulation().add(rest1g);
		getPopulation().add(rest1h);
		getPopulation().add(rest1i);
		getPopulation().add(rest1j);
		getPopulation().add(rest1k);
		getPopulation().add(bank3a);
		getPopulation().add(bank3b);
		getPopulation().add(bank3c);

		getPopulation().add(rest3g);
		getPopulation().add(rest3h);
		getPopulation().add(rest3i);
		getPopulation().add(rest3j);
		getPopulation().add(rest3k);

		//People
		getPopulation().add(wealthy1);

		runHousing();
		runGui();

		//Starting Threads
		//Shift 1
		bank1a.startThread();
		bank1b.startThread();	
		bank1c.startThread();
		market3d.startThread();
		market3e.startThread();
		market3f.startThread();
		rest1g.startThread();
		rest1h.startThread();
		rest1i.startThread();
		rest1j.startThread();
		rest1k.startThread();
		bank3a.startThread();
		bank3b.startThread();	
		bank3c.startThread();
		/*
		rest3g.startThread();
		rest3h.startThread();
		rest3i.startThread();
		rest3j.startThread();
		rest3k.startThread();
		 */
		//
		//		//People
		wealthy1.startThread();
	}
	/*
	B. Scenario: [Tests all the behaviors.]
			All workplaces fully employed.
			Day starts and all workers go to work.
			Three not-working persons eat at home, then visit all the workplaces in different orders. 
			[one should walk; one should take a car; one should take a bus.]
	 */
	public void runScenarioB () {

	}

	/*
	 	C. Scenario: [Tests cook, cashier, market interaction.]
			Each restaurant gets low on food and orders from market(s).
			Market delivers food to the open restaurant.
			Market sends invoice to cashier, who verifies and pays it.
	 */
	public void runScenarioC () {

	}

	/*
	 * E. Scenario: [Shows bus-stop behavior]
			Person visits a bus stop. [as part of step 7]
			Bus arrives.
			Person gets on.
			Person gets off at destination.
	 */
	
	public void runScenarioD () {
		//To turn on weekend
		//Use TimeManager.getTimeManager().setAmericanCalendarTrue();
		//Which will set first day to Sunday
		
	}
	
	public void runScenarioE () {

	}
	
	public void stopRunningScenario ()
	{
	}




}





