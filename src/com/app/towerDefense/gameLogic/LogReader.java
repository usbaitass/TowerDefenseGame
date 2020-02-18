package com.app.towerDefense.gameLogic;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.app.towerDefense.models.MapModel;
import com.app.towerDefense.models.MapPlayersStatisticsModel;
import com.app.towerDefense.models.Tower;
import com.app.towerDefense.staticContent.AppilicationEnums.E_LogViewerState;
import com.app.towerDefense.staticContent.ApplicationStatics;
import com.app.towerDefense.utilities.FileStorage;
import com.app.towerDefense.utilities.MiscellaneousHelper;

/**
 * This Class contain the logic how to read a log file depending upload it state
 * 
 * @author Sajjad Ashraf
 * 
 */
public class LogReader {
	File file;
	String logFilePath;
	String logResultant;
	E_LogViewerState logReadingState;// enum
	Tower tower;

	/**
	 * Constructor for LogReader
	 * 
	 * @param new_log_file_path
	 *            Path to the log file
	 * @param new_elog_viewer_state
	 *            LogViewerState that is CurrentSessionLog,TowerCollectionLog,
	 * @param new_tower
	 *            Tower to view
	 */
	public LogReader(String new_log_file_path, E_LogViewerState new_elog_viewer_state, Tower new_tower) {
		logFilePath = new_log_file_path;
		file = new File(logFilePath);
		logReadingState = new_elog_viewer_state;
		logResultant = "";
		tower = new_tower;
	}

	/**
	 * This method reads the tower log
	 * 
	 * @return parse for current session be it sessionlog, towerlog,
	 *         towercollectionlog,MapPlayersStatistics
	 */
	public String read() {
		logResultant = new MiscellaneousHelper().readFile(file);

		if (E_LogViewerState.CurrentSessionLog == logReadingState) {
			return parseForCurrentSession();
		} else if (E_LogViewerState.TowerLog == logReadingState) {
			return parseLogForTower();
		} else if (E_LogViewerState.TowerCollectionLog == logReadingState) {
			logResultant = parseLogForTowersCollection();
		} else if (E_LogViewerState.MapPlayersStatistics == logReadingState) {
			return parseLogForMapPlayerStatistics();
		}
		System.out.print("Length : " + logResultant.length());
		return logResultant;
	}

	/**
	 * This method gets the current session log
	 * 
	 * @return the log current session
	 */
	public String parseForCurrentSession() {
		logResultant = new MiscellaneousHelper().readFile(file);
		Pattern pattern = Pattern.compile("((.|\n)*)" + ApplicationStatics.getLog_Current_Session_Tag());
		Matcher matcher = pattern.matcher(logResultant);
		while (matcher.find()) {
			logResultant = logResultant.substring(matcher.end(), logResultant.length());
		}
		return logResultant;
	}

	/**
	 * This method gets the log for all the tower collection
	 * 
	 * @return log for towerCollection
	 */
	public String parseLogForTowersCollection() {
		// get Current Session Log
		logResultant = parseForCurrentSession();
		Pattern pattern = Pattern.compile("(.+Tower.+)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(logResultant);
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			sb.append(matcher.group() + "\n");
		}
		logResultant = sb.toString();
		return logResultant;
	}

	/**
	 * This method gets the tower log
	 * 
	 * @return the log for a tower
	 */
	public String parseLogForTower() {
		logResultant = parseLogForTowersCollection();
		Pattern pattern;
		if (tower == null) {
			pattern = Pattern.compile("(.+Tower_.+_towerID_.+)", Pattern.CASE_INSENSITIVE);
		} else
			pattern = Pattern.compile("(.+Tower_" + tower.getTowerName() + "_towerID_" + tower.towerID + ".+)",
					Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(logResultant);
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			sb.append(matcher.group() + "\n");
		}
		logResultant = sb.toString();
		return logResultant;
	}

	/**
	 * This method gets the Map Player Statistics
	 * 
	 * @return the log for the Map Player statistics
	 */
	public String parseLogForMapPlayerStatistics() {

		if (ApplicationStatics.MAP_CURRENT_OPENED_FILE_PATH != "") {
			File file = new File(ApplicationStatics.MAP_CURRENT_OPENED_FILE_PATH);
			MapModel mapModel = (new FileStorage()).openMapFile(file);

			Collections.sort(mapModel.getMapPlayersStatisticsArray(), new Comparator<MapPlayersStatisticsModel>() {

				public int compare(MapPlayersStatisticsModel m1, MapPlayersStatisticsModel m2) {
					return m2.getWaveNo().compareTo(m1.getWaveNo());
				}
			});

			int length = mapModel.getMapPlayersStatisticsArray().size();
			StringBuilder sb = new StringBuilder();
			sb.append(
					" -------------------------------------------------------------------------------------------------\n");
			sb.append(" S.No	PLAYER NAME	 START			WAVE NO.	      END \n");
			sb.append(
					" -------------------------------------------------------------------------------------------------\n");
			for (int i = 0; i < length; i++) {
				sb.append(" " + (i + 1) + "	");
				sb.append(mapModel.getMapPlayersStatisticsArray().get(i).getPlayerName() + "	         ");
				sb.append(mapModel.getMapPlayersStatisticsArray().get(i).getCurrentSessionStart() + "	");
				sb.append(mapModel.getMapPlayersStatisticsArray().get(i).getWaveNo() + "	              ");
				sb.append(mapModel.getMapPlayersStatisticsArray().get(i).getCurrentSessionEnd() + "\n");
			}
			logResultant = sb.toString();
		} else {
			logResultant = "First load Map.";
		}
		return logResultant;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * the file to set
	 * 
	 * @param new_file new file
	 * 
	 */
	public void setFile(File new_file) {
		this.file = new_file;
	}

	/**
	 * returns the path of the log file
	 * 
	 * @return the logFilePath
	 */
	public String getLogFilePath() {
		return logFilePath;
	}

	/**
	 * sets the path for log file
	 * 
	 * @param new_logFilePath log file path
	 * 
	 */
	public void setLogFilePath(String new_logFilePath) {
		this.logFilePath = new_logFilePath;
	}

	/**
	 * returns the log file
	 * 
	 * @return the logResultant
	 */
	public String getLogResultant() {
		return logResultant;
	}

	/**
	 * sets the logResultant
	 * 
	 * @param new_logResultant log resultant
	 * 
	 */
	public void setLogResultant(String new_logResultant) {
		this.logResultant = new_logResultant;
	}

	/**
	 * returns the logger read state
	 * 
	 * @return the logReadingState
	 */
	public E_LogViewerState getLogReadingState() {
		return logReadingState;
	}

	/**
	 * sets the log Reading State
	 * 
	 * @param new_logReadingState log read state
	 * 
	 */
	public void setLogReadingState(E_LogViewerState new_logReadingState) {
		this.logReadingState = new_logReadingState;
	}

	/**
	 * returns the tower object
	 * 
	 * @return the tower
	 */
	public Tower getTower() {
		return tower;
	}

	/**
	 * sets the tower object
	 * 
	 * @param new_tower tower
	 * 
	 */
	public void setTower(Tower new_tower) {
		this.tower = new_tower;
	}

}