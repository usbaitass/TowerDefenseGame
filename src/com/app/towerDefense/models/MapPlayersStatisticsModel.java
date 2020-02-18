/**
 * 
 */
package com.app.towerDefense.models;

/**
 * 
 * @author Sajjad Ashraf
 *
 */
public class MapPlayersStatisticsModel {

	String playerName;
	String currentSessionStart;
	String waveNo;
	String currentSessionEnd;

	/**
	 * Constructor for MapPlayersStatisticsModel
	 */
	public MapPlayersStatisticsModel() {
		// default constructor
	}

	/**
	 * Constructor for MapPlayersStatisticsModel
	 * 
	 * @param new_playerName player name
	 * @param new_currentSessionStart current session
	 * @param new_waveNo wave number
	 * @param new_currentSessionEnd current session
	 */
	public MapPlayersStatisticsModel(String new_playerName, String new_currentSessionStart, String new_waveNo,
			String new_currentSessionEnd) {
		super();
		this.playerName = new_playerName;
		this.currentSessionStart = new_currentSessionStart;
		this.waveNo = new_waveNo;
		this.currentSessionEnd = new_currentSessionEnd;
	}

	/**
	 * This method gets the Player name
	 * 
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * This method sets the player name
	 * 
	 * @param new_playerName
	 *            the playerName to set
	 */
	public void setPlayerName(String new_playerName) {
		this.playerName = new_playerName;
	}

	/**
	 * This method gets the current session start value
	 * 
	 * @return the currentSessionStart
	 */
	public String getCurrentSessionStart() {
		return currentSessionStart;
	}

	/**
	 * This method sets the current session start value
	 * 
	 * @param new_currentSessionStart
	 *            the currentSessionStart to set
	 */
	public void setCurrentSessionStart(String new_currentSessionStart) {
		this.currentSessionStart = new_currentSessionStart;
	}

	/**
	 * This method gets the waveNo
	 * 
	 * @return the waveNo
	 */
	public String getWaveNo() {
		return waveNo;
	}

	/**
	 * This method sets the waveNo
	 * 
	 * @param new_waveNo
	 *            the waveNo to set
	 */
	public void setWaveNo(String new_waveNo) {
		this.waveNo = new_waveNo;
	}

	/**
	 * This method gets the current session end value
	 * 
	 * @return the currentSessionEtart
	 */
	public String getCurrentSessionEnd() {
		return currentSessionEnd;
	}

	/**
	 * This method sets current session end value
	 * 
	 * @param new_currentSessionEtart
	 *            the currentSessionEtart to set
	 */
	public void setCurrentSessionEnd(String new_currentSessionEtart) {
		this.currentSessionEnd = new_currentSessionEtart;
	}

}
