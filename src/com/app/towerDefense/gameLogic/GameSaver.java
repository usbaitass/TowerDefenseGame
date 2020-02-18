package com.app.towerDefense.gameLogic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.app.towerDefense.staticContent.ApplicationStatics;

/**
 * This class saves the game data for a player
 * 
 * @author usbaitass
 *
 */
public class GameSaver {

	FileWriter out;
	File file;
	String filePath;

	/**
	 * Constructor
	 * @param new_file new file
	 * @throws IOException file may not be at location
	 */
	public GameSaver(File new_file) throws IOException {
		file = new_file;
		filePath = file.getPath();
		savePlayerData();

	}

	/**
	 * This method opens the file path directory and saves the game there
	 * 
	 */
	public void savePlayerData() {

		try {

			if (!filePath.endsWith(".tdg"))
				filePath += ".tdg";

			out = new FileWriter(filePath);

			String playerName = ApplicationStatics.PLAYERMODEL.getPlayerName() + ","
					+ ApplicationStatics.MAP_CURRENT_OPENED_FILE_PATH;
			int hpPlayer = ApplicationStatics.PLAYERMODEL.getHpPlayer();
			int sunCurrency = ApplicationStatics.PLAYERMODEL.getSunCurrency();
			int gameWave = ApplicationStatics.PLAYERMODEL.getGameWave();
			int lastTowerID = ApplicationStatics.PLAYERMODEL.lastTowerID;

			out.write(playerName + ";" + hpPlayer + ";" + sunCurrency + ";" + gameWave + ";" + lastTowerID);

			for (int i = 0; i < ApplicationStatics.PLAYERMODEL.towerModelArray.size(); i++) {
				out.write(";" + ApplicationStatics.PLAYERMODEL.towerModelArray.get(i).towerID);
				out.write(";" + ApplicationStatics.PLAYERMODEL.towerModelArray.get(i).getTowerName());
				out.write(";" + ApplicationStatics.PLAYERMODEL.towerModelArray.get(i).getTowerlevel());
				out.write(";" + ApplicationStatics.PLAYERMODEL.towerModelArray.get(i).getX());
				out.write(";" + ApplicationStatics.PLAYERMODEL.towerModelArray.get(i).getY());
				out.write(";" + ApplicationStatics.PLAYERMODEL.towerModelArray.get(i).getStrategy().getStrategyName());
			}

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
