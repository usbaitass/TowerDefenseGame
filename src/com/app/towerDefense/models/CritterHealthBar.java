package com.app.towerDefense.models;

import java.util.Observable;
import java.util.Observer;

/**
 * This class is an implementation of critter health bar it observes the critter
 * 
 * @author usbaitass
 *
 */
public class CritterHealthBar implements Observer {

	public int x, y, xMid, xEnd;

	/**
	 * Constructor
	 */
	public CritterHealthBar() {
	}

	@Override
	public void update(Observable o, Object arg) {
		BasicCritter cr = (BasicCritter) o;

		x = cr.getX() + 15;
		y = cr.getY() + 36;
		xMid = x + cr.getCurrentHealth();
		xEnd = x + cr.getActualHealth();
	}

	// END
}
