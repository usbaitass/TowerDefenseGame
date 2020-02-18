package com.app.towerDefense.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.towerDefense.models.Splasher;
import com.app.towerDefense.models.Tower;
import com.app.towerDefense.models.TowerFactory;

/**
 * The class <code>SplasherTest</code> contains tests for the class
 * <code>{@link Splasher}</code>.
 * 
 * 
 * @author George Ekow-Daniels
 * @version 1.0
 */
public class SplasherTest {

	Tower splasher;

	/**
	 * Test case Initialization for SplasherTest
	 */
	@Before
	public void splasherTestCase() {
		System.out.println("@BeforeClass - oneTimeSetUp-Creating object of class Splasher");
		splasher = TowerFactory.getTower("Splasher");
	}

	/**
	 * Test case for setter methods in class SplasherTest
	 */
	@Test
	public void test() {

		int towerLevel = 1;
		int towerPower = 1;
		int towerRange = 100;
		int towerFireRate = 1;
		int towerCost = 80;
		int towerFireRateUpgrade = 0;
		int towerLevelUpgrade = 1;
		int towerUpgradeCost = 40;
		int towerFireRangeUpgrade = 25;
		int towerPowerUpgrade = 0;
		String towerName = "Splasher";

		assertNotNull(splasher);

		assertEquals(splasher.getTowerName(), towerName);

		assertEquals(splasher.getTowerlevel(), towerLevel);

		assertEquals(splasher.getTowerPower(), towerPower);

		assertEquals(splasher.getTowerRange(), towerRange);

		assertEquals(splasher.getTowerFireRate(), towerFireRate);

		assertEquals(splasher.getTowerCost(), towerCost);

		assertEquals(splasher.getTowerFireRangeUpgrade(), towerFireRangeUpgrade);

		assertEquals(splasher.getTowerFireRateUpgrade(), towerFireRateUpgrade);

		assertEquals(splasher.getTowerlevelUpgrade(), towerLevelUpgrade);

		assertEquals(splasher.getTowerPowerUpgrade(), towerPowerUpgrade);

		assertEquals(splasher.getTowerUpgradeCost(), towerUpgradeCost);

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
		splasher = null;
		assertNull(splasher);

	}

}
