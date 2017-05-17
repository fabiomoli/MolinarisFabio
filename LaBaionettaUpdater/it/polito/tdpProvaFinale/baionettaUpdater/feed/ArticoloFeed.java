package it.polito.tdpProvaFinale.baionettaUpdater.feed;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import it.polito.tdpProvaFinale.BaionettaUpdater.model.Articolo;
import it.polito.tdpProvaFinale.BaionettaUpdater.model.Mostrina;
import it.polito.tdpProvaFinale.BaionettaUpdater.model.ParolaChiave;
import it.polito.tdpProvaFinale.BaionettaUpdater.model.Penna;

public class ArticoloFeed {

	public List<Articolo> getArticoliFromRss() {

		List<Articolo> articoli = new ArrayList<>();

		String feedUrl = "https://labaionetta.blogspot.com/feeds/posts/default?alt=rss";

		FeedReader fd = new FeedReader(feedUrl);

		if (fd.read() == null) {
			System.out.println("Internet!!");
		} else {
			Feed feed = fd.readFeed();

			for (FeedMessage message : feed.getMessages()) {

				if (message.getLink() != null) {
					Mostrina m = new Mostrina(message.getCategory());
					Penna p = creaPenna(message.getAuthor());

					LocalDate date = LocalDate.parse(message.getPubDate(), DateTimeFormatter.RFC_1123_DATE_TIME);
					Articolo a = new Articolo(message.getTitle(), m, p, message.getLink(), date);
					if (!articoli.contains(a)) {
						articoli.add(a);
					}
				}
			}
		}
		return articoli;
	}

	private Penna creaPenna(String s) {
		if (s.contains("Baionetta")) {
			return new Penna("La Baionetta");
		}
		if (s.contains("Fabio")) {
			return new Penna("Fabio Molinaris");
		}
		if (s.contains("Daniele")) {
			return new Penna("Daniele Barale");
		}
		if (s.contains("Darth")) {
			return new Penna("Darth Gender");
		}
		if (s.contains("Federico")) {
			return new Penna("Federico Montagnani");
		}
		return null;
	}

	private Set<ParolaChiave> getText(Articolo a) {
		Set<ParolaChiave> paroleChiave = new HashSet<>();
		try {
			Document doc = Jsoup.connect(a.getLink()).get();
			Elements body = doc.getElementsByClass("post-body entry-content");
			String testoHtml = Jsoup.clean(body.html(), Whitelist.simpleText());
			for (String parola : testoHtml.split(" ")) {
				String p = parola.replaceAll("[^a-zA-Z0-9]", "");

				if (p.length() > 3 && !p.contains("quel") && !p.contains("dell")) {

					ParolaChiave pc = new ParolaChiave(p.toLowerCase(), a.getLink(), 1);

					if (!paroleChiave.contains(pc)) {
						paroleChiave.add(pc);
					}

					if (paroleChiave.contains(pc)) {
						for (ParolaChiave par : paroleChiave) {
							if (pc.getLink().equals(par.getLink()) && pc.getParola().equals(par.getParola())) {
								int numero = par.getPeso();
								numero++;
								par.setPeso(numero);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Internet!!");
		}

		return paroleChiave;

	}

	public Set<ParolaChiave> getParoleGold(Articolo a) {

		Set<ParolaChiave> paroleChiaveGold = new HashSet<>();

		for (ParolaChiave parola : getText(a)) {
			if (parola.getPeso() > 4) {
				paroleChiaveGold.add(parola);
			}
		}

		return paroleChiaveGold;
	}

}
