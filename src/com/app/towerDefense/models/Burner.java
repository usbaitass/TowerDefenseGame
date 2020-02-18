package com.app.towerDefense.models;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.app.towerDefense.staticContent.ApplicationStatics;

/**
 * {@inheritDoc} This class create a burner tower from the Tower Class.
 * 
 * @author George Ekow-Daniels
 *
 */
public class Burner extends Tower {

	/**
	 * Constructor for the Burner Tower This initiates the values for the tower
	 */
	public Burner() {
		setTowerName();
		setTowerImage();
		setTowerCost();
		setTowerlevel();
		setTowerPower();
		setTowerRange();
		setTowerFireRate();

		setTowerUpgradeCost();
		setTowerlevelUpgrade();
		setTowerFireRangeUpgrade();
		setTowerFireRateUpgrade();
		setTowerPowerUpgrade();

		specialEffect = "Burn";
		this.setStrategy(new NearestToEndPointStrategy());
	}

	@Override
	public String getTowerName() {
		return super.towerName;
	}

	@Override
	public void setTowerName() {
		super.towerName = "Burner";
	}

	@Override
	public int getTowerFireRangeUpgrade() {
		return super.towerFireRangeUpgrade;
	}

	@Override
	public void setTowerFireRangeUpgrade() {
		super.towerFireRangeUpgrade = 0;
	}

	@Override
	public int getTowerRange() {
		return super.towerRange;
	}

	@Override
	public void setTowerRange() {
		super.towerRange = 100;
	}

	@Override
	public int getTowerPower() {
		return super.towerPower;
	}

	@Override
	public void setTowerPower() {
		super.towerPower = 1;
	}

	@Override
	public int getTowerFireRate() {
		return super.towerFireRate;
	}

	@Override
	public void setTowerFireRate() {
		super.towerFireRate = 1;
	}

	@Override
	public int getTowerCost() {
		return super.towerCost;
	}

	@Override
	public void setTowerCost() {
		super.towerCost = 60;
	}

	@Override
	public int getTowerlevel() {
		return super.towerlevel;
	}

	@Override
	public void setTowerlevel() {
		super.towerlevel = 1;
	}

	@Override
	public Icon getTowerImage() {
		return super.towerImage;
	}

	@Override
	public void setTowerImage() {
		super.towerImage = new ImageIcon(ApplicationStatics.IMAGE_PATH_MAP_TOWER3);
	}

	@Override
	public Dimension getTowerCordinate() {
		return null;
	}

	@Override
	public void setTowerCordinate() {
		// TODO Auto-generated method stub
	}

	@Override
	public int getTowerUpgradeCost() {
		return super.towerUpgradeCost;
	}

	@Override
	public void setTowerUpgradeCost() {
		super.towerUpgradeCost = 30;
	}

	@Override
	public int getTowerlevelUpgrade() {
		return super.towerlevelUpgrade;
	}

	@Override
	public void setTowerlevelUpgrade() {
		super.towerlevelUpgrade = 1;
	}

	@Override
	public int getTowerPowerUpgrade() {
		return super.towerPowerUpgrade;
	}

	@Override
	public void setTowerPowerUpgrade() {
		super.towerPowerUpgrade = 1;
	}

	@Override
	public int getTowerFireRateUpgrade() {
		return super.towerFireRateUpgrade;
	}

	@Override
	public void setTowerFireRateUpgrade() {
		super.towerFireRateUpgrade = 0;
	}

	@Override
	public int getX() {
		return super.x;
	}

	@Override
	public int getY() {
		return super.y;
	}

	@Override
	public void setXY(int new_x, int new_y) {
		super.x = new_x;
		super.y = new_y;
		calculateRangeCircleCoordinates();
	}

	@Override
	public void upgradeTower() {
		super.towerFireRate = getTowerFireRate() + getTowerFireRateUpgrade();
		super.towerCost = getTowerCost() + getTowerUpgradeCost();
		super.towerRange = getTowerRange() + getTowerFireRangeUpgrade();
		super.towerlevel = getTowerlevel() + getTowerlevelUpgrade();
		super.towerPower = getTowerPower() + getTowerPowerUpgrade();

		super.calculateRangeCircleCoordinates();
	}

	@Override
	public int getRefund() {
		return getTowerCost() / 2;
	}

	/**
	 * Plugs in a specific strategy to be used
	 */
	@Override
	public void setStrategy(Strategy new_strategy) {
		super.strategy = new_strategy;
	}

	/**
	 * Method that executes a different strategy depending on what strategy was
	 * plugged in upon instantiation.
	 */
	@Override
	public void executeStrategy(Tower new_tower, CritterType new_critter) {
		this.strategy.execute(new_tower, new_critter);
	}

	// END
}
