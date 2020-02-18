package com.app.towerDefense.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.towerDefense.models.MapModel;
import com.app.towerDefense.utilities.FileStorage;
import com.app.towerDefense.utilities.MiscellaneousHelper;

/**
 * The class <code>FileStorageTest</code> contains tests for the class
 * <code>{@link FileStorage}</code>.
 * 
 * 
 * @author Sajjad Ashraf
 * @version 1.0
 */
public class FileStorageTest {

	FileStorage fileStorage;
	MapModel mapModel;
	File fileSave;
	File fileOpen;

	/**
	 * Test case Initialization for FileStorageTest
	 */

	@Before
	public void runBefore() {
		System.out.println("@BeforeClass - oneTimeSetUp-Creating object of class MapModel and others.");
		fileStorage = new FileStorage();
		mapModel = new MapModel();
		fileSave = new File("testfiles/newGeneratedByTestCase.tdm");
		fileOpen = new File("testfiles/abc.tdm");

	}

	/**
	 * Test GetJsonFromObject Return String Json from Object
	 * 
	 */
	@Test
	public void testGetJsonFromObject() {
		mapModel = fileStorage.openMapFile(fileOpen);
		String json = fileStorage.getJsonFromObject(mapModel);
		// Check for null
		assertNotNull(json);
		// Check it is string
		assertFalse((new MiscellaneousHelper()).isDouble(json));
	}

	/**
	 * Test GetObjectFromJson Return Object from Json String Depending upon
	 * Class Type
	 * 
	 * @throws IOException
	 *             map file is wrong
	 */

	@Test
	public void testGetObjectFromJson() throws IOException {

		String fileContent = new String(Files.readAllBytes(Paths.get(fileOpen.getPath())));
		fileContent = (new MiscellaneousHelper()).DecodeBase64(fileContent);
		mapModel = (MapModel) fileStorage.getObjectFromJson(fileContent, MapModel.class);
		assertNotNull(mapModel);
	}

	/**
	 * Test OpenMapFile Method which load a model from Map files
	 */
	public void testOpenMapFile() {
		mapModel = (new FileStorage()).openMapFile(fileOpen);
		assertNull(mapModel);
	}

	/**
	 * Test SaveMapFile Method it save the mapModel to file
	 */
	@Test
	public void testSaveMapFile() {
		mapModel = (new FileStorage()).openMapFile(fileOpen);
		String result = (new FileStorage()).saveMapFile(fileSave, mapModel);
		assertEquals(result, "SUCCESS");
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
		fileStorage = null;
		mapModel = null;
		fileSave = null;
		fileOpen = null;
		assertNull(fileStorage);
		assertNull(mapModel);
		assertNull(fileSave);
		assertNull(fileOpen);

	}

}
