/*
 * Joshua Medernach
 * 3-26-15
 * 
 * class Road models a road, which contains length, and the two cities the road connects together
 */

public class Road {

	private int roadID = -1;				// Initialized to not have a road id yet
	private City city1 = null;				// City the road connects on one end
	private City city2 = null;				// City the road connects on the other end
	private int length = 0;					// Initialized to not have a length yet
	
	public Road(int roadID, int length, City city1, City city2) {
		this.roadID = roadID;
		this.length = length;
		this.city1 = city1;
		this.city2 = city2;
	}
	
	// Get the ID of this road
	public int getRoadID() {
		return this.roadID;
	}
	
	// Get the length of this road
	public int getLength() {
		return this.length;
	}
	
	// Get City 1
	public City getCity1() {
		return this.city1;
	}
	
	// Get City 2
	public City getCity2() {
		return this.city2;
	}
}
