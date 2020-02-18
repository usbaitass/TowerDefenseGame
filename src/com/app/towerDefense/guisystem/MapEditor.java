package com.app.towerDefense.guisystem;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import com.app.towerDefense.guiComponents.JMenuBarComponent;
import com.app.towerDefense.guiComponents.JPanelComponent;
import com.app.towerDefense.staticContent.AppilicationEnums.*;
import com.app.towerDefense.models.MapModel;
import com.app.towerDefense.staticContent.ApplicationStatics;

/**
 * This is the map editor class
 * 
 * @author Sajjad Ashraf
 * 
 */
public class MapEditor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MapModel mapModel;
	E_MapEditorMode mapEditorMode;// enum

	/**
	 * Map editor Constructor
	 * 
	 * @param new_parent
	 *            JFrame
	 * @param new_title
	 *            is the title of the frame
	 * @param new_width
	 *            is the width of the frame
	 * @param new_height
	 *            is the height of the frame
	 * @param new_mapModel
	 *            is a map
	 * @param new_mapEditorMode
	 *            is the map editor mode
	 */
	public MapEditor(JFrame new_parent, String new_title, int new_width, int new_height, MapModel new_mapModel,
			E_MapEditorMode new_mapEditorMode) {
		if (new_parent != null) {
			Dimension parentSize = new_parent.getSize();
			Point p = new_parent.getLocation();
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}

		mapModel = new_mapModel;
		mapEditorMode = new_mapEditorMode;

		if (E_MapEditorMode.Create == mapEditorMode) {
			new_title += " " + ApplicationStatics.MAP_MODE_CREATE;
		} else {
			new_title += " " + ApplicationStatics.MAP_MODE_OPEN;
		}

		// --- Set Map Editor Windows Properties
		this.setTitle(new_title);
		this.setPreferredSize(new Dimension(new_width, new_height));
		this.setMaximumSize(new Dimension(new_width, new_height));
		this.setMinimumSize(new Dimension(new_width, new_height));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);

		// -- Load MenuBar From Components
		this.setJMenuBar((new JMenuBarComponent()).getMapEditorJMenuBar(new_mapModel, this));
		// -- Load GridEditor Panel From Components
		this.setContentPane((new JPanelComponent()).getMapEditorGridPanel(mapModel, null, mapEditorMode));

	}

}
