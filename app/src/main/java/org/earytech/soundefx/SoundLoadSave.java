package org.earytech.soundefx;

import java.io.*;
import java.text.ParseException;
import java.util.Scanner;

/**
 * The SoundLoadSave class handles the loading and saving of SoundIDC objects 
 * to/from a file. It supports saving the sound data to a file and loading it 
 * back into an array of SoundIDC objects.
 */
public class SoundLoadSave {
    // Array to hold loaded SoundIDC objects
    private SoundIDC[] sounds;

    /**
     * Default constructor to initialize the SoundLoadSave object.
     */
    public SoundLoadSave() {
        // No initialization required at this time
    }

    /**
     * Saves the given array of SoundIDC objects to the specified file.
     * Each SoundIDC object's file name and looping status are written to the file.
     * 
     * @param soundIDC The array of SoundIDC objects to save
     * @param fileName The name of the file where data should be saved
     * @throws IOException If an I/O error occurs while writing the file
     */
    protected void saveGame(SoundIDC[] soundIDC, String fileName) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
            writeData(soundIDC, out);
        }
    }

    /**
     * Writes the data from the SoundIDC array to the given BufferedWriter.
     * Each SoundIDC object's file name and looping status are written in CSV format.
     * 
     * @param soundIDC The array of SoundIDC objects to write
     * @param out The BufferedWriter to write the data to
     * @throws IOException If an I/O error occurs while writing the data
     */
    private void writeData(SoundIDC[] soundIDC, BufferedWriter out) throws IOException {
        for (SoundIDC sound : soundIDC) {
            if (sound != null) {
                out.write(sound.getFileName() + "," + sound.isLooping());
            } else {
                out.write("null"); // If the SoundIDC is null, write "null"
            }
            out.newLine();
        }
    }

    /**
     * Loads the sound data from a file and parses it into an array of SoundIDC objects.
     * Each line in the file represents one SoundIDC object, containing a file name 
     * and a looping status.
     * 
     * @param fileName The name of the file to load data from
     * @throws FileNotFoundException If the specified file does not exist
     * @throws ParseException If the data in the file is improperly formatted
     */
    protected void load(String fileName) throws FileNotFoundException, ParseException {
        try (Scanner sc = new Scanner(new File(fileName))) {
            loadConfig(sc);
        }
    }

    /**
     * Parses the sound configuration from the input scanner and populates the sounds array.
     * Each line in the file should contain a file name and a looping status.
     * 
     * @param input The scanner containing the data to parse
     * @throws ParseException If the data cannot be parsed properly
     */
    private void loadConfig(Scanner input) throws ParseException {
		String data;
		Scanner sc;
		boolean looping;
		String fileName;
		sounds = new SoundIDC[SoundEFX.KEYNUM];
		for (int i = 0; i < SoundEFX.KEYNUM; i++) {
			data = getNextDataLine(input);
			if (data.isEmpty()) {
				continue; // Skip empty lines
			}
	
			sc = new Scanner(data);
			sc.useDelimiter(",");
			if (sc.hasNext()) {
				fileName = sc.next();
				if ("null".equals(fileName)) {
					continue; // Skip "null" entries
				}
	
				if (sc.hasNext()) {
					looping = Boolean.parseBoolean(sc.next());
					sounds[i] = new SoundIDC(fileName, looping);
				} else {
					// Handle the case where the looping value is missing
					sc.close();
					throw new ParseException("Missing looping value in line: " + data, i);
				}
			} else {
				// Handle the case where the file name is missing
				sc.close();
				throw new ParseException("Missing file name in line: " + data, i);
			}
		}
	}
	

    /**
     * Retrieves the array of SoundIDC objects.
     * 
     * @return The array of SoundIDC objects
     */
    protected SoundIDC[] getSoundIDC() {
        return sounds;
    }

    /**
     * Reads the next line of data from the scanner.
     * If no more data is available, returns an empty string.
     * 
     * @param input The scanner to read from
     * @return The next line of data, or an empty string if none is available
     */
    private String getNextDataLine(Scanner input) {
        return input.hasNext() ? input.nextLine().trim() : "";
    }
}
