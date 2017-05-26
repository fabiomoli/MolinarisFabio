package it.polito.tdpProvaFinale.laBaionettaReader.localBackup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import it.polito.tdpProvaFinale.laBaionettaReader.beans.Articolo;

public class LocalBackup {

	private int numArtNew;
	private int numAggiornati;
	private Set<String> artLetti = new HashSet<>();;

	public int getNumAggiornati() throws IOException {
		numAggiornati = numArtNew - getNumArtOld();
		return numAggiornati;
	}

	public void setNumArtNew(int numArtNew) {
		this.numArtNew = numArtNew;
	}

	public int getNumArtOld() throws IOException {
		if (artLetti.isEmpty()) {
			try {
				getArticoliFromFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return artLetti.size();
	}

	public Set<String> getArticoliFromFile() throws IOException {

		File BaioBackup = new File("BaioBackup.molis");
		BaioBackup.createNewFile(); // if file already exists will do nothing

		String s;

		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("BaioBackup.molis"));
			while ((s = reader.readLine()) != null) {

				String riga = s.split(" ")[0];
				artLetti.add(riga);

			}
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return artLetti;
	}

	public void updateFileBackup(Articolo articolo) throws IOException {

		if (!artLetti.contains(articolo.getLink())) {

			artLetti.add(articolo.getLink());

			FileWriter out = new FileWriter("BaioBackup.molis", true);

			String content = "" + articolo.getLink() + "\n";
			out.append(content);

			out.close();

		}
	}

	public boolean isLetto(String a) {
		if (!artLetti.contains(a)) {
			try {
				getArticoliFromFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return artLetti.contains(a);
	}

}
