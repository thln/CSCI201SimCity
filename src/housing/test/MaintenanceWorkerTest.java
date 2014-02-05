package housing.test;
import housing.Housing;
import housing.Housing.housingState;
import housing.MaintenanceWorkerRole;
import housing.MaintenanceWorkerRole.maintenanceState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import person.Wealthy;
import person.Worker;
import application.Phonebook;
import junit.framework.TestCase;

public class MaintenanceWorkerTest extends TestCase 
{

	List<Housing> testHousing;// = Collections.synchronizedList(new ArrayList<Housing>());
	Worker person;
	MaintenanceWorkerRole testingMaintenanceWorkerRole;
	Wealthy homeOwner1;
	Wealthy homeOwner2;
	Housing homeTest1;
	Housing homeTest2;
	
	//Why isn't Phonebook working
	public void SetUp() throws Exception
	{
		super.setUp();
		testHousing = Collections.synchronizedList(new ArrayList<Housing>());
		Phonebook.getPhonebook().setTestHousingList(testHousing);
		//testHousing = Phonebook.getPhonebook().getAllHousing(true);
		person = new Worker("testHMC", 500, "Maintenance", "Maintenance Company", 800, 1200, 1800);
		testingMaintenanceWorkerRole = new MaintenanceWorkerRole(person, person.getName(), "test Maintenance");
		testingMaintenanceWorkerRole.test = true;
		
		homeOwner1 = new Wealthy("home Owner 1", 0);
		homeOwner2 = new Wealthy("home Owner 1", 0);
		homeTest1 = new Housing(homeOwner1, 0, "Mansion");
		homeTest2 = new Housing(homeOwner2, 0, "Mansion");
		homeOwner1.setHome(homeTest1);
		homeOwner2.setHome(homeTest2);
		
	}
	
	//Test that maintenance worker can handle one house
	
	public void testNormativeScenarioOne()
	{
		try 
		{
			SetUp();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	  	  /******************************/
		 /***Testing preconditions******/
		/******************************/
		
		assertEquals("List of Housing should be 0. It isn't.", 0, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		
		assertFalse("Since there is no housing, the maintenance worker's scheduler should return false.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());
	
		testingMaintenanceWorkerRole.msgRefreshHousingList();
		
		assertEquals("List of Housing should still be 0. It isn't.", 0, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);
		
		assertFalse("Since there is no housing, the maintenance worker's scheduler should return false.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());
		
	  	  /******************************/
		 /********Testing 1 house*******/
		/******************************/
		
		testHousing.add(homeTest1);
		
		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The first house on the list should belong to home Owner1. It doesn't.", homeOwner1, Phonebook.getPhonebook().getAllHousing(true).get(0).occupant);
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);
		
		assertTrue("Since there is one housing, the maintenance worker's scheduler should return true.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is checkingHouse. It isn't.", maintenanceState.CheckingHouse, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be checking. It isn't.", housingState.Checking, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);
	}
		 /*********************************/
		/** List of housing does refresh */
	   /*********************************/
	public void testNormativeScenarioTwo()
	{
		try 
		{
			SetUp();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	  	  /******************************/
		 /***Testing preconditions******/
		/******************************/
		
		assertEquals("List of Housing should be 0. It isn't.", 0, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		
		assertFalse("Since there is no housing, the maintenance worker's scheduler should return false.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());
	
		testingMaintenanceWorkerRole.msgRefreshHousingList();
		
		assertEquals("List of Housing should still be 0. It isn't.", 0, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);
		
		assertFalse("Since there is no housing, the maintenance worker's scheduler should return false.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());
		
	  	  /******************************/
		 /********Testing 1 house*******/
		/******************************/
		
		testHousing.add(homeTest1);
		
		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The first house on the list should belong to home Owner1. It doesn't.", homeOwner1, Phonebook.getPhonebook().getAllHousing(true).get(0).occupant);
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);
		
		assertTrue("Since there is one housing, the maintenance worker's scheduler should return true.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is checkingHouse. It isn't.", maintenanceState.CheckingHouse, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be checking. It isn't.", housingState.Checking, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		assertTrue("Since there is one housing, and because it is done, the maintenance worker's scheduler should return true to reset his list.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up again. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);
	}
	
	
	  /******************************/
	 /***** Testing  two houses ****/
	/******************************/
	
	public void testNormativeScenarioThree()
	{
		try 
		{
			SetUp();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	  	  /******************************/
		 /***Testing preconditions******/
		/******************************/
		
		assertEquals("List of Housing should be 0. It isn't.", 0, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		
		assertFalse("Since there is no housing, the maintenance worker's scheduler should return false.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());
	
		testingMaintenanceWorkerRole.msgRefreshHousingList();
		
		assertEquals("List of Housing should still be 0. It isn't.", 0, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);
		
		assertFalse("Since there is no housing, the maintenance worker's scheduler should return false.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());
		
	  	  /******************************/
		 /********Testing 1 house*******/
		/******************************/
		
		testHousing.add(homeTest1);
		
		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The first house on the list should belong to home Owner1. It doesn't.", homeOwner1, Phonebook.getPhonebook().getAllHousing(true).get(0).occupant);
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);
		
		assertTrue("Since there is one housing, the maintenance worker's scheduler should return true.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is checkingHouse. It isn't.", maintenanceState.CheckingHouse, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be checking. It isn't.", housingState.Checking, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		assertTrue("Since there is one housing, and because it is done, the maintenance worker's scheduler should return true to reset his list.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up again. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		  /******************************/
		 /***** Testing  two houses ****/
		/******************************/
		testHousing.add(homeTest2);

		assertEquals("List of Housing should be 2 now. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first house on the list should belong to home Owner1. It doesn't.", homeOwner1, Phonebook.getPhonebook().getAllHousing(true).get(0).occupant);
		assertEquals("The second house on the list should belong to home Owner2. It doesn't.", homeOwner2, Phonebook.getPhonebook().getAllHousing(true).get(1).occupant);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		assertTrue("Since there are two housings, the maintenance worker's scheduler should return true.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is checkingHouse. It isn't.", maintenanceState.CheckingHouse, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be checking. It isn't.", housingState.Checking, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		assertTrue("Since there are two housings, the maintenance worker's scheduler should return true.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is checkingHouse. It isn't.", maintenanceState.CheckingHouse, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a checking. It isn't.", housingState.Checking, homeOwner2.getHousing().state);

		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner2.getHousing().state);

		assertTrue("Since there are two housing, and because they are done, the maintenance worker's scheduler should return true to reset his list.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up again. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up again. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);
	}
	
	  /********************************/
	 /** Testing  urgent work order **/
	/********************************/
	public void testNormativeScenarioFour()
	{
		try 
		{
			SetUp();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	  	  /******************************/
		 /***Testing preconditions******/
		/******************************/
		
		assertEquals("List of Housing should be 0. It isn't.", 0, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		
		assertFalse("Since there is no housing, the maintenance worker's scheduler should return false.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());
	
		testingMaintenanceWorkerRole.msgRefreshHousingList();
		
		assertEquals("List of Housing should still be 0. It isn't.", 0, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);
		
		assertFalse("Since there is no housing, the maintenance worker's scheduler should return false.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());
		
	  	  /******************************/
		 /********Testing 1 house*******/
		/******************************/
		
		testHousing.add(homeTest1);
		
		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The first house on the list should belong to home Owner1. It doesn't.", homeOwner1, Phonebook.getPhonebook().getAllHousing(true).get(0).occupant);
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);
		
		assertTrue("Since there is one housing, the maintenance worker's scheduler should return true.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is checkingHouse. It isn't.", maintenanceState.CheckingHouse, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be checking. It isn't.", housingState.Checking, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		assertTrue("Since there is one housing, and because it is done, the maintenance worker's scheduler should return true to reset his list.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up again. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		  /******************************/
		 /***** Testing  two houses ****/
		/******************************/
		testHousing.add(homeTest2);

		assertEquals("List of Housing should be 2 now. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first house on the list should belong to home Owner1. It doesn't.", homeOwner1, Phonebook.getPhonebook().getAllHousing(true).get(0).occupant);
		assertEquals("The second house on the list should belong to home Owner2. It doesn't.", homeOwner2, Phonebook.getPhonebook().getAllHousing(true).get(1).occupant);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		assertTrue("Since there are two housings, the maintenance worker's scheduler should return true.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is checkingHouse. It isn't.", maintenanceState.CheckingHouse, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be checking. It isn't.", housingState.Checking, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		assertTrue("Since there are two housings, the maintenance worker's scheduler should return true.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is checkingHouse. It isn't.", maintenanceState.CheckingHouse, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a checking. It isn't.", housingState.Checking, homeOwner2.getHousing().state);

		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner2.getHousing().state);

		assertTrue("Since there are two housing, and because they are done, the maintenance worker's scheduler should return true to reset his list.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up again. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up again. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		  /********************************/
		 /** Testing  urgent work order **/
		/********************************/
		
		testingMaintenanceWorkerRole.msgNeedMaintenance(homeOwner2.getHousing());
		
		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need to be urgently checked. It isn't.", housingState.UrgentWorkOrder, homeOwner2.getHousing().state);

		assertTrue("Since there are two housings, the maintenance worker's scheduler should return true.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is checkingHouse. It isn't.", maintenanceState.CheckingHouse, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a checking. It isn't.", housingState.Checking, homeOwner2.getHousing().state);

		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner2.getHousing().state);
	}
	
	
	  /*********************************/
	 /** Testing  msg List Refresher **/
	/*********************************/
	public void testNormativeScenarioFive()
	{
		try 
		{
			SetUp();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	  	  /******************************/
		 /***Testing preconditions******/
		/******************************/
		
		assertEquals("List of Housing should be 0. It isn't.", 0, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		
		assertFalse("Since there is no housing, the maintenance worker's scheduler should return false.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());
	
		testingMaintenanceWorkerRole.msgRefreshHousingList();
		
		assertEquals("List of Housing should still be 0. It isn't.", 0, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);
		
		assertFalse("Since there is no housing, the maintenance worker's scheduler should return false.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());
		
	  	  /******************************/
		 /********Testing 1 house*******/
		/******************************/
		
		testHousing.add(homeTest1);
		
		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The first house on the list should belong to home Owner1. It doesn't.", homeOwner1, Phonebook.getPhonebook().getAllHousing(true).get(0).occupant);
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);
		
		assertTrue("Since there is one housing, the maintenance worker's scheduler should return true.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is checkingHouse. It isn't.", maintenanceState.CheckingHouse, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be checking. It isn't.", housingState.Checking, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		assertTrue("Since there is one housing, and because it is done, the maintenance worker's scheduler should return true to reset his list.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 1. It isn't.", 1, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up again. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		  /******************************/
		 /***** Testing  two houses ****/
		/******************************/
		testHousing.add(homeTest2);

		assertEquals("List of Housing should be 2 now. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first house on the list should belong to home Owner1. It doesn't.", homeOwner1, Phonebook.getPhonebook().getAllHousing(true).get(0).occupant);
		assertEquals("The second house on the list should belong to home Owner2. It doesn't.", homeOwner2, Phonebook.getPhonebook().getAllHousing(true).get(1).occupant);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		assertTrue("Since there are two housings, the maintenance worker's scheduler should return true.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is checkingHouse. It isn't.", maintenanceState.CheckingHouse, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be checking. It isn't.", housingState.Checking, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		assertTrue("Since there are two housings, the maintenance worker's scheduler should return true.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is checkingHouse. It isn't.", maintenanceState.CheckingHouse, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a checking. It isn't.", housingState.Checking, homeOwner2.getHousing().state);

		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner2.getHousing().state);

		assertTrue("Since there are two housing, and because they are done, the maintenance worker's scheduler should return true to reset his list.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up again. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up again. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

		  /********************************/
		 /** Testing  urgent work order **/
		/********************************/
		
		testingMaintenanceWorkerRole.msgNeedMaintenance(homeOwner2.getHousing());
		
		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need to be urgently checked. It isn't.", housingState.UrgentWorkOrder, homeOwner2.getHousing().state);

		assertTrue("Since there are two housings, the maintenance worker's scheduler should return true.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is checkingHouse. It isn't.", maintenanceState.CheckingHouse, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a checking. It isn't.", housingState.Checking, homeOwner2.getHousing().state);

		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner2.getHousing().state);

		  /*********************************/
		 /** Testing  msg List Refresher **/
		/*********************************/
		
		testingMaintenanceWorkerRole.msgRefreshHousingList();
		
		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is RefreshList. It isn't.", maintenanceState.RefreshList, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should be recently checked. It isn't.", housingState.RecentlyChecked, homeOwner2.getHousing().state);

		assertTrue("Since there are two housings, the maintenance worker's scheduler should return true.", testingMaintenanceWorkerRole.pickAndExecuteAnAction());

		assertEquals("List of Housing should still be 2. It isn't.", 2, Phonebook.getPhonebook().getAllHousing(true).size());
		assertEquals("The maintentenance worker's state is working. It isn't.", maintenanceState.Working, testingMaintenanceWorkerRole.state);
		assertEquals("The first owner's house should need a check up again. It isn't.", housingState.CheckUpNeeded, homeOwner1.getHousing().state);
		assertEquals("The second owner's house should need a check up again. It isn't.", housingState.CheckUpNeeded, homeOwner2.getHousing().state);

	}
}
