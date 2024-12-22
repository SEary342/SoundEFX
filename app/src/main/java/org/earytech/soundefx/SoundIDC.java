package org.earytech.soundefx;

import java.util.Objects;

/**
 * The SoundIDC class represents an immutable sound identification object, 
 * which includes the file name of the sound and whether the sound should loop.
 * 
 * This class is used to encapsulate sound data for playback systems, ensuring
 * that sound files are referenced with proper metadata about their looping behavior.
 * 
 * <p>This class is immutable, meaning its state cannot be changed after it is created.
 * It is also thread-safe, since its fields are final and cannot be modified after construction.</p>
 */
public final class SoundIDC {
    
    /** The name of the sound file. */
    private final String fileName;
    
    /** Whether the sound should loop when played. */
    private final boolean looping;

    /**
     * Constructs a new SoundIDC object with the specified file name and looping behavior.
     * 
     * @param fileName The name of the sound file.
     * @param looping  A boolean indicating whether the sound should loop. 
     *                 A value of true means the sound will loop, and false means it will play once.
     * @throws NullPointerException if the provided fileName is null.
     */
    public SoundIDC(String fileName, boolean looping) {
        this.fileName = Objects.requireNonNull(fileName, "fileName cannot be null");
        this.looping = looping;
    }

    /**
     * Returns the name of the sound file.
     * 
     * @return The name of the sound file.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns whether the sound is set to loop.
     * 
     * @return true if the sound loops; false otherwise.
     */
    public boolean isLooping() {
        return looping;
    }

    /**
     * Returns a string representation of the SoundIDC object.
     * 
     * <p>The returned string contains the file name and the looping status of the sound.</p>
     * 
     * @return A string representation of the SoundIDC object.
     */
    @Override
    public String toString() {
        return "SoundIDC{" +
                "fileName='" + fileName + '\'' +
                ", looping=" + looping +
                '}';
    }

    /**
     * Compares this object with the specified object for equality.
     * 
     * <p>Two SoundIDC objects are considered equal if they have the same file name and looping behavior.</p>
     * 
     * @param o The object to compare with.
     * @return true if the objects are equal; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoundIDC soundIDC = (SoundIDC) o;
        return looping == soundIDC.looping && fileName.equals(soundIDC.fileName);
    }

    /**
     * Returns a hash code value for the SoundIDC object.
     * 
     * <p>The hash code is computed based on the file name and looping status.</p>
     * 
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(fileName, looping);
    }
}
