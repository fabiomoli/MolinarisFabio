package it.polito.tdpProvaFinale.BaionettaUpdater.model;

import java.util.HashSet;
import java.util.Set;

import it.polito.tdpProvaFinale.baionettaUpdater.dao.ArticoloDAO;
import it.polito.tdpProvaFinale.baionettaUpdater.feed.ArticoloFeed;

public class Model {

	private Set<Articolo> articoliRSS = new HashSet<>();
	private Set<Articolo> articoliDB = new HashSet<>();

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

	public void update() {
		getAllArticoliDB();
		getArticoliFromRss();
		Set<Articolo> articoliNew = new HashSet<>();
		if (!articoliRSS.isEmpty()) {
			for (Articolo a : articoliRSS) {
				if (!articoliDB.contains(a)){
					dao.addArticolo(a);
					articoliNew.add(a);
					System.out.println(articoliNew.size());
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
}
