package com.app.towerDefense.models;

/**
 * The classes that implement a concrete strategy should implement this. The
 * Tower class uses this to use a concrete strategy.
 * 
 * @author usbaitass
 *
 */
public interface Strategy {

	/**
	 * Method whose implementation varies depending on the strategy adopted.
	 * 
	 * @param new_tower
	 *            tower that initated the execute
	 * @param new_critter
	 *            a potential target critter
	 */
	void execute(Tower new_tower, CritterType new_critter);

	/**
	 * This method returns the strategy name
	 * 
	 * @return strategy name
	 */
	public String getStrategyName();

	// END
}
