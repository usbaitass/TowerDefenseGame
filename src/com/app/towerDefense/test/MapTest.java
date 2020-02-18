package com.app.towerDefense.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.towerDefense.gameLogic.Map;
import com.app.towerDefense.models.MapModel;
import com.app.towerDefense.staticContent.ApplicationStatics;
import com.app.towerDefense.utilities.FileStorage;

/**
 * The class <code>MapTest</code> contains tests for the class
 * <code>{@link Map}</code>.
 * 
 * 
 * @author Amritansh Mishra
 * @version 1.0
 */
public class MapTest {

	Map map;
	MapModel mapModel;
	File file;

	/**
	 * Test case Initialization for MapTest
	 */
	@Before
	public void towerModel1TestCase() {
		if (System.getProperty("os.name").contains("Windows")) {
			file = new File("testfiles\\abc.tdm");// for Windows OS
		} else {
			file = new File("testfiles/abc.tdm"); // for Mac OSX
		}
		System.out.println("@BeforeClass - oneTimeSetUp-Creating object of class Map");
		map = new Map();
		mapModel = new MapModel();
	}

	/**
	 * Test case for map validation
	 */
	@Test
	public void testMapValidation() {

		mapModel = (new FileStorage()).openMapFile(file);
		String status = (new Map()).mapValidations(mapModel);
		assertNull(status);

	}

	/**
	 * Test case for map path validation
	 */
	@Test
	public void testMapPathValidation() {
		String status = (new Map()).mapValidations(mapModel);
		mapModel = (new FileStorage()).openMapFile(file);
		status = (new Map()).mapPathValidation(mapModel);
		assertTrue(status.contains("Success"));
	}

	/**
	 * Test case Check is there is any Independent or non Connected Cell.
	 */
	@Test
	public void testCheckIndependentSelectedCells() {
		map = new Map();
		mapModel = new MapModel();
		mapModel = (new FileStorage()).openMapFile(file);
		ApplicationStatics.MAP_ROUT_PATH = mapModel.getMapRoutPath();
		assertTrue(map.checkIndependentSelectedCells(mapModel));
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
		mapModel = null;
		map = null;
		file = null;
		assertNull(mapModel);
		assertNull(map);
		assertNull(file);

	}

}
