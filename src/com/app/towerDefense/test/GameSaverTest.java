package com.app.towerDefense.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.*;

import com.app.towerDefense.models.NearestToEndPointStrategy;
import com.app.towerDefense.models.NearestToTowerStrategy;
import com.app.towerDefense.models.PlayerModel;
import com.app.towerDefense.models.StrongestStrategy;
import com.app.towerDefense.models.Tower;
import com.app.towerDefense.models.TowerFactory;
import com.app.towerDefense.models.WeakestStrategy;

/**
 * This class test the GameSaver.class
 * 
 * @author usbaitass
 *
 */
public class GameSaverTest {

	FileWriter out;
	BufferedReader in;
	String filePath;
	PlayerModel player;

	/**
	 * Before each test case new player model object is created
	 */
	@Before
	public void Before() {
		player = new PlayerModel("Ulan", 200, 10, 2);
		player.buyTower(1);
		player.buyTower(2);
	}

	/**
	 * After each test case the player is deleted
	 */
	@After
	public void After() {
		player = null;
	}

	/**
	 * Test case to verify if the file path is exists
	 */
	@Test
	public void writeFileTest() {
		filePath = "testfiles/game.tdg";
		File file = new File(filePath);
		assertNotNull(file.exists());
	}

	/**
	 * Test case to check if the data is written correctly and properly
	 */
	@Test
	public void savePlayerDataTest() {

		assertNotNull(player);
		assertNotNull(player.towerModelArray.get(0));
		assertNotNull(player.towerModelArray.get(1));

		// first we write data to the file
		filePath = "testfiles/game.tdg";

		if (!filePath.endsWith(".tdg"))
			filePath += ".tdg";
		try {
			out = new FileWriter(filePath);
			String playerName = player.getPlayerName() + "," + filePath;
			int hpPlayer = player.getHpPlayer();
			int sunCurrency = player.getSunCurrency();
			int gameWave = player.getGameWave();
			int lastTowerID = player.lastTowerID;
			out.write(playerName + ";" + hpPlayer + ";" + sunCurrency + ";" + gameWave + ";" + lastTowerID);
			for (int i = 0; i < player.towerModelArray.size(); i++) {
				out.write(";" + player.towerModelArray.get(i).towerID);
				out.write(";" + player.towerModelArray.get(i).getTowerName());
				out.write(";" + player.towerModelArray.get(i).getTowerlevel());
				out.write(";" + player.towerModelArray.get(i).getX());
				out.write(";" + player.towerModelArray.get(i).getY());
				out.write(";" + player.towerModelArray.get(i).getStrategy().getStrategyName());
			}

			out.close();
			// then we read it and check if the written data is same as the data
			// in memory
			in = new BufferedReader(new FileReader(filePath));

			String temp = in.readLine();

			String[] dataArray = temp.split(";");

			String[] dataFileInfoArray = dataArray[0].split(",");
			assertEquals(player.getPlayerName(), dataFileInfoArray[0]);
			assertEquals(filePath, dataFileInfoArray[1]);
			assertEquals(player.getHpPlayer(), Integer.parseInt(dataArray[1]));
			assertEquals(player.getSunCurrency(), Integer.parseInt(dataArray[2]));
			assertEquals(player.getGameWave(), Integer.parseInt(dataArray[3]));
			assertEquals(player.lastTowerID, Integer.parseInt(dataArray[4]));

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
				assertEquals(player.towerModelArray.get(i % 5).getTowerName(), tempTower.getTowerName());
				assertEquals(player.towerModelArray.get(i % 5).getTowerlevel(), tempTower.getTowerlevel());
			}

			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
