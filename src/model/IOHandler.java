package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IOHandler {
	public static IOHandler ioHandler;
	
	/* privater Konstruktor */
	private IOHandler() {
	};

	/**
	 * "get Instance" wird verwerndet, um nur genau ein Objekt der Klasse IOHandler zu verwalten. <br><br>
	 * 
	 * 1. Existiert ein IOHandler-Objekt? <br> 
	 * 1.1 Nein ! erzeuge es <br> 
	 * 2 gebe das Objekt zurück <br> 
	 * 
	 * @return Instance der Klasse
	 */
	public static IOHandler getInstance() {
		if (IOHandler.ioHandler == null) {
			IOHandler.ioHandler = new IOHandler();
		}
		return IOHandler.ioHandler;
	}

	/**
	 * "read" liest eine Textdatei aus und fuellt das uebergebene map-Objekt. <br><br>
	 * 
	 * 1. Exisitert die Datei? <br>
	 * 1.1 Nein! Programm wird beendet <br>
	 * 2. Einlesen des Namen des Kennwertes <br>
	 * 3. Zeilenweise die Daten eines Staates einlesen, ein state-Objekt erstellen und der map hinzufuegen <br>
	 * 4. Zeilenweise die Strings der Nachbarschaftsbeziehungen einlesen und an map-Methode uebergeben <br>
	 * 
	 * @param fp Pfad zur Textdatei
	 * @param map das Map-Objekt
	 */
	public void read(String fp, Map map) {
		File file = new File(fp);

		if (!file.canRead() || !file.isFile()) {
			System.out.println(".txt-Datei nicht gefunden. Programm wird beendet.");
			System.exit(0);
		}

		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(fp));
			String row = null;

			// get title
			row = in.readLine();
			map.setKeyFigure(row);

			// skip next line
			row = in.readLine();

			// get state data and add it to list
			while (!(row = in.readLine()).contains("#")) {
				String[] strpdData = row.split(" ");
				double tmpArea = Double.parseDouble(strpdData[1]);
				double tmpX = Double.parseDouble(strpdData[2]);
				double tmpY = Double.parseDouble(strpdData[3]);

				State tmpState = new State(strpdData[0], tmpArea, tmpX, tmpY);
				map.getStates().add(tmpState);
			}

			// get state data and add it to list
			while ((row = in.readLine()) != null) {
				map.addNeighborsToState(row);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}

	}

	
	/***
	 * "write" erstellt aus den Informationen des map-Objekts eine Textdatei, die mit dem Tool gnu-plot dargestellt werden kann. <br><br>
	 * 
	 * 1. Erstelle den gesamten Pfad zur Textdatei <br>
	 * 2. Schreibe alle Daten zur Achsenskalierung in die Datei <br>
	 * 3. Schreibe fuer jeden Staat die passenden Daten in die Textdatei <br>
	 * 4. Schreibe die restlichen Daten in die Textdatei <br>
	 * 
	 * @param fp Pfad der (zu speichernden) Textdatei
	 * @param fn Name der Datei
	 * @param map Map-Objekt
	 */
	public void write(String fp, String fn, String ext, Map map) {
		String[] tmp = fn.split(".txt");
		
		String gfp = fp + "output/" + tmp[0] + ext + "_out.txt";
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(gfp));
			bw.write("reset");
			bw.newLine();
			bw.write("set xrange [" + map.getRange()[0] + ":" + map.getRange()[1] + "]");
			bw.newLine();
			bw.write("set yrange [" + map.getRange()[2] + ":" + map.getRange()[3] + "]");
			bw.newLine();
			bw.write("set size ratio 1.0");
			bw.newLine();
			bw.write("set title \"" + map.getKeyFigure() + ", Iteration: " + map.getIteration() +  "\"");
			bw.newLine();
			bw.write("unset xtics");
			bw.newLine();
			bw.write("unset ytics");
			bw.newLine();
			bw.write("unset ytics");
			bw.newLine();
			bw.write("$data << EOD");
			bw.newLine();
			int idCnt = 0;
			for (State s : map.getStates()) {
				bw.write(s.getM().getX() + " " + s.getM().getY() + " " + s.getR() + " " + s.getCc() + " " + idCnt);
				bw.newLine();
				idCnt++;
			}
			bw.write("EOD");
			bw.newLine();
			bw.newLine();
			bw.write("plot \\");
			bw.newLine();
			bw.write("'$data' using 1:2:3:5 with circles lc var notitle, \\");
			bw.newLine();
			bw.write("'$data' using 1:2:4:5 with labels font \"arial, 9\" tc var notitle");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
