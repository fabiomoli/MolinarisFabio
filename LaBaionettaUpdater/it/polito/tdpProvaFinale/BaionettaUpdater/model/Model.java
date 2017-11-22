package it.polito.tdpProvaFinale.BaionettaUpdater.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import it.polito.tdpProvaFinale.baionettaUpdater.dao.ArticoloDAO;
import it.polito.tdpProvaFinale.baionettaUpdater.feed.ArticoloFeed;
import it.polito.tdpProvaFinale.baionettaUpdater.feed.BackupText;

public class Model {

	private Set<Articolo> articoliFILE = new HashSet<>();
	private Set<Articolo> articoliRSS = new HashSet<>();
	private Set<Articolo> articoliDB = new HashSet<>();

	private Set<Penna> penne = new HashSet<>();
	private Set<Mostrina> mostrine = new HashSet<>();

	ArticoloDAO dao = new ArticoloDAO();
	ArticoloFeed rss = new ArticoloFeed();

	public void getArticoliFromRss() {
		articoliRSS.clear();
		articoliRSS.addAll(rss.getArticoliFromRss());
	}

	public void getAllArticoliDB() {
		articoliDB.clear();
		articoliDB.addAll(dao.getAllArticoli());
	}

	public void getAllPenne(){
		penne.clear();
		penne.addAll(dao.getAllPenne());
	}

	public void getAllMostrine(){
		mostrine.clear();
		mostrine.addAll(dao.getAllMostrine());
	}

	public void update() throws IOException {

		//getArticoliFromFile();

		getAllArticoliDB();
		getArticoliFromRss();
		getAllPenne();
		getAllMostrine();

		Set<Articolo> articoliNew = new HashSet<>();
		if (!articoliRSS.isEmpty()) {
			for (Articolo a : articoliRSS) {
				if (!articoliDB.contains(a)){
					dao.addArticolo(a);
					articoliNew.add(a);
					//System.out.println(articoliNew.size());
				}
				if (!penne.contains(a.getPenna())){
					dao.addPenna(a.getPenna());
					penne.add(a.getPenna());
				}
				if(!mostrine.contains(a.getMostrina())){
					dao.addMostrina(a.getMostrina());
					mostrine.add(a.getMostrina());
				}
			}
		}

		if (!articoliFILE.isEmpty()) {
			for (Articolo a : articoliFILE) {
				if (!articoliDB.contains(a)){
					dao.addArticolo(a);
					articoliNew.add(a);
					System.out.println(a);
				}
				if (!penne.contains(a.getPenna())){
					dao.addPenna(a.getPenna());
					penne.add(a.getPenna());
				}
				if(!mostrine.contains(a.getMostrina())){
					dao.addMostrina(a.getMostrina());
					mostrine.add(a.getMostrina());
				}
			}
		}

		for (Articolo a : articoliNew) {
			Set<ParolaChiave> paroleChiave = new HashSet<>();
			// ottengo le parole chiave da web
			paroleChiave.addAll(rss.getParoleGold(a));
			// ottengo le parole chiave da db
			paroleChiave.addAll(dao.getAllParoleChiave(a));
			// ottengo le parole chiave dal titolo (valgono doppio)
			paroleChiave.addAll(getParoleTitolo(a));
			// aggiungo parole del titolo se non già presenti
			a.setParoleChiave(paroleChiave);
			for (ParolaChiave pca : a.getParoleChiave()) {
				if (!dao.getAllParoleChiave(a).contains(pca))
					dao.addParoleChiave(pca);
			}
			BackupText bt = new BackupText();
			bt.backupText(a);
			System.out.println("NUOVO!!!");
		}
	}

	private Set<ParolaChiave> getParoleTitolo(Articolo a) {

		Set<ParolaChiave> paroleChiave = new HashSet<>();

		for (String p : a.getTitolo().split(" ")) {
			if (p.length() > 3) {
				String parola = p.replaceAll("[^a-zA-Z0-9]", "");
				paroleChiave.add(new ParolaChiave(parola.toLowerCase(), a.getLink(), 2));
			}
		}

		return paroleChiave;
	}


	public void getArticoliFromFile() throws IOException{
		String s;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("src/BaioBackup.molis") );
			while( (s = reader.readLine()) != null ){
				//System.out.println(s);

				Articolo a = new Articolo(null, null, null, null, null);

				LocalDate date = LocalDate.parse(s.split("<> ")[0], DateTimeFormatter.ISO_LOCAL_DATE);
				a.setData(date);

				Mostrina m = null;
				if(!s.split("<> ")[2].isEmpty()){
					m = new Mostrina(s.split("<> ")[1]);
					a.setMostrina(m);
					}

				a.setTitolo(s.split("<> ")[2]);

				a.setLink(s.split("<> ")[3]);

				Penna p = new Penna(s.split("<> ")[4]);
				a.setPenna(p);

				articoliFILE.add(a);
				penne.add(p);
				mostrine.add(m);
			}

			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
