package com.app.towerDefense.guiComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import com.app.towerDefense.staticContent.ApplicationStatics;

/**
 * 
 * @author usbaitass Ulan Baitassov
 *         <p>
 *         Class description:<br>
 *         creates the Panel on which the game information is displayed: such as
 *         tower shop, tower description panel, player's amount of currency and
 *         health, the wave and critter information
 *         </p>
 */
public class BottomGamePanelView extends JPanel {

	private static final long serialVersionUID = 8434137027978433069L;
	// -- Class attributes
	private int width, height;
	public TowerShopPanel towerShopPanel;
	public GameInfoPanel infoPanel;
	public TowerDescriptionPanel towerDescrPanel;

	/**
	 * The constructor
	 * 
	 * @param new_width
	 *            width of the frame
	 * @param new_height
	 *            height of the frame
	 */
	public BottomGamePanelView(int new_width, int new_height) {

		this.width = new_width;
		this.height = new_height;

		// ---BOTTOM-GAME-PANEL-VIEW-- setting Dimensions and layout
		this.setMinimumSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setBackground(new Color(205, 183, 158)); // BROWN
		BorderLayout borderLayout = new BorderLayout();
		this.setLayout(borderLayout);

		// ------THREE----PANELS-------------------------------
		towerShopPanel = new TowerShopPanel(width / 4, height);
		infoPanel = new GameInfoPanel(width / 4, height);
		towerDescrPanel = new TowerDescriptionPanel(width / 2, height);

		this.add(infoPanel, BorderLayout.WEST);
		this.add(towerDescrPanel, BorderLayout.CENTER);
		this.add(towerShopPanel.getPanel(), BorderLayout.EAST);

		// Link observers and observable objects
		towerShopPanel.addObserver(towerDescrPanel);
		ApplicationStatics.PLAYERMODEL.addObserver(infoPanel);
		ApplicationStatics.PLAYERMODEL.addObserver(towerDescrPanel);
	}
	// END
}
