package com.app.towerDefense.test;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.app.towerDefense.gameLogic.LogReader;
import com.app.towerDefense.models.PlayerModel;
import com.app.towerDefense.models.Tower;
import com.app.towerDefense.staticContent.ApplicationStatics;
import com.app.towerDefense.staticContent.AppilicationEnums.E_LogViewerState;

/**
 * The class <code>LogReaderTest</code> contains tests for the class
 * <code>{@link LogReader}</code>.
 * 
 * 
 * @author Sajjad Ashraf
 * @version 1.0
 */
public class LogReaderTest {

	File file;
	LogReader logReader;
	E_LogViewerState logReadingState;
	Tower tower;

	public LogReaderTest() {
	}

	/**
	 * Test case Initialization for LogReaderTest
	 */
	@Before
	public void beforeLogReaderTest() {
		if (System.getProperty("os.name").contains("Windows")) {
			file = new File("testfiles\\gameLog.log");// for Windows OS
			ApplicationStatics.MAP_CURRENT_OPENED_FILE_PATH = "testfiles\\Map_10_8_MapPlayerStatisticsTest.tdm";
		} else {
			file = new File("testfiles/gameLog.log"); // for Mac OSX
			ApplicationStatics.MAP_CURRENT_OPENED_FILE_PATH = "testfiles/Map_10_8_MapPlayerStatisticsTest.tdm";
		}

		logReadingState = E_LogViewerState.GlobalLog;
		logReader = new LogReader(file.getPath(), logReadingState, tower);
		ApplicationStatics.setLog_Current_Session_Tag("20160407102707423");
		ApplicationStatics.PLAYERMODEL = new PlayerModel();

		// tower= new
		System.out.println("@BeforeClass - oneTimeSetUp-Creation");
	}

	/**
	 * Test case for Log Reader Constructors
	 */
	@Test
	public void testLogReader() {
		logReader = new LogReader(file.getPath(), logReadingState, tower);
		assertNotNull(logReader);
		assertEquals(file, logReader.getFile());
		assertEquals(logReadingState, logReader.getLogReadingState());
		assertEquals(tower, logReader.getTower());
	}

	/**
	 * Test case for Log Reader Parse for Current Session
	 */
	@Test
	public void testParseForCurrentSession() {
		// Set Log Session
		String result = logReader.parseForCurrentSession();
		assertEquals(7867, result.length());

	}

	/**
	 * Test case for Log Reader For Towers Collection
	 */
	@Test
	public void testParseLogForTowersCollection() {
		String result = logReader.parseLogForTowersCollection();
		assertEquals(4727, result.length());

	}

	/**
	 * Test case for Log Reader For Specific Tower
	 */
	@Test
	public void testParseLogForTower() {
		ApplicationStatics.PLAYERMODEL = new PlayerModel();
		tower = ApplicationStatics.PLAYERMODEL.buyTower(0);
		logReader.setTower(tower);
		String result = logReader.parseLogForTower();
		assertEquals(1693, result.length());
	}

	/**
	 * Test case for Log Reader For Map Player Statistics
	 */
	@Test
	public void testParseLogForMapPlayerStatistics() {
		String result = logReader.parseLogForMapPlayerStatistics();
		assertEquals(1692, result.length());
	}

	/**
	 * Test case for Log Reader For Read Method
	 */
	@Test
	public void testRead() {
		// Read Global Log
		logReader.setLogReadingState(E_LogViewerState.GlobalLog);
		String result = logReader.read();
		assertEquals(8204, result.length());

		// Read Current Seesion
		logReader.setLogReadingState(E_LogViewerState.CurrentSessionLog);
		result = logReader.read();
		assertEquals(7867, result.length());

		// Read Tower Collection
		logReader.setLogReadingState(E_LogViewerState.TowerCollectionLog);
		result = logReader.read();
		assertEquals(4727, result.length());

		// Read Specific Tower
		ApplicationStatics.PLAYERMODEL = new PlayerModel();
		tower = ApplicationStatics.PLAYERMODEL.buyTower(0);
		logReader.setTower(tower);
		logReader.setLogReadingState(E_LogViewerState.TowerLog);
		result = logReader.read();
		assertEquals(1693, result.length());

		// Read Specific Tower
		logReader.setLogReadingState(E_LogViewerState.MapPlayersStatistics);
		result = logReader.read();
		assertEquals(1692, result.length());
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
	public void afterLogReaderTest() throws Exception {
		// Add additional tear down code here
		System.out.println("@AfterClass - oneTimeTearDown");
		file = null;
		logReader = null;
		logReadingState = null;
		tower = null;
		ApplicationStatics.MAP_CURRENT_OPENED_FILE_PATH = null;
		ApplicationStatics.PLAYERMODEL = null;
		assertNull(file);
		assertNull(logReader);
		assertNull(logReadingState);
		assertNull(tower);
		assertNull(ApplicationStatics.MAP_CURRENT_OPENED_FILE_PATH);
		assertNull(ApplicationStatics.PLAYERMODEL);
	}

}
