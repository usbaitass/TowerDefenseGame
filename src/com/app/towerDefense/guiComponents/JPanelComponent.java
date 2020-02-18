package com.app.towerDefense.guiComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.app.towerDefense.gameLogic.LogReader;
import com.app.towerDefense.guisystem.Game;
import com.app.towerDefense.guisystem.LogViewer;
import com.app.towerDefense.models.MapModel;
import com.app.towerDefense.models.Tower;
import com.app.towerDefense.models.TowerFactory;
import com.app.towerDefense.staticContent.AppilicationEnums.E_LogViewerState;
import com.app.towerDefense.staticContent.AppilicationEnums.E_MapEditorMode;
import com.app.towerDefense.staticContent.ApplicationStatics;
import com.app.towerDefense.utilities.MiscellaneousHelper;

/**
 * This class has All JPanel Implementation like GamePlayMap JPanel, MapEdiort
 * JPanel.
 * 
 * @author Sajjad Ashraf
 * 
 */
public class JPanelComponent implements Observer {

	private BottomGamePanelView bottomGamePanel;

	private JButton mapButtonsGrid2DArray[][];
	private JButton jButtonEntry;
	private JButton jButtonExit;
	public JButton jButtonTower;
	public MapPanel mapPanel; // CHANGE Ulan

	private E_MapEditorMode mapEditorMode;
	final static Logger logger = Logger.getLogger(JPanelComponent.class);

	/**
	 * get Implemented Panel for Game Window screen Tower Section
	 * 
	 * @param new_parentDimension
	 *            type Dimension dimension of parent Screen so that this panel
	 *            Fit or reshape accordingly
	 * @return JPanel
	 */
	public JPanel getGameTowerPanel(Dimension new_parentDimension) {
		bottomGamePanel = new BottomGamePanelView(new_parentDimension.width, new_parentDimension.height * 1 / 4 - 30);
		bottomGamePanel
				.setPreferredSize(new Dimension(new_parentDimension.width, new_parentDimension.height * 1 / 4 - 30));
		bottomGamePanel
				.setMaximumSize(new Dimension(new_parentDimension.width, new_parentDimension.height * 1 / 4 - 30));
		bottomGamePanel
				.setMinimumSize(new Dimension(new_parentDimension.width, new_parentDimension.height * 1 / 4 - 30));
		return bottomGamePanel;
	}

	/**
	 * get Implemented Panel for Game Window screen Map Section in Playing mode
	 * 
	 * @param new_width
	 *            Integer width of parent
	 * @param new_height
	 *            Integer height of parent
	 * @return JPanel game panel object
	 */
	public JPanel getGameMapPanel(int new_width, int new_height) {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(new_width, new_height));
		panel.setMaximumSize(new Dimension(new_width, new_height));
		panel.setMinimumSize(new Dimension(new_width, new_height));
		panel.setBackground(Color.GREEN);
		return panel;
	}

	/**
	 * get Implemented Panel for Game Window screen Actual Map Grid this one
	 * Function is used for three different purposes 1) Map Grid when Mode is
	 * Play 2) Map Grid Editor when Mode is Create 3) Map Grid Editor when Mode
	 * is OPen
	 * 
	 * @param new_mapModel
	 *            Type MapModel
	 * @param new_parentDimension
	 *            Type Dimension
	 * @param new_mode
	 *            Type E_MapEditorMode Enum variable with three possible values
	 *            {Create, Open, Play}
	 * @return JPanel
	 */
	public JPanel getMapEditorGridPanel(final MapModel new_mapModel, Dimension new_parentDimension,
			E_MapEditorMode new_mode) {
		mapEditorMode = new_mode;
		JPanel panel;
		GridLayout gridLayout;

		// When Create Mode Initialize the mapGridSelection to new
		if (E_MapEditorMode.Create == mapEditorMode) {
			new_mapModel.mapGridSelection = new int[new_mapModel.getMapHeight()][new_mapModel.getMapWidth()];
		}

		// In case 'Play' Increase Panel size According to the Play Game Window
		if (E_MapEditorMode.Play == mapEditorMode) {

			panel = new MapPanel(new_mapModel);
			gridLayout = new GridLayout(new_mapModel.getMapHeight(), new_mapModel.getMapWidth(), 0, 0);
			panel.setLayout(gridLayout);

			mapPanel = (MapPanel) panel; // CHANGE Ulan

			if (new_parentDimension != null) {
				panel.setPreferredSize(
						new Dimension(new_parentDimension.width, new_parentDimension.height * 3 / 4 - 10));
				panel.setMaximumSize(new Dimension(new_parentDimension.width, new_parentDimension.height * 3 / 4 - 10));
				panel.setMinimumSize(new Dimension(new_parentDimension.width, new_parentDimension.height * 3 / 4 - 10));
			}
		} else {
			panel = new JPanel();
			gridLayout = new GridLayout(new_mapModel.getMapHeight(), new_mapModel.getMapWidth(), 3, 3);
			panel.setLayout(gridLayout);
		}

		mapButtonsGrid2DArray = new JButton[new_mapModel.getMapHeight()][new_mapModel.getMapWidth()];

		for (int i = 0; i < new_mapModel.getMapHeight(); i++) {
			for (int j = 0; j < new_mapModel.getMapWidth(); j++) {
				mapButtonsGrid2DArray[i][j] = new JButton();
				int value = 0;
				int multiple = 0;

				multiple = new_mapModel.getMapWidth();

				if (i == 0 && j == 0)
					value = 0;
				else
					value = 1 + j + (i * multiple);

				mapButtonsGrid2DArray[i][j].setName(value + ":" + i + ":" + j);

				if (E_MapEditorMode.Create == mapEditorMode) {
					new_mapModel.mapGridSelection[i][j] = 0;
					mapButtonsGrid2DArray[i][j].setBackground(Color.gray);
					// Click event
					addButtonClickEvents(mapButtonsGrid2DArray[i][j], new_mapModel);
					// Right Click Event
					addMouseClickOnButtonEvents(mapButtonsGrid2DArray[i][j], new_mapModel);
				}

				else if (E_MapEditorMode.Open == mapEditorMode) {

					if (new_mapModel.mapGridSelection[i][j] == ApplicationStatics.MAP_PATH_POINT) {
						mapButtonsGrid2DArray[i][j].setBackground(Color.green);
					} else if (new_mapModel.mapGridSelection[i][j] == ApplicationStatics.MAP_ENTRY_POINT) {
						mapButtonsGrid2DArray[i][j].setBackground(Color.red);
						mapButtonsGrid2DArray[i][j].setText("E");
					} else if (new_mapModel.mapGridSelection[i][j] == ApplicationStatics.MAP_EXIT_POINT) {
						mapButtonsGrid2DArray[i][j].setBackground(Color.red);
						mapButtonsGrid2DArray[i][j].setText("O");
					} else {
						mapButtonsGrid2DArray[i][j].setBackground(Color.gray);
					}

					// Click event
					addButtonClickEvents(mapButtonsGrid2DArray[i][j], new_mapModel);
					// Right Click Event
					addMouseClickOnButtonEvents(mapButtonsGrid2DArray[i][j], new_mapModel);
				}

				else if (E_MapEditorMode.Play == mapEditorMode) {
					mapButtonsGrid2DArray[i][j].setBorder(new LineBorder(Color.green, 0));
					mapButtonsGrid2DArray[i][j].setPreferredSize(new Dimension(10, 10));

					// Condition for Path cell
					if (new_mapModel.mapGridSelection[i][j] == ApplicationStatics.MAP_PATH_POINT) {
						mapButtonsGrid2DArray[i][j].setVisible(false);
						ApplicationStatics.PATH_ARRAY.add(i);
						ApplicationStatics.PATH_ARRAY.add(j);

						if ((i >= 0 && i < new_mapModel.getMapHeight()) || (j >= 0 && j < new_mapModel.getMapWidth())) {
							String name = "";
							// Select Down button
							if (i != new_mapModel.getMapHeight() - 1) {
								if (new_mapModel.mapGridSelection[i + 1][j] != ApplicationStatics.MAP_PATH_POINT
										&& new_mapModel.mapGridSelection[i + 1][j] != ApplicationStatics.MAP_ENTRY_POINT
										&& new_mapModel.mapGridSelection[i
												+ 1][j] != ApplicationStatics.MAP_EXIT_POINT) {
									name = "" + (i + 1) + ":" + j;
									if (!ApplicationStatics.MAP_PATH_BOUNDARY_BUTTONS_NAME.contains(name))
										ApplicationStatics.MAP_PATH_BOUNDARY_BUTTONS_NAME += name + ",";
								}
							}

							// Select up button
							if (i > 0) {
								if (new_mapModel.mapGridSelection[i - 1][j] != ApplicationStatics.MAP_PATH_POINT
										&& new_mapModel.mapGridSelection[i - 1][j] != ApplicationStatics.MAP_ENTRY_POINT
										&& new_mapModel.mapGridSelection[i
												- 1][j] != ApplicationStatics.MAP_EXIT_POINT) {
									name = "" + (i - 1) + ":" + j;
									if (!ApplicationStatics.MAP_PATH_BOUNDARY_BUTTONS_NAME.contains(name))
										ApplicationStatics.MAP_PATH_BOUNDARY_BUTTONS_NAME += name + ",";
								}
							}

							// Checking for Left cell
							if (j != new_mapModel.getMapWidth() - 1) {
								if (new_mapModel.mapGridSelection[i][j + 1] != ApplicationStatics.MAP_PATH_POINT
										&& new_mapModel.mapGridSelection[i][j + 1] != ApplicationStatics.MAP_ENTRY_POINT
										&& new_mapModel.mapGridSelection[i][j
												+ 1] != ApplicationStatics.MAP_EXIT_POINT) {
									name = "" + (i) + ":" + (j + 1);
									if (!ApplicationStatics.MAP_PATH_BOUNDARY_BUTTONS_NAME.contains(name))
										ApplicationStatics.MAP_PATH_BOUNDARY_BUTTONS_NAME += name + ",";
								}
							}

							// Checking For Right Cell
							if (j > 0) {
								if (new_mapModel.mapGridSelection[i][j - 1] != ApplicationStatics.MAP_PATH_POINT
										&& new_mapModel.mapGridSelection[i][j - 1] != ApplicationStatics.MAP_ENTRY_POINT
										&& new_mapModel.mapGridSelection[i][j
												- 1] != ApplicationStatics.MAP_EXIT_POINT) {
									name = "" + (i) + ":" + (j - 1);
									if (!ApplicationStatics.MAP_PATH_BOUNDARY_BUTTONS_NAME.contains(name))
										ApplicationStatics.MAP_PATH_BOUNDARY_BUTTONS_NAME += name + ",";
								}
							}

						}

					}
					// Condition for Entry Cell
					else if (new_mapModel.mapGridSelection[i][j] == ApplicationStatics.MAP_ENTRY_POINT) {
						mapButtonsGrid2DArray[i][j].setBackground(Color.red);
						mapButtonsGrid2DArray[i][j].setText("E");
						// b[i][j].setIcon(new ImageIcon (
						// ApplicationStatics.IMAGE_PATH_MAP_ENTRY));
						mapButtonsGrid2DArray[i][j].setEnabled(false);
					}
					// Condition for Exit Cell
					else if (new_mapModel.mapGridSelection[i][j] == ApplicationStatics.MAP_EXIT_POINT) {
						mapButtonsGrid2DArray[i][j].setBackground(Color.red);
						mapButtonsGrid2DArray[i][j].setText("O");
						mapButtonsGrid2DArray[i][j].setEnabled(false);
					}
					// Condition for Scenery Cell
					else {
						mapButtonsGrid2DArray[i][j].setIcon(new ImageIcon(
								((new ImageIcon(ApplicationStatics.IMAGE_PATH_MAP_SCENERY).getImage().getScaledInstance(
										(int) new_parentDimension.getWidth() / new_mapModel.getMapWidth(),
										(int) new_parentDimension.getHeight() / new_mapModel.getMapHeight(),
										java.awt.Image.SCALE_SMOOTH)))));

						// Click event
						addButtonClickEvents(mapButtonsGrid2DArray[i][j], new_mapModel);

					}

				}

				mapButtonsGrid2DArray[i][j].setOpaque(true);
				mapButtonsGrid2DArray[i][j].setBorderPainted(false);
				panel.add(mapButtonsGrid2DArray[i][j]);
			}
		}

		if (E_MapEditorMode.Play == mapEditorMode) {
			if (ApplicationStatics.MAP_PATH_BOUNDARY_BUTTONS_NAME.length() > 1) {
				ApplicationStatics.MAP_PATH_BOUNDARY_BUTTONS_NAME = (new MiscellaneousHelper())
						.RemoveCharacterFromEndorRight(ApplicationStatics.MAP_PATH_BOUNDARY_BUTTONS_NAME, ",");
			}
			logger.info("MAP Boundary Points : " + ApplicationStatics.MAP_PATH_BOUNDARY_BUTTONS_NAME);
			ApplicationStatics.MAP_BUTTONS = mapButtonsGrid2DArray;

		}

		return panel;
	}

	/**
	 * Get UI JPanel for logViewer
	 * 
	 * @param new_log_file_path file path
	 * @param new_elog_viewer_state enum
	 * @param new_tempTM temp
	 * @return JPanel that contains all Log Viewer Data. log view data
	 */
	public JPanel getLogViewerPanel(final String new_log_file_path, E_LogViewerState new_elog_viewer_state,
			final Tower new_tempTM) {
		JPanel panel = new JPanel();
		// Tabs
		JTabbedPane jTabbedPane = new JTabbedPane();
		panel.add(jTabbedPane);

		// Tabs Panel
		JPanel tabPanelGloble = new JPanel();
		JPanel tabPanelCurrentSession = new JPanel();
		JPanel tabPanelTowers = new JPanel();
		JPanel tabPanelTowersCollection = new JPanel();
		JPanel tabPanelMapPlayersStatistics = new JPanel();

		// TextArea for Displaying log data
		final JTextArea txtAreaGloble = getJTextArea();
		final JTextArea txtAreaCurrentSession = getJTextArea();
		final JTextArea txtAreaTowers = getJTextArea();
		final JTextArea txtAreaTowersCollection = getJTextArea();
		final JTextArea txtAreaMapPlayersStatistics = getJTextArea();

		// Add Scroll bar to tabs
		tabPanelGloble.add(getJScrollPane(txtAreaGloble));
		tabPanelCurrentSession.add(getJScrollPane(txtAreaCurrentSession));
		tabPanelTowers.add(getJScrollPane(txtAreaTowers));
		tabPanelTowersCollection.add(getJScrollPane(txtAreaTowersCollection));
		tabPanelMapPlayersStatistics.add(getJScrollPane(txtAreaMapPlayersStatistics));

		// add Panels to Tabs
		jTabbedPane.addTab(ApplicationStatics.LOG_VIEWER_MODE_GLOBLE, tabPanelGloble);
		jTabbedPane.addTab(ApplicationStatics.LOG_VIEWER_MODE_CURRENT_SESSION, tabPanelCurrentSession);
		jTabbedPane.addTab(ApplicationStatics.LOG_VIEWER_MODE_TOWERS, tabPanelTowers);
		jTabbedPane.addTab(ApplicationStatics.LOG_VIEWER_MODE_TOWERS_COLLECTION, tabPanelTowersCollection);
		jTabbedPane.addTab(ApplicationStatics.LOG_VIEWER_MODE_MAP_PLAYERS_STATISTICS, tabPanelMapPlayersStatistics);

		// Set Tab which is by default Opened
		jTabbedPane.setSelectedIndex(new_elog_viewer_state.ordinal());

		// Read Logs for Current Opeing Tab
		JTextArea txtAreaLog;
		LogReader logReader = new LogReader(new_log_file_path, new_elog_viewer_state, new_tempTM);
		if (E_LogViewerState.TowerLog == new_elog_viewer_state) {
			txtAreaTowers.setText(logReader.read());
			txtAreaTowers.setCaretPosition(txtAreaTowers.getDocument().getLength());
			txtAreaLog = txtAreaTowers;
		} else {
			txtAreaGloble.setText(logReader.read());
			txtAreaGloble.setCaretPosition(txtAreaGloble.getDocument().getLength());
			txtAreaLog = txtAreaGloble;
		}

		// initialized Tab Change event
		jTabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (e.getSource() instanceof JTabbedPane) {
					JTabbedPane pane = (JTabbedPane) e.getSource();
					if (E_LogViewerState.GlobalLog.ordinal() == pane.getSelectedIndex()) {
						txtAreaGloble.setText(
								new LogReader(new_log_file_path, E_LogViewerState.GlobalLog, new_tempTM).read());
						txtAreaGloble.setCaretPosition(txtAreaGloble.getDocument().getLength());
						LogViewer.resetLogInfo(txtAreaGloble, new_log_file_path, E_LogViewerState.GlobalLog,
								new_tempTM);
					} else if (E_LogViewerState.CurrentSessionLog.ordinal() == pane.getSelectedIndex()) {
						txtAreaCurrentSession.setText(
								new LogReader(new_log_file_path, E_LogViewerState.CurrentSessionLog, new_tempTM)
										.read());
						txtAreaCurrentSession.setCaretPosition(txtAreaCurrentSession.getDocument().getLength());
						LogViewer.resetLogInfo(txtAreaCurrentSession, new_log_file_path,
								E_LogViewerState.CurrentSessionLog, new_tempTM);
					} else if (E_LogViewerState.TowerLog.ordinal() == pane.getSelectedIndex()) {
						txtAreaTowers.setText(
								new LogReader(new_log_file_path, E_LogViewerState.TowerLog, new_tempTM).read());
						txtAreaTowers.setCaretPosition(txtAreaTowers.getDocument().getLength());
						LogViewer.resetLogInfo(txtAreaTowers, new_log_file_path, E_LogViewerState.TowerLog, new_tempTM);
					} else if (E_LogViewerState.TowerCollectionLog.ordinal() == pane.getSelectedIndex()) {
						txtAreaTowersCollection.setText(
								new LogReader(new_log_file_path, E_LogViewerState.TowerCollectionLog, new_tempTM)
										.read());
						txtAreaTowersCollection.setCaretPosition(txtAreaTowersCollection.getDocument().getLength());
						LogViewer.resetLogInfo(txtAreaTowersCollection, new_log_file_path,
								E_LogViewerState.TowerCollectionLog, new_tempTM);
					} else if (E_LogViewerState.MapPlayersStatistics.ordinal() == pane.getSelectedIndex()) {
						txtAreaMapPlayersStatistics.setText(
								new LogReader(new_log_file_path, E_LogViewerState.MapPlayersStatistics, new_tempTM)
										.read());
						txtAreaMapPlayersStatistics
								.setCaretPosition(txtAreaMapPlayersStatistics.getDocument().getLength());
						LogViewer.resetLogInfo(txtAreaMapPlayersStatistics, new_log_file_path,
								E_LogViewerState.MapPlayersStatistics, new_tempTM);
					}
				}
			}
		});

		LogViewer.resetLogInfo(txtAreaLog, new_log_file_path, new_elog_viewer_state, new_tempTM);
		return panel;
	}

	/**
	 * Actually Map Grid contains buttons inside each cell of grid and on click
	 * of these button we perform certain actions this method implements logic
	 * behind the on click of button
	 * 
	 * @param new_button
	 *            Type JButton
	 * @param new_mapModel
	 *            Type MapModel
	 */
	private void addButtonClickEvents(JButton new_button, final MapModel new_mapModel) {
		new_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = ((JButton) e.getSource());
				String[] nameArry = btn.getName().split(":");
				int _i = Integer.parseInt(nameArry[1]);
				int _j = Integer.parseInt(nameArry[2]);

				// If the last point was a scenery Point
				if (new_mapModel.mapGridSelection[_i][_j] == ApplicationStatics.MAP_Scenery_POINT) {
					btn.setBackground(Color.green);
					new_mapModel.mapGridSelection[_i][_j] = ApplicationStatics.MAP_PATH_POINT;
				}
				// If the last point was a path point
				else if (new_mapModel.mapGridSelection[_i][_j] == ApplicationStatics.MAP_PATH_POINT) {
					new_mapModel.mapGridSelection[_i][_j] = ApplicationStatics.MAP_Scenery_POINT;
					btn.setBackground(Color.gray);
				}
				// the last point was an Entry point
				else if (new_mapModel.mapGridSelection[_i][_j] == ApplicationStatics.MAP_ENTRY_POINT) {
					new_mapModel.mapGridSelection[_i][_j] = ApplicationStatics.MAP_Scenery_POINT;
					btn.setBackground(Color.gray);
					btn.setText("");
					new_mapModel.isEntryDone = false;
					jButtonEntry = null;
				}
				// If the last point was an Exit Point
				else if (new_mapModel.mapGridSelection[_i][_j] == ApplicationStatics.MAP_EXIT_POINT) {
					new_mapModel.mapGridSelection[_i][_j] = ApplicationStatics.MAP_Scenery_POINT;
					btn.setBackground(Color.gray);
					btn.setText("");
					new_mapModel.isExitDone = false;
					jButtonExit = null;
				}

				logger.info("Button Click Event Btn Name : " + btn.getName());

				// -- ulan's code here
				String[] tempStr = btn.getName().split(":");
				int new_x = Integer.parseInt(tempStr[1]);
				int new_y = Integer.parseInt(tempStr[2]);

				Tower tempTM = TowerFactory.getTower("Shooter");

				logger.info("x : " + new_x + " , y : " + new_y);

				ApplicationStatics.SET_TOWER_DESCR_VISIBLE = false;

				if (bottomGamePanel != null && bottomGamePanel.towerDescrPanel != null) {
					bottomGamePanel.towerDescrPanel.updateTowerDscrPanel(tempTM);
				}
				ApplicationStatics.CURRENT_SELECTED_TOWER = 4;

				if (ApplicationStatics.HAS_BOUGHT_TOWER) {
					logger.info("Tower Placcement Button " + btn.getName() + " is clicked");
					if (!ApplicationStatics.PLAYERMODEL.towerModelArray.isEmpty()) {

						// -- sets the tower coordinates
						int arrSize = ApplicationStatics.PLAYERMODEL.towerModelArray.size();
						ApplicationStatics.PLAYERMODEL.towerModelArray.get(arrSize - 1).setXY(new_x, new_y);
						ApplicationStatics.HAS_BOUGHT_TOWER = false;
						setMapButtonsToYellow();
					} else {
						logger.info("Dont have towers");
					}
				} else {
					if (!ApplicationStatics.PLAYERMODEL.towerModelArray.isEmpty()) {

						for (int i = 0; i < ApplicationStatics.PLAYERMODEL.towerModelArray.size(); i++) {
							tempTM = ApplicationStatics.PLAYERMODEL.towerModelArray.get(i);

							if (new_x == tempTM.getX() && new_y == tempTM.getY()) {
								logger.info("HERE x=" + tempTM.getTowerName());
								ApplicationStatics.SET_TOWER_DESCR_VISIBLE = true;
								bottomGamePanel.towerDescrPanel.updateTowerDscrPanel(tempTM);
							}
						}
					}
				}

			}
		});
	}

	/**
	 * Actually Map Grid contains buttons inside each cell of grid and on right
	 * click on these button we perform certain actions this method implements
	 * logic behind the on right click of button
	 * 
	 * @param new_button
	 *            Type JButton
	 * @param new_mapModel
	 *            Type MapModel
	 */
	private void addMouseClickOnButtonEvents(JButton new_button, final MapModel new_mapModel) {
		new_button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				// When Right button Clicked of Mouse
				if (e.getButton() == MouseEvent.BUTTON1) {
				}
				// When Middle button Clicked of Mouse
				else if (e.getButton() == MouseEvent.BUTTON2) {
				}
				// When Left button Clicked of Mouse
				else if (e.getButton() == MouseEvent.BUTTON3) {
					JButton btn = ((JButton) e.getSource());
					String[] nameArry = btn.getName().split(":");
					int _i = Integer.parseInt(nameArry[1]);
					int _j = Integer.parseInt(nameArry[2]);

					// if the last point was an Entry Button
					if (new_mapModel.mapGridSelection[_i][_j] == ApplicationStatics.MAP_ENTRY_POINT) {
						// Change mapGridSelection value
						new_mapModel.mapGridSelection[_i][_j] = ApplicationStatics.MAP_Scenery_POINT;
						btn.setBackground(Color.gray);
						btn.setText("");
						jButtonEntry = null;
						new_mapModel.isEntryDone = false;
						new_mapModel.setEntryPoint(null);
					}
					// if the last point was an Exit Button
					else if (new_mapModel.mapGridSelection[_i][_j] == ApplicationStatics.MAP_EXIT_POINT) {
						new_mapModel.mapGridSelection[_i][_j] = ApplicationStatics.MAP_Scenery_POINT;
						btn.setBackground(Color.gray);
						btn.setText("");
						jButtonExit = null;
						new_mapModel.isExitDone = false;
						new_mapModel.setExitPoint(null);
					}
					// if the last point was Path and Scenery button
					else if (new_mapModel.mapGridSelection[_i][_j] == ApplicationStatics.MAP_Scenery_POINT
							|| new_mapModel.mapGridSelection[_i][_j] == ApplicationStatics.MAP_PATH_POINT) {
						// Check Entry is already Selected
						if (!new_mapModel.isEntryDone) {
							btn.setBackground(Color.RED);
							btn.setText("E");
							jButtonEntry = btn;
							new_mapModel.mapGridSelection[_i][_j] = ApplicationStatics.MAP_ENTRY_POINT;
							new_mapModel.setEntryPoint(new Point(_i, _j));
							new_mapModel.isEntryDone = true;
						} else if (!new_mapModel.isExitDone) {
							btn.setBackground(Color.RED);
							btn.setText("O");
							jButtonExit = btn;
							new_mapModel.mapGridSelection[_i][_j] = ApplicationStatics.MAP_EXIT_POINT;
							new_mapModel.setExitPoint(new Point(_i, _j));
							new_mapModel.isExitDone = true;
						} else {
							JOptionPane.showMessageDialog(null, "Both Enrty and Exit Point Already Selected");
							logger.info("Both Enrty and Exit Point Already Selected");
						}
					}
					logger.info(" Mouse Right Clicked Event Btn Name : " + btn.getName());
				}
			}
		});
	}

	/**
	 * This method sets the reference to bottom game panel
	 * 
	 * @param new_panel
	 *            the reference to object of BottomGamePanelView
	 */
	public void setBottomGamePanelView(BottomGamePanelView new_panel) {
		bottomGamePanel = new_panel;
	}

	/**
	 * @param new_jButtonTower
	 *            the jButtonTower to set
	 */
	public void setjButtonTower(JButton new_jButtonTower) {
		this.jButtonTower = new_jButtonTower;
	}

	/**
	 * @return the bottomGamePanel
	 */
	public BottomGamePanelView getBottomGamePanel() {
		return bottomGamePanel;
	}

	/**
	 * @return the mapButtonGrid2DArray
	 */
	public JButton[][] getMapButtonGrid2DArray() {
		return mapButtonsGrid2DArray;
	}

	/**
	 * @return the jButtonEntry
	 */
	public JButton getjButtonEntry() {
		return jButtonEntry;
	}

	/**
	 * @return the jButtonExit
	 */
	public JButton getjButtonExit() {
		return jButtonExit;
	}

	/**
	 * @return the jButtonTower
	 */
	public JButton getjButtonTower() {
		return jButtonTower;
	}

	/**
	 * @return the mapEditorMode
	 */
	public E_MapEditorMode getMapEditorMode() {
		return mapEditorMode;
	}

	/**
	 * update method from observable PlayerModel class
	 */
	@Override
	public void update(Observable new_o, Object new_arg) {
		// TODO Auto-generated method stub
		setMapButtonsToYellow();
	}

	/**
	 * This method sets the Buttons to Yellow on the map that are eligible for
	 * tower placement
	 * 
	 */
	public void setMapButtonsToYellow() {

		String stringMapCoord = "";
		// logger.info("inside setMapButtonsToYellow "+
		// ApplicationStatics.HAS_BOUGHT_TOWER);

		for (int i = 0; i < ApplicationStatics.MAP_BUTTONS.length; i++) {

			for (int j = 0; j < ApplicationStatics.MAP_BUTTONS[i].length; j++) {
				stringMapCoord = "" + (i) + ":" + j;
				if (ApplicationStatics.MAP_PATH_BOUNDARY_BUTTONS_NAME.contains(stringMapCoord)) {

					ApplicationStatics.MAP_BUTTONS[i][j].setEnabled(true);
					if (ApplicationStatics.HAS_BOUGHT_TOWER) {
						ApplicationStatics.MAP_BUTTONS[i][j]
								.setIcon(new ImageIcon(ApplicationStatics.IMAGE_PATH_MAP_BUTTONYELLOW));
						setTowersOnMap(i, j);
					} else {
						// -- sets all button icons to green scenery and later
						int x = ApplicationStatics.MAP_BUTTONS[i][j].getWidth();
						int y = ApplicationStatics.MAP_BUTTONS[i][j].getHeight();
						ApplicationStatics.MAP_BUTTONS[i][j]
								.setIcon(new ImageIcon(new ImageIcon(ApplicationStatics.IMAGE_PATH_MAP_SCENERY)
										.getImage().getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH)));

						setTowersOnMap(i, j);
					}
				} else { // -- disable buttons boundaries
					ApplicationStatics.MAP_BUTTONS[i][j].setEnabled(!ApplicationStatics.HAS_BOUGHT_TOWER);
					// ApplicationStatics.MAP_BUTTONS[i][j].setDisabledIcon(new
					// ImageIcon());
				}
			}
		}

	}

	/**
	 * This method sets icons on the map buttons where they have been placed by
	 * player
	 * 
	 * @param new_i
	 *            x coordinate of the button
	 * @param new_j
	 *            y coordinate of the button
	 */
	public void setTowersOnMap(int new_i, int new_j) {
		for (int k = 0; k < ApplicationStatics.PLAYERMODEL.towerModelArray.size(); k++) {
			int x = ApplicationStatics.PLAYERMODEL.towerModelArray.get(k).getX();
			int y = ApplicationStatics.PLAYERMODEL.towerModelArray.get(k).getY();
			if (new_i == x && new_j == y) {
				if (ApplicationStatics.HAS_BOUGHT_TOWER) {
					ApplicationStatics.MAP_BUTTONS[new_i][new_j].setEnabled(false);
				} else {
					ApplicationStatics.MAP_BUTTONS[new_i][new_j].setEnabled(true);
				}
				ApplicationStatics.MAP_BUTTONS[new_i][new_j]
						.setIcon(ApplicationStatics.PLAYERMODEL.towerModelArray.get(k).getTowerImage());
			}
		}

	}

	/**
	 * This function return Text area
	 * 
	 * @return JTextArea
	 */
	public JTextArea getJTextArea() {
		JTextArea jTextArea = new JTextArea(33, 100);
		// jTextArea.setText(new LogReader(logFilePath,
		// eLogViewerState.GlobalLog).read());
		jTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		jTextArea.setEditable(false);
		jTextArea.setLineWrap(true);
		jTextArea.setWrapStyleWord(true);
		return jTextArea;
	}

	/**
	 * This function return ScrollPane
	 * 
	 * @param new_jtextarea
	 *            JTextArea type
	 * @return JScrollPane
	 */
	public JScrollPane getJScrollPane(JTextArea new_jtextarea) {
		JScrollPane jScrollPane = new JScrollPane(new_jtextarea);
		jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		return jScrollPane;
	}
}
