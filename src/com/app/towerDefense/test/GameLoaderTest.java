package com.app.towerDefense.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.*;
import com.app.towerDefense.gameLogic.GameLoader;
import com.app.towerDefense.models.NearestToEndPointStrategy;
import com.app.towerDefense.models.NearestToTowerStrategy;
import com.app.towerDefense.models.PlayerModel;
import com.app.towerDefense.models.StrongestStrategy;
import com.app.towerDefense.models.Tower;
import com.app.towerDefense.models.TowerFactory;
import com.app.towerDefense.models.WeakestStrategy;

/**
 * The class <code>GameLoaderTest</code> contains tests for the class
 * <code>{@link GameLoader}</code>.
 * 
 * 
 * @author Amritansh Mishra
 * @version 1.0
 */
public class GameLoaderTest {

	GameLoader gameLoader;
	BufferedReader in;

	String filename = "testfiles/game.tdg";
	PlayerModel player;
	String[] dataArray, dataFileInfoArray;
	String temp;
	ArrayList<Tower> tempTowerModelArray;

	@Before
	public void BeforeGameLoaderTest() {
		player = new PlayerModel();

	}

	/**
	 * Test case to check if the file exists or not.
	 */
	@Test
	public void filePathTest() {
		File file = new File(filename);
		assertTrue(file.exists());
	}

	/**
	 * Test case to check file name
	 */
	@Test
	public void readStringTest() {
		try {
			in = new BufferedReader(new FileReader(filename));
			try {
				temp = in.readLine();
				assertTrue(temp != null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test to check player information
	 */
	@Test
	public void readPlayerTest() {
		tempTowerModelArray = new ArrayList<Tower>();

		try {
			in = new BufferedReader(new FileReader(filename));
			try {
				temp = in.readLine();
				assertTrue(temp != null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		dataArray = temp.split(";");
		dataFileInfoArray = dataArray[0].split(",");
		String playerName = dataFileInfoArray[0];
		String mapFilePath = dataFileInfoArray[1];
		int hpPlayer = Integer.parseInt(dataArray[1]);
		int sunCurrency = Integer.parseInt(dataArray[2]);
		int gameWave = Integer.parseInt(dataArray[3]);
		int lastTowerID = Integer.parseInt(dataArray[4]);
		Tower tempTower;
		for (int i = 5; i < dataArray.length; i += 6) {
			tempTower = TowerFactory.getTower(dataArray[i + 1]);
			tempTower.towerID = Integer.parseInt(dataArray[i]);
			for (int j = 1; j < Integer.parseInt(dataArray[i + 2]); j++) {
				tempTower.upgradeTower();
			}
			tempTower.setXY(Integer.parseInt(dataArray[i + 3]), Integer.parseInt(dataArray[i + 4]));
			String strStrategy = dataArray[i + 5];
			if (strStrategy.equalsIgnoreCase("NearToEND")) {
				tempTower.setStrategy(new NearestToEndPointStrategy());
			} else if (strStrategy.equalsIgnoreCase("NearTower")) {
				tempTower.setStrategy(new NearestToTowerStrategy());
			} else if (strStrategy.equalsIgnoreCase("Strongest")) {
				tempTower.setStrategy(new StrongestStrategy());
			} else if (strStrategy.equalsIgnoreCase("Weakest")) {
				tempTower.setStrategy(new WeakestStrategy());
			}
			tempTowerModelArray.add(tempTower);
		}

		player = new PlayerModel(playerName, sunCurrency, hpPlayer, gameWave);
		player.towerModelArray = tempTowerModelArray;
		player.lastTowerID = lastTowerID;
		assertNotNull(mapFilePath);
		assertTrue(playerName != null);
		assertNotNull(sunCurrency);
		assertNotNull(hpPlayer);
		assertNotNull(gameWave);
		assertEquals(player.getPlayerName(), playerName);
		assertEquals(player.getSunCurrency(), sunCurrency);
		assertEquals(player.getHpPlayer(), hpPlayer);
		assertEquals(player.getGameWave(), gameWave);
		assertTrue(!tempTowerModelArray.isEmpty());
		assertEquals(player.towerModelArray, tempTowerModelArray);
		for (int i = 0; i < player.towerModelArray.size(); i++) {
			assertEquals(player.towerModelArray.get(i).towerID, tempTowerModelArray.get(i).towerID);
			assertEquals(player.towerModelArray.get(i).getTowerlevel(), tempTowerModelArray.get(i).getTowerlevel());
		}
	}

}
