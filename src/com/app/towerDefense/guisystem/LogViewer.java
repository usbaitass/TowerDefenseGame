package com.app.towerDefense.guisystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.app.towerDefense.gameLogic.LogReader;
import com.app.towerDefense.guiComponents.JPanelComponent;
import com.app.towerDefense.models.Tower;
import com.app.towerDefense.staticContent.AppilicationEnums.E_LogViewerState;
import com.app.towerDefense.staticContent.ApplicationStatics;

/**
 * This is the Log Viewer class. It lets you view different types of log. There
 * is one universal log known as game logger. And the rest of the logs are
 * generated based on filtering logic using regex.
 * 
 * @author Sajjad Ashraf
 * 
 */
public class LogViewer extends JFrame {

	private static final long serialVersionUID = 1L;
	E_LogViewerState eLogViewerState;
	String logFilePath = "";
	static Color colors[] = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA };
	Timer timer;

	//
	private static JTextArea txtAreaLog;
	private static String filePath;
	private static E_LogViewerState elogViewerState;
	private static Tower tower;

	/**
	 * Constructor for the LogViewer
	 * 
	 * @param new_parent
	 *            the GameFrame
	 * @param new_title
	 *            game title
	 * @param new_width
	 *            frame width
	 * @param new_height
	 *            frame height
	 * @param new_log_file_path
	 *            file path of log
	 * @param new_elog_viewer_state
	 *            LogViewerState
	 * @param new_tower
	 *            tower
	 */
	public LogViewer(JFrame new_parent, String new_title, int new_width, int new_height, String new_log_file_path,
			E_LogViewerState new_elog_viewer_state, Tower new_tower) {
		// Set Window size
		if (new_parent != null) {
			Dimension parentSize = new_parent.getSize();
			Point p = new_parent.getLocation();
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}

		logFilePath = new_log_file_path;
		eLogViewerState = new_elog_viewer_state;

		// Set Title
		if (E_LogViewerState.GlobalLog == eLogViewerState) {
			new_title += " " + ApplicationStatics.LOG_VIEWER_MODE_GLOBLE;
		} else if (E_LogViewerState.CurrentSessionLog == eLogViewerState) {
			new_title += " " + ApplicationStatics.LOG_VIEWER_MODE_CURRENT_SESSION;
		} else if (E_LogViewerState.TowerLog == eLogViewerState) {
			new_title += " " + ApplicationStatics.LOG_VIEWER_MODE_TOWERS;
		} else if (E_LogViewerState.TowerCollectionLog == eLogViewerState) {
			new_title += " " + ApplicationStatics.LOG_VIEWER_MODE_TOWERS_COLLECTION;
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

		ApplicationStatics.isLogViewerOpen = true;
		// Tabs
		getContentPane()
				.add(new JPanelComponent().getLogViewerPanel(new_log_file_path, new_elog_viewer_state, new_tower));

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				ApplicationStatics.isLogViewerOpen = false;
				System.out.println(" Frame Closed! ");
			}
		});

		timer = new Timer();
		timer.schedule(new RemindTask(), 0, 1000);

	}

	/**
	 * This method resets log Info
	 * 
	 * @param new_txt_area_log
	 *            textArea
	 * @param new_log_file_path
	 *            log file path
	 * @param new_elog_viewer_state
	 *            E_LogViewerState
	 * @param new_tower
	 *            tower
	 */
	public static void resetLogInfo(JTextArea new_txt_area_log, String new_log_file_path,
			E_LogViewerState new_elog_viewer_state, Tower new_tower) {
		txtAreaLog = new_txt_area_log;
		filePath = new_log_file_path;
		elogViewerState = new_elog_viewer_state;
		tower = new_tower;
	}

	/**
	 * This method schedules a task to writing to TextArea by a timer
	 * 
	 * @author George Ekow-daniels {@inheritDoc}
	 */
	class RemindTask extends TimerTask {
		public void run() {
			if (ApplicationStatics.isLogViewerOpen) {
				txtAreaLog.setText(new LogReader(filePath, elogViewerState, tower).read());
				txtAreaLog.setCaretPosition(txtAreaLog.getDocument().getLength());
			} else {
				timer.cancel();
			}
		}
	}
}
