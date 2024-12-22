package org.earytech.soundefx;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

public class SoundLoadSaveTest {

    private SoundLoadSave soundLoadSave;
    private SoundIDC[] testSounds;
    private String testFileName;

    @Before
    public void setUp() {
        // Initialize the SoundLoadSave instance and prepare the test data
        soundLoadSave = new SoundLoadSave();
        testFileName = "test_sounds.txt";
        testSounds = new SoundIDC[SoundEFX.KEYNUM];

        // Create sample SoundIDC objects
        for (int i = 0; i < SoundEFX.KEYNUM; i++) {
            testSounds[i] = new SoundIDC("sound" + i + ".mp3", i % 2 == 0); // Even indices loop, odd ones do not
        }
    }

    @After
    public void tearDown() {
        // Delete the test file after each test to clean up
        File file = new File(testFileName);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testSaveGame() throws IOException {
        // Save the sounds to a file
        soundLoadSave.saveGame(testSounds, testFileName);

        // Verify the file exists
        File file = new File(testFileName);
        assertTrue("The file should exist after saving", file.exists());

        // Optionally: Verify the content manually if needed
        // For simplicity, we are not verifying the content in this test.
    }

    @Test
    public void testLoad() throws IOException, ParseException {
        // Save the sounds to a file first
        soundLoadSave.saveGame(testSounds, testFileName);

        // Load the sounds back from the file
        soundLoadSave.load(testFileName);

        // Get the loaded SoundIDC array
        SoundIDC[] loadedSounds = soundLoadSave.getSoundIDC();

        // Verify the loaded sounds match the original data
        for (int i = 0; i < SoundEFX.KEYNUM; i++) {
            assertNotNull("SoundIDC at index " + i + " should not be null", loadedSounds[i]);
            assertEquals("File name should match", "sound" + i + ".mp3", loadedSounds[i].getFileName());
            assertEquals("Looping flag should match", i % 2 == 0, loadedSounds[i].isLooping());
        }
    }

    @Test(expected = IOException.class)
    public void testSaveGame_IOException() throws IOException {
        // Simulate an error by providing an invalid file name (e.g., directory instead of a file)
        String invalidFileName = "/invalid/directory/test_sounds.txt";
        soundLoadSave.saveGame(testSounds, invalidFileName);
    }

    @Test(expected = ParseException.class)
    public void testLoad_InvalidFormat() throws IOException, ParseException {
        // Create a file with invalid content (e.g., missing looping flag)
        File invalidFile = new File(testFileName);
        invalidFile.createNewFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(invalidFile))) {
            writer.write("sound0.mp3, true");
            writer.newLine();
            writer.write("invalidData");  // Invalid format
            writer.newLine();
            writer.write("sound1.mp3, false");
        }

        // Attempt to load the invalid file
        soundLoadSave.load(testFileName);
    }

    @Test
    public void testLoad_SkipNullEntries() throws IOException, ParseException {
        // Create a file with some null entries
        File invalidFile = new File(testFileName);
        invalidFile.createNewFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(invalidFile))) {
            writer.write("sound0.mp3,true");
            writer.newLine();
            writer.write("null");  // Null entry
            writer.newLine();
            writer.write("sound2.mp3,false");
        }

        // Load the file
        soundLoadSave.load(testFileName);

        // Get the loaded SoundIDC array
        SoundIDC[] loadedSounds = soundLoadSave.getSoundIDC();

        // Check that the null entry was skipped and the valid ones were loaded
        assertNotNull("Sound at index 0 should be loaded", loadedSounds[0]);
        assertNull("Sound at index 1 should be null", loadedSounds[1]);
        assertNotNull("Sound at index 2 should be loaded", loadedSounds[2]);
    }
}
