package com.app.towerDefense.models;

import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.app.towerDefense.guiComponents.MapPanel;
import com.app.towerDefense.staticContent.ApplicationStatics;

/**
 * 
 * @author usbaitass
 * 
 *         Player class that stores the Player information such as sunCurrency,
 *         hp, gameWave, player name and his Towers.
 * 
 */
public class PlayerModel extends Observable {

	private int sunCurrency;
	private int hpPlayer;
	private int gameWave;
	public String playerName;
	public static ArrayList<Tower> towerModelArray;
	final static Logger logger = Logger.getLogger(MapPanel.class);
	public int lastTowerID = 0;

	/**
	 * Constructor that initializes default values
	 */
	public PlayerModel() {
		if (playerName == null) {
			playerName = "Defualt";
		}
		sunCurrency = 500;
		hpPlayer = 10;
		gameWave = 1;
		towerModelArray = new ArrayList<Tower>();
	}

	/**
	 * Constructor that assigns its parameter to class attributes
	 * 
	 * @param new_playerName
	 *            Player name
	 * @param new_sunCurrency
	 *            Player's amount of money
	 * @param new_hpPlayer
	 *            Player health status
	 * @param new_gameWave
	 *            Game wave level
	 */
	public PlayerModel(String new_playerName, int new_sunCurrency, int new_hpPlayer, int new_gameWave) {
		playerName = new_playerName;
		sunCurrency = new_sunCurrency;
		hpPlayer = new_hpPlayer;
		gameWave = new_gameWave;
		towerModelArray = new ArrayList<Tower>();

	}

	/**
	 * The method gets suNCurrency
	 * 
	 * @return amount of sun currency Player has.
	 */
	public int getSunCurrency() {
		return sunCurrency;
	}

	/**
	 * adds value to sun currency
	 * 
	 * @param new_value
	 *            amount of increase
	 */
	public void addSunCurrency(int new_value) {
		sunCurrency += new_value;
		setChanged();
		notifyObservers();
	}

	/**
	 * subtracts value from sun currency
	 * 
	 * @param new_value
	 *            amount of decrease
	 */
	public void subSunCurrency(int new_value) {
		sunCurrency -= new_value;
		setChanged();
		notifyObservers();
	}

	/**
	 * This method gets the health of a player
	 * 
	 * @return amount of health Player has.
	 */
	public int getHpPlayer() {
		return hpPlayer;
	}

	/**
	 * This method gets the game wave
	 * 
	 * @return current game level
	 */
	public int getGameWave() {
		return gameWave;
	}

	/**
	 * This method gets the player name
	 * 
	 * @return Name of the Player
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * creates new Tower Model and adds it to ArrayList of Tower Models of
	 * Player
	 * 
	 * @param new_towerID
	 *            Tower id
	 * @return bought tower model
	 */
	public Tower buyTower(int new_towerID) {
		Tower tempTM = null;

		switch (new_towerID) {
		case 0:
			tempTM = TowerFactory.getTower("Shooter");
			break;
		case 1:
			tempTM = TowerFactory.getTower("Freezer");
			break;
		case 2:
			tempTM = TowerFactory.getTower("Burner");
			break;
		case 3:
			tempTM = TowerFactory.getTower("Splasher");
			break;

		}
		tempTM.towerID = lastTowerID++;
		towerModelArray.add(tempTM);

		ApplicationStatics.HAS_BOUGHT_TOWER = true;
		subSunCurrency(tempTM.getTowerCost());

		logger.info("Player bought Tower = " + Integer.toString(new_towerID) + ", Tower_" + tempTM.getTowerName()
				+ "_towerID_" + tempTM.towerID);

		return tempTM;
	}

	/**
	 * deletes the tower from ArrayList of Tower Models
	 * 
	 * @param new_towerID
	 *            Tower id that will be removed
	 * @return true if deleted successfully
	 */
	public boolean sellTower(int new_towerID) {
		logger.info("inside sellTower()");
		if (towerModelArray.isEmpty()) {
			return false;
		} else {
			int refundTM = towerModelArray.get(new_towerID).getRefund();
			addSunCurrency(refundTM);
			logger.info("Player sell Tower = " + towerModelArray.get(new_towerID).towerID + ", Tower_"
					+ towerModelArray.get(new_towerID).getTowerName() + "_towerID_"
					+ towerModelArray.get(new_towerID).towerID);
			towerModelArray.remove(new_towerID);
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "You were refunded = " + Integer.toString(refundTM) + " suns.");
			return true;
		}
	}

	/**
	 * call the function from tower model that upgrade the tower
	 * 
	 * @param new_towerID
	 *            Tower id
	 * @return Tower model with upgraded attributes
	 */
	public Tower upgradeTower(int new_towerID) {
		Tower tempTM = towerModelArray.get(new_towerID);
		logger.info("Player upgrade Tower = " + towerModelArray.get(new_towerID).towerID + ", Tower_"
				+ towerModelArray.get(new_towerID).getTowerName() + "_towerID_"
				+ towerModelArray.get(new_towerID).towerID);
		tempTM.upgradeTower();
		return tempTM;
	}

	/**
	 * prints all towers in the ArrayList in System command
	 */
	public void printAllTowers() {
		for (int i = 0; i < towerModelArray.size(); i++) {
			logger.info(towerModelArray.get(i).getTowerName() + " x=" + towerModelArray.get(i).getX() + " y="
					+ towerModelArray.get(i).getY());
			// System.out.println(towerModelArray.get(i).getTowerName() + "
			// x="+towerModelArray.get(i).getX() + "
			// y="+towerModelArray.get(i).getY());
		}
	}

	/**
	 * This method gets the tower array
	 * 
	 * @return the tower model array list
	 */
	public ArrayList<Tower> getTowerModelArray() {
		return towerModelArray;
	}

	/**
	 * This method substracts hit points from player current health points
	 * 
	 * @param new_n
	 *            hit points to be substracted
	 * @return true if player is still has health
	 */
	public boolean decrementHealth(int new_n) {
		hpPlayer -= new_n;
		logger.info("Critter reached the Exit: -1 from Hit Points!");
		setChanged();
		notifyObservers();
		if (hpPlayer <= 0) {
			// JFrame frame = new JFrame();
			logger.info("Hit Points reached zero.");
			// JOptionPane.showMessageDialog(frame,"You are dead.");
			return false;
		}
		return true;
	}

	/**
	 * this method increment game wave and alert observers about the changes
	 */
	public void incGameWave() {
		gameWave++;
		setChanged();
		notifyObservers();
	}

	// END
}
