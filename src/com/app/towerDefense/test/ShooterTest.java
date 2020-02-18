package com.app.towerDefense.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.towerDefense.models.Shooter;
import com.app.towerDefense.models.Tower;
import com.app.towerDefense.models.TowerFactory;

/**
 * The class <code>ShooterTest</code> contains tests for the class
 * <code>{@link Shooter}</code>.
 * 
 * 
 * @author George Ekow-Daniels
 * @version 1.0
 */
public class ShooterTest {

	Tower shooter;

	/**
	 * Test case Initialization for ShooterTest
	 */
	@Before
	public void shooterTestCase() {
		System.out.println("@BeforeClass - oneTimeSetUp-Creating object of class Shooter");
		shooter = TowerFactory.getTower("Shooter");
	}

	/**
	 * Test case for setter methods in class ShooterTest
	 */
	@Test
	public void test() {

		int towerLevel = 1;
		int towerPower = 1;
		int towerRange = 100;
		int towerFireRate = 1;
		int towerCost = 20;
		int towerFireRateUpgrade = 0;
		int towerLevelUpgrade = 1;
		int towerUpgradeCost = 10;
		int towerFireRangeUpgrade = 25;
		int towerPowerUpgrade = 1;
		String towerName = "Shooter";

		assertNotNull(shooter);

		assertEquals(shooter.getTowerName(), towerName);

		assertEquals(shooter.getTowerlevel(), towerLevel);

		assertEquals(shooter.getTowerPower(), towerPower);

		assertEquals(shooter.getTowerRange(), towerRange);

		assertEquals(shooter.getTowerFireRate(), towerFireRate);

		assertEquals(shooter.getTowerCost(), towerCost);

		assertEquals(shooter.getTowerFireRangeUpgrade(), towerFireRangeUpgrade);

		assertEquals(shooter.getTowerFireRateUpgrade(), towerFireRateUpgrade);

		assertEquals(shooter.getTowerlevelUpgrade(), towerLevelUpgrade);

		assertEquals(shooter.getTowerPowerUpgrade(), towerPowerUpgrade);

		assertEquals(shooter.getTowerUpgradeCost(), towerUpgradeCost);

	}

	/**
	 * Perform post-test clean-up.
	 * 
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 * 
	 * 
	 */
	@After
	public void tearDown() throws Exception {

		System.out.println("@AfterClass - oneTimeTearDown");
		shooter = null;
		assertNull(shooter);

	}

}
