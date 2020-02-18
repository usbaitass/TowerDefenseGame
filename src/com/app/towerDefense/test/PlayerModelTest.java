package com.app.towerDefense.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.app.towerDefense.models.PlayerModel;
import com.app.towerDefense.models.Tower;
import com.app.towerDefense.models.TowerFactory;

/**
 * The class <code>PlayerModelTest</code> contains tests for the class
 * <code>{@link PlayerModel}</code>.
 *
 *
 * @author George Ekow-Daniels
 * @version Revision: 2.0
 */
public class PlayerModelTest {

	PlayerModel playerModel;
	Tower Shooter;
	ArrayList<Tower> towerModelArray;

	/**
	 * Test case Initialization for PlayerModelTest
	 */
	@Before
	public void towerModel1TestCase() {
		System.out.println("@BeforeClass - oneTimeSetUp-Creating object of class PlayerModel");
		playerModel = new PlayerModel();
		Shooter = TowerFactory.getTower("Shooter");

	}

	/**
	 * Run the PlayerModel default constructor test.
	 * 
	 */
	@Test
	public void testDefaultConstructor() {

		String playerName = "defaultName";
		int sunCurrency = 500;
		int hpPlayer = 10;
		int gameWave = 1;

		assertEquals(sunCurrency, playerModel.getSunCurrency());

		assertEquals(hpPlayer, playerModel.getHpPlayer());

		assertEquals(gameWave, playerModel.getGameWave());

	}

	/**
	 * Run the PlayerModel parameterized constructor test. PlayerModel(String ,
	 * int , int , int )
	 * 
	 *
	 *
	 */
	@Test
	public void testParameterizedConstructor() {

		String playerName = "testName";
		int sunCurrency = 100;
		int hpPlayer = 5;
		int gameWave = 5;

		playerModel = new PlayerModel(playerName, sunCurrency, hpPlayer, gameWave);

		assertEquals(playerName, playerModel.getPlayerName());

		assertEquals(sunCurrency, playerModel.getSunCurrency());

		assertEquals(hpPlayer, playerModel.getHpPlayer());

		assertEquals(gameWave, playerModel.getGameWave());

	}

	/**
	 * Test buyTower method.
	 */
	@Test
	public void buyTowerTest() {

		playerModel = new PlayerModel();
		Shooter = TowerFactory.getTower("Shooter");
		playerModel.buyTower(1);
		assertEquals(playerModel.towerModelArray.size(), 1);

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
		// Add additional tear down code here
		System.out.println("@AfterClass - oneTimeTearDown");
		playerModel = null;
		Shooter = null;
		assertNull(playerModel);
		assertNull(Shooter);

	}

}
