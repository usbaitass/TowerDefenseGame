package com.app.towerDefense.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.towerDefense.guiComponents.JFileChooserComponent;
import com.app.towerDefense.staticContent.AppilicationEnums.E_JFileChooserMode;

/**
 * The class <code>JFileChooserComponentTest</code> contains tests for the class
 * <code>{@link JFileChooserComponent}</code>.
 * 
 * 
 * @author Sajjad Ashraf
 * @version 1.0
 */
public class JFileChooserComponentTest {

	JFileChooserComponent jFileChooserComponent;

	/**
	 * Test case Initialization for GameTest
	 */
	@Before
	public void beforeTestCase() {
		System.out.println("@BeforeClass - oneTimeSetUp-Creating object of class JFileChooserComponent");
		jFileChooserComponent = new JFileChooserComponent();
	}

	/**
	 * Run Unit Test TestGetJFileChooser IT gives you JFileChooser Save or Open
	 * mode according to provided input
	 */
	@Test
	public void TestGetJFileChooser() {
		assertNotNull(jFileChooserComponent.getJFileChooser(E_JFileChooserMode.MapOpen));
		assertNotNull(jFileChooserComponent.getJFileChooser(E_JFileChooserMode.MapSave));
	}

	/**
	 * Run Unit Test TestGetFileChooser return the fileChooser
	 */
	@Test
	public void TestGetFileChooser() {
		assertNull(jFileChooserComponent.getFileChooser());

		jFileChooserComponent.getJFileChooser(E_JFileChooserMode.MapOpen);
		assertNotNull(jFileChooserComponent.getFileChooser());

		jFileChooserComponent.getJFileChooser(E_JFileChooserMode.MapSave);
		assertNotNull(jFileChooserComponent.getFileChooser());
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
