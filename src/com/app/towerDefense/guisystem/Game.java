package com.app.towerDefense.guisystem;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import com.app.towerDefense.guiComponents.BottomGamePanelView;
import com.app.towerDefense.guiComponents.JMenuBarComponent;
import com.app.towerDefense.guiComponents.JPanelComponent;
import com.app.towerDefense.models.PlayerModel;
import com.app.towerDefense.staticContent.ApplicationStatics;
import com.app.towerDefense.utilities.MiscellaneousHelper;

/**
 * 
 * @author usbaitass
 * 
 *         Main Class that initiates the creation of the project Applied the
 *         Singleton Pattern on Game Class.
 */
public class Game extends Canvas implements Runnable { // change 1

	private static final long serialVersionUID = 1324355855108644765L;

	// Added logging function
	final static Logger logger = Logger.getLogger(Game.class);

	private static Game instance = new Game();

	private int width;
	private int height;
	private String title;

	private static JMenuBarComponent jMenuBarComponent;
	private JMenuBar gameJMenuBar;
	private BottomGamePanelView bottomGamePanel;
	private JPanelComponent panelComponent;
	private JFrame frame;

	// THREAD
	private Thread thread;
	private boolean running = false;

	/**
	 * Constructor of the Game Class
	 */
	private Game() {
		// Added logging function
		ApplicationStatics.setLog_Current_Session_Tag(new MiscellaneousHelper().getCurrentDateStr());
		logger.info(ApplicationStatics.getLog_Current_Session_Tag());

		// Game frame settings
		width = ApplicationStatics.WINDOW_WIDTH;
		height = ApplicationStatics.WINDOW_HEIGHT;
		ApplicationStatics.PLAYERMODEL = new PlayerModel();
		title = ApplicationStatics.getTitleGameWindow();

		frame = new JFrame();
		frame.setTitle(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); // center window on the screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().removeAll();
		frame.setLayout(new BorderLayout());
		jMenuBarComponent = new JMenuBarComponent();
		gameJMenuBar = jMenuBarComponent.getGameJMenuBar(frame);
		frame.setJMenuBar(gameJMenuBar);
		frame.setVisible(true);
		logger.info("Game Loaded!!");

	}

	/**
	 * This method refreshes the game frame Title
	 */
	public void refreshGameFrameTitle() {
		title = ApplicationStatics.getTitleGameWindow();
		frame.setTitle(title);
	}

	/**
	 * If instance was not previously created, then created one and return the
	 * instance.
	 * 
	 * @return game object - instance
	 */
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}

	/**
	 * Main Method of the class that creates the Game Instance and starts the
	 * game.
	 * 
	 * @param new_args
	 *            contains the supplied command-line arguments as an array of
	 *            String objects
	 */
	public static void main(String new_args[]) {
		Game.getInstance().start();
	}

	/**
	 * Game loop - updates the game. This method runs the thread
	 */
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 20.0; // Game speed 5.0
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				if (ApplicationStatics.GAME_OVER) {
					stop();
					break;
				}
				delta--;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
		stop();

	}

	/**
	 * This method starts the thread
	 */
	public synchronized void start() {
		thread = new Thread(this);
		running = true;
		thread.start();
	}

	/**
	 * This method stops the thread
	 */
	public synchronized void stop() {
		try {
			thread.join(0); // stops the thread
			System.out.println("Game loop is stopped.");
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION HERE void stop function.");
		}
	}

	/**
	 * Drawing on map panel
	 */
	@SuppressWarnings("static-access")
	private void tick() {

		if (!ApplicationStatics.GAME_OVER) { // check if game is over

			if ((panelComponent = jMenuBarComponent.getPanelComponent()) != null) {
				if (panelComponent.mapPanel == null) {
					try {
						thread.sleep(500);
					} catch (InterruptedException e) {

					}
				}
				panelComponent.mapPanel.revalidate();
				panelComponent.mapPanel.repaint();

				bottomGamePanel = jMenuBarComponent.getBottomPanel();

				if (ApplicationStatics.START_WAVE && bottomGamePanel != null) {
					bottomGamePanel.towerShopPanel.enableTowerButtons(false);
					bottomGamePanel.towerDescrPanel.enableButtons(false);
					bottomGamePanel.infoPanel.startWaveButton.setEnabled(false);
				} else if (!ApplicationStatics.START_WAVE && bottomGamePanel != null) {
					bottomGamePanel.towerShopPanel.enableTowerButtons(true);
					bottomGamePanel.towerDescrPanel.enableButtons(true);
					bottomGamePanel.infoPanel.startWaveButton.setEnabled(true);
				}
			}

		} else {

			String tempStr = "";
			if (ApplicationStatics.PLAYERMODEL.getHpPlayer() <= 0) {
				tempStr = "You lose, game over!";
			} else {
				tempStr = "You win!";
			}
			JFrame frame = new JFrame();

			JOptionPane.showMessageDialog(frame, tempStr);
			Game.getInstance().stop();
			logger.info("Game over: Result : " + tempStr);
		}

	}

	// END
}
