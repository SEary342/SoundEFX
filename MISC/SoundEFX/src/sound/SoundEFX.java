package sound;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.filechooser.FileFilter;

/**
 * This is a sound effects package to be used in concert with a Logitech
 * G13 keypad.
 * 
 * @author Sam Eary
 * @version 1.2.1
 */
public class SoundEFX implements ActionListener, KeyListener {
	private static final String FILEBASE = "SoundFiles/";
	private static final String CONFIGBASE = "SoundConfigs/";
	private static final Font titleFont = new Font("Impact",
			Font.PLAIN, 48);
	private static final Font standard = new Font("Courant", Font.BOLD,
			18);
	protected static final int KEYNUM = 25;
	private JPanel panel;
	private JFrame mainFrame;
	private Engine engine;
	private ArrayList<JButtonExtra> buttons;
	private JButton stop;
	private JMenuItem newConfig, saveConfig, loadConfig, quit,
			changeSound, about;
	private JLabel title;;

	public SoundEFX() {
		engine = new Engine(KEYNUM);
		mainFrame = new JFrame("SoundEFX");
		mainFrame.setLayout(new BorderLayout());
		buttons = new ArrayList<JButtonExtra>();
		title = new JLabel("SoundEFX");
		panel = new JPanel();

		mainFrame.add(panel, BorderLayout.CENTER);
		int idNum = 65;
		for (int i = 0; i < KEYNUM; i++) {
			buttons.add(new JButtonExtra(idNum, "G" + (i + 1)));
			buttons.get(i).addActionListener(this);
			buttons.get(i).addKeyListener(this);
			format(buttons.get(i));
			idNum++;
		}
		stop = new JButton("Stop");
		stop.addActionListener(this);
		createPanel();
		format(title);
		title.setFont(titleFont);
		format(panel);

		format(stop);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupMenus();
		mainFrame
				.setIconImage(new ImageIcon("MISC/spk.png").getImage());
		mainFrame.setResizable(false);
		mainFrame.setUndecorated(true);
		MoveMouseListener mml = new MoveMouseListener(
				(JComponent) title);
		title.addMouseListener(mml);
		title.addMouseMotionListener(mml);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	private void format(Component comp) {
		comp.setBackground(Color.BLACK);
		comp.setForeground(Color.WHITE);
		comp.setFont(standard);
	}

	private void setupMenus() {
		JMenuBar menu = new JMenuBar();
		JMenu operation = new JMenu("File");
		JMenu options = new JMenu("Options");
		format(menu);
		format(operation);
		format(options);

		// Adds the menu bar and changes its color.
		mainFrame.setJMenuBar(menu);
		menu.add(operation);
		menu.add(Box.createGlue());
		menu.add(title);
		menu.add(Box.createGlue());
		menu.add(options);

		newConfig = new JMenuItem("New Configuration");
		saveConfig = new JMenuItem("Save Configuration");
		loadConfig = new JMenuItem("Load Configuration");
		quit = new JMenuItem("Quit");
		changeSound = new JMenuItem("Change Sound");
		about = new JMenuItem("About");
		format(newConfig);
		format(saveConfig);
		format(loadConfig);
		format(quit);
		format(changeSound);
		format(about);
		// Creates the operation menu.
		operation.add(newConfig);
		newConfig.addActionListener(this);
		operation.add(saveConfig);
		saveConfig.addActionListener(this);
		operation.add(loadConfig);
		loadConfig.addActionListener(this);
		options.add(changeSound);
		changeSound.addActionListener(this);
		operation.add(quit);
		quit.addActionListener(this);
		options.add(about);
		about.addActionListener(this);
	}

	private void createPanel() {
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);

		// horizontal links
		layout.linkSize(SwingConstants.VERTICAL, buttons.get(0),
				buttons.get(1), buttons.get(2), buttons.get(3),
				buttons.get(4), buttons.get(5), buttons.get(6));
		layout.linkSize(SwingConstants.VERTICAL, buttons.get(7),
				buttons.get(8), buttons.get(9), buttons.get(10),
				buttons.get(11), buttons.get(12), buttons.get(13));
		layout.linkSize(SwingConstants.VERTICAL, buttons.get(14),
				buttons.get(15), buttons.get(16), buttons.get(17),
				buttons.get(18));
		layout.linkSize(SwingConstants.VERTICAL, buttons.get(19),
				buttons.get(20), buttons.get(21), stop);

		// vertical links
		layout.linkSize(SwingConstants.HORIZONTAL, buttons.get(0),
				buttons.get(7));
		layout.linkSize(SwingConstants.HORIZONTAL, buttons.get(1),
				buttons.get(8), buttons.get(14));
		layout.linkSize(SwingConstants.HORIZONTAL, buttons.get(2),
				buttons.get(9), buttons.get(15), buttons.get(19));
		layout.linkSize(SwingConstants.HORIZONTAL, buttons.get(3),
				buttons.get(10), buttons.get(16), buttons.get(20));
		layout.linkSize(SwingConstants.HORIZONTAL, buttons.get(4),
				buttons.get(11), buttons.get(17), buttons.get(21));
		layout.linkSize(SwingConstants.HORIZONTAL, buttons.get(5),
				buttons.get(12), buttons.get(18));
		layout.linkSize(SwingConstants.HORIZONTAL, buttons.get(6),
				buttons.get(13), buttons.get(15), stop);

		GroupLayout.SequentialGroup vGroup = layout
				.createSequentialGroup();
		vGroup.addGroup(layout.createParallelGroup(Alignment.CENTER)
				.addComponent(buttons.get(0))
				.addComponent(buttons.get(1))
				.addComponent(buttons.get(2))
				.addComponent(buttons.get(3))
				.addComponent(buttons.get(4))
				.addComponent(buttons.get(5))
				.addComponent(buttons.get(6)));
		vGroup.addGroup(layout.createParallelGroup(Alignment.CENTER)
				.addComponent(buttons.get(7))
				.addComponent(buttons.get(8))
				.addComponent(buttons.get(9))
				.addComponent(buttons.get(10))
				.addComponent(buttons.get(11))
				.addComponent(buttons.get(12))
				.addComponent(buttons.get(13)));
		vGroup.addGroup(layout.createParallelGroup(Alignment.CENTER)
				.addComponent(buttons.get(14))
				.addComponent(buttons.get(15))
				.addComponent(buttons.get(16))
				.addComponent(buttons.get(17))
				.addComponent(buttons.get(18)));
		vGroup.addGroup(layout.createParallelGroup(Alignment.CENTER)
				.addComponent(buttons.get(19))
				.addComponent(buttons.get(20))
				.addComponent(buttons.get(21)).addComponent(stop));

		GroupLayout.SequentialGroup hGroup = layout
				.createSequentialGroup();
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(buttons.get(0))
				.addComponent(buttons.get(7)));
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(buttons.get(1))
				.addComponent(buttons.get(8))
				.addComponent(buttons.get(14)));
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(buttons.get(2))
				.addComponent(buttons.get(9))
				.addComponent(buttons.get(15))
				.addComponent(buttons.get(19)));
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(buttons.get(3))
				.addComponent(buttons.get(10))
				.addComponent(buttons.get(16))
				.addComponent(buttons.get(20)));
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(buttons.get(4))
				.addComponent(buttons.get(11))
				.addComponent(buttons.get(17))
				.addComponent(buttons.get(21)));
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(buttons.get(5))
				.addComponent(buttons.get(12))
				.addComponent(buttons.get(18)));
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(buttons.get(6))
				.addComponent(buttons.get(13)).addComponent(stop));
		layout.setVerticalGroup(vGroup);
		layout.setHorizontalGroup(hGroup);

	}

	private void openAudio(int idc) {
		boolean looping = false;
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(FILEBASE));
		FileFilter filter = new FileFilter() {

			/****************************************************************
			 * Tests to see if the file types can be displayed.
			 ****************************************************************/
			public boolean accept(File f) {
				return f.isDirectory()
						|| f.getName().toLowerCase().endsWith(".wav");
			}

			/****************************************************************
			 * The String representing the file types.
			 ****************************************************************/
			public String getDescription() {
				return ".wav files";
			}
		};
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(mainFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			int reply = JOptionPane.showConfirmDialog(null,
					"Do you want to loop this file?", "Loop?",
					JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION)
				looping = true;
			engine.setSound(idc, fc.getSelectedFile().getPath(),
					looping);
			buttons.get(idc)
					.setText(
							"<html>G"
									+ (idc + 1)
									+ " "
									+ fc.getSelectedFile()
											.getName()
											.substring(
													0,
													fc.getSelectedFile()
															.getName()
															.length() - 4));

			if (looping)
				buttons.get(idc).setText(
						buttons.get(idc).getText() + "<br>(Looping)");
			mainFrame.pack();
			mainFrame.setLocationRelativeTo(null);
		}
	}

	public void actionPerformed(ActionEvent e) {
		JComponent comp = (JComponent) e.getSource();
		for (int i = 0; i < KEYNUM; i++)
			if (comp == buttons.get(i))
				activate(i);
		if (comp == newConfig)
			reset();
		else if (comp == saveConfig)
			save();
		else if (comp == loadConfig)
			load();
		else if (comp == quit)
			quit();
		else if (comp == changeSound)
			changeSound();
		else if (comp == stop)
			stopAll();
		else if (comp == about)
			about();
	}

	private void about() {
		JOptionPane.showMessageDialog(null, "Author: Sam Eary     "
				+ "SoundEFX Version: 1.2.1", "About",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void stopAll() {
		for (int i = 0; i < KEYNUM; i++) {
			engine.stopClip(i);
			format(buttons.get(i));
		}
	}

	private void save() {
		JFileChooser fc = new JFileChooser();
		String file;
		fc.setCurrentDirectory(new File(CONFIGBASE));
		int returnVal = fc.showSaveDialog(mainFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION)
			try {
				file = fc.getSelectedFile().getPath();
				if (file.substring(file.length() - 9).equals(
						".soundEFX"))
					engine.save(file);
				else
					engine.save(file + ".soundEFX");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Error saving file!", "Save Error",
						JOptionPane.ERROR_MESSAGE);
			}
	}

	private void load() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(CONFIGBASE));
		FileFilter filter = new FileFilter() {

			/****************************************************************
			 * Tests to see if the file types can be displayed.
			 ****************************************************************/
			public boolean accept(File f) {
				return f.isDirectory()
						|| f.getName().toLowerCase()
								.endsWith(".soundefx");
			}

			/****************************************************************
			 * The String representing the file types.
			 ****************************************************************/
			public String getDescription() {
				return ".soundEFX files";
			}
		};
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(mainFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				engine.load(fc.getSelectedFile().getPath());
				update();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,
						"Could not find file!", "Missing File",
						JOptionPane.ERROR_MESSAGE);
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null,
						"Could not load file!", "Invalid File",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void update() {
		for (int i = 0; i < KEYNUM; i++) {
			try {
				File f = new File(engine.getSoundName(i));
				String name = f.getName();
				boolean looping = engine.isLooped(i);
				buttons.get(i).setText(
						"<html>G" + (i + 1) + " "
								+ name.substring(0, name.length() - 4));
				if (looping)
					buttons.get(i).setText(
							buttons.get(i).getText() + "<br>(Looping)");
			} catch (NullPointerException e) {
				buttons.get(i).setText("G" + (i + 1));
			}
		}
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
	}

	private void quit() {
		int reply = JOptionPane
				.showConfirmDialog(
						null,
						"Are you sure that you want to quit?  All unsaved changes will be lost.",
						"Quitting?", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	private void changeSound() {
		String[] options = new String[KEYNUM];
		for (int i = 0; i < KEYNUM; i++)
			options[i] = "G" + (i + 1);
		String reply = (String) JOptionPane
				.showInputDialog(null, "Choose key", "Change Sound",
						JOptionPane.QUESTION_MESSAGE, null, options,
						options[0]);
		reply = reply.substring(1);
		int idc = Integer.parseInt(reply) - 1;
		openAudio(idc);
	}

	private void reset() {
		int reply = JOptionPane.showConfirmDialog(null,
				"Are you sure?  All unsaved changes will be lost.",
				"New Configuration?", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			engine.reset();
			for (int i = 0; i < KEYNUM; i++)
				buttons.get(i).setText("G" + (i + 1));
			mainFrame.pack();
			mainFrame.setLocationRelativeTo(null);
		}
	}

	private void activate(int keyNum) {
		if (keyNum == 22 || keyNum == 23)
			stopAll();
		else if (engine.isPlaying(keyNum)) {
			engine.stopClip(keyNum);
			if (engine.isLooped(keyNum))
				format(buttons.get(keyNum));
		} else if (engine.isSound(keyNum))
			try {
				engine.playSound(keyNum);
				if (engine.isLooped(keyNum))
					playFormat(buttons.get(keyNum));
			} catch (UnsupportedAudioFileException e) {
				JOptionPane.showMessageDialog(null,
						"That filetype is not supported!",
						"Invalid File Type", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Could not read file!", "Read Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (LineUnavailableException e) {
				JOptionPane.showMessageDialog(null,
						"Audio line unavalible!", "Line Error",
						JOptionPane.ERROR_MESSAGE);
			}
		else
			openAudio(keyNum);

	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		SoundEFX a = new SoundEFX();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode >= 56 && keyCode <= (65 + 26)) {
			if (engine.isSound(keyCode - 65))
				playFormat(buttons.get(keyCode - 65));
			activate(keyCode - 65);
		}
		else if(keyCode == KeyEvent.VK_ESCAPE)
			quit();
	}

	private void playFormat(Component comp) {
		comp.setBackground(Color.RED);
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode >= 56 && keyCode <= (65 + 26)
				&& !engine.isLooped(keyCode - 65))
			format(buttons.get(keyCode - 65));
	}

	public void keyTyped(KeyEvent arg0) {
	}
}
