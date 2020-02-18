package com.app.towerDefense.test;

import static org.junit.Assert.*;

import org.junit.*;

import com.app.towerDefense.guiComponents.MapPanel;
import com.app.towerDefense.models.BasicCritter;
import com.app.towerDefense.models.MapModel;

/**
 * This Test class checks if the Special Effects that were implemented in the
 * project work as intended
 * 
 * @author usbaitass
 *
 */
public class SpecialEffectsTest {

	BasicCritter critter;

	/**
	 * Runs before all test cases
	 */
	@BeforeClass
	public static void beforeClass() {
		System.out.println("Entered Testing SpecialEffectsTest Class");
	}

	/**
	 * Runs after all test cases are completed
	 */
	@AfterClass
	public static void AfterClass() {
		System.out.println("Left Testing SpecialEffectsTest Class");
	}

	/**
	 * Initialize the variables before each test case
	 */
	@Before
	public void before() {
		critter = new BasicCritter(1);
		System.out.print("inside");
	}

	/**
	 * Delete variables after each test case execution
	 */
	@After
	public void after() {
		critter = null;
		System.out.println("outside");
	}

	/**
	 * Test case that checks if the freezing towers slow effect works properly
	 */
	@Test
	public void testSlowSpeed() {
		System.out.println(" testSlowSpeed()");

		assertNotNull(critter);

		assertEquals(2, critter.getSpeed());
		critter.slowSpeed();
		assertEquals(1, critter.getSpeed());

	}

	/**
	 * Test case that checks if burning towers burn effect works properly
	 */
	@Test
	public void testBurnHealth() {
		System.out.println(" testBurnHealth()");

		assertNotNull(critter);

		assertEquals(10, critter.getCurrentHealth());
		critter.burnHealth(1);
		assertEquals(10, critter.getCurrentHealth());

		Thread thread = new Thread();
		try {
			thread.sleep(1500);

			assertEquals(9, critter.getCurrentHealth());

			thread.join();

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Test case that checks if splash damage is applied properly
	 */
	@Test
	public void testSplashDamage() {
		System.out.println(" testSplashDamage()");

		BasicCritter critter2 = new BasicCritter(1);
		BasicCritter critter3 = new BasicCritter(2);

		assertNotNull(critter);
		assertNotNull(critter2);
		assertNotNull(critter3);

		critter.setID(1);
		critter2.setID(2);
		critter3.setID(3);

		critter.setXY(0, 0);
		critter2.setXY(49, 0);
		critter3.setXY(101, 0);

		MapPanel mapPanel = new MapPanel(new MapModel());
		// setting diameter of splash damage
		critter.blockW = 100;
		critter.blockH = 100;

		mapPanel.critter.add(critter);
		mapPanel.critter.add(critter2);
		mapPanel.critter.add(critter3);

		critter.splashDamage(1);

		assertEquals(10, critter.getCurrentHealth());
		assertEquals(9, critter2.getCurrentHealth());
		assertEquals(10, critter3.getCurrentHealth());

	}

	// END
}
