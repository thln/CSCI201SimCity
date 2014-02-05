package person;

import java.util.*;

public class Profile {
	
	String name = new String();
	String type = new String();
	String age = new String();
	String gender = new String();
	ArrayList<String> jobs = new ArrayList<String>();
	String currentJob = new String();
	String currentLocation = new String();
	double cash = 0.00;
	double netWorth = 0.00;
	
	public Profile(String name, String type, String age, String gender, 
				   ArrayList<String> jobs, String currentJob,
				   String currentLocation, double cash, double netWorth){
		this.name = name;
		this.type = type;
		this.age = age;
		this.gender = gender;
		this.jobs = jobs;
		this.currentJob = currentJob;
		this.currentLocation = currentLocation;
		this.cash = cash;
		this.netWorth = netWorth; 
	}
	public Profile(String name, String type){
		this.name = name;
		this.type = type;
		this.age = "18";
		this.gender = "Male";
		
		//Set Money 
		cash = 80.00;
		netWorth = 500.00;
	}
	
	public String printFullView(){
		String fullView = new String();
		return fullView;
	}
	
	public String printQuickView(){
		//TODO logic for quickview
		String quickView = new String();
		/*"<html><h2><u>" + name + "</u></h2><table><tr><td>" +occupation 
        + "</td><td>" + name + "</td></tr></table><h3><u>"
        +currLoc+ "</u></h3><table><tr><td>"+"$"
        +money+ "</td><</tr>>" + "</table><br></html>"*/
		return quickView;
	}
	public String getName(){
		return name;
	}
}
