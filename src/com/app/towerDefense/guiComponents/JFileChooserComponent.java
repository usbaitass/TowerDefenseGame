package com.app.towerDefense.guiComponents;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.app.towerDefense.staticContent.AppilicationEnums.E_JFileChooserMode;

/**
 * This class allows us to select a file from system.
 * 
 * @author Sajjad Ashraf
 * 
 */
public class JFileChooserComponent {

	private JFileChooser fileChooser;

	/**
	 * The Method gives you JFileChooser Save or Open mode according to provided
	 * input.
	 * 
	 * @param new_fileChooserMode
	 *            the mode of a file open and on the base of this argument it
	 *            behave accordingly.
	 * @return a file
	 */
	public JFileChooser getJFileChooser(E_JFileChooserMode new_fileChooserMode) {
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setAcceptAllFileFilterUsed(false);

		if (new_fileChooserMode == E_JFileChooserMode.MapPlay) {
			fileChooser.setDialogTitle("Tower Defense Select .tdm file");
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Tower Defence Map", "tdm"));
		} else if (new_fileChooserMode == E_JFileChooserMode.MapOpen) {
			fileChooser.setDialogTitle("Tower Defense Select .tdm file");
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Tower Defence Map", "tdm"));
		} else if (new_fileChooserMode == E_JFileChooserMode.MapSave) {
			fileChooser.setDialogTitle("Tower Defence Map Save");
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Tower Defence Map", "tdm"));
		} else if (new_fileChooserMode == E_JFileChooserMode.GameLoad) {
			fileChooser.setDialogTitle("Tower Defence Game Load");
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Tower Defence Game", "tdg"));
		} else if (new_fileChooserMode == E_JFileChooserMode.GameSave) {
			fileChooser.setDialogTitle("Tower Defence Game Save");
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Tower Defence Game", "tdg"));
		}

		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		return fileChooser;
	}

	/**
	 * @return the fileChooser
	 */
	public JFileChooser getFileChooser() {
		return fileChooser;
	}

}
