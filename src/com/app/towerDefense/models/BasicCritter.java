package com.app.towerDefense.models;

import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import com.app.towerDefense.guiComponents.MapPanel;
import com.app.towerDefense.staticContent.ApplicationStatics;

import javafx.beans.InvalidationListener;

/**
 * {@inheritDoc} This class is the basic critter that would move on the map
 * Contains different methhods to create and move a critter on the map selected
 * by player
 * 
 * @author Amritansh Mishra
 *
 */
public class BasicCritter extends java.util.Observable implements CritterType {

	private int critterId;
	private Image image;
	private int value;
	private int actualHealth;
	private int currentHealth;
	private CritterHealthBar healthBar;
	public boolean isDead = false;
	private String currentPath;

	// current X and Y position of critter
	private int x, y;
	// decides which direction critter moves
	public int directionX;
	public int directionY;
	public int currentBlocki = 0;
	// x and y of the next path point
	private int xNext;
	private int yNext;
	// map blocks size
	public int blockW, blockH;

	private int speed;
	private int actualSpeed;
	private Timer timerFreezing;
	private Timer timerBurning;
	private Timer timerSplash;
	private boolean showSplashArea;

	// middle points of image critter on the map
	public int xCr, yCr;

	/**
	 * Constructor of a critter based on the wave level parameter
	 * 
	 * @param new_waveLevel
	 *            wave level parameter
	 */
	public BasicCritter(int new_waveLevel) {
		currentPath = ApplicationStatics.IMAGE_PATH_MAP_CRITTER1;
		image = new ImageIcon(currentPath).getImage();

		int multiplier = (new_waveLevel - 1) / 3 + 1;
		actualHealth = 10 * multiplier;
		currentHealth = actualHealth;
		actualSpeed = (new_waveLevel - 1) / 5 + 2;
		speed = actualSpeed;
		value = actualHealth / 2 + actualSpeed;

		blockW = ApplicationStatics.BLOCK_WIDTH;
		blockH = ApplicationStatics.BLOCK_HEIGHT;

		timerFreezing = new Timer();
		timerBurning = new Timer();
		timerSplash = new Timer();
		showSplashArea = false;

		healthBar = new CritterHealthBar();
		this.addObserver(healthBar);

	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setXY(int new_xEntry, int new_yEntry) {
		x = new_xEntry;
		y = new_yEntry;

	}

	@Override
	public int getActualHealth() {
		return actualHealth;
	}

	@Override
	public int getCurrentHealth() {
		return currentHealth;
	}

	@Override
	public int getCritterId() {
		return critterId;
	}

	@Override
	public void setID(int new_id) {
		critterId = new_id;
	}

	@Override
	public Image getCritterImage() {
		return image;
	}

	@Override
	public void setCritterImage(String new_path) {

		if (ApplicationStatics.IMAGE_PATH_MAP_CRITTER1 == currentPath) {
			image = new ImageIcon(new_path).getImage();
			currentPath = new_path;
		}
	}

	/**
	 * This method returns the money value of a critter when it dies.
	 * 
	 * @return amount of currency
	 */
	public int getValue() {
		return value;
	}

	/**
	 * This method returns the critter speed
	 * 
	 * @return speed value
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * This method sets the critter speed to original value
	 */
	public void setToNormalSpeed() {
		speed = actualSpeed;
	}

	@Override
	public CritterHealthBar getHealthBar() {

		return healthBar;
	}

	@Override
	public boolean getShowSplashArea() {

		return showSplashArea;
	}

	@Override
	public void addListener(InvalidationListener new_arg0) {
		// Not needed

	}

	@Override
	public void removeListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean calculatePath() {

		// System.out.println("i : "+i+" , arraysize : "+
		// (ApplicationStatics.PATH_ARRAY1.size()-1));

		// check if it is the end of the path
		if (currentBlocki == ApplicationStatics.PATH_ARRAY1.size() - 1) {
			return false;
		}

		// calculate the next x and y coordinates of the next block of the path
		xNext = ApplicationStatics.PATH_ARRAY1.get(currentBlocki + 1).y * blockW;
		yNext = ApplicationStatics.PATH_ARRAY1.get(currentBlocki + 1).x * blockH;
		// calculate the critter movement direction
		directionY = ApplicationStatics.PATH_ARRAY1.get(currentBlocki + 1).x
				- ApplicationStatics.PATH_ARRAY1.get(currentBlocki).x;
		directionX = ApplicationStatics.PATH_ARRAY1.get(currentBlocki + 1).y
				- ApplicationStatics.PATH_ARRAY1.get(currentBlocki).y;

		// move to next block when conditions satisfy
		if (directionX == 1 || directionX == -1) {
			if (x == xNext || x == xNext - 1) {
				// System.out.println("inside 1 :" + (i++));
				currentBlocki++;
				x = xNext;
			}
		} else if (directionX == 0) {
			if (directionY == 1 || directionY == -1) {
				if (y == yNext || y == yNext - 1) {
					// System.out.println("inside 2" + (i++));
					currentBlocki++;
					y = yNext;
				}
			}
		}
		// increment or decrement the x and y coordinates of the critter
		x += directionX * speed;
		y += directionY * speed;

		xCr = x + blockW / 3;
		yCr = y + blockH / 3;

		// notify all observers about a change in x and y
		setChanged();
		notifyObservers();
		// System.out.println("inside critter x : " + x + " , y : " + y);

		return true; // successful path calculation

	}

	@Override
	public boolean decreaseLife(int new_power) {
		// TODO Auto-generated method stub
		if (currentHealth <= 0) {
			return false;
		}
		currentHealth = currentHealth - new_power;

		return true;
	}

	/**
	 * This method slows the speed of critter movement
	 */
	public void slowSpeed() {
		speed = 1;
		timerFreezing.schedule(new TimerTask() {
			@Override
			public void run() {
				// Your database code here
				speed = actualSpeed;
				image = new ImageIcon(ApplicationStatics.IMAGE_PATH_MAP_CRITTER1).getImage();
				currentPath = ApplicationStatics.IMAGE_PATH_MAP_CRITTER1;
			}
		}, 1450);

	}

	/**
	 * This method burns the critter for the same amount after taking the damage
	 * from burning tower
	 * 
	 * @param new_amount
	 *            amount health to burn
	 */
	public void burnHealth(final int new_amount) {
		timerBurning.schedule(new TimerTask() {
			@Override
			public void run() {
				// Your database code here
				if (decreaseLife(new_amount)) {
					System.out.println("critter id:" + critterId + " is burning for -" + new_amount);
				}
				killCritter();
				image = new ImageIcon(ApplicationStatics.IMAGE_PATH_MAP_CRITTER1).getImage();
				currentPath = ApplicationStatics.IMAGE_PATH_MAP_CRITTER1;
			}
		}, 1450);
	}

	@Override
	public boolean killCritter() {
		// TODO Auto-generated method stub
		if (currentHealth <= 0 && !isDead) {
			System.out.println("critter id:" + critterId + " is killed.");
			ApplicationStatics.PLAYERMODEL.addSunCurrency(getValue());
			for (int i = 0; i < MapPanel.critter.size(); i++) {
				if (MapPanel.critter.get(i).getCritterId() == critterId) {
					MapPanel.critter.remove(i);
				}
			}
			isDead = true;
			setChanged();
			notifyObservers();
			return true;
		}
		return false;
	}

	@Override
	public void splashDamage(int new_amount) {
		// TODO Auto-generated method stub

		showSplashArea = true;

		timerSplash.schedule(new TimerTask() {
			@Override
			public void run() {
				// Your database code here
				showSplashArea = false;
			}
		}, 1450);

		Ellipse2D ellipse = new Ellipse2D.Double(x - blockW / 2, y - blockH / 2, blockW, blockH);
		for (int i = 0; i < MapPanel.critter.size(); i++) {
			if (ellipse.contains(MapPanel.critter.get(i).getX(), MapPanel.critter.get(i).getY())) {
				if (critterId != MapPanel.critter.get(i).getCritterId()) {
					if (MapPanel.critter.get(i).decreaseLife(new_amount)) {
						System.out.println("critter id:" + MapPanel.critter.get(i).getCritterId()
								+ " hit by splash damage -" + new_amount);
						MapPanel.critter.get(i).killCritter();
					}
				}
			}
		}
	}

	@Override
	public int getXCr() {
		// TODO Auto-generated method stub
		return xCr;
	}

	@Override
	public int getYCr() {
		// TODO Auto-generated method stub
		return yCr;
	}

	@Override
	public boolean getIsDead() {
		// TODO Auto-generated method stub
		return isDead;
	}

	@Override
	public int getCurrentBlockI() {
		// TODO Auto-generated method stub
		return currentBlocki;
	}

	@Override
	public int getDirectionX() {
		// TODO Auto-generated method stub
		return directionX;
	}

	@Override
	public int getDirectionY() {
		// TODO Auto-generated method stub
		return directionY;
	}

}
