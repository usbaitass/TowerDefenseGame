package com.app.towerDefense.test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.towerDefense.guisystem.Game;

/**
 * The class <code>GameTest</code> contains tests for the class
 * <code>{@link Game}</code>.
 * 
 * @author Sajjad Ashraf
 * @version 1.0
 */

public class GameTest {

	/**
	 * Test case Initialization for GameTest
	 */
	@Before
	public void beforeTestCase() {
		System.out.println("@BeforeClass - oneTimeSetUp-Creating object of class Game");
	}

	/**
	 * Test game Instance to Check Singleton Object is created or not
	 */
	@Test
	public void testGameInstance() {
		boolean response = false;

		try {
			Game.getInstance();
			response = true;
		} catch (Exception e) {
			response = false;
		}
		assertTrue(response);
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

	}

}
