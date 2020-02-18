
package com.app.towerDefense.models;

/**
 * Tower Factory that creates different kinds of Tower objects by calling its
 * static factory method
 * 
 * @author George Ekow-Daniels
 *
 */
public class TowerFactory {

	/**
	 * Constructor is made private to prevent instantiation
	 */
	private TowerFactory() {
	};

	/**
	 * This method allows as to create a variation of towers based on the name
	 * of the tower parsed to it as parameter
	 * 
	 * @param new_towerName
	 *            is the name of the tower(Burner,Freezer,Shooter,Splasher) for
	 *            which the tower is created
	 * @return a Tower name
	 */
	static public Tower getTower(String new_towerName) {
		if (new_towerName.equalsIgnoreCase("Burner")) {
			return new Burner();
		} else if (new_towerName.equalsIgnoreCase("Freezer")) {
			return new Freezer();
		} else if (new_towerName.equalsIgnoreCase("Shooter")) {
			return new Shooter();
		} else if (new_towerName.equalsIgnoreCase("Splasher")) {
			return new Splasher();
		}
		return null;
	}

}
