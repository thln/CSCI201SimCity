package application.gui.trace;

/**
 * These enums represent tags that group alerts together.  <br><br>
 * 
 * This is a separate idea from the {@link AlertLevel}.
 * A tag would group all messages from a similar source.  Examples could be: BANK_TELLER, RESTAURANT_ONE_WAITER,
 * or PERSON.  This way, the trace panel can sort through and identify all of the alerts generated in a specific group.
 * The trace panel then uses this information to decide what to display, which can be toggled.  You could have all of
 * the bank tellers be tagged as a "BANK_TELLER" group so you could turn messages from tellers on and off.
 * 
 * @author Keith DeRuiter
 * @modified Tam Henry Le Nguyen
 */
public enum AlertTag {
        PERSON, 					//Purely to use Demo Launcher
        BANK_TELLER, 				//Purely to use Demo Launcher
        BANK_CUSTOMER, 				//Purely to use Demo Launcher
        BUS_STOP, 					//Purely to use Demo Launcher
        RESTAURANT,                //For the demo code where you make a new restaurant
        BANK,						//For the demo code where you make a new bank
        HOUSING, 
        MARKET,
        GENERAL_CITY
}