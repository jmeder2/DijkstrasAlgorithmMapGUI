/*
 * Joshua Medernach
 * 3-26-15
 * 
 * class City models a City, which contains all roads leading to the city (if any) and the city's name
 */

import java.util.*;

public class City {
	
	private String name = null;					// City's name
	private List<Road> roadList= null;			// List of roads the city is associated with
	
	private int pathLength = -1;				// This is for use in shortest route calculation
												// -1 indicates infinity
	
	private List<City> path = null;				// the solution path leading up to this city, will be shortest distance path
	
	public City (String name) {
		this.name = name;
		this.roadList = new LinkedList<Road>();
		this.path = new LinkedList<City>();
		this.path.add(this);
	}
	
	// Returns name of this city
	public String getName() {
		return this.name;
	}
	
	// Gets the number of roads this city is associated with
	public int getNumOfRoads() {
		return this.roadList.size();
	}
	
	// Add a new road to this city
	public void addRoad(Road road) {
		this.roadList.add(road);
	}
	
	// Returns the list of roads this city is associated with
	public List<Road> getListOfRoads() {
		return this.roadList;
	}
	
	// Returns the other city a given road connects with this city
	public City otherCity(Road road) {
		if (this.equals(road.getCity1())) {
			return road.getCity2();
		}
		else {
			return road.getCity1();
		}
	}
	
	// Returns a string that lays out all the cities this city is connected to and the distance to each of them
	public String printAllRoadsByCity() {
		StringBuilder sb = new StringBuilder("");
		
		for (int i = 0; i < this.roadList.size() ; i++) {
			Road road = this.roadList.get(i);
			sb.append("&emsp;&emsp;Road ID: " + road.getRoadID() + "&emsp;&emsp;City: " + this.otherCity(road).getName() + "&emsp;&emsp;Distance: " + road.getLength() + "<br/>");
		}
		
		String printOut = sb.toString();
		if (printOut.equals("")) {
			return "&emsp;&emsp;&emsp;&emsp;No Roads listed.<br/>";
		}
		else {
			return printOut;
		}
	}
	
	// Sets pathLength to -1
	public void initializePathLength() {
		this.pathLength = -1;
	}
	
	// Sets pathLength to a calculated length
	public void setPathLength(int length) {
		this.pathLength = length;
	}
	
	// Returns the pathLength for comparison methods
	public int getPathLength() {
		return this.pathLength;
	}
	
	// get the Solution path
	public List<City> getSolutionPath() {
		return this.path;
	}
	
	// set the Solution path
	public void setSolutionPath(List<City> list) {
		this.path.addAll(0, list); // Inserts the entire path before this city (inserts at index 0)
	}
	
	// reset the Solution path
	public void resetSolutionPath() {
		this.path.clear();
		this.path.add(this);
	}
	
	// Overrides equals() method, two cities are equal if both have same String name
	@Override
	public boolean equals(Object city) {
		if (city instanceof City) {
			if (this.name.toLowerCase().equals(((City)city).getName().toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
