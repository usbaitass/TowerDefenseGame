package com.app.towerDefense.test;

import static org.junit.Assert.*;

import org.junit.*;

import org.junit.Test;

import com.app.towerDefense.guiComponents.MapPanel;
import com.app.towerDefense.models.BasicCritter;
import com.app.towerDefense.models.MapModel;
import com.app.towerDefense.models.NearestToEndPointStrategy;
import com.app.towerDefense.models.NearestToTowerStrategy;
import com.app.towerDefense.models.Shooter;
import com.app.towerDefense.models.StrongestStrategy;
import com.app.towerDefense.models.WeakestStrategy;

/**
 * This class test 4 strategies that were implemented in the project
 * 
 * @author usbaitass
 *
 */
public class StrategyTest {

	Shooter tower;
	BasicCritter critter1;
	BasicCritter critter2;

	/**
	 * This method runs the before all test cases
	 */
	@BeforeClass
	public static void beforeClass() {
		System.out.println("Entered StrategyTest Class");
	}

	/**
	 * This method runs after all test cases were ran
	 */
	@AfterClass
	public static void afterClass() {
		System.out.println("Left StrategyTest Class");
	}

	/**
	 * This method initiate the variable before each test case
	 */
	@Before
	public void before() {
		System.out.print("inside");

		tower = new Shooter();
		critter1 = new BasicCritter(1);
		critter2 = new BasicCritter(1);
		critter1.setID(1);
		critter2.setID(2);
		tower.towerID = 1;

		tower.setXY(10, 10);
	}

	/**
	 * This method deletes and sets variables to null after each test case
	 */
	@After
	public void after() {
		System.out.println("outside");
		tower = null;
		critter1 = null;
		critter2 = null;
	}

	/**
	 * Test case that checks if the Strongest Strategy works as intented
	 */
	@Test
	public void testStrongestStrategy() {
		System.out.println(" testStrongestStrategy()");

		MapPanel mapPanel = new MapPanel(new MapModel());

		assertNotNull(tower);
		assertNotNull(critter1);
		assertNotNull(critter2);

		assertEquals(10, critter1.getCurrentHealth());
		assertEquals(10, critter2.getCurrentHealth());

		tower.setStrategy(new StrongestStrategy());
		assertEquals("Strongest", tower.getStrategy().getStrategyName());

		Thread thread = new Thread();
		try {

			// initially tower has no targets
			// so if critter1 comes first, it will shoot it
			// and sleep for 500ms
			tower.executeStrategy(tower, critter1);

			// we assert if tower has shot the critter1
			assertEquals(9, critter1.getCurrentHealth());

			// we assume that after tower shot,
			// 2 critters try to notify the tower to shoot them
			// however tower will not shoot because it still sleeps
			thread.sleep(100);
			tower.executeStrategy(tower, critter1);
			tower.executeStrategy(tower, critter2);

			// later this critters try to notify again
			// tower selects the target and shoots based on their Health status
			// which critter has GREATER hp, it is gonna be the target
			thread.sleep(1000);
			tower.executeStrategy(tower, critter1);
			tower.executeStrategy(tower, critter2);

			// we check if the target was correctly chosen
			// in our case, it should be the critter2
			assertEquals(9, critter1.getCurrentHealth());
			assertEquals(9, critter2.getCurrentHealth());

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This test case checks if Weakest strategy works as intended
	 */
	@Test
	public void testWeakestStrategy() {
		System.out.println(" testWeakestStrategy()");

		MapPanel mapPanel = new MapPanel(new MapModel());

		assertNotNull(tower);
		assertNotNull(critter1);
		assertNotNull(critter2);

		assertEquals(10, critter1.getCurrentHealth());
		assertEquals(10, critter2.getCurrentHealth());

		tower.setStrategy(new WeakestStrategy());
		assertEquals("Weakest", tower.getStrategy().getStrategyName());

		Thread thread = new Thread();
		try {

			// initially tower has no targets
			// so if critter1 comes first, it will shoot it
			// and sleep for 500ms
			tower.executeStrategy(tower, critter1);

			// we assert if tower has shot the critter1
			assertEquals(9, critter1.getCurrentHealth());

			// we assume that after tower shot,
			// 2 critters try to notify the tower to shoot them
			// however tower will not shoot because it still sleeps
			thread.sleep(100);
			tower.executeStrategy(tower, critter1);
			tower.executeStrategy(tower, critter2);

			// later this critters try to notify again
			// tower selects the target and shoots based on their Health status
			// which critter has LESSER hp, it is gonna be the target
			thread.sleep(1000);
			tower.executeStrategy(tower, critter1);
			tower.executeStrategy(tower, critter2);

			// we check if the target was correctly chosen
			// in our case, it should be the critter1
			assertEquals(8, critter1.getCurrentHealth());
			assertEquals(10, critter2.getCurrentHealth());

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	/**
	 * This test case checks if Nearest to tower strategy works as intended
	 */
	@Test
	public void testNearestToTowerStrategy() {
		System.out.println(" testNearestToTowerStrategy()");

		MapPanel mapPanel = new MapPanel(new MapModel());

		assertNotNull(tower);
		assertNotNull(critter1);
		assertNotNull(critter2);

		assertEquals(10, critter1.getCurrentHealth());
		assertEquals(10, critter2.getCurrentHealth());

		tower.setStrategy(new NearestToTowerStrategy());
		assertEquals("NearTower", tower.getStrategy().getStrategyName());

		tower.setXY(0, 0);
		critter1.xCr = 20;
		critter1.yCr = 0;
		critter2.xCr = 30;
		critter2.yCr = 0;

		// System.out.println("critter1 x:"+critter1.getXCr()+"
		// y:"+critter1.getYCr());
		// System.out.println("critter2 x:"+critter2.getXCr()+"
		// y:"+critter2.getYCr());

		Thread thread = new Thread();
		try {

			// initially tower has no targets
			// so if critter1 comes first, it will shoot it
			// and sleep for 500ms
			tower.executeStrategy(tower, critter1);

			// we assert if tower has shot the critter1
			assertEquals(9, critter1.getCurrentHealth());

			// we assume that after tower shot,
			// 2 critters try to notify the tower to shoot them
			// however tower will not shoot because it still sleeps
			thread.sleep(100);
			tower.executeStrategy(tower, critter1);
			tower.executeStrategy(tower, critter2);

			// later this critters try to notify again
			// tower selects the target and shoots based on their distance
			// which critter has smaller distance between it and tower,
			// it is gonna be the target
			thread.sleep(1000);
			tower.executeStrategy(tower, critter1);
			tower.executeStrategy(tower, critter2);

			// we check if the target was correctly chosen
			// in our case, it should be the critter1
			assertEquals(8, critter1.getCurrentHealth());
			assertEquals(10, critter2.getCurrentHealth());

			// now if we change the location of critter1
			// and make it further
			critter1.xCr = 40;
			critter1.yCr = 0;
			critter2.xCr = 30;
			critter2.yCr = 0;

			// System.out.println("critter1 x:"+critter1.getXCr()+"
			// y:"+critter1.getYCr());
			// System.out.println("critter2 x:"+critter2.getXCr()+"
			// y:"+critter2.getYCr());

			// critters call the shoot the function but the tower is still
			// sleeps
			thread.sleep(100);
			tower.executeStrategy(tower, critter1);
			tower.executeStrategy(tower, critter2);

			// one second later we request shoot again
			thread.sleep(1000);
			tower.executeStrategy(tower, critter1);
			tower.executeStrategy(tower, critter2);

			// in this case, the distance between tower and critter2
			// became smaller than distance between tower and critter1
			// therefore, tower should shoot the critter2 now
			assertEquals(8, critter1.getCurrentHealth());
			assertEquals(9, critter2.getCurrentHealth());

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	/**
	 * This test case checks if Nearest to End point strategy works as intended
	 */
	@Test
	public void testNearestToEndPointStrategy() {
		System.out.println(" testNearestToEndPointStrategy()");

		MapPanel mapPanel = new MapPanel(new MapModel());
		BasicCritter critter3 = new BasicCritter(1);
		critter3.setID(3);

		assertNotNull(tower);
		assertNotNull(critter1);
		assertNotNull(critter2);
		assertNotNull(critter3);

		assertEquals(10, critter1.getCurrentHealth());
		assertEquals(10, critter2.getCurrentHealth());
		assertEquals(10, critter3.getCurrentHealth());

		tower.setStrategy(new NearestToEndPointStrategy());
		assertEquals("NearToEND", tower.getStrategy().getStrategyName());

		// Here we place the critters on the map path blocks
		tower.setXY(0, 0);
		critter1.setXY(20, 0);
		critter2.setXY(30, 0);
		critter3.setXY(100, 0);

		// place the critter inside path blocks which
		// ordered ascending, which represented as
		// from Entry to Exit block
		critter1.currentBlocki = 1;
		critter2.currentBlocki = 1;
		critter3.currentBlocki = 2;
		// critter3.currentBlocki = 1;

		// we indicate the direction in which critters are moving
		critter1.directionX = 1;
		critter1.directionY = 0;
		critter2.directionX = 1;
		critter2.directionY = 0;
		critter3.directionX = 1;
		critter3.directionY = 0;

		try {
			Thread thread = new Thread();
			// shoot at first critter within range
			tower.executeStrategy(tower, critter1);

			// tower sleeps for 500, during that time
			// critters still ask to be shot
			thread.sleep(100);
			tower.executeStrategy(tower, critter1);
			tower.executeStrategy(tower, critter2);
			tower.executeStrategy(tower, critter3);

			// selects the target and shoots after 1 second
			thread.sleep(1000);
			tower.executeStrategy(tower, critter1);
			tower.executeStrategy(tower, critter2);
			tower.executeStrategy(tower, critter3);

			// check if expected data is the same
			assertEquals(9, critter1.getCurrentHealth());
			assertEquals(10, critter2.getCurrentHealth());
			assertEquals(9, critter3.getCurrentHealth());

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

}
