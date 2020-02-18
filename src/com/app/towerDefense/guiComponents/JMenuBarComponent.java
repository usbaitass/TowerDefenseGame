package com.app.towerDefense.guiComponents;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.app.towerDefense.gameLogic.GameLoader;
import com.app.towerDefense.gameLogic.GameSaver;
import com.app.towerDefense.gameLogic.Map;
import com.app.towerDefense.guisystem.Game;
import com.app.towerDefense.guisystem.LogViewer;
import com.app.towerDefense.guisystem.MapEditor;
import com.app.towerDefense.models.MapModel;
import com.app.towerDefense.staticContent.AppilicationEnums.E_JFileChooserMode;
import com.app.towerDefense.staticContent.AppilicationEnums.E_LogViewerState;
import com.app.towerDefense.staticContent.AppilicationEnums.E_MapEditorMode;
import com.app.towerDefense.staticContent.ApplicationStatics;
import com.app.towerDefense.utilities.FileStorage;

/**
 * This class handles all menu bar component of the application
 * 
 * @author Sajjad Ashraf
 * 
 */
public class JMenuBarComponent {
	private JPanel gameMapPanel;

	private BottomGamePanelView bottomGamePanel;
	private static JPanelComponent panelComponent;

	// Logger added
	final static Logger logger = Logger.getLogger(JMenuBarComponent.class);

	/**
	 * this Function Implement the Menu bar for Gmae Main Window
	 * 
	 * @param new_jframe
	 *            as JFrame
	 * @return JMenuBar
	 */
	public JMenuBar getGameJMenuBar(final JFrame new_jframe) {

		// Set Background Image
		final JLabel backGround = new JLabel(new ImageIcon(
				((new ImageIcon("images/gameBk.png").getImage().getScaledInstance(new_jframe.getSize().width,
						(int) ((int) new_jframe.getSize().height - 30), java.awt.Image.SCALE_SMOOTH)))));
		new_jframe.add(backGround);

		JMenuBar menuBar = new JMenuBar();

		JMenu menuFile = new JMenu(ApplicationStatics.MENU_FILE);
		menuBar.add(menuFile);

		final JMenuItem menuItemPlay = new JMenuItem(ApplicationStatics.MENU_ITEM_PLAY);
		menuFile.add(menuItemPlay);
		final JMenuItem menuItemCreateMap = new JMenuItem(ApplicationStatics.MENU_ITEM_CREATE_MAP);
		menuFile.add(menuItemCreateMap);
		final JMenuItem menuItemOpenMap = new JMenuItem(ApplicationStatics.MENU_ITEM_OPEN_MAP);
		menuFile.add(menuItemOpenMap);
		final JMenuItem menuItemLoadGame = new JMenuItem(ApplicationStatics.MENU_ITEM_LOAD_GAME);
		menuFile.add(menuItemLoadGame);
		final JMenuItem menuItemSaveGame = new JMenuItem(ApplicationStatics.MENU_ITEM_SAVE_GAME);
		menuFile.add(menuItemSaveGame);
		final JMenuItem menuItemExit = new JMenuItem(ApplicationStatics.MENU_ITEM_EXIT);
		menuFile.add(menuItemExit);

		JMenu menuView = new JMenu(ApplicationStatics.MENU_VIEW);
		menuBar.add(menuView);
		final JMenuItem menuItemLogViewer = new JMenuItem(ApplicationStatics.MENU_ITEM_LOG_VIEWER);
		menuView.add(menuItemLogViewer);

		JMenu menuHelp = new JMenu(ApplicationStatics.MENU_HELP);
		menuBar.add(menuHelp);
		final JMenuItem menuItemAbout = new JMenuItem(ApplicationStatics.MENU_ITEM_ABOUT);
		menuHelp.add(menuItemAbout);

		/**
		 * This class handle the menu Item action on click action
		 * 
		 * @author Sajjad
		 * 
		 */
		class menuItemAction implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent new_e) {
				if (new_e.getSource().equals(menuItemPlay) || new_e.getSource().equals(menuItemLoadGame)) {

					JFileChooser fileChooser;
					ApplicationStatics.START_WAVE = false;
					GameLoader gameLoader = null;
					File file;

					boolean isGamePlay = false;
					if (new_e.getSource().equals(menuItemPlay)) {
						logger.info(String.format(ApplicationStatics.MSG_MENU_SELECTED, ApplicationStatics.MENU_FILE,
								ApplicationStatics.MENU_ITEM_PLAY));
						isGamePlay = true;
						fileChooser = new JFileChooserComponent().getJFileChooser(E_JFileChooserMode.MapPlay);
					} else {
						logger.info(String.format(ApplicationStatics.MSG_MENU_SELECTED, ApplicationStatics.MENU_FILE,
								ApplicationStatics.MENU_ITEM_LOAD_GAME));
						fileChooser = new JFileChooserComponent().getJFileChooser(E_JFileChooserMode.GameLoad);

					}
					int result = fileChooser.showOpenDialog(new_jframe);
					if (result == JFileChooser.APPROVE_OPTION) {
						file = fileChooser.getSelectedFile();

						if (!isGamePlay) {
							gameLoader = new GameLoader(file);
							file = new File(gameLoader.getMapFilePath());
						}

						// Check file exist or not
						if (!file.exists() && !file.isDirectory()) {
							// returnMsg="ERROR:Map file not found. \n Please
							// past file on folloing path and then try again. \n
							// Path:"+file.getAbsolutePath();
							JOptionPane.showMessageDialog(null,
									"ERROR:Map file not found. \n Please past file on folloing path and then try again. \n Path:"
											+ file.getAbsolutePath());
						} else {
							MapModel mapModel = (new com.app.towerDefense.utilities.FileStorage()).openMapFile(file);
							logger.info(String.format(ApplicationStatics.MSG_FILE_SELECT, "Map",
									" Name:" + file.getName() + " "));
							if (mapModel != null) {
								if (mapModel.mapSecret.contains(ApplicationStatics.MAP_SECRECT_TAG)) {
									// loadGamePlayGrid(new_jframe, mapModel,
									// file, true);
									if (isGamePlay)
										getPlayerName();

									new_jframe.getContentPane().removeAll();
									new_jframe.setLayout(new BorderLayout());
									panelComponent = new JPanelComponent();

									// MapLog code
									ApplicationStatics.MAP_CURRENT_OPENED_FILE_PATH = file.getAbsolutePath();

									gameMapPanel = (panelComponent).getMapEditorGridPanel(mapModel,
											new_jframe.getSize(), E_MapEditorMode.Play);

									ApplicationStatics.GAME_OVER = false;
									if (gameLoader != null)
										gameLoader.load();

									new_jframe.getContentPane().add(gameMapPanel, BorderLayout.NORTH);

									bottomGamePanel = (BottomGamePanelView) new JPanelComponent()
											.getGameTowerPanel(new_jframe.getSize());

									new_jframe.getContentPane().add(bottomGamePanel, BorderLayout.SOUTH);
									new_jframe.setVisible(true);
									panelComponent.setBottomGamePanelView(bottomGamePanel);

									ApplicationStatics.PLAYERMODEL.addObserver(panelComponent);

									ApplicationStatics.BLOCK_WIDTH = gameMapPanel.getWidth() / mapModel.getMapWidth();
									;
									ApplicationStatics.BLOCK_HEIGHT = gameMapPanel.getHeight()
											/ mapModel.getMapHeight();

									if (!isGamePlay) {
										gameLoader.recalculate();
									}

									panelComponent.setMapButtonsToYellow();

									logger.info(String.format(ApplicationStatics.MSG_MAP_FILE_LOADED_SAVED, "Loaded"));
								} else {
									logger.info(ApplicationStatics.MSG_IN_VALID_MAP);
									JOptionPane.showMessageDialog(null, ApplicationStatics.MSG_IN_VALID_MAP);
								}

							} else {
								logger.info(ApplicationStatics.MSG_UNABLE_TO_TDM_OPEN_FILE);
								JOptionPane.showMessageDialog(null, ApplicationStatics.MSG_UNABLE_TO_TDM_OPEN_FILE);
							}
						}

					} else {
						logger.info(ApplicationStatics.MSG_NO_FILE_SELECTED);
						JOptionPane.showMessageDialog(null, ApplicationStatics.MSG_NO_FILE_SELECTED);
					}
				} else if (new_e.getSource().equals(menuItemCreateMap)) {
					logger.info(String.format(ApplicationStatics.MSG_MENU_SELECTED, ApplicationStatics.MENU_FILE,
							ApplicationStatics.MENU_ITEM_CREATE_MAP));
					final JTextField txtX = new JTextField();
					final JTextField txtY = new JTextField();

					txtX.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent e) {
							char ch = e.getKeyChar();
							if (!(Character.isDigit(ch) || (ch == KeyEvent.VK_BACK_SPACE)
									|| (ch == KeyEvent.VK_DELETE))) {
								e.consume();
							}
							if (txtX.getText().length() > 2)
								e.consume();
						}
					});

					txtY.addKeyListener(new KeyAdapter() {
						public void keyTyped(KeyEvent e) {
							char ch = e.getKeyChar();
							if (!(Character.isDigit(ch) || (ch == KeyEvent.VK_BACK_SPACE)
									|| (ch == KeyEvent.VK_DELETE))) {
								e.consume();
							}
							if (txtY.getText().length() > 2)
								e.consume();
						}
					});

					Object[] message = { "Size of X:", txtX, "Size of Y:", txtY };

					int option = JOptionPane.showConfirmDialog(null, message,
							ApplicationStatics.TITLE_MSG_SET_SIZE_OF_MAP, JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.OK_OPTION) {
						String x = txtX.getText().trim();
						String y = txtY.getText().trim();

						if (x.length() == 0) {
							logger.info(String.format(ApplicationStatics.MSG_X_MAY_NOT_EMPTY, "X"));
							JOptionPane.showMessageDialog(null,
									String.format(ApplicationStatics.MSG_X_MAY_NOT_EMPTY, "X"));
						}

						else if (y.length() == 0) {
							logger.info(String.format(ApplicationStatics.MSG_X_MAY_NOT_EMPTY, "Y"));
							JOptionPane.showMessageDialog(null,
									String.format(ApplicationStatics.MSG_X_MAY_NOT_EMPTY, "Y"));
						}

						else if (Integer.parseInt(x) < 1 || Integer.parseInt(x) > 30) {
							logger.info(String.format(ApplicationStatics.MSG_X_MUST_BE_IN_RANGE, "X"));
							JOptionPane.showMessageDialog(null,
									String.format(ApplicationStatics.MSG_X_MUST_BE_IN_RANGE, "X"));
						}

						else if (Integer.parseInt(y) < 1 || Integer.parseInt(y) > 30) {
							logger.info(String.format(ApplicationStatics.MSG_X_MUST_BE_IN_RANGE, "Y"));
							JOptionPane.showMessageDialog(null,
									String.format(ApplicationStatics.MSG_X_MUST_BE_IN_RANGE, "Y"));
						}

						else {
							MapModel mapModel = new MapModel();
							mapModel.setMapWidth(Integer.parseInt(x));
							mapModel.setMapHeight(Integer.parseInt(y));
							new MapEditor(new_jframe, ApplicationStatics.TITLE_MAP_EDITOR,
									ApplicationStatics.CHILD_POPUP_WINDOW_WIDTH,
									ApplicationStatics.CHILD_POPUP_WINDOW_HEIGHT, mapModel, E_MapEditorMode.Create);
						}
					} else {
						logger.info(ApplicationStatics.MSG_CANCELED_MAP_CREATION);
					}

				} else if (new_e.getSource().equals(menuItemOpenMap)) {
					logger.info(String.format(ApplicationStatics.MSG_MENU_SELECTED, ApplicationStatics.MENU_FILE,
							ApplicationStatics.MENU_ITEM_OPEN_MAP));
					JFileChooser fileChooser = new JFileChooserComponent().getJFileChooser(E_JFileChooserMode.MapOpen);
					int result = fileChooser.showOpenDialog(new_jframe);
					if (result == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						MapModel mapModel = (new com.app.towerDefense.utilities.FileStorage()).openMapFile(file);
						logger.info(String.format(ApplicationStatics.MSG_FILE_SELECT, "Map",
								" Name:" + file.getName() + " "));
						if (mapModel != null) {
							if (mapModel.mapSecret.contains(ApplicationStatics.MAP_SECRECT_TAG)) {
								new MapEditor(new_jframe, ApplicationStatics.TITLE_MAP_EDITOR,
										ApplicationStatics.CHILD_POPUP_WINDOW_WIDTH,
										ApplicationStatics.CHILD_POPUP_WINDOW_HEIGHT, mapModel, E_MapEditorMode.Open);
							} else {
								logger.info(ApplicationStatics.MSG_IN_VALID_MAP);
								JOptionPane.showMessageDialog(null, ApplicationStatics.MSG_IN_VALID_MAP);
							}

						} else {
							logger.info(ApplicationStatics.MSG_UNABLE_TO_TDM_OPEN_FILE);
							JOptionPane.showMessageDialog(null, ApplicationStatics.MSG_UNABLE_TO_TDM_OPEN_FILE);
						}
					} else {
						logger.info(ApplicationStatics.MSG_NO_FILE_SELECTED);
						JOptionPane.showMessageDialog(null, ApplicationStatics.MSG_NO_FILE_SELECTED);
					}
				} else if (new_e.getSource().equals(menuItemExit)) {
					logger.info(String.format(ApplicationStatics.MSG_MENU_SELECTED, ApplicationStatics.MENU_FILE,
							ApplicationStatics.MENU_ITEM_EXIT));
					logger.info(ApplicationStatics.MSG_CLOSING_GAME_APPLICATION);
					System.exit(0);
				} else if (new_e.getSource().equals(menuItemAbout)) {
					logger.info(String.format(ApplicationStatics.MSG_MENU_SELECTED, ApplicationStatics.MENU_HELP,
							ApplicationStatics.MENU_ITEM_ABOUT));
					JOptionPane.showMessageDialog(null,
							"**** Tower Defense Game **** \r\n Version 1.0 Build 1 \r\n Developed By Team5 \r\n All rights reserved  � Fall 2016");
				} else if (new_e.getSource().equals(menuItemLogViewer)) {
					logger.info(String.format(ApplicationStatics.MSG_MENU_SELECTED, ApplicationStatics.MENU_VIEW,
							ApplicationStatics.MENU_ITEM_LOG_VIEWER));
					if (System.getProperty("os.name").contains("Windows")) {
						new LogViewer(new_jframe, ApplicationStatics.TITLE_LOG_VIEWER,
								ApplicationStatics.CHILD_POPUP_WINDOW_WIDTH,
								ApplicationStatics.CHILD_POPUP_WINDOW_HEIGHT, ApplicationStatics.LOG_File_PATH,
								E_LogViewerState.GlobalLog, null);
					} else {
						new LogViewer(new_jframe, ApplicationStatics.TITLE_LOG_VIEWER,
								ApplicationStatics.CHILD_POPUP_WINDOW_WIDTH,
								ApplicationStatics.CHILD_POPUP_WINDOW_HEIGHT, ApplicationStatics.LOG_File_PATH_iPhone,
								E_LogViewerState.GlobalLog, null);
					}

				}

				else if (new_e.getSource().equals(menuItemSaveGame)) {
					logger.info(String.format(ApplicationStatics.MSG_MENU_SELECTED, ApplicationStatics.MENU_FILE,
							ApplicationStatics.MENU_ITEM_SAVE_GAME));
					System.out.println("ApplicationStatics.START_WAVE :" + ApplicationStatics.START_WAVE);
					if (ApplicationStatics.GAME_OVER) {
						JOptionPane.showMessageDialog(null, "Cannot save game. Game is over.");
					} else if (ApplicationStatics.START_WAVE) {
						JOptionPane.showMessageDialog(null,
								"Cannot save fame. Game is Running. Please wait end of game wave.");
						logger.info("Cannot save game. Game is running. Please wait end of game wave.");
					} else if (!ApplicationStatics.START_WAVE && ApplicationStatics.PLAYERMODEL.getGameWave() > 1) {

						JFileChooser fileChooser = new JFileChooserComponent()
								.getJFileChooser(E_JFileChooserMode.GameLoad);
						int result = fileChooser.showSaveDialog(new_jframe);
						if (result == JFileChooser.APPROVE_OPTION) {
							File file = fileChooser.getSelectedFile();
							try {
								GameSaver gameSaver = new GameSaver(file);
								JOptionPane.showMessageDialog(null, "Game saved successfully.");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								// e.printStackTrace();
								JOptionPane.showMessageDialog(null, e.getMessage());
							}

						} else {
							logger.info(ApplicationStatics.MSG_NO_FILE_SELECTED);
							JOptionPane.showMessageDialog(null, ApplicationStatics.MSG_NO_FILE_SELECTED);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Cannot save game on intital wave.");
						logger.info("Cannot save game on intital wave.");
					}
				}
			}

		}
		menuItemPlay.addActionListener(new menuItemAction());
		menuItemCreateMap.addActionListener(new menuItemAction());
		menuItemOpenMap.addActionListener(new menuItemAction());
		menuItemExit.addActionListener(new menuItemAction());
		menuItemAbout.addActionListener(new menuItemAction());
		menuItemLogViewer.addActionListener(new menuItemAction());
		menuItemLoadGame.addActionListener(new menuItemAction());
		menuItemSaveGame.addActionListener(new menuItemAction());
		return menuBar;
	}

	/**
	 * This method gets the Map editor menu bar
	 * 
	 * @param new_mapModel
	 *            type MapModel object is passed to the getMapEditorJmenuBar
	 * @param new_jframe
	 *            type JFrame the frame of the application
	 * @return the menu bar type JMenuBar
	 */
	public JMenuBar getMapEditorJMenuBar(final MapModel new_mapModel, final JFrame new_jframe) {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu(ApplicationStatics.MENU_FILE);
		final JMenuItem menuItemSave = new JMenuItem(ApplicationStatics.MENU_ITEM_SAVE);
		menuFile.add(menuItemSave);
		final JMenuItem menuItemExit = new JMenuItem(ApplicationStatics.MENU_ITEM_EXIT);
		menuFile.add(menuItemExit);

		class menuItemAction implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent new_event) {
				if (new_event.getSource().equals(menuItemSave)) {
					logger.info(String.format(ApplicationStatics.MSG_MENU_SELECTED, ApplicationStatics.MENU_FILE,
							ApplicationStatics.MENU_ITEM_SAVE));
					String MapValidationStatus = (new Map()).mapValidations(new_mapModel);

					if (MapValidationStatus != null) {
						logger.info("Map Validation Failed, " + MapValidationStatus);
						JOptionPane.showMessageDialog(null, MapValidationStatus);
					} else {
						JFileChooser fileChooser = new JFileChooserComponent()
								.getJFileChooser(E_JFileChooserMode.MapSave);
						int result = fileChooser.showSaveDialog(null);
						if (result == JFileChooser.APPROVE_OPTION) {
							File file = fileChooser.getSelectedFile();
							logger.info(String.format(ApplicationStatics.MSG_FILE_SELECT, "Map",
									" Name:" + file.getName() + " "));

							new_mapModel.setMapSecret();

							new_mapModel.setMapRoutPath(ApplicationStatics.MAP_ROUT_PATH);
							new_mapModel.setMapRoutBoundaries(ApplicationStatics.MAP_PATH_BOUNDARY_BUTTONS_NAME);
							new_mapModel.getMapRoutPathList();

							String msg = new FileStorage().saveMapFile(file, new_mapModel);
							if (msg.contains(ApplicationStatics.STATUS_SUCCESS)) {
								logger.info(String.format(ApplicationStatics.MSG_MAP_FILE_LOADED_SAVED, "saved"));
								JOptionPane.showMessageDialog(null,
										String.format(ApplicationStatics.MSG_MAP_FILE_LOADED_SAVED, "saved"));
								closeFrame(new_jframe);
							} else {
								logger.info("Saving failed, " + msg);
								JOptionPane.showMessageDialog(null, msg);
							}
						}
					}
				}

				else if (new_event.getSource().equals(menuItemExit)) {
					closeFrame(new_jframe);
				}

			}

		}
		menuItemSave.addActionListener(new menuItemAction());
		menuItemExit.addActionListener(new menuItemAction());
		menuBar.add(menuFile);
		return menuBar;
	}

	/**
	 * this function Prompt user to enter Player Name
	 */
	public void getPlayerName() {
		// reset Player Name or remove old player name
		ApplicationStatics.PLAYERMODEL.playerName = "";
		JTextField firstName = new JTextField();
		final JComponent[] jComponentArray = new JComponent[] { new JLabel(ApplicationStatics.MSG_GAME_PLAYER_NAME),
				firstName };
		JOptionPane.showMessageDialog(null, jComponentArray, ApplicationStatics.getTitleGameWindow(),
				JOptionPane.PLAIN_MESSAGE);
		if (firstName.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, ApplicationStatics.MSG_GAME_PLAYER_NAME + " required.",
					ApplicationStatics.getTitleGameWindow(), JOptionPane.WARNING_MESSAGE);
			getPlayerName();
		} else {
			ApplicationStatics.PLAYERMODEL.playerName = firstName.getText().replaceAll(" ", "_");
			logger.info(ApplicationStatics.MSG_GAME_PLAYER_NAME + " : " + ApplicationStatics.PLAYERMODEL.playerName);
			Game.getInstance().refreshGameFrameTitle();
		}
	}

	/**
	 * This method closes the frame of the application
	 * 
	 * @param new_jframe
	 *            the frame of the application
	 */
	public void closeFrame(JFrame new_jframe) {
		new_jframe.dispose();
	}

	/**
	 * This method returns the bottom panel
	 * 
	 * @return bottom game panel object
	 */
	public BottomGamePanelView getBottomPanel() {
		return bottomGamePanel;
	}

	/**
	 * This method returns the top map panel
	 * 
	 * @return game map panel object
	 */
	public JPanelComponent getPanelComponent() {
		return panelComponent;
	}

}
