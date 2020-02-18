package com.app.towerDefense.guiComponents;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.app.towerDefense.gameLogic.GameLoader;
import com.app.towerDefense.gameLogic.GameSaver;
import com.app.towerDefense.gameLogic.Map;
import com.app.towerDefense.models.BasicCritter;
import com.app.towerDefense.models.CritterFactory;
import com.app.towerDefense.models.CritterType;
import com.app.towerDefense.models.MapModel;
import com.app.towerDefense.models.PlayerModel;
import com.app.towerDefense.staticContent.ApplicationStatics;

/**
 * Draws elements on map
 * 
 * @author usbaitass
 * 
 */
public class MapPanel extends JPanel {

	private static final long serialVersionUID = -9082005090002375868L;

	boolean isInitialCond = true;
	// Critter entry point co-ordinates
	int xEntry = 0;
	int yEntry = 0;

	// Critter Exit point co-ordinates
	int xExit = 0;
	int yExit = 0;

	// To release multiple critters in the game
	long multipleCriiterCounter = 0;

	MapModel mapModel;
	// Variable to create number of critter based on game levels
	int wave = 2;
	public static ArrayList<CritterType> critter = new ArrayList<CritterType>();

	public static Graphics graphics;
	final static Logger logger = Logger.getLogger(MapPanel.class);

	/**
	 * Parameterized constructor for Map Panel
	 * 
	 * @param new_mapModel
	 *            map model object
	 */
	public MapPanel(MapModel new_mapModel) {
		mapModel = new_mapModel;
	}

	/**
	 * Creates critter objects and calculates the path on which critter has to
	 * move
	 * 
	 * @param new_graphics
	 */
	@Override
	protected void paintComponent(Graphics new_graphics) {
		try {
			graphics = new_graphics;
			if (!ApplicationStatics.GAME_OVER) {
				if (ApplicationStatics.START_WAVE) {

					if (isInitialCond) {
						panelInit();
						for (int i = 0; i < wave; i++) {
							critter.add(CritterFactory.getCritterfromFactory("BasicCritter"));
							critter.get(i).setXY(xEntry, yEntry);

							critter.get(i).setID(i);
						}
						isInitialCond = false;
					}

					super.paintComponent(new_graphics);
					if (critter.size() > 0) {

						for (int i = 0; i < critter.size(); i++) {
							if (critter.size() > 0 && critter.get(i).calculatePath()) {

								// drawing critters
								new_graphics.drawImage(critter.get(i).getCritterImage(), critter.get(i).getX(),
										critter.get(i).getY(), 30, 30, null);

								// splash effect drawing
								if (critter.get(i).getShowSplashArea()) {
									Ellipse2D ellipse2 = new Ellipse2D.Double(critter.get(i).getX() - 27,
											critter.get(i).getY(), ApplicationStatics.BLOCK_WIDTH,
											ApplicationStatics.BLOCK_HEIGHT);
									Graphics2D g3 = (Graphics2D) graphics;
									((Graphics2D) graphics).setStroke(new BasicStroke(1));
									graphics.setColor(Color.RED);
									g3.draw(ellipse2);
								}

								// critter health bar drawing
								graphics.setColor(Color.GREEN);
								((Graphics2D) graphics).setStroke(new BasicStroke(5));
								graphics.drawLine((critter.get(i)).getHealthBar().x, (critter.get(i)).getHealthBar().y,
										(critter.get(i)).getHealthBar().xMid, (critter.get(i)).getHealthBar().y);
								if (critter.get(i).getHealthBar().xMid != critter.get(i).getHealthBar().xEnd) {
									graphics.setColor(Color.RED);
									graphics.drawLine((critter.get(i)).getHealthBar().xMid,
											(critter.get(i)).getHealthBar().y, (critter.get(i)).getHealthBar().xEnd,
											(critter.get(i)).getHealthBar().y);
								}

								if (multipleCriiterCounter < (i + 1) * 15) {
									break;
								}
							} else {
								if (ApplicationStatics.PLAYERMODEL.decrementHealth(1)) {
									logger.info("Play smarter, you still have a chance!");
									logger.info("Play smarter, you still have a chance!");
								} else {
									ApplicationStatics.GAME_OVER = true;
									break;
								}
								critter.remove(i);
								i = 0;
							}

						}
					}

					// drawing tower range circles
					for (int k = 0; k < PlayerModel.towerModelArray.size(); k++) {
						double xt = PlayerModel.towerModelArray.get(k).getXT();
						double yt = PlayerModel.towerModelArray.get(k).getYT();
						double dW = PlayerModel.towerModelArray.get(k).getDTW();
						double dH = PlayerModel.towerModelArray.get(k).getDTH();
						Ellipse2D ellipse = new Ellipse2D.Double(xt, yt, dW, dH);
						Graphics2D g2 = (Graphics2D) graphics;
						((Graphics2D) graphics).setStroke(new BasicStroke(1));
						graphics.setColor(Color.BLACK);
						g2.draw(ellipse);
					}

					multipleCriiterCounter++;

					if (critter.size() == 0 && !ApplicationStatics.GAME_OVER) {
						if (ApplicationStatics.PLAYERMODEL.getHpPlayer() == 0
								|| ApplicationStatics.PLAYERMODEL.getGameWave() == 2) {
							ApplicationStatics.GAME_OVER = true;
							new Map().saveMapLog();
						} else {
							logger.info("Wave end.");
							ApplicationStatics.START_WAVE = false;
							isInitialCond = true;
							multipleCriiterCounter = 0;
							ApplicationStatics.PLAYERMODEL.incGameWave();
							new Map().saveMapLog();
							logger.info("Next Wave : " + ApplicationStatics.PLAYERMODEL.getGameWave() + " Started.");

						}
					}

				}
			}

		} catch (IndexOutOfBoundsException e) {
			e.getMessage();
		}

	}

	/**
	 * Draws the shooting lines from tower to critter
	 * 
	 * @param new_tx
	 *            tower x coordinate
	 * @param new_ty
	 *            tower y coordinate
	 * @param new_cx
	 *            critter x coordinate
	 * @param new_cy
	 *            critter y coordinate
	 * @param new_tower_types
	 *            tower type number
	 * @param new_critterId
	 *            current critter id
	 */
	public static void drawLines(int new_tx, int new_ty, int new_cx, int new_cy, String new_tower_types,
			int new_critterId) {

		if (graphics != null) {
			// graphics = (Graphics2D) graphics;
			if (new_tower_types == "Burner") {
				graphics.setColor(Color.RED);
				// ((Graphics2D) graphics).setStroke(new BasicStroke(10));
			} else if (new_tower_types == "Freezer") {
				graphics.setColor(Color.BLUE);
				// ((Graphics2D) graphics).setStroke(new BasicStroke(10));
			} else if (new_tower_types == "Splasher") {
				graphics.setColor(Color.YELLOW);
				// ((Graphics2D) graphics).setStroke(new BasicStroke(10));
			} else if (new_tower_types == "Shooter") {
				graphics.setColor(Color.GREEN);
				// ((Graphics2D) graphics).setStroke(new BasicStroke(10));
			}

			((Graphics2D) graphics).setStroke(new BasicStroke(3));

			graphics.drawLine(new_tx, new_ty, new_cx, new_cy);

		}
	}

	/**
	 * This method iniitializes the route in the map panel for critters
	 */
	public void panelInit() {
		if (mapModel != null) {
			ApplicationStatics.PATH_ARRAY1 = mapModel.getMapRoutPathList();
			// logger.info("Map Model is not null");

			xEntry = ApplicationStatics.PATH_ARRAY1.get(0).y * ApplicationStatics.BLOCK_WIDTH;
			yEntry = ApplicationStatics.PATH_ARRAY1.get(0).x * ApplicationStatics.BLOCK_HEIGHT;

			xExit = ApplicationStatics.PATH_ARRAY1.get(ApplicationStatics.PATH_ARRAY1.size() - 1).y
					* ApplicationStatics.BLOCK_WIDTH;
			yExit = ApplicationStatics.PATH_ARRAY1.get(ApplicationStatics.PATH_ARRAY1.size() - 1).x
					* ApplicationStatics.BLOCK_HEIGHT;

			/*
			 * for (int k = 0; k < ApplicationStatics.PATH_ARRAY1.size(); k +=
			 * 1) { logger.info(k + " : x=" +
			 * ApplicationStatics.PATH_ARRAY1.get(k).x + " , y=" +
			 * ApplicationStatics.PATH_ARRAY1.get(k).y); }
			 */

			wave = 2 * ApplicationStatics.PLAYERMODEL.getGameWave();

		}
	}

}
