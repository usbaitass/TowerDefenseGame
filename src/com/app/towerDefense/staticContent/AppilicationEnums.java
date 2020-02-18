package com.app.towerDefense.staticContent;

/**
 * this class contains All enumerators used by the whole application actually
 * this the only place where must declare our All enumerators
 * 
 * @author Sajjad Ashraf
 * 
 */
public class AppilicationEnums {

	/**
	 * Enumerators to express Map Editor Mode
	 * 
	 */
	public enum E_MapEditorMode {
		Create, Open, Play
	}

	/**
	 * Enumerators to used in Map Validation which the direction of map
	 * validation iteration
	 * 
	 */
	public enum E_MapValidationDirecton {
		Initial, Up, Down, Left, Right
	}

	/**
	 * Enumerators to express the Map Cell/Points Description
	 * 
	 */
	public enum E_MapCellDescription {
		Unselected, Selected, Enrtry, Exit
	}

	/**
	 * Enumerators For JFile Chooser Mode
	 * 
	 */
	public enum E_JFileChooserMode {
		MapOpen, MapSave, MapPlay, GameLoad, GameSave
	}

	/**
	 * Enumerators to express the Log Viewer State
	 * 
	 */
	public enum E_LogViewerState {
		GlobalLog, CurrentSessionLog, TowerLog, TowerCollectionLog, MapPlayersStatistics
	}

}
