package housing;

import person.Wealthy;

/**** This class is purely for 
 * the Wealthy people aka Landlords to pick up money and for everyone else in the city
 * to drop off money. Additionally, since everyone outside of wealthy live in apartments or 
 * parks, and people who live in the park don't pay, all the payments are the same ***/

public class Mailbox 
{
	private int paymentCash;
	private int apartmentRentPrice = 50;

	//should I check how people pay rent? to keep them accountable?
	//note for v2
	public void dropRentMoney(int payment)
	{
		paymentCash += payment;
	}
	
	public int pickUpRentMoney(Wealthy landlord)
	{
		//think of this like a finger print scanna
		if(landlord instanceof Wealthy)
		{
			int withdraw = paymentCash;
			paymentCash = 0;
			return withdraw;
		}
		else
		{
			return 0;
		}
	}
	
	public int getApartmentRentCost()
	{
		return apartmentRentPrice;
	}
	
	public int getCurrentPaymentAmount()
	{
		return paymentCash;
	}
}
