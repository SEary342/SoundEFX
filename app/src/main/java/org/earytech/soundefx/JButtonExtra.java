package org.earytech.soundefx;

import javax.swing.JButton;

/**************************************************************************
 * A class to extend {@code JButton} in order to store further
 * information about the buttons.
 * 
 * @author Sam Eary
 * @version 1.0
 **************************************************************************/
public class JButtonExtra extends JButton {

	/** The serial id. */
	private static final long serialVersionUID = -8924774949986790979L;

	/** The letter id number of the button. */
	private int id;

	/**************************************************************************
	 * Primary constructor for the {@code JButtonExtra} class. Creates a
	 * new button.
	 * 
	 * @param id
	 *            The numerical letter id of the button.
	 * @param name
	 *            The text to be placed on the button.
	 **************************************************************************/
	public JButtonExtra(int id, String name) {
		super();
		this.id = id;
		this.setText(name);
	}

	/**************************************************************************
	 * Gets the ID number of the button.
	 * 
	 * @return The ID number of the button.
	 **************************************************************************/
	protected int getID() {
		return id;
	}
}
