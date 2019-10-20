package sound;

import java.io.*;
import java.text.ParseException;
import java.util.Scanner;

public class SoundLoadSave {
	private SoundIDC[] sounds;

	public SoundLoadSave() {

	}

	protected void saveGame(SoundIDC[] soundIDC, String fileName)
			throws IOException {
		BufferedWriter out = new BufferedWriter(
				new FileWriter(fileName));
		writeData(soundIDC, out);
		out.close();
	}

	private void writeData(SoundIDC[] soundIDC, BufferedWriter out)
			throws IOException {
		SoundIDC curr;
		for (int i = 0; i < soundIDC.length; i++) {
			curr = soundIDC[i];
			if (curr != null)
				out.write(curr.getFileName() + "," + curr.getLooping());
			else
				out.write("null");
			out.newLine();
		}
	}

	protected void load(String fileName) throws FileNotFoundException,
			ParseException {
		Scanner sc = new Scanner(new File(fileName));
		loadConfig(sc);
	}

	private void loadConfig(Scanner input) throws ParseException {
		String data;
		Scanner sc;
		boolean looping;
		String fileName;
		sounds = new SoundIDC[SoundEFX.KEYNUM];
		for (int i = 0; i < SoundEFX.KEYNUM; i++) {
			data = getNextDataLine(input);
			sc = new Scanner(data);
			sc.useDelimiter(",");
			fileName = sc.next();
			if (fileName.equals("null"))
				continue;
			looping = Boolean.parseBoolean(sc.next());
			sounds[i] = new SoundIDC(fileName, looping);
		}
	}

	protected SoundIDC[] getSoundIDC() {
		return sounds;
	}

	private String getNextDataLine(Scanner input) {
		String data = "";
		if (input.hasNext())
			data = input.nextLine().trim();
		return data;
	}
}
