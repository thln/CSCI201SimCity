package italianRestaurant;

import italianRestaurant.ItalianMyCustomer.MyCustState;
import italianRestaurant.interfaces.*;
import application.Phonebook;
import person.Person;

public class ItalianAltWaiterRole extends ItalianWaiterRole implements ItalianWaiter{
	//private RevolvingStand theRevolvingStand;
	protected String RoleName = "Alternative AmericanRestaurantWaiter";

	public ItalianAltWaiterRole(Person person, String name, String title) {
		super(person, name, title);
	}

	protected void PlaceOrder(ItalianMyCustomer customer) {
		if(test)
		{
			print("Test AmericanRestaurantCustomer");
		}
		else
		{
			print("Placing order for " + customer);
		}
		
		customer.c.msgdoneOrdering();
		/*
		waiterGui.DoGotoCook();
		try {
			atCook.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//cook.msgHereIsCustOrder(this, customer.choice, customer.table);
		print("Placing order on Revolving Stand.");
		Phonebook.getPhonebook().getItalianRestaurant().getRevolvingStand().newOrder(new ItalianRestaurantOrder(this, customer.choice, customer.table));
		customer.s = MyCustState.waiting;
	}

}
