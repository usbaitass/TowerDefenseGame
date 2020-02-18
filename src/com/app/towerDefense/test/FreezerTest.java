package com.app.towerDefense.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.towerDefense.models.Freezer;
import com.app.towerDefense.models.Tower;
import com.app.towerDefense.models.TowerFactory;

/**
 * The class <code>FreezerTest</code> contains tests for the class
 * <code>{@link Freezer}</code>.
 * 
 * 
 * @author George Ekow-Daniels
 * @version 1.0
 */
public class FreezerTest {

	Tower freezer;

	/**
	 * Test case Initialization for FreezerTest
	 */
	@Before
	public void freezerTestCase() {
		System.out.println("@BeforeClass - oneTimeSetUp-Creating object of class Freezer");
		freezer = TowerFactory.getTower("Freezer");
	}

	/**
	 * Test case for setter methods in class FreezerTest
	 */
	@Test
	public void test() {

		int towerLevel = 1;
		int towerPower = 1;
		int towerRange = 100;
		int towerFireRate = 1;
		int towerCost = 40;
		int towerFireRateUpgrade = 0;
		int towerLevelUpgrade = 1;
		int towerUpgradeCost = 20;
		int towerFireRangeUpgrade = 25;
		int towerPowerUpgrade = 0;
		String towerName = "Freezer";

		assertNotNull(freezer);

		assertEquals(freezer.getTowerName(), towerName);

		assertEquals(freezer.getTowerlevel(), towerLevel);

		assertEquals(freezer.getTowerPower(), towerPower);

		assertEquals(freezer.getTowerRange(), towerRange);

		assertEquals(freezer.getTowerFireRate(), towerFireRate);

		assertEquals(freezer.getTowerCost(), towerCost);

		assertEquals(freezer.getTowerFireRangeUpgrade(), towerFireRangeUpgrade);

		assertEquals(freezer.getTowerFireRateUpgrade(), towerFireRateUpgrade);

		assertEquals(freezer.getTowerlevelUpgrade(), towerLevelUpgrade);

		assertEquals(freezer.getTowerPowerUpgrade(), towerPowerUpgrade);

		assertEquals(freezer.getTowerUpgradeCost(), towerUpgradeCost);

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
		freezer = null;
		assertNull(freezer);

	}

}
