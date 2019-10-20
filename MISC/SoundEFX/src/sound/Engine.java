package sound;

import java.io.*;
import java.text.ParseException;

import javax.sound.sampled.*;

public class Engine {
	private SoundIDC[] sounds;
	private Clip[] clip;
	private int size;

	public Engine(int size) {
		this.size = size;
		reset();
	}

	protected void save(String fileName) throws IOException {
		SoundLoadSave s = new SoundLoadSave();
		s.saveGame(sounds, fileName);
	}

	protected boolean isSound(int idc) {
		try {
			return sounds[idc] != null;
		} catch (Exception e) {
			return false;
		}
	}

	protected boolean isLooped(int idc) {
		if (sounds[idc] != null)
			return sounds[idc].getLooping();
		return false;
	}

	protected String getSoundName(int idc) {
		return sounds[idc].getFileName();
	}

	protected void reset() {
		sounds = new SoundIDC[size];
		clip = new Clip[size];
	}

	protected void playSound(int keyNum)
			throws UnsupportedAudioFileException, IOException,
			LineUnavailableException {
		playAudioClip(sounds[keyNum].getFileName(),
				sounds[keyNum].getLooping(), keyNum);
	}

	protected void setSound(int idc, String fileName, boolean looping) {
		sounds[idc] = new SoundIDC(fileName, looping);
	}

	protected void stopClip(int idc) {
		if (clip[idc] != null && clip[idc].isRunning())
			clip[idc].stop();
	}

	protected boolean isPlaying(int idc) {
		try {
			if (clip[idc] != null)
			return clip[idc].isRunning();
		} catch (Exception e) {
		}
		return false;
	}

	private void playAudioClip(String fileName, boolean looping, int idc)
			throws UnsupportedAudioFileException, IOException,
			LineUnavailableException {
		File soundFile = new File(fileName);
		AudioInputStream sound = AudioSystem
				.getAudioInputStream(soundFile);
		DataLine.Info info = new DataLine.Info(Clip.class,
				sound.getFormat());
		clip[idc] = (Clip) AudioSystem.getLine(info);
		clip[idc].open(sound);
		playClip(looping, idc);
	}

	private void playClip(boolean looping, int idc) {
		if (looping)
			clip[idc].loop(Clip.LOOP_CONTINUOUSLY);
		else
			clip[idc].start();
	}

	protected void load(String path) throws FileNotFoundException,
			ParseException {
		SoundLoadSave s = new SoundLoadSave();
		s.load(path);
		this.sounds = s.getSoundIDC();

	}
}
