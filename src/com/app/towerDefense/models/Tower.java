package com.app.towerDefense.models;
/**
 * 
 */

import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Icon;

import org.apache.log4j.Logger;

import com.app.towerDefense.guiComponents.MapPanel;
import com.app.towerDefense.staticContent.ApplicationStatics;

/**
 * Tower abstract class is the super-typer of all types of objects produced by
 * the TowerFactory.
 * 
 * @author George Ekow-Daniels
 *
 */
public abstract class Tower implements Observer {

	public int towerID;
	protected String towerName;
	protected int towerRange;
	protected int towerPower;
	protected int towerFireRate;
	protected int towerCost;
	protected int towerlevel;
	protected Icon towerImage;
	protected String towerImagePath;
	protected Dimension TowerCordinate;
	protected int towerUpgradeCost;
	protected int towerlevelUpgrade;
	protected int towerPowerUpgrade;
	protected int towerFireRateUpgrade;
	protected int towerFireRangeUpgrade;
	protected int x, y;
	protected Strategy strategy;
	protected String specialEffect;

	protected double xt, yt, dtw, dth; // x,y coordinates of range circle and
										// diameters

	protected Timer timer; // timer for shooting
	protected boolean shoot = true; // shoots when value is true
	CritterType targetCritter;

	public int bW, bH; // block width and height
	int xMid, yMid;
	final static Logger logger = Logger.getLogger(Tower.class);

	long curT = 0; // temporary variable
	int prevCritterID = 0; // previous target critter id

	/**
	 * Constructor
	 */
	public Tower() {
		timer = new Timer();
		bW = ApplicationStatics.BLOCK_WIDTH;
		bH = ApplicationStatics.BLOCK_HEIGHT;
	}

	/**
	 * This method gets the name of the tower
	 * 
	 * @return the tower name
	 */
	public abstract String getTowerName();

	/**
	 * This method sets the Tower name
	 * 
	 */
	public abstract void setTowerName();

	/**
	 * This method gets the value the value for range upgrade
	 * 
	 * @return the tower fire range upgrade value
	 */
	public abstract int getTowerFireRangeUpgrade();

	/**
	 * This method sets the tower fire range upgrade value
	 * 
	 */
	public abstract void setTowerFireRangeUpgrade();

	/**
	 * This method gets the
	 * 
	 * @return the tower range
	 */
	public abstract int getTowerRange();

	/**
	 * This is the method for setting the tower range
	 * 
	 */
	public abstract void setTowerRange();

	/**
	 * This method gets tower power
	 * 
	 * @return the tower power
	 */
	public abstract int getTowerPower();

	/**
	 * The method sets the <b>power</b> of the tower
	 * 
	 */
	public abstract void setTowerPower();

	/**
	 * This set the firing rate of a tower
	 * 
	 * @return the fire rate of a tower
	 */
	public abstract int getTowerFireRate();

	/**
	 * This method sets the firing rate of a tower
	 * 
	 */
	public abstract void setTowerFireRate();

	/**
	 * This method gets the current cost of towers
	 * 
	 * @return the cost of tower
	 */
	public abstract int getTowerCost();

	/**
	 * This method sets the cost of the tower
	 * 
	 */
	public abstract void setTowerCost();

	/**
	 * This method gets the tower level
	 * 
	 * @return the current tower level
	 */
	public abstract int getTowerlevel();

	/**
	 * This method sets the tower level
	 *
	 */
	public abstract void setTowerlevel();

	/**
	 * This method sets the tower image
	 * 
	 * @return the tower image
	 */
	public abstract Icon getTowerImage();

	/**
	 * This method sets the image of the tower
	 */
	public abstract void setTowerImage();

	/**
	 * This method gets the tower coordinates
	 * 
	 * @return the tower coordinate
	 */
	public abstract Dimension getTowerCordinate();

	/**
	 * This method sets the tower coordinate
	 *
	 */
	public abstract void setTowerCordinate();

	/**
	 * This method gets upgrade cost of a tower
	 * 
	 * @return the tower upgrade cost
	 */
	public abstract int getTowerUpgradeCost();

	/**
	 * This method sets the tower upgrade cost
	 * 
	 */
	public abstract void setTowerUpgradeCost();

	/**
	 * This method gets tower upgrade level
	 * 
	 * @return the tower level upgrade
	 */
	public abstract int getTowerlevelUpgrade();

	/**
	 * This method sets level upgrade of a tower
	 *
	 */
	public abstract void setTowerlevelUpgrade();

	/**
	 * This method gets power upgrade of a tower
	 * 
	 * @return the tower power upgrade
	 */
	public abstract int getTowerPowerUpgrade();

	/**
	 * This method sets tower power upgrade
	 * 
	 */
	public abstract void setTowerPowerUpgrade();

	/**
	 * This method gets the fire rate update of a tower
	 * 
	 * @return the tower fire rate update
	 */
	public abstract int getTowerFireRateUpgrade();

	/**
	 * This method sets the tower firing rate upgrade
	 * 
	 */
	public abstract void setTowerFireRateUpgrade();

	/**
	 * This method get the tower model's X coordinate
	 * 
	 * @return x coordinate of tower model
	 */
	public abstract int getX();

	/**
	 * This method get the tower model's Y coordinate
	 * 
	 * @return y coordinate of tower model
	 */
	public abstract int getY();

	/**
	 * This method sets the X and Y coordinates of the tower model
	 * 
	 * @param new_x
	 *            x axis
	 * @param new_y
	 *            y axis
	 */
	public abstract void setXY(int new_x, int new_y);

	/**
	 * This method upgrades Tower
	 */
	public abstract void upgradeTower();

	/**
	 * This method calculates the amount for refund after a sell of a tower
	 * 
	 * @return the value of the tower
	 */
	public abstract int getRefund();

	/**
	 * This method returns current tower strategy
	 * 
	 * @return strategy object
	 */
	public Strategy getStrategy() {
		return strategy;
	}

	/**
	 * Plugs in a specific strategy to be used
	 * 
	 * @param new_strategy
	 *            Strategy object
	 */
	public abstract void setStrategy(Strategy new_strategy);

	/**
	 * This method executes the target selection based on strategy assigned
	 * 
	 * @param new_tower
	 *            current tower
	 * @param new_critter
	 *            critter that in range of the tower
	 */
	public abstract void executeStrategy(Tower new_tower, CritterType new_critter);

	/**
	 * update method that called when observable called its function notify
	 * updates the location between tower and critter if it in range of tower,
	 * get shot
	 * 
	 * @param new_critter
	 *            observable critter
	 * @param new_x
	 *            the object
	 */
	public void update(Observable new_critter, Object new_x) {

		Ellipse2D ellipse = new Ellipse2D.Double(xt, yt, dtw, dth);
		if (targetCritter != null) {
			if (!ellipse.contains(targetCritter.getXCr(), targetCritter.getYCr()) || targetCritter.getIsDead()) {
				targetCritter = null;
			}
		}

		// critter is in range of tower
		if (ellipse.contains(((CritterType) new_critter).getXCr(), ((CritterType) new_critter).getYCr())) {

			this.executeStrategy(this, ((CritterType) new_critter));

		}
	}

	/*
	 * This method returns the tower x coordinate
	 * 
	 * @return x coordinate
	 */
	public double getXT() {
		return xt;
	}

	/*
	 * This method returns the tower y coordinate
	 * 
	 * @return y coordinate
	 */
	public double getYT() {
		return yt;
	}

	/**
	 * This method returns the special effect name
	 * 
	 * @return special effect name
	 */
	public String getSpecialEffect() {
		return specialEffect;
	}

	/**
	 * This method calculates the tower range circle area, top left x and y
	 * coordinates
	 */
	public void calculateRangeCircleCoordinates() {

		bW = ApplicationStatics.BLOCK_WIDTH;
		bH = ApplicationStatics.BLOCK_HEIGHT;

		double rangeT = (double) getTowerRange() / 100 - 1;
		if (rangeT == -1) {
			rangeT = 0;
		}

		xt = y * bW - bW - bW * rangeT;
		yt = x * bH - bH - bH * rangeT;

		// calculation the range circle diameter
		rangeT *= 2;
		dtw = ApplicationStatics.BLOCK_WIDTH * 3 + ApplicationStatics.BLOCK_WIDTH * rangeT;
		dth = ApplicationStatics.BLOCK_HEIGHT * 3 + ApplicationStatics.BLOCK_HEIGHT * rangeT;

		// calculation of mid points of tower button
		xMid = y * bW - bW / 2;
		yMid = x * bH - bH / 2;
	}

	/**
	 * This method returns the diameter in width of range circle of the tower
	 * 
	 * @return diameter value width
	 */
	public double getDTW() {
		return dtw;
	}

	/**
	 * This method returns the diameter in height of range circle of the tower
	 * 
	 * @return diameter value height
	 */
	public double getDTH() {
		return dth;
	}

	/**
	 * This method shoots the target critter and initiates the bullet drawing
	 */
	public void shoot() {

		if (targetCritter != null) {

			if (shoot) {
				if (targetCritter.decreaseLife(getTowerPower())) {
					logger.info("Tower_" + getTowerName() + "_towerID_" + towerID + " shoots critter id:"
							+ targetCritter.getCritterId() + " damage:" + getTowerPower());
				}
				if (getSpecialEffect() == "Freeze") {
					targetCritter.setCritterImage(ApplicationStatics.IMAGE_PATH_CRITTER_FROZEN);
					targetCritter.slowSpeed();
				} else if (getSpecialEffect() == "Burn") {
					targetCritter.setCritterImage(ApplicationStatics.IMAGE_PATH_CRITTER_BURN);
					targetCritter.burnHealth(getTowerPower());
				} else if (getSpecialEffect() == "Splash") {
					targetCritter.splashDamage(getTowerPower());
				}

				shoot = false;
				curT = System.currentTimeMillis();
				prevCritterID = targetCritter.getCritterId();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						shoot = true;
					}
				}, 1000);

			}

			if (!shoot && System.currentTimeMillis() - curT < 300 && prevCritterID == targetCritter.getCritterId()) {
				MapPanel.drawLines(getY() * bW + bW / 2, getX() * bH + bH / 2, targetCritter.getXCr(),
						targetCritter.getYCr(), getTowerName(), targetCritter.getCritterId());
			}
			// delete critter from static critter arraylist if it gets killed by
			// the tower
			if (targetCritter.killCritter()) {
				logger.info("Tower_" + getTowerName() + "_towerID_" + towerID + " killed critter id:"
						+ targetCritter.getCritterId());
				targetCritter = null;
			}
		}
	}

	/**
	 * This method returns the targeted critter object
	 * 
	 * @return critter object
	 */
	public CritterType getTargetCritter() {
		return targetCritter;
	}

	/**
	 * This method assigns the the target critter to a tower
	 * 
	 * @param new_critter new critter
	 */
	public void setTargetCritter(CritterType new_critter) {
		targetCritter = new_critter;
	}

	/**
	 * This method returns the middle x point of a tower button on the map
	 * 
	 * @return x middle coordinate
	 */
	public int getXMid() {
		return xMid;
	}

	/**
	 * This method returns the middle y point of a tower button on the map
	 * 
	 * @return y middle coordinate
	 */
	public int getYMid() {
		return yMid;
	}

	// END
}
