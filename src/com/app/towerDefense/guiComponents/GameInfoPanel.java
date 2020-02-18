package com.app.towerDefense.guiComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.apache.log4j.Logger;
import com.app.towerDefense.models.CritterFactory;
import com.app.towerDefense.models.CritterType;
import com.app.towerDefense.models.PlayerModel;
import com.app.towerDefense.staticContent.ApplicationStatics;

/**
 * This class creates view of the Game information panel and all elements on it
 * 
 * @author usbaitass
 * 
 */
public class GameInfoPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 8847617647575898521L;
	private JLabel sunCurrencyLabel;
	public JButton startWaveButton;
	private JLabel hpLabel;
	private JLabel waveLabel;
	private JLabel critterLabel;
	private JButton critterIconButton;
	private CritterType tempCritter;

	final static Logger logger = Logger.getLogger(GameInfoPanel.class);
	/**
	 * Constructor
	 * 
	 * @param new_width
	 *            the width parameter given by its parent
	 * @param new_height
	 *            the height parameter given by its parent
	 */
	public GameInfoPanel(int new_width, int new_height) {

		this.setMinimumSize(new Dimension(new_width, new_height));
		this.setMaximumSize(new Dimension(new_width, new_height));
		this.setPreferredSize(new Dimension(new_width, new_height));

		// -- setting layout and secondary panels
		BorderLayout borderLayout = new BorderLayout();
		this.setLayout(borderLayout);
		JPanel leftInfoPanel = new JPanel();
		JPanel rightInfoPanel = new JPanel();
		leftInfoPanel
				.setPreferredSize(new Dimension(new_width / 3, new_height));
		rightInfoPanel.setPreferredSize(new Dimension(new_width * 2 / 3,
				new_height));
		leftInfoPanel.setBackground(new Color(205, 183, 158)); // BROWN
		rightInfoPanel.setBackground(new Color(205, 183, 158)); // BROWN
		leftInfoPanel.setLayout(new FlowLayout());
		// creating and setting labels and buttons on Game Info Panel
		JLabel sunButton = new JLabel(new ImageIcon(
				ApplicationStatics.IMAGE_PATH_MAP_SUN));
		sunCurrencyLabel = new JLabel("Sun "
				+ ApplicationStatics.PLAYERMODEL.getSunCurrency());
		sunCurrencyLabel.setFont(new Font("Serif", Font.BOLD, 13));

		// -- Label that show the health status of the player
		hpLabel = new JLabel("HP "
				+ ApplicationStatics.PLAYERMODEL.getHpPlayer());
		hpLabel.setFont(new Font("Serif", Font.BOLD, 13));
		hpLabel.setForeground(Color.RED);

		startWaveButton = new JButton("Start");
		startWaveButton.setVisible(true);
		startWaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent new_event) {

				logger.info("Wave is started "+ApplicationStatics.PLAYERMODEL.getGameWave()+".");
				if (!ApplicationStatics.START_WAVE) {
					ApplicationStatics.START_WAVE = true;
			
				}
			}
		});

		leftInfoPanel.add(sunButton);
		leftInfoPanel.add(sunCurrencyLabel);
		leftInfoPanel.add(hpLabel);
		leftInfoPanel.add(startWaveButton);

		// -- label that shows game wave number
		waveLabel = new JLabel("WAVE: "
				+ ApplicationStatics.PLAYERMODEL.getGameWave());
		waveLabel.setFont(new Font("Serif", Font.BOLD, 12));
		waveLabel.setHorizontalAlignment(JLabel.CENTER);

		tempCritter = CritterFactory
				.getCritterfromFactory("BasicCritter");
		String s = "<html>Critter: Zomby x"
				+ ApplicationStatics.PLAYERMODEL.getGameWave() * 2
				+ "<br>Health: " + tempCritter.getActualHealth() + "</html>";
		critterLabel = new JLabel(s, SwingConstants.CENTER);

		// -- button that displays the critter image as background image
		critterIconButton = new JButton(new ImageIcon(
				ApplicationStatics.IMAGE_PATH_MAP_CRITTER1));
		critterIconButton.setDisabledIcon(new ImageIcon(
				ApplicationStatics.IMAGE_PATH_MAP_CRITTER1));
		critterIconButton.setEnabled(false);

		// -- adding labels and buttons on Game info Panel
		rightInfoPanel.add(waveLabel);
		rightInfoPanel.add(critterLabel);
		rightInfoPanel.add(critterIconButton);

		this.add(leftInfoPanel, BorderLayout.WEST);
		this.add(rightInfoPanel, BorderLayout.EAST);
		
		logger.info("Sun "+ ApplicationStatics.PLAYERMODEL.getSunCurrency()+", "
				+"HP :"+ApplicationStatics.PLAYERMODEL.getHpPlayer()+", "
				+"WAVE: " + ApplicationStatics.PLAYERMODEL.getGameWave()+", "
				+ "Critter: Zomby x"+ (ApplicationStatics.PLAYERMODEL.getGameWave() * 2)+", "
				+ "Health: " + tempCritter.getActualHealth()+".");

	}

	/**
	 * updates all info on the player if any changes occur on his behalf
	 */
	@Override
	public void update(Observable new_o, Object new_arg) {
		// TODO Auto-generated method stub
		sunCurrencyLabel.setText("Sun "
				+ ((PlayerModel) new_o).getSunCurrency());
		hpLabel.setText("HP " + ((PlayerModel) new_o).getHpPlayer());
		waveLabel.setText("WAVE: " + ((PlayerModel) new_o).getGameWave());
		String s = "<html>Critter: Zomby x"
				+ ApplicationStatics.PLAYERMODEL.getGameWave() * 2
				+ "<br>Health: " + tempCritter.getActualHealth() + "</html>";
		critterLabel.setText(s);
		logger.info("Sun :"+ ((PlayerModel) new_o).getSunCurrency()+", "
				+"HP :"+((PlayerModel) new_o).getHpPlayer()+", "
				+"WAVE: " + ((PlayerModel) new_o).getGameWave()+", "
				+ "Critter: Zomby x"+ (ApplicationStatics.PLAYERMODEL.getGameWave() * 2)+", "
				+ "Health: " + tempCritter.getActualHealth()+".");
	}

}
