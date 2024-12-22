package org.earytech.soundefx;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SoundIDCTest {

    private SoundIDC soundIDC;

    // Setup method that runs before each test
    @Before
    public void setUp() {
        // Initialize the object before each test
        soundIDC = new SoundIDC("test_sound.wav", true);
    }

    // Test the constructor and fileName getter
    @Test
    public void testGetFileName() {
        assertNotNull("File name should not be null", soundIDC.getFileName());
        assertEquals("File name should match", "test_sound.wav", soundIDC.getFileName());
    }

    // Test the constructor and looping getter
    @Test
    public void testGetLooping() {
        assertTrue("Looping should be true", soundIDC.isLooping());
        
        // Create a new instance with different looping behavior
        SoundIDC soundIDCWithoutLoop = new SoundIDC("another_sound.wav", false);
        assertFalse("Looping should be false", soundIDCWithoutLoop.isLooping());
    }

    // Test the constructor with a null file name (assuming it's allowed in your constructor logic)
    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullFileName() {
        new SoundIDC(null, true);  // Should throw NullPointerException
    }

    // Test equality of SoundIDC objects (two objects with the same fileName and looping behavior)
    @Test
    public void testEquals() {
        SoundIDC soundIDC1 = new SoundIDC("test_sound.wav", true);
        SoundIDC soundIDC2 = new SoundIDC("test_sound.wav", true);
        assertEquals("Objects should be equal", soundIDC1, soundIDC2);
        
        SoundIDC soundIDC3 = new SoundIDC("different_sound.wav", true);
        assertNotEquals("Objects should not be equal", soundIDC1, soundIDC3);
    }

    // Test hashCode for consistent behavior with equals method
    @Test
    public void testHashCode() {
        SoundIDC soundIDC1 = new SoundIDC("test_sound.wav", true);
        SoundIDC soundIDC2 = new SoundIDC("test_sound.wav", true);
        assertEquals("Hash codes should be equal for equal objects", soundIDC1.hashCode(), soundIDC2.hashCode());

        SoundIDC soundIDC3 = new SoundIDC("different_sound.wav", true);
        assertNotEquals("Hash codes should be different for different objects", soundIDC1.hashCode(), soundIDC3.hashCode());
    }
}
