package sound;

public class SoundIDC {
	private String fileName;
	private boolean looping;

	public SoundIDC(String fileName, boolean looping) {
		this.fileName = fileName;
		this.looping = looping;
	}

	protected String getFileName() {
		return fileName;
	}

	protected boolean getLooping() {
		return looping;
	}
}
