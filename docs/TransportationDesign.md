# Vehicle Agent

Note: To be implemented in v2 instead.

## Requirements Summary
 + Driverless
 + Solarpowered(no gas)
 + Accidents are rare
 + Passengers
 + Underground Parking Structures in each building (assumption)
 + Group Leader is always the first passenger in the occupants array (assumption)
 
### Data

<pre><code>
int capacity
String type
ArrayList <Person> Occupants
boolean full
int xDestination, yDestination;
Double change
Enum VehicleState(On, Off)
Enum VehicleEvent(TurnOn, TurnOff)
Boolean private
</code></pre>

### Messages

<pre><code>
msgStartCar()
VehicleEvent = TurnOn

msgEntering(ArrayList <Person> group)
for(Person p: group)
            	occupants.add(p)
if(private)
            	VehicleEvent = TurnOn

msgTakeMeTo(int x, int y) {
	xDestination = x;
	yDestination = y;
}

msgLeaving()
Empty Occupants array
this.LocationIndex = null
if(private)
VehicleEvent = TurnOff
</code></pre>

### Scheduler

<pre><code>
If(VehicleState == On) {
	if(!occupants.isEmpty()) {
	If(LocationIndex.isNull())
	RequestDestination(occupants.get(0))
	}
}
else if (VehicleState == Off)
Drive(xDestination, yDestination);
</code></pre> 

### Actions

<pre><code>
StartCar() {
	msgStartCar();
}
RequestDestination(Person p) {
     p.msgWhereTo();
}
Drive(int location) {
    Gui.goToLocation(xDestination, yDestination);
}
StopCar() {
	msgStopCar();
}
</code></pre>

# Gui Design

<pre><code>
GoToLocation(int x, int y) {
	//gui movements
}
</code></pre> 

# Considerations                                         	
 + What happens when the vehicle is idle?
 	- The "business" of the car, bus, etc. closes for the day.
 + When can the vehicle be idle
 	- when driver goes off shift, or parked somewhere in a structure.
           
