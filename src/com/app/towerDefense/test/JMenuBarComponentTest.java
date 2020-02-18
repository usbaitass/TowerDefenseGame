package com.app.towerDefense.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.towerDefense.guiComponents.JMenuBarComponent;
import com.app.towerDefense.models.MapModel;
import com.app.towerDefense.staticContent.ApplicationStatics;

/**
 * The class <code>JMenuBarComponentTest</code> contains tests for the class
 * <code>{@link JMenuBarComponent}</code>.
 * 
 * 
 * @author Sajjad Ashraf
 * @version 1.0
 */
public class JMenuBarComponentTest {

	JMenuBarComponent jMenuBarComponent;

	/**
	 * Test case Initialization for GameTest
	 */
	@Before
	public void beforeTestCase() {
		System.out.println("@BeforeClass - oneTimeSetUp-Creating object of class JMenuBarComponent");
		jMenuBarComponent = new JMenuBarComponent();
	}

	/**
	 * Run Unit Test GetGameJMenuBar it return game menu
	 * 
	 */
	@Test
	public void testGetGameJMenuBar() {
		int width = ApplicationStatics.WINDOW_WIDTH;
		int height = ApplicationStatics.WINDOW_HEIGHT;

		JFrame frame = new JFrame();
		// frame.setTitle(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.getContentPane().removeAll();

		JMenuBar jMenubar = jMenuBarComponent.getGameJMenuBar(frame);
		assertNotNull(jMenubar);
	}

	/**
	 * Run Unit Test GetMapEditorJMenuBar it return Map Editor menu
	 * 
	 */
	@Test
	public void testGetMapEditorJMenuBar() {
		int width = ApplicationStatics.WINDOW_WIDTH;
		int height = ApplicationStatics.WINDOW_HEIGHT;

		JFrame frame = new JFrame();
		// frame.setTitle(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.getContentPane().removeAll();

		JMenuBar jMenubar = jMenuBarComponent.getMapEditorJMenuBar(new MapModel(), frame);
		assertNotNull(jMenubar);
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
		jMenuBarComponent = null;
		assertNull(jMenuBarComponent);

	}

}
