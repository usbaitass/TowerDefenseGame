package com.app.towerDefense.staticContent;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JButton;
import com.app.towerDefense.models.PlayerModel;
import com.app.towerDefense.models.Tower;

/**
 * This class contains all constant, Final and Static variables such as windows
 * width, height of window for main window and child window, titles, menu items
 * names and image paths
 * 
 * @author Sajjad Ashraf
 * 
 */
public class ApplicationStatics {

	// Game Statistics
	public static final int WINDOW_WIDTH = 840;
	public static final int WINDOW_HEIGHT = (int) WINDOW_WIDTH / 12 * 9;

	public static final int CHILD_POPUP_WINDOW_WIDTH = (int) WINDOW_WIDTH - 100;
	public static final int CHILD_POPUP_WINDOW_HEIGHT = (int) WINDOW_HEIGHT - 100;

	// Map variables
	public static JButton[][] MAP_BUTTONS;
	public static boolean SET_TOWER_DESCR_VISIBLE = false;
	public static int CURRENT_SELECTED_TOWER = 0;
	public static Tower[] TOWER_MODELS = new Tower[4];
	public static PlayerModel PLAYERMODEL;
	public static boolean HAS_BOUGHT_TOWER = false;
	public static boolean START_WAVE = false;
	public static ArrayList<Integer> PATH_ARRAY = new ArrayList<Integer>();
	public static ArrayList<Point> PATH_ARRAY1;
	public static String MAP_ROUT_PATH;
	public static String MAP_PATH_BOUNDARY_BUTTONS_NAME = "";
	public static int BLOCK_WIDTH;
	public static int BLOCK_HEIGHT;
	public static boolean GAME_OVER = false;
	public static String MAP_SECRECT_TAG = "_Team5";
	public static String MAP_CURRENT_OPENED_FILE_PATH = "";
	// public static MapModel MAP_MODEL ;
	/*
	 * public static final int MAP_ENTRY_POINT=1; public static final int
	 * MAP_EXIT_POINT=900; public static final int MAP_PATH_POINT=1; public
	 * static final int MAP_Scenery_POINT=0;
	 */

	public static final int MAP_ENTRY_POINT = 2;
	public static final int MAP_EXIT_POINT = 3;
	public static final int MAP_PATH_POINT = 1;
	public static final int MAP_Scenery_POINT = 0;

	// Window Titles
	// public static String GAME_PLAYER_NAME = "";
	private static final String TITLE_GAME_WINDOW = "Tower Defence Game by \"Team 5\".";
	public static final String TITLE_MAP_EDITOR = "MAP EDITOR";
	public static final String TITLE_LOG_VIEWER = "LOG VIEWER";
	public static final String MAP_MODE_CREATE = "(CREATE)";
	public static final String MAP_MODE_OPEN = "(OPEN)";
	public static final String LOG_VIEWER_MODE_GLOBLE = "GLOBLE";
	public static final String LOG_VIEWER_MODE_CURRENT_SESSION = "CURRENT SESSION";
	public static final String LOG_VIEWER_MODE_TOWERS = "TOWERS";
	public static final String LOG_VIEWER_MODE_TOWERS_COLLECTION = "TOWERS COLLECTION";
	public static final String LOG_VIEWER_MODE_MAP_PLAYERS_STATISTICS = "Map Players Statistics";

	// MENU Related Items
	public static final String MENU_FILE = "FILE";
	public static final String MENU_HELP = "HELP";
	public static final String MENU_VIEW = "VIEW";
	public static final String MENU_ITEM_PLAY = "PLAY";
	public static final String MENU_ITEM_CREATE_MAP = "CREATE MAP";
	public static final String MENU_ITEM_OPEN_MAP = "OPEN MAP";
	public static final String MENU_ITEM_LOAD_GAME = "LOAD GAME";
	public static final String MENU_ITEM_SAVE_GAME = "SAVE GAME";
	public static final String MENU_ITEM_ABOUT = "ABOUT";
	public static final String MENU_ITEM_SAVE = "SAVE";
	public static final String MENU_ITEM_EXIT = "EXIT";
	public static final String MENU_ITEM_LOG_VIEWER = "LOG VIEWER ";

	// Logs
	private static String LOG_CURRENT_SESSION_TAG = "";
	private static String LOG_TOWE_TAG = "";
	public static String LOG_File_PATH = "log\\gameLog.log";
	public static String LOG_File_PATH_iPhone = "log\\gameLog.log";
	public static boolean isLogViewerOpen = false;
	// Date
	public static final String DATE_FORMAT_DEFAULT = "yyyyMMddHHmmssSSS";

	// -- Images Paths
	public static final String IMAGE_PATH_GAME_BK = "images/gameBk.png";
	public static final String IMAGE_PATH_MAP_ENTRY = "images/mapEntry.png";
	public static final String IMAGE_PATH_MAP_EXIT = "images/mapExit.png";
	public static final String IMAGE_PATH_MAP_ROUTE = "images/mapRouteGreen.png";
	public static final String IMAGE_PATH_MAP_SCENERY = "images/mapSceneryGreen.png";
	public static final String IMAGE_PATH_MAP_CRITTER1 = "images/critter1.gif";
	public static final String IMAGE_PATH_MAP_SUN = "images/sun.png";
	public static final String IMAGE_PATH_MAP_TOWER1 = "images/tower1.png";
	public static final String IMAGE_PATH_MAP_TOWER2 = "images/tower2.png";
	public static final String IMAGE_PATH_MAP_TOWER3 = "images/tower3.png";
	public static final String IMAGE_PATH_MAP_TOWER4 = "images/tower4.png";
	public static final String IMAGE_PATH_MAP_TOWER5 = "images/tower5.png";
	public static final String IMAGE_PATH_MAP_BUTTONYELLOW = "images/button_yellow.png";
	public static final String IMAGE_PATH_CRITTER = "images/critter1.gif";
	public static final String IMAGE_PATH_CRITTER_FROZEN = "images/freeze.gif";
	public static final String IMAGE_PATH_CRITTER_BURN = "images/burn.gif";
	public static final String IMAGE_PATH_CRITTER_SPLASH = "images/splash.gif";

	// Popup Title
	public static final String TITLE_MSG_SET_SIZE_OF_MAP = "SET SIZE OF MAP";

	// Status
	public static final String STATUS_SUCCESS = "SUCCESS";

	// Prompt Messages
	public static final String MSG_IN_VALID_MAP = "In valid map file.";
	public static final String MSG_UNABLE_TO_TDM_OPEN_FILE = "Unable to .tdm open file";
	public static final String MSG_NO_FILE_SELECTED = "No file selected";
	public static final String MSG_MAP_FILE_LOADED_SAVED = "Map file %s successfully!";
	public static final String MSG_X_MAY_NOT_EMPTY = "Size of %s may not be empty";
	public static final String MSG_X_MUST_BE_IN_RANGE = "Size of %s must Lie between 1 - 30";
	public static final String MSG_CANCELED_MAP_CREATION = "Canceled map creation.";
	public static final String MSG_FILE_SELECT = "%s File %s selected.";
	public static final String MSG_CLOSING_GAME_APPLICATION = "Closing game application.";
	public static final String MSG_MENU_SELECTED = "Breadcrumbs Menu Selected: %s > %s.";
	public static final String MSG_GAME_PLAYER_NAME = "Game Player Name";

	// Getter and Setter Sections

	// Window Titles
	/**
	 * This method gets the game window title
	 * 
	 * @return the titleGameWindow
	 */
	public static String getTitleGameWindow() {
		if (ApplicationStatics.PLAYERMODEL.playerName.length() > 0)
			return TITLE_GAME_WINDOW + ". Player :(" + ApplicationStatics.PLAYERMODEL.playerName + ")";
		else
			return TITLE_GAME_WINDOW;
	}

	// Added log logic
	/**
	 * This method gets the current log session tag
	 * 
	 * @return the lOG_CURRENT_SESSION_TAG
	 */
	public static String getLog_Current_Session_Tag() {
		return LOG_CURRENT_SESSION_TAG;
	}

	/**
	 * This method sets the log current session tag
	 * 
	 * @param new_session
	 *            the lOG_CURRENT_SESSION_TAG to set
	 */
	public static void setLog_Current_Session_Tag(String new_session) {
		LOG_CURRENT_SESSION_TAG = "LOG_CURRENT_SESSION_TAG_" + new_session;
	}

	/**
	 * This method gets log tower tag
	 * 
	 * @return the lOG_TOWE_TAG
	 */
	public static String getLog_Tower_Tag() {
		return LOG_TOWE_TAG;
	}

	/**
	 * This method sets a log tower tag
	 * 
	 * @param new_session
	 *            the lOG_TOWE_TAG to set
	 */
	public static void setLog_Tower_Tag(String new_session) {
		LOG_TOWE_TAG = new_session;
	}

}
