package transportation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import person.Person;
import application.Phonebook;
import application.TimeManager;
import application.gui.animation.agentGui.BusGuiHorizontal;
import application.gui.animation.agentGui.BusGuiVertical;
import application.gui.trace.AlertLog;
import application.gui.trace.AlertTag;
import agent.Agent;

public class BusAgent extends Agent{

	/*********************
	 ****** DATA *********
	 *********************/
	boolean needToDeposit;
	boolean ifHorizontal;
	boolean checkedStation = false;
	int currentBusStop;
	int expectedNumberOfPassengers;
	BusGuiHorizontal guiH;
	BusGuiVertical guiV;
	String name;

	public enum busState {Driving, ReachedStop, DroppedOffPeople, PickingUpPeople, Waiting, Leaving};
	busState state = busState.Driving;
	class busPassenger
	{
		Person passenger;
		int busStop;
		busPassenger(Person p, int bStop)
		{
			passenger = p;
			busStop = bStop;
		}
	}

	List<busPassenger> busPassengers = Collections.synchronizedList(new ArrayList<busPassenger>());
	//List<Person> peopleAtBusStop = Collections.synchronizedList(new ArrayList<Person>());


	public BusAgent(String name) 
	{
		this.name = name + " Bus";
	}

	/**** 
	 * Interactions to go -
	 * Person messaging bus stop and going to sleep with metaphor
	 * Bus arrives at bus stops and gets all waiting customers
	 * Messages all sleeping customers to wake up
	 * Customers responds by messaging bus I am getting on
	 * Customer goes to sleep, Gui disappears
	 * Bus arrives at next bus stop, messages all customers with that destination
	 * Customer wakes up, GUI reappears at that spot
	 * ***/

	/*********************
	 ***** MESSAGES ******
	 *********************/

	public void msgAtBusStop(int busStopNumber)
	{
		//print("Got at stop");
		if(!checkedStation)
		{
			checkedStation = true;
		//	AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, name, "Arrived at Bus Stop " + busStopNumber);
			currentBusStop = busStopNumber;
			state = busState.ReachedStop;
			stateChanged();
		}
	}

	public void msgGettingOnBus(Person p, int bStop)
	{
//		try {
//			pickingUpPeople.acquire();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, name, "Adding " + p.getName());
		busPassengers.add(new busPassenger(p, bStop));
		state = busState.PickingUpPeople;
		stateChanged();
	}
	
	public void msgLeavingStation()
	{
		checkedStation = false;
	}

	/*********************
	 ***** SCHEDULER *****
	 *********************/
	protected boolean pickAndExecuteAnAction() 
	{
		if(state == busState.ReachedStop)
		{
			//System.err.println("Running tell People Get off");
			tellPeopleGetOff();
			return true;
		}
		if(state == busState.DroppedOffPeople)
		{
			tellPeopleWaiting();
			return true;
		}
		if(state == busState.PickingUpPeople)
		{
			checkNumberOfPassengers();
			return true;
		}
		if (state == busState.Leaving) {
			goToNextStop();
			return true;
		}
		return false;
	}

	/*********************
	 ****** ACTIONS ******
	 *********************/

	private void tellPeopleWaiting()
	{
		if( Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).getCurrentBus() == null)
		{
			//AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, name, "Telling people to get on at " + Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).getBusStopNumber());
			//state = busState.PickingUpPeople;
			state = busState.Waiting;
			//peopleAtBusStop = Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).getAllWaitingPassengers(this);
			expectedNumberOfPassengers += Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).getNumberOfWaitingPassengers();
			if(Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).getAllWaitingPassengers(this).isEmpty())
			{
			//	AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, name, "No one's here. Leaving.");
				state = busState.PickingUpPeople;
				return;
			}
			else
			{
				//AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, name, "Telling"+ Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).getNumberOfWaitingPassengers()+ " people at bus stop.");
				synchronized(Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).getAllWaitingPassengers(this))
				{
					int tempSize = Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).getNumberOfWaitingPassengers();
//					try {
//						pickingUpPeople.acquire();
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					for(int i = 0; i < tempSize; i++)
					{
					//	AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, name, "Waking up " 
					//+ Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).getAllWaitingPassengers(this).get(0).getName());
						Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).getAllWaitingPassengers(this).get(0).msgBusIsHere();
						Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).getAllWaitingPassengers(this).remove(0);
						//AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, name, " # " + Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).getNumberOfWaitingPassengers());
//						if(Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).getNumberOfWaitingPassengers() == 0)
//						{
//							AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, name, "SDFDSf " );
//							pickingUpPeople.release();
//						}
					}
					//pickingUpPeople.release();
				}
				//Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).clearWaitingPassengers();
			}
		}
		return;
	}

	private void tellPeopleGetOff()
	{
		//AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, name, "Telling people to get off.");
		synchronized(busPassengers)
		{
			for(int i = 0; i < busPassengers.size(); i++)
			{
				if(busPassengers.get(i).busStop == currentBusStop)
				{
					//AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, name, busPassengers.get(i).passenger.getName() + " is leaving at " + currentBusStop);				
					busPassengers.get(i).passenger.msgAtBusStopDestination();
					busPassengers.get(i).busStop = 5;
					//busPassengers.remove(i);
				}
			}
			for(int i = 0; i < busPassengers.size(); i++)
			{
				if(busPassengers.get(i).busStop == 5)
				{
					busPassengers.remove(i);
				}
			}
		}
		expectedNumberOfPassengers = busPassengers.size();
		state = busState.DroppedOffPeople;
		stateChanged();
	}

	private void checkNumberOfPassengers() {
		//AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, name, "Checking if everyone's here. Expecting :" + expectedNumberOfPassengers);
		if(expectedNumberOfPassengers == busPassengers.size())
		{
			//AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, name, "Everyone's here, leaving. ");
			//			if(ifHorizontal)
			//			{
			//				guiH.wait.release();
			//			}
			//state = busState.Driving;
			 Phonebook.getPhonebook().getAllBusStops().get(currentBusStop).busIsLeaving();
			state = busState.Leaving;
			//peopleAtBusStop = null;
			currentBusStop = 0;
			expectedNumberOfPassengers = 0;
		}
		else
		{
			state = busState.Waiting;	
			//AlertLog.getInstance().logInfo(AlertTag.GENERAL_CITY, name, "Not enough people. Expecting :" + expectedNumberOfPassengers + " Have: " + busPassengers.size());

		}
		//state = busState.Leaving;
		stateChanged();
	}

	private void goToNextStop(){
		if (ifHorizontal) {
			guiH.goToNextBusStop();
		}
		else {
			guiV.goToNextBusStop();
		}
		state = busState.Driving;
		stateChanged();
	}

	public void setHGui(BusGuiHorizontal g)
	{
		ifHorizontal = true;
		guiH = g;
	}

	public void setVGui(BusGuiVertical g)
	{
		ifHorizontal = false;
		guiV = g;
	}
	
	public boolean getCheckedStation()
	{
		return checkedStation;
	}
	
	public String toString() {
		return name;
	}
}