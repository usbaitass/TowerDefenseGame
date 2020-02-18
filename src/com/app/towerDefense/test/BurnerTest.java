package com.app.towerDefense.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.towerDefense.models.Burner;
import com.app.towerDefense.models.Tower;
import com.app.towerDefense.models.TowerFactory;

/**
 * The class <code>BurnerTest</code> contains tests for the class
 * <code>{@link Burner}</code>.
 * 
 * 
 * @author George Ekow-Daniels
 * @version 1.0
 */
public class BurnerTest {

	Tower burner;

	/**
	 * Test case Initialization for BurnerTest
	 */
	@Before
	public void burnerTestCase() {
		System.out.println("@BeforeClass - oneTimeSetUp-Creating object of class Burner");
		burner = TowerFactory.getTower("Burner");
	}

	/**
	 * Test case for setter methods in class BurnerTest
	 */
	@Test
	public void test() {

		int towerLevel = 1;
		int towerPower = 1;
		int towerRange = 100;
		int towerFireRate = 1;
		int towerCost = 60;
		int towerFireRateUpgrade = 0;
		int towerLevelUpgrade = 1;
		int towerUpgradeCost = 30;
		int towerFireRangeUpgrade = 0;
		int towerPowerUpgrade = 1;
		String towerName = "Burner";

		assertNotNull(burner);

		assertEquals(burner.getTowerName(), towerName);

		assertEquals(burner.getTowerlevel(), towerLevel);

		assertEquals(burner.getTowerPower(), towerPower);

		assertEquals(burner.getTowerRange(), towerRange);

		assertEquals(burner.getTowerFireRate(), towerFireRate);

		assertEquals(burner.getTowerCost(), towerCost);

		assertEquals(burner.getTowerFireRangeUpgrade(), towerFireRangeUpgrade);

		assertEquals(burner.getTowerFireRateUpgrade(), towerFireRateUpgrade);

		assertEquals(burner.getTowerlevelUpgrade(), towerLevelUpgrade);

		assertEquals(burner.getTowerPowerUpgrade(), towerPowerUpgrade);

		assertEquals(burner.getTowerUpgradeCost(), towerUpgradeCost);

	}

	/**
	 * Perform post-test clean-up.
	 * 
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 * 
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("@AfterClass - oneTimeTearDown");
		burner = null;
		assertNull(burner);

	}

}