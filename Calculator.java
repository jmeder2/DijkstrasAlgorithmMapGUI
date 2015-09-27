/*
 * Joshua Medernach
 * 3-26-15
 * 
 * class Calculator displays the GUI to the user showing the user a list of cities with their associated roads
 * 
 * The user has the ability to add or remove roads, add or remove cities
 * 
 * Lastly, the user can enter in two cities and hit the Calculate button to find the shortest route
 */

import java.awt.*;
import java.util.List;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;

public class Calculator extends JPanel {
	// This JPanel will be a BoxLayout
	
	
	// More general variables stuff here
	private List<City> listOfCities = null;			// We want to keep track of cities
	
	
	private static int roadID = 0;					// We will use this to assign roads ids
	
	private List<City> unvisitedCities = null;			// List of unvisited cities for Calculation purposes
	// private List<City> overallCandidateCities = null;	// List of all candidate cities considered (unvisited) not adjunct to currentCity

	// GUI Components for main screen
	private JFrame frame = null;					// Our window
	private int width = 500;								// Our window will be 500 pixals wide
	private int height = 650;								// Our window height will be 600 pixals tall
	Dimension dim = new Dimension(this.width, this.height);	// Dimension to lock in the width and height
	
	private JLabel title = null;
	
	private JPanel top = null;						// JPanel for top part of window
	private int screenWidth = 450;
	private int screenHeight = 500;
	Dimension screenDim = new Dimension(this.screenWidth, this.screenHeight);
	
	JScrollPane scroller = null;
	
	private JPanel middleTop = null;				// JPanel for Add New City/Road buttons in FlowLayout
	private int buttonMenuWidth = 500;
	private int buttonMenuHeight = 25;
	Dimension buttonMenuDim = new Dimension(this.buttonMenuWidth, this.buttonMenuHeight);
	
	private JPanel middleBottom = null;				// JPanel for textfields
	private int textFieldMenuWidth = 500;
	private int textFieldMenuHeight = 25;
	Dimension textFieldMenuDim = new Dimension(this.textFieldMenuWidth, this.textFieldMenuHeight);
	
	// There isn't a bottom JPanel, instead a Calculate button will be placed there	
	
	private JLabel screen = null;					// JLabel that contains the printout of all cities and roads	
	private JButton addCity = null;					// JButton that adds a new city
	private JButton addRoad = null;					// JButton that adds a new road
	private int addMenuButtonWidth = 125;
	private int addMenuButtonHeight = 25;
	Dimension addMenuButtonDim = new Dimension(addMenuButtonWidth, addMenuButtonHeight);
	
	
	private int addButtonWidth = 100;
	private int addButtonHeight = 25;
	Dimension addButtonDim = new Dimension(addButtonWidth, addButtonHeight);
	
	private JLabel city1Label = null;
	private JLabel city2Label = null;
	private JTextField city1TextBox = null;			// Text field to enter in city 1
	private JTextField city2TextBox = null;			// Text field to enter in city 2
	private JButton calculate = null;				// JButton that calculates shortest route between city 1 and city 2
	
	// Components for adding a new City pop-up
	private JDialog addCityPopup = null;			// Pop up to add a city
	private JLabel cityNameLabel = null;			// JLabel that says "City Name:"
	private JTextField addCityTextBox = null;		// Textfield to enter new city in
	private JButton addCityPopButton = null;		// JButton to confirm new city add
	private JButton cancelCityPopButton = null;		// JButton that cancels the process
	private JPanel buttonHost = null;
	private boolean onScreenAddCity = false;		// Ensures there's only one instance of this pop up on screen
	
	// Components for adding a new Road pop-up
	private JDialog addRoadPopup = null;			// Pop up to add a road
	private JLabel addCity1 = null;					// JLabel that says "City 1 Name:"
	private JTextField addCity1TextBox = null;		// Textbox for City 1
	private JLabel addLength = null;				// JLabel that says "Distance:"
	private JTextField lengthTextBox = null;		// Textbox for Distance
	private JLabel addCity2 = null;					// JLabel that says "City 2  Name:"
	private JTextField addcity2TextBox = null;		// Textbox for City 2
	private JButton addRoadAddButton = null;		// JButton that adds the road
	private JButton addRoadCancelButton = null;		// JButton that cancels the process
	private boolean onScreenAddRoad = false;		// Ensures there's only one instance of this pop up on screen
	
	private int textWidth = 100;
	private int textHeight = 20;
	private Dimension textBoxDim = new Dimension(this.textWidth, this.textHeight);
	
	private JPanel city1Panel = null;
	private JPanel lengthPanel = null;
	private JPanel city2Panel = null;
	private JPanel addRoadButtonsPanel = null;
	
	// Components for a generic JDialog with a generic JLabel passed in
	private JDialog genericPopUp = null;			// A generic pop up
	private JLabel genericLabel = null;				// A generic JLabel displaying a passed-in string
	private JButton genericButton = null;			// A generic button saying "Ok"
	private boolean onScreenGeneric = false;		// Ensures there's only one instance of this pop up on screen
	
	// END OF GLOBAL VARIABLES -----------------------------------------------------------------------------------------------------------
	
	public Calculator() {
		this.listOfCities = new LinkedList<City>();
		
		this.setUpMainScreen();
		
		City city1 = new City("New Orleans");
		this.listOfCities.add(city1);
		
		City city2 = new City("Baton Rouge");
		this.listOfCities.add(city2);
		
		City city3 = new City("Lafayette");
		this.listOfCities.add(city3);
		
		City city4 = new City("Shreveport");
		this.listOfCities.add(city4);
		
		City city5 = new City("Pensacola");
		this.listOfCities.add(city5);
		
		City city6 = new City("Houston");
		this.listOfCities.add(city6);
		
		City city7 = new City("Dallas");
		this.listOfCities.add(city7);
		
		City city8 = new City("Chicago");
		this.listOfCities.add(city8);
		
		this.addNewRoad("New Orleans", "Baton Rouge", 60);
		this.addNewRoad("Lafayette", "Baton Rouge", 50);
		this.addNewRoad("Baton Rouge", "Shreveport", 250);
		this.addNewRoad("Pensacola", "New Orleans", 200);
		this.addNewRoad("Houston", "Lafayette", 220);
		this.addNewRoad("Houston", "Dallas", 240);
		this.addNewRoad("Shreveport", "Dallas", 190);
	}
		
	// Add a new city to the list of cities. If cityExists returns false, then add to list otherwise return -1
	// return 0 for success
	// return -1 for failure
	private int addNewCity(String name) {
		City city = new City(name);
		if (this.listOfCities.indexOf(city) == -1) {
			this.listOfCities.add(city);
			return 0;
		}
		else {
			return -1;
		}
	}
	
	// Add a new road, and adds that road to both cities associated road lists
	// name1 and name2 will be names of the cities that ALREADY EXIST in the listOfCities, and name1 != name2
	private void addNewRoad(String name1, String name2, int distance) {
		City city1 = new City(name1);
		City city2 = new City(name2);
		
		city1 = this.listOfCities.get(this.listOfCities.indexOf(city1));
		city2 = this.listOfCities.get(this.listOfCities.indexOf(city2));
		
		Road road = new Road(roadID, distance, city1, city2);
		roadID = roadID + 1; // Increment the static ID
		
		city1.addRoad(road);
		city2.addRoad(road);
		
		this.updateScreen();
	}
	
	// Return a string representation of absolutely everything, the string format is like:
	// city 1
	// 		roadID distance otherCity
	//		roadID distance otherCity
	//		roadID distance otherCity
	// city 2
	// 		roadID distance otherCity
	// 		roadID distance otherCity
	// city 3
	// 		No Roads listed.
	private String masterPrintEverything() {
		StringBuilder sb = new StringBuilder("");
		City city = null;
		sb.append("<html>");
		for (int i = 0; i < this.listOfCities.size() ; i++) {
			city = this.listOfCities.get(i);
			sb.append(city.getName() + "<br/>");
			sb.append(city.printAllRoadsByCity());
			sb.append("<br/>");
		}
		
		sb.append("</html>");
		
		String printOut = sb.toString();
		if (printOut.equals("")) {
			return "No Cities Listed!";
		}
		else {
			return printOut;
		}
	}
	
	// Updates the JLabel screen with fresh strings
	private void updateScreen() {
		this.screen.setText(this.masterPrintEverything());
	}
	
	// We set up our main window woo!
	private void setUpMainScreen() {
		this.frame = new JFrame("Dijkstra's Algorithm by Joshua Medernach");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(this.dim);
		this.frame.setResizable(false);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.title = new JLabel("List of Cities and Roads");
		this.title.setAlignmentX(CENTER_ALIGNMENT);
		
		this.top = this.setUpScreen(this.top);
		this.middleTop = this.setUpButtonEntries(this.middleTop);
		this.middleBottom = this.setUpTextFields(this.middleBottom);
		
		this.updateScreen();
		
		this.middleTop.setPreferredSize(buttonMenuDim);
		this.middleBottom.setPreferredSize(textFieldMenuDim);
		
		this.scroller = new JScrollPane(this.top, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
			      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.scroller.setPreferredSize(screenDim);
		this.scroller.getVerticalScrollBar().setUnitIncrement(16);
		
		this.add(title);
		this.add(this.scroller);
		this.add(this.middleTop);
		this.add(this.middleBottom);
		
		this.calculate = new JButton();
		
		Action actionCalculate = new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Calculate is pressed");
				Calculate();
			}
		};
		
		this.calculate.setAction(actionCalculate);
		this.calculate.setText("Calculate");
		this.calculate.setPreferredSize(addButtonDim);
		this.calculate.setAlignmentX(CENTER_ALIGNMENT);
		
		this.add(this.calculate);
		
		this.frame.add(this);
		this.frame.setVisible(true);
	}
	
	private JPanel setUpScreen(JPanel top) {
		top = new JPanel();
		this.screen = new JLabel();
		this.updateScreen();
		this.screen.setAlignmentX(LEFT_ALIGNMENT);
		top.add(this.screen);
		return top;
	}
	
	private JPanel setUpButtonEntries(JPanel middleTop) {
		middleTop = new JPanel();
		middleTop.setLayout(new FlowLayout());
		
		// Create an action for Add New City button
		Action actionAddNewCity = new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Add New City is pressed");
				if (!onScreenAddCity && !onScreenAddRoad) {
					onScreenAddCity = true;
					addCityPopup();
				}
			}
		};
		
		// Create an action for Add New City button
		Action actionAddNewRoad = new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Add New Road is pressed");
				if (!onScreenAddRoad && !onScreenAddCity) {
					onScreenAddRoad = true;
					addRoadPopup();
				}
			}
		};
		
		this.addCity = new JButton();
		this.addCity.setAction(actionAddNewCity);
		this.addCity.setText("Add New City");
		this.addCity.setAlignmentX(CENTER_ALIGNMENT);
		this.addCity.setPreferredSize(addMenuButtonDim);
		
		this.addRoad = new JButton();
		this.addRoad.setAction(actionAddNewRoad);
		this.addRoad.setText("Add New Road");
		this.addRoad.setAlignmentX(CENTER_ALIGNMENT);
		this.addRoad.setPreferredSize(addMenuButtonDim);
		
		middleTop.add(this.addCity);
		middleTop.add(Box.createRigidArea(new Dimension(25,0)));
		middleTop.add(this.addRoad);

		return middleTop;
	}
	
	private JPanel setUpTextFields(JPanel middleBottom) {
		middleBottom = new JPanel();
		middleBottom.setLayout(new FlowLayout());
		
		this.city1Label = new JLabel("City 1:");
		this.city2Label = new JLabel("City 2:");
		
		this.city1TextBox = new JTextField();
		this.city2TextBox = new JTextField();
		
		this.city1TextBox.setPreferredSize(this.textBoxDim);
		this.city2TextBox.setPreferredSize(this.textBoxDim);
		
		middleBottom.add(this.city1Label);
		middleBottom.add(Box.createRigidArea(new Dimension(10,0)));
		middleBottom.add(this.city1TextBox);
		middleBottom.add(Box.createRigidArea(new Dimension(25,0)));
		middleBottom.add(this.city2Label);
		middleBottom.add(Box.createRigidArea(new Dimension(10,0)));
		middleBottom.add(this.city2TextBox);
		
		return middleBottom;
	}
	
	private void addCityPopup() {
		this.addCityPopup = new JDialog();
		this.addCityPopup.setLayout(new BoxLayout(this.addCityPopup.getContentPane(), BoxLayout.Y_AXIS));
		this.addCityPopup.setResizable(false);
		
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.cityNameLabel = new JLabel("City Name:");
		this.cityNameLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.addCityTextBox = new JTextField();
		
		this.buttonHost = new JPanel();
		this.buttonHost.setLayout(new FlowLayout());
		
		this.addCityPopButton = new JButton();
		this.cancelCityPopButton = new JButton();
		
		Action actionAddNewCityButton = new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Attempt at adding a new city pressed.");
				addCity(addCityTextBox.getText());
			}
		};
		
		Action actionCancelCityButton = new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Cancelled adding a new city process.");
				onScreenAddCity = false;
				addCityPopup.setVisible(false);
			}
		};
		
		this.addCityPopButton.setAction(actionAddNewCityButton);
		this.addCityPopButton.setText("Add");
		this.addCityPopButton.setPreferredSize(addButtonDim);
		
		this.cancelCityPopButton.setAction(actionCancelCityButton);
		this.cancelCityPopButton.setText("Cancel");
		this.cancelCityPopButton.setPreferredSize(addButtonDim);
		
		this.buttonHost.add(addCityPopButton);
		this.buttonHost.add(Box.createRigidArea(new Dimension(25,0)));
		this.buttonHost.add(cancelCityPopButton);

		this.addCityPopup.add(this.cityNameLabel);
		this.addCityPopup.add(this.addCityTextBox);
		this.addCityPopup.add(this.buttonHost);
				
		this.addCityPopup.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	onScreenAddCity = false;
		    }
		});
		
		this.addCityPopup.setLocationRelativeTo(this.frame);
		this.addCityPopup.pack();
		this.addCityPopup.setVisible(true);
	}
	
	private void addCity(String name) {
		City city = new City(name);
		if (name.equals("")) {
			if (!onScreenGeneric) {
				System.out.println("Add City Failure: No name.");
				onScreenGeneric = true;
				this.genericPopup("City must have a name!");
			}
		}
		else if (this.listOfCities.indexOf(city) != -1) { // Given city already exists
			if (!onScreenGeneric) {
				System.out.println("Add City Failure: City already exists.");
				onScreenGeneric = true;
				this.genericPopup("No Duplicate Cities");
			}
		}
		else {
			System.out.println("Add City Success.");
			this.listOfCities.add(city);
			this.updateScreen();
			this.onScreenAddCity = false;
			this.addCityPopup.setVisible(false);
		}
	}
	
	private void addRoadPopup() {
		this.addRoadPopup = new JDialog();
		this.addRoadPopup.setLayout(new BoxLayout(this.addRoadPopup.getContentPane(), BoxLayout.Y_AXIS));
		this.addRoadPopup.setResizable(false);
		
		this.city1Panel = new JPanel();
		this.city1Panel.setLayout(new FlowLayout());
		
		this.addCity1 = new JLabel("City 1:");
		this.addCity1TextBox = new JTextField();
		this.addCity1TextBox.setPreferredSize(textBoxDim);
		
		this.city1Panel.add(this.addCity1);
		this.city1Panel.add(Box.createRigidArea(new Dimension(25,0)));
		this.city1Panel.add(this.addCity1TextBox);
		
		
		this.lengthPanel = new JPanel();
		this.lengthPanel.setLayout(new FlowLayout());
		
		this.addLength = new JLabel("Length:");
		this.lengthTextBox = new JTextField();
		this.lengthTextBox.setPreferredSize(textBoxDim);
		
		this.lengthPanel.add(this.addLength);
		this.lengthPanel.add(Box.createRigidArea(new Dimension(25,0)));
		this.lengthPanel.add(this.lengthTextBox);
		
		
		this.city2Panel = new JPanel();
		this.city2Panel.setLayout(new FlowLayout());
		
		this.addCity2 = new JLabel("City 2:");
		this.addcity2TextBox = new JTextField();
		this.addcity2TextBox.setPreferredSize(textBoxDim);
		
		this.city2Panel.add(this.addCity2);
		this.city2Panel.add(Box.createRigidArea(new Dimension(25,0)));
		this.city2Panel.add(this.addcity2TextBox);
		
		this.addRoadButtonsPanel = new JPanel();
		this.addRoadButtonsPanel.setLayout(new FlowLayout());
		
		this.addRoadAddButton = new JButton();
		this.addRoadCancelButton = new JButton();
		
		Action actionAddNewRoadButton = new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Attempt at adding a new road pressed.");
				try {
					String test = lengthTextBox.getText();
					int length = Integer.parseInt(test);
					
					// if parsing Integer was successful, then do the following
					addRoad(addCity1TextBox.getText(), addcity2TextBox.getText(), length);
				}
				catch (NumberFormatException e) {
					onScreenGeneric = true;
					genericPopup("Length must be a number!");
				}
			}
		};
		
		Action actionCancelRoadButton = new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Cancelled adding a new road process.");
				onScreenAddRoad = false;
				addRoadPopup.setVisible(false);
			}
		};
		
		this.addRoadAddButton.setAction(actionAddNewRoadButton);
		this.addRoadCancelButton.setAction(actionCancelRoadButton);
		
		this.addRoadAddButton.setText("Add");
		this.addRoadCancelButton.setText("Cancel");
		
		this.addRoadAddButton.setPreferredSize(this.addButtonDim);
		this.addRoadCancelButton.setPreferredSize(this.addButtonDim);
		
		this.addRoadButtonsPanel.add(this.addRoadAddButton);
		this.addRoadButtonsPanel.add(Box.createRigidArea(new Dimension(25,0)));
		this.addRoadButtonsPanel.add(this.addRoadCancelButton);
		
		this.addRoadPopup.add(this.city1Panel);
		this.addRoadPopup.add(this.lengthPanel);
		this.addRoadPopup.add(this.city2Panel);
		this.addRoadPopup.add(this.addRoadButtonsPanel);
		
		this.addRoadPopup.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	onScreenAddRoad = false;
		    }
		});
		
		this.addRoadPopup.setLocationRelativeTo(this.frame);
		this.addRoadPopup.pack();
		this.addRoadPopup.setVisible(true);
	}
	
	private void addRoad(String city1, String city2, int length) {
		City city01 = new City(city1);
		City city02 = new City(city2);
		if (length <= 0) {
			this.onScreenGeneric = true;
			this.genericPopup("Length needs to be a number greater than zero!");
		}
		else if (city1.toLowerCase().equals(city2.toLowerCase())) {
			this.onScreenGeneric = true;
			this.genericPopup("Road cannot loop itself back to the same city!");
		}
		else if (this.listOfCities.indexOf(city01) == -1) {
			this.onScreenGeneric = true;
			this.genericPopup("City 1 does not exist!");
		}
		else if (this.listOfCities.indexOf(city02) == -1) {
			this.onScreenGeneric = true;
			this.genericPopup("City 2 does not exist!");
		}
		else {
			this.addNewRoad(city1, city2, length);
			this.onScreenAddRoad = false;
			this.addRoadPopup.setVisible(false);
		}
	}
	
	private void genericPopup(String message) {
		this.genericPopUp = new JDialog();
		this.genericPopUp.setLayout(new BoxLayout(this.genericPopUp.getContentPane(), BoxLayout.Y_AXIS));
		this.genericPopUp.setResizable(false);
		
		this.genericLabel = new JLabel(message);
		this.genericLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.genericButton = new JButton();
		
		this.genericLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		Action actionGenericOkay = new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Generic okay pressed.");
				onScreenGeneric = false;
				genericPopUp.setVisible(false);
			}
		};
		
		this.genericButton.setAction(actionGenericOkay);
		this.genericButton.setText("Ok");
		this.genericButton.setPreferredSize(addButtonDim);
		this.genericButton.setAlignmentX(CENTER_ALIGNMENT);
		
		this.genericPopUp.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	onScreenGeneric = false;
		    }
		});
		
		this.genericPopUp.add(this.genericLabel);
		this.genericPopUp.add(genericButton);
		
		this.genericPopUp.setLocationRelativeTo(this.frame);
		this.genericPopUp.pack();
		this.genericPopUp.setVisible(true);
	}
	
	/*
	 * Algorithm for shortest path ******************************************************************************************************************
	 */
	private void Calculate() {
		String city1 = this.city1TextBox.getText();
		String city2 = this.city2TextBox.getText();
		
		if (this.checkConditions(city1, city2)) {
			this.Calculate(this.listOfCities.get(this.listOfCities.indexOf(new City(city1))), 
					this.listOfCities.get(this.listOfCities.indexOf(new City(city2))));
		}
	}
	// Checks the conditions for the cities
	private boolean checkConditions(String city1, String city2) {
		City city01 = new City(city1);
		City city02 = new City(city2);
		if (city1.toLowerCase().equals(city2.toLowerCase())) {
			this.onScreenGeneric = true;
			this.genericPopup("Road cannot loop itself back to the same city!");
			return false;
		}
		else if (this.listOfCities.indexOf(city01) == -1) {
			this.onScreenGeneric = true;
			this.genericPopup("City 1 does not exist!");
			return false;
		}
		else if (this.listOfCities.indexOf(city02) == -1) {
			this.onScreenGeneric = true;
			this.genericPopup("City 2 does not exist!");
			return false;
		}
		else {
			return true;
		}
	}
	
	private void Calculate(City city1, City city2) {
		this.unvisitedCities = this.cloneList(this.listOfCities);
		this.unvisitedCities = this.reinitalizePath(this.unvisitedCities);		// Keep track of unvisited cities
		
		System.out.println(this.unvisitedCities.toString());
		
		City currentCity = this.unvisitedCities.get(this.unvisitedCities.indexOf(city1));				// city1 is our first visit
		currentCity.setPathLength(0); 			// This is our initial city, so set initial path to 0!
		
		boolean found = false;					// boolean if city2 is found, set true
		boolean loopBreak = false;				// if city2 isn't found and all possible reachable cities are visited, set true
		
		// things to consider:
		// we will be using Dijkstra's algorithm
		// each unvisited city contains -1 as distance, which represents infinity
		while (!found && !loopBreak) {
			
			this.compileNeighborCities(currentCity);
			this.unvisitedCities.remove(currentCity);	// Since currentCity is visited, remove from list
			
			// Some conditions to go through
			// Has the destination city been marked?
			if (currentCity.equals(city2)) {
				found = true;
				this.printSolutionPath(currentCity); // currentCity contains the solution path
			}
			// If not, is the smallest distance among cities in the unvisited list infinity?
			else if (this.selectSmallestDistanceUnvisitedCity(this.unvisitedCities).getPathLength() == -1) {
				// There's really no cities to backtrack to so output Infinity (destination city is unreachable)
				loopBreak = true;
				this.onScreenGeneric = true;
				this.genericPopup("Infinity");
			}
			// Looks like destination city hasn't been found and there's a non-infinity city in the unvisited list
			// Set currentCity to the smallest non-infinity distanced unvisited city
			else {
				currentCity = this.selectSmallestDistanceUnvisitedCity(this.unvisitedCities);
			}
		}	
	}
	
	private City selectSmallestDistanceUnvisitedCity(List<City> list) {
		City city = new City("");
		city.setPathLength(-1);
		City testCity = null;
		boolean set = false; // We want to replace city with first occurrence of getPathLength != 1 only once
		for (int i = 0; i < list.size(); i++) {
			testCity = list.get(i);
			if ((!set) && (testCity.getPathLength() != -1)) {
				city = testCity;
				set = true;
			}
			else if ((testCity.getPathLength() != -1) && (testCity.getPathLength() < city.getPathLength())) {
				city = testCity;
			}
		}
		return city;
	}
	
	// Resets all cities path lengths and order number
	private List<City> reinitalizePath(List<City> list) {
		City city = null;
		for (int i = 0; i < list.size(); i++) {
			city = list.get(i);
			city.initializePathLength();
			city.resetSolutionPath();
		}
		return list;
	}
	
	private void compileNeighborCities(City currentCity) {
		// For loop initializations
		int currentCityRoadCount = 0;
		List<Road> roadList = null;									// roadList of currentCity
		Road road = null;											// Road to be inspected
		City city = null;											// City to be inspected
		currentCityRoadCount = currentCity.getNumOfRoads();
		roadList = currentCity.getListOfRoads();
		
		// Set the distance and solution path to a neighbor city
		for (int i = 0; i < currentCityRoadCount; i++) {
			road = roadList.get(i);
			city = currentCity.otherCity(road); // Get the other city this road connects currentCity to
			
			// Next, we want to make sure the neighbor city is unvisited
			if (this.unvisitedCities.contains(city)) {
				// Unvisited! so add city to candidate city
				
				// Set the overall distance and solution path to neighbor city
				if (city.getPathLength() == -1) { // this has been marked as infinity, safe to assign distance to it
					city.setPathLength(currentCity.getPathLength() + road.getLength());
					city.setSolutionPath(currentCity.getSolutionPath());						
				}
				else if (city.getPathLength() > (currentCity.getPathLength() + road.getLength())) {
					// This means the path previously taken to get to this neighbor city is longer than
					// going to the currentCity plus taking path to this neighbor city
					// so replace this neighbor city path with the new shortest distance
					city.setPathLength(currentCity.getPathLength() + road.getLength());

					// We also want to update the solution path for this neighbor city as well
					city.resetSolutionPath();
					city.setSolutionPath(currentCity.getSolutionPath());
				}
				// else here, but not needed
				// the neighbor city is already assigned a distance that is shorter than reaching currentCity + taking path to the 
				// neighbor city so leave the neighborcity's original assigned distance and solution path alone
			}
		}
		// All neighboring cities have been compiled
	}

	private void printSolutionPath(City city) {
		// the solution path should be already in order on a linked list
		// don't forget to tally the distance and print it as well
		
		StringBuilder sb = new StringBuilder("");
		sb.append("<html> <center>The Solution Path</center> <br/>");
		
		List<City> path = city.getSolutionPath();
		
		City city1 = null;
		City city2 = null;
		List<Road> roadList = null;
		Road road = null;
		
		int distanceTally = 0;
		// Iterate through the solution path. Need to match roads to the cities on solution path
		for (int i = 0; i < path.size() ; i++) {
			city1 = path.get(i);
			if ((i+1) < path.size()) {
				city2 = path.get(i+1);
			}
			
			// Print the name of city1
			if ((i+1) < path.size()) {
				sb.append("<center>" + city1.getName() + "</center>");
				sb.append("<center>|</center>");
			}
			else {
				sb.append("<center>" + city1.getName() + "</center>");
			}
			
			// Access the list of roads associated with city1
			roadList = city1.getListOfRoads();
			for (int j = 0; j < roadList.size() ; j++) {
				road = roadList.get(j);
				if (city1.otherCity(road).equals(city2)) {
					// We found the road that connects city1 and city2 together
					sb.append("Road ID: " + road.getRoadID() + "&emsp;Length: " + road.getLength() + "<br/><center>|</center>");
					distanceTally = distanceTally + road.getLength();
					j = roadList.size(); // stop the j for loop early
				}
			}			
		}
		
		sb.append("<br/> <center>Total Distance: " + distanceTally + "</center></html>");
		
		this.onScreenGeneric = true;
		this.genericPopup(sb.toString());
	}
	
	private List<City> cloneList(List<City> list) {
		List<City> clone = new LinkedList<City>();
		for (int i = 0 ; i < list.size() ; i++) {
			clone.add(list.get(i));
		}
		return clone;
	}
}