package com.app.towerDefense.guiComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.app.towerDefense.guisystem.LogViewer;
import com.app.towerDefense.models.NearestToEndPointStrategy;
import com.app.towerDefense.models.NearestToTowerStrategy;
import com.app.towerDefense.models.StrongestStrategy;
import com.app.towerDefense.models.Tower;
import com.app.towerDefense.models.WeakestStrategy;
import com.app.towerDefense.staticContent.ApplicationStatics;
import com.app.towerDefense.staticContent.AppilicationEnums.E_LogViewerState;

/**
 * This class creates the view of tower description that shows the current
 * selected tower information
 * 
 * @author usbaitass
 * 
 */
public class TowerDescriptionPanel extends JPanel implements Observer, ActionListener {

	private static final long serialVersionUID = -2664654291872636064L;
	public JLabel towerLabelDESCR = new JLabel(new ImageIcon(ApplicationStatics.IMAGE_PATH_MAP_TOWER1));
	private JLabel[] labelStatsTower;
	private JLabel towerNameLabel;
	private JButton upgradeTowerButton;
	private JButton sellBuyTowerButton;
	private JButton strategyTowerButton;
	private JButton logTowerButton;
	private Tower tempTM;
	final static Logger logger = Logger.getLogger(TowerDescriptionPanel.class);

	/**
	 * Constructor
	 * 
	 * @param new_width
	 *            sets width by a given parameter
	 * @param new_height
	 *            sets height by a given parameter
	 */
	public TowerDescriptionPanel(int new_width, int new_height) {

		ApplicationStatics.SET_TOWER_DESCR_VISIBLE = false;
		ApplicationStatics.HAS_BOUGHT_TOWER = false;

		this.setMinimumSize(new Dimension(new_width, new_height));
		this.setMaximumSize(new Dimension(new_width, new_height));
		this.setPreferredSize(new Dimension(new_width, new_height));
		this.setBackground(new Color(205, 183, 158)); // BROWN

		// -- setting layout
		BorderLayout borderLayout = new BorderLayout();
		this.setLayout(borderLayout);

		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(new_width * 2 / 3, new_height));
		rightPanel.setPreferredSize(new Dimension(new_width * 1 / 3, new_height));

		leftPanel.setBackground(new Color(205, 183, 158)); // BROWN
		rightPanel.setBackground(new Color(205, 183, 158)); // BROWN

		GridLayout gridLayoutLeft = new GridLayout(6, 3);
		leftPanel.setLayout(gridLayoutLeft);

		// -- Adding Labels on Tower Description Panel
		// -- which displays the tower information
		labelStatsTower = new JLabel[18];

		for (int i = 0; i < 18; i++) {
			String s = Integer.toString(i);
			if (i == 0) {
				s = "Level";
			} else if (i == 3) {
				s = "Power";
			} else if (i == 6) {
				s = "Range";
			} else if (i == 9) {
				s = "Fire Rate";
			} else if (i == 12) {
				s = "Special";
			} else if (i == 15) {
				s = "Cost";
			}
			labelStatsTower[i] = new JLabel(s, SwingConstants.CENTER);
			labelStatsTower[i].setFont(new Font("Serif", Font.BOLD, 14));
			labelStatsTower[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
			labelStatsTower[i].setBackground(Color.WHITE);
			labelStatsTower[i].setOpaque(true);
			leftPanel.add(labelStatsTower[i]);
		}

		// --Adding 4 buttons to Tower Description Panel
		// -- setting layouts and secondary panels
		BorderLayout borderLayoutRight = new BorderLayout();
		rightPanel.setLayout(borderLayoutRight);

		// -- Labels that show the Name of the tower
		towerNameLabel = new JLabel("Tower Name", SwingConstants.CENTER);

		// -- initializing the Sell-Buy Tower Button
		// and implementing functionality
		sellBuyTowerButton = new JButton("BUY");
		sellBuyTowerButton.setPreferredSize(new Dimension(70, 20));
		sellBuyTowerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (sellBuyTowerButton.getText() == "BUY" && ApplicationStatics.CURRENT_SELECTED_TOWER < 4) {
					logger.info("BUY Button Clicked.");
					int tempTCost = ApplicationStatics.TOWER_MODELS[ApplicationStatics.CURRENT_SELECTED_TOWER]
							.getTowerCost();
					int currentBalance = ApplicationStatics.PLAYERMODEL.getSunCurrency();

					if (currentBalance >= tempTCost) { // -- true if BUY is
														// success
						Tower tempTM = ApplicationStatics.PLAYERMODEL
								.buyTower(ApplicationStatics.CURRENT_SELECTED_TOWER);
						if (tempTM != null) {

							JFrame frame = new JFrame();
							JOptionPane.showMessageDialog(frame,
									"You have bought a tower.\n Place it ONLY on the YELLOW Part on the map please.");
							logger.info(
									"You have bought a tower.\n Place it ONLY on the YELLOW Part on the map please.");
						}
					} else {
						JFrame frame = new JFrame();
						JOptionPane.showMessageDialog(frame, "You don't have enough suns for this tower.");
						logger.info("You don't have enough suns for this tower.");
					}
				} else {
					logger.info("Sell Button Clicked.");
					int temp_x = tempTM.getX();
					int temp_y = tempTM.getY();

					for (int k = 0; k < ApplicationStatics.PLAYERMODEL.towerModelArray.size(); k++) {
						int x = ApplicationStatics.PLAYERMODEL.towerModelArray.get(k).getX();
						int y = ApplicationStatics.PLAYERMODEL.towerModelArray.get(k).getY();
						if (temp_x == x && temp_y == y) {
							if (ApplicationStatics.PLAYERMODEL.sellTower(k)) {
								int w = ApplicationStatics.MAP_BUTTONS[x][y].getWidth();
								int h = ApplicationStatics.MAP_BUTTONS[x][y].getHeight();

								ApplicationStatics.MAP_BUTTONS[x][y]
										.setIcon(new ImageIcon(new ImageIcon(ApplicationStatics.IMAGE_PATH_MAP_SCENERY)
												.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH)));
								logger.info("tower deleted successfully.");

								ApplicationStatics.SET_TOWER_DESCR_VISIBLE = false;
								ApplicationStatics.CURRENT_SELECTED_TOWER = 0;
							} else {
								logger.info("could not delete tower.");
							}
						}
					}
				}
			}

		});

		logTowerButton = new JButton("Log");
		logTowerButton.setPreferredSize(new Dimension(70, 20));
		logTowerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				logger.info("Tower log button was clicked.");
				if (System.getProperty("os.name").contains("Windows")) {
					new LogViewer(null, ApplicationStatics.TITLE_LOG_VIEWER,
							ApplicationStatics.CHILD_POPUP_WINDOW_WIDTH, ApplicationStatics.CHILD_POPUP_WINDOW_HEIGHT,
							ApplicationStatics.LOG_File_PATH, E_LogViewerState.TowerLog, tempTM);
				} else {
					new LogViewer(null, ApplicationStatics.TITLE_LOG_VIEWER,
							ApplicationStatics.CHILD_POPUP_WINDOW_WIDTH, ApplicationStatics.CHILD_POPUP_WINDOW_HEIGHT,
							ApplicationStatics.LOG_File_PATH_iPhone, E_LogViewerState.TowerLog, tempTM);
				}
				;
			}
		});

		// -- initializing the tower upgrade button
		// -- and implementing its functionality
		upgradeTowerButton = new JButton("UPGRADE");
		upgradeTowerButton.setOpaque(true);
		upgradeTowerButton.setPreferredSize(new Dimension(70, 20));

		upgradeTowerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("upgrade button was clicked!");

				int balance = ApplicationStatics.PLAYERMODEL.getSunCurrency();

				if (balance >= tempTM.getTowerUpgradeCost()) {
					tempTM.upgradeTower();
					logger.info("Tower is upgraded successfully.");
					ApplicationStatics.PLAYERMODEL.subSunCurrency(tempTM.getTowerUpgradeCost());
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame,
							"You upgraded the tower successfully -" + tempTM.getTowerUpgradeCost() + " suns.");

				} else {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Not enough suns!");

				}

			}
		});

		// -- adding the labels and buttons on Tower Description Panel
		strategyTowerButton = new JButton("NearToEND");
		strategyTowerButton.setOpaque(true);
		strategyTowerButton.setPreferredSize(new Dimension(70, 20));
		strategyTowerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("strategy button was clicked!");

				if (strategyTowerButton.getText() == "NearToEND") {
					strategyTowerButton.setText("NearTower");
					tempTM.setStrategy(new NearestToTowerStrategy());
					logger.info("strategy changed to NearTower");
				} else if (strategyTowerButton.getText() == "NearTower") {
					strategyTowerButton.setText("Strongest");
					tempTM.setStrategy(new StrongestStrategy());
					logger.info("strategy changed to Strongest");
				} else if (strategyTowerButton.getText() == "Strongest") {
					strategyTowerButton.setText("Weakest");
					tempTM.setStrategy(new WeakestStrategy());
					logger.info("strategy changed to Weakest");
				} else if (strategyTowerButton.getText() == "Weakest") {
					strategyTowerButton.setText("NearToEND");
					tempTM.setStrategy(new NearestToEndPointStrategy());
					logger.info("strategy changed to NearToEND");
				}

			}
		});

		// -- initializing the tower description panel information to default
		updateTowerDscrPanel(ApplicationStatics.TOWER_MODELS[0]);

		JPanel botPanel = new JPanel();
		botPanel.setLayout(new BorderLayout());
		botPanel.add(strategyTowerButton, BorderLayout.WEST);
		botPanel.add(upgradeTowerButton, BorderLayout.EAST);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(sellBuyTowerButton, BorderLayout.WEST);
		topPanel.add(logTowerButton, BorderLayout.EAST);
		topPanel.setBackground(new Color(205, 183, 158)); // BROWN

		JPanel commonPanel = new JPanel();
		commonPanel.setLayout(new BorderLayout());
		commonPanel.add(topPanel, BorderLayout.NORTH);
		commonPanel.add(botPanel, BorderLayout.SOUTH);

		rightPanel.add(towerNameLabel, BorderLayout.NORTH);
		rightPanel.add(towerLabelDESCR, BorderLayout.CENTER);
		rightPanel.add(commonPanel, BorderLayout.SOUTH);

		this.add(leftPanel, BorderLayout.CENTER);
		this.add(rightPanel, BorderLayout.EAST);

	}

	/**
	 * updates the Tower Description Panel information when new tower is
	 * selected
	 * 
	 * @param new_tower_models
	 *            tower
	 */
	public void updateTowerDscrPanel(Tower new_tower_models) {

		tempTM = new_tower_models;

		labelStatsTower[1].setText(Integer.toString(new_tower_models.getTowerlevel()));
		labelStatsTower[4].setText(Integer.toString(new_tower_models.getTowerPower()));
		labelStatsTower[7].setText(Double.toString((double) new_tower_models.getTowerRange() / 100));
		labelStatsTower[10].setText(Integer.toString(new_tower_models.getTowerFireRate()));
		labelStatsTower[13].setText(new_tower_models.getSpecialEffect());
		labelStatsTower[16].setText(Integer.toString(new_tower_models.getTowerCost()));

		towerNameLabel.setText(new_tower_models.getTowerName());
		towerLabelDESCR.setIcon(new_tower_models.getTowerImage());

		// -- checks if tower is selected from the shop or the map

		if (ApplicationStatics.SET_TOWER_DESCR_VISIBLE) {
			labelStatsTower[2].setText(
					Integer.toString(new_tower_models.getTowerlevel() + new_tower_models.getTowerlevelUpgrade()));
			labelStatsTower[5].setText(
					Integer.toString(new_tower_models.getTowerPower() + new_tower_models.getTowerPowerUpgrade()));
			labelStatsTower[8].setText(Double.toString(
					(double) (new_tower_models.getTowerRange() + new_tower_models.getTowerFireRangeUpgrade()) / 100));
			labelStatsTower[11].setText(
					Integer.toString(new_tower_models.getTowerFireRate() + new_tower_models.getTowerFireRateUpgrade()));
			labelStatsTower[14].setText(new_tower_models.getSpecialEffect());
			labelStatsTower[17].setText(Integer.toString((int) (new_tower_models.getTowerUpgradeCost())));
			sellBuyTowerButton.setText("SELL");
			strategyTowerButton.setText(tempTM.getStrategy().getStrategyName());

		} else {
			labelStatsTower[2].setText("");
			labelStatsTower[5].setText("");
			labelStatsTower[8].setText("");
			labelStatsTower[11].setText("");
			labelStatsTower[14].setText("");
			labelStatsTower[17].setText("");
			sellBuyTowerButton.setText("BUY");

			upgradeTowerButton.setVisible(false);
			strategyTowerButton.setVisible(false);
		}

		labelStatsTower[2].setVisible(ApplicationStatics.SET_TOWER_DESCR_VISIBLE);
		labelStatsTower[5].setVisible(ApplicationStatics.SET_TOWER_DESCR_VISIBLE);
		labelStatsTower[8].setVisible(ApplicationStatics.SET_TOWER_DESCR_VISIBLE);
		labelStatsTower[11].setVisible(ApplicationStatics.SET_TOWER_DESCR_VISIBLE);
		labelStatsTower[14].setVisible(ApplicationStatics.SET_TOWER_DESCR_VISIBLE);
		labelStatsTower[17].setVisible(ApplicationStatics.SET_TOWER_DESCR_VISIBLE);
		upgradeTowerButton.setVisible(ApplicationStatics.SET_TOWER_DESCR_VISIBLE);
		strategyTowerButton.setVisible(ApplicationStatics.SET_TOWER_DESCR_VISIBLE);
		logTowerButton.setVisible(ApplicationStatics.SET_TOWER_DESCR_VISIBLE);

	}

	/**
	 * updates the tower description panel when notified from observable
	 * Playermodel or towerShopPanel
	 */
	@Override
	public void update(Observable new_o, Object new_arg) {

		int a = ApplicationStatics.CURRENT_SELECTED_TOWER;

		if (a < 4) {
			updateTowerDscrPanel(ApplicationStatics.TOWER_MODELS[a]);
		} else {

			updateTowerDscrPanel(tempTM);
		}

	}

	/**
	 * override functions of Observer Class
	 */
	@Override
	public void actionPerformed(ActionEvent new_e) {
		// Not needed

	}

	/**
	 * This method enables the sell buy button
	 * 
	 * @param new_value
	 *            takes true or false as a boolean value
	 */
	public void enableButtons(boolean new_value) {
		sellBuyTowerButton.setEnabled(new_value);
	}

}
